/*
 * Copyright 2024, Hongyou Software Development Studio.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hongyou.baron.ag01;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.fasterxml.jackson.databind.JsonNode;
import com.hongyou.baron.RindjaException;
import com.hongyou.baron.ag01.faces.Column;
import com.hongyou.baron.logging.Log;
import com.hongyou.baron.logging.LogFactory;
import com.hongyou.baron.util.StringUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用浏览表格界面
 *
 * @author Berlin
 */
@RestController
@RequestMapping("/ag01/grider")
public class GriderInquiry extends AbstractInquiry {

    /**
     * logger
     */
    private static final Log logger = LogFactory.getLog(GriderInquiry.class);

    /**
     * 传入参数
     */
    @Data
    public static class GriderParam {

        /**
         * 功能模块号
         */
        private String module;

        /**
         * 界面名称
         */
        private String name;

        /**
         * 本地语言
         */
        private String local;

        /**
         * 查询的数据表格、实现主副表，多个表以逗号分隔
         */
        private String datatables;

        /**
         * 查询参数
         */
        private JsonNode params;

        /**
         * 字段排序
         */
        private Sorter sorter;

        /**
         * 当前页
         */
        private int pageNumber;

        /**
         * 每页记录数
         */
        private int pageSize;
    }

    /**
     * 加载浏览表格界面定义
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadDefine")
    public JsonNode loadDefine(@RequestBody final GriderParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            this.loadUserPermission(env, param.getModule());
            Grider grider = this.getGrider(env, param.getModule(), param.getName());

            return grider.generate(env);
        } catch (Exception e) {
            logger.error("加载浏览表格界面定义失败", e);
            throw new RindjaException("加载浏览表格界面定义失败", e);
        }
    }

    /**
     * 查询主表浏览数据
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadTableData")
    public JsonNode loadTableData(@RequestBody final GriderParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Grider grider = this.getGrider(env, param.getModule(), param.getName());

            return grider.getTableData(
                    env, param.getSorter(), param.getPageNumber(), param.getPageSize()
            );
        } catch (Exception e) {
            logger.error("加载浏览表格界面数据失败", e);
            throw new RindjaException("加载浏览表格界面数据失败", e);
        }
    }

    /**
     * 查询子表浏览数据
     *
     * @param param 前端传入参数
     */
    @PostMapping("/loadSubTablesData")
    public JsonNode loadSubTablesData(@RequestBody final GriderParam param) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Grider grider = this.getGrider(env, param.getModule(), param.getName());

            return grider.getSubTablesData(
                    env, param.getDatatables(), param.getSorter()
            );
        } catch (Exception e) {
            logger.error("加载浏览表格界面数据失败", e);
            throw new RindjaException("加载浏览表格界面数据失败", e);
        }
    }

    /**
     * 导出通用浏览表格Excel数据
     */
    @PostMapping("/export")
    private void export(
            @RequestBody final GriderParam param, final HttpServletResponse response
    ) {

        try {
            Environment env = this.createEnvironment(param.getLocal(), param.getParams());
            Grider grider = this.getGrider(env, param.getModule(), param.getName());
            JsonNode data = grider.getTableData(env, param.getSorter(), 1, 10000).get("data");

            // 表格标题
            List<String> titles = new ArrayList<>();
            for (Column column: grider.getDatatable().getColumns()) {
                column.generate(env);
                if (!column.isHidden() && column.isExportable()) {
                    titles.add(env.getLocalResource(column.getTitle()));
                }
            }

            List<List<String>> rows = new ArrayList<>();
            rows.add(titles);

            // 表格数据
            for (JsonNode item : data) {
                List<String> row = new ArrayList<>();
                for (Column column: grider.getDatatable().getColumns()) {
                    if (!column.isHidden() && column.isExportable()) {
                        JsonNode value = item.get(column.getName());
                        row.add(value.isNull() ? StringUtil.EMPTY : value.asText());
                    }
                }
                rows.add(row);
            }

            // 写入Excel
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.write(rows);

            // 响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=export.xlsx");

            // 写入流
            ServletOutputStream os = response.getOutputStream();
            writer.flush(os, true);
            writer.close();
            os.close();
        } catch (Exception e) {
            logger.error("导出通用浏览表格Excel数据失败", e);
            throw new RindjaException("导出通用浏览表格Excel数据失败");
        }
    }

    /**
     * 获取通用浏览表格界面定义
     *
     * @param module 模块名
     * @param name 界面名
     */
    private Grider getGrider(final Environment env, final String module, final String name) {
        Descriptor descriptor = this.getDescriptor(env, module);
        Grider grider = descriptor != null ? descriptor.getGrider(name) : null;
        if (grider == null) {
            throw new RindjaException("未加载到模块[{}]定义的浏览表格界面[{}]", module, name);
        }
        return grider;
    }
}

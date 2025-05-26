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

import com.hongyou.baron.cache.CacheUtil;
import com.hongyou.baron.cache.TimedCache;
import com.hongyou.baron.util.XmlUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import org.w3c.dom.Element;

import java.util.*;

/**
 * 通用界面国际化语言
 *
 * @author Berlin
 */
public class International {

    /**
     * 缓存数据库国际化语言(7天未使用自动清除, key: 语言@表名.字段名, value: 字段标题)
     */
    private static final TimedCache<String, String> tableFields = CacheUtil.newTimedCache(1000 * 60 * 60 * 24 * 7);

    /**
     * 缓存数据库枚举值国际化语言(7天未使用自动清除, key: 语言@表名.字段名@值, value: 枚举显示值)
     */
    private static final Map<String, String> tableFieldValues = new LinkedHashMap<>();

    /**
     * 界面定义的国际化语言(key: 语言@定义的key, value: 语言描述)
     */
    private final HashMap<String, String> entries = new HashMap<>();

    /**
     * 加载界面定义的国际化语言
     *
     * @param element 界面定义元素
     */
    public International(final Element element) {
        List<Element> lang = XmlUtil.getGrandChildElements(element, "intl", "lang");
        lang.forEach(this::getLangItems);
    }

    /**
     * 加载国际化语言配置项
     *
     * @param langNode 语言配置元素
     */
    private void getLangItems(final Element langNode) {
        String local = XmlUtil.getAttribute(langNode, "code");
        List<Element> itemNodes = XmlUtil.getChildElements(langNode, "item");

        itemNodes.forEach(itemNode -> {
            String key = XmlUtil.getAttribute(itemNode, "key");
            String label = XmlUtil.getTextContent(itemNode);
            this.entries.put(this.getCacheLabelKey(local, key), label);
        });
    }

    /**
     * 获取国际化语言标题
     *
     * @param local 语言代码
     * @param key 多语言字段
     */
    public String get(final String local, final String key) {
        if (!key.startsWith("@")) {
            return key;
        }
        String langKey = key.substring(1);

        // 先从本地加载，没有再从数据库加载
        String label = this.entries.get(this.getCacheLabelKey(local, langKey));
        if (label != null) {
            return label;
        }
        return this.getFieldLabel(local, langKey);
    }

    /**
     * 获取数据库国际化语言
     *
     * @param local 语言代码
     * @param key 定义的语言key
     */
    private String getFieldLabel(final String local, final String key) {
        String cacheKey = this.getCacheLabelKey(local, key);

        if (key.contains(".") && !tableFields.containsKey(cacheKey)) {
            this.loadTableFiled(local, key.split("\\.")[0]);
        }
        String label = tableFields.get(cacheKey);
        return label == null ? key : label;
    }

    /**
     * 加载数据库国际化语言
     *
     * @param local 语言代码
     * @param table 数据库表名
     */
    private void loadTableFiled(final String local, final String table) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select("fldnam", "title").
                eq("tblnam", table).
                eq("langug", local);
        List<Row> rows = Db.selectListByQuery("tbfdds", wrapper);

        rows.forEach(row -> {
            String fldnam = row.getString("fldnam");
            String title = row.getString("title");
            tableFields.put(this.getCacheLabelKey(local, table + "." + fldnam), title);
        });
    }

    /**
     * 获取枚举显示值
     *
     * @param local 语言代码
     * @param key 多语言字段
     * @param value 枚举值
     */
    public String getValue(final String local, final String key, final String value) {
        String cacheValueKey = this.getCacheValueKey(local, key, value);

        if (key.contains(".") && !tableFieldValues.containsKey(cacheValueKey)) {
            this.loadTableFiledValue(local, key.split("\\.")[0]);
        }
        String displayValue = tableFieldValues.get(cacheValueKey);
        return displayValue == null ? value : displayValue;
    }

    /**
     * 获取枚举列表
     *
     * @param local 语言
     */
    public List<ValueModel> getValues(final String local, final String key) {
        List<ValueModel> values = new ArrayList<>();

        // 是否能匹配到表字段值
        String keyPrefix = local + "@" + key;
        if (!this.matchedKey(local, key)) {
            this.loadTableFiledValue(local, key.split("\\.")[0]);
        }

        // 解析表字段值
        Set<String> keys = tableFieldValues.keySet();
        for (String cacheKey: keys) {
            if (cacheKey.startsWith(keyPrefix)) {
                values.add(ValueModel.builder().
                    value(cacheKey.split("@")[2]).
                    label(tableFieldValues.get(cacheKey)).
                    build()
                );
            }
        }
        return values;
    }

    /**
     * 检测是否能加载到字段的枚举值
     *
     * @param local 语言
     * @param key 表名.字段名
     */
    private boolean matchedKey(final String local, final String key) {
        String keyPrefix = local + "@" + key;
        Set<String> keys = tableFieldValues.keySet();
        for (String name : keys) {
            if (name.startsWith(keyPrefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载数据库枚举值
     *
     * @param local 语言代码
     * @param table 数据库表名
     */
    private void loadTableFiledValue(final String local, final String table) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.select("fldnam", "value", "dspval").
                eq("tblnam", table).
                eq("langug", local).
                orderBy("sortng");
        List<Row> rows = Db.selectListByQuery("vtbfdvl", wrapper);

        rows.forEach(row -> {
            String fldnam = row.getString("fldnam");
            String value = row.getString("value");
            String dspval = row.getString("dspval");
            tableFieldValues.put(
                this.getCacheValueKey(local, table + "." + fldnam, value), dspval
            );
        });
    }

    /**
     * 存入缓存标题的多语言key
     *
     * @param local 语言代码
     * @param key 定义的多语言key
     */
    private String getCacheLabelKey(final String local, final String key) {
        return local + "@" + key;
    }

    /**
     * 存入缓存枚举值的多语言key
     *
     * @param local 语言代码
     * @param key 定义的多语言key
     * @param value 枚举值
     */
    private String getCacheValueKey(final String local, final String key, final String value) {
        return local + "@" + key + "@" + value;
    }
}
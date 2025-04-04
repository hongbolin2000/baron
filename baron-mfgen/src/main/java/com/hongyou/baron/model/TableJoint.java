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
package com.hongyou.baron.model;

import com.hongyou.baron.GenerationException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 外键试图生成对象
 *
 * @author Berlin
 */
@Data
public class TableJoint {

    /**
     * 生成的关联表语句
     */
    private List<String> jointTablesSQL = new ArrayList<>();

    /**
     * 生成的关联表字段语句
     */
    private List<String> jointColumnsSQL = new ArrayList<>();

    /**
     * 外键字段
     */
    private List<JointColumn> jointColumns = new ArrayList<>();

    /**
     * 已经生成的外键表
     */
    List<String> joinedTables = new ArrayList<>();

    /**
     * 生成外键试图
     *
     * @param table 生成试图的表
     */
    protected TableJoint(final Table table) throws GenerationException {
        for (Map.Entry<String, List<String>> links: table.getJointLinks().entrySet()) {
            this.getJoint(links, table);
        }
    }

    /**
     * 生成外键试图
     *
     * @param links 外连接表字段
     * @param table 当前表
     */
    private void getJoint(
            final Map.Entry<String, List<String>> links, final Table table
    ) throws GenerationException {
        String[] tables = links.getKey().split(":");

        // 父表
        String parentName = table.getName();
        for (int i = 0; i < tables.length; i++) {
            String childName = tables[i];

            // 找到父表对象
            Table parentTable = table;
            if (i != 0) {
                parentTable = Database.getInstance().getTableByName(parentName, links.getKey());
            }
            String childAS = childName.toLowerCase();

            // 在父表中找到关联子表的外键对象
            ForeignKey childForeign = this.getForeignByRefTable(parentTable, childName);
            if (childForeign == null) {
                childForeign = this.getForeignByName(parentTable, "F" + childName);
                childAS = childForeign.getName();
                parentName = childForeign.getRefTable().getName();
            } else {
                parentName = childName;
            }

            // 检查子表是否已经生成关联
            if (!joinedTables.contains(childName)) {
                Column column = childForeign.getColumns().get(0);

                String joinTable = childForeign.getRefTable().getSqlName() + " AS " + childAS;
                String parentColumn = parentTable.getSqlName() + "." + column.getSqlName();
                String childColumn = childAS + "." + childForeign.getRefColumn().getSqlName();

                String joinSQL = column.isNullable() ? "INNER JOIN " : "LEFT JOIN ";
                this.jointTablesSQL.add(joinSQL + joinTable + " ON " + parentColumn + " = " + childColumn);
            }
            joinedTables.add(childName);
            if (i == tables.length - 1) {
                this.getJointColumns(childForeign.getRefTable(), childAS, links.getValue());
            }
        }
    }

    /**
     * 生成外键字段
     *
     * @param table 字段来源表
     * @param jointName 试图SQL中来源表外联接名
     * @param fields 需从表中拿取的字段
     * @throws GenerationException 拿取字段时的异常
     */
    private void getJointColumns(
            final Table table, final String jointName, final List<String> fields
    ) throws GenerationException {
        for (String field : fields) {
            String name = field;
            String asName = field;
            String alias = "";

            if (field.contains("as")) {
                String[] items = field.split("\\s+");
                if (items.length != 5) {
                    throw new GenerationException("外联接字段格式无效\n{}", field);
                }
                name = items[0].trim();
                asName = items[2].trim();
                alias = items[4].trim();
            }
            Column column = table.getColumnByName(name, "");

            alias = alias.isEmpty() ? column.getJlabel() : alias;
            this.jointColumns.add(new JointColumn(
                    asName.toLowerCase(), alias, column.getClabel(), column.getElabel(), column.getJavaType()
            ));
            this.jointColumnsSQL.add(jointName + "." + column.getSqlName() + " AS " + asName.toLowerCase());
        }
    }

    /**
     * 根据外键表查找外键对象
     *
     * @param table 查找的表
     * @param refTable 定义的外键表
     * @return 外键对象
     */
    protected ForeignKey getForeignByRefTable(final Table table, final String refTable) {
        return table.getForeignKeys().stream().filter(i -> refTable.equals(i.getRefTable().getName())).
                findFirst().orElse(null);
    }

    /**
     * 根据外键名查找外键对象
     *
     * @param table 查找的表
     * @param foreignName 查找的外键名称
     * @return 外键对象
     * @throws GenerationException 找不到时抛出的异常
     */
    protected ForeignKey getForeignByName(final Table table, final String foreignName) throws GenerationException {
        ForeignKey foreignKey = table.getForeignKeys().stream().filter(i -> foreignName.equals(i.getName())).
                findFirst().orElse(null);
        if (foreignKey == null) {
            throw new GenerationException("未找到表({})中定义的外键表({})", table.getName(), foreignName);
        }
        return foreignKey;
    }
}

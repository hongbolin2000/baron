/*
 * Copyright ${.now?string("yyyy")}, Hongyou Software Development Studio.
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
package ${classPackage}.mapper;

<#if table.uniqueKeys?size gt 0 || table.indexKeys?size gt 0 || table.foreignKeys?size gt 0>
import org.apache.ibatis.annotations.Select;
</#if>

import com.hongyou.baron.mybatis.AbstractFlexMapper;
import ${classPackage}.model.${table.javaName};
<#if table.hasMethod>
import java.util.List;
</#if>

/**
 * ABSTRACT TABLE MAPPER ${table.name}: ${table.label}
 *
 * @author Hongyou Code Generator
 * @date ${.now?string("yyyy-MM-dd")}
 * @since 1.0.0
 */
public interface Abstract${table.javaName}Mapper extends AbstractFlexMapper<${table.javaName}> {
<#list table.uniqueKeys as unique>

    /**
     * the method query of ${unique.method}
     *
    <#list unique.columns as column>
     * @param ${column.sqlName} the field of ${column.elabel}
    </#list>
     * @return the instance of table ${table.sqlName}
     */
    @Select("select * from ${table.sqlName} where<#list unique.columns as column> ${column.sqlName} = ${r"#{"}${column.sqlName}}<#if column_has_next> and</#if></#list>")
    ${table.javaName} ${unique.method}(<#list unique.columns as column>final ${column.javaType} ${column.sqlName}<#if column_has_next>, </#if></#list>);
</#list>
<#list table.indexKeys as index>
    <#if index.method??>

    /**
     * the method query of ${index.method}
     *
    <#list index.columns as column>
     * @param ${column.sqlName} the field of ${column.elabel}
    </#list>
     * @return the instance list of table ${table.sqlName}
     */
    @Select("select * from ${table.sqlName} where<#list index.columns as column> ${column.sqlName} = ${r"#{"}${column.sqlName}}<#if column_has_next> and</#if></#list>")
    List<${table.javaName}> ${index.method}(<#list index.columns as column>final ${column.javaType} ${column.sqlName}<#if column_has_next>, </#if></#list>);
    </#if>
</#list>
<#list table.foreignKeys as foreign>
    <#if foreign.method??>

    /**
     * the method query of ${foreign.method}
     *
    <#list foreign.columns as column>
     * @param ${column.sqlName} the field of ${column.elabel}
    </#list>
     * @return the instance list of table ${table.sqlName}
     */
    @Select("select * from ${table.sqlName} where<#list foreign.columns as column> ${column.sqlName} = ${r"#{"}${column.sqlName}}<#if column_has_next> and</#if></#list>")
    List<${table.javaName}> ${foreign.method}(<#list foreign.columns as column>final ${column.javaType} ${column.sqlName}<#if column_has_next>, </#if></#list>);
    </#if>
</#list>
}
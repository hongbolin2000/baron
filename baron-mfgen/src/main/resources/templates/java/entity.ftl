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
package ${classPackage}.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * TABLE ${table.name}: ${table.label}
 *
 * @author Hongyou Code Generator
 * @date ${.now?string("yyyy-MM-dd")}
 * @since 1.0.0
 */
@Getter
@Table("${table.sqlName}")
public class ${table.javaName} {

<#list table.columns as column>
    <#if column.enums?size gt 0>
    /**
     * ENUM ${column.name}: ${column.elabel}
     */
    public static class ${column.name} {
        private ${column.name}() {}

    <#list column.enums as enum>
        /**
         * the value ${enum.code} of ${enum.field}
         */
        public static final String ${enum.field?upper_case} = "${enum.code}";
        <#if enum_has_next>

        </#if>
    </#list>
    }

    </#if>
</#list>
<#list table.columns as column>
    /**
     * FIELD ${column.name}: ${column.elabel}
     */
    <#if column.identity>
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    </#if>
    @JsonProperty(value = "${column.jlabel}", index = ${column_index + 1})
    private ${column.javaType} ${column.sqlName}<#if column.javaValue??> = ${column.javaValue}</#if>;
    <#if column_has_next>

    </#if>
</#list>

<#list table.columns as column>
    /**
     * set value for current instance field ${column.sqlName}
     *
     * @param ${column.sqlName} the ${column.elabel} to set current instance field ${column.sqlName}
     * @return current instance
     */
    public ${table.javaName} ${column.sqlName}(final ${column.javaType} ${column.sqlName}) {
        this.${column.sqlName} = ${column.sqlName};
        return this;
    }
    <#if column_has_next>

    </#if>
</#list>
}
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
package ${classPackage}.pojo;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import ${classPackage}.model.${table.javaName};

/**
 * POJO: ${table.elabel}
 *
 * @author Hongyou Code Generator
 * @date ${.now?string("yyyy-MM-dd")}
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${table.javaName}Pojo {

<#list table.columns as column>
    /**
     * ${column.clabel}
     */
    private ${column.javaType} ${column.jlabel};
    <#if column_has_next>

    </#if>
</#list>

    /**
     * pojo convert to entity
     */
    public void toEntity(final ${table.javaName}Pojo pojo) {
        ${table.javaName} ${table.sqlName} = new ${table.javaName}();
        ${table.sqlName}.<#list table.columns as column><#if column_index != 0>            </#if>${column.sqlName}(pojo.get${column.javaName}())<#if column_has_next>.<#else>;</#if>
        </#list>
    }
}

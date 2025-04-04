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

import com.hongyou.baron.Reference;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
* TABLE VIEW V${table.name}: ${table.label}
*
* @author Hongyou Code Generator
* @date ${.now?string("yyyy-MM-dd")}
* @since 1.0.0
*/
@Getter
@Table("v${table.sqlName}")
public class V${table.javaName} extends Model<${"V"}${table.javaName}> {

<#list table.columns as column>
    /**
     * FIELD ${column.name}: ${column.elabel}
     */
    @Getter
    <#if column.identity>
    @Id(keyType = KeyType.None)
    </#if>
    @JsonProperty(value = "${column.jlabel}", index = ${column_index + 1})
    @Reference(alias = "${column.jlabel}", primary = "${column.elabel}", secondary = "${column.clabel}")
    private ${column.javaType} ${column.sqlName};
    <#if column_has_next>

    </#if>
</#list>

<#list table.columns as column>
    <#if column.identity>
    /**
     * set value for current instance field ${column.sqlName}
     *
     * @param ${column.sqlName} the ${column.elabel} to set current instance field ${column.sqlName}
     * @return current instance
     */
    public V${table.javaName} ${column.sqlName}(final ${column.javaType} ${column.sqlName}) {
        this.${column.sqlName} = ${column.sqlName};
        return this;
    }
    </#if>
</#list>

<#list table.joint.jointColumns as column>
    /**
     * JOINT FIELD ${column.name}: ${column.elabel}
     */
    @JsonProperty(value = "${column.jlabel}", index = ${table.columns?size + column_index + 1})
    @Reference(alias = "${column.jlabel}", primary = "${column.elabel}", secondary = "${column.clabel}")
    private ${column.javaType} ${column.name};
    <#if column_has_next>

    </#if>
</#list>
}
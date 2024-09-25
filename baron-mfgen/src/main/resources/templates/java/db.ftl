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
package ${classPackage};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ${classPackage}.mapper.*;

/**
 * DB COMPONENT: ${dbName}
 *
 * @author Hongyou Code Generator
 * @date ${.now?string("yyyy-MM-dd")}
 * @since 1.0.0
 */
@Component
public class ${dbName} {

<#list tables as table>
    /**
     * table mapper ${table.sqlName}
     */
    private ${table.javaName}Mapper ${table.sqlName}Mapper;

</#list>
<#list tables as table>
    /**
     * get table mapper ${table.sqlName}
     *
     * @return the mapper of ${table.sqlName}
     */
    public ${table.javaName}Mapper ${table.name?lower_case}() {
        return this.${table.sqlName}Mapper;
    }

    /**
     * inject table mapper ${table.sqlName}
     */
    @Autowired
    private void set${table.javaName}Mapper(final ${table.javaName}Mapper mapper) {
        this.${table.sqlName}Mapper = mapper;
    }
    <#if table_has_next>

    </#if>
</#list>
}
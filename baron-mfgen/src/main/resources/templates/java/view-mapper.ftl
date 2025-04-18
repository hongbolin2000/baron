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

import com.mybatisflex.core.BaseMapper;

import ${classPackage}.model.V${table.javaName};

/**
 * TABLE VIEW MAPPER V${table.name}: ${table.label}
 *
 * @author Hongyou Code Generator
 * @date ${.now?string("yyyy-MM-dd")}
 * @since 1.0.0
 */
public interface V${table.javaName}Mapper extends BaseMapper<${r"V"}${table.javaName}>{
}
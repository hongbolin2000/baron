CREATE TABLE ${table.sqlName} (
<#list table.columns as column>
${column.sqlName} ${column.mySQLType}<#if column.sqlValue??> DEFAULT ${column.sqlValue}</#if><#if column.nullable> NOT NULL</#if><#if column.identity> AUTO_INCREMENT</#if> COMMENT '${column.clabel}',
</#list>
PRIMARY KEY (<#list table.primaryKeys as column>${column.sqlName}<#if column_has_next>, </#if></#list>)
);
<#list table.uniqueKeys as unique>
ALTER TABLE ${table.sqlName} ADD CONSTRAINT ${unique.name} UNIQUE (<#list unique.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>);
</#list>
<#list table.foreignKeys as foreign>
ALTER TABLE ${table.sqlName} ADD FOREIGN KEY ${foreign.name} (${foreign.columns[0].sqlName}) REFERENCES ${foreign.refTable.sqlName} (${foreign.refColumn.sqlName});
</#list>
<#list table.indexKeys as index>
CREATE INDEX ${index.name} ON ${table.sqlName} (<#list index.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>);
</#list>


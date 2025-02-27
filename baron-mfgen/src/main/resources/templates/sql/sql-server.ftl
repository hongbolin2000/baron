CREATE TABLE ${table.sqlName} (
<#list table.columns as column>
    ${column.sqlName} ${column.sqlServerType}<#if column.sqlValue != ""> DEFAULT ${column.sqlValue}</#if><#if column.nullable> NOT NULL</#if>,
</#list>
    PRIMARY KEY (<#list table.primaryKeys as column>${column.sqlName}<#if column_has_next>, </#if></#list>)
);
<#list table.uniqueKeys as unique>
ALTER TABLE ${table.sqlName} ADD CONSTRAINT ${unique.name} UNIQUE (<#list unique.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>);
</#list>
<#list table.foreignKeys as foreign>
ALTER TABLE ${table.sqlName} ADD CONSTRAINT ${foreign.name} FOREIGN KEY (${foreign.columns[0].sqlName}) REFERENCES ${foreign.refTable.sqlName} (${foreign.refColumn.sqlName});
</#list>
<#list table.indexKeys as index>
CREATE INDEX ${index.name} ON ${table.sqlName} (<#list index.columns as column>${column.sqlName}<#if column_has_next>, </#if></#list>);
</#list>
<#list table.columns as column>
EXEC sp_addextendedproperty 'MS_Description', N'${column.clabel}', 'SCHEMA', N'dbo', 'TABLE', N'${table.sqlName}', 'COLUMN', N'${column.sqlName}';
</#list>


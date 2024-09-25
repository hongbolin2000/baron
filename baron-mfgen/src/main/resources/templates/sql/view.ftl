CREATE VIEW v${table.sqlName} AS SELECT
<#list table.columns as column>
    ${table.sqlName}.${column.sqlName},
</#list>
<#list table.joint.jointColumnsSQL as sql>
    ${sql}<#if sql_has_next>,</#if>
</#list>
FROM ${table.sqlName} as ${table.sqlName}
<#list table.joint.jointTablesSQL as sql>
${sql}<#if !sql_has_next>;</#if>
</#list>
<#if type == 'SQLServer'>GO

</#if>

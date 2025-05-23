<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-grider version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}列表
    </description>

    <filters>
    <#list table.columns as column>
        <#if column.enums?size gt 0>
        <opts name="${column.jlabel}" type="enum" table="${table.name}" column="${column.name}"/>
        </#if>
    </#list>
    </filters>

    <datatable title="@table.title">
        <actions>
            <action type="link" mode="dialog" icon="add" title="@add" link="/graces/editor/模块号/${editorName}" action="add"/>
        </actions>

        <statement>
            <table name="${table.sqlName}"/>
            <param column="cmpnid" type="=" expr="_companyId"/>
            <order by="XXX desc"/>
        </statement>

        <columns>
        <#list table.columns as column>
            <#if column.enums?size gt 0>
            <column type="tag" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="70" format="@@${table.name}.${column.name}" filter="@${column.jlabel}"/>
            <#elseif column.type.defineType == 'N' || column.type.defineType == 'INT'>
            <column type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="100" format="%#.###"/>
            <#elseif column.type.defineType == 'DATE'>
            <column type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="100" format="!yyyy-MM-dd" filter="date"/>
            <#elseif column.type.defineType == 'TIME'>
            <column type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="100" format="!HH:mm:ss"/>
            <#elseif column.type.defineType == 'TS'>
            <column type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="150" format="!yyyy-MM-dd HH:mm:ss"/>
            <#else>
            <column type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" width="100" filter="text"/>
            </#if>
        </#list>
            <column type="link" mode="dialog" name="edit" icon="edit" expr="${table.primaryKeys[0].sqlName}" title="@edit" width="80" link="/graces/editor/模块号/${editorName}" action="update"/>
            <column type="scriptLink" name="delete" icon="delete" expr="${table.primaryKeys[0].sqlName}" title="@delete" width="80" link="" labelColumn="" danger="true" action="delete"/>
        </columns>
    </datatable>

    <intl>
        <lang code="zh-CN">
            <item key="table.title">${table.clabel}</item>
            <item key="add">新增</item>
            <item key="edit">编辑</item>
            <item key="delete">删除</item>
        </lang>

        <lang code="en-US">
            <item key="table.title">${table.elabel}</item>
            <item key="add">Add</item>
            <item key="edit">Edit</item>
            <item key="delete">Delete</item>
        </lang>
    </intl>
</rindja-generic-grider>
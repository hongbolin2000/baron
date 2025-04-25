<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-viewer version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}详情
    </description>

    <title>@title</title>

    <statement>
        <table name="${table.sqlName}"/>
        <param column="${table.primaryKeys[0].sqlName}" type="=" expr="id"/>
    </statement>

    <master title="@master.title">
        <#list table.columns as column>
        <#if column.enums?size gt 0>
        <scene type="tag" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="@@${table.name}.${column.name}"/>
        <#elseif column.type.defineType == 'N' || column.type.defineType == 'INT'>
        <scene type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="%#.###"/>
        <#elseif column.type.defineType == 'DATE'>
        <scene type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!yyyy-MM-dd"/>
        <#elseif column.type.defineType == 'TIME'>
        <scene type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!HH:mm:ss"/>
        <#elseif column.type.defineType == 'TS'>
        <scene type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!yyyy-MM-dd HH:mm:ss"/>
        <#else>
        <scene type="label" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}"/>
        </#if>
        </#list>
    </master>

    <intl>
        <lang code="zh-CN">
            <item key="title">${table.clabel}详情</item>
            <item key="editor.title">${table.clabel}信息</item>
        </lang>

        <lang code="en-US">
            <item key="atitle">${table.elabel} Detail</item>
            <item key="editor.title">${table.elabel} Info</item>
        </lang>
    </intl>
</rindja-generic-viewer>
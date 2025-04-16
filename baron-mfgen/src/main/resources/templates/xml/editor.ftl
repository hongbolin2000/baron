<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-editor version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}编辑
    </description>

    <atitle>@atitle</atitle>
    <etitle>@etitle</etitle>
    <url>XXX</url>

    <statement>
        <table name="${table.sqlName}"/>
        <param column="${table.primaryKeys[0].sqlName}" type="=" expr="id"/>
    </statement>

    <editor title="@editor.title" labelColumn="">
    <#list table.columns as column>
        <#if column.enums?size gt 0>
        <input type="enum" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" options="@@${table.name}.${column.name}"<#if column.sqlValue??> default="${column.pageSqlValue}"</#if><#if column.nullable> required="true"</#if>/>
        <#elseif column.type.defineType == 'N' || column.type.defineType == 'INT'>
        <input type="number" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="%#.###"<#if column.sqlValue??> min="${column.pageSqlValue}" default="${column.pageSqlValue}"</#if> scale="${column.type.scale}"<#if column.nullable> required="true"</#if>/>
        <#elseif column.type.defineType == 'DATE'>
        <input type="date" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!yyyy-MM-dd"<#if column.nullable> required="true"</#if>/>
        <#elseif column.type.defineType == 'TIME'>
        <input type="time" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!HH:mm:ss"<#if column.nullable> required="true"</#if>/>
        <#elseif column.type.defineType == 'ID'>
        <input type="text" name="${column.jlabel}" expr="${column.sqlName}" hidden="true"/>
        <#else>
        <input type="text" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" <#if column.nullable>required="true"</#if> length="${column.type.length}"/>
        </#if>
    </#list>
    </editor>

    <intl>
        <lang code="zh-CN">
            <item key="atitle">新增${table.clabel}</item>
            <item key="etitle">修改${table.clabel}</item>
            <item key="editor.title">${table.clabel}信息</item>
        </lang>

        <lang code="en-US">
            <item key="atitle">Add ${table.elabel}</item>
            <item key="etitle">Modify ${table.elabel}</item>
            <item key="editor.title">${table.elabel} Info</item>
        </lang>
    </intl>
</rindja-generic-editor>
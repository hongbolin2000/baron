<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-editor version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}编辑
    </description>

    <atitle>@add</atitle>
    <etitle>@edit</etitle>
    <url>XXX</url>

    <statement>
        <table name="${table.sqlName}"/>
        <param column="${table.primaryKeys[0].sqlName}" type="=" expr="id"/>
    </statement>

    <editor title="@title">
    <#list table.columns as column>
        <#if column.enums?size gt 0>
        <input type="enum" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" options="@@${table.name}.${column.name}"<#if column.sqlValue??> default="${column.pageSqlValue}"</#if> <#if column.nullable>required="true"</#if>/>
        <#elseif column.type.defineType == 'N' || column.type.defineType == 'INT'>
        <input type="number" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="%#.###"<#if column.sqlValue??> min="${column.pageSqlValue}"</#if> scale="${column.type.scale}" <#if column.nullable>required="true"</#if>/>
        <#elseif column.type.defineType == 'DATE'>
        <input type="date" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!yyyy-MM-dd" <#if column.nullable>required="true"</#if>/>
        <#elseif column.type.defineType == 'TIME'>
        <input type="time" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" format="!HH:mm:ss" <#if column.nullable>required="true"</#if>/>
        <#else>
        <input type="text" name="${column.jlabel}" expr="${column.sqlName}" title="@${table.name}.${column.name}" <#if column.nullable>required="true"</#if> length="${column.type.length}"/>
        </#if>
    </#list>
    </editor>

    <intl>
        <lang code="zh-CN">
            <item key="add">${table.clabel}新增</item>
            <item key="edit">${table.clabel}编辑</item>
            <item key="title">${table.clabel}信息</item>
        </lang>

        <lang code="en-US">
            <item key="add">${table.clabel} Add</item>
            <item key="edit">${table.clabel} Edit</item>
            <item key="title">${table.clabel} Info</item>
        </lang>
    </intl>
</rindja-generic-editor>
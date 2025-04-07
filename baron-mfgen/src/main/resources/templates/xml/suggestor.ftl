<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-suggestor version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}查询建议器
    </description>

    <maxRows>20</maxRows>

    <statement>
        <table name="${table.sqlName}"/>
        <param column="cmpnid" type="=" expr="_companyId"/>
        <param column="${table.primaryKeys[0].sqlName}" type="=" expr="fieldValue"/>
        <param column="XXX" expr="searchValue"/>
        <param condition="or" column="XXX" expr="searchValue"/>
        <order by="XXX desc"/>
    </statement>

    <fields>
        <field name="value" expr="XXX" />
        <field name="label" expr="XXX" />
    </fields>
</rindja-generic-suggestor>

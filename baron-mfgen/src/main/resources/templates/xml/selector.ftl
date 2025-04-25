<?xml version="1.0" encoding="UTF-8"?>
<rindja-generic-selector version="1.0">
    <developer>
        <name>XXX</name>
    </developer>

    <description>
        ${table.clabel}查询选择器
    </description>

    <datatable labelColumn="">
        <statement>
            <table name="${table.sqlName}"/>
            <order by="XXX desc"/>
        </statement>

        <columns>
            <column type="selection" single="true" name="id" expr="${table.primaryKeys[0].sqlName}"/>
        </columns>
    </datatable>
</rindja-generic-selector>
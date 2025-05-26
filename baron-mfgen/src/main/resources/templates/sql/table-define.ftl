INSERT INTO TBDFMS (TBMSID, TBLNAM, TBLALS, CTITLE, ETITLE, REMARK) VALUES (${table.tbmsid}, '${table.name}', '${table.label}', '${table.clabel}', '${table.elabel}', '${table.remark}');
<#list table.columns as column>
INSERT INTO TBFDDS (TFDSID, TBLNAM, FLDNAM, FLDALS, TITLE, LANGUG, DTATYP, LENGTH, SCLLEN, DFTVAL, REQURD, REMARK) VALUES (${column.ctfdsid}, '${table.name}', '${column.name}', '${column.jlabel}', '${column.clabel}', 'zh-CN', '${column.type.nativeType}', ${column.type.length}, ${column.type.scale}, ${column.defineSqlValue}, '<#if column.nullable>Y<#else>N</#if>', '${column.remark}');
INSERT INTO TBFDDS (TFDSID, TBLNAM, FLDNAM, FLDALS, TITLE, LANGUG, DTATYP, LENGTH, SCLLEN, DFTVAL, REQURD, REMARK) VALUES (${column.etfdsid}, '${table.name}', '${column.name}', '${column.jlabel}', '${column.elabel}', 'en-US', '${column.type.nativeType}', ${column.type.length}, ${column.type.scale}, ${column.defineSqlValue}, '<#if column.nullable>Y<#else>N</#if>', '${column.remark}');
<#list column.enums as enum>
INSERT INTO TBFDVL (TFVLID, TFDSID, VALCDE, VALUE, DSPVAL, SORTNG, REMARK) VALUES (${enum.ctfvlid}, ${column.ctfdsid}, '${enum.field}', '${enum.code}', '${enum.clabel}', ${enum_index}, '${enum.remark}');
</#list>
<#if column.enums?size gt 0>
<#list column.enums as enum>
INSERT INTO TBFDVL (TFVLID, TFDSID, VALCDE, VALUE, DSPVAL, SORTNG, REMARK) VALUES (${enum.etfvlid}, ${column.etfdsid}, '${enum.field}', '${enum.code}', '${enum.elabel}', ${enum_index}, '${enum.remark}');
</#list>
</#if>
</#list>


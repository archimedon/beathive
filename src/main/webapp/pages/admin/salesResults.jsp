<%@ include file="/common/var_contentType.jsp"%>
<div>
<div id="itemlist">
<%--decorator="org.displaytag.decorator.TotalTableDecorator" --%>
	<display:table name="${salesReportPager}" id="loop" htmlId="soldLoops" class="table cliptable" cellspacing="0" cellpadding="0" requestURI="/admin/salesReport.html" export="true">
	<display:column title="email" property="email" sortable="true" sortProperty="u.email" media="html csv xml xls pdf"/>
	<display:column title="storeName" property="storeName" sortable="true" sortProperty="storeName" media="html"/>
	<display:column title="amount" property="price" media="html"/>
	<display:column title="net" property="net" sortable="true" sortProperty="net"  media="html csv xml xls pdf"/>
	<display:column title="created" property="created" sortable="true" sortProperty="created" media="html"/>
	<display:column title="currency"media="html csv xml xls pdf">USD</display:column>
    <display:setProperty name="paging.banner.item_name" value="producer"/>
    <display:setProperty name="paging.banner.items_name" value="producers"/>
    <display:setProperty name="export.excel.filename" value="Producers.xls"/>
    <display:setProperty name="export.csv.filename" value="Producers.csv"/>
    <display:setProperty name="export.pdf.filename" value="Producers.pdf"/>
    <display:setProperty name="export.xml.filename" value="Producers.xml"/>
	</display:table>
</div>
<div>
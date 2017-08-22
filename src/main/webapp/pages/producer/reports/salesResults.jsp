<%@ include file="/common/var_contentType.jsp"%>
<div>
<div id="summary">
	<fieldset width="250px" style="float: left; padding: 5px; margin:5px;">
		<legend>Volume</legend>
		<ul width="250px" style="list-style: none inside; padding-left:0;margin-left:0;" >
			<li>Loops: ${salesReportPager.loopVolume}</li>
			<li>Trackpacks: ${salesReportPager.trackpackVolume}</li>
			<%-- li><br/>Total: ${salesReportPager.fullListSize}</li --%>
			<li><br/>Total: ${salesReportPager.loopVolume + salesReportPager.trackpackVolume}</li>
		</ul>
	</fieldset>
	<fieldset width="250px" style="float: left; padding: 5px; margin:5px;">
		<legend>Net Income</legend>
		<ul width="250px" style="list-style: none inside; padding-left:0;margin-left:0;" >
			<li>Loop net: <fmt:formatNumber value="${salesReportPager.totalLoopYeild}" type="currency"/></li>
			<li>Trackpack net: <fmt:formatNumber value="${salesReportPager.totalTrackpackYeild}" type="currency"/></li>
			<li><br/>Total Earned: <fmt:formatNumber value="${salesReportPager.totalTrackpackYeild + salesReportPager.totalLoopYeild}" type="currency"/></li>
		</ul>
	</fieldset>
</div>

<div id="itemlist">
<%--decorator="org.displaytag.decorator.TotalTableDecorator" --%>
	<display:table name="${salesReportPager.list}" id="loop" htmlId="soldLoops" class="table cliptable" cellspacing="0" cellpadding="0" requestURI="" style="width:70%" varTotals="totalv" 
	
	 sort="list" defaultsort="1" pagesize="${salesReportPager.objectsPerPage}" length="${salesReportPager.fullListSize}" partialList="false" size="salesReportPager.fullListSize"
	 
	>
<display:column title="piid" property="id" media="html"/>
<display:column property="clipId" media="html"/>
<%--
	<display:column title="Deducted" property="purchase.net" group="1"/>
	<display:column title="TrxnFee" property="purchase.trxnFee" group="1"/>group="2"
group="3" --%>
	<display:column title="Name" property="clipName" sortable="true" />
	<display:column title="Price" sortable="true" property="price" format="{0,number,$0.00}" />

	<display:column title="Promo Discounts"><c:if test="${not empty loop.purchase.promoDiscount}">
	<fmt:formatNumber type="percent" value="${loop.purchase.promoDiscount}"/> : ${loop.purchase.promoCode}</c:if></display:column>
	<display:column title="TrxnFee" property="trxnFee" format="{0,number, $0.00}"/>
	<display:column title="Bulk Discount" property="purchase.discount" format="{0,number,percent}"/>
	
	<display:column title="Net" sortable="true" property="net" format="{0,number,$0.00}" total="true" 
         class="lineItemTotal" headerClass="lineItemTotal" />
	</display:table>
</div>
<div>

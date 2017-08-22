<%@ include file="/common/taglibs.jsp"%>
<fmt:setLocale value="en_US" scope="session"/>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.producer.reports"><fmt:param value="${storeForm.name}"/></fmt:message></title>
    <content tag="heading"><fmt:message key="menu.producer.reports"><fmt:param value="${storeForm.name}"/></fmt:message></content>
	<meta name="menu" content="ProducerHomeIn"/>


	<link type="text/css" href="<c:url value='/scripts/ui/themes/base/ui.all.css'/>" rel="stylesheet" />
	
	<script type="text/javascript"
		 src="<c:url value='/scripts/producer_reports.js'/>"></script>
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.core.js'/>"></script>
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.datepicker.js'/>"></script>
</head>
<%-- [RON added] force linebreak to deal with problem on viewreports --%>
<p style="display:block;clear:left;"><div id="shopSummary">
<h1>${storeForm.name}</h1>
<html:img page="${storeForm.bannerImg}" styleClass="bannerImg alignLeft"/>
<span style="color:#000;"># Loops: ${storeForm.numLoops}
# Track Packs: ${storeForm.numTrackpacks}</span>
</div>
<div id="salesReportPage">
<div class="alignLeft">
<html:form action="/producer/reports"  styleId="salesReportForm">
<ul>
<li>
<fieldset>
<beathive2:label key="storeForm.startDate"/>: <input type="text" id="startdate" name="startDate" size="10" value=""/>
<beathive2:label key="storeForm.endDate"/>: <input type="text" id="enddate" name="endDate" size="10" value=""/>
</fieldset>
</li>
<li>
<html:hidden styleId="storeId" property="storeId" value="${storeForm.id}"/>
</br>
<input type="submit" id="runreport" name="method.sales" value="view report"/>
</br>
<%-- view performance: <input type="checkbox" id="perf" name="groupByClip"/> --%>
</br>
</li>
</ul>
</html:form>
</div>
<%-- The Elem that holds store stats. these are based on given date range --%>
<div class="ltr" id="stat" ></div>
<br clear="all"/>
<%-- The Elem that holds the result table --%>
<div id="reportResult"></div>
</div></p>
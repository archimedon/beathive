<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
	<%@ include file="/common/meta.jsp" %>
	<title><fmt:message key="webapp.name"/> | <decorator:title/></title>
	<c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set><c:if var="isAdminMenu" test="${currentMenu == 'AdminMenu'}"/>
     <%-- Set the BaseHREF --%>
	<script type="text/javascript">
		CONTEXT_PATH = '<%=request.getContextPath()%>';
	</script>

	<%-- load reset style sheets --%>
	<link rel="stylesheet" type="text/css" media="all"	href="<c:url value='/styles/reset.css'/>" />

<!-- CSS tools: break lines, spacers, left-to-right -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/tools.css'/>" />
<!--* used for the HTML ,BODY - overall structure  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/layout.css'/>" />
<!-- NAV BAR  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/nav-horizontal.css'/>" /> 
<!-- fonts and type face  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/typo.css'/>" />
<!-- structure (& style) for all loop tables  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/displaytag.css'/>" />
<!-- form elements  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/forms.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/global.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/messages.css'/>" />

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/caption.css'/>" />


<link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />


		<%-- load javascript --%>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.js'/>"></script>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.blockUI.js'/>"></script>
		
		<%-- script type="text/javascript"
		src="<c:url value='/scripts/jquery.listen.js'/>"></script --%>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.form.js'/>"></script>
		
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.scrollTo-min.js'/>"></script>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.tooltip.pack.js'/>"></script>
		
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.dimensions.js'/>"></script>
		
		<script type="text/javascript"
		src="<c:url value='/scripts/ajaxfileupload.js'/>"></script>
		
		<%-- used for auto upload button --%>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.ajax_upload.2.8.js'/>"></script>
		
		<%-- custom funcs --%>
		<script type="text/javascript" src="<c:url value='/scripts/funcs.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
		
		<%-- script type="text/javascript" src="<c:url value='/scripts/swfobject/swfobject.js'/>"></script --%>
		<script type="text/javascript" src="<c:url value='/scripts/search_results.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/thickbox.js'/>"></script>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/styles/thickbox.css'/>" />

<%-- slowing server response --%>
<%--
 <c:if var="shownews" test="${! fn:startsWith(pageContext.request.requestURI , '/loops.html')}">
   <script type="text/javascript" src="<%=request.getScheme()%>://www.google.com/jsapi?key=ABQIAAAA02kzZoILfNMPSi4jvcqd1xRLabaPJtXI7LODWlbJKs51Z2H71RQyhMXBMH6i624MJ5pjMTh617anoQ">
</script>
</c:if>
--%>
<script type="text/javascript">
//<![CDATA[
<%----%>

<c:if  test="${shownews}">
google.load("feeds", "1") //Load Google Ajax Feed API (version 1)
</c:if>
$(document).ready(function(){
<c:if test="${! fn:startsWith(pageContext.request.requestURI , '/admin') and ! fn:startsWith(pageContext.request.requestURI , '/cart.html')}">
setMeter('${userCart.total}');
</c:if>
<%--	<c:if test="${shownews}">loadRSSFeed();</c:if>	--%>
});
//]]>
</script>

       <decorator:head/>

<%@include file="/common/doUserLoggedin.jsp"%>

</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<div id="page">		
	<div id="header" class="clearfix">
		<jsp:include page="/common/header.jsp"/>
	</div>
	<!-- top-nav -->
	
	<div id="nav"><jsp:include page="/common/menu.jsp"/></div>
	<!-- end top-nav -->
		
	<!-- CONTENT -->
	<div id="content" class="clearfix">

		<!-- MAIN -->
		<div id="main">
					<!-- ADMIN MENU (conditional) -->
<c:if test="${isAdmin and isAdminMenu}"><jsp:include page="/WEB-INF/pages/admin/menu.jsp"/></c:if>
			<decorator:body/>
		</div>
		<!--END MAIN -->
		
		<!-- right column -->
<c:if  test="${! (fn:contains(pageContext.request.requestURI , '/viewCart') or fn:contains(pageContext.request.requestURI , '/purchaseComplete')  or fn:contains(pageContext.request.requestURI , '/ppcomplete') )}">		<div id='right'><%@ include file="/common/right_column.jsp" %></div></c:if>

		<!-- END RIGHT COL -->
	</div><!-- END CONTENT -->
	
	<!-- FOOTER -->
	<div id="footer" class="clearfix">
		<jsp:include page="/common/footer.jsp"/>
	</div><!-- END FOOTER -->
</div><!-- END PAGE -->
</body>
</html>
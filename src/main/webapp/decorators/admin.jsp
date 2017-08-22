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
		<%-- load themes
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
 --%>

<!-- CSS tools: break lines, spacers, left-to-right -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/tools.css'/>">
<!--* used for the HTML ,BODY - overall structure  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/layout.css'/>">
<!-- NAV BAR  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/nav-horizontal.css'/>"> 
<!-- fonts and type face  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/simplicity/typo.css'/>">
<!-- structure (& style) for all loop tables  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/displaytag.css'/>">
<!-- form elements  -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/forms.css'/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/global.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/messages.css'/>" />


<link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />


		<%-- @ include file="/common/styleSwitch.jsp" --%>
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
		
		<script type="text/javascript" src="<c:url value='/scripts/swfobject/swfobject.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/scripts/search_results.js'/>"></script>

       <decorator:head/>

<%@ include file="/common/doUserLoggedin.jsp"%>
</head>
<body id="admin">

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
			<jsp:include page="/WEB-INF/pages/admin/menu.jsp"/>
			<decorator:body/>
		</div>
		<!--END MAIN -->
		
	</div><!-- END CONTENT -->
	
	<!-- FOOTER -->
	<div id="footer" class="clearfix">
		<jsp:include page="/common/footer.jsp"/>
	</div><!-- END FOOTER -->
</div><!-- END PAGE -->
</body>
</html>
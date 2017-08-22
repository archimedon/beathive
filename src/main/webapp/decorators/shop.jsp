<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
        <%@ include file="/common/meta.jsp" %>
		<title><fmt:message key="webapp.name"/> <decorator:title/></title>
		
		
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

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/caption.css'/>" />


<link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

		<%-- load javascript --%>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.js'/>"></script>
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.blockUI.js'/>"></script>
		
		<script type="text/javascript"
		src="<c:url value='/scripts/jquery.listen.js'/>"></script>
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

    <script type="text/javascript" src="<c:url value='/scripts/thickbox.js'/>"></script>

<link rel="stylesheet" type="text/css" media="screen" href="<c:url value='/styles/thickbox.css'/>" />

 <%-- script type="text/javascript" src="<%=request.getScheme()%>://www.google.com/jsapi?key=ABQIAAAA02kzZoILfNMPSi4jvcqd1xRLabaPJtXI7LODWlbJKs51Z2H71RQyhMXBMH6i624MJ5pjMTh617anoQ"></script --%>

<script type="text/javascript">
 <%-- google.load("feeds", "1") --%>
$(document).ready(function(){
	setMeter('${userCart.total}');
 <%-- 	loadRSSFeed(); --%>
});
</script>

       <decorator:head/>
<%@ include file="/common/doUserLoggedin.jsp"%>
</head>
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>


<div id="page">
	<div id="header" class="clearfix">
			<jsp:include page="/common/header.jsp"/>
	</div>
		<c:if test="${not empty requestScope.storeForm}">
<%-- c:set var="currentMenu" scope="request">SearchLoops</c:set --%>
<c:set var="currentMenu" scope="request">ShopDirectory</c:set>
</c:if>
		<div id="nav"><jsp:include page="/common/menu.jsp"/></div>
	<!-- CONTENT -->
	<div id="content" class="clearfix">
		<!-- top-nav -->

		<!-- end top-nav -->
		
		<!-- MAIN -->
		<div id="main">
			<decorator:body/>
		</div> <!--END MAIN -->
		
		<!-- right column -->
		<div id='right'>

			<!-- LOGIN -->
			<%@ include file="/common/loginBox.jsp" %>

			<!-- SHOP SUMMARY -->
			<%@ include file="/WEB-INF/pages/store/rt_col_summary.jsp" %>

			<!-- CART SUMMARY -->
			<div id="cartSummary" class="rightColItem">
				<c:if test="${!  (  fn:contains(pageContext.request.requestURI , '/cart')  or  fn:contains(pageContext.request.requestURI , 'purchaseComplete')   or fn:contains(pageContext.request.requestURI , '/ppcomplete')   )}"><%@ include file="/WEB-INF/pages/cart/cartSummary.jsp"%></c:if>
			</div>
			
			
			<!--  NEWS -->
<%--		NO NEWS IN SHOP
<c:if test="${shownews}"><div class="rightColItem" id="feeddiv"><h2>News</h2></div></c:if>
--%>			
			
		<logic:present role="admin">
			<!-- ADMIN MENU (conditional) -->
			<div class="rightColItem"><menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssVerticalMenu.vm" permissions="rolesAdapter"><menu:displayMenu name="AdminMenu"/></menu:useMenuDisplayer></div>
			<!-- END ADMIN MENU --></logic:present>
			


<%--
			<!-- ITEM 4 - ADMIN MENU (conditional) -->
<c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set><c:if test="${currentMenu == 'AdminMenu'}">
			<div class="rightColItem"><menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssVerticalMenu.vm" permissions="rolesAdapter"><menu:displayMenu name="AdminMenu"/></menu:useMenuDisplayer></div>
			<!-- END ADMIN MENU --></c:if>
--%>			
		</div><!-- END RIGHT COL -->
	</div><!-- END CONTENT -->
	
	<!-- FOOTER -->
	<div id="footer" class="clearfix">
		<jsp:include page="/common/footer.jsp"/>
	</div><!-- END FOOTER -->
</div><!-- END PAGE -->
</body>
</html>
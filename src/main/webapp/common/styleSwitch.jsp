		<%-- load style sheets --%>
		<c:if test="${param.genreId eq '9' or param.genreId eq '7'}">
		<c:set var="pgenreId" value="${param.genreId}"/>
		</c:if>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}${pgenreId}/theme.css'/>" />

		<link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}${pgenreId}/print.css'/>" />

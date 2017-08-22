<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <li class="pad">&nbsp;</li>
    <c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if>
    <logic:present role="producer">
		<menu:displayMenu name="ProducerMainMenu"/>
		<menu:displayMenu name="ProducerHome"/>
		<menu:displayMenu name="ViewReports"/>
		<c:if test="${not empty param.storeId}">
			<menu:displayMenu name="UploadClip"/>
			<menu:displayMenu name="EditShop"/>
	<%--
			<menu:displayMenu name="EditLoop"/>
			<menu:displayMenu name="EditTrackpack"/>
	--%>
		</c:if>
	</logic:present>
</ul>
</menu:useMenuDisplayer>
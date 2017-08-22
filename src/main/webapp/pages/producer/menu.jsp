<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<div id="menuBox">
<ul class="menuList">
<li><a href="/producer/home.html" title="<fmt:message key="menu.producer.home"/>"><fmt:message key="menu.producer.home"/></a></li>
<menu:displayMenu name="TrackpackInventory"/>
<menu:displayMenu name="LoopInventory"/>
<menu:displayMenu name="ShopReport"/>
<menu:displayMenu name="EditShop"/>
<menu:displayMenu name="UploadClip"/>
</ul>
</menu:useMenuDisplayer>
<logic:present role="producer">
<html:form action="/producer/home" styleId="storeForm" method="get">
<bean:size id="stcnt" name="storeMenu"/><c:if test="${stcnt > 1}">
<%-- input type='hidden' name='storeId' value='' id="sndstoreId"/ --%>
<fmt:message key="producer.storeMenu.label"/><html:select property="id" styleId="storeId" onchange="this.form.submit();">
<html:options collection="storeMenu" property="value" labelProperty="label"/>
</html:select></c:if>
</html:form>
</logic:present>
</div> <!--END menuBox -->

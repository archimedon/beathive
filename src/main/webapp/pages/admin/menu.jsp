<%@ include file="/common/taglibs.jsp"%><menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<div id="menuBox">
<ul class="menuList">
<menu:displayMenu name="AdminMenu"/>
<menu:displayMenu name="SalesReport"/>
<menu:displayMenu name="ViewUsers"/>
<menu:displayMenu name="ActiveUsers"/>
<menu:displayMenu name="ReloadContext"/>
<menu:displayMenu name="FlushCache"/>
<!-- menu:displayMenu name="Clickstream"/ -->
<menu:displayMenu name="PromoMenu"/>
<!-- menu:displayMenu name="AdminItems"/ -->
</ul>
</menu:useMenuDisplayer>
</div> <!--END menuBox -->

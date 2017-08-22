<%@ include file="/common/taglibs.jsp" %>
<div id="menuBox" class="short">
<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul class="menuList">
    <menu:displayMenu name="UserMenu"/>
   <menu:displayMenu name="EditPreference"/>
   <menu:displayMenu name="ZipMenu"/>
</ul>
</menu:useMenuDisplayer></div>
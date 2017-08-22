<%@ include file="/common/taglibs.jsp"%>
<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <li class="pad">&nbsp;</li>
     <menu:displayMenu name="UploadClip"/>
     <menu:displayMenu name="EditLoop"/>
     <menu:displayMenu name="EditTrackpack"/>
</ul>
</menu:useMenuDisplayer>
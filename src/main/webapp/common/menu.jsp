<%@ include file="/common/taglibs.jsp"%>
<menu:useMenuDisplayer name="Velocity" config="WEB-INF/classes/cssHorizontalMenu.vm" permissions="rolesAdapter">
<ul id="primary-nav" class="menuList">
    <%-- menu:displayMenu name="MainMenu"/ --%>
   <menu:displayMenu name="SearchLoops"/>
   <menu:displayMenu name="ShopDirectory"/>
   <menu:displayMenu name="ViewCart"/>
   <menu:displayMenu name="FAQ"/>
  <logic:present role="admin,user,producer"><menu:displayMenu name="ViewWishList"/></logic:present>
   <c:choose><c:when test="${not empty storeForm and not empty storeForm.id}"><menu:displayMenu name="ProducerHomeIn"/></c:when>
   <c:otherwise><menu:displayMenu name="ProducerHome"/></c:otherwise></c:choose>
    <menu:displayMenu name="AdminMenu"/>
   <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>

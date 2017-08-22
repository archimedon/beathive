<%@ include file="/common/taglibs.jsp"%>

<c:set var="inc_url1" value="/WEB-INF/pages/store/listItem.jsp"/>
<c:if test="${not empty param.jay}">
	<c:set var="inc_url1" value="/WEB-INF/pages/store/listItem.htm"/>
</c:if>

<head>
    <title><fmt:message key="menu.shop.listing"/></title>
    <content tag="heading"><fmt:message key="menu.shop.listing"/></content>
    <meta name="menu" content="ShopDirectory"/>

<script type="text/javascript">
$(document).ready(function(){
	var itemsFound = $('div#storeTable span.itemsFound');
	$($(itemsFound)[1]).remove();
});
</script>

</head>
<body id="shopdirectory">
<beathive2:menu property="clipGenres" listName="genre" toScope="page"	/>			
<ul id="storeGenres" class="menuList">			
<c:forEach items="${genre}" var="gi">
<c:if test="${param.genreId eq gi.value}"><c:set var="gname"><fmt:message key="${gi.label}"/></c:set></c:if>
<li><html:link page="/stores.html?genreId=${gi.value}&method=search"><fmt:message key="${gi.label}"/></html:link></li>
</c:forEach>
</ul>
<div id="storeTable">
<display:table name="${loopshops}" id="store" htmlId="storeListing" class="storeList" export="false" requestURI="" >
 <display:column>
 <c:set var="store" scope="request" value="${store}"/>
 <jsp:include page="${inc_url1}"/>

</display:column>
    <display:setProperty name="basic.show.header" value="false"/>
    <display:setProperty name="paging.banner.placement" value="both"/>
    <display:setProperty name="paging.banner.item_name">${gname} loop shop</display:setProperty>
    <display:setProperty name="paging.banner.items_name">${gname} loop shops</display:setProperty>
	<display:setProperty name="paging.banner.no_items_found"><span class="itemsFound">No {0} Found</span></display:setProperty>
    <display:setProperty name="paging.banner.all_items_found"><span class="itemsFound"><span class="count">{0}</span> {1}</span></display:setProperty>
    <display:setProperty name="paging.banner.one_item_found"><span class="itemsFound">1 <span class="count">{0}</span></span></display:setProperty>
    <display:setProperty name="paging.banner.some_items_found"><span class="itemsFound"><span class="count">{0}</span> {1}</span></display:setProperty>

</display:table>
</div>
</body>

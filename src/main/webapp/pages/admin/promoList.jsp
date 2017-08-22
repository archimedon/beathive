<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="promoList.title"/></title>
<content tag="heading"><fmt:message key="promoList.heading"/></content>
<meta name="menu" content="AdminMenu"/>

<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/admin/editPromo.html"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<html:rewrite forward="mainMenu"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>
<display:table name="promoList" cellspacing="0" cellpadding="0"
    id="promoList" pagesize="25" class="table promoList"
    export="true" requestURI="">

    <display:column property="code" sortable="true" headerClass="sortable"
        url="/admin/editPromo.html" paramId="id" paramProperty="id"
		 titleKey="promoForm.code"/>
    <display:column property="start" sortable="true" headerClass="sortable"
         titleKey="promoForm.start"/>
    <display:column property="duration" sortable="true" headerClass="sortable"
         titleKey="promoForm.duration"/>
    <display:column property="discount" sortable="true" headerClass="sortable"
         titleKey="promoForm.discount"/>
    <display:setProperty name="paging.banner.item_name" value="promo"/>
    <display:setProperty name="paging.banner.items_name" value="promos"/>
</display:table>

<c:out value="${buttons}" escapeXml="false"/>

<script type="text/javascript">
    highlightTableRows("promoList");
</script>

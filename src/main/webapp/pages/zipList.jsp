<%@ include file="/common/taglibs.jsp"%>
<c:if test="${not empty frompp}" var="ppwait"><c:remove var="frompp" scope="session"/><c:remove var="cartItemList" scope="session"/></c:if>
<head>
<title><fmt:message key="zipList.title"/></title>
<content tag="heading"><fmt:message key="zipList.heading"/></content>
<meta name="menu" content="ZipMenu"/>
</head><%@ include file="/WEB-INF/pages/user/tabs.jsp"%>
<c:if test="${ppwait}">If you do not see your order reload this page in a minute</c:if>
<c:choose>
<c:when test="${not empty zipList}">
<display:table name="zipList" htmlId="zipList" id="zipItem" pagesize="25" class="table zipList" requestURI="">
<display:column titleKey="zipForm.name" sortProperty="name"><a href="<c:url value="${zipItem.downloadURL}"/>">${zipItem.name}</a></display:column>
    <display:column property="accessCount" sortable="true" headerClass="sortable"
         titleKey="zipForm.accessCount"/>
    <display:column property="fileSizeH" sortable="true" headerClass="sortable"
         titleKey="zipForm.fileSize"/>
    <display:setProperty name="paging.banner.item_name" value="zip"/>
    <display:setProperty name="paging.banner.items_name" value="zips"/>
</display:table>
</c:when>
<c:otherwise><div class="emptytable">
<fmt:message key="user.ziplist.nodowloads"/></div>
</c:otherwise>
</c:choose>
<script type= "text/javascript">
//<![CDATA[
highlightTableRows("zipList");
//]]>
</script>

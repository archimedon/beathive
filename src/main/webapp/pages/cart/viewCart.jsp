<%@ include file="/common/var_contentType.jsp"%>
<c:choose>
<c:when test="${param.rtype eq 'data'}"><c:set var="wishsize" value="0"/><c:if test="${not empty userFavorites}"><bean:size id="wishsize" name="userFavorites" property="list"/></c:if><c:set var="cartsize" value="0"/><c:if test="${not empty userCart}"><bean:size id="cartsize" name="userCart" property="list"/></c:if>{'wishsize':'${wishsize}' , 'cartsize':'${cartsize}'}
</c:when>
<c:when test="${param.rtype eq 'summary'}"><%@ include file="/WEB-INF/pages/cart/cartSummary.jsp"%>
</c:when>
<c:otherwise><head><title><fmt:message key="menu.viewcart"/></title>
<meta name="menu" content="ViewCart"/>
<content tag="heading">ViewCart</content>
</head><body id="viewCartPage">
<%@ include file="/WEB-INF/pages/cart/cartList.jsp"%>
</body>
</c:otherwise>
</c:choose>

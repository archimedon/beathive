<%@ include file="/common/var_contentType.jsp"%>
<c:choose>
<c:when test="${param.rtype eq 'data'}">
<c:choose>
  <c:when test="${not empty userFavorites.list}">
	<bean:size id="wishsize" name="userFavorites" property="list"/>
  </c:when>
  <c:otherwise><c:set var="wishsize" value="0"/></c:otherwise>
</c:choose>
<c:choose>
<c:when test="${not empty userCartPager.list}"><bean:size id="cartsize" name="userCartPager" property="list"/></c:when><c:otherwise><c:set var="cartsize" value="0"/></c:otherwise></c:choose>{'wishsize':${wishsize} , 'cartsize':${cartsize}}</c:when>
<c:when test="${param.rtype eq 'summary'}"><%@ include file="/WEB-INF/pages/cart/cartSummary.jsp"%></c:when>
<c:otherwise>
<head><title><fmt:message key="menu.viewcart"/></title>
<meta name="menu" content="ViewWishList"/>
<content tag="heading">ViewWishList</content>
		<script type="text/javascript" src="/scripts/swfobject/swfobject.js"></script>

</head>
<body id="wishListPage">
<br/>
<h1><fmt:message key="menu.viewwishlist"/></h1>
<br/><div id="resultBody">
<display:table name="${userFavorites.list}" cellspacing="0" cellpadding="0" keepStatus="false" id="favorite" class="display userFavorites loopList" requestURI="" pagesize="10" partialList="false">
	<%-- COL: 0 - OPEN Trackpack button .--%>
	<display:column class="expandCell" headerClass="expandHeaderClass"  title="">
		<c:set var="ltype" value="loop"/>
		<c:if test="${fn:contains(favorite.class.name , 'Trackpack')}">
		<c:set var="ltype" value="trackpack"/>
		<c:set var="compUrl">/viewComponents.html?clipId=${favorite.id}&wishlist=1</c:set>
		<a class="expand" title="[ Component Loops ]" name="a_${favorite.id}" href="<c:url value='${compUrl}'/>" ref="${favorite.id}"><div class="expandButton">&nbsp;</div></a>
		</c:if>
	</display:column>
	
	<%-- COL: 1 - playbuttons.--%>
	<display:column class="playbuttonCell" headerClass="playbuttonHeaderClass">
		<table class="swfarea" ref="emb_${favorite.id}" id="${favorite.id}" style="display:inline;"><tr>
		<c:set value="${favorite.audioFile}" var="loopsam"/>	
			<td>
				<div class="player"  id="swf_${favorite.id}_${loopsam.formatId}">
					<c:import url="/component/playbutton.jsp">
						<c:param name="path" value="${loopsam.samplePath}"/>
						<c:param name="oid" value="${favorite.id}_${loopsam.formatId}"/>
						<c:param name="num" value="${favorite_rowNum}"/>
						<c:param name="js" value="1"/>
					</c:import>
				</div>
				${loopsam.formatId}
			</td>
		</tr></table>
		<%-- hidden loop descriptors--%>
		<div class="attr" style="display:none"><c:out value="${favorite.descriptors}"/></div>
		<c:if test="${fn:contains(favorite.class.name , 'Trackpack')}">
			<%-- pack_loops hidden --%>
			<div style="display:none" id="pack_loops_${favorite.id}">
				<%-- @ include file="/WEB-INF/pages/producer/innerLoopList.jsp" --%>
			</div>
		</c:if>	
	</display:column>


	
	<display:column  class="wishCell" headerClass="wishHeaderClass">
	<html-el:link page="/user/wishList.html?fileId=${favorite.fileId}&amp;clipId=${favorite.id}&amp;userId=${uid}&amp;type=${ltype}&amp;method=sub&amp;to=cart" styleClass="fromWishlist clear"><div class="toCartButton">&nbsp;</div></html-el:link></display:column>

	<display:column property="name" titleKey="soundClipForm.name" headerClass="nameHeaderClass" class="nameCell"/>
	
	<display:column titleKey="soundClipForm.instrument" headerClass="instrumentHeaderClass">
		<c:choose>
			<c:when test="${fn:contains(favorite.class.name , 'Trackpack')}"><fmt:message key="soundClipForm.numcomponents.label"><fmt:param value="${favorite.loopsExpected}"/></fmt:message></c:when>
			<c:otherwise><fmt:message key="${favorite.instrument.labelKey}"/></c:otherwise>
		</c:choose>
	</display:column>
	
	<display:column
		titleKey="soundClipForm.genre"
		class="genreCell"
		headerClass="genreHeaderClass"><fmt:message key="${favorite.genre[0].labelKey}"/></display:column>
	
	<display:column titleKey="soundClipForm.bpm" class="bpmCell" property="bpm" sortable="false" sortName="bpm" headerClass="bpmHeaderClass" />
	
	<display:column 
		titleKey="soundClipForm.storeName" 
		class="storeNameCell"
		headerClass="storeNameHeaderClass"
		url="/shop/loops.html"
		paramId="storeId"
		paramProperty="storeId"
		property="storeName"
	/>
	
	<display:column property="price" titleKey="soundClipForm.price" class="priceCell"/>
	
	
	<display:column headerClass="removeFavHeaderClass"><span class="cat_pagination"><html-el:link page="/user/wishList.html?fileId=${favorite.fileId}&amp;clipId=${favorite.id}&amp;userId=${uid}&amp;type=${ltype}&amp;method=sub" styleClass="fromWishlist clear">remove</html-el:link></span></display:column>

    <display:setProperty name="basic.show.header" value="true"/>
    <display:setProperty name="sort.amount" value="list"/>
    <display:setProperty name="paging.banner.item_name" value="item"/>
    <display:setProperty name="paging.banner.items_name" value="items"/>
</display:table>
</div>
</body>
</c:otherwise>
</c:choose>


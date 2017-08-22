<display:table list="${oneitem}" htmlId="tpview" id="tpclip" class="trackpackList" requestURI="">


	<display:column  class="playbuttonCell infoMark" headerClass="playbuttonHeaderClass">
		<%-- initialize variables --%><c:set var="formats"></c:set><c:set var="fileId"></c:set><c:set var="tmp" value="${tpclip.id}"/>
			<c:set var="choiceId" value="${cartmap[tmp]}" scope="request"/>
			<c:forEach items="${tpclip.audioFormat}" var="loopsam" varStatus="stat" begin="0" end="0"><c:set var="picked" value="${loopsam}"/>	</c:forEach>
	<c:choose><c:when test="${choiceId!=null}">
		<c:forEach items="${tpclip.audioFormat}" var="loopsam" varStatus="stat">
			<c:if test="${loopsam.id eq choiceId}"><c:set var="picked" value="${loopsam}"/></c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<%-- step through the formats --%>
		<c:forEach items="${tpclip.audioFormat}" var="loopsam" varStatus="stat">	
			<c:if test="${loopsam.formatId eq param.preffrm}">
				<c:set var="picked" value="${loopsam}"/>
			</c:if>
		</c:forEach>
	</c:otherwise></c:choose>
	<c:set var="fileId" value="${picked.id}"/>
		<div class="player"  id="swf_${tpclip.id}_${picked.formatId}"><c:import url="/component/playbutton.jsp">
			<c:param name="path" value="${picked.samplePath}"/>
			<c:param name="oid" value="${tpclip.id}_${picked.formatId}"/>
			<c:param name="num" value="${tpclip_rowNum}"/>
			<c:param name="js" value="1"/>
		</c:import>
		</div>


		<%-- hidden loop descriptors--%>
		<div class="attr" style="display:none"><c:set var="descriptors"  value="${tpclip.descriptors}" scope="request"/><c:import url="/component/search/clipMeta.jsp"/></div>

</display:column>
<%-- CART Column --%>
<display:column  class="cartCell infoMark" headerClass="cartHeaderClass" title="&nbsp;">
	<c:set var="meth" value="add"/>
	<c:set var="fid" >"${tpclip.id}_"</c:set>
	<c:set var="cartStyle" value="toCart"/>
	<c:if test="${fn:contains(itemlist , fid)}">
		<c:set var="meth" value="sub"/>
		<c:set var="cartStyle" value="fromCart"/>
	</c:if>
	<%-- CART Button --%>
	<c:if test="${tpclip.ownedByViewer}">
		<span class="owned_bullet" title="results.pack.owned">&bull;</span>
	</c:if>
	<html:link page='/loopCart.html?method=${meth}&fileId=${fileId}&clipId=${tpclip.id}&type=trackpack' styleClass="${cartStyle}"><div class="${cartStyle}Button">&nbsp;</div></html:link>
</display:column>

<%-- WishList Column --%>
<display:column  class="wishCell infoMark" headerClass="wishHeaderClass" title="&nbsp;">
	<c:set var="favStyle" value="toWishlist"/>
	<c:set var="meth" value="add"/>
	<c:if test="${tpclip.AFavorite}">
		<c:set var="meth" value="sub"/>
		<c:set var="favStyle" value="fromWishlist"/>
	</c:if>
	<%-- WishList BUTTON --%>
	<html:link page='/user/wishList.html?method=${meth}&fileId=${fileId}&clipId=${tpclip.id}' styleClass="${favStyle}"><div class="${favStyle}Button">&nbsp;</div></html:link>
</display:column>
<%--
    <display:column property="name" sortable="true" class="nameCell"/>
    <c:if test="${not inShop}">
    <display:column class="shopCell"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${tpclip.storeId}'/>"title="">${tpclip.storeName}</a></display:column>
    </c:if>
   --%>
    <display:column class="infoMark" headerClass="nameHeaderClass" property="name" title="&nbsp;"/><%-- ${tpclip.name}<br/>
    <c:if test="${not inShop}"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${tpclip.storeId}'/>"title=""><small>${tpclip.storeName}</small></a></c:if></display:column> --%>
    
    
<display:column class="formatsCell" headerClass="formatsHeaderClass" title="&nbsp;">
	<%-- div class="formatSwitch">${formats}</div --%>
	<c:set var="choice" value="${param.preffrm}"/>
	<c:set var="formats" value="${tpclip.audioFormat}"/>
<%@ include file="/WEB-INF/pages/listcomponents/fileformats.jsp"%>
</display:column>

    <%-- display:column sortable="false" class="genreCell"><fmt:message key="${tpclip.genre[0].labelKey}"/></display:column --%>

<display:column class="numLoopsCell" headerClass="numLoopsHeaderClass" title="&nbsp;"><fmt:message key="soundClipForm.numcomponents.label"><fmt:param value="${numl}"/></fmt:message></display:column>
    

    <%-- display:column property="score" sortable="true" sortName="score"/ --%>
   <display:column property="price.amount" title="&nbsp;" format="{0,number,0.00}" class="priceCell" headerClass="priceHeaderClass"/>

	<display:column property="bpm" title="&nbsp;"headerClass="bpmHeaderClass" class="bpmCell" />

<display:column headerClass="rateHeaderClass" class="rateCell" title="&nbsp;">
<c:choose>
	<c:when test="${tpclip.viewerScore eq '1'}">
		<div class='rateItNot'><div class="thumbUp">&nbsp;</div></div>
	</c:when>
	
	<c:when test="${tpclip.viewerScore eq '-1'}">
		<div class='rateItNot'><div class="thumbDown">&nbsp;</div>	</div>
	</c:when>
	<c:otherwise>
	<security:authorize ifAnyGranted="user,producer,admin"><c:set var="theirin" value="${true}"/></security:authorize>
		<c:choose><c:when test="${theirin}">		<div class='rateIt'>
			<html:link page="/user/rateClip?userId=${USER_ID}&clipId=${tpclip.id}&score=1&amp;shopId=${tpclip.storeId}" styleClass="rateClip"><div class="thumbUp">&nbsp;</div></html:link>
			
			<html:link page="/user/rateClip?userId=${USER_ID}&clipId=${tpclip.id}&score=-1&amp;shopId=${tpclip.storeId}" styleClass="rateClip"><div class="thumbDown">&nbsp;</div></html:link>	
		</div>
</c:when><c:otherwise>		<div class='rateItNot'>
			<div class="thumbUp">&nbsp;</div>
			
			<div class="thumbDown">&nbsp;</div>
		</div>
</c:otherwise></c:choose>
	</c:otherwise>
</c:choose>
</display:column>
    <display:setProperty name="sort.amount" value="list"/>
    <display:setProperty name="paging.banner.item_name" value="loop"/>
    <display:setProperty name="paging.banner.items_name" value="loops"/>
    
<display:setProperty name="basic.show.header" value="true"/>

    <display:setProperty name="paging.banner.all_items_found"> </display:setProperty>

    <display:setProperty name="paging.banner.one_item_found"> </display:setProperty>
    
    <display:setProperty name="paging.banner.some_items_found"> </display:setProperty>
</display:table>

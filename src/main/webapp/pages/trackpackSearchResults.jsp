<%@ include file="/common/var_contentType.jsp"%><c:if test="${empty param.x}">
<head><title><c:choose><c:when test="${not empty requestScope.storeForm}"><fmt:message key="visit.shop.title"><fmt:param value="${requestScope.storeForm.name}"/></fmt:message></c:when><c:otherwise><fmt:message key="trackpackList.title"/></c:otherwise></c:choose></title>
<meta name="menu" content="SearchLoops"/>
<content tag="heading">Trackpacks</content>
<%--
<style type="text/css">.wavStyle{color: #F00;}
.aifStyle{color: #00F;}</style>

<script type="text/javascript">$(document).ready(function(){
var sels = $('.formatSwitch');
	sels.each(function(){
		var t = this;
		$(this).find('[selected]').each(function(){
			$(t).addClass($(this).attr('class'));		
		});
	});
});</script>
--%>
</head>
</c:if><body id="searchresults"><c:if test="${not empty param.x}"><div></c:if>
<c:set var="itemlist"><c:forEach items="${userCart.list}" var="item" varStatus="stat">"${item.id}_"<c:if test="${stat.last}">,</c:if></c:forEach></c:set>
<c:set var="cartmap" value="${userCart.map}" scope="request"/>
<%@ include file="/common/resultTabs.jsp"%>
<div id="resultBody"><display:table name="loopList" htmlId="searchResultsLoop" id="tpclip" class="display trackpackList" requestURI=""><display:caption id="metaData">
	<%@ include file="/WEB-INF/pages/searchCriteriaSummary.jsp"%>
</display:caption>
	<display:column class="expandCell" headerClass="expandHeaderClass" >
	  <c:set var="compUrl">/viewComponents.html?clipId=${tpclip.id}<c:if test="${inShop}">&storeId=${tpclip.storeId}</c:if></c:set>
	<a class="expand" title="[ Component Loops ]" name="a_${tpclip.id}" href="<c:url value='${compUrl}'/>" ref="${tpclip.id}"><bean:size id="numl" property="loops" name="tpclip"/><div class="expandButton">&nbsp;</div></a></display:column>
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
<display:column  class="cartCell infoMark" headerClass="cartHeaderClass">
	<c:set var="meth" value="add"/>
	<c:set var="fid">${tpclip.id}</c:set>
	<c:set var="cartStyle" value="toCart"/>
	<c:if test="${choiceId!=null}">
		<c:set var="meth" value="sub"/>
		<c:set var="cartStyle" value="fromCart"/>
	</c:if>
	<%-- CART Button --%>
	<c:if test="${tpclip.ownedByViewer}">
		<span class="owned_bullet" title="<fmt:message key="results.pack.owned"/>">&bull;</span>
	<%-- html:img src="/images/owned.gif" align="right" titleKey="results.loop.owned" altKey="results.loop.owned"/ --%></c:if>
	<html:link page='/loopCart.html?method=${meth}&fileId=${fileId}&clipId=${tpclip.id}&type=trackpack' styleClass="${cartStyle}"><div class="${cartStyle}Button">&nbsp;</div></html:link>
</display:column>

<%-- WishList Column --%>
<display:column  class="wishCell infoMark " headerClass="wishHeaderClass">
	<c:set var="favStyle" value="toWishlist"/>
	<c:set var="meth" value="add"/>
	<c:if test="${tpclip.AFavorite}">
		<c:set var="meth" value="sub"/>
		<c:set var="favStyle" value="fromWishlist"/>
	</c:if>
	<%-- WishList BUTTON --%>
	<html:link page='/user/wishList.html?method=${meth}&fileId=${fileId}&clipId=${tpclip.id}' styleClass="${favStyle} ${userStat}"><div class="${favStyle}Button">&nbsp;</div></html:link>
</display:column>
<%--
    <display:column property="name" sortable="true" titleKey="soundClipForm.name" class="nameCell" headerClass="nameHeaderClass"/>
    <c:if test="${not inShop}">
    <display:column class="shopCell" headerClass="shopHeaderClass"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${tpclip.storeId}'/>" title="view shop">${tpclip.storeName}</a></display:column>
    </c:if>
   --%>
    <display:column sortProperty="name" sortable="true" titleKey="soundClipForm.name"  class="nameCell infoMark" headerClass="nameHeaderClass">${tpclip.name}<br />
    <c:if test="${not inShop}"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${tpclip.storeId}'/>" title="view shop"><small>${tpclip.storeName}</small></a></c:if></display:column>
    
    
<display:column class="formatsCell" headerClass="formatsHeaderClass">
	<%-- div class="formatSwitch">${formats}</div --%>
	<c:set var="choice" value="${param.preffrm}"/>
	<c:set var="formats" value="${tpclip.audioFormat}"/>
<%@ include file="/WEB-INF/pages/listcomponents/fileformats.jsp"%>
</display:column>

    <%-- display:column sortable="false" titleKey="soundClipForm.genre" class="genreCell" headerClass="genreHeaderClass"><fmt:message key="${tpclip.genre[0].labelKey}"/></display:column --%>

<display:column class="numLoopsCell" headerClass="numLoopsHeaderClass" titleKey="soundClipForm.numloops.col.label"><fmt:message key="soundClipForm.numcomponents.label"><fmt:param value="${numl}"/></fmt:message></display:column>
    

    <%-- display:column property="score" sortable="true" titleKey="soundClipForm.score" sortName="score"/ --%>

   <%--    <display:column property="price.amount" sortable="true" titleKey="soundClipForm.price" sortName="price.amount" format="{0,number,0.00}" class="priceCell" headerClass="priceHeaderClass"/>
 --%>
<display:column sortProperty="price.amount" sortable="true" titleKey="soundClipForm.price" sortName="price.amount" class="priceCell" headerClass="priceHeaderClass">
<fmt:formatNumber minFractionDigits="2" type="currency" maxFractionDigits="2" value="${tpclip.priceForm.amount}" currencySymbol="$"/>
</display:column>


	<display:column property="bpm" sortable="true" sortName="bpm" titleKey="soundClipForm.bpm" class="bpmCell" headerClass="bpmHeaderClass" />

<display:column titleKey="soundClipForm.viewerScore" class="rateCell ${userStat}" headerClass="rateHeaderClass">
<c:choose>
	<c:when test="${tpclip.viewerScore eq '1'}">
		<div class='rateItNot'><div class="thumbUp">&nbsp;</div></div>
	</c:when>
	
	<c:when test="${tpclip.viewerScore eq '-1'}">
		<div class='rateItNot'><div class="thumbDown">&nbsp;</div>	</div>
	</c:when>
	<c:otherwise>
		<c:choose><c:when test="${isMember}">		<div class='rateIt'>
			<html:link page="/user/rateClip?userId=${uid}&clipId=${tpclip.id}&score=1&amp;shopId=${tpclip.storeId}" styleClass="rateClip"><div class="thumbUp">&nbsp;</div></html:link>
			
			<html:link page="/user/rateClip?userId=${uid}&clipId=${tpclip.id}&score=-1&amp;shopId=${tpclip.storeId}" styleClass="rateClip"><div class="thumbDown">&nbsp;</div></html:link>	
		</div>
</c:when><c:otherwise>		<div class='rateItNot'>
			<div class="thumbUp">&nbsp;</div>
			
			<div class="thumbDown">&nbsp;</div>
		</div>
</c:otherwise></c:choose>
	</c:otherwise>
</c:choose>
</display:column>

<%-- 
<display:column class="owner${tpclip.ownedByViewer}" headerClass="noColumn"></display:column>
<display:column style="display:none;" headerClass="noColumn">
Hold OFF on Emailing 
<input type="checkbox"/> -&gt;EchoBox
</display:column>
--%>
<%-- @ include file="/common/resultTabs.jsp" --%>
<display:setProperty name="paging.banner.no_items_found" value=""/>

    <display:setProperty name="paging.banner.all_items_found" value=""/>

    <display:setProperty name="paging.banner.one_item_found" value=""/>
    
    <display:setProperty name="paging.banner.some_items_found" value=""/>

        <display:setProperty name="paging.banner.placement" value="bottom"/>



</display:table>
</div>
<c:if test="${not empty param.x}"></div></c:if></body>
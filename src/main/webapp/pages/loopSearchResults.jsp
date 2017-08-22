<%@ include file="/common/var_contentType.jsp"%><%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:if test="${empty param.x}">
<head>
<title><c:choose><c:when test="${not empty requestScope.storeForm}"><fmt:message key="visit.shop.title"><fmt:param value="${requestScope.storeForm.name}"/></fmt:message></c:when><c:otherwise><fmt:message key="loopList.title"/></c:otherwise></c:choose></title>
 <meta name="menu" content="SearchLoops"/>
<content tag="heading">Loops</content>
 </head></c:if><body id="searchresults"><c:if test="${not empty param.x}"><div></c:if>
<script type="text/javascript">rownums = {};</script>
 <c:set var="itemlist"><c:forEach items="${userCart.list}" var="item" varStatus="stat">"${item.id}_"<c:if test="${stat.last}">,</c:if></c:forEach></c:set>
<c:set var="cartmap" value="${userCart.map}" scope="request"/>
<%@ include file="/common/resultTabs.jsp"%>
<div id="resultBody">
<display:table name="loopList" htmlId="searchResultsLoop" id="loop" class="display loopList" requestURI="" defaultsort='11'>

<display:caption id="metaData">
	<%@ include file="/WEB-INF/pages/searchCriteriaSummary.jsp"%>
</display:caption>




<display:column  class="playbuttonCell infoMark" headerClass="playbuttonHeaderClass">
	<%-- initialize variables --%><c:set var="formats"></c:set><c:set var="fileId"></c:set><c:set var="tmp" value="${loop.id}"/>
			<c:set var="choiceId" value="${cartmap[tmp]}" scope="request"/>
			<c:forEach items="${loop.audioFormat}" var="loopsam" varStatus="stat" begin="0" end="0"><c:set var="picked" value="${loopsam}"/>	</c:forEach>
	<c:choose><c:when test="${choiceId!=null}">
		<c:forEach items="${loop.audioFormat}" var="loopsam" varStatus="stat">
			<c:if test="${loopsam.id eq choiceId}"><c:set var="picked" value="${loopsam}"/></c:if>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<%-- step through the formats --%>
		<c:forEach items="${loop.audioFormat}" var="loopsam" varStatus="stat">	
			<c:if test="${loopsam.formatId eq param.preffrm}">
				<c:set var="picked" value="${loopsam}"/>
			</c:if>
		</c:forEach>
	</c:otherwise></c:choose>
	<c:set var="fileId" value="${picked.id}"/>
		<div class="player"  id="swf_${loop.id}_${picked.formatId}"><c:import url="/component/playbutton.jsp">
			<c:param name="path" value="${picked.samplePath}"/>
			<c:param name="oid" value="${loop.id}_${picked.formatId}"/>
			<c:param name="num" value="${loop_rowNum}"/>
			<c:param name="js" value="1"/>
		</c:import>
		</div>
	<%-- hidden loop descriptors--%>
	<div class="attr" style="display:none"><c:set var="descriptors"  value="${loop.descriptors}" scope="request"/><c:import url="/component/search/clipMeta.jsp"/></div>
</display:column>

<%-- CART Column --%>
<display:column  class="cartCell infoMark" headerClass="cartHeaderClass">
	<c:set var="meth" value="add"/><c:set var="cartStyle" value="toCart"/>
	<c:if var="isComponent" test='${loop.trackpackId != null}'>
		<c:set var="ctpid" value="${loop.trackpackId}"/>
		<c:set var="chfid" value="${cartmap[ctpid]}" scope="request"/>
		<c:if var="parincart" test="${chfid!=null}">
			<span class="incart_bullet" title="<fmt:message key="results.parent.incart"/>">&bull;</span>
		</c:if>
	</c:if>
	<c:if test="${parincart or choiceId!=null}">
		<c:set var="meth" value="sub"/>
		<c:set var="cartStyle" value="fromCart"/>
	</c:if>
	<%-- CART Button --%>
	<c:if test="${loop.ownedByViewer}">
	<span class="owned_bullet" title="<fmt:message key="results.loop.owned"/>">&bull;</span>
	<%-- html:img src="/images/owned.gif" align="right" titleKey="results.loop.owned" altKey="results.loop.owned"/ --%></c:if>
	<c:choose>
	<c:when test="${parincart}"><div class="${cartStyle}Button">&nbsp;</div>
	</c:when>
	<c:otherwise>
	<html:link page='/loopCart.html?method=${meth}&fileId=${fileId}&clipId=${loop.id}&type=loop' styleClass="${cartStyle}"><div class="${cartStyle}Button">&nbsp;</div></html:link>
	</c:otherwise>
	</c:choose>
</display:column>




<%-- WishList Column --%>
<display:column  class="wishCell infoMark" headerClass="wishHeaderClass">
	<c:set var="favStyle" value="toWishlist"/>
	<c:set var="meth" value="add"/>
	<c:if test="${loop.AFavorite}">
		<c:set var="meth" value="sub"/>
		<c:set var="favStyle" value="fromWishlist"/>
	</c:if>
	<%-- WishList BUTTON --%>
	<html:link page='/user/wishList.html?method=${meth}&fileId=${fileId}&clipId=${loop.id}' styleClass="${favStyle} ${userStat}"><div class="${favStyle}Button">&nbsp;</div></html:link>
</display:column>


    <display:column sortProperty="name" sortable="true" titleKey="soundClipForm.name"  class="nameCell infoMark" headerClass="nameHeaderClass">${loop.name}<br/>
    <c:if test="${not inShop}"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${loop.storeId}'/>" title="view shop"><small>${loop.storeName}</small></a></c:if></display:column>


     <%-- display:column property="score" sortable="true" titleKey="soundClipForm.score" sortName="score"/ --%>
<display:column class="formatsCell" headerClass="formatsHeaderClass">
	<%-- div class="formatSwitch">${formats}</div --%>
	<c:set var="choice" value="${param.preffrm}"/>
	<c:set var="formats" value="${loop.audioFormat}"/>
<%@ include file="/WEB-INF/pages/listcomponents/fileformats.jsp"%>
</display:column>

<display:column class="genreCell" headerClass="genreHeaderClass">
	<c:set var="iconimg">/images/loop_icon.gif</c:set>
	<c:if var="isComponent" test='${not empty loop.trackpackId && loop.trackpack.ready}'>
		<c:set var="iconimg">/images/pack_loop_icon.gif</c:set>	
	</c:if>
	<c:choose><c:when test="${isComponent}">
  <html:link page="${tpUrl}" styleClass="viewParent" styleId="innerId_${loop.id}"  paramId="clipId" paramName="loop" paramProperty="trackpackId"><html-el:img page="${iconimg}" width="15px" height="15px" title="${loop.trackpack.name}"/></html:link></c:when>
  <c:otherwise><html-el:img page="${iconimg}" width="15px" height="15px" title="${loop.trackpack.name}"/></c:otherwise></c:choose>
 </display:column>


    <display:column sortable="true" sortProperty="instrumentId" titleKey="soundClipForm.instrument" class="instrumentCell" headerClass="instrumentHeaderClass"><fmt:message key="${loop.instrument.labelKey}"/></display:column>
	<display:column sortProperty="price.amount" sortable="true" titleKey="soundClipForm.price" sortName="price.amount" class="priceCell" headerClass="priceHeaderClass">
<fmt:formatNumber minFractionDigits="2" type="currency" maxFractionDigits="2" value="${loop.price.amount}" currencySymbol="$"/>
</display:column>

<%--
    <display:column sortable="false" titleKey="soundClipForm.genre" class="genreCell" headerClass="genreHeaderClass" headerClass="genreHeaderClass"><fmt:message key="${loop.genre[0].labelKey}"/></display:column>
--%>

<display:column property="bpm" sortable="true" sortName="bpm" titleKey="soundClipForm.bpm" class="bpmCell" headerClass="bpmHeaderClass"/>

<display:column titleKey="soundClipForm.viewerScore" class="rateCell" headerClass="rateHeaderClass">
<c:choose>
	<c:when test="${loop.viewerScore eq '1'}">
		<div class='rateItNot'><div class="thumbUp" title="thumbsUp">&nbsp;</div></div>
	</c:when>
	
	<c:when test="${loop.viewerScore eq '-1'}">
		<div class='rateItNot'><div class="thumbDown" title="thumbsDown">&nbsp;</div>	</div>
	</c:when>
	<c:otherwise>
		<c:choose><c:when test="${isMember}">		<div class='rateIt'>
			<html:link page="/user/rateClip?userId=${uid}&clipId=${loop.id}&score=1&amp;shopId=${loop.storeId}" styleClass="rateClip"><div class="thumbUp" title="thumbsUp">&nbsp;</div></html:link>
			
			<html:link page="/user/rateClip?userId=${uid}&clipId=${loop.id}&score=-1&amp;shopId=${loop.storeId}" styleClass="rateClip"><div class="thumbDown" title="thumbsDown">&nbsp;</div></html:link>	
		</div>
</c:when><c:otherwise>		<div class='rateItNot'>
			<div class="thumbUp" title="thumbsUp">&nbsp;</div>
			
			<div class="thumbDown" title="thumbsDown">&nbsp;</div>
		</div>
</c:otherwise></c:choose>
	</c:otherwise>
</c:choose>
</display:column>

<%-- 
<display:column property="modified" sortName="modified" sortable="true" class="modifiedCell" headerClass="modifiedHeaderClass" headerClass="modifiedHeaderClass" format="{0,date,MM/dd/yyyy}" title="date"/>
<display:column class="owner${loop.ownedByViewer}" headerClass="noColumn"></display:column>
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
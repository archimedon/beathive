<c:set var="tpUrl" value="/viewTrackpack.html" />
<c:if var="inShop" test="${not empty param.storeId}">
	<c:set var="tpUrl" value="/viewTrackpack.html?storeId=${loop.storeId}" />
	<div id="shopInfo"><%--<html-el:img page="${storeForm.bannerImg}" styleClass="smallBannerImg alignLeft"/>--%>
	<ul id="storeHed">
		<li class="storename">${requestScope.storeForm.name}</li>
		<li class="producername"><span class="subHed"><fmt:message key="loopSearchForm.producer.label"/>:</span> <b>${requestScope.storeForm.producerName}</b></li>
	</ul></div>
</c:if>
<c:set var="hasAdv" value="${fn:length(searchmeta.advancedCriteria) > 0}"/>
<div id="searchCriteria">
<c:if test="${ (fn:length(searchmeta.requiredCriteria) > 0) or hasAdv}">
	<c:set var="requiredCriteria" value="${searchmeta.requiredCriteria}"/>
	<c:set var="advancedCriteria" value="${searchmeta.advancedCriteria}"/>

	<ul class="requiredCriteria">
		<c:forEach  items="${searchmeta.requiredCriteria}" var="val" varStatus="stat">
			<li><b>${val}</b></li>
		</c:forEach>
		<c:if test="${inShop}">
			<li><small>&nbsp;&lt;&ndash; Results are filtered <a href="<c:url value='/shop/loops.html?method=search&storeId=${param.storeId}'/><c:if test="${not empty param.type}">&type=${param.type}</c:if>" id="dropFilter" title="<fmt:message key="results.store.viewall"/>">[<fmt:message key="results.store.viewall"/>]</a></small>
</li>
		</c:if>
	</ul><br style="clear:both"/>
	<c:if test='${hasAdv}'>
	<ul id="advancedCriteria">
		<c:forEach items="${searchmeta.advancedCriteria}" var="entry" varStatus="stat">
			<li><label>${entry.key}</label>: <b>${entry.value}</b></li>
		</c:forEach>
	</ul>
	</c:if>
</c:if>
</div>

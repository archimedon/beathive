<bean:struts mapping="/loops" id="smap"/>
<c:set var="backurl">http://<%=request.getServerName() + request.getContextPath()%>${smap.path}</c:set>
<c:choose>
<c:when test="${not empty param.storeId and fn:startsWith( header['referer'] , backurl )}"><c:set var="backbutton" scope="session"><li class="resTab" name="back"><html:link href="${header['referer']}" ><fmt:message key="results.tab.returntoresults"/></html:link></li></c:set>
</c:when>
<c:when test="${empty param.storeId}">
<c:remove var="backbutton" scope="session"/>
</c:when>
<c:otherwise>
</c:otherwise>
</c:choose>
<c:choose>
<c:when test="${param.type eq 'loop'}">
<fmt:message var="label2" key="loopSearchForm.loop.label"/>
<c:if test="${loopList.fullListSize > 1}">
<fmt:message var="label2" key="loopSearchForm.loops.label"/>
</c:if>
<fmt:message var="label1" key="loopSearchForm.trackpack.label"/>
<c:if test="${loopList.relTotal > 1}">
<fmt:message var="label1" key="loopSearchForm.trackpacks.label"/>
</c:if>
<c:set var="tab1"><li class="resTab" name="trackpack"><a>${label1} ${loopList.relTotal}</a></li></c:set>
<c:set var="tab2"><li class="selected"><a>${label2} ${loopList.fullListSize}</a></li></c:set>
</c:when>
<c:otherwise>
<fmt:message var="label2" key="loopSearchForm.loop.label"/>
<c:if test="${loopList.relTotal > 1}">
<fmt:message var="label2" key="loopSearchForm.loops.label"/>
</c:if>
<fmt:message var="label1" key="loopSearchForm.trackpack.label"/>
<c:if test="${loopList.fullListSize > 1}">
<fmt:message var="label1" key="loopSearchForm.trackpacks.label"/>
</c:if>
<c:set var="tab1"><li class="selected"><a>${label1} ${loopList.fullListSize}</a></li></c:set>
<c:set var="tab2"><li class="resTab" name="loop"><a>${label2} ${loopList.relTotal}</a></li></c:set>
</c:otherwise>
</c:choose>
<div id="menuBox" class="short">
<ul class="menuList" id="searchTabs">
	${backbutton}
	<c:out value="${tab1}" escapeXml="false"/>
	<c:out value="${tab2}" escapeXml="false"/>
	</ul>
</div>

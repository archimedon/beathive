<c:if var="inWishlist" test="${not empty param.wishlist}"><c:set var="inCart" value="${false}"/></c:if>
<div class="resultBody">
<display:table name="loopList" htmlId="searchResultsInnerLoop" id="loop" class="display loopList" requestURI="/viewComponents.html" defaultsort='11'>

<%-- 1 playbuttonCell --%>
	<display:column  class="playbuttonCell" headerClass="playbuttonHeaderClass">
		 <c:if var="inShop" test="${not empty param.storeId}"/>
		<%-- initialize variables --%><c:set var="formats"></c:set><c:set var="fileId"></c:set><c:set var="tmp"></c:set>
		<%-- step through the formats --%>
		<c:forEach items="${loop.audioFormat}" var="loopsam" varStatus="stat">	
			<%-- hold a fileId for later --%>
			<c:set var="tmp" value="${loopsam.id}"/>
			<%-- display any play button. The first --%>
			<c:if test="${stat.first}"><div class="player"  id="swf_${loop.id}_${loopsam.formatId}">
			<c:import url="/component/playbutton.jsp">
				<c:param name="path" value="${loopsam.samplePath}"/>
				<c:param name="oid" value="${loop.id}_${loopsam.formatId}"/>
				<c:param name="num" value="${loop_rowNum}"/>
				<c:param name="js" value="1"/>
			</c:import>
			</div></c:if>
			<%-- store format names --%>
			<c:set var="formats"><c:if test="${loopsam.formatId ne 'aif'}"><label class="formatOption">&nbsp;</label></c:if>
			<label class="formatOption<c:if test='${param.preffrm eq loopsam.formatId}'> selected<c:set var="fileId" value="${loopsam.id}"/></c:if>">${loopsam.formatId}</label>
			<c:if test="${stat.last and loopsam.formatId ne 'wav'}"><label class="formatOption">&nbsp;</label></c:if></c:set>
			<%-- end format names --%>
		</c:forEach>
		<%-- confirm an id was selected or set --%>
		<c:if test='${fileId eq ""}'><c:set var="fileId" value="${tmp}"/></c:if>
		<%-- hidden loop descriptors--%>
		<div class="attr" style="display:none"><c:set var="descriptors"  value="${loop.descriptors}" scope="request"/><c:import url="/component/search/clipMeta.jsp"/></div>
</display:column>

<display:column headerClass="cartHeaderClass">&nbsp;</display:column>
    <display:column property="name" sortable="false" titleKey="soundClipForm.name"  class="nameCell" headerClass="nameHeaderClass"/>
  
<%-- instrumentCell --%>
<display:column sortable="false" sortName="instrumentId" sortProperty="instrumentId"   titleKey="soundClipForm.instrument" class="instrumentCell" headerClass="instrumentHeaderClass"><fmt:message key="${loop.instrument.labelKey}"/></display:column>

  <display:column sortable="false" class="genreCell" headerClass="genreHeaderClass"></display:column>


<%-- bpmCell --%>
<display:column property="bpm" sortable="false" sortName="bpm" titleKey="soundClipForm.bpm" class="bpmCell" headerClass="bpmHeaderClass" />

    <display:column titleKey="soundClipForm.storeName"  class="storeNameCell" headerClass="storeNameHeaderClass"><a class="viewShop" href="<c:url value='/shop/loops.html?storeId=${loop.storeId}'/>" title="view shop">${loop.storeName}</a></display:column>

<%-- priceCell --%>
   <display:column property="price.amount" sortable="false" titleKey="soundClipForm.price" sortName="price.amount" format="{0,number,0.00}" class="priceCell" headerClass="priceHeaderClass"/>
<c:if test="${inWishlist}">
<display:column   headerClass="removeFavHeaderClass"></display:column>
</c:if>

<display:column class="owner${loop.ownedByViewer}" headerClass="noColumn"></display:column>
   --%>
         <display:setProperty name="paging.banner.placement" value="bottom"/>

    <display:setProperty name="sort.amount" value="list"/>
    <display:setProperty name="paging.banner.item_name" value="loop"/>
    <display:setProperty name="paging.banner.items_name" value="loops"/>
    
<display:setProperty name="basic.show.header" value="true"/>
<display:setProperty name="paging.banner.no_items_found"> </display:setProperty>

<display:setProperty name="paging.banner.all_items_found"> </display:setProperty>

<display:setProperty name="paging.banner.one_item_found"> </display:setProperty>

<display:setProperty name="paging.banner.some_items_found"> </display:setProperty>
</display:table>
</div>
<div id="cartDetail">

<br/>
<h1><fmt:message key="cart.summary.heading"/></h1>
<br/>
<ul id="cartSummary">


<%@ include file="/WEB-INF/pages/cart/cartstats.jsp" %>
	<li class="checkout2">
		<div class="cartAmount">
						<html:link styleClass="checkout2" forward="paymentForm"><fmt:message key="cart.summary.ccinfo.button"/></html:link>
		</div>		
	</li>
	<li><p><br/></p></li>
	<li class="item">
		<div class="cartCenter">
		<div id="resultBody">
		
<display:table name="${userCart.list}" htmlId="userCart" id="tpclip" class="display loopList" requestURI="" >
<%-- export="true"  keepStatus="true"--%>

	<display:column class="expandCell" headerClass="expandHeaderClass"  title="#">
		<c:set var="ltype" value="loop"/><c:set var="numl"  value="0"/>
		<c:if var="isTP" test="${fn:contains(tpclip.class.name , 'Trackpack')}"><c:set value="${fn:length(tpclip.loops)}" var="numl" /></c:if>	
		<c:if  test="${isTP}">
			<c:set var="ltype" value="trackpack"/>
			<c:set var="compUrl">/viewComponents.html?cartlist=1&clipId=${tpclip.id}</c:set>
			<a class="expand" title="[ <fmt:message key="soundClipForm.component_loops.label"/> ]" name="a_${tpclip.id}" href="<c:url value='${compUrl}'/>" ref="${tpclip.id}"><div class="expandButton">&nbsp;</div></a>
		</c:if>	
	</display:column>

	<display:column class="playbuttonCell" headerClass="playbuttonHeaderClass">
		<%-- initialize variables --%><c:set var="formats"></c:set><c:set var="fileId"></c:set><c:set var="tmp"></c:set>
		<%-- step through the formats --%>
		<c:set value="${tpclip.audioFile}" var="loopsam"/>	
		<%-- display any play button. The first --%>
		<div class="player"  id="swf_${tpclip.id}_${loopsam.formatId}">
			<c:import url="/component/playbutton.jsp">
				<c:param name="path" value="${loopsam.samplePath}"/>
				<c:param name="oid" value="${tpclip.id}_${loopsam.formatId}"/>
				<c:param name="js" value="1"/>
			</c:import>
		</div>
		<label class="formatOption">${loopsam.formatId}</label>
		<%-- confirm an id was selected or set --%>
		<c:set var="fileId" value="${loopsam.id}"/>
		<%-- hidden tpclip descriptors--%>
		<div class="attr" style="display:none"><c:out value="${tpclip.descriptors}"/></div>
	</display:column>


<%-- CART Column --%>
<display:column class="cartCell" headerClass="cartHeaderClass">
	<c:set var="meth" value="sub"/>
	<c:set var="fid" value="${tpclip.id}_"/>
	<c:set var="cartStyle" value="fromCart"/>
	<%-- CART Button --%>
	<html:link page='/loopCart.html?method=${meth}&type=${ltype}&fileId=${fileId}&clipId=${tpclip.id}' styleClass="${cartStyle}"><div class="${cartStyle}Button">&nbsp;</div></html:link>
</display:column>

<display:column property="name" sortable="false" titleKey="soundClipForm.name" class="nameCell" headerClass="nameHeaderClass"/>

<display:column titleKey="soundClipForm.instrument" headerClass="instrumentHeaderClass" class="instrumentCell">
	<c:choose>
		<c:when test="${isTP}"><fmt:message key="soundClipForm.numcomponents.label"><fmt:param value="${numl}"/></fmt:message></c:when>
		<c:otherwise><fmt:message key="${tpclip.instrument.labelKey}"/></c:otherwise>
	</c:choose>
</display:column>

<display:column sortable="false" titleKey="soundClipForm.genre" sortProperty="genreId"  class="genreCell" headerClass="genreHeaderClass"><fmt:message key="${tpclip.genre[0].labelKey}"/></display:column>
<display:column property="bpm" sortable="false" sortName="bpm" titleKey="soundClipForm.bpm" class="bpmCell"  headerClass="bpmHeaderClass"/>
<display:column class="shopCell" titleKey="soundClipForm.storeName" headerClass="shopHeaderClass">${tpclip.storeName}</display:column>
<display:column property="price.amount" sortable="false" titleKey="soundClipForm.price" sortName="price.amount" format="{0,number,0.00}" class="priceCell"  headerClass="priceHeaderClass" total="true"/>

<display:setProperty name="sort.amount" value="list"/>

</display:table>

</div>

</div>
	</li>
	<li class="checkout2">
		<div class="cartAmount">
						<html:link styleClass="checkout2" forward="paymentForm"><fmt:message key="cart.summary.ccinfo.button"/></html:link>
		</div>		
	</li>
</ul>
</div>

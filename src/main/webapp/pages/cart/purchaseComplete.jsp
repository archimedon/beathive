<%@ include file="/common/var_contentType.jsp"%>
<c:if test="${empty downUrl}"><c:redirect url="/user/zips.html"/></c:if>
<%@ include file="/common/messages.jsp" %>
<%if(true){	session.removeAttribute("cartItemList");%>
<head>
<title><fmt:message key="cart.receipt.title"/></title>
</head>
<body id="purchaseCompletePage">
<div id="cartDetail">
<ul>
	<li><h1><fmt:message key="cart.receipt.head"/></h1></li>

	<li class="separator"></li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="subTotal"><fmt:formatNumber currencySymbol="\$" type="currency" value="${fincart.subTotal}"/></div></li>
	<li class="item">
		<div  class="cartTitle"><fmt:message key="cart.summary.bulkdiscount.label"/></div>
		<div class="cartAmount" id="savings">&ndash; <fmt:formatNumber currencySymbol="\$" type="currency" value="${fincart.savings}"/></div>
	</li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="total"><fmt:formatNumber currencySymbol="\$" type="currency" value="${fincart.total}"/></div></li>
	<li class="item">
	<div class="cartTitle"><fmt:message key="cart.summary.promodiscount.label"/></div>
	<div class="cartAmount">
	<c:choose><c:when test="${empty fincart.promoDiscount}">--</c:when><c:otherwise>
	<fmt:formatNumber pattern="##% off" type="percent" value="${fincart.promoDiscount}"/><br/><small class="xsmall">[<c:out value="${fincart.promoCode}"/>]</small></c:otherwise></c:choose></div></li>

	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="discountedAmount"><fmt:formatNumber currencySymbol="\$" type="currency" value="${fincart.discountedAmount}"/></div></li>
	<li class="separator"></li>
<li class="cartCenter"><br /><br /><br /><html:link styleClass="button" href="${downUrl}"><fmt:message key="cart.receipt.download.label"/></html:link> <br /><br /><br /><html:link href="${downUrl}">&lt;${downUrl}&gt;</html:link><br /><br />
</li>
<li class="cartCenter"><br /><br /><br />Allow 2-5 minutes for Paypal confirmation of payment to be received. If the download link on this page is not yet ready please visit <html:link href="https://www.beathive.com/user/zips.html">your download area</html:link> in a few minutes.<br /><br />
</li>
<li class="cartCenter">
<display:table name="${carttmp.list}" cellspacing="0" cellpadding="0" keepStatus="true"
    id="cartItem" class="table userCart ltr" requestURI="" partialList="false">
	<display:column>
			<c:forEach items="${cartItem.audioFormat}" var="loopsam" varStatus="stat" begin="0" end="0">
			<span class="player" id="swf_${cartItem.id}" >
 <c:url var='moviepath' value='/loopButton?path=${loopsam.samplePath}'/>
 <c:url var='embedpath' value='/loopButton.swf?path=${loopsam.samplePath}'/>	  
<object id="obj_bh-loop_${cartItem.id}" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000_1" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" align="middle" height="22" width="22">
	<param name="movie" value="<c:out value='${moviepath}' escapeXml='false'/>">
	<param name="bgcolor" value="#f2f0ea">
	<param name="allowScriptAccess" value="always">
	<param name="quality" value="high">
	<param name="autostart" value="true">
	<embed src="<c:out value='${embedpath}' escapeXml='false'/>" autostart="true" bgcolor="#f2f0ea" quality="high"  swLiveConnect="true"	 id="emb_bh-loop_${cartItem.id}" name="sndPlayer" 	align="middle" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22"/>
</object></span>
</c:forEach>
</display:column>
    <display:column property="name" sortable="true" titleKey="soundClipForm.name"/>


    <c:choose>
    <c:when test="${fn:contains(cartItem.class.name , 'Trackpack')}">
    <display:column># <fmt:message key="soundClipForm.numcomponent_loops.label"><fmt:param value="${cartItem.loopsExpected}"/></fmt:message></display:column>
</c:when>
<c:otherwise>    <display:column sortable="true" titleKey="soundClipForm.instrument"><fmt:message key="${cartItem.instrument.labelKey}"/></display:column>
</c:otherwise>
</c:choose>
    <display:column><fmt:message key="${cartItem.genre[0].labelKey}"/></display:column>
    <display:column property="price"></display:column>
    <display:setProperty name="basic.show.header" value="false"/>
    
</display:table>
</li>
</ul>
</div>
</body>
<%
session.removeAttribute("carttmp");
}%>
${userCart.clear}

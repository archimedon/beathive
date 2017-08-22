<c:choose>
<c:when test="${param.js=='1'}">
{'numLoops':'${userCart.tally.loop}','numTrackpacks':'${userCart.tally.trackpack}',
'subTotal':'<fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total}"/>','savings':'- <fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total - salesDiscount.discounted[userCart.total]}"/>','total':'<fmt:formatNumber currencySymbol="\$" type="currency" value="${salesDiscount.discounted[userCart.total]}"/>','discountedAmount':'<fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.discountedAmount}"/>'}</c:when>
<c:when  test="${fn:contains(pageContext.request.requestURI , '/cart')}">

<ul>
	<li><h2><fmt:message key="cart.summary.heading"/></h2></li>
</ul>
<ul>
	<li class="separator"></li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="subTotal"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total}"/></div></li>
	<li class="item">
		<div  class="cartTitle"><fmt:message key="cart.summary.bulkdiscount.label"/></div>
		<div class="cartAmount" id="savings">&ndash; <fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total - salesDiscount.discounted[userCart.total]}"/></div>
	</li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="total"><fmt:formatNumber currencySymbol="\$" type="currency" value="${salesDiscount.discounted[userCart.total]}"/></div></li>
	<li class="item">
	<div class="cartTitle"><fmt:message key="cart.summary.promodiscount.label"/></div>
	<div class="cartAmount">
	<c:choose><c:when test="${empty userCart.promoDiscount}">--</c:when><c:otherwise>
	<small class="xsmall">(code: <c:out value="${userCart.promoCode}"/>)</small> <fmt:formatNumber pattern="##% off" type="percent" value="${userCart.promoDiscount}"/></c:otherwise></c:choose></div></li>

	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="discountedAmount"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.discountedAmount}"/></div></li>
</ul>






</c:when>
<c:when  test="${fn:contains(pageContext.request.requestURI , '/purchaseComplete')  or fn:contains(pageContext.request.requestURI , '/ppcomplete') }">








</c:when>
<c:otherwise><c:if var="show" test="${!  (  fn:contains(pageContext.request.requestURI , '/cart')  or  fn:contains(pageContext.request.requestURI , 'purchaseComplete')  or  fn:contains(pageContext.request.requestURI , '/viewCart')   or fn:contains(pageContext.request.requestURI , '/ppcomplete')  )}"/><%-- --%>
<c:choose>
<c:when test="${show}">
	<ul>
	<li><h2 class="showForm" ref="cartbody"><fmt:message key="cart.summary.heading"/></h2></li>
	</ul>
	<ul id="cartbody">


	
	<li class="item"><div class="cartTitle"><fmt:message key="loopSearchForm.loops.label"/>:</div><div class="cartAmount" id="numLoops">${userCart.tally.loop}</div></li>
	<li class="item"><div class="cartTitle"><fmt:message key="loopSearchForm.trackpacks.label"/>:</div><div class="cartAmount"id="numTrackpacks">${userCart.tally.trackpack}</div></li>
	<li class="separator"></li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="subTotal"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total}"/></div></li>
	<li class="item">
		<fmt:message key="cart.summary.bulkdiscount.label"/> 
		<div class="cartAmount" id="savings" style="letter-spacing:0;"> &ndash;&nbsp;<fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total - salesDiscount.discounted[userCart.total]}"/>
		</div>
		<br /><html:link styleClass="sidebar_link" page="/about/faq.html#discount"><fmt:message key="cart.summary.bulkdiscount.link.label"/></html:link>
	</li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="total"><fmt:formatNumber currencySymbol="\$" type="currency" value="${salesDiscount.discounted[userCart.total]}"/></div></li>
	
	
	<c:if test="${not empty userCart.promoDiscount}">
		<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.promodiscount.label"/>:</div><div class="cartAmount"><small class="xsmall">(code: <c:out value="${userCart.promoCode}"/>)</small><fmt:formatNumber currencySymbol="\%" type="percent" value="${userCart.promoDiscount}"/></div></li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="discountedAmount"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.discountedAmount}"/></div></li>
	</c:if>
	
	
	<li id="checkout">
		<div class="cartAmount">
			<html:link styleClass="checkout" action="viewCart"><fmt:message key="cart.summary.checkout.button"/></html:link>
		</div>		
	</li>
	<li id="methodOfPayments">
		<fmt:message key="cart.summary.customerservice.message"/>
		<ul id="payments">
			<li><html:img page="/images/logo_ccVisa.gif"/></li>
			<li><html:img page="/images/logo_ccMC.gif"/></li>
			<li><html:img page="/images/logo_ccAmex.gif"/></li>
			<li><html:img page="/images/logo_paypal.gif"/></li>
		</ul>		
	</li>
</ul>
</c:when>
<c:otherwise>
	<ul>
	<li><h2 class="showForm" ref="cartbody"><fmt:message key="cart.summary.heading"/></h2></li>
	</ul>
	<ul id="cartbody">


	
	<li class="item"><div class="cartTitle"><fmt:message key="loopSearchForm.loops.label"/>:</div><div class="cartAmount" id="numLoops">${userCart.tally.loop}</div></li>
	<li class="item"><div class="cartTitle"><fmt:message key="loopSearchForm.trackpacks.label"/>:</div><div class="cartAmount"id="numTrackpacks">${userCart.tally.trackpack}</div></li>
	<li class="separator"></li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="subTotal"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total}"/></div></li>
	<li class="item">
		<div class="cartCenter"><fmt:message key="cart.summary.bulkdiscount.label"/> <html:link styleClass="sidebar_link" page="/about/faq.html#discount"><fmt:message key="cart.summary.bulkdiscount.link.label"/></html:link>
		<div  class="cartTitle">
			<%-- Discount METER HERE --%>
			<table id="meter" title="&ndash; <fmt:formatNumber currencySymbol="\$" type="currency" value="${(salesDiscount.memberDiscount[userCart.total]/100) * userCart.total}"/>">
				<tr>
					<td>10.0%</td>
					<td>12.0%</td>
					<td>15.0%</td>
					<td>20.0%</td>
				</tr>
			</table>
		</div>
		<div class="cartAmount" id="savings">&ndash; <fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total - salesDiscount.discounted[userCart.total]}"/></div>
	</li>
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="total"><fmt:formatNumber currencySymbol="\$" type="currency" value="${salesDiscount.discounted[userCart.total]}"/></div></li>
	<li id="checkout">
		<div class="cartAmount">
			<html:link styleClass="checkout" forward="paymentForm"><fmt:message key="cart.summary.ccinfo.button"/></html:link>
		</div>		
	</li>
	<li id="methodOfPayments">
		<fmt:message key="cart.summary.customerservice.message"/>
		<ul id="payments">
			<li><html:img page="/images/logo_ccVisa.gif"/></li>
			<li><html:img page="/images/logo_ccMC.gif"/></li>
			<li><html:img page="/images/logo_ccAmex.gif"/></li>
			<li><html:img page="/images/logo_paypal.gif"/></li>
		</ul>		
	</li>
</ul>
</c:otherwise>
</c:choose>
</c:otherwise>
</c:choose>
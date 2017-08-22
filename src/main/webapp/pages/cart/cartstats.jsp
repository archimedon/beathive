	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="subTotal"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.total}"/></div></li>
	<li class="item">
		
		<div  class="cartTitle">Bulk Discount: 
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
	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.subtotal.label"/>:</div><div class="cartAmount" id="total"><fmt:formatNumber currencySymbol="\$" type="currency" value="${salesDiscount.discounted[userCart.total]}"/></div></li>
	<li class="item">
	<div class="cartTitle">Promotional Discount</div>
	<div class="cartAmount">
	<c:choose><c:when test="${empty userCart.promoDiscount}"><form><small>Enter Promo Code: </small><input type="text" class="small" size="5" name="dcode"/><button class="button">APPLY</button></form></c:when><c:otherwise>
	<small class="xsmall">(Promo Code: <c:out value="${userCart.promoCode}"/>)</small> <fmt:formatNumber pattern="##% off" type="percent" value="${userCart.promoDiscount}"/></c:otherwise></c:choose></div></li>

	<li class="item"><div class="cartTitle"><fmt:message key="cart.summary.total.label"/>:</div><div class="cartAmount" id="discountedAmount"><fmt:formatNumber currencySymbol="\$" type="currency" value="${userCart.discountedAmount}"/></div></li>

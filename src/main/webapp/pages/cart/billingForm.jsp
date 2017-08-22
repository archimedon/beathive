<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="billingForm.title"/></title>
</head>
<body id="billingFormPage">
<logic:messagesPresent >
    <div class="error" id="errorMessages">
        <html:messages id="error" bundle="cybersourceCodes">
            <html:img pageKey="icon.warning.img" 
                altKey="icon.warning" styleClass="icon"/>
            <c:out value="${error}" escapeXml="false"/><br/>
        </html:messages>
    </div>
</logic:messagesPresent>
<h3 class="showForm" ref="cartitems"><%--<fmt:message key="number.of.items.label"/> ${fn:length(userCart.list)}--%>BILLING INFORMATION</h3>
<table id="cartitems" style="display:none;">
<c:forEach items="${userCart.list}" varStatus="stat" var="loopItem">

<%-- the clip-id--%>
<input
	type="hidden"
	name="<c:out value='item_number_${stat.count}'/>"
	value="<c:out value='${loopItem.fileId}'/>"/>

<input type="hidden" name="<c:out value='on0_${stat.count}'/>" value="Producer"/>
<input
	type="hidden"
	name="<c:out value='os0_${stat.count}'/>"
	value="<c:out value='${loopItem.storeName}'/>"/>


<input type="hidden" name="<c:out value='item_name_${stat.count}' escapeXml="false"/>" value="<c:out value='${loopItem.name}' escapeXml="false"/>"/>
<input type="hidden" name="<c:out value='amount_${stat.count}' escapeXml="false"/>" value="<fmt:formatNumber maxFractionDigits="2" value='${loopItem.price.amount * (userCart.discountedAmount / userCart.total )}'/>"/>
<input type="hidden" name="<c:out value='tax_${stat.count}' escapeXml="false"/>" value="0.00"/>

<tr>
<td class="item-name"><div>${loopItem.name}<p class="item-option">Item #&nbsp;${loopItem.fileId}</p>
<p class="item-option"><fmt:message key="loopSearchForm.producer.label"/>: ${loopItem.storeName}</p>
</div></td>
<td><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" value='${loopItem.price.amount}'/></td>
<td>1</td>
<td class="item-total"><fmt:formatNumber type="currency" currencySymbol="$" maxFractionDigits="2" value='${loopItem.price.amount * (userCart.discountedAmount / userCart.total )}'/></td>
<td class="item-action"></td>
</tr>

</c:forEach></table>
<br>
<B><fmt:message key="twoways.to.pay.label"/> </B>
<br><br>
<%@ include file="/WEB-INF/pages/cart/paypalPluginForm.jsp"%>
<br/>


OR

<br/>
<br/>
<%--
--%>

<b><fmt:message key="accepted.cards.label"/></b><br /><br />
<h2 styleClass="anyClassName"><fmt:message key="billingForm.heading"/></h2>
<html-el:form action="/purchase" styleId="billingForm" method="post" focus="firstName" onsubmit="return validateBillingForm(this)">

	<table> 
		<tr> 
			<td><beathive2:label styleClass="anyClassName" key="billingForm.firstName"/></td>
			<td>
				<html:text property="firstName" styleClass="FormInside" size="30" maxlength="60"/>
			</td>
		</tr> 
		<tr> 
			<td ><beathive2:label styleClass="anyClassName" key="billingForm.lastName"/>:</td>
			<td> 
			 <html:text property="lastName" styleClass="FormInside" size="30" maxlength="60"/>
			</td>
		</tr> 
		<tr>
			<td><beathive2:label styleClass="anyClassName" key="billingForm.country"/>:</td>
			<td><html:select property="country" styleId="country">
					<html:options collection="countries" property="value" labelProperty="label" />
			</html:select>
				
			</td>
		</tr> 
		<tr>
			<td ><beathive2:label styleClass="anyClassName" key="billingForm.street1"/>:</td>
			<td>
				<html:text property="street1" styleClass="FormInside" size="30" maxlength="60"/>
			</td>
		</tr> 
<%--		<tr> 
			<td><beathive2:label styleClass="anyClassName" key="billingForm.street2"/>:</td>
			<td>
				<html:text property="street2" styleClass="FormInside" size="30" maxlength="60"/>
			</td>
		</tr>
--%>
		<tr> 
			<td><beathive2:label styleClass="anyClassName" key="billingForm.city"/>:</td>
			<td>
				<html:text property="city" styleClass="FormInside" size="30" maxlength="60"/>
			</td>
		</tr> 
		<tr>
			<td><beathive2:label styleClass="anyClassName" key="billingForm.state"/>/<beathive2:label styleClass="anyClassName" key="billingForm.province"/>:</td>
			<td>	
						<html:select property="province" styleId="province">
						<html:options collection="states" property="value" labelProperty="label" />
						</html:select>
				
				
			</td>
		</tr> 
		<tr> 
			<td><beathive2:label styleClass="anyClassName" key="billingForm.postalCode"/>:</td>
			<td> 
			 <html:text property="postalCode"  styleId="postalCode" styleClass="FormInside" size="12" maxlength="12"/>
			</td>
		</tr> 
		<tr> 
			<td><b><fmt:message key="billingForm.areacode"/>/<fmt:message key="billingForm.phone"/>:</b></td>
			<td> 
				<html:text
					property="areacode"
					styleClass="FormInside"
					size="5" maxlength="5"/> <html:text property="phone" styleClass="FormInside" size="18" maxlength="18"/>
			</td>
		</tr>
		<tr>
			<td><beathive2:label styleClass="anyClassName" key="billingForm.email"/>:</td>
			<td>	<html:text property="email" styleClass="FormInside" size="30" maxlength="60"/></td>
		</tr>

		<%@ include file="/WEB-INF/pages/cart/creditCardForm.jsp" %>
		<!-- tr>
			<td><beathive2:label styleClass="anyClassName" key="billingForm.promoCode"/>:</td>
			<td><html:text property="promoCode"  value="${userCart.promoCode}" size="5" maxlength="5"/></td>
		</tr -->
		<tr>
			<td>&nbsp;</td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<html:submit styleClass="button checkout" property="method.auth"><fmt:message key="cart.summary.checkout.button"/></html:submit></td>
		</tr>
</table>

<script type="text/javascript">
$(function(){
	$('#country').change(function(){
		var val = this.options[this.selectedIndex].value;
		var parent = $('#province').closest('td');
		parent.load('<c:url value="/component/lookupProvince.jsp"/>?cntry='+val); 
	});
});
</script>


<!-- Begin Validator Javascript Function-->
<html:javascript formName="billingForm"/>
<!-- End of Validator Javascript Function--></html-el:form>
<div id="cybersource" style="text-align:center;">Payments are processed by 
<a href="http://www.cybersource.com/" target="offsite">Cybersource</a></div>
</body>
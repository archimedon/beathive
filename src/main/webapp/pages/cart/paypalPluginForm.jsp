<fmt:setLocale scope="page" value="US"/>
<button class="button paypalcheckout" id="ppbutton"><fmt:message key='cart.paypal.button'/></button>
<script type="text/javascript">
$(document).ready(function() {
	$('#ppbutton').click(function(){
		$.post(
				// url
			"/cart.html"
				// params
			, {"method":"paypal"} 
				// call back
			, function (data) {
					if(data['message']){alert(data['message']);return false;}
					
				for (var t in data){
					$('#'+t).val(data[t]);
				}
				uri = $('#return').val();
				$('#return').val(uri + data['invoice']);
				
				$('#ppForm').submit();
			} 
				// dataType
			, 'json'
		);	
		$(this).unbind("click");		
		$(this).css({'color':'#ccc'}).attr('disabled');
		return false;
	});
});
</script>
<form action="https://www.paypal.com/us/cgi-bin/webscr" method="post" id="ppForm">
<input type="hidden" name="cmd" value="_ext-enter">
<input type="hidden" name="redirect_cmd" value="_cart">
<%--
<form action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post" id="ppForm">
<input type="hidden" name="ipn_test" value="1">
#test biz-id
<input type="hidden" id="test_id" name="business" value="8EZLUXCPUXCQG">
#actual business ID
<input type="hidden" name="business" value="FUK5CU6U6W8X8">
<input type="hidden" name="business" value="<fmt:message key="paypal.sandbox.seller.email"/>">
--%>
<input type="hidden" name="business" value="<fmt:message key="paypal.seller.email"/>">

<input type="hidden" name="notify_url" value='https://<%=request.getServerName() +"/ppnotify.html;jsessionid=" + session.getId() +"?method.notify=1"%>'/>
<input type="hidden" name="custom" id="custom" value="${billingForm.userId}">
<input type="hidden" name="invoice" id="invoice" value="">

<input type="hidden" name="login_email" value="${billingForm.email}"/>
<%-- input type="hidden" name="login_email" value="<c:out value="${billingForm.email}"/>" --%>
<input type="hidden" name="first_name" value="${billingForm.firstName}"/>
<input type="hidden" name="last_name" value="${billingForm.lastName}"/>
<input type="hidden" name="address1" value="${billingForm.street1}"/>
<input type="hidden" name="address2" value="${billingForm.street2}"/>
<input type="hidden" name="city" value="${billingForm.city}"/>
<input type="hidden" name="country " value="${billingForm.country}"/>
<input type="hidden" name="state" value="${billingForm.province}"/>
<input type="hidden" name="zip" value="${billingForm.postalCode}"/>



<%--
	Do not prompt payers for shipping address. Allowable values:
	0 Ð prompt for an address, but do not require one
	1 Ð do not prompt for an address
	2 Ð prompt for an address, and require one
	 - The default is 0.
--%>
<input type="hidden" name="no_shipping" value="1">
<%-- user is not prompted to leave a note --%>
<input type="hidden" name="no_note" value="1">
<%-- indicates to paypal that the cart contents will be uploaded --%>
<input type="hidden" name="upload" value="1">

<input type="hidden" name="currency_code" value="USD">


<c:url var="cancel_return" value="/viewCart.html"/>
<c:url var="return_url" value="/ppcomplete.html"/>
<c:url var="cpp_header_image" value="/images/BeatHive_logo_medium_small.gif"/>
<c:url var="shopping_url" value="/searchLoops.html"/>



<input type="hidden" id="return" name="return" value="https://<%=request.getServerName()%>${return_url};jsessionid=<%=session.getId()%>?method.ppcomplete=1&invoice="/>
<input type="hidden" name="cpp_header_image" value="https://<%=request.getServerName()%>${cpp_header_image}"/>
<input type="hidden" name="shopping_url" value="https://<%=request.getServerName()%>${shopping_url}"/>
<input type="hidden" name="cbt" value="<fmt:message key="cart.purchase.complete"/>"/>
<input type="hidden" name="cancel_return" value="https://<%=request.getServerName()%>${cancel_return}"/>

<c:forEach items="${userCart.list}" varStatus="stat" var="loopItem">
	<%-- the clip-id--%>
	<input type="hidden" name="item_number_${stat.count}" value="${loopItem.fileId}"/>
	<input type="hidden" name="on0_${stat.count}" value="Producer"/>
	<input type="hidden" name="os0_${stat.count}" value="${loopItem.storeName}"/>	
	<input type="hidden" name="item_name_${stat.count}" value="${loopItem.name}"/>
	<fmt:formatNumber var="ftot" maxFractionDigits="2" value='${loopItem.price.amount * (userCart.discountedAmount / userCart.total )}'/>
	<input type="hidden" name="amount_${stat.count}" value="${ftot}"/>
	<input type="hidden" name="tax_${stat.count}" value="0.00"/>
</c:forEach>
<input type="hidden" name="tax_cart" value="0.00">
<%--
"lc": Optional The language of the login or sign-up page that subscribers see
when they click the Subscribe button. If unspecified, the
language is determined by a PayPal cookie in the subscriberÕs
browser. If there is no PayPal cookie, the default language is U.S.
English.
For allowable values, see .

--%>
<input type="hidden" name="lc" value="1">

<%--
"rm": Return method.

The FORM METHOD used to send data to ${return_url} after payment completion.
Allowable values:

0 Ð all shopping cart transactions use the GET method

1 Ð the payerÕs browser is redirected to the return URL by the
GET method, and no transaction variables are sent

2 Ð the payerÕs browser is redirected to the return URL by the
POST method, and all transaction variables are also posted

--%>
<input type="hidden" name="rm" value="0"/>


<%-- 
<input type="submit" class="button paypalcheckout" value="<fmt:message key='cart.paypal.button'/>">
--%></form>

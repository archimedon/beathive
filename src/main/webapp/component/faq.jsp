<%@ include file="/common/var_contentType.jsp"%>
<ol>
 <li id="faqDiscount">
 <dt>Member Discount
<dd>
<c:if test="${not empty userCart}">
<c:set var="mdisc"  value="${salesDiscount.memberDiscount[userCart.total]}"/>
</c:if>
<c:forEach items="${salesDiscount.memberDiscountMap}" var="entry">
<c:set var="scale"><c:out value="${scale}" escapeXml="false"/><td>${entry.value}%</td></c:set>
<c:set var="legend"><c:out value="${legend}" escapeXml="false"/><td style="border: 1px gold ridge;width:7px; background-color: ${memberDiscountColorCodes[entry.value]}">&nbsp;</td></c:set>
<c:if test="${not empty userCart}">
<c:set var="userpos"><c:out value="${userpos}" escapeXml="false"/><td align="center"><c:if test="${mdisc == entry.value}"><html:img page="/images/up.png" /></c:if></td></c:set>
</c:if>
</c:forEach>
<table id="currentDiscounts">
<tbody>
<tr><c:out value="${scale}" escapeXml="false"/></tr>
<tr><c:out value="${legend}" escapeXml="false"/></tr>
<tr><c:out value="${userpos}" escapeXml="false"/></tr>
</tbody>
</table>
</dd></dt>
</li>
</ol>

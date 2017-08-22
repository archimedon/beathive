<%@ include file="/common/var_contentType.jsp"%>
<div class="message" id="disclaimer"><fmt:message key="contact.producer.disclaimer"/></div>
<html:form styleId="contactForm" method="post" action="/shop/contactProducer.html">
<html:hidden property="storeId" value="${param.storeId}"/>
<html:hidden property="senderName" styleId="senderName" value="${pageContext.request.remoteUser}"/>
<html:hidden property="recipientName" styleId="recipientName" value="${contactForm.recipientName}"/>
<table valign="top" style="position: relative; top: 20%; left: 5%;" border="0" cellpadding="0" cellspacing="5">
<tbody>
<tr>
	<td align="right">To:</td>
	<td align="left"><b>${contactForm.recipientName}</b> <tt style="font-size: 12px;">(${param.storeName})</tt></td>
</tr>
<tr>
	<td align="right">From:</td>
	<td align="left"><b>${pageContext.request.remoteUser}</b></td>
</tr>
<tr>
	<td align="right">Subject:</td>
	<td align="left"><html:text property="subject" styleId="senderName" styleClass="text medium" size="25" /></td>
</tr>
<tr>
	<td align="right" valign="top">Message</td>
	<td align="left"><html:textarea property="message" cols="30" rows="12"></html:textarea></td>
</tr>
<tr>
	<td align="right"></td>
	<td align="right"><html:submit property="method.send" styleClass="button" value="send" styleId="method"/></td>
</tr>
</tbody>
</table>
</html:form>

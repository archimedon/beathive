<%@ include file="/common/taglibs.jsp"%>
<h2>CONTACT</h2>
<br/>
<c:choose>
<c:when test="${sent}">
<%-- fmt:message var="recp" key="${feedbackForm.recipient}"/>
<fmt:message key="feedback.message.sent">
	<fmt:param value="${recp}"/>
</fmt:message --%>

<dl>
<dt>subject: ${feedbackForm.subject}<dt>
<dt>from: ${feedbackForm.email}</dt>
<dd>message:<br/>${feedbackForm.message}</dd>

</dl>
<c:remove var="feedbackForm" scope="session"/>
<c:remove var="sent" scope="session"/>
<script type="text/javascript">
 //<![CDATA[
setTimeout('window.location.href="<c:url value="/home.html"/>";' , 10000);
 //]]>
</script>
</c:when>
<c:otherwise>
<%--@ include file="/common/messages.jsp" --%>
&nbsp;Customer Service: 1-(877)-814-6510 (toll free in the US) or 415-513-5798
<br><br>
&nbsp;AIM: BeatHive Support
<br><br><br>
&nbsp;Please fill out the form below:<br><br>
<html:form action="sendFeedback" method="post" styleId="feedbackForm">
<html-el:hidden property="username" value="${pageContext.request.remoteUser}"/>
<html-el:hidden property="userip" value="${pageContext.request.remoteAddr}"/>
<input type="hidden" name="creationTime"/>
<table valign="top" align="left" border="0" cellpadding="0" cellspacing="5">
<tr>
	<td align="right">Your Email:</td>
	<td align="left"><html:text size="25" property="email"/></td>
</tr>
<tr>
	<td align="right">Send To:</td>
	<td align="left">
	<select name="recipient">
		<option value="feedback.support">Customer Support</option>
		<option value="feedback.tech">Technical Support</option>
		<option value="feedback.sales">Sales/Marketing</option>
		<option value="feedback.bizdev">Business Development</option>
	</select>
	</td>
</tr>
<tr>
	<td align="right">Subject:</td>
	<td align="left"><html:text size="25" property="subject"/></td>
</tr>
<tr>
	<td align="right" valign="top">Message</td>
	<td align="left"><html:textarea property="message" rows="10" cols="50"/></td>
</tr>
<tr>
	<td align="right">
		&nbsp;
	</td>
	<td align="right">
		<html:submit value="SUBMIT MESSAGE"/><br><br><br><br><br><br>
	</td>
</tr>
</tbody></table>

</html:form>

<script type="text/javascript">
 //<![CDATA[
document.forms["feedbackForm"].elements["creationTime"].value=new Date();
 //]]>
</script>

<html:javascript formName="feedbackForm" cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript"
      src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:otherwise>
</c:choose>
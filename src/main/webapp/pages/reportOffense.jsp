<%@ include file="/common/taglibs.jsp"%>
<title><fmt:message key="offenseForm.title"/></title>
<content tag="heading"><fmt:message key="offenseForm.heading"/></content>
<br/>

<c:if test="${not sent}">

Please fill out the form below:<br><br>
<html:form action="sendOffense" method="post" styleId="offenseForm">
<html-el:hidden property="userip" value="${pageContext.request.remoteAddr}"/>
<input type="hidden" name="creationTime"/>
<table valign="top" align="left" border="0" cellpadding="0" cellspacing="5">
<tr>
	<td align="right">Your Full Name:</td>
	<td align="left"><html:text size="25" property="fullname"/></td>
</tr>
<tr>
	<td align="right">Your Company:</td>
	<td align="left"><html:text size="25" property="companyName"/></td>
</tr>
<tr>
	<td align="right">Your Email:</td>
	<td align="left"><html:text size="25" property="email"/></td>
</tr>
<tr>
	<td align="right">Complaint:</td>
	<td align="left">
	<select name="complaint">
		<option>--</option>
		<option>Producer is selling my loop</option> 
		<option>Producer is selling someone else's loop</option>
		<option>Producer does not own the rights to loop</option>
	</select>
	</td>
</tr>
<tr>
	<td align="right">Offending Loop Name:</td>
	<td align="left"><html:text size="25" property="loopName"/></td>
</tr>

<tr>
	<td align="right">Offending Producer Name:</td>
	<td align="left"><html:text size="25" property="producerName"/></td>
</tr>
<tr>
	<td align="right" valign="top">Description</td>
	<td align="left"><html:textarea property="description" rows="10" cols="50"/></td>
</tr>
<tr>
	<td align="right">
		&nbsp;
	</td>
	<td align="right">
		By submitting this complaint, you agree to our <a href="http://www.beathive.com/about.jsp?type=termsofservice">Terms &amp; Conditions</a>.<BR><BR><html:submit value="SUBMIT COMPLAINT"/><br><br><br><br><br><br><br>
	</td>
</tr>
</tbody></table>

</html:form>

<script type="text/javascript">
 //<![CDATA[
document.forms["offenseForm"].elements["creationTime"].value=new Date();
 //]]>
</script>

<html:javascript formName="offenseForm" cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript"
      src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:if>

<%@ include file="/common/taglibs.jsp"%>
<style>
.buttonrow{	padding: 30px 0; }
.midcenter{ margin-left: 200px; margin-top: 15%;width: 400px; }
</style>
<div class="midcenter">
	<%@ include file="/common/messages.jsp" %>
	<c:choose>
	<%-- REPSONSE --%>
	  <c:when test="${newpassword}">
		<c:remove var="newpassword" scope="session"/>
		An email with the new password has been sent to <c:out value="${passwordForm.email}"/>.
	  </c:when>
	  
	<%-- FORM --%>
	  <c:otherwise>
		<h1>Reset password</h1>
		<p>
			<html:form method="post" styleId="passwordForm" action='resetPassword'>
			<div class="fieldrow">
			<label for="email" ><fmt:message key="label.email"/>:</label>
			<html:text property="email" styleId="email" size="45" tabindex="1" />
			</div>
			
			<div class="buttonrow">
			<input id="resetPassword" class="button" name="method.recover" value="Reset Password" tabindex="4" type="submit">
			</div>
			</html:form>
		</p>
	  </c:otherwise>
	<%-- END FORM --%>
	</c:choose>
</div>
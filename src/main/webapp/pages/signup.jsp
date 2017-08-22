<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="signup.title"/></title>
<content tag="heading"><fmt:message key="signup.heading"/></content>
<body id="signup"/>
<%
if (session!=null){
org.springframework.security.ui.savedrequest.SavedRequest sreq = (org.springframework.security.ui.savedrequest.SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY");
if (sreq!=null){
String[] keyv = sreq.getParameterValues("key");
if (keyv!=null && keyv.length > 0){
	pageContext.setAttribute("key" , keyv[0]);
}
}
}
%>
<c:choose>
<c:when test="${registered}">
<div class="registration_text">
<%@ include file="/common/messages.jsp"%>
<%-- fmt:message key="signup.registration_text"/ --%>
<c:remove var="registered" scope="session"/>
<c:remove var="nextUri" scope="session"/>
<c:remove var="uri" scope="session"/>
<%--
<html:link href="${uri}"><c:out value="${uri}" escapeXml="false"/></html:link>  <html:link page="${uri}"><c:out value="${uri}" escapeXml="false"/></html:link>
--%>
</div>
</c:when>
<c:otherwise>
<%@ include file="/common/messages.jsp"%>
</b>
<html:form action="/signup" styleId="signupForm" onsubmit="return validateSignupForm(this)">
<ul>
    <li class="info">
       <fmt:message key="signup.message"/>
    </li>
    <li>
        <beathive2:label styleClass="desc" key="signupForm.username"/>
        <html:errors property="username"/>
        <html:text styleClass="text large" property="username" styleId="username"/>
    </li>
    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="signupForm.password"/>
                <html:errors property="password"/>
                <html:password styleClass="text medium" property="password" redisplay="true"/>
            </div>
            <div>
                <beathive2:label styleClass="desc" key="signupForm.confirmPassword"/>
                <html:errors property="confirmPassword"/>
                <html:password styleClass="text medium" property="confirmPassword" styleId="confirmPassword" redisplay="true"/>
            </div>
        </div>
    </li>
    <c:if test="${cookieLogin != 'true'}">
    </c:if>
    <li>
        <beathive2:label styleClass="desc" key="userForm.passwordHint"/>
        <html:errors property="passwordHint"/>
        <html:text styleClass="text large" property="passwordHint" styleId="passwordHint"/>
    </li>
    <li>
                <beathive2:label styleClass="desc" key="userForm.email"/>
                <html:errors property="email"/>
                <html:text styleClass="text medium" property="email" styleId="email"/>
    </li>
    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="signupForm.firstName"/>
                <html:errors property="firstName"/>
                <html:text styleClass="text medium" property="firstName" styleId="firstName" maxlength="50"/>
            </div>
            <div>
                <beathive2:label styleClass="desc" key="signupForm.lastName"/>
                <html:errors property="lastName"/>
                <html:text styleClass="text medium" property="lastName" styleId="lastName" maxlength="50"/>
            </div>
        </div>
    </li>
    <li class="buttonBar bottom">
        <html:submit property="method.signup" styleClass="button" onclick="bCancel=false">
            <fmt:message key="button.register"/>
        </html:submit>
    </li>
</ul>
</html:form>

<html:javascript formName="signupForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</c:otherwise>
</c:choose>
</body>
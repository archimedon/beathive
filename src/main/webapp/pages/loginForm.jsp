<%@ include file="/common/taglibs.jsp"%>
<%

org.springframework.security.ui.savedrequest.SavedRequest req = 
((org.springframework.security.ui.savedrequest.SavedRequest)session.getAttribute("SPRING_SECURITY_SAVED_REQUEST_KEY"));

if (req != null){ 
	String[] keyv = req.getParameterValues("key");
	 if (keyv!=null && keyv.length > 0){
		pageContext.setAttribute("key" , keyv[0]);
	}
	String uri = req.getRequestUrl();

	pageContext.setAttribute("isShopLogin" , new Boolean (uri.indexOf("producer/home") > -1 || uri.indexOf("createShop") > -1 ) );
	
}
%>
<c:if test="${isShopLogin}"><div class="inform"><fmt:message key="message.createshop">
                <fmt:param><c:url value="/register.html"/></fmt:param>
            </fmt:message></div></c:if>
<form method="post" id="loginForm" action="https://<%=request.getServerName()%>/j_security_check"
    onsubmit="saveUsername(this);return validateForm(this)">
<fieldset>
<ul>
<c:if test="${param.error != null}">
    <li class="error" id="errorBox">
        <img src="<c:url value="/images/iconWarning.gif"/>"
            alt="<fmt:message key="icon.warning"/>" class="icon" />
        <fmt:message key="errors.password.mismatch"/>
        <!--${sessionScope.ACEGI_SECURITY_LAST_EXCEPTION.message}-->
    </li>
</c:if>
    <li>
       <label for="j_username" class="desc fl" style="width:80px;">
            <fmt:message key="label.username"/> <span class="req">*</span>:
        </label>
        <input type="text" class="text medium" name="j_username" id="j_username" tabindex="1" />
    </li>

    <li>
        <label for="j_password" class="desc fl" style="width:80px;">
            <fmt:message key="label.password"/> <span class="req">*</span>:</label>
        <input type="password" class="text medium" name="j_password" id="j_password" tabindex="2" />
    </li>

<c:if test="${appConfig['rememberMeEnabled']}">
    <li>
        <input type="checkbox" class="checkbox" name="rememberMe" id="rememberMe" tabindex="3"/>
        <label for="rememberMe" class="choice"><fmt:message key="login.rememberMe"/></label>
    </li>
</c:if>
    <li>
        <input type="submit" class="button" name="login" value="<fmt:message key="button.login"/>" tabindex="4" />
        <p>
            <fmt:message key="login.signup">
                <fmt:param><c:url value="/register.html"/></fmt:param>
            </fmt:message>
        </p>
    </li>
</ul>
</fieldset>
</form>

<%@ include file="/scripts/login.js"%>

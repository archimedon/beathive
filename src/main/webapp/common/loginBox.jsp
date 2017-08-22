<security:authorize ifAnyGranted="user,producer,admin">
<c:set var="username"><security:authentication property="principal.username"/></c:set>
<div id="loginBox" class="rightColItem">
	<ul>
	<c:choose>
	<c:when test="${switched}">
		<li>
		<h2 style="clear:none;display:inline;"><fmt:message key="user.status"/>: <html:link page="/j_spring_security_exit_user" title="Exit User">${username}</html:link></h2><html:link styleClass="button alignRight" page="/logout.jsp"><fmt:message key="user.logout"/></html:link></li>
	</c:when>
	<c:otherwise>
			<li>
			<h2 style="clear:none;display:inline;"><fmt:message key="user.status"/>: ${username}</h2></li>
	</c:otherwise>
	</c:choose>
	</ul>
</div>
</security:authorize>     
<security:authorize ifNotGranted="user,producer,admin">
<div id="loginBox" class="rightColItem">
<c:if test="${not ( fn:contains(pageContext.request.requestURI , '/login') || fn:contains(pageContext.request.requestURI , '/register'))}">
	<ul>
	<li><h2 class="showFormNot" ref="loginForm"><fmt:message key="button.login"/> &nbsp;&nbsp;<small class="xsmall"><fmt:message key="login.signup">
                <fmt:param><c:url value="/register.html"/></fmt:param>
            </fmt:message></small></h2> </li>
	</ul>
	<form method="post" id="loginForm" action="https://www.beathive.com/j_security_check">
	<ul id="form">
		<li class="gap">&nbsp;</li>
    <li id="errorline"></li>
    <li>
       <label for="j_username" class="left"><fmt:message key="label.username"/></label>
        <input type="text" name="j_username" id="j_username" tabindex="1" />
    </li>
		<li class="gap">&nbsp;</li>

    <li>
        <label for="j_password" class="left"><fmt:message key="label.password"/></label>
        <input type="password" name="j_password" id="j_password" tabindex="2" />
    </li>

	<c:if test="${appConfig['rememberMeEnabled']}">
		<li class="gap">&nbsp;</li>
		<li>
		<span class="left">
			<input type="checkbox" name="rememberMe" id="rememberMe" tabindex="3"/>
			<label for="rememberMe"><fmt:message key="login.rememberMe"/></label></span>
		<span class="right"><input type="submit" id="loginButton"  class="button" name="login" value="<fmt:message key="button.login"/>" tabindex="4" /></span>
		</li>
	</c:if>
</ul>
</form>

<script type="text/javascript">
$(function(){
	$('#signup').click( function(){
		window.location.href='<c:url value="/register.html"/>';
	});	
});
</script>
</c:if>
</div>
</security:authorize>     

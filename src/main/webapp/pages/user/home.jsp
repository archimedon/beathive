<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <content tag="heading"><fmt:message key="userProfile.heading"/></content>
    <meta name="menu" content="UserMenu"/>
</head>
<%@ include file="/WEB-INF/pages/user/tabs.jsp"%>
<%@ include file="/common/messages.jsp" %>
<html:form action="/user/editProfile" method="get">
<!-- IGNORE THIS CODE -->
<!-- <c:set var="province"><c:choose><c:when test="${userForm.addressForm.country=='CA'}"><c:forEach items="${candianTerritories}" var="prv"><c:if test="${prv.value eq userForm.addressForm.province}">${prv.label}</c:if></c:forEach>
</c:when><c:when test="${userForm.addressForm.country=='US'}"><c:forEach items="${states}" var="prv"><c:if test="${prv.value eq userForm.addressForm.province}">${prv.label}</c:if></c:forEach></c:when>					<c:otherwise>${userForm.addressForm.province}</c:otherwise></c:choose></c:set>
<c:forEach items="${countries}" var="prv"><c:if test="${prv.value eq userForm.addressForm.country}"><c:set var="country">${prv.label}</c:set></c:if></c:forEach> -->
<ul>
	<li>
        <label class="desc"><fmt:message key="userForm.username"/></label> <span class="value">${userForm.username}</span>
    </li>
    <li id="fullname">
		<div class="left">
        	<label class="desc"><fmt:message key="userForm.firstName"/></label> <span class="value">${userForm.firstName}</span>
		</div>
		<div>
        	<label class="desc"><fmt:message key="userForm.lastName"/></label> <span class="value">${userForm.lastName}</span>
		</div>
	</li>

    <li>
        <label class="desc email"><fmt:message key="userForm.email"/></label> <span class="value">${userForm.email}</span>
    </li>
    <li>
        <label class="desc"><fmt:message key="userForm.lastName"/></label> <span class="value">${userForm.lastName}</span>
    </li>
<%-- CONDITIONAL ADDRESS --%>
<c:if test="${not empty userForm.address}">
    <li>
        <div>
            <div class="left">
            <label class="desc"><fmt:message key="userForm.addressForm.phone"/></label>
            <span class="value"> (${userForm.addressForm.areacode}) ${userForm.addressForm.phone}</span>
                
            </div>
            <div><br clear="all"/>
            </div>
        </div>
    </li>
    <li>
        <strong class="desc"><fmt:message key="userForm.addressForm.address"/></strong>
        <div class="group">
            <div class="left">
				<label class="desc"><fmt:message key="userForm.addressForm.street1"/></label><span class="value">${userForm.addressForm.street1}</span>
            </div>
            <div>
                <label class="desc"><fmt:message key="userForm.addressForm.city"/></label> <span class="value">${userForm.addressForm.city}</span>
            </div>
            <div class="left">
				<label class="desc"><fmt:message key="userForm.addressForm.province"/></label>
				<span class="value">${province}</span>
            </div>
            <div>
               	<label class="desc"><fmt:message key="userForm.addressForm.postalCode"/></label> <span class="value">${userForm.addressForm.postalCode}</span>
			</div>
            <div class="left">
               	<label class="desc"><fmt:message key="userForm.addressForm.country"/></label>
               	<span class="value">${country}</span>
            </div>
       </div>
	</li>
</c:if>
<%-- END CONDITIONAL ADDRESS --%>
	<li>
		<label class="desc"><html:submit value="edit" styleClass="button" /></label>
    </li>
</ul>
</html:form>

<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="preferenceDetail.title"/></title>
<content tag="heading"><fmt:message key="preferenceDetail.heading"/></content>
<meta name="menu" content="PreferenceMenu"/>
<%@ include file="/WEB-INF/pages/user/tabs.jsp"%>
<html:form action="/user/savePreferences" method="post" styleId="preferenceForm">
<ul>

<html:hidden property="userId" value='${uid}'/>

    <li>
        <beathive2:label styleClass="desc" key="preferenceForm.format"/>
        <html:errors property="format"/>
<beathive2:menu property="format"
				listName="format"
				default="${preferenceForm.format}"/>
    </li>

    <li>
        <beathive2:label styleClass="desc" key="preferenceForm.hideOwned"/>
        <html:errors property="hideOwned"/>
        <html:checkbox property="hideOwned" styleId="hideOwned" styleClass="checkbox"/>

    </li>

    <li>
        <beathive2:label styleClass="desc" key="preferenceForm.hideFav"/>
        <html:errors property="hideFav"/>
        <html:checkbox property="hideFav" styleId="hideFav" styleClass="checkbox"/>

    </li>

    <li class="buttonBar bottom">
        <html:submit styleClass="button" property="method.save" onclick="bCancel=false">
            <fmt:message key="button.save"/>
        </html:submit>
    </li>
</ul>
</html:form>


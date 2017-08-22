<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="preferenceList.title"/></title>
<content tag="heading"><fmt:message key="preferenceList.heading"/></content>
<meta name="menu" content="PreferenceMenu"/>
<%@ include file="/WEB-INF/pages/user/tabs.jsp"%>
<html:form action="/user/savePreferences" method="post" styleId="preferenceForm" onsubmit="return validatePreferenceForm(this)">
<ul>

<html:hidden property="userId" value='${uid}'/>

    <li>
        <beathive2:label styleClass="desc" key="preferenceForm.format"/>
        <html:errors property="format"/>
        <html:text property="format" styleId="format" styleClass="text medium"/>

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

        <html:submit styleClass="button" property="method.delete" onclick="bCancel=true; return confirmDelete('Preference')">
            <fmt:message key="button.delete"/>
        </html:submit>

        <html:cancel styleClass="button" onclick="bCancel=true">
            <fmt:message key="button.cancel"/>
        </html:cancel>
    </li>
</ul>
</html:form>

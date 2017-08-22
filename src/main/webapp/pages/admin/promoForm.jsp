<%@ include file="/common/taglibs.jsp"%>

<title><fmt:message key="promoDetail.title"/></title>
<content tag="heading"><fmt:message key="promoDetail.heading"/></content>

<html:form action="/admin/savePromo" method="post" styleId="promoForm" onsubmit="return validatePromoForm(this)">
<ul>

<html:hidden property="id"/>

    <li>
        <beathive2:label styleClass="desc" key="promoForm.code"/>
        <html:errors property="code"/>
        <html:text property="code" styleId="code" styleClass="text medium"/>

    </li>

    <li>
        <beathive2:label styleClass="desc" key="promoForm.start"/>
        <html:errors property="start"/>
        <html:text property="start" styleId="start" styleClass="text medium"/>

    </li>

    <li>
        <beathive2:label styleClass="desc" key="promoForm.duration"/>
        <html:errors property="duration"/>
        <html:text property="duration" styleId="duration" styleClass="text medium"/>

    </li>

    <li>
        <beathive2:label styleClass="desc" key="promoForm.discount"/>
        <html:errors property="discount"/>
        <html:text property="discount" styleId="discount" styleClass="text medium"/>

    </li>

    <li class="buttonBar bottom">
        <html:submit styleClass="button" property="method.save" onclick="bCancel=false">
            <fmt:message key="button.save"/>
        </html:submit>

        <html:submit styleClass="button" property="method.delete" onclick="bCancel=true; return confirmDelete('Promo')">
            <fmt:message key="button.delete"/>
        </html:submit>

        <html:cancel styleClass="button" onclick="bCancel=true">
            <fmt:message key="button.cancel"/>
        </html:cancel>
    </li>
</ul>
</html:form>

<script type="text/javascript">
    Form.focusFirstElement($("promoForm"));
</script>

<html:javascript formName="promoForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

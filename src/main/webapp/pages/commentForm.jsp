<%@ include file="/common/taglibs.jsp"%>
<html:form action="/shop/saveComment" method="post" styleId="commentForm" onsubmit="return validateCommentForm(this)">
<html:hidden property="id"/>
<c:set var="now"><%=com.beathive.util.DateUtil.getDateTime(com.beathive.util.DateUtil.getDateTimePattern() , new java.util.Date())%></c:set>
<html:hidden property="entryTime" styleId="entryTime" styleClass="text medium" value="${now}"/>
<html:hidden property="postedByUsername" value="${username}" styleId="postedByUsername" styleClass="text medium"/>
<input type="hidden" name="storeId" styleId="userId" value="${param.storeId}"/>
<html-el:hidden property="userId" name="storeForm" styleId="userId" />
<html-el:hidden property="postedById" styleId="postedById" value="${uid}"/>

<ul>

    <li> <html:errors property="statement"/>
        <html:textarea property="statement" styleId="statement" styleClass="textarea" cols="20" rows="5"></html:textarea>
    </li>

    <li class="buttonBar bottom">
        <html:submit styleClass="button" property="method.save" onclick="bCancel=false">
            <fmt:message key="button.save"/>
        </html:submit>
    </li>
</ul>
</html:form>

<html:javascript formName="commentForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

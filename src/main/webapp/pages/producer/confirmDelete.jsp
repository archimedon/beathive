<%-- @ include file="/common/taglibs.jsp" --%>
<%@ include file="/common/var_contentType.jsp"%>
<div id="confirmBox">
	<html:form styleId="confirmDeleteForm" method="post" action="/producer/deleteLoop">
	<html:hidden property="storeId" value="${storeForm.id}"/>
	<html:hidden property="id" value="${param.id}"/>
	<html:hidden property="js" value="${param.js}"/>
	<html:hidden property="modal" value="false"/>
	<html:hidden property="type" value="${param.type}"/>
	<big class="messageOutput" style="font-size:16px;line-height: 20px; padding: 30px 0px 30px 0px;">Delete &#147;${param.name}&#148; ?</big>
	<p>
	<c:if test="${param.type eq 'trackpack'}">
	<input type="checkbox" name="deep" id="deep"/>
		<label for="deep">delete components?</label><br/>
	</c:if>
<br/>
<input type="submit" class="button"  id="dlink_${param.id}"value="Proceed!"/>
	</p>
	</html:form>
</div>

<%@ include file="/common/var_contentType.jsp"%>
<ul class="clipdescriptors">
<c:forEach items="${descriptors}" var="nm" varStatus="stat">
<c:if test="${not empty nm.value}"><c:choose><c:when test="${nm.key eq 'bpm'}"><c:set var="val" value="${nm.value}"/></c:when><c:otherwise><fmt:message var="val" key="${nm.value}"/></c:otherwise></c:choose><li><label><fmt:message key="loopSearchForm.descriptor.${nm.key}"/></label> : <b>${val}</b></li></c:if></c:forEach></ul>
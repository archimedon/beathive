<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/var_contentType.jsp"%>
<c:choose>
<c:when test="${param.cntry=='US'}">
<select name="province" id="province">
<c:forEach varStatus='stat' items="${states}" var="opt">
	<option value='<c:out value="${opt.value}"/>' <c:if test="${param.sel== opt.value}">selected='true'</c:if>><c:out value="${opt.label}"/></option></c:forEach>
</select>
</c:when>
<c:when test="${param.cntry=='CA'}">
<select name="province" id="province">
<c:forEach varStatus='stat' items="${candianTerritories}" var="opt">
	<option value='<c:out value="${opt.value}"/>' <c:if test="${param.sel== opt.value}">selected='true'</c:if>><c:out value="${opt.label}"/></option></c:forEach>
</select>
</c:when>
<c:otherwise>
<input type="text" name="province" id="province" value="" class="anyClassName"/>
</c:otherwise>
</c:choose>

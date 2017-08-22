<c:choose><c:when test="${fn:length(formats) > 1}">
<c:choose><c:when test="${choiceId != null}">
<select name="fileId" class="formatSwitch" size="1">
<c:forEach items="${formats}" var="formato" varStatus="stat">
<option class="${formato.formatId}Style" value="${formato.id}"<c:if test="${choiceId eq formato.id}"> selected="selected"</c:if>><fmt:message key="label.format.${formato.formatId}"/></option>
</c:forEach>
</select>
</c:when>
<c:otherwise><select name="fileId" class="formatSwitch" size="1">
<c:forEach items="${formats}" var="formato" varStatus="stat">
<option class="${formato.formatId}Style" value="${formato.id}"<c:if test="${choice eq formato.formatId}"> selected="selected"</c:if>><fmt:message key="label.format.${formato.formatId}"/></option>
</c:forEach>
</select></c:otherwise></c:choose>
</c:when><c:otherwise><c:forEach items="${formats}" var="formato" varStatus="stat" begin="0" end="1">
<label class="singleOpt"><span class="${formato.formatId}Style" selected="true"><fmt:message key="label.format.${formato.formatId}"/></span></label>
</c:forEach>
</c:otherwise></c:choose>

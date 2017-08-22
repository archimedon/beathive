<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
<c:when test="${param.cmd eq 'listUnpackedAudio'}">
<c:forEach items="${audioFiles}" var="audfile" varStatus="stat"><c:set var="sam" scope="request" value="${audfile}"/>
<c:import url="/component/loopsummary.jsp">
<c:param name="index" value="${stat.index}"/>
</c:import>
</c:forEach>
</c:when>
<c:when test="${param.cmd eq 'producerShops'}">
</c:when>
<c:when test="${param.cmd eq 'listClipAudio'}">
</c:when>
<c:otherwise></c:otherwise>
</c:choose>
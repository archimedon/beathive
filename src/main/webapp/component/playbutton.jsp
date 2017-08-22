<jsp:scriptlet><![CDATA[ String ctype = (request.getParameter("js")!=null) ? "text/plain;" : (request.getParameter("x")!=null ? "text/xml;" :  "text/html;");   response.setContentType(ctype + "charset=UTF-8"); ]]></jsp:scriptlet><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <c:url var='path' value='${param.path}'/>
 <c:url var='moviepath' value='/loopButton?path=${path}'/>
 <c:url var='embedpath' value='/loopButton.swf?path=${path}'/>	 
 <c:if test="${not empty param.color}"><c:set var="color" value="${param.color}"/></c:if>
 <c:if test="${empty color}"><c:set var="color"><c:choose><c:when test="${(param.num % 2) == 0}">#F3F0EB</c:when><c:otherwise>#E2DFDA</c:otherwise></c:choose></c:set></c:if>
<object id="o_<c:out value='${param.oid}'/>" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000_1" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" align="middle" height="22" width="22">
	<param name="movie" value="<c:out value='${moviepath}' escapeXml='false'/>" />
	<param name="bgcolor" value="${color}"/>
	<param name="allowScriptAccess" value="always"/>
	<param name="quality" value="high"/>
	<param name="autostart" value="true"/>
	<embed id="e_<c:out value='${param.oid}'/>" src="<c:out value='${embedpath}' escapeXml='false'/>" autostart="true" bgcolor="${color}" quality="high"  swLiveConnect="true"	name="sndPlayer" 	align="middle" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22"/>
</object>

<%@ include file="/common/taglibs.jsp"%>
<table class="dirtree">
  <thead>
    <tr>
      <th width="250px"></th>
      <th>Formats</th>
      <th>Instrument/# Loops</th>
      <th>Genre(s)</th>
      <th>BPM</th>
      <th>Price (USD)</th>
    </tr>
  </thead>
  <tbody id="assetlist"><c:set var="nodePtr" value="0"/>

 <c:forEach items="${dirlist}" var="clip" varStatus="stat">
	 <c:choose>
	 <c:when test="${fn:contains(clip.class.name , 'Trackpack')}"> 
		 <tr id="node-${clip.id}">
<html:form action="/producer/saveLoopInfo.html?method=details">
	<td nowrap="nowrap" width="250px">	
<html:text property="loopId" value="${clip.id}"/>
<span class="folder">

	<c:if test="${not empty clip.audioFormat}"><c:forEach items="${clip.audioFormat}" var="sam" varStatus="stat" end="0">
			  <c:url var='moviepath' value='/loopButton?path=${fn:trim(sam.samplePath)}'/>
			  <c:url var='embedpath' value='/loopButton.swf?path=${fn:trim(sam.samplePath)}'/><embed src="${embedpath}" bgcolor="#f2f0ea" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22"/>
	</c:forEach></c:if> ${clip.name}</span></td>
			  <td><c:if test="${not empty clip.audioFormat}"><c:set var="formatlist" value="${clip.audioFormat}" scope="request"/><c:import url="/WEB-INF/pages/listcomponents/fileformats.jsp"/></c:if></td>
			  <td></td>
			  <td><c:set var="genrelist" value="${clip.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
			  <td><html:text property="attrib(bpm)" value="${clip.bpm}" onchange="this.form.submit();"/></td>
			  <td>${clip.price.amount}</td>
</html:form></tr>
	<c:forEach items="${clip.loops}" var="loop">
			<tr id="node-${loop.id}" class="child-of-node-${clip.id}">
<html:form action="/producer/saveLoopInfo.html?method=details">
			  <td nowrap="nowrap" width="200px">	
<html:text property="loopId" value="${loop.id}"/>
<span class="file">
			   <c:forEach items="${loop.audioFormat}" var="sam" varStatus="stat" end="0">
			  <c:url var='moviepath' value='/loopButton?path=${sam.samplePath}'/>
			  <c:url var='embedpath' value='/loopButton.swf?path=${sam.samplePath}'/>
<object id="bh-loop_<c:out value='${param.index}'/>" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000_1" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" align="middle" height="22" width="22">
	<param name="movie" value="<c:out value='${moviepath}' escapeXml='false'/>">
	<param name="bgcolor" value="#f2f0ea">
	<param name="allowScriptAccess" value="always">
	<param name="quality" value="high">
	<embed src="<c:out value='${embedpath}' escapeXml='false'/>" bgcolor="#f2f0ea" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22"/>
</object></c:forEach>		    ${loop.name}</span></td>
			  <td><c:if test="${not empty loop.audioFormat}"><c:set var="formatlist" value="${loop.audioFormat}" scope="request"/><c:import url="/WEB-INF/pages/listcomponents/fileformats.jsp"/></c:if></td>
			  <td></td>
			  <td><c:set var="genrelist" value="${loop.genre}" scope="request"/><c:import url="/WEB-INF/pages/listcomponents/genres.jsp"/></td>
			  <td><html:text property="attrib(bpm)" value="${loop.bpm}" onchange="this.form.submit();"/></td>
			  <td>${loop.price.amount}</td>
</html:form>
</tr>
		 </c:forEach>
	 </c:when>
	 <c:otherwise>
		 <tr id="node-${clip.id}">
<html:form action="/producer/saveLoopInfo.html?method=details">
		  <td width="250px"><span class="file">
	<c:if test="${not empty clip.audioFormat}"><c:forEach items="${clip.audioFormat}" var="sam" varStatus="stat" end="0">
			  <c:url var='moviepath' value='/loopButton?path=${fn:trim(sam.samplePath)}'/>
			  <c:url var='embedpath' value='/loopButton.swf?path=${fn:trim(sam.samplePath)}'/><embed src="${embedpath}" bgcolor="#f2f0ea" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22"/>
	</c:forEach></c:if> ${clip.name}</span></td>
			  <td><c:if test="${not empty clip.audioFormat}"><c:set var="formatlist" value="${clip.audioFormat}" scope="request"/><c:import url="/WEB-INF/pages/listcomponents/fileformats.jsp"/></c:if></td>
			  <td>${clip.instrument}</td>
			  <td><c:set var="genrelist" value="${clip.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
			  <td><html:text property="attrib(bpm)" value="${clip.bpm}" onchange="this.form.submit();"/></td>
			  <td>${clip.price.amount}</td>
</html:form>
</tr>
</c:otherwise>
	</c:choose>
 </c:forEach>
 	<tr id="node-999999">
			  <td><span></span></td>
			  <td></td>
			  <td></td>
			  <td></td>
			  <td></td>
			  <td></td>
		   </tr>

  </tbody>
</table>

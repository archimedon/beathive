<style type="text/css">
div.embd{
	width: 170 !important;
	height: 125;
	margin: 15px;
	font-size: 9px;
	display: inline;
	float: left;
	border: 1px solid #bbccbb;
	overflow: hidden;
}
.embd li{
	line-height: 13px;
}
</style><%@ include file="/common/taglibs.jsp"%>
<c:forEach items="${clipAudioFiles}" var="sam" varStatus="stat">
<c:url var='moviepath' value='/loopButton?path=${sam.samplePath}'/>
<c:url var='embedpath' value='/loopButton.swf?path=${sam.samplePath}'/>
<li><div class="embd" style="width: 170 !important;height: 125;font-size: 9px;display: inline-block;float: left;overflow: hidden;">

<div style="position:relative; top:0; float: left;"><object id="bh-loop_<c:out value='${stat.index}'/>" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000_1" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0" align="middle" height="22" width="22">
	<param name="movie" value="<c:out value='${moviepath}' escapeXml='false'/>">
	<param name="bgcolor" value="#f2f0ea">
	<param name="allowScriptAccess" value="always">
	<param name="quality" value="high">
	<embed src="<c:out value='${embedpath}' escapeXml='false'/>" bgcolor="#f2f0ea" quality="high" allowscriptaccess="always" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" height="22" width="22">
</object>
</div>
</li>
<c:set var="filename" value='${sam.fileRef}' scope="page"/>
<%
String filename = (String)pageContext.getAttribute("filename");
int ptr = filename.lastIndexOf("/");
filename = filename.substring(ptr + 1);
pageContext.setAttribute("filename" , filename);
%>
id:&nbsp;&nbsp;<b><c:out value='${sam.id}' escapeXml='false'/></b><br/>
storeId:&nbsp;&nbsp;<b><c:out value='${sam.storeId}' escapeXml='false'/></b><br/>
filename:&nbsp;&nbsp;<b><c:out value='${filename}' escapeXml='false'/></b><br/>
formatId:&nbsp;&nbsp;<b><c:out value='${sam.formatId}' escapeXml='false'/></b><br/>
clipId:&nbsp;&nbsp;<b><c:out value='${sam.clipId}' escapeXml='false'/></b><br/>
sampleSize:&nbsp;&nbsp;<c:out value='${sam.sampleSize}' escapeXml='false'/><br/>
sampleRate:&nbsp;&nbsp;<c:out value='${sam.sampleRate}' escapeXml='false'/><br/>

</div></li>
</c:forEach>
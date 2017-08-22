<%@ include file="/common/taglibs.jsp"%><%@page import="com.beathive.webapp.util.RequestUtil, java.util.*,java.io.*" %>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.producer.trackpackinventory"><fmt:param value="${storeForm.name}"/></fmt:message></title>
    <content tag="heading"><fmt:message key="menu.producer.loopinventory"/></content>
   <meta name="menu" content="ProducerHomeIn"/>
<script type= "text/javascript">/*<![CDATA[*/
SWFURL = "<c:url value='/loopButton.swf'/>";
INSTALLPATH = "/scripts/swfobject/expressInstall.swf";
DETAIL_URL='<c:url value="/producer/saveLoopInfo.html?method=details"/>';

$(function(){
	$('#help').tooltip({ 
		delay: 0, 
		showURL: false, 
		bodyHandler: function() { 
			gurl = $(this).attr("href");
			var vhtml = $.ajax({url: gurl,async: false}).responseText;
        	return vhtml; 
    	}
	});
});

/*]]>*/
</script>
</head>
<body id="producerinventory">

<div id="cons"></div>


<div class="resultBox">

<html:form action="/producer/saveLoopInfo.html?method=details"></html:form>

<display:table name="${storetrackpackPager}"  htmlId="producerTrackpacks" cellspacing="0" cellpadding="0" keepStatus="true" id="tpclip" class="loopList producercliptable" requestURI="">
	<display:caption>
	<form id="trackpackUploadForm" class="uploader" method="post" action="/zazz/producer/sampleAudio.html" ref='<c:url value="/producer/editLoop.html"/>' enctype="multipart/form-data">
<input type="hidden" name="username" value="${pageContext.request.remoteUser}"/>
<input type="hidden" name="storeId" value="${storeForm.id}"/>
<input type="hidden" name='method' value="up"/>
<input type="hidden" name='js' value="1"/>
	<strong for="uploadin" class="uploadOutput"><fmt:message key="menu.producer.uploadtrackpack"/></strong>
	<input class="button" size='4' ref="uploadOutput" id="uploadin"  onchange="uploadFile(this , ['<fmt:message key="error.upload.ziprequired"/>']);" name="uploads[0]" type="file">
	
	
</form><%--	<a href="/component/uploadhelp.jsp" id="help">help</a> --%>

</display:caption>

	<%-- COL: 1 - OPEN Trackpack button .--%>
	<display:column class="expandCell" headerClass="expandHeaderClass">
	  <c:set var="compUrl">/producer/viewComponents.html?clipId=${tpclip.id}</c:set>
	<a id="op_open_${tpclip.id}" class="expand" title="[ Component Loops ]" name="a_${tpclip.id}" href="<c:url value='${compUrl}'/>" ref="${tpclip.id}"><div class="expandButton">&nbsp;</div></a>
	</display:column>
	
    <%-- COL: 0 - loop ready/not ready cell --%>
	<display:column titleKey="soundClipForm.ready" sortProperty="ready" sortable="false" headerClass="readyHeaderClass"><div class="${tpclip.ready}Ready" id="stat_${tpclip.id}">&nbsp;</div></display:column>
	
	
	
	<%-- COL: 2 - playbuttons.--%>
	<display:column class="compLoops" headerClass="playbuttonHeaderClass">
	<table class="swfarea" ref="emb_${tpclip.id}" id="${tpclip.id}"><tr>
		<c:forEach items="${tpclip.audioFormat}" var="loopsam" varStatus="stat">
		<td>
			<div class="player"  id="swf_${tpclip.id}_${loopsam.formatId}">
				<c:import url="/component/playbutton.jsp">
					<c:param name="path" value="${loopsam.samplePath}"/>
					<c:param name="oid" value="${tpclip.id}_${loopsam.formatId}"/>
					<c:param name="js" value="1"/>
				</c:import>
				</div>
				${loopsam.formatId}
			</td>
		</c:forEach></tr></table>
		<%-- clip data as JS array --%>
		<script type="text/javascript">//<![CDATA[
		window.CLIPS[${tpclip.id}]=${tpclip.JSON};
		//]]></script>
	</display:column>
	<display:column titleKey="soundClipForm.name" class="nameCell" headerClass="nameHeaderClass" sortProperty="name" sortable="true">
		<input name="attrib(name)" type="text" id="clipName_${tpclip.id}" value="${tpclip.name}" class="attrib" size="25"/>
		<label class="loop_attribs" id="edit_${tpclip.id}" ref="emb_${tpclip.id}">edit</label>
	</display:column>
	<display:column title="component" headerClass="instrumentHeaderClass"><bean:size id="numLoops" property="loops" name="tpclip"/><span class="number">${numLoops}</span>/<%-- label for="loopsExpected_${tpclip.id}"><fmt:message key="menu.producer.numcomponents"/></label --%><input type="text" name="attrib(loopsExpected)"  id="loopsExpected_${tpclip.id}" value="${tpclip.loopsExpected}" class="attrib" size="2"/></display:column>
    <display:column titleKey="soundClipForm.genre" class="genreCell" headerClass="genreHeaderClass"><c:set var="genrelist" value="${tpclip.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></display:column>
    <display:column titleKey="soundClipForm.bpm" class="bpmCell" headerClass="bpmHeaderClass">
    	<input type="text" name="attrib(bpm)" value="${tpclip.bpm}" class="attrib" size="4"/>
    </display:column>
    
    <display:column titleKey="soundClipForm.price" class="priceCell" headerClass="priceHeaderClass">
	    <fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${tpclip.priceForm.amount}" var="amt"/>
	    <input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4"/>
	</display:column>
 
<%--    
<display:column title="created" property="created" sortable="true"/>
--%>
<display:column titleKey="soundClipForm.timesSold" property="timesSold" sortable="true" headerClass="salesHeaderClass"/>
	<display:column titleKey="soundClip.created" class="createdCell" sortProperty="created" property="created" sortable="true" headerClass="createdHeaderClass"/>
	
	
<display:column headerClass="deleteHeaderClass" class="rem_${tpclip.id}"><c:url var="dlink" value='/producer/confirmDelete.html'>
<c:param name="id" value="${tpclip.id}"/>
<c:param name="name" value="${tpclip.name}"/>
<c:param name="height" value="150"/>
<c:param name="width" value="300"/>
<c:param name="js" value="1"/>
<c:param name="modal" value="false"/>
<c:param name="type" value="trackpack"/></c:url><a class="thickbox" href="${dlink}"><html:img page="/images/BUTTONS/trash.gif" titleKey="soundClipForm.delete" width="15px"/></a></display:column>
	
	<display:setProperty name="sort.amount" value="list"/>
	<display:setProperty name="basic.show.header" value="true"/>
    <display:setProperty name="paging.banner.item_name" value="track pack"/>
    <display:setProperty name="paging.banner.items_name" value="track packs"/>
</display:table>


</div>
<iframe name="upload_target" id="upload_target" src="" style="position:absolute;left:-1000px;top:-1000px;width:0;height:0;border:1px solid #fff;"></iframe>
</body>
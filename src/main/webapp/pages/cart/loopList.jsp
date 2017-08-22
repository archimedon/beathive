<%@ include file="/common/taglibs.jsp"%><%@page import="com.beathive.webapp.util.RequestUtil, java.util.*,java.io.*" %>
<c:choose>
<c:when test="${not empty param.js}"></c:when>
<c:otherwise>
<script type="text/javascript">
/* clips structure -
 {	1 : { loop_attrib1:'atribval1', loop_attrib2:'atribval2', ... , swfs:{aif:'/path/to/swf' , wav:'/path/to/swf'} } ,
	2 : { ... }  ,
	...
 }
*/
LOOP_WIDTH = LOOP_HEIGHT = "22";
FLVER = "6.0.65"; //"6.0.65"
SWFURL = "<c:url value='/loopButton.swf'/>";
INSTALLPATH = "/scripts/swfobject/expressInstall.swf";

/**
 * adds or replaces the SWF on the page
 */
function loadSwf(path , clipid, frmid){
	var suffix = clipid +"_"+frmid;	
	var swfid = "swfobj_"+suffix;
	var target = "swf_"+suffix;
	var bgcolor	=	(frmid=='wav' ? '#1122ee': '#f2f0ea') ;
	
	// if the button already exists
	if (document.getElementById(swfid) != null){
		// remove it
		$('#'+swfid).remove();
	}
	// if the target does not exist
	if (document.getElementById(target) == null){
		// if the new upload is a wav
		if (frmid=='wav'){
			// then either append the 'target' div
			$($(".swfarea[ref='emb_"+clipid +"']")[0]).append( '<div id="' +target+'"/>');
		}else{
			// or prepend it
			$($(".swfarea[ref='emb_"+clipid +"']")[0]).prepend( '<div id="' +target+'"/>');
		}
	}	
	// create and target button
<%-- swfobject.embedSWF("the.swf", "destObjID", "300", "120", "9.0.0","expressInstall.swf", flashvars, params, attributes); --%>
	swfobject.embedSWF(SWFURL, target , LOOP_WIDTH , LOOP_HEIGHT, FLVER, INSTALLPATH ,
		{'path':escape(path) },						//sound path
		{'bgcolor':bgcolor}, 				// color
		{id: swfid , name:swfid}			// object's id
	); 
}

$(document).ready(function(){	
	// load clips
	for (var cjid in clips){
		with (clips[cjid]){
			for (var frmid in swfs){
				loadSwf(swfs[frmid] , cjid, frmid);
			}
		}
	}
});

function gotoPage(num , optURL){
	var loc =  optURL || window.location.href;
	var locpts = loc.split('?');
	var qp = qparam(locpts[1]);
	qp['page'] = num+'';
	window.location.href=locpts[0]+'?'+qstr(qp);
}

function qparam(str){
	var ret = {};
	var ary = str.split('&');
	var tstr = [];
	for (var t in ary){
		tstr = ary[t].split('=');
		ret[tstr[0]]=tstr[1];
	}
	return ret;
}
function qstr(param){
	var ary = [];
	for (var g in param){
		ary.push(g+'='+param[g]);
	}
	return ary.join('&');
}

function gotoSize(num , reset){
	var loc = window.location.href;
	var locpts = loc.split('?');
	var qp = qparam(locpts[1]);
	qp['size'] = num+'';
	loc = locpts[0]+'?'+qstr(qp);;
	if(reset){
	 	gotoPage(0 , loc);
	}else{
		window.location.href=loc
	}
}
</script>

</head>

<div id="workspace">
	<table class="dirtree" border="1" id="dirtreeTable">
		<caption align="top" id="details"></caption>
		<tr height="25px">
			<td height="25px" colspan="6"><h3 align="center" height="25px">Loops</h3></td>
		</tr>
		<thead>
		<tr>
			<th class="smallcell"></th>
			<th width="150px"><span>&nbsp;</span></th>
			<th>Instrument</th>
			<th>Genre(s)</th>
			<th>BPM</th>
			<th>Price (USD)</th>
		</tr>
		</thead>
		<tbody id="looplist">
		<c:set var="nodePtr" value="0"/>

<c:forEach items="${userCart}" var="item" varStatus="stat">
<c:set var="clip" value="${item.soundClipForm}"/>
<tr ref="emb_${clip.id}" id="node-${clip.id}" class="looprow">
<html:form action="/producer/saveLoopInfo.html?method=details">
<html:hidden property="loopId" value="${clip.id}"/>
<c:choose>
	<c:when test="${! clip.ready}">
		<c:set var="lstyle">loopStat</c:set>
	</c:when>
	<c:otherwise>
		<c:set var="lstyle">loopStatOff</c:set>
	</c:otherwise>
</c:choose>
<td class="smallcell"><div class="lefttab ${lstyle}"></div></td>
<td nowrap="nowrap"><span class="file">${stat.count + (storeloopQueryMeta.currIndex * storeloopQueryMeta.fetchSize)} .<c:if test="${not empty clip.audioFormat}">
	<c:set var="formatlist" value="${clip.audioFormat}" scope="request"/>
	<c:set var="cid" value="${clip.id}" scope="request"/>
	<%-- 	BEGIN IMPORT BUTTON		--%>
	<div class="swfarea" ref="emb_${cid}" valign="bottom"><c:forEach items="${formatlist}" var="loopsam">
<div  id="swf_${cid}_${loopsam.formatId}" ></div></c:forEach></div>
	<%-- 	END IMPORT BUTTON		--%>
</c:if><html:text property="attrib(name)" value="${clip.name}" styleClass="attrib" size="25"/>
		<label class="loop_attribs" id="edit_${clip.id}"  id="edit_${clip.id}" ref="emb_${clip.id}">edit</label></span>
	</td>
	<%-- td>
		<c:if test="${not empty clip.audioFormat}">
			<c:import url="/WEB-INF/pages/listcomponents/fileformats.jsp"/>
		</c:if>
	</td --%>
	<td class="flippa" ref="instrumentgroup"><c:choose>
		<c:when test="${not empty clip.instrument}">
			<input class="attrib" type="text" name="gid=${clip.instrument.groupId}&instrumentId=${clip.instrument.id}&id=${clip.id}" value="<fmt:message key="${clip.instrument.labelKey}"/>"/>
		</c:when>
		<c:otherwise>
			<span name="id=${clip.id}"><html:img page="/images/exclamation.jpg" height="17px"/></span>
		</c:otherwise></c:choose>
	</td>
	<td><c:set var="genrelist" value="${clip.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
	<td><html:text property="attrib(bpm)" value="${clip.bpm}" styleClass="attrib" size="4"/></td>
	
	<td><fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${clip.priceForm.amount}" var="amt"/>
		<input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4">
	</td>
</html:form>
</tr>
<c:set var="scriptStr" value='${scriptStr} clips["${clip.id}"]=${clip.JSON};'  scope="session"/>
<%-- c:set var="dataloop" scope="request" value="${clip}"/>	
<@ include file="/component/producer/rawLoopData.jsp" --%>
 </c:forEach>
		
		</tbody>
<tr><td colspan="6"></td></tr>
	</table>
	<script type="text/javascript">
	<c:out value="${scriptStr}" escapeXml="false"/>
	</script><c:remove var="scriptStr" scope="session"/>
</div>
</c:otherwise></c:choose>
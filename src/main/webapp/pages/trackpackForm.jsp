<jsp:scriptlet><![CDATA[ String ctype = ((request.getParameter("js")!=null) ||  (request.getParameter("dat")!=null)) ? "text/plain;" : (request.getParameter("x")!=null ? "text/xml;" :  "text/html;");   response.setContentType(ctype + "charset=UTF-8"); ]]></jsp:scriptlet><%@ include file="/common/taglibs.jsp"%><c:choose><c:when test="${not empty param.dat}">${loopForm.JSON}</c:when>
<c:otherwise><tr>
<td class="expandCell"><c:set var="compUrl">/producer/viewComponents.html?clipId=${loopForm.id}</c:set>
	<a id="op_open_${loopForm.id}" class="expand" title="[ Component Loops ]" name="a_${loopForm.id}" href="<c:url value='${compUrl}'/>" ref="${loopForm.id}"><div class="expandButton">&nbsp;</div></a>
</td>
<td><div class="falseReady" id="stat_${loopForm.id}">&nbsp;</div></td>
<td class="compLoops">
	<table class="swfarea" ref="emb_${loopForm.id}" id="${loopForm.id}">
	<tr><c:forEach items="${loopForm.audioFormat}" var="loopsam" varStatus="stat">
		<td>
			<div class="player" id="swf_${loopForm.id}_${loopsam.formatId}">
				<c:import url="/component/playbutton.jsp">
					<c:param name="path" value="${loopsam.samplePath}"/>
					<c:param name="oid" value="${loopForm.id}_${loopsam.formatId}"/>
					<c:param name="js" value="1"/>
				</c:import>
			</div>
			${loopsam.formatId}
		</td>
	</c:forEach>
	</tr>
	</table><script type="text/javascript">//<![CDATA[		window.CLIPS[${loopForm.id}]=${loopForm.JSON};//]]>
	</script>
	<c:set var="tpclip" value="${loopForm}"/>
	<div style="display:none" id="pack_loops_${tpclip.id}">
		<%@ include file="/WEB-INF/pages/producer/innerLoopList.jsp"%>
	</div>
</td>
<td class="nameCell">
<input name="attrib(name)" type="text" id="clipName_${loopForm.id}" value="${loopForm.name}" class="attrib" size="25"/>
<label class="loop_attribs" id="edit_${loopForm.id}" ref="emb_${loopForm.id}">edit</label>
</td>
	<td><bean:size id="numLoops" property="loops" name="loopForm"/><span class="number">${numLoops}</span>/<input type="text" name="attrib(loopsExpected)"  id="loopsExpected_${loopForm.id}" value="${loopForm.loopsExpected}" class="attrib" size="2"/></td>
    <td class="genreCell"><c:set var="genrelist" value="${loopForm.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
    <td class="bpmCell">
    	<input type="text" name="attrib(bpm)" value="${loopForm.bpm}" class="attrib" size="4"/>
    </td>
    <td class="priceCell">
	    <fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${loopForm.priceForm.amount}" var="amt"/>
	    <input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4"/>
	</td>
<td>${loopForm.timesSold}</td>
	<td class="createdCell">${loopForm.created}</td>
<td><a href="<c:url value='/producer/deleteLoop.html?storeId=${storeForm.id}&id=${loopForm.id}'/>" onclick="return arm(this ,{'name':'${loopForm.name}' ,'type':'Track Pack'});"><html:img page="/images/BUTTONS/trash.gif" titleKey="soundClipForm.delete" width="20px"/></a></td>
</tr></c:otherwise>
</c:choose>
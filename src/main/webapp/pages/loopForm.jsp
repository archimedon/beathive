<jsp:scriptlet><![CDATA[ String ctype = ((request.getParameter("js")!=null) ||  (request.getParameter("dat")!=null)) ? "text/plain;" : (request.getParameter("x")!=null ? "text/xml;" :  "text/html;");   response.setContentType(ctype + "charset=UTF-8"); ]]></jsp:scriptlet><%@ include file="/common/taglibs.jsp"%><c:choose><c:when test="${not empty param.dat}">${loopForm.JSON}</c:when><c:when test="${param.type=='trackpack'}"><tr>
	<td style="width:10px;vertical-align:middle;"><div class="falseReady" id="stat_${loopForm.id}">&nbsp;</div></td>
	<td style="width:15px" class="pack_func" headerClass="pack_func_header">
		<a class="opener" ref="${loopForm.id}" id="op_open_${loopForm.id}" href="./?ffv=1">&gt;</a>
	</td>
	<td style="width: 50px;">
		<table class="swfarea" ref="emb_${loopForm.id}" id="${loopForm.id}">
			<tbody>
				<tr>
					<c:forEach items="${loopForm.audioFormat}" var="loopsam" varStatus="stat">
						<td>
							<div class="player" id="swf_${loopForm.id}_${loopsam.formatId}">
								<c:import url="/component/playbutton.jsp">
									<c:param name="path" value="${loopsam.samplePath}"/>
									<c:param name="oid" value="obj_bh-loop_${loopForm.id}"/>
									<c:param name="js" value="1"/>
								</c:import>
							</div>
							${loopsam.formatId}
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table><script type="text/javascript">//<![CDATA[
		window.CLIPS[${loopForm.id}]=${loopForm.JSON};
		//]]>
		</script>
		<c:set var="tpclip" value="${loopForm}"/>
		<div style="display:none" id="pack_loops_${tpclip.id}">
			<%@ include file="/WEB-INF/pages/producer/innerLoopList.jsp"%>
		</div>
	</td>
<td style="width: 200px;">
<input type="text" name="attrib(name)" value="${loopForm.name}" class="attrib" size="25"/>
<label class="loop_attribs" id="edit_${loopForm.id}"  ref="emb_${loopForm.id}">edit</label>
	</td>
<td><label for="loopsExpected_${tpclip.id}">number of components: <input type="text" name="attrib(loopsExpected)"  id="loopsExpected_${tpclip.id}" value="${tpclip.loopsExpected}" class="attrib" size="2"/></label></td>
<td><c:set var="genrelist" value="${loopForm.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
    <td><input type="text" name="attrib(bpm)"value="${loopForm.bpm}" class="attrib" size="4"/></td>
    
	<td>
		<fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${loopForm.priceForm.amount}" var="amt"/>
    	<input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4"/>
    </td>
<td>0</td>
</tr>
</c:when>

<c:otherwise><tr>
	<td style="width:10px;vertical-align:middle;"><div class="falseReady" id="stat_${loopForm.id}">&nbsp;</div></td>
	<td style="width: 50px;">
		<table class="swfarea" ref="emb_${loopForm.id}" id="${loopForm.id}">
			<tbody>
				<tr>
					<c:forEach items="${loopForm.audioFormat}" var="loopsam" varStatus="stat">
						<td>
							<div class="player" id="swf_${loopForm.id}_${loopsam.formatId}">
								<c:import url="/component/playbutton.jsp">
									<c:param name="path" value="${loopsam.samplePath}"/>
									<c:param name="oid" value="obj_bh-loop_${loopForm.id}"/>
									<c:param name="js" value="1"/>
								</c:import>
							</div>
							${loopsam.formatId}
						</td>
					</c:forEach>
				</tr>
			</tbody>
		</table><script type="text/javascript">//<![CDATA[
		window.CLIPS[${loopForm.id}]=${loopForm.JSON};
		//]]>
		</script>
	</td>
<td style="width: 200px;">
<input type="text" name="attrib(name)" value="${loopForm.name}" class="attrib" size="25"/>
<label style="color: brown; size:10" class="loop_attribs" id="edit_${loopForm.id}"  ref="emb_${loopForm.id}">edit</label>
	</td>
<td class="instrumentgroup" >
	  <c:choose><c:when test="${not empty loopForm.instrument}">
		<input class="attrib" type="text" name="gid=${loopForm.instrument.groupId}&instrumentId=${loopForm.instrument.id}&id=${loopForm.id}" value="<fmt:message key="${loopForm.instrument.labelKey}"/>"/>
		</c:when><c:otherwise><span name="id=${loopForm.id}"><input type="text" class="errorReq" size="25" value="Select Instrument"/></span></c:otherwise></c:choose>
		</td>
<td class="genreCell"><c:set var="genrelist" value="${loopForm.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></td>
    <td><input type="text" name="attrib(bpm)"value="${loopForm.bpm}" class="attrib" size="4"/></td>
    
	<td>
		<fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${loopForm.priceForm.amount}" var="amt"/>
    	<input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4"/>
    </td>
<td>0</td>

	<td class="createdCell">${loopForm.created}</td>
<td><html:link action="/producer/deleteLoop" onclick="return arm(this ,'loop');" paramName="loopForm" paramId="id" paramProperty="id"><html:img page="/images/BUTTONS/trash.gif" titleKey="soundClipForm.delete" width="20px"/></html:link></td>


</tr>
</c:otherwise>
</c:choose>
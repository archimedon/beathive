<%@ include file="/common/taglibs.jsp"%><c:choose><c:when test="${param.type == 'form'}">
<script type="text/javascript">
$(document).ready(function(){
	var loopId = '${param.id}';
	// "done" button clicked
	$('.done').click(function(){
		var field = this;
		var par = $(field.form).parents('tr')[0];
		var refval = $(par).attr('ref');
		var str = '';
		params ={};
		$(field.form).find("select,[type='hidden']").each(function(i){
			params[this.name]=$(this).val();
			
			str += this.name +":" +$(this).val();
		});
		
		$.get(field.form.action, params ,function(data){
			var dat = {};
			eval ('dat='+ $.trim(data) +';');
			
			for(entry in dat){
				window.CLIPS[loopId][entry]=dat[entry];
			}
			var editBut = '#edit_' + loopId;

			// make fadeOut effect shorter 
			//$.unblockUI({ fadeOut: 200 }); 
			$(editBut).trigger('click');
		});
		return false;
	});
	
	
	box = null;
	btxt = $('#uploadbutton').text();
	
	new Ajax_upload('#uploadbutton', {
		//action: 'upload',
		action: '/zazz/producer/sampleAudio.html', 
		name: 'uploads[0]',
		// Additional data to send
		data: {
			username: "<c:out value='${pageContext.request.remoteUser}'/>",
			storeId:	"${storeForm.id}",
			clipId: "${param.id}", 
			method: "over"
		  },
		onSubmit : function(file , ext){
			if (ext && /^(aif|aiff|wav)$/.test(ext)){

				$('#uploadbutton').text('Uploading ' + file);
				box = $($('#uploadbutton').parents("form#loopInfo")[0]).parent();
				
				$(box).block({ message: "Processing" }); 
       
			} else {
				
				// extension is not allowed
				$('#uploadwrap .text').text('Error: only AIF or WAV files are allowed');
				// cancel upload
				return false;				
			}
	
		},
		onComplete : function(file, response) {
//		alert(response);
$('#uploadbutton').text(btxt);
dat = {};
eval ('dat='+$.trim(response).replace(/=/g,":"));
	loadSwf(dat.samplePath , dat.clipId, dat.formatId);
			$('#uploadwrap .text').text('audio['+ dat.clipId+'] updated');				
$(box).unblock(); 
//$('tr#node-${param.id}').unblock(); 
		}		
	});
	
});
</script>
<html:form action="/producer/saveLoopInfo.html?method=details" styleId="loopInfo"><html:hidden property="loopId" value="${param.id}"/>
<table border='0' id="attribsTable">
<caption align="top"></caption>
<tr id="uploadwrap">
<td class="text">Overwrite AIF or <span>WAV</span></td>
<td width="110" nowrap="nowrap"><a id="uploadbutton">click to upload</a></td>
<td colspan="2" align="right"><button class="done">done</button></td>
</tr>

<tr>
		<td>
			<label for="keynote">
				<fmt:message key="soundClipForm.keynote"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(keynote)"
					listName="keynote"
					promptKey="prompt.notApplicable"
					default="${param.keynote}"
				/>
		</td>
		<td>
			<label for="scale">
				<fmt:message key="soundClipForm.scale"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(scale)"
					listName="scale"
					promptKey="prompt.notApplicable"
					default="${param.scale}"
				/>
		</td>
    </tr>
	<tr>
		<td>
			<label for="timesignature">
				<fmt:message key="soundClipForm.timesignature"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(timesignature)"
					listName="timesignature"
					promptKey="prompt.notApplicable"
					default="${param.timesignature}"
				/>
		</td>
		<td><label for="searchable"><fmt:message key="soundClipForm.searchable"/></label><span class="RequiredFormField">**</span></td>
			 <td>
			<select name="attrib(searchable)">
			<option value="false" <c:if test="${not param.searchable}">selected='true'</c:if>>Hidden</option>
			<option value="true" <c:if test="${param.searchable}">selected='true'</c:if>>Visible</option>
			</select></td>
</tr>

	<tr>
		<td>
			<label for="passageMenu">passage
				<fmt:message key="soundClipForm.passage"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(passage)"
					listName="passage"
					promptKey="prompt.notApplicable"
					default="${param.passage}"
				/>
		</td>
		<td>
			<label for="energyMenu">
				<fmt:message key="soundClipForm.energy"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(energy)"
					listName="energy"
					promptKey="prompt.notApplicable"
					default="${param.energy}"
				/>
		</td>
    </tr>
 
 	<tr>
		<td>
			<label for="feelMenu">
				<fmt:message key="soundClipForm.feel"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(feel)"
					listName="feel"
					promptKey="prompt.notApplicable"
					default="${param.feel}"
				/>
		</td>
		<td>
			<label for="sonorityMenu">
				<fmt:message key="soundClipForm.sonority"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(sonority)"
					listName="sonority"
					promptKey="prompt.notApplicable"
					default="${param.sonority}"
				/>
		</td>
    </tr>
  
 	<tr>
		<td>
			<label for="originMenu">
				<fmt:message key="soundClipForm.origin"/>
	        </label>
        </td>
        <td>
				<beathive2:menu 
					property="attrib(origin)"
					listName="origin"
					promptKey="prompt.notApplicable"
					default="${param.origin}"
				/>
		</td>
		<td>
		</td>
    </tr>
<tr><td colspan="4" align="right"><button class="done">done</button></td></tr>

</table></html:form></c:when><c:otherwise>
<%-- edit CSS(#infoTemplate) CSS(.extraInfo) is used elsewhere--%>
<div id="infoTemplate">
<c:forTokens items="keynote,scale,timesignature,searchable,passage,energy,feel,sonority,origin" delims="," var="tok">
<c:choose>
<c:when test="${tok =='searchable'}"><fmt:message key="loopSearchForm.descriptor.${tok}"><fmt:param><c:url value="/about/faq.html#hiddenloop"/></fmt:param></fmt:message></c:when>
<c:otherwise><label><fmt:message key="loopSearchForm.descriptor.${tok}"/></label>
</c:otherwise>
</c:choose>:
<c:choose>
<c:when test="${not empty param[tok]}"><label id="inf_${tok}" class="value">
<c:choose>
<c:when test="${tok =='searchable'}"><fmt:message key="loopSearchForm.${tok}.${param[tok]}"/></c:when>
<c:otherwise><fmt:message key="${param[tok]}"/></c:otherwise>
</c:choose></label></c:when>
<c:otherwise><label id="inf_scale" class="value"></label></c:otherwise>
</c:choose><br/>
</c:forTokens>
</div></c:otherwise></c:choose>
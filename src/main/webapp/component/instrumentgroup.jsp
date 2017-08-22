<%@ include file="/common/taglibs.jsp"%><c:set var="gid" value="1"/><c:set var="iid" value="1"/><c:if test="${not empty param.gid and (param.gid > 0)}"><c:set var="gid" value="${param.gid}"/></c:if><c:if test="${not empty param.instrumentId and param.instrumentId > 0}"><c:set var="iid" value="${param.instrumentId}"/></c:if><html:form action="/producer/saveLoopInfo.html?method=details"><html:hidden property="loopId" value="${param.id}"/><table border='1' id="instrumentgroupTable"><caption align="top"></caption><tr><td><beathive2:menu 
				property="attrib(instrument.groupId)"
				listName="instrumentgroup"
				default="${gid}"/></td><td align="right"><%-- span style="right:0;border:1px solid #ccc; float:right;color:red;size:20px;width:20px;text-align:center;" id="close">X</span --%></td></tr>
<tr>
<td id="subMenu"></td><td><button id="done">done</button></td></tr>
</table></html:form>
<script type="text/javascript">
$(document).ready(function(){
	var loopId = ${param.id};
/** initialize */	
	// load using base values
	$('#subMenu')
	.load(
		"<c:url value='/component/instruments.jsp'/>"
		, {'instrumentId' : '${iid}','gid': '${gid}'}
	);

/** Events */

	// get submenu
	$('.instrumentgroupMenu').change(function(){
		$('#subMenu').load("<c:url value='/component/instruments.jsp'/>" ,  {'instrumentId' : '','gid': $(this).val()});
	});
	
	// "done" button clicked
	$('#done').click(function(){
		var field = this;
		// get parentNode
		var par = $(field).parents("td.editing")[0];
		// get currently selected instrument id 
		var iid =  $('#instrumentMenu').val(); /* Note '#' id ref for instrument menu vs groupmenu using class reference '.' */
		// get currently selected instrument_group id 
		var gid =  $('.instrumentgroupMenu')[0].value;
		// get instrument name from <option>
		var txt =  $('#instrumentMenu option[value=' +iid +']').text();
		
		// prepare loop Id and instrument id for send to server
		var param={'loopId':loopId , 'attrib(instrument.id)':iid};
		
		// do http GET
		$.get(field.form.action, param,function(resp){
		
			checkStat(resp , loopId);
			par.prev= '<input class="attrib" type="text" name="gid='+gid+'&instrumentId='+iid+'&id='+loopId+'" value="' + txt+'"/>';
			// close parent, and this form
			$(par).trigger('close');
		});
		
		
		return false;
	});
});
</script>
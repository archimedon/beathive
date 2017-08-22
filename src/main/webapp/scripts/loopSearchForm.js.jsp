<script type="text/javascript">
//<![CDATA[
/*
 * triggered by group menu, this func loads the sub instruments
 */
function loadSubInstrument( iid , gid){

	$('#instrumentId').remove();

	if(gid!=''){
		$.get(		
			/* instrument URL */
			'<c:url value="/component/search/instruments.jsp"/>'			
			/* set instumentID & instrumentGroupID */
			, {'instrumentId' : iid ,'gid': gid}			
			/* handle response */
			, function(resp){
				var txt = $.trim(resp).replace(/attrib\(instrument\.id\)/ , 'instrumentForm.id');
				$('select' , txt).change(doUpdate);
				var spot = $('#subMenu');
				spot.html(txt);				spot.find('select').attr('id','instrumentId');//.addClass('criteria').addClass('button');
			 }
			/* data type  */
			,'html'
		);
	}};
	
function doUpdate(){
	var node = this;
	if (node.id=='instrumentGroupId'){			
		loadSubInstrument( '',node.value);		
	}
	sendForm(node);			
	checkAdvanced();	
}
	

$(document).ready(function(){
	var loopId =-1;
		var prefs = {};
	/*
	 * initialize the page sizer function
	 */
	$("#slider").slider({
		value:20,
		min: 4,
		max: 40,
		step: 2,
		slide: function(event, ui) {
			$("#size").val(ui.value);
		}
	});

	/*
	 * a function to clear forms (more than form.reset())
	 */
	$.fn.clearForm = function() {
		var desc = $('#advancedcriteriaForms');

		desc.find('label.criteria').each(function(){
			$(this).removeClass('highlight').addClass('disabled');
		});
		desc.block({ message: null });
	
		// iterate the elements within the form
		$(':input', this).each(function() {
			var type = this.type, tag = this.tagName.toLowerCase();
			if (type == 'text' || type == 'password' || tag == 'textarea')
			this.value = '';
		});
		
	};

	/*
	 * a function to make grouped criteria XOR'ed
	 */
	$.fn.xor=function(){
		$('.highlight[name="'+$(this).attr('name') +'"]').not('[id="'+$(this).attr('id')+'"]').removeClass('highlight');
		$(this).toggleClass("highlight");
	};

	$('label.criteria').click(function(){
		$(this).xor();
		if($('select.criteria.highlight').length > 0 ){
			sendForm(this);	
		}
	});

	$('.criteria').not('select.required').each(function(){
		
		var tag = this.tagName.toLowerCase();
		var tagsem = tag=='input' ? $(this).attr('type') : tag;
		
		switch (tagsem){
			case 'select': {
				$(this).bind('change' , doHasValue);
				break;
			}
			case 'text': {
				$(this).bind('blur' , doHasValue);
				break;
			}
			case 'checkbox': {
				$(this).bind('click' , doHasValue);
	
				break;
			}
			case 'radio': {
				$(this).bind('click' , doHasValue);
	
				break;
			}
			case 'label': {
				$(this).bind('click' , doHasValue);
				break;
			}
		}
	
	});
	$('select.required, #subMenu select.criteria').change(doUpdate);

	
	$('#soundClipForm').submit(function(){
		var data = { 'method':'search' };
		if($('#instrumentGroupId')[0].selectedIndex > 0 || $('#numTrackpacks').text()=='0'){
			data.type='loop';
		}
		$('.criteria').each(function(){//|| $(this).hasClass('highlight')
			key = $(this).attr('name') || $(this).attr('id');
			if(this.tagName=='LABEL'){
				if( $(this).hasClass('highlight')){
					data[key] = $(this).attr('value');
				}
			}
			else if($(this).attr('type')=='checkbox'){
				data[key] = this.checked +'';
			}
			else if($(this).attr('type')=='radio'){
				if(this.checked){
					data[key] = this.value;
				}
			}
			else if(this.value !=''){
				data[(key=='tempo')?'bpm' : key] = this.value;
				if (key=='sort'){
					data['dir'] = 'desc';
				}
			}
		});


		$('input#size' , this).each(function(){
			data[$(this).attr('name') || $(this).attr('id')] =$(this).attr('value');
		});
		window.location.href=this.action +'?'+qstr(data);
		return false;
	});

checkAdvanced(false);


	// init page size value
	$("#size").val($("#slider").slider("value"));
});// end ready

	
	function hasMinimumRequired(){
		var ary = $('.required');
		for (var y = 0 ; y < ary.length ; y++){
			 if(ary[y].value!=''){
				return true;
			}
		}
		return false;
	}
	
	function doHasValue(){
		if ($(this).attr('id')=='keyword' && this.value != ''){
			sendForm(this);
		}else{
			if(hasMinimumRequired()){
				sendForm(this);
			}
		}
	}
	function doChecks(){
	}
	function doAdvanced(){
	}

	function checkAdvanced(on){
		var open = $('select.criteria.highlight').length > 0 ;
		var desc = $('#advancedcriteriaForms');
		var tmp = 0;
		var res = $('#resultTracker').find('.counterNumber').each(function(){  tmp+= parseInt($(this).text());});
		if (on || tmp > 1){
			desc.find('label.criteria').removeClass('disabled');//.addClass('highlight');
			desc.unblock(); 
		}else{
			desc.block({ message: null }).find('.highlight')//.removeClass('highlight');
			desc.find('label.criteria').addClass('disabled');//.addClass('disabled');
		}
	}
	


function sendForm(node){

	var key = $(node).attr('name') || $(node).attr('id');
	var data={	'method':'count'  };
	$('.criteria').each(function(){
		key = $(this).attr('name') || $(this).attr('id');
		if(this.tagName=='LABEL'){
			if( $(this).hasClass('highlight')){
				data[key] = $(this).attr('value');
			}
		}
		else if($(this).attr('type')=='checkbox'){
			data[key] = this.checked +'';
		}
		else if($(this).attr('type')=='radio'){
			if(this.checked){
				data[key] = this.value;
			}
		}
		else if(this.value !=''){
			data[(key=='tempo')?'bpm' : key] = this.value;
		}
	});
	if(data['instrumentForm.groupId'] || data['genreId'] || data['keyword']){
		
		$.ajax({
      url: '<c:url value="/loops.html"/>',
      type: "GET",
	  async: false,
      data: data,
      dataType: "html",
      success: function(resp){
			var respd = {};
			eval('respd='+$.trim(resp) +';');
			$('#resultTracker').updateKeyed(respd);
			if(respd['numTrackpacks'] !='0' || respd['numLoops'] !='0'){
				$('#pagesizeEL').fadeIn('fast');
			}else{
				$('#pagesizeEL').fadeOut();
			}
		}
   });

		
	}else{
		$('#resultTracker').updateKeyed({'numTrackpacks' : '0' ,'numLoops':'0' });
	}
	return true;
}
 //]]>
</script>

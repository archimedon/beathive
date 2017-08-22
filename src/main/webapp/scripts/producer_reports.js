$.fn.serializeForm = function(){
	var params = {};
	$(this)
		.find("input").each(function(){
			if (this.value) {params[ this.name || this.id || this.parentNode.name || this.parentNode.id ] = this.value;}
		});
	return params;
}

function jaxTab(url){
	var params =qparam(url.slice(1));
	$("#reportResult").trigger("loadPage", [$('#salesReportForm').attr('action') , params ]);
	return false;
}
$(function() {
  	var  d = new Date(2007, 8 - 1, 25);
		$.datepicker.setDefaults({
			showButtonPanel: false ,
			changeYear: true ,
			minDate: d ,
			altFormat:'yyyy-mm-dd'
		});
				
	$('#reportResult').bind("loadPage", function(e, url , params){
		if (! params['js']){
		params['js']= 1;
		}
		var flip = params['dir'] == 'asc';
				
		$.post(url , params , function(dat){
			var ret = dat.replace(/href="([^"]+)/g , function(a , b){return 'href="javascript:void(jaxTab(\''+b+'\'));"';} );
			ret = ret.replace( /dir=\w+/g , flip ? 'dir=desc' : 'dir=asc');
			$('#reportResult').html($('#itemlist' , ret).html() );
			$('#stat').html($('#summary' , ret).html() );
		});
	});

	$('#runreport').click(function(){
		var url = this.form.action;
		var params =$('#salesReportForm').serializeForm();
		$("#reportResult").trigger("loadPage", [this.form.action , params ]);
		return false;
	});
	
	$('#enddate').datepicker().datepicker( 'setDate' ,  1);
	$('#startdate').datepicker().datepicker( 'setDate' ,  -182);
	
	
	$('.pagelinks a').live('click' , function(){
		$('#reportResult').load(this.href);
		return false;
	});
	
});	

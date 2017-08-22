<script type="text/javascript">/*<![CDATA[*/
$(document).ready(function(){

	// initializes event listeners for the current table
	$('#producerTrackpacks').ready(initTable);


	$('.opener')
	// toggle components on click 
	.toggle(openTP ,  closeTP)
	// addtionally bind closeTP directly to button
	// 'close' is called to make sure all component views are closed
	.bind('close' ,  closeTP );

	// try to get openTP cookie
    var stat = $.cookie('tpOpen');
    // if a TP was open prior to page-load
    if (stat){
    	// re-open it
		$('#' + stat).trigger('click');
	}


	$('.uploader input[type="file"]').live("change",function(){
		var form =this.form;
		var button = this;
		var $out = $('#'+$(button).attr('ref'));
		$(form).ajaxSubmit({
				beforeSubmit: function(a,f,o) {
					o.dataType ='html';
					$out.html('Submitting...');
				},
				success: function(data) {
					$out.html('Finalizing ...');
					var $doc = $($.trim(data));
					var err = $doc.find('#errors')[0];
					if(err){
						$(err).appendTo('#cons');
					}
				   
					var id = $doc.find('#completed').text();
					if (id && $.trim(id) !=''){
						id = $.trim(id);
						var table = $($(button).parents('table').get(0));
						var tbody = $(table.find('tbody')[0]);
				
						$.get(
							form.action
							, {'js':'1', 'id':id}
							, function(resp){
								tbody.prepend(resp);
								$(table).ready(initTable);
								$out.html('complete!');
							}
						); // end lookup
					}
				}
		});
	});

});/*]]>*/
// end ready
</script>

<c:set var="scriptStr" value='' scope="session"/>

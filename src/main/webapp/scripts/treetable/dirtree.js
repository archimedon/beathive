
// loads the "tooltip" using the given params
function getRendered(params){
	html = 'Loading ...';
	$('#tooltip').load(CONTEXT_PATH + '/component/producer/loopattribs.jsp' , params);
	return html;
}

// get data from the local 'clips' cache
// element MUST contain attribute 'ref' = 'ccc_nn' ex. 'emb_45'
function getData(elem){
	// get the ref attribute of this object
	var input_id = $(elem).attr('ref').split('_')[1];
	return clips[input_id];
}

$(document).ready(function() {
/*
	// Attach tooltip to edit button and play button. NB (removed: .swfareaButton)
    $('.loop_attribs , .swfarea').tooltip(
     	{
     		bodyHandler: function() {
				var params =getData(this);
				params['type']='summary';
				var sumval = getRendered(params);
  				return sumval;
     		} ,
			showURL: false ,
			track: false, 
			delay: 0, 
			showURL: false, 
			opacity: 1, 
			fixPNG: true, 
			top: -15, 
			left: 5 
     	}
     );
*/

//     $('.loop_attribs').mouseover(attrib_click).mouseout(attrib_unclick);
//     $('.swfarea').hover(attrib_click , attrib_unclick);

	$('.table tr').bind('addSibling' , function(e, rowElem){
		$(this).css({'background-color': 'orange'});
		var overlay= $('<div id="attrOverlay"/>');
		var cnt = $('<div />');
		cnt.css({width: '400' , height: '500' , 'border':'1px solid red' , 'margin':'auto'});
		cnt.append(rowElem);
		overlay.append('<div><a href="#" id="attribcloser">close</a></div>');
		overlay.append(cnt);
		$('body').append(overlay);
		$('#attribcloser').click(function(){
			$('#attribsTable').remove();
			overlay.remove();
		});
//		$(rowElem).insertAfter(this);
	});
	
	$('.looprow').bind('removeSibling' , function(e){
//		alert('removeSibling');
		$(this).css({'border': 'none'});
		$(this).next('tr').remove();
//		$('#detailsRow').remove();
//		$(this).next().remove();
	});

var clr = '';

	attrib_unclick = function(){
//		$('#detailsRow').prev('tr').trigger('removeSibling');
//		$('#detailsRow').trigger('clear');
//		$(this).removeClass('attribsOpen');
$('#attribcloser').trigger('click');
//		$(this).removeClass('attribsOpen');
//		$('#detailsRow').remove();

//		alert('y');
		//$('#detailsRow').each(function(){$(this).remove();});
		//	$(this).trigger("removeSibling");
	};
	
	attrib_click = function(){
		var node = this;
		var params =getData(node);
		params['type']='form';

//		$('#detailsRow').remove();
		
		$('.attribsOpen').each(function(){
			$(this).removeClass('attribsOpen');
			$(this).trigger("removeSibling");
			$(this).click();
		});

		$(this).css({'background-color': 'orange'});
		var overlay= $('<div id="attrOverlay"/>');
		var cnt = $('<div />');
		overlay.append('<div><a href="#" id="attribcloser">close</a></div>');
		overlay.append(cnt);
		$('body').append(overlay);
		cnt.load(CONTEXT_PATH+ '/component/producer/loopattribs.jsp' , params);
		$('#attribcloser').click(function(){
			$('#attribsTable').remove();
			overlay.remove();
		});
	} ;


     $('.loop_attribs').toggle(attrib_click , attrib_unclick);
}); // end doc ready


  function zazzIt(buttonId , args , callbackfn){
var ID = '#' +buttonId;

$(ID).css({position: 'absolute' ,  top: '0' , left: '0' , color: 'red'});
var btxt = $(ID).text();
	new Ajax_upload(
		ID, 
		{
			//action: 'upload.php',
			action: '/zazz/producer/sampleAudio.html', 
			name: 'uploads[0]',
			// Additional data to send
			data:args ,
			onSubmit : function(file , ext)
			{
				//if (ext && new RegExp('^(' + allowed.join('|') + ')$').test(ext)){
				if (ext && /^(aif|aiff|wav)$/.test(ext)){
					/* Setting data */
					//				this.set_data({					'key': 'This string will be send with the file' 				});
	
					$(ID).text('Uploading ' + file);
//					box = $($('#uploadbutton').parents("form#loopInfo")[0]).parent();
					
//					$(box).block({ message: "Processing" }); 
					//				 $('tr#node-${param.id}').block({ message: null }); 
				} else {				
					// extension is not allowed
					$('#uploadwrap .text').text('Error: only AIF or WAV files are allowed');
					
									$(ID).text('Error: only AIF or WAV files are allowed');
				
									setTimeout("$(ID).text(btxt +'ll')" , 3000);
					// cancel upload
					return false;				
				}
			} ,
			onComplete : function(file, response , callbackfn)
			{
				//		alert(response);
				$(ID).text(btxt);
				dat = {};
				eval ('dat='+$.trim(response).replace(/=/g,":"));
				
				
		
				callbackfn(dat);
				
//				loadSwf(dat.samplePath , dat.clipId, dat.formatId);
//				$('#uploadwrap .text').text('audio['+ dat.clipId+'] updated');				
//				$(box).unblock(); 
				//$('tr#node-${param.id}').unblock(); 
			}	
		}	
	);
}

  
  /*
				$('#detailsRow').remove();
				var par = $(this).parents("tr")[0];
				var str  ='';
				$(par).children('td').find('input').each(function(i){
					str += ',' + this.name + ':'+ ( this.value || $(this).text());
				});
				
				var detailsNode = '<tr><td colspan="6" class="detailsRow">'+str+'</td></tr>';
				par.trigger("addSibling", [ detailsNode ]);
		*/
		//     $('#details').html(str);
		

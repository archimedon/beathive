window.CLIPS ={};

/*
<c:if test="${cnt lt 3}">
</c:if> <%-- html:img page="/images/BUTTONS/trash2.jpg"/ --%>
*/

var addGenreButton = '<big class="addGenreButton"></big>';
var removeGenreButton = '<big class="removeGenreButton"></big>';


function putParam(param){
	var dat = window.CLIPS[param[id]];
	for (var key in param){
		dat[key]=param[key];
	}
}

function gotoPage(num , optURL){
	var loc =  optURL || window.location.href;
	var locpts = loc.split('?');
	var qp = qparam(locpts[1]);
	qp['page'] = num+'';
	window.location.href=locpts[0]+'?'+qstr(qp);
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

// loads the "tooltip" using the given params
function getRendered(params){
	html = 'Loading ...';
	$('#tooltip').load(CONTEXT_PATH+ '/component/producer/loopattribs.jsp' , params);
	return html;
}

// get data from the local 'clips' cache
// element MUST contain attribute 'ref' = 'ccc_nn' ex. 'emb_45'
function getData(elem){
	// get the ref attribute of this object
	var input_id = $(elem).attr('ref').split('_')[1];
	return window.CLIPS[input_id];
}



STYLE ={'color':'green', 'background-color':'white'};
STYLEFOCUS ={'color':'black', 'background-color':'grey'};
/* clips structure -
 {	1 : { loop_attrib1:'atribval1', loop_attrib2:'atribval2', ... , swfs:{aif:'/path/to/swf' , wav:'/path/to/swf'} } ,
	2 : { ... }  ,
	...
 }
*/

// initialize swfObject (pseudo deprecated)
LOOP_WIDTH = LOOP_HEIGHT = "22";
FLVER = "6.0.65"; //"6.0.65"
SWFURL = CONTEXT_PATH + "/loopButton.swf";
INSTALLPATH = CONTEXT_PATH + "/scripts/swfobject/expressInstall.swf";
DETAIL_URL=CONTEXT_PATH + "/producer/saveLoopInfo.html?method=details";
// end init. value are re-initialized in JSP

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
	//swfobject.embedSWF("the.swf", "destObjID", "300", "120", "9.0.0","expressInstall.swf", flashvars, params, attributes);
	swfobject.embedSWF(SWFURL, target , LOOP_WIDTH , LOOP_HEIGHT, FLVER, INSTALLPATH ,
		{'path':escape(path) },						//sound path
		{'bgcolor':bgcolor}, 				// color
		{id: swfid , name:swfid}			// object's id
	); 
}
    
    

function msgClose(bReload){
	$.unblockUI({ fadeOut: 200 });
	if (bReload) {setTimeout ("window.location.reload();" , 500);}
}

/**
 * return the ID of the clip
 * represented by the given TR element 
 */
function getClipId(elem){
	var loc = elem;
	if (!$(loc).is('tr')){
		loc = $(elem).parents('tr')[0];
	}
	var id = $($(loc).find('.swfarea')[0]).attr('id');
	return id;
}
function wheel(pref , map){
//	var str ='';
//	$.each(map , function(k){
//		str += k +':'+this;
//	});
}
function doGenre(event){
	var field = this;
	var deleting = ((!field.value) || field.value == '');

	
	var row = $(field).parents('tr').get(0);
	var parTd = $(field).parents('.loopGenres').get(0);
	var gmens = $('.genreMenu' , parTd);

	if (deleting && gmens.length == 1){
		return false;
	}

	$(field).css(STYLEFOCUS).blur(function(){
		$(field).css(STYLE);	
	});

	var data={};
	var loopId= getClipId(row) ;

	$.each(gmens, function(i){
		if (typeof data[field.name] != 'undefined') {
		  data[field.name] += ',' + this.value;
		} else {
		  data[field.name] = this.value;
		}
	});
	data['loopId'] = loopId;


	$.get(DETAIL_URL,data,function(res){
		$(field).css(STYLE);
		
	if(deleting && gmens.length > 1){
			$($(field).next('big')[0]).remove();
		if (gmens.length == 3){$(parTd).append(addGenreButton);}
			$(field).remove();
		}
		checkStat(res , loopId);
	});

	
	event.stopPropagation();
	return false;
}

/* toolTip params*/
tooltipParams = {
     		bodyHandler: function() {
				var params =getData(this);
				params['type']='summary';
				var sumval = getRendered(params);
  				return sumval;
     		} ,
			track: false, 
			delay: 6, 
			showURL: false, 
			opacity: 1, 
			fixPNG: true, 
			top: -15, 
			left: 5 
     	};
     	
/**
 *	injects the instrument menus
 */
function switchVal(event){
	$('.editing').trigger('close');
	$(this).unbind('click');

	var node = $(this).is('input') ? $(this).parents('td')[0] : this;
	$($(node).children()[0]).css(STYLEFOCUS);
	// get list-ref from this node
	var listRef = $(node).attr('ref');
	node.prev = $(node).html();
	$(node).html("loading...");
	// get query from inner
	var propname = $(node.prev).attr('name');
	var propvals = propname.split("&");
	var param = {};
	var t = 0 ; 
	for (t = 0 ; t < propvals.length; t++ ){
		pcs = propvals[t].split("=");
		param[pcs[0]] =  pcs[1];
	}
	var url = CONTEXT_PATH + '/component/instrumentgroup.jsp';

	$.get(url , param, function(resp){
		var toppos = Math.round($(node).position().top - 0);
		var leftpos = Math.round($(node).position().left - 25) 
		var table = $(resp).find('#instrumentgroupTable' )[0];
		$(table).css({'color':'red'});
		$(node).html($(resp).css({'position':'absolute', 'top':toppos+ 'px', 'left':leftpos+ 'px'}));
		$(node).addClass('editing');
		$(node).bind('close' ,function(e){
			$(this).removeClass('editing');
			$($(this).html()).remove();
			$(this).html(this.prev);
			$($(this).children()[0]).css(STYLE);
			$(this).click(switchVal);
			e.stopPropagation();
		});
	});
	
	event.stopPropagation();
	return false;
}
	
var attribFocus = function(){
		var field = this;
		$(field).css(STYLEFOCUS)
		.one('change' , function(){
			var data={};
			var loopId = getClipId($(field).parents('tr').get(0)) ;
			data['loopId'] =loopId ;
			data[field.name] =  field.value ;
			
			$.get(DETAIL_URL,data,function(res){

				
				checkStat(res , loopId);

//				$('#stat_'+loopId).trigger("isready", [ dat, loopId] );
			});
		})
		.blur(function(){
				$(field).css(STYLE);
		});

	};

function checkStat(res , idnum){
	var loopdata={};
	eval('loopdata='+$.trim(res) +';');
	var stat = loopdata['ready'] +'';
	if (stat == 'true'){
		$('#stat_'+idnum).removeClass().addClass('trueReady');
	var tables = $('#stat_'+idnum).parents('table.innerLoopList');
	if	(tables.length > 0 ) {
		var tpid = $(tables[0]).attr('id').split('_')[1];

$.get(DETAIL_URL,{loopId:tpid , 'attrib(name)':$('#clipName_'+tpid).attr('value')},function(dat){
	var data={};
	eval('data='+$.trim(dat) +';');
		for (var t in data){
			window.CLIPS[tpid][t]=data[t];
		}
			stat = data['ready'] +'';
			if (stat == 'true'){
				$('#stat_'+tpid).removeClass().addClass('trueReady');
				
			}else{
				$('#stat_'+tpid).removeClass().addClass('falseReady');
			}
		});
	}
	
	
	}else{
		$('#stat_'+idnum).removeClass().addClass('falseReady');
	}

	
	
}
function CLICK_HANDLER (event) {
CLICK_FUNCS = [
{ "key" : ".errorReq" , "func" : switchVal}  
// , { "key" : ".instrument" , "func"  : switchVal }
// , { "key" :'.attrib' , "func"   : attribFocus}
]
    var target = $(event.target);
    for(var i=0 ; i < CLICK_FUNCS.length ; i++)
    {
      var selector  = CLICK_FUNCS[i].key;
      if(target.is(selector))
      {
        return CLICK_FUNCS[i].func.apply(event.target, arguments);
      }
    }
}


/** The ID of the component loops TR **/
NEW_ROW_ID = 'comprow';

/** utility to create a suitable TR **/
function rwrap(str){
	return '<tr class="packloop" id="'+NEW_ROW_ID+'"><td><'+'/td><td id="comploops" colspan="8"><div class="innerbox">'+str+'</div><'+'/td><'+'/tr>';
}


function initTable(){
		
	/** 
	 *	Intended to change square in cell 1 from red to black if loop is ready for searching
	 */
	$('.swfarea' , this).tooltip(tooltipParams);
//	$('#producerLoops .genreMenu' , this).change(doGenre);
	$('.genreMenu' , this).change(doGenre);
//	$('#producerTrackpacks .innerLoopList .genreMenu' , this).focus(doGenre);
    $('.instrument', this).focus(switchVal);
    $('.errorReq', this).click(switchVal);


}


			
var uploadFile = function(but , errorarray){


	var button = but || this;
	
	var form =button.form;
	
	var out = $($(form).find('.uploadOutput')[0]);// $('#cons');
	$(form).ajaxSubmit({
		'beforeSubmit': function(a,f,o) {
	var fileup = undefined;
	$.each(a , function(){
		if(f.attr('id').indexOf('trackpack') >-1){
			if ((!fileup) || fileup.indexOf('.zip')==-1){
				out.html('<strong class="error">'+errorarray[0]+'<'+'/strong>');
				return false;
			}
		}
	});
	o.dataType ='html';
	out.html('Processing...');
}
				
		,
		
		'success': function(data) {
	out.html('Finalizing ...');
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
			$(form).attr('ref')
			, {'js':'1', 'id':id}
			, function(resp){
				tbody.prepend(resp);
				$(resp , '.expand').toggle(txExpand, tpCollapse);
				$(table).ready(initTable);
				out.html('complete!');
			}
		); // end lookup
	}
}
	});

};
function doLoad(e ){
	var url = (this && this.href) ? this.href : e.target;
	var str = '';
	var aUrl = new URL(url);
	var params = aUrl.getParams();
	params.js=1;
	var flip = params['idir'] == 'asc';
	$.get(aUrl.getURI() , params , function(dat){
		var pagl = $($('#iloops').html(dat).find('.pagelinks')[0]);
		pagl.find('a').each(function(){
			this.href = this.href.replace(/\b(\&{0,1}ipage=\w+|\&{0,1}isize=\w+|\&{0,1}idir=\w+|\&{0,1}isort=\w+)\b/g ,'')
			.replace(/\b(page|size|dir|sort)\b/g , function(a){return 'i'+a ;});
		});
	});
	e.preventDefault();
	return false;
}

function loadChild(url , cell){
	var aUrl = new URL(url);
	var params = aUrl.getParams();
	var tables = $(this).parents('table');
	$.get(aUrl.getURI() , params , function(doc){
		cell.html(doc);
		
		cell.find('.pagelinks a').each(function(i){
			var turl = new URL(this.href);
			var inps = turl.getParams();
				
			for (var g in inps){
				if(g=='page'){
					inps['i'+g]=inps[g];
					delete inps[g];
				}
			}
			this.href = turl.getURI() +'?'+qstr(inps);
			$(this).click(function(){
				loadChild(this.href , cell);
				return false;
			});
			$(tables[0]).ready(initTable);
		});
		
		cell.find('.sortable a').each(function(i){
			var href = this.href;
			var turl = new URL(href);
			var pars = {};
			var inps = turl.getParams();
			
			for (var g in inps){
				if(g.indexOf('i') != 0 && g!='clipId'){
					pars['i'+g]=inps[g];
				}else{
					pars[g]=inps[g];
				}
			}
			this.href = turl.getURI() +'?'+qstr(pars);
			$(this).click(function(){
				loadChild(this.href , cell);
				return false;
			});
			$(tables[0]).ready(initTable);
		});
		tb_init('a.thickbox'); //call tb_init function to initiate ThichBox into your respective tab panels

	});
}

function toggleView(e){
	if ($(this).hasClass('curropen')){
		$(this).removeClass('curropen');
		$(this).find('div').removeClass('openedButton').addClass('expandButton');
		$('#componentRow').trigger('close');
	}else{
	$('#componentRow').trigger('close');
		var cell = getCell(this);
		
				$(this).find('div').removeClass('expandButton').addClass('openedButton');

		cell.html('loading ...');
		loadChild(this.href , cell);
		$(this).addClass('curropen');
	}
	return false;
}



/**
 * Create and insert a hidden, empty row beneath trigger.
 */
function getCell(elem){
	var tlen = 11;
	var rows = $(elem).parents('tr');
	var target = null;
	if (rows.length > 0 ){
		target = $(rows[0]);
		tlen = target.find('td').length;
	}else{
		target = $($(elem).parents('tbody')[0]);
		tlen = 11;
	}
	
	var componentRow = $('<tr id="componentRow" class="innerres"><td></td></tr>');//.hide();
	var cell = $('<td id="iloops" colspan="10">Loading...</td>');
	if (rows.length > 0 ){
		componentRow.insertAfter(target);
	}else{
		target.append(componentRow);
	}
	componentRow.append(cell);
	componentRow.bind('close', function(){$(this).fadeOut(function(){$(this).remove();})});
	return cell;
}		
function cleanUp(row , msgview , msg){
	var id = row.split('_')[1];
	$($('.rem_' + id)[0]).closest('tr').fadeOut(1200, function(){msgview.html(msg);
	setTimeout(endDialog , 1000);delete CLIPS[id];});
}

function endDialog(row){
	self.parent.tb_remove();
	$('#TB_window').remove('click');
}

$(document).ready(function() {
	$('.genreMenu').change(doGenre);
	$('.swfarea').tooltip(tooltipParams);

	$('#confirmDeleteForm input:submit').live('click' , function(){
		if($.data(this, 'clicked')){
		  return false;
		}
		else{
			
			var button = this;
			var form =button.form;
			var outmsg = $($('.messageOutput' , form)[0]);
		
			$(form).ajaxSubmit({
				// ary_values : [{ name: 'pname0', value: 'pvalue0' }, { name: 'pname1', value: 'pvalue1' }]
				'beforeSubmit': function(ary_values,formObject,ajaxOptions) {
					ajaxOptions.dataType ='json';

					$.data(this, 'clicked', true);
				}	,	
				'success': function(jresp, statusText)  { 
					if(jresp.error){
						outmsg.html(jresp.error);
					}else{
					
					
					cleanUp($(button).attr("id") , outmsg ,jresp.msg );
					//outmsg.html(jresp.msg);
					}
					$.data(this, 'clicked', false);
					return false;
				}	,
				'error': function()  { 
					$.data(this, 'clicked', false);
					outmsg.html('error');
					return false;
				}
			});
		}
		return false;
	});


$('.removeGenreButton').live('click' , function(){
	var field = this; //$(this).prev('.genreMenu')[0];
	var parTd = $(field).parents('.loopGenres').get(0);
	var gmens = $('.genreMenu' , parTd);

$(this).prev()[0].selectedIndex=0;
$(this).prev('select').trigger('change');
	return false;
});

$('.addGenreButton').live('click' , function(){
	var field = this; //$(this).prev('.genreMenu')[0];
	var row = $(field).parents('tr').get(0);
	var parTd = $(field).parents('.loopGenres').get(0);
	var gmens = $('.genreMenu' , parTd);

	var tables = $(row).parents('table');		
	if	(tables.length > 1 || $(tables[0]).attr('id')=='producerLoops') {
		if (gmens.length < 3){
			var clone = $(gmens[0]).clone();
			
			$('option' , clone).removeAttr( 'selected' );
			
			$(gmens).each(function(){				
				$(clone).find('option[value="'+this.value+'"]').remove();
			});
			$(clone).change(doGenre);
			
			$(parTd).append(clone);
			$(clone).insertBefore(field);
			
			$(removeGenreButton).insertAfter($(clone));
//			
//			$(field).prepend(clone);
			//$(clone).appendTo($('.loopGenres' , parTd));
		}
	}
	gmens = $('.genreMenu' , parTd);
	if (gmens.length == 3){
		$(field).remove();
	}
});

	
	$('.attrib' ).live('change',function(){
		var data={};
		var loopId = getClipId($(this).parents('tr').get(0)) ;
		data['loopId'] =loopId ;
		data[this.name] =  this.value ;
		
		$.get(DETAIL_URL,data,function(dat){
		checkStat(dat , loopId);
		//	$('#stat_'+loopId).trigger("isready", [ dat, loopId] );
		});
	}).css(STYLE);
	
	/**
	 * View Component
	 */
	$('.expand').toggle(txExpand, tpCollapse);



var clr = '';

	attrib_unclick = function(node){
	$('#attribcloser').trigger('click');
	$(node).removeClass('attribsOpen');
	};
	
	attrib_click = function(node){
		var params =getData(node);
		params['type']='form';
		$('#attribcloser').trigger('click');
		$(node).addClass('attribsOpen');
		var curr = (new URL(window.location.href)).getParams();
		var insert = "";
		if (curr['jay']=='1'){
			insert = $.ajax({
		  url: CONTEXT_PATH+ '/component/producer/editloopoverlay.htm',
		  global: false,
		  type: "GET",
		  async: false 
	   }).responseText;
			$('body').append(insert);
		}else{
		
		insert = $('<div style="display:none;width:400px; height: 400px;" id="attribsBox"><h3><a style="line-height: 15px;" id="attribcloser">close</a></h3></div>');
		var form = $('<div id="attribForm"/>');
		form.load(CONTEXT_PATH+ '/component/producer/loopattribs.jsp' , params);
		insert.css({'background-color':'#ccc', 'position':'absolute', 'left':'30%','top':'30%'}).append(form).show();
		$('body').append(insert);
	}
} ;
	$('#attribcloser').live('click'  , function(){
		$('#attribsBox').remove();
		return false;
	});


     $('.loop_attribs').live("click", function(){
     	if (! $(this).hasClass('attribsOpen')){
     		attrib_click(this);
     	}else{
      		attrib_unclick(this);
    	}
     });
}); // end doc ready

	function txExpand(){
		$('.curropen').click();
		var cell = getCell(this);
		
				$(this).find('div').removeClass('expandButton').addClass('openedButton');

		cell.html('loading ...');
		loadChild(this.href , cell);
		$(this).addClass('curropen');

		
		
		var pos = $(this).position();
		$.scrollTo({ top: (pos.top + 90 ), left:0 } , 1500);
		// test if scrolling is causing error in safari
		if(! $.browser.safari ){
		}
		return false;
	}
	
	function tpCollapse(){
		$(this).removeClass('curropen');
		$(this).find('div').removeClass('openedButton').addClass('expandButton');
		$('#componentRow').trigger('close');
		return false;
	}





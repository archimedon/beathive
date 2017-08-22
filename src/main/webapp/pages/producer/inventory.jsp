<%@ include file="/common/taglibs.jsp"%><%@page import="com.beathive.webapp.util.RequestUtil, java.util.*,java.io.*" %>
<script type="text/javascript">
clips ={};
</script>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="producerMenu.title"/></title>
    <content tag="heading"><fmt:message key="producerMenu.heading"/></content>
    <meta name="menu" content="ProducerHome"/>
<style type="text/css">
#bookView{
	width: 75%;
	height: 502px;
	margin: auto auto;
	border: 1px solid brown;
}
#bookView #left,#right{
	width: 49.5%;
	height:500px;
	margin: 0 auto;
	border: 1px solid black;
	overflow: auto;
	padding: 3px 0;
	overflow-y:scroll;
}
div#left{
	float: left;
	left:0;
	display: inline-block;
}
div#right{
	float: right;
	right:0;
	display: inline-block;
}
#bookView div ul{
	padding: 0;
	margin: 0;
}
#bookView div ul li{
padding: 0;
margin: 3px auto;
height: 24px;
background-color: #ccc;
}
#bookView #left ul li{
	text-align:left;
}
#bookView #right ul li{
	text-align:left;
}
.treebutton{
	margin: 2px;
}
#bookView div * li.branch{
	width: 350px !important;

}
#bookView #left dl dt{
	text-align:left;
	background-color: #777;
color: #fff;
}
div#tpopts{
	height: 20px !important;
	position: relative;
	top: 0;
	left: 0;
	z-index: 1;
	opacity:1;
	filter:alpha(opacity=100);
}






#unboundList{
	display: block;
	margin: auto auto;
	width: 75%;
	height: 200px;
	overflow: scroll;
	border: 1px solid #569588;
}

.audiofileButton{
width: 125 !important;
height: 125;
font-size: 9px;display: inline;float: left;
overflow: hidden;
white-space: nowrap;
border: 1px solid #000;
margin: 5px;
padding: 5px;
}
#filecontainer{
	height: 90%;
	width: 600px;
}
.inputline{
	border: 1px solid brown;
	width: 100px;
}





.wrapper {
	width: 133px;
	/* Centering button will not work, so we need to use additional div */
	margin: 0 auto;
}

div.button {
	height: 29px;	
	width: 133px;
	background: url(http://valums.com/wp-content/uploads/ajax-upload/button.png) 0 0;
	
	font-size: 14px;
	color: #C7D92C;
	text-align: center;
	padding-top: 15px;
}
/* 
We can't use ":hover" preudo-class because we have
invisible file input above, so we have to simulate
hover effect with javascript. 
 */
div.button.hover {
	background: url(button.png) 0 56px;
	color: #95A226;	
}


.loopStat{
	background-color: #fff;
}
.loopStatOff{
	border: 1px solid orange;
	background-color: #bbb;

}

.dirtree{
	width: 600px;
}
#tplist{
	/*
	height: 700px;
min-height: 300px;
	max-height: 400px;
	height: 50%;
*/
	overflow-y: auto;
	width: 500px;
}
#looplist{
	/*
min-height: 300px;
	max-height: 400px;
	height: 50%;
*/
	overflow-y: auto;
	width: 500px;
}

.flippa{
	
}

.swfarea{
	border: 0px solid #445;
	display: inline-block;
	clear: none;
	z-index: 111;
	width: 56px;
	height: 30px;
	padding-top: 7px;
	margin-right: 12px;
	padding-right: 7px;
	text-align:left;
	vertical-align:bottom;
}
.swfareaButton{
	display: inline-block;
	clear: none;
	z-index: 1;
	text-align:center;
	vertical-align:bottom;
}

/*
div#inventoryLoopinfo{
	width: 400px;
	height: 200px;
	border: 1px solid orange;
	margin: auto;
	
}
.detailsRow table{
	width: 400px;
	height: 200px;
	collapse-border: collapse;
}
*/


.attribsOpen{
	color: pink;
}
</style>



<script type= "text/javascript">/*<![CDATA[*/
function msgClose(bReload){
	$.unblockUI({ fadeOut: 200 });
	if (bReload) {setTimeout ("window.location.reload();" , 500);}
}

$(document).ready(function(){
	function nextLine(){
		nextNum = parseInt($('#filecontainer').find('.inputline').length);
		$('<span class="inputline""><input type="file" name="uploads['+nextNum+']" class="file" id="uploads'+nextNum+'"/><'+'/span>').insertAfter('#uploads'+ (nextNum - 1));
		return false;
	}
	$('#addinput').click(nextLine);



	var button = $('#button1'), interval;
	new Ajax_upload(button,{
  // Location of the server-side upload script
  action: '/zazz/producer/sampleAudio.html', 
  name: 'uploads[0]',
  // Additional data to send
  data: {
	  username: "<c:out value='${pageContext.request.remoteUser}'/>",
	  storeId:	"<c:out value='${storeForm.id}'/>",
	  method: "up"
  },
  // Submit file after selection
  autoSubmit: true,
  // Fired after the file is selected
  // Useful when autoSubmit is disabled
  // You can return false to cancel upload
  // @param file basename of uploaded file
  // @param extension of that file
  onChange: function(file, extension){},
  // Fired before the file is uploaded
  // You can return false to cancel upload
  // @param file basename of uploaded file
  // @param extension of that file
  onSubmit: function(file, extension) {
  			// change button text, when user selects file			
			button.text('Processing ' +   file);
			
			// If you want to allow uploading only 1 file at time,
			// you can disable upload button
			this.disable();
			
			// Uploding -> Uploading. -> Uploading...
			interval = window.setInterval(function(){
				var text = button.text();
				if (text.length < 20){
					button.text(text + '.');					
				} else {
					button.text('Processing ' +   file);				
				}
			}, 200);
		},

  // Fired when file upload is completed
  // @param file basename of uploaded file
  // @param response server response
  onComplete: function(file, response) {
	button.text('Complete');
	window.clearInterval(interval);
	this.enable();
	button.text('Upload');
	var resp = $.trim(response);
	
	
	var procresp = "<div style=''position:absolute;top:30px;overflow:auto; height:40px;color:green;border: 1px solid red;width:500px;'>" + resp +"<span style='right:20px; top: 10px;'><a href='javascript:void(msgClose("+(resp.indexOf('completed')>-1 ? 'true' : 'false')+"))'>close<"+"/a><\/span><"+"/div>";
			$.blockUI({
			    css: { 
        padding:        0, 
        margin:         0, 
        width:          '50%', 
        top:            '10%', 
        textAlign:      'left', 
        color:          '#000', 
        border:         '3px solid #aaa', 
        backgroundColor:'#fff', 
        cursor:         'hand' ,
        left: 			'10%'
    }, 
 
    // styles for the overlay 
    overlayCSS:  { 
        backgroundColor:'#556', 
        opacity:        '0.6' 
    }, 
 
    // styles applied when using $.growlUI 
    growlCSS: { 
        width:    '350px', 
        top:      '10px', 
        left:     '', 
        right:    '10px', 
        border:   'none', 
        padding:  '5px', 
        opacity:  '0.6', 
        color:    '#fff', 
        backgroundColor: '#000', 
        '-webkit-border-radius': '10px', 
        '-moz-border-radius':    '10px' 
    }, 
 
    // z-index for the blocking overlay 
    baseZ: 1000, 
 
 
    // allow body element to be stetched in ie6; this makes blocking look better 
    // on "short" pages.  disable if you wish to prevent changes to the body height 
    allowBodyStretch: true, 
 
    // be default blockUI will supress tab navigation from leaving blocking content; 
    constrainTabKey: true, 
 
    // fadeIn time in millis; set to 0 to disable fadeIn on block 
    fadeIn:  200, 
 
    // fadeOut time in millis; set to 0 to disable fadeOut on unblock 
    fadeOut:  400, 
 
    // time in millis to wait before auto-unblocking; set to 0 to disable auto-unblock 
    timeout: 0, 
			
	message: procresp
	}); 
  }
});
$('#expandButton').toggle(
	function(){
	$(".dirtree .folder").each(
	function(){
			$($(this).parents("tr")[0]).expand().addClass('expanded');
		});
		$(this).text("COLLAPSE ALL");
	}
	,function(){
		$(".dirtree .folder").each(function(){
			$($(this).parents("tr")[0]).addClass('collapsed').collapse().removeClass('expanded');
		});
		$(this).text("EXPAND ALL");
	}
);

});

/*]]>*/</script>

<!-- === -->
<script type="text/javascript">
	STYLE ={'color':'green', 'background-color':'white'};
	STYLEFOCUS ={'color':'black', 'background-color':'grey'};

inner = null;

var switchVal = function(){


$('.editing').trigger('close');


	$(this).unbind('click');
	
		var node = this;
		$($(node).children()[0]).css(STYLEFOCUS);
		// get list-ref from this node
		var listRef = $(node).attr('ref');
		node.prev = $(node).html();
		
		// get query from inner
		var propname = $(node.prev).attr('name');
		var propvals = propname.split("&");
		var param = {};
		var t = 0 ; 
		for (t = 0 ; t < propvals.length; t++ ){
			pcs = propvals[t].split("=");
			param[pcs[0]] =  pcs[1];
		}
		var url = '<c:url value="/component/instrumentgroup.jsp"/>';

		$.get(url , param, function(resp){
			var toppos = Math.round($(node).position().top - 0);
			var leftpos = Math.round($(node).position().left - 25) 
			var table = $(resp).find('#instrumentgroupTable' )[0];
			$(table).css({'color':'red'});
			$(node).html($(resp).css({'position':'absolute', 'top':toppos+ 'px', 'left':leftpos+ 'px'}));
			$(node).addClass('editing');
		});
};
	
$(document).ready(function(){


	// swtiches text/img to <input>
	$('.looprow .flippa').click(switchVal);
	

$('.flippa')
	.bind('close' ,function(){
		$(this).removeClass('editing');
		$($(this).html()).remove();
		$(this).html(this.prev);
		$($(this).children()[0]).css(STYLE);

		$(this).click(switchVal);
		
	})
	.click(switchVal);

	$('.attrib')
	.css(STYLE)
	.focus(function(){
		var field = this;
		$(field).css(STYLEFOCUS)
		.one('change' , function(){
			var data={};
			data['loopId'] = this.form.elements['loopId'].value ;
			data[field.name] =  field.value ;
			
			$.get(field.form.action,data,function(data){
				$(field).css(STYLE);
//				alert("Data Loaded: " + data);
			});
		
		})
		.blur(function(){
				$(field).css(STYLE);
		});

	});
	$('.genreMenu')
	.css(STYLE)
	.focus(function(){
		var field = this;
		$(field).css(STYLEFOCUS)
		.one('change' , function(){
			var data={};
			data['loopId'] = this.form.elements['loopId'].value ;
			data[field.name] =  field.value ;
			
			$.get(field.form.action,data,function(data){
				$(field).css(STYLE);
			});
		
		})
		.blur(function(){
				$(field).css(STYLE);
		});

	});
	
	var hold = {};
	
});

</script>
<c:set var="scriptStr" value='' scope="session"/>
       
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
	//swfobject.embedSWF("the.swf", "destObjID", "300", "120", "9.0.0","expressInstall.swf", flashvars, params, attributes);
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
</script>

</head>
<div id="cons"></div>
<p><fmt:message key="producerHome.message"/></p>
<form id="sampleForm" method="post" action="/zazz/producer/sampleAudio.html" enctype="multipart/form-data">
<div style="overflow: hidden; position: relative; display: block; height: 44px; width: 133px;">
	<div id="button1" class="button">Upload</div>
	<input style="margin: 0px; padding: 0px; position: absolute; width: 220px; height: 10px; opacity: 0; cursor: pointer; top: 6.93333px; left: -41.5px;" name="myfile" type="file">
</div>
</form>
<c:if test="${true}">

<div id="workspace">
<table class="dirtree" border="1" id="dirtreeTable">
<caption align="top" id="details"></caption>
<tr height="25px">
<td height="25px" colspan="5">
	<h3 align="center" height="25px">Trackpacks</h3>
	<div id="tpopts"><span id="expandButton">EXPAND ALL</span></div>
</td>
<thead>
	<tr>
	  <th width="285px"><span style="width:285px">&nbsp;</span></th>
	  <th>Instrument</th>
	  <th>Genre(s)</th>
	  <th>BPM</th>
	  <th>Price (USD)</th>
	</tr>
</thead>
  <tbody id="tplist">
	<c:set var="dirlist" scope="request" value="${storeTrackpacks}"/>
	
	
	
	
  <c:import url="/component/dirtree.jsp"/>
  </tbody>
<tr height="25px">
	<td height="25px" colspan="5"><h3 align="center" height="25px">Loops</h3></td>
</tr>
<tr><td colspan="5">
</td></tr>
<c:set var="dirlist" scope="request" value="${storeLoops}"/>
<tbody id="looplist">
	<c:import url="/component/dirtree.jsp"/>
</tbody>
</table>
<script type="text/javascript">
<c:out value="${scriptStr}" escapeXml="false"/>
</script><c:remove var="scriptStr" scope="session"/>
</div>
</c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>

	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.js'/>"></script>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.bgiframe.js'/>"></script>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.dimensions.js'/>"></script>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.tooltip.pack.js'/>"></script>

	<script language="javascript">AC_FL_RunContent = 0;</script>
	<script type="text/javascript" language="javascript"
	     src="<c:url value='/scripts/AC_RunActiveContent.js'/>"></script>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.form.js'/>"></script>


	<%-- used for auto upload button --%>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.ajax_upload.1.0.js'/>"></script>

 
 <!-- dirtree -->
	<link href="<c:url value='/scripts/treetable/css/master.css'/>" rel="stylesheet" type="text/css" />
	<link href="<c:url value='/scripts/treetable/css/jquery.treeTable.css'/>" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<c:url value='/scripts/treetable/jquery.ui.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/treetable/jquery.treeTable.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/scripts/treetable/dirtree.js'/>"></script>
	
	<script type="text/javascript" src="<c:url value='/scripts/jquery.blockUI.js'/>"></script>
 <!-- /dirtree -->
 

        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/${appConfig["csstheme"]}/theme.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='/styles/${appConfig["csstheme"]}/print.css'/>" />

<script type="text/javascript" src="<c:url value='/scripts/nicetabs.js'/>"></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/niceTabs.css'/>" />

       
<style type="text/css">
	#cart{
		position:absolute !important;
		top: 100px;
		display: block !important;
		width: 180 !important;
		height: 200 !important;
		right: 0 !important;
		border: 3px solid #fff;
	}
	#page{
	float:left;
	}
</style>
          <style type="text/css">
    .buttonbar{
    	 width: 100%;
    	 height: 50px;
    }


       #floatAcc{
       	position:absolute;
       	display: block;
       	top:5px;
       	right:10px !important;
       	z-index: 200;
       	float: right;
       	border: 1px solid #888;
       	background-color: #bbb;
       	color: #fff;
       
       	text-align: center;
       }
       
       
#tooltip{
       	position:absolute;
       	border: 1px solid #888;
       	background-color: #95A226;
       	color: #fff;
}

.extraInfo{
	display: none;
	visability: hidden;
}
       </style>
    </head>

<ul>
	<li id="example3" class="example">
				<input id="button3" type="file" />
			<p>Uploaded files:</p>
			<ol class="files"><div id="unboundList"><c:import url="/component/loops.html?method=listClipAudio"></c:import>
</div>
</ol>
	</li>
</ul>
<form id="sampleForm" method="post" action="/zazz/producer/sampleAudio.html" enctype="multipart/form-data">
<input type="hidden" name="storeId" id="storeId" value="1"/><%-- c:out value='${request.remoteUser}'/ --%>
<input type="hidden" name="js" id="js" value="1"/>
<input type="text" name="username" class="medium" id="username" value="anonymous"/>

<ul>
    <li>
        <!-- input type="file" name="uploads[0]" class="file medium" id="uploads0"/ -->
    </li>
  <li class="buttonBar">
  <input name="method" value="up" onclick="bCancel=false; return ajaxFileUpload()" class="button" type="submit" id="submitButton">
        
    </li>
</ul>
</form>
<script type="text/javascript">
</script>
<div id="upload_button">Upload</div>
<script type= "text/javascript">/*<![CDATA[*/
var i = 0;
function showSwf(srcpath){
var spath = escape(srcpath);
return '<embed width="25" height="25" src="/zazz/loopButton.swf?path=' + spath+ '" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" align="middle" play="false" loop="true" scale="showall" wmode="window" devicefont="false" bgcolor="#252525" name="main7_v'+ (i++)+ '" menu="true" allowFullScreen="false" allowScriptAccess="always" salign="" type="application/x-shockwave-flash" ><'+'/embed>';
}

$(document).ready(function(){
	
	/* example3 */
	new Ajax_upload('#button3', {
		//action: 'upload',
		action: '/zazz/producer/sampleAudio.html', // I disabled uploads in this example for security reaaons
		name: 'uploads[0]',
		 data: {
    js : '1',
    username : 'anonymous', 
    'method':'up'
  },
		onComplete : function(file, response){
			var ary = [];
			eval ("ary = " + response +";");
	var ht = showSwf(ary[0].samplePath);
			$('<li></li>').appendTo($('#example3 .files')).append(ht);
		}	
	});		
});

/*]]>*/</script>




<script type="text/javascript">

function rat(){
	return (arguments[0][1]);
}

$(document).ready(function(){
var dd = ['free','i','tune'];

new Ajax_upload('#upload_button', {
//action: 'upload',
  action: '/zazz/producer/sampleAudio.html',
  // File upload name
  name: 'uploads[0]',
  // Additional data to send
  data: {
    js : '1',
    username : 'anonymous', 
    'method':'up'
  },
  // Fired when user selects file
  // You can return false to cancel upload
  // @param file basename of uploaded file
  // @param extension of that file
  onSubmit: function(file, extension) {
  },
  // Fired when file upload is completed
  // @param file basename of uploaded file
  // @param response server response
  onComplete: function(file, response) {alert(response);}

  });

});
</script>


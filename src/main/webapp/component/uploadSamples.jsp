<%@ include file="/common/taglibs.jsp"%>
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.ajax_upload.1.0.js'/>"></script>
	     
	     <script language="javascript">AC_FL_RunContent = 0;</script>

	 <script type="text/javascript" language="javascript"
	     src="<c:url value='/scripts/AC_RunActiveContent.js'/>"></script>


<ul>
	<li id="uploadline" class="example">
				<input id="button3" type="file" />
			<p>Uploaded files:</p>
			<ol class="files" id="unboundList" style='border: 1px solid green;'>
				<c:import url="/component/loops.html?method=listClipAudio"></c:import>
			</ol>
	</li>
</ul>
<div id="upload_button">Upload</div>
<script type= "text/javascript">/*<![CDATA[*/
var i = 0;
function showSwf(srcpath){
var spath = escape(srcpath);
return '<embed width="25" height="25" src="/zazz/loopButton.swf?path=' + spath+ '" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" align="middle" play="false" loop="true" scale="showall" wmode="window" devicefont="false" bgcolor="#fff" name="main7_v'+ (i++)+ '" menu="true" allowFullScreen="false" allowScriptAccess="always" salign="" type="application/x-shockwave-flash" ><'+'/embed>';
}

$(document).ready(function(){

	
	new Ajax_upload('#button3', {
		//action: 'upload.php',
		action: '/zazz/producer/sampleAudio.html', 
		name: 'uploads[0]',
		 data: {
    js : '1',
    username : '${SPRING_SECURITY_LAST_USERNAME}', 
    'method':'up',
    'clipId':  '${param.clipId}'
  },
		onComplete : function(file, response){
			var ary = [];
			eval ("ary = " + response +";");
	var ht = showSwf(ary[0].samplePath);
			$('<li></li>').appendTo($('#unboundList')).append(ht);
		}	
	});		
});

/*]]>*/</script>
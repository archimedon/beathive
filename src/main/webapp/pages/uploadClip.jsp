<%@ include file="/common/taglibs.jsp"%><head>
<title><fmt:message key="menu.producer.uploadclip"><fmt:param value="${storeForm.name}"/></fmt:message></title>
<content tag="heading"><fmt:message key="menu.producer.uploadclip"/></content>
<meta name="menu" content="ProducerHomeIn"/>
<script type="text/javascript">
$(document).ready(function(){
	function nextLine(){
		nextNum = parseInt($('#filecontainer').find('.inputline').length);
		$('<span class="inputline"><input type="file" name="uploads['+nextNum+']" class="file" id="uploads'+nextNum+'"/><'+'/span>').insertAfter('#uploads'+ (nextNum - 1));
		$('#addbox').hide();
		return false;
	}
	$('#addinput').click(nextLine);
	$('.inputline').change(function(){
		$('#addbox').show();
	});

var $out = $('#messages');
$('#sampleForm').ajaxForm({
	'beforeSubmit': function(a,f,o) {
		$out.html('<strong>Uploading for batch processing...<'+'/strong>');
	},
	'success': function(data) {
		$out.html('<b style="color:#000;">you may upload another 20+ megs<'+'/b><p><br/><strong>You will be notified by email when file(s) are processed.<'+'/strong><br/><'+'/p>' +  data);
	}
});	

});
</script>
<style type="text/css">
#uploadBody h3{font-size: 20px;text-decoration:none; border:none; padding: 10px 0;	}
#uploadBody dt{color: #000;font-size: 12px;}

/* the response from swfgen*/
#message {color: #000;font-size: 12px;
	padding: 5px 30px 0;
	font-size: 90%;
}
#message ul li{list-style-type: decimal;}

</style>
</head>
<body id="uploadBody">
<h3>Batch Upload</h3>

<%@ include file="/component/uploadhelp.jsp"%>

<div style="width:400px;margin-left:30px;" class="fl">
<form id="sampleForm" method="post" action="/zazz/producer/sampleAudio.html" enctype="multipart/form-data">
<table><tr>
<td width="300">
<%--div>
<fmt:message key="producer.uploadclip.bodymessage"/>
</div>
<br/ --%>
<p>
<div class="separator"></div>

<input type="hidden" name="username" class="medium" id="username" value="<security:authentication property="principal.username"/>"/>
<input type="hidden" name="userId" class="medium" id="userId" value="<security:authentication property="principal.id"/>"/>
<input type="hidden" name="storeId" class="medium" id="storeId" value="<c:out value='${storeForm.id}'/>"/>
 

</td>
<td width="600px" valign="top">
 <p>
<div id="filecontainer">
<span class="inputline"">
	<input type="file" name="uploads[0]" class="file" id="uploads0"/>
</span>
 <div id="addbox" style="display:none; margin:20px: padding:15px; width: 120px; text-align:center; size:20px; height: 21px; top: 15px;">
	<b><a id="addinput" name="addinput" href="#addinput">upload another</a></b>
</div>
</div>
</p><input name="method.batch" onclick="bCancel=false"  type="submit" value="upload files">
</td>
</tr>
</table>
<p class="fl" style="color:#000;">
* 20 - 25MB (uncompressed audio) can be uploaded per post.
</p>
<p class="fl" style="color:#000;">
** The same file can not be uploaded twice, duplicates will be rejected.
</p>
<p class="fl" style="color:#000;">
*** You will be notified when files are processed at the email address we have on record,,, usually within 12 hours.
</p>
</form>
</div>
</p>
<p>
<%-- upload output messages --%>
<div id="messages" class="fl" style="width:350px;border: 1px solid #bbb;display:block;margin: 15px 40px;">
</div>
</p>
</body>
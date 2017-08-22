<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="menu.producer.editstoreinfo"><fmt:param value="${storeForm.name}"/></fmt:message></title>
<%-- set the meta tag. indicates to nav the item to select --%>
<meta name="menu" content="ProducerHomeIn"/>

<script type= "text/javascript">
 //<![CDATA[
function imgObj(srcstr){
	return $("<img src='"+srcstr +"'/>").addClass('bannerImg');
}

function loadPic(fpath){
	$('#imgcell').html(imgObj(fpath).hide().show(100));
}

$(document).ready(function(){

	// on page load, if storeobject has an image
	if ($('#bannerImg').val() != ''){
		// , create an img tag then load it
		loadPic(CONTEXT_PATH +$('#bannerImg').val());
	}
	// setup upload params
	var uploadParams=	{
		dataType: "script",
		action: '<c:url value="/component/uploadFile.html"/>', 
		name: 'uploads[0]',
		data: {
			js : '1',
			username : '<c:out value="${username}"/>', 
			'type':  'banner'
		},
		autoSubmit: true,
		onComplete : function(file, response){
			var ary = [];
			eval ("ary = " + $(response).html());
			$('#bannerImg').val(ary[0]);
			var randomnumber=Math.floor(Math.random()*10001);
			$('#imgcell img').remove();
			setTimeout('loadPic("' + CONTEXT_PATH + ary[0] +"?r=" +randomnumber + '")', 700);
		}
	};
	
	// activate file-input button
	new Ajax_upload( '#bannerfile' , 	uploadParams	);

});
 //]]>
</script>
</head>
<html:form action="/producer/saveStore" method="post" styleId="storeForm" onsubmit="return validateStoreForm(this)">
<c:forEach items="${storeForm.comments}" var="comment">
<input type="hidden" name="commentIds" value="${comment.id}"/>
</c:forEach>
<html:hidden property="id"/>
<html:hidden property="bannerImg" styleId="bannerImg"/>
<html-el:hidden property="userId" styleId="userId" value="${uid}"/>
<html:hidden property="views" styleId="views"/>
<html:hidden property="statusId" styleId="statusId"/>
<html:hidden property="level" styleId="level"/>

<ul id="store_form_table" border="0" cellpadding="0" cellspacing="0">
	<li class="label">
		<html:hidden property="agreement" styleId="agreement"/>
		<beathive2:label styleClass="desc" key="storeForm.name"/>
		<html:errors property="name"/>
		<html:text property="name" styleId="name" styleClass="text medium"/>
	</li>
<%-- li>
		<beathive2:label styleClass="desc" key="storeForm.paymentEmail"/><html:errors property="paymentEmail"/>
		<html:text property="paymentEmail" styleId="paymentEmail" styleClass="text medium"/>
</li --%>
		<html:hidden property="paymentEmail" styleId="paymentEmail"/>
<li>
	<beathive2:label styleClass="desc" key="storeForm.description"/>
	<html:errors property="description"/>
	<html:textarea property="description" styleId="description" cols="40" rows="10" styleClass="aTextarea"/>
</li>
</ul>
<ul id="store_form_table_right" class="fr">
<li>
	<beathive2:label styleClass="desc" key="storeForm.bannerImg"/>
	<input id="bannerfile" type="file" size="10"/>	
</li>
<li id="imgcell" class="emptyImg"></li>
</li>
<li>
<br />
	<span class="RequiredFormField">*</span> = Required Field 
	<br /><br />
	<button class="button" name="method.save" onclick="bCancel=false"><fmt:message key="button.save"/></button>
	<br /><br />
</li>
</ul>

</html:form>
<html:javascript formName="storeForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<html:rewrite page="/scripts/validator.jsp"/>"></script>

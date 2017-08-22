<div id="shopSummary" class="rightColItem">
	<ul>
	<li><logic:present role="admin">
<h2>${storeForm.name} <small >producer:	<a title="become...${storeForm.producerName}" href="<c:url value='/admin/j_spring_security_switch_user'><c:param name='j_username' value='${storeForm.producerName}'/></c:url>">${storeForm.producerName}</a></small></h2>
</logic:present>
<logic:notPresent role="admin"><h2 class="showForm" ref="summarybody">${storeForm.name} <small>producer: ${storeForm.producerName}</small></h2>
</logic:notPresent></li>

	</ul>
	<ul id="summarybody">
<li class="separator"></li>
	<li><html-el:img page="${storeForm.bannerImg}" styleClass="bannerImg alignLeft"/>

	<div class="storeStats alignRight">
		Loops: <span class="keyword">${storeForm.numLoops}</span>
		<br/>Trackpacks: <span class="keyword">${storeForm.numTrackpacks}</span>
	</div>
	</li>
<li class="item">	<ul class="flat">
		<li><html:link forward="viewShopInfo"  paramId="storeId" paramName="storeForm" paramProperty="id">Info</html:link></li>
		<li><html:link action="/shop/comments" paramId="storeId" paramName="storeForm" paramProperty="id"><nobr><fmt:message key='storeForm.comments'/></nobr></html:link></li>
<c:choose><c:when test="${isMember}"><li><html:link styleId="contactProducerFormButton" titleKey="contactProducerForm.heading" action="/shop/contactProducer?recipientName=${storeForm.producerName}&amp;storeName=${storeForm.name}&amp;height=400&amp;width=500&amp;js=1&amp;modal=false" paramId="storeId" paramName="storeForm" paramProperty="id" styleClass="thickbox"><nobr><fmt:message key='contactProducerForm.heading'/></nobr></nobr></html:link></li>
</c:when>
<c:otherwise>
		<li><a title="errors.login.required" class="notLoggedIn"><nobr><fmt:message key='contactProducerForm.heading'/></nobr></nobr></a></li>
</c:otherwise>
</c:choose>
		
	</ul>
	</li>
</ul>
</div>
<script type="text/javascript">
 //<![CDATA[
function cleanUp(id){
	self.parent.tb_remove();
//	$('#TB_window').remove('click');
}
$(function(){
<security:authorize ifAnyGranted="user,producer,admin">
$('#contactForm input:submit').live('click' , function(){
	 var form = this.form;
		var data = {'js':'1' };
		var url = this.action;
		$('input , textarea' , form).each(function(){
			data[$(this).attr('name') || $(this).attr('id')] =$(this).attr('value');
		});
		$.post(url , data , function(resp , stat){
			if (stat=='success'){
				var box = $('#contactBox');
				$('#disclaimer').html('<h1>message sent<'+'/h1>');
				setTimeout("cleanUp('contactBox')" , 1000);
			}
		});
		return false;
	});

function closer(but){
	var box = $('#contactBox');
	box.find('form').remove();
	box.hide(10);
}

</security:authorize>     
<security:authorize ifNotGranted="user,producer,admin">
	$('#contactProducerFormButton').click(handleNotLoggedIn);
</security:authorize>     
});

function closeContact(but){
	$(but).parents('div').html().hide();
}
 //]]>
</script>
<div id="contactBox"><h3 class="button" onclick="closeContact(this);">close</h3></div>

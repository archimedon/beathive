<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="userProfile.title"/></title>
    <content tag="heading"><fmt:message key="userProfile.heading"/></content>
    <meta name="menu" content="UserMenu"/>
    <script type="text/javascript" src="<c:url value='/scripts/jquery.jeditable.js'/>"></script>
    
    <script type="text/javascript">
    var prompts = [{'key':'validate', 'value':'Validating...'} , {'key':'verify', 'value':'Verifying'}];
    
$(function(){
//	$('#email').click(function(){});
$('#validateButton').val('validate');
	$('#validateButton').live('click' , function(){
		var $mainElem = $('#email');
		if($.data(this, 'clicked')){
			  return false;
			}
			else{
				
				var button = this;
				var form =button.form;
				var outmsg = $('#messageOutput' , form);
			
				$(form).ajaxSubmit({
					// ary_values : [{ name: 'pname0', value: 'pvalue0' }, { name: 'pname1', value: 'pvalue1' }]
					'beforeSubmit': function(ary_values,formObject,ajaxOptions) {
						ajaxOptions.dataType ='json';
						ary_values.push({'name':button.name , 'value':'1'});
						$.data(this, 'clicked', true);
						var i = -1; 
						for ( i in prompts){		
							if (prompts[i].key == button.value){
								break;
							}
						}
						if(i==0){
							outmsg.html(prompts[i].value + ' '+ form.elements['newemail'].value);
						}else{
							outmsg.html(prompts[i].value + ' ' +  form.elements['newemail'].value );
						}
					}	,	
					'success': function(jresp, statusText)  { 
						if(jresp.error){
							outmsg.html(jresp.error);
							switch (jresp.errorcode){
								case 'keyrequired':{
									$(form.elements['newemail'])
									.attr('disabled','disabled')
									.parent('li').	hide('fast',function(){
										$('#f2').fadeIn().children('input').removeAttr('disabled');
										button.value=prompts[1].key;
									});
									break;
								}
								case 'nonunique' :{
									break;
								}
								case 'nochange':{
									break;
								}
							}
						}else{
							if(button.value == prompts[0].key){
								$(form.elements['newemail']).attr('disabled','disabled').parent('li').hide('fast',function(){
								outmsg.html(jresp.message)
									$('#f2').fadeIn().children('input').removeAttr('disabled');
									button.value=prompts[1].key;
								});
							}else{
								$mainElem.val(jresp.value);
								self.parent.tb_remove();
								$('#TB_window').remove('click');
							}
						}
						$.data(this, 'clicked', false);
						return false;
					}	,
					'error': function()  { 
						$.data(this, 'clicked', false);
						return false;
					}
					



				});
			}
			return false;
	});

});
    </script>
    <style>
    label#email{
    	border: 1px inset #666;
    	background-color: #fff;
    	color:#000;
    	padding:3px;
    	width: 174px;
    	font-size: 115%;
    }
    #confirmButton{
    	display: none;
    }
    </style>
</head>
<body id="profileFormPage">
<%@ include file="/WEB-INF/pages/user/tabs.jsp"%>
		<!-- conditional message -->
			<%@ include file="/common/messages.jsp" %>

<html:form action="saveUser" styleId="userForm" onsubmit="return validateUserForm(this)">
<html:hidden property="id"/>
<html:hidden property="version"/>
<input type="hidden" name="from" value="${param.from}"/>

<c:if test="${cookieLogin == 'true'}">
    <html:hidden property="password"/>
    <html:hidden property="confirmPassword"/>
</c:if>


<ul>
    <li class="buttonBar right">
        <%-- So the buttons can be used at the bottom of the form --%>
        <c:set var="buttons">
            <html:submit styleClass="button" property="method.save" onclick="bCancel=false">
                <fmt:message key="button.save"/>
            </html:submit>

            <c:if test="${param.from == 'list' and param.method != 'Add'}">
            <html:submit styleClass="button" property="method.delete" onclick="bCancel=true; return confirmDelete('User')">
                <fmt:message key="button.delete"/>
            </html:submit>
            </c:if>

            <html:cancel styleClass="button" onclick="bCancel=true">
                <fmt:message key="button.cancel"/>
            </html:cancel>
        </c:set>
        <%-- c:out value="${buttons}" escapeXml="false"/ --%>
    </li>
    <li class="info">
        <c:choose>
            <c:when test="${param.from == 'list'}">
                <p><fmt:message key="userProfile.admin.message"/></p>
            </c:when>
            <c:otherwise>
                <p><fmt:message key="userProfile.message"/></p>
            </c:otherwise>
        </c:choose>
    </li>
    <li>
        <beathive2:label styleClass="desc" key="userForm.username"/>
        <html:errors property="username"/>
        <html:text styleClass="text large" property="username" styleId="username"/>
    </li>
    <c:if test="${cookieLogin != 'true'}">
    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="userForm.password"/>
                <html:errors property="password"/>
                <html:password styleClass="text medium" property="password" 
                    styleId="password" redisplay="true"/>
            </div>
            <div>
                <beathive2:label styleClass="desc" key="userForm.confirmPassword"/>
                <html:errors property="confirmPassword"/>
                <html:password styleClass="text medium" property="confirmPassword" styleId="confirmPassword" redisplay="true"/>
            </div>
        </div>
    </li>
    </c:if>
    <li>
        <beathive2:label styleClass="desc" key="userForm.passwordHint"/>
        <html:errors property="passwordHint"/>
        <html:text styleClass="text large" property="passwordHint" styleId="passwordHint"/>
    </li>
    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="userForm.firstName"/>
                <html:errors property="firstName"/>
                <html:text styleClass="text medium" property="firstName" styleId="firstName" maxlength="50"/>
            </div>
            <div>
                <beathive2:label styleClass="desc" key="userForm.lastName"/>
                <html:errors property="lastName"/>
                <html:text styleClass="text medium" property="lastName" styleId="lastName" maxlength="50"/>
            </div>
        </div>
    </li>
    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="userForm.email"/>
                <html:errors property="email"/>

                 
<html:text styleClass="text medium" property="email" styleId="email" maxlength="50" value="${userForm.email}" readonly="true"/>
                 <input alt="/user/changeEmail.html?method=confirm&js=1&height=200&width=300&inlineId=content&modal=false" title="Validate Email" id="changebutton" class="thickbox" type="button" value="<fmt:message key='userForm.validate.email'/>" />  
                 
            </div>
            <div><br clear="all"/>
            </div>
        </div>
    </li>
<c:if test="${not empty userForm.address}">
<html-el:hidden property="addressForm.userId" value="${userForm.id}"/>
<html-el:hidden property="preferenceForm.userId" value="${userForm.id}"/>

    <li>
        <div>
            <div class="left">
                <beathive2:label styleClass="desc" key="addressForm.phone"/>
                <html:errors property="addressForm.phone"/><html:text styleClass="text small" property="addressForm.areacode" styleId="addressForm.areacode"/>
                <html:text styleClass="text" property="addressForm.phone" styleId="addressForm.phone"/>
            </div>
            <div><br clear="all"/>
            </div>
        </div>
    </li>
    <li>
        <label class="desc"><fmt:message key="userForm.addressForm.address"/></label>
        <div class="group">
            <div>
                <html:errors property="addressForm.street1"/>
                <p><beathive2:label key="userForm.addressForm.street1"/></p>
                <html:text styleClass="text large" property="addressForm.street1"
                    styleId="addressForm.street1"/>
            </div>
            <div class="left">
                <html:errors property="addressForm.city"/>
                <p><beathive2:label key="userForm.addressForm.city"/></p>
                <html:text styleClass="text medium" property="addressForm.city"
                    styleId="addressForm.city"/>
            </div>
            <div>
                <html:errors property="addressForm.province"/>
                <p><beathive2:label key="userForm.addressForm.province"/></p>
                <c:choose>
					<c:when test="${userForm.addressForm.country=='CA'}">
						<html:select property="addressForm.province" styleId="province" styleClass="select medium">
						<html:options collection="candianTerritories" property="value" labelProperty="label" />
						</html:select>
					</c:when>
					<c:when test="${userForm.addressForm.country=='US'}">
						<html:select property="addressForm.province" styleId="province" styleClass="select medium">
						<html:options collection="states" property="value" labelProperty="label" />
						</html:select>
					</c:when>
					<c:otherwise>
						<html:text property="addressForm.province" styleClass="text medium" size="18" maxlength="18"/>
					</c:otherwise>
				</c:choose>
                
            </div>
            <div class="left">
                <html:errors property="addressForm.postalCode"/>
                <p><beathive2:label key="userForm.addressForm.postalCode"/></p>
                <html:text styleClass="text medium zip" property="addressForm.postalCode"
                    styleId="addressForm.postalCode"/>
            </div>
            <div>
                <html:errors property="addressForm.country"/>
                <p><beathive2:label key="userForm.addressForm.country"/></p>
                <html:select property="addressForm.country" name="userForm" styleClass="select">
                    <html:option value=""/>
                    <html:options collection="countries"
                        property="value" labelProperty="label"/>
                </html:select>
            </div>
        </div>
    </li>
</c:if>
<c:choose>
    <c:when test="${param.from == 'list' or param.method == 'Add'}">
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.accountSettings"/></legend>
            <html:checkbox styleClass="checkbox" property="enabled" styleId="enabled"/>
            <label for="enabled" class="choice"><fmt:message key="userForm.enabled"/></label>

            <html:checkbox styleClass="checkbox" property="accountExpired" styleId="accountExpired"/>
            <label for="accountExpired" class="choice"><fmt:message key="userForm.accountExpired"/></label>

            <html:checkbox styleClass="checkbox" property="accountLocked" styleId="accountLocked"/>
            <label for="accountLocked" class="choice"><fmt:message key="userForm.accountLocked"/></label>

            <html:checkbox styleClass="checkbox" property="credentialsExpired" styleId="credentialsExpired"/>
            <label for="credentialsExpired" class="choice"><fmt:message key="userForm.credentialsExpired"/></label>
        </fieldset>
    </li>
    <li>
        <fieldset>
            <legend><fmt:message key="userProfile.assignRoles"/></legend>
            <c:forEach var="role" items="${availableRoles}">
                <html-el:multibox styleClass="checkbox" property="userRoles" styleId="${role.label}">
                    ${role.value}
                </html-el:multibox>
                <label class="choice" for="${role.label}">
                    ${role.label}
                </label>
            </c:forEach>
        </fieldset>
    </li>
    </c:when>
    <c:when test="${not empty userForm.id}">
    <li>
        <strong><fmt:message key="userForm.roles"/>:</strong>
        <c:forEach var="role" items="${userForm.roles}" varStatus="status">
            ${role.name}<c:if test="${!status.last}">,</c:if>
            <input type="hidden" name="userRoles" value="${role.name}"/>
        </c:forEach>
        <c:forEach var="shop" items="${userForm.stores}" varStatus="status">
           <%--  ${shop.id}<c:if test="${!status.last}">,</c:if> --%>
            <input type="hidden" name="userStores" value="${shop.id}"/>
        </c:forEach>
        <html:hidden property="enabled"/>
        <html:hidden property="accountExpired"/>
        <html:hidden property="accountLocked"/>
        <html:hidden property="credentialsExpired"/>
    </li>
    </c:when>
</c:choose>
    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</ul>
</html:form>

<script type= "text/javascript">
//<![CDATA[
$(document).ready(function(){
	$('#firstName').focus();
	$('#password').change(function(){
		var origPassword = "${userForm.password}";
		if(this.value != origPassword) {
			$('#userForm').append('<input type="hidden" name="encryptPass" id="encryptPass" value="true"/>');
		}
	});
});
//]]>
</script>
<html:javascript formName="userForm" cdata="false" dynamicJavascript="true" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>
</body>

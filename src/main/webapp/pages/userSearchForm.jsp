<%@ include file="/common/taglibs.jsp"%>
<fmt:setLocale value="en_US" scope="session"/>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.admin.salesreports"/></title>
    <content tag="heading"><fmt:message key="menu.admin.salesreports"/></content>
	<meta name="menu" content="AdminMenu"/>

	
	<link type="text/css" href="<c:url value='/scripts/ui/themes/base/ui.all.css'/>" rel="stylesheet" />
	
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.core.js'/>"></script>
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.datepicker.js'/>"></script>
<script type="text/javascript">		
$.fn.serialForm = function(){
	var params = {};
	$(this).find("input").each(function(){
	if($(this).attr('type')=='radio'){
		if($(this).attr('checked')){params[this.name] = this.value;}
	}else{
		if (this.value) {params[ this.name || this.id || this.parentNode.name || this.parentNode.id ] = this.value;}
	}
	});
	return params;
}

function jaxTab(url){
	var params =qparam(url.slice(1));
	$("#reportResult").trigger("loadPage", [$('#salesReportForm').attr('action') , params ]);
	return false;
}
$(function() {
  	var  d = new Date(2005, 8 - 1, 25);
	$.datepicker.setDefaults({
		showButtonPanel: false ,
		changeYear: true ,
		altFormat:'yyyy-mm-dd'
	});
			
	$('#reportResult').bind("loadPage", function(e, url , params){
		params['js']= 1;
		var flip = params['dir'] == 'asc';
				
		$.post(url , params , function(dat){
			$('#reportResult').html($('#itemlist' , dat).html() );
		});
	});

	$('#runreport').click(function(){

		var url = this.form.action;
		var params =$('#salesReportForm').serialForm();
		$("#reportResult").trigger("loadPage", [this.form.action , params ]);
		return false;
	});
	
	$('#enddate').datepicker();//.datepicker( 'setDate' ,  1);
	$('#startdate').datepicker();//.datepicker( 'setDate' ,  -182);
	
	$('.pagelinks a, .sortable a').live('click' , function(){
		$('#reportResult').load(this.href);
		return false;
	});
});	
</script>
</head>
<h1>Search Users</h1>
<br/>
<div id="salesReportPage">
<div class="alignLeft">
<html:form action="/admin/users"  styleId="salesReportForm">
<ul>
<li>
<fieldset class="ltr">
<beathive2:label key="storeForm.startDate"/>: <input type="text" id="startdate" name="startDate" size="10" value=""/>
<beathive2:label key="storeForm.endDate"/>: <input type="text" id="enddate" name="endDate" size="10" value=""/>
<br/>
firstName: <html:text styleId="firstName" styleClass="text medium" property="firstName"/>
<br/>
lastName: <html:text styleId="lastName" styleClass="text medium" property="lastName"/>
<br/>
username: <html:text styleId="username" styleClass="text medium" property="username"/>
<html:hidden styleId="sort" styleClass="text medium" property="sort" value="created"/>
<html:hidden styleId="dir" styleClass="text medium" property="dir" value="asc"/>
</fieldset><fieldset class="ltr">
            <legend><fmt:message key="userProfile.assignRoles"/></legend>
            <c:forEach var="role" items="${availableRoles}">
                <input type="radio" name="roleName" value="${role.value}" id="${role.label}"/>                   
                <label class="choice" for="${role.label}">
                    ${role.label}
                </label>
            </c:forEach>
        </fieldset>

</li>
<li>
<%-- html:hidden styleId="storeId" property="storeId" value="${storeForm.id}"/ --%>
</br>
<input type="submit" id="runreport" name="method.search" value="get list"/>
</br>
<%-- view performance: <input type="checkbox" id="perf" name="groupByClip"/> --%>
</br>
</li>
</ul>
</html:form>
</div>
<%-- The Elem that holds store stats. these are based on given date range --%>
<div class="ltr" id="stat" ></div>
<br clear="all"/>
<%-- The Elem that holds the result table --%>
<div id="reportResult"></div>
</div>
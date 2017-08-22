<%@ include file="/common/taglibs.jsp"%>
<fmt:setLocale value="en_US" scope="session"/>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.admin.salesreports"/></title>
    <content tag="heading"><fmt:message key="menu.admin.salesreports"/></content>
	<meta name="menu" content="AdminMenu"/>

	
	<link type="text/css" href="<c:url value='/scripts/ui/themes/base/ui.all.css'/>" rel="stylesheet" />
	
<script type="text/javascript">
$(function() {
	
  	var  d = new Date(2007, 8 - 1, 25);
		$.datepicker.setDefaults({
			showButtonPanel: false ,
			changeYear: true ,
			minDate: d ,
			altFormat:'yyyy-mm-dd'
		});
	
	$('#enddate').datepicker().datepicker( 'setDate' ,  1);
	$('#startdate').datepicker().datepicker( 'setDate' ,  -182);
	
	$('.sortable>*').live('click' , function(){
		$('#reportResult').load(this.href);
		return false;
	});
	
	$('.pagelinks a').live('click' , function(){
		$('#reportResult').load(this.href);
		return false;
	});


    // bind form using 'ajaxForm' 
    $('#salesReportForm').ajaxForm({target:'#reportResult'}); 

});	

	</script>
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.core.js'/>"></script>
	<script type="text/javascript"
		 src="<c:url value='/scripts/ui/ui.datepicker.js'/>"></script>
</head>
<h1>Compile Sales Report</h1>
<br/>
<div id="salesReportPage">
<div class="alignLeft">
<html:form action="/admin/salesReport"  styleId="salesReportForm">
<ul>
<li>
<fieldset>
<beathive2:label key="storeForm.startDate"/>: <input type="text" id="startdate" name="startDate" size="10" value=""/>
<beathive2:label key="storeForm.endDate"/>: <input type="text" id="enddate" name="endDate" size="10" value=""/>
</fieldset>
</li>
<li>
<html:hidden styleId="sort" property="sort" value="created"/>
<html:hidden styleId="size" property="size" value="20"/>
<html:hidden styleId="js" property="js" value="1"/>
</br>
<input type="submit" id="runreport" name="method.sales" value="view report"/>
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
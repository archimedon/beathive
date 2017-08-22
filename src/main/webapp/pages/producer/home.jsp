<%@ include file="/common/taglibs.jsp"%>
<logic:present role="producer"><c:set var="isProducer" value="${true}"/><c:if test="${empty storeMenu}"><c:redirect url="/"/></c:if></logic:present>
<c:choose>
<c:when test="${isProducer}">
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.producer.home"><fmt:param value="${storeForm.name}"/></fmt:message></title>
    <content tag="heading"><fmt:message key="producerHome.heading"><fmt:param value="${storeForm.name}"/></fmt:message></content>
    <meta name="menu" content="ProducerHomeIn"/>
    <script type="text/javascript"
    src="<c:url value='/dwr/interface/WebService.js'/>"> </script>
<script type="text/javascript"
    src="<c:url value='/dwr/engine.js'/>"> </script>
      <script type='text/javascript' src='/dwr/util.js'></script>
<script type="text/javascript">
    
$(function(){

	WebService.getProducerInventoryCounts('${storeForm.id}' , function(obj){
		for (var t in obj){
			$('#'+t).html(obj[t]);
		}
	});


	$('.unpacked').click(function(){
		if ($(this).text()!='0'){
			window.location.href=this.href;
		}
		return false;
	});
});
</script>
</head>
<%@ include file="/WEB-INF/pages/producer/home_body.jsp"%>
</c:when>
<c:otherwise>
	<head>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<title><fmt:message key="producerMenu.title"/></title>
		<content tag="heading"><fmt:message key="producerMenu.heading"/></content>
		<meta name="menu" content="ProducerHome"/>
	</head>
<body id="setupshopPage">
	<c:import url="/WEB-INF/pages/producer/setupshop_instr.jsp"/>
	<div class="buttonbar"><html:link forward="agreementForm" styleClass="button"><fmt:message key="setup.shop.button"/></html:link></div>
</body>
</c:otherwise>
</c:choose>

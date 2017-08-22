<%@ include file="/common/taglibs.jsp"%>

<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><fmt:message key="menu.producer.reports"><fmt:param value="${storeForm.name}"/></fmt:message></title>
    <content tag="heading"><fmt:message key="producerMenu.heading"/></content>
	<meta name="menu" content="ProducerHomeIn"/>
</head>
<ul class="glassList">
<li>
         <a href="<c:url value="/producer/reports.html?method.sales"/>">sales</a>
    </li>
</ul>

<div class="glassList">
<h1>${storeForm.name}</h1>
<html:img page="${storeForm.bannerImg}" styleClass="bannerImg alignLeft"/>
Total # Loops: ${storeForm.numLoops}
Total # Packs: ${storeForm.numTrackpacks}
# Visits: ${storeForm.views}
</div>

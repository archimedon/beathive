<%@ include file="/common/taglibs.jsp"%>
<head>
<%-- title><fmt:message key="visit.shop.title"><fmt:param value="${storeForm.name}"/></fmt:message></title --%>
<title><fmt:message key="storeForm.info.title"><fmt:param value="${storeForm.name}"/></fmt:message></title>
<content tag="heading"><fmt:message key="storeForm.info.title"><fmt:param value="${storeForm.name}"/></fmt:message></content>
</head>
<%@ include file="/WEB-INF/pages/store/tabs.jsp"%>
<h3>${storeForm.name}</h3>
<table align="left" border="5" bordercolor="#ffffff" cellpadding="5" cellspacing="0" width="100%">
<tbody><tr>
	<td><strong>Producer:</strong></td>
	<td>${storeForm.producerName}</td>

</tr>

<tr>
	<td><strong>Average:</nobr></strong></td>
	<td valign="top">${storeForm.average}</td>
</tr>
<tr>
	<td><strong>Open Since:</strong></td>
	<td>${storeForm.created}</td>
</tr>
<tr>
	<td><strong>#comments:</strong></td>
	<td>${fn:length(storeForm.comments)}</td>
</tr>
<tr>
	<td><strong>#numTrackpacks:</strong></td>
	<td>${storeForm.numTrackpacks}</td>
</tr>
<tr>
	<td><strong>#numLoops:</strong></td>
	<td>${storeForm.numLoops}</td>
</tr>
<tr>
	<td valign="top"><strong>Description:</strong></td>
	<td><div style="width: 100%;">${storeForm.description}</div></td>
</tr>
</tbody></table>

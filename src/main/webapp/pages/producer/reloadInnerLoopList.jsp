<jsp:scriptlet><![CDATA[ String ctype ="text/plain;";   response.setContentType(ctype + "charset=UTF-8"); ]]></jsp:scriptlet><%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %><%@ taglib uri="http://struts.apache.org/tags-tiles-el" prefix="tiles" %><%@ taglib uri="http://struts.apache.org/tags-bean-el" prefix="bean-el" %><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %><%@ taglib uri="http://struts.apache.org/tags-html-el" prefix="html-el" %><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %><%@ taglib uri="http://struts.apache.org/tags-logic-el" prefix="logic-el" %><%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %><%@ taglib uri="http://displaytag.sf.net" prefix="display" %><%@ taglib uri="http://struts-menu.sf.net/tag-el" prefix="menu" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %><%@ taglib uri="http://www.opensymphony.com/oscache" prefix="cache" %><%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%><%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%><%@ taglib uri="/WEB-INF/beathive2.tld" prefix="beathive2" %>
<c:set var="uid"><security:authentication property="principal.id"/></c:set><display:table name="innerLoopList" id="loop" htmlId="loops_${tpclip.id}" cellspacing="0" cellpadding="0" pagesize="10" class="innerLoopList producercliptable" requestURI="/producer/viewComponents.html">
    <%-- COL: 0 - loop ready/not ready cell --%>
	<display:column titleKey="soundClipForm.ready" sortProperty="ready" sortable="false" headerClass="readyHeaderClass"><div class="${loop.ready}Ready" id="stat_${loop.id}">&nbsp;</div></display:column>
	<%-- COL: 1 - playbuttons.--%>
   	<display:column class="swfAudioCell" headerClass="playbuttonHeaderClass">
		<table class="swfarea" ref="emb_${loop.id}" id="${loop.id}">
			<tr><c:forEach items="${loop.audioFormat}" var="loopsam" varStatus="stat">
			<td>
				<div class="player"  id="swf_${loop.id}_${loopsam.formatId}">
					<c:import url="/component/playbutton.jsp">
						<c:param name="path" value="${loopsam.samplePath}"/>
						<c:param name="oid" value="${loop.id}_${loopsam.formatId}"/>
						<c:param name="js" value="1"/>
					</c:import>
					</div>
					${loopsam.formatId}
				</td>
			</c:forEach></tr>
		</table>	
		<%-- loop data to JS array <c:if test='${! loop.ready}'>$('#stat_${loop.id}').addClass('falseReady');</c:if> --%>
		<script type="text/javascript">//<![CDATA[
		window.CLIPS[${loop.id}]=${loop.JSON};
		//]]></script>
	</display:column>
	<%-- COL: 2 - name & edit-button.--%>
	<display:column titleKey="soundClipForm.name" class="nameCell" sortable="true" sortProperty="name"  headerClass="nameHeaderClass">
		<html:text property="attrib(name)" value="${loop.name}" styleClass="attrib" size="25"/>
		<label  class="loop_attribs" id="edit_${loop.id}"  ref="emb_${loop.id}">edit</label>
	</display:column>

	<display:column titleKey="soundClipForm.instrument" class="instrumentgroup" headerClass="instrumentHeaderClass">
		<c:choose>
			<c:when test="${not empty loop.instrument}">
				<input class="attrib instrument" type="text" name="gid=${loop.instrument.groupId}&instrumentId=${loop.instrument.id}&id=${loop.id}" value="<fmt:message key="${loop.instrument.labelKey}"/>"/>
			</c:when>
			<c:otherwise><span name="id=${loop.id}"><img src="<c:url value='/images/exclamation.jpg'/>" height="17px" /><input type="text" class="errorReq" size="25" value="Select Instrument"/></span>
			</c:otherwise>
		</c:choose>
	</display:column>

    <display:column titleKey="soundClipForm.genre" class="genreCell" headerClass="genreHeaderClass"><c:set var="gfull" value="${true}" scope="request"/><c:set var="genrelist" value="${loop.genre}" scope="request"/><%@ include file="/WEB-INF/pages/listcomponents/genres.jsp"%></display:column>
    <display:column titleKey="soundClipForm.bpm" class="bpmCell" headerClass="bpmHeaderClass"><html:text property="attrib(bpm)" value="${loop.bpm}" styleClass="attrib" size="4"/></display:column>
    
    <display:column titleKey="soundClipForm.price" class="priceCell" headerClass="priceHeaderClass">
    <fmt:formatNumber currencyCode="USD" minFractionDigits="2" maxFractionDigits="2" value="${loop.priceForm.amount}" var="amt"/>
    <input type="text" name="attrib(price.amount)" value="${amt}" class="attrib" size="4"/></display:column>
    
 <display:column titleKey="soundClipForm.timesSold" property="timesSold" sortable="true" headerClass="salesHeaderClass"/>
	<display:column titleKey="soundClip.created" class="createdCell" headerClass="createdHeaderClass" sortProperty="created" property="created" sortable="true"/>

<display:column headerClass="deleteHeaderClass" class="rem_${loop.id}"><c:url var="dlink" value='/producer/confirmDelete.html'>
<c:param name="id" value="${loop.id}"/>
<c:param name="name" value="${loop.name}"/>
<c:param name="height" value="150"/>
<c:param name="width" value="300"/>
<c:param name="js" value="1"/>
<c:param name="modal" value="false"/>
<c:param name="type" value="loop"/></c:url><a class="thickbox" href="${dlink}"><html:img page="/images/BUTTONS/trash.gif" titleKey="soundClipForm.delete" width="15px"/></a></display:column>

<display:caption><span ref="par-emb_${tpclip.id}" id="node-000-${tpclip.id}" class="child-of-node-${tpclip.id} looprow addrow"><form class="uploader" method="post" action="/zazz/producer/sampleAudio.html" ref='<c:url value="/producer/editLoop.html"/>' enctype="multipart/form-data">
<input type="hidden" name="username" value="${pageContext.request.remoteUser}"/>
<input type="hidden" name="trackpackId" value="${tpclip.id}"/>
<input type="hidden" name="storeId" value="${sessionScope.storeForm.id}"/>
<input type="hidden" name="userId" value="${uid}"/>
<input type="hidden" name='method' value="addComp"/>
<input type="hidden" name='js' value="1"/>
	<h5 for="uploadin" class="uploadOutput" id="uploadComponentOutput${tpclip.id}"><fmt:message key="menu.producer.addcomponent"/>:</h5><input class="button" size=4  ref="uploadComponentOutput${tpclip.id}" onchange="uploadFile(this , ['mg']);" name="uploads[0]" type="file">
</form></span>
</display:caption>
<%--    
<display:column title="created" property="created" sortable="true"/>
--%>

    <display:setProperty name="sort.amount" value="list"/>
    <display:setProperty name="paging.banner.item_name" value="loop"/>
    <display:setProperty name="paging.banner.items_name" value="loops"/>

</display:table>

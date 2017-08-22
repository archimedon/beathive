<%@ page language="java"
	import="com.beathive.webapp.form.*
	,java.util.*
	"%>
<%@ include file="/common/taglibs.jsp"%>
<%
InstrumentgroupForm igf = new InstrumentgroupForm();
igf.setId(request.getParameter("gid"));
List gs = (List) ((Map) application.getAttribute("DESCRIPTORS_KEY")).get("instrumentgroup");

int y = gs.indexOf(igf);
igf = (InstrumentgroupForm)gs.get(y);
pageContext.setAttribute("instlist" , igf.getInstruments());
%>
<select name="attrib(instrument.id)" id="instrumentMenu">
<c:forEach items="${instlist}" var="instr">
<option value="${instr.id}"<c:if test="${instr.id eq param.instrumentId}"> selected='selected'</c:if>><fmt:message key="${instr.labelKey}"/></option>
</c:forEach>
</select>
<%--
<script type="text/javascript">
$(document).ready(function(){
var style ={'color':'green', 'background-color':'white'};
var styleFocus ={'color':'black', 'background-color':'grey'};
	$('#instrumentMenu')
	.css(style)
	.focus(function(){
		var field = this;
		$(field).css(styleFocus)
		.one('change' , function(){
			var data={};
		
		$('#subMenu').load('<c:url value="/component/instruments.jsp"/>' , data , function(){
			
		});
		
		});

	});

});
</script>
--%>
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
List inslist =  new LinkedList();
Map tmp = new HashMap();
tmp.put("id" ,"");
tmp.put("labelKey","prompt.selectInstrument");
inslist.add(tmp);
inslist.addAll((List)igf.getInstruments());
pageContext.setAttribute("instlist" , inslist);
%>
<select name="attrib(instrument.id)" id="instrumentMenu" class="criteria" onchange="doUpdate();">
<c:forEach items="${instlist}" var="instr">
<option value="${instr.id}"<c:if test="${instr.id eq param.instrumentId}"> selected='selected'</c:if>><fmt:message key="${instr.labelKey}"/></option>
</c:forEach>
</select>

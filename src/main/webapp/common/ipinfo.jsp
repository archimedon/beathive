<%@ page language="java" pageEncoding="UTF-8" contentType="text/xml;charset=utf-8"
import="java.util.*,org.apache.struts.util.MessageResources,java.util.regex.* ,
				org.apache.struts.Globals"%>
<%@ include file="/common/taglibs.jsp"%>
<%!
public static String countryImgPattern = "gfx/flags/([a-zA-Z]+).png";
public static Pattern cp = Pattern.compile(countryImgPattern);

public aBaseNode getAsNode(String txt , String id) {
	Matcher m = cp.matcher(txt);
	aBaseNode header = new BaseNode("country");

	if (m.find()) {
		header.setAttribute("id",id);
		header.setAttribute("code",m.group(1).toUpperCase());
	}
	return header;
}

public String getAsString(String txt , String id) {
	Matcher m = cp.matcher(txt);
	String str = "";
	
	if (m.find()){
		str = m.group(1).toUpperCase();
	}
	return str;
}
%>
<c:import var="txtResp" url="http://www.selfseo.com/ip_to_country.php?ip=${param.ip}"/>
<%=getAsString((String)pageContext.getAttribute("txtResp"), request.getParameter("ip"))%>

<%@ page pageEncoding="UTF-8" contentType="text/plain;charset=utf-8" import="java.util.*,java.util.regex.*,java.net.*,java.io.*" %><%!
public static String countryImgPattern = "img src=gfx/flags/([a-zA-Z]+).png";
public static Pattern cp = Pattern.compile(countryImgPattern);%><%
URL url = new URL("http://www.selfseo.com/ip_to_country.php");  
URLConnection connection = url.openConnection();
connection.setDoOutput(true);
OutputStreamWriter cout = new OutputStreamWriter(connection.getOutputStream());
cout.write("ip="+request.getParamter("ip"));
cout.close();
BufferedReader in = new BufferedReader(
			new InputStreamReader(
			connection.getInputStream()));
String decodedString;
while ((decodedString = in.readLine()) != null) {
	Matcher m = cp.matcher(decodedString);
	if (m.find()){
		out.println(m.group(1).toUpperCase());
		break;
	}
}
in.close();%><%-- Looks up a country code using IP address --%>
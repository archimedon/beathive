<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ page import="org.apache.commons.beanutils.BeanUtils, org.apache.commons.beanutils.*,java.beans.PropertyDescriptor,com.beathive.webapp.form.*" %>
<%!

public String quote(String str){
	return "'" + str + "'";
}
public String pair(String str1 , String str2){
	return quote(str1) + ":" + quote(str2);
}
public String getJSON(SoundClipForm clip) {
	String[] attribs = {"id","storeId","name","modified","created","statusId","searchable","keyword","numFormats","score",	"timesrated","bpm","keynote","scale",
	"timesignature","passage", "energy","feel","mood","origin","sonority",
			"texture","viewerScore","ownedByViewer","AFavorite" , "priceForm"};
	Object o = null;
	StringBuffer buf = new StringBuffer("{");
	for (int y = 0; y < attribs.length; y++){
		String key= attribs[y];
		try {
			o = PropertyUtils.getProperty(clip , key);
			if (o!=null){
				if (key.equals("priceForm")){
					buf.append(pair(key ,  ((PriceForm)o).getAmount()));
				}else{
					buf.append(pair(key , o.toString()));
				}
			}
			else{
				buf.append(pair(key , ""));
			}

		} catch (Exception e) {
			buf.append(key + ":error");
			e.printStackTrace();
		}
		if ((y+1)<attribs.length){
			buf.append(",");
		}
	}
	buf.append("}");
	return buf.toString();
}
%>

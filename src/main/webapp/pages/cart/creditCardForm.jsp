			<% java.util.List ccs = new java.util.LinkedList();
			ccs.add(new com.beathive.model.LabelValue("Visa","Visa"));
			ccs.add(new com.beathive.model.LabelValue("Master Card","Master Card"));
			ccs.add(new com.beathive.model.LabelValue("American Express","American Express"));
			pageContext.setAttribute("ccs",ccs);
			%>
<%
int jan = 1;
java.util.List m = new java.util.LinkedList();
String mm;
for (int y=0;y <12;y++){
	mm = "" + (jan + y);
	m.add(new com.beathive.model.LabelValue(mm, mm));
}
pageContext.setAttribute("monthList" , m);
%>

<%
java.util.Calendar cal = java.util.Calendar.getInstance();
int start = cal.get(java.util.Calendar.YEAR);
java.util.List l = new java.util.LinkedList();
String year;
for (int y=0;y <7;y++){
	year = "" + (start + y);
	l.add(new com.beathive.model.LabelValue(year,year));
}
pageContext.setAttribute("yearList" , l);
%>

<tr bgcolor="#f2deb6" >
			<td colspan="2" style="padding:5px 0 5px 0;"><b>PAYMENT INFO</b></td>
		</tr>
		<tr>
			<td class="FormHead" align="right" nowrap="nowrap"><beathive2:label key="billingForm.accountNumber"/>:</td>
			<td>
				<html:text property="accountNumber" styleClass="FormInside" size="30" maxlength="20"/>
			</td>
		</tr>
		<tr>
			<td class="FormHead" nowrap="nowrap"><b>Credit Card Type *:</b></td>
			<td>
			<html:select property="cardType">
			<html:options collection="ccs" property="value" labelProperty="label"/>
			</html:select>
			</td>
		</tr>
	<tr>

<td align='RIGHT' nowrap="nowrap" class='FormHead'> :</td><td>
<br />
<beathive2:label key="billingForm.expirationMonth"/>/<beathive2:label key="billingForm.expirationYear"/>
<br/>
<html-el:select property="expirationMonth">
	<html:option value="" key="prompt.selectMonth"/>
	<html:options collection="monthList" property="value" labelProperty="label"/>
</html-el:select>
<html-el:select property="expirationYear">
	<html:option value="" key="prompt.selectYear"/>
	<html:options collection="yearList" property="value" labelProperty="label"/>
</html-el:select>

</td></tr> 

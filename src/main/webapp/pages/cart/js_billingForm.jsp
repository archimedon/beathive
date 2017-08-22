<script type="text/javascript">
//<![CDATA[
<c:set var="domain" value="http://${pageContext.request.serverName}"/>
<c:if test="${pageContext.request.secure}">
<c:set var="domain" value="http://${pageContext.request.serverName}"/>
</c:if>
<c:set var="province" value="/shp/province.jsp"/>
<c:set var="country" value="/shp/findynpho.jsp"/>

function getCountry(ip){
	var cnt  =$("#findcntry").html();
return cnt;
}
function getProvince(cntry){
	var res = "";
	var ajax = new XMLHttpRequest();	
	ajax.onreadystatechange = function() {processRes();};
	ajax.open("GET", "<c:out value="${province}"/>?cntry="+cntry, true);
	ajax.send(null);

	function processRes(){
		if (ajax.readyState == 4) {
			if (ajax.status == 200) {
				res = ajax.responseText;
				alert(res);
			} else {
				alert('fail');
			}
		}
		return res;
	}
}
function setMenu(select,val){
	setOpt(select,val);
//	doc.getElementById("countryMenu").innerHTML = val;
}

function setOpt(selectO , selectV){
	var opts = selectO.options;
	
	for (var i = 0; i < opts.length; i++){
		if (opts[i].value == selectV){
			opts[i].selected = true;
			break;
		}
	}
}
function checkTerritories(){
	var stateCode = '<c:out value="${billingForm.province}"/>';
	var index = document.getElementById("country").selectedIndex;
	if (index > 0){
		var ccode = document.getElementById("country").options[index].value;
		if (ccode != 'US' && ccode !='CA'){
			if (document.getElementById("province")){
			}
			$("#provinceMenu").html('<input type="text" '+
			'name="province" styleClass="FormInside" ' +
			'size="20" maxlength="20"/' + '>');
		}else{
			$("#provinceMenu").html("...loading");
			if(ccode == 'US'){
				$("#provinceMenu").html($("#US").html());
			}
			else{
				$("#provinceMenu").html($("#CA").html());				
			}

		}
	}
}

//]]>
</script>


<br><br>
      <div id="US" style="visibility:hidden">
      <c:import url="${province}">
      	<c:param name="cntry" value="US"/>
      	<c:param name="sel" value="${billingForm.province}"/>
      </c:import>
	</div>
	      <div id="CA" style="visibility:hidden">
      <c:import url="${province}">
      	<c:param name="cntry" value="CA"/>
      	<c:param name="sel" value="${billingForm.province}"/>
      </c:import>
	</div>

<script type="text/javascript">
	<c:import url="/scripts/validator.jsp"/>
</script>

<html:javascript formName="billingForm" cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>

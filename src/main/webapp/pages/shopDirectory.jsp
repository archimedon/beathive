<%@ include file="/common/taglibs.jsp"%><head>
    <title><fmt:message key="menu.shop.listing"/></title>
    <content tag="heading"><fmt:message key="menu.shop.listing"/></content>
    <meta name="menu" content="ShopDirectory"/>
</head>
<beathive2:menu 
				property="clipGenres"
				listName="genre"
				toScope="page"
			/>
			
<ul id="storeGenres" class="menuList">			
<c:forEach items="${genre}" var="gi">
<li class="bigLinks"><html:link page="/stores.html?genreId=${gi.value}&method=search"><fmt:message key="${gi.label}"/></html:link></li>
</c:forEach>
</ul>
<%--
MAY NOT BOTHER WITH CRITERIA on directory page 
==============

 	 <script type="text/javascript"
	   src="<c:url value='/scripts/instrument.js'/>"></script>


<html:form action="/stores" method="get" styleId="soundClipForm">
<input type='hidden' name='size' value='5'/>
<ul>
    <li>
<fieldset>
<legend>Search Shops</legend>
<table border='0' id="instrumentgroupTable" cellpadding="3" cellspacing="3">
<caption align="top"></caption>
<tr><td><beathive2:menu 
				property="instrument.groupId"
				listName="instrumentgroup"
				promptKey="prompt.selectInstrumentGroup"
				default="${gid}"/></td><td id="subMenu"></td></tr>

<tr>
</td><td colspan="2"><beathive2:menu 
				property="genreId"
				listName="genre"
				promptKey="prompt.selectGenre"
				default=""/></td>
				</tr>
</td>
</tr>

</table>
</fieldset>
   </li>
<script type="text/javascript">

function loadSubInstrument( iid , gid){
	$.get(
		'<c:url value="/component/search/instruments.jsp"/>'
		, {'instrumentId' : iid ,'gid': gid} , 
		function(resp){
			var txt = $.trim(resp).replace(/attrib\(instrument\.id\)/ , 'instrumentId');
			
			$('#subMenu').html(txt);
	});
}
$(document).ready(function(){
	var loopId =-1;
/** Events */

	// get submenu
	$('.instrumentgroupMenu').change(function(){
		loadSubInstrument( '',$(this).val());
	});
});
</script>
    <li class="buttonBar bottom">
        <html:submit styleClass="button" property="method.search" onclick="bCancel=false">
            <fmt:message key="button.search"/>
        </html:submit>
    </li>
</ul>

</html:form>
--%>
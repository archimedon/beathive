<%@ include file="/common/taglibs.jsp"%>
<head>
<title><fmt:message key="commentList.title"><fmt:param value="${requestScope.storeForm.name}"/></fmt:message></title>
<content tag="heading"><fmt:message key="commentList.heading"/></content>
<meta name="menu" content="CommentMenu"/>
</head><%@ include file="/WEB-INF/pages/store/tabs.jsp"%>
<display:table name="commentList" cellspacing="0" cellpadding="0"
    id="comment" class="display commentList" htmlId="commentList"
    export="false" requestURI=""><security:authorize ifAnyGranted="admin,producer,user">
    <display:caption class="form">
    <fieldset>
    <legend class="showForm" ref="formContainer"  title="<fmt:message key='errors.login.required'/>"><label class="desc"><fmt:message key="commentForm.statement"/></label></legend>
    <div id="formContainer"><%@ include file="/WEB-INF/pages/commentForm.jsp"%></div>
    </fieldset></display:caption></security:authorize>
	<display:column>
	

	<div class="shopsummary">
		<div class="shopinfo fl">
			<a class="author desc">${comment.postedByUsername}</a> -- ${comment.entryTime}

			<br/><p>
			<span class="text fl"desc"><div id="comment_${comment.id}">${comment.statement}</div></span>
		</p></div>
	</div>
	</display:column>
	<display:setProperty name="basic.show.header" value="false"/>
	<display:setProperty name="basic.empty.showtable" value="true"/>
</display:table>

<%-- c:out value="${buttons}" escapeXml="false"/ --%>

<script type="text/javascript">
//<![CDATA[
$(function(){
	$('#commentForm').submit(function(){
	 var form = this;
		var data = {'js':'1' };
		var url = this.action;
		var str ='';
		$('input , textarea' , form).each(function(){
			data[$(this).attr('name') || $(this).attr('id')] =$(this).attr('value');
			str += ($(this).attr('name') || $(this).attr('id')) +' ='+$(this).attr('value')+'\n';

		});
		$.post(url , data , function(resp){
			var row =$('<tr><td>' + resp + '<'+'/td><'+'/tr>');
			var list = $('#commentList');
			var classN='odd';	
			var prerow = list.find('tr');
			if (prerow.length > 0){
				classN = ( ( $(prerow[0]).attr('class').indexOf('even') > -1 )? 'odd' : 'even' );
			}
			row.addClass(classN);
			list.prepend(row);
			form.reset();
			$('#commentList caption .showForm').click();
		});
		return false;
	});
});
//]]>
</script>

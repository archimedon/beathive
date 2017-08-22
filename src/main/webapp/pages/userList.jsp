<%@ include file="/common/var_contentType.jsp"%>
<div>
<div id="itemlist">
<display:table name="${userList}" cellspacing="0" cellpadding="0" requestURI="/admin/users.html" 
    defaultsort="1" id="user"  class="table" export="true" sort="list">
    <display:column property="username" escapeXml="true" sortable="true" media="html" titleKey="userForm.username" style="width: 25%" url="/editUser.html?from=list" paramId="username" paramProperty="username"/>
    <display:column property="firstName" escapeXml="true" sortable="true" media="html"/>
    <display:column property="lastName" escapeXml="true" sortable="true" media="html"/>
    <display:column property="email" sortable="true" titleKey="userForm.email" style="width: 25%" media="csv xml excel pdf html" url="/admin/j_spring_security_switch_user" paramId="j_username" paramProperty="username"/>
    <%-- display:column property="enabled" titleKey="userForm.enabled" media="html"/ --%>

    <display:setProperty name="export.excel.filename" value="User List.xls"/>
    <display:setProperty name="export.csv.filename" value="User List.csv"/>
    <display:setProperty name="export.pdf.filename" value="User List.pdf"/>
    <display:setProperty name="paging.banner.item_name" value="user"/>
    <display:setProperty name="paging.banner.items_name" value="users"/>

</display:table>
</div></div>
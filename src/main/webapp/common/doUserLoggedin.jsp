<%-- Called by:
./decorators/default.jsp
./decorators/producer.jsp
./decorators/shop.jsp
./decorators/admin.jsp --%>
<c:choose>
<c:when test="${isMember}">
<style type="text/css">
#primary-nav li:last-child { float:right; margin-right:50px; }
#primary-nav li:last-child a { color:#FE9915; }
</style>
<script type="text/javascript">
$(function(){
	var last = $("a[title='Logout']");
	last.prepend('[${username}] ');
});
</script><c:set var="userStat" scope="session" value="loggedIn"/>
</c:when>
<c:otherwise><c:set var="userStat" scope="session" value="notLoggedIn"/>
</c:otherwise>
</c:choose>

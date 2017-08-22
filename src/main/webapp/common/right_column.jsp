<!-- LOGIN -->
<%@ include file="/common/loginBox.jsp" %>

<c:if test="${!  fn:contains(pageContext.request.requestURI , '/admin/')}">
	<!-- CART -->
	<div id="cartSummary" class="rightColItem">
		<c:if test="${!  ( fn:startsWith(pageContext.request.requestURI , '/viewCart')   )}"><%@ include file="/WEB-INF/pages/cart/cartSummary.jsp"%></c:if>
	</div>
</c:if>

<c:if test="${shownews}">
	<!-- NEWS -->
	<div class="rightColItem""><h2>News</h2><!-- div id="feeddiv"></div -->
	<br />
	<b>Welcome to BeatHive 2.0!</b><br />
	Faster, stronger, and more able to serve your loop needs, the new site is here for you. Please let us know if you see any worts or come across a glitch and we'll fix it right away: <a href="mailto:contact@beathive.com">contact@beathive.com</a>
	</div>
	<!-- END NEWS -->
</c:if>

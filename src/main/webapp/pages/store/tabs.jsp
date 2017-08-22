<div id="menuBox">
	<ul class="menuList">
		<li>
			<html:link page="/shop/loops.html?storeId=${param.storeId}"><fmt:message key="results.tab.viewshoploops"/></html:link>
		</li>
		<li>
			<html:link forward="viewShopInfo"  paramId="storeId" paramName="storeForm" paramProperty="id"><fmt:message key="results.tab.producerinfo"/></html:link>
		</li>
		<li>
			<html:link page="/shop/comments.html?storeId=${param.storeId}"><fmt:message key="results.tab.comments"/></html:link>
		</li>
	</ul>
</div>
<p><br/></p>

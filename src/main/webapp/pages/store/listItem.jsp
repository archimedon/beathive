<%@ include file="/common/taglibs.jsp"%>
<html:link titleKey="shop.view.link" page="/shop/loops.html?storeId=${store.id}" styleClass="viewShop"><html-el:img page="${store.bannerImg}" styleClass="bannerImg"/></html:link>
<div class="shopsummary">
	<div class="shopinfo">
		<html:link titleKey="shop.view.link" page="/shop/loops.html?storeId=${store.id}" styleClass="viewShop">${store.name}</html:link>
		<div class="storeStats">Loops: <span class="keyword">${store.numLoops}</span><br/>Trackpacks: <span class="keyword">${store.numTrackpacks}</span></div>

	</div>
	<p><br/></p><span class="text">${store.description}</span>
</div>
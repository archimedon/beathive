<%@ include file="/common/var_contentType.jsp"%><ul class="meta">
	<li class="author desc"><a href="${commentForm.postedById}">${commentForm.postedByUsername}</a></li>
	<li class="date desc">${commentForm.entryTime}</li>
	<li class="textarea desc"><div id="comment_${commentForm.id}">${commentForm.statement}</div></li>
</ul>
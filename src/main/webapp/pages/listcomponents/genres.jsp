<div class="loopGenres"><c:set var="numg" value="${fn:length(genrelist)}"/>
<ul>
<c:choose>
<c:when test="${not empty genrelist}">
<c:forEach items="${genrelist}" var="ugitem" varStatus="stat">
<li>
	<beathive2:menu property="attrib(genre)" listName="genre" prompt=""	default="${ugitem.id}"/><c:if test="${not stat.first}"><span class="removeGenreButton">&nbsp;</span></c:if>
</li>
</c:forEach>
<c:if test="${gfull and numg < 3}"><li><br/><span class="addGenreButton">&nbsp;</span></li></c:if>
</c:when>
<c:otherwise>
<li><beathive2:menu property="attrib(genre)" listName="genre" prompt=""	default=""/><c:if test="${gfull}"><span class="addGenreButton">&nbsp;</span></c:if></li>
</c:otherwise>
</c:choose>
</ul>
</div>


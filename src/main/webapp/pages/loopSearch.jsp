<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="loopSearchForm.title"/></title>
    <content tag="heading"><fmt:message key="loopSearchForm.heading"/></content>
    <meta name="menu" content="SearchLoops"/>
  <link type="text/css" href="<c:url value='/scripts/ui/themes/base/ui.all.css'/>" rel="stylesheet" />
  <script type="text/javascript" src="<c:url value='/scripts/ui/ui.core.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/ui/ui.slider.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.listen-min.js'/>"></script>
</head>
<body id="searchFormPage">
<html:form action="loops" method="get" styleId="soundClipForm">


<ul id="searchLeft_ul" class="fl"><li><h2><fmt:message key="loopSearchForm.heading"/></h2></li>
<li>
 <span id="requiredCriteria" style="margin-top:10px;">
<!-- BEGIN instrument and GENRE -->
	<ul id="instrumentgroupTable">
	<li>
		<span><beathive2:menu 
				property="instrumentForm.groupId"
				listName="instrumentgroup"
				promptKey="prompt.selectInstrumentGroup"
				styleClass="required criteria"
				styleId="instrumentGroupId"
				default=""
		/>	</span><span id="subMenu"><!-- &lt;&ndash;&ndash;<div id="instruction">select an instrument or genre to begin</div> --> <%-- this is where the instrument sub-menu is injected --%></span>
	</li>
	<li>
	<span><beathive2:menu 
		property="genreId"
		styleClass="required criteria"
		styleId="genreId"
		listName="genre"
		promptKey="prompt.selectGenre"
		default=""
	/>	</span>
	</li>
	</ul>
</span>
<!-- END instrument and GENRE -->
</li>


<li>
<h3><fmt:message key="loopSearchForm.filters.label"/></h3>
	<ul>
		<li>
		<html:checkbox property="AFavorite" styleId="AFavorite" styleClass="criteria" value="true"/>
			<label for="AFavorite">
				<fmt:message key="loopSearchForm.filter.favorites"/>
			</label><br />
		<html:checkbox property="ownedByViewer" styleId="ownedByViewer" styleClass="criteria" value="true"/>
			<label for="ownedByViewer">
				<fmt:message key="loopSearchForm.filter.owned"/>
			</label>
		</li>
	</ul>
</li>
<li>
	<h3><fmt:message key="loopSearchForm.sort_options.label"/></h3>
	<ul>
	<li>
	  <ul class="searchprefs">
		<li><h5><fmt:message key="loopSearchForm.sort_by.label"/></h5></li>
		<li>
		<html:radio property="sort" styleId="sortscore" styleClass="criteria" value="score"/>
				<label for="sortscore">
					<fmt:message key="loopSearchForm.sort.highscore.label"/>
				</label>
		</li>
		<li>
		<html:radio property="sort" styleId="sortmod" styleClass="criteria" value="modified"/>
				<label for="sortmod">
					<fmt:message key="loopSearchForm.sort.newest.label"/>
				</label>
		</li>
	  </ul>
	  <ul class="searchprefs">
		<beathive2:menu property="frm" listName="format" toScope="page"/>
		<li><h5><fmt:message key="loopSearchForm.format_preferences.label"/></h5></li>
		<c:forEach var="lblv" items="${frm}" varStatus="stat"><li>
			<html:radio styleId="preffrm_${stat.index}" property="preffrm" value="${lblv.value}" styleClass="criteria" />
			<label for="preffrm_${stat.index}">${lblv.label}</label>
		</li></c:forEach>
	  </ul>
	</li>
	</ul>
</li>
<li>
<!-- BEGIN Advanced Criteria === (TREAT AS ATOMIC) -->
<h3><fmt:message key="loopSearchForm.advancedcriteria.heading"/><small class="xsmall showForm rtl" ref="advancedcriteriaForms"> </small></h3>
	<div id="advancedcriteriaForms">
		<c:forTokens items="tempo,timespan,origin,passage,feel" delims="," var="key">
			<ul class="descriptor"><li><h3><fmt:message key="loopSearchForm.descriptor.${key}"/></h3></li>
			<c:forEach items="${checkLists[key]}" var="opt" varStatus="status">
				<li><label id="${key}_${status.index}"  value="${opt.id}" name="${key}" class="button criteria"><fmt:message key="${opt.labelKey}"/></label></li>
			</c:forEach>
			</ul>
		</c:forTokens>
	</div>
<!-- END Advanced Criteria -->
</li>
</ul>



<ul id="searchRight_ul" class="fr">
<li>
	<h2><fmt:message key="loopSearchForm.keyword.label"/></h2>
	<ul>
	<li><input type='text' class="required criteria" id="keyword" name='keyword' value='' size="25" length="25"/>
	</li>
	</ul>
</li>
<li>
	<h3><fmt:message key="loopSearchForm.loopCounter"/></h3>
	<ul id="resultTracker">
		<li><span id="numLoops" class="counterNumber">0</span> <label><fmt:message key="loopSearchForm.loops.label"/></label></li>
		<li><span id="numTrackpacks" class="counterNumber">0</span> <label><fmt:message key="loopSearchForm.trackpacks.label"/></label></li>
	</ul>
</li>
	

	<div id="pagesizeEL">
	<!-- BEGIN - items per page slider -->
	<h5><fmt:message key="loopSearchForm.pagesize"/></h5>
	<input readonly="true" type='text' class="button" id="size" name='size' value='10' size="2" length="2"/>
	<div id="slider"></div>
	<!-- END - slider -->
	<br/>
	<html:submit styleClass="button" styleId="searchbutton" property="method.search" onclick="bCancel=false"><fmt:message key="button.search"/></html:submit>
	</div>

</ul>
</html:form>
<%@ include file="/scripts/loopSearchForm.js.jsp"%>
</body>

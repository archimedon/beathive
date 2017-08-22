<body id="producerHomePage">
<h1>Activity</h1>
<br/>
<table class="summary small">
<tbody>
<tr>
	<th>visits:</th>
	<td>${storeForm.views}</td>
</tr>
<tr>
	<th>comments:</th>
	<td><html:link action="/shop/comments" paramId="storeId" paramName="storeForm" paramProperty="id">${fn:length(storeForm.comments)}</html:link></td>
</tr>
<tr>
	<th>Trackpacks:</th>
	<td>${storeForm.numTrackpacks}</td>
</tr>
<tr>
	<th>Loops:</th>
	<td>${storeForm.numLoops}</td>
</tr>
</tbody>
</table>

<table class="summary">
<caption></caption>
<tr>
	<th></th>
	<th class='center'>un-packaged</th>
	<th class='center'>packaged</th>
	<th class='center'>#items sold</th>
</tr>
<tr>
	<td class='row_header'>Loops</td>
	<td><a id="unpackedLoops" href="/producer/home.html?storeId=${storeForm.id}&method=searchInventory&type=loop&ready=false&sort=created&dir=desc" class="unpacked">0</a></td>
	<td id="packedLoops">0</td>
	<td id="numLoopsSold">0</td>
</tr>
<tr>
	<td class='row_header'>Track Packs</td>
	<td><a id="unpackedTrackpacks" href="/producer/home.html?storeId=${storeForm.id}&method=searchInventory&type=trackpack&ready=false&sort=created&dir=desc" class="unpacked">0</a></td>
	<td id="packedTrackpacks">0</td>
	<td id="numTrackpacksSold">0</td>
</tr>
</table>
</body>

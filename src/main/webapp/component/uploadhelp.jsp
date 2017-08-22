<style type="text/css">
#tpformat td * {font-size: 90%;}
#tpformat td.begin {width: 150px;}
#tpformat img{
	display:inline;
	border:0;
	margin:0;
	vertical-align:top;
	width: 180px;
}

td.begin span {font-size: 70%; color:#444;}
span i , span b{color:#000; font-weight: 900;}
#tpformat td{
	padding:0;
	vertical-align:top;
}
#tpformat{
	padding:0;
	border:0;
	margin:0;
	border-collapse:collapse;
	background-color: #fff;
	width: 358px;
}
dl{
	width: 370px;
}
ol li{
	list-style-type: decimal;
	padding: 5px 0;
	font-size: 90%;
}

</style>


<dl class="fl">
<dt><h5>Folder structure of a Track Pack zip file:</h5></dt>
<dd>
<table id="tpformat">
	<tr>
		<td class="begin"><img src="/images/directory.png"></td>
		<td rowspan="2"><img src="/images/files.png"/></td>
	</tr>
	<tr>
		<td class="begin"><span><b>Fortune</b> is a Track pack folder.</td>
		<td></td>
	</tr>
</table>
</dd>
<dt>&nbsp;</dt>
<dt><i>__MAIN__.aif</i> is the full-mix</dt>
<dd><ol><li>A track pack zip file must contain a full-mix/demo-track.</li><li>The name of the demo track must contain the characters '__MAIN__'.</li>
<li>The full mix may be named "Fortune__MAIN__.aif, __MAIN__Fortune.aif or just __MAIN__.aif, etc...</li>
<li>If the archive does not include a file whose name has the format, *__MAIN__*.[aif|wav] the zip will be treated as a folder of single loops.</li>
<li><i>Fortune_Bass.aif</i>, <i>Fortune_Clave.aif</i>, <i>Fortune_Drums.aif</i>, <i>etc...</i>, are component loops</li></ol></dd>
<dt>&nbsp;</dt>
<dt>&nbsp;</dt>
<dt><h5>Folder structure for Loop zip file:</h5></dt>
<dt>&nbsp;</dt>
<dd>Single loops can be: single aif/wav files , aif/wav files zipped in an archive or, aif/wav file in a zipped folder - similar to a track pack folder but without the <i>__MAIN__.aif</i> file</dd>

</dl>

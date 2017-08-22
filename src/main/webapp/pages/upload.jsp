<%@ include file="/common/taglibs.jsp"%>
				<div style="clear:both"></div>

<script type= "text/javascript">
//<![CDATA[

	function ajaxFileUpload()
	{
		$.ajaxFileUpload
		(
			{
				url:'<c:url value="/zazz/producer/sampleAudio.html"/>',
				secureuri:false,
				fileElementId:'fileToUpload',
				dataType: 'json',
				beforeSend:function()
				{
					$("#loading").show();
				},
				complete:function()
				{
					$("#loading").hide();
				},				
				success: function (data, status)
				{
					if(typeof(data.error) != 'undefined')
					{
						if(data.error != '')
						{
							alert(data.error);
						}else
						{
							alert(data.msg);
						}
					}
				},
				error: function (data, status, e)
				{
					alert(e);
				}
			}
		)
		
		return false;

	}
//]]>
</script>
	<div>

		<img id="loading" src="/images/loading.gif" style="display:none;">
		<form name="form" action="" method="POST" enctype="multipart/form-data">
		<table cellpadding="0" cellspacing="0" class="tableForm">

		<thead>
			<tr>

				<th>Ajax File Upload</th>
			</tr>
		</thead>
		<tbody>	
			<tr>
				<td><input id="fileToUpload" type="file" size="45" name="uploads[0]" class="input"></td>	

					
			</tr>
			<tr>
				<td>Please select a file and click Upload button</td>

			</tr>
		</tbody>
			<tfoot>
				<tr>
					<td><button class="button" id="buttonUpload" onclick="return ajaxFileUpload();">Upload</button></td>
				</tr>
			</tfoot>
	
	</table>

		</form>
	</div>
<tiles:insert definition=".producer.Assets" />
	 <script type="text/javascript"
	     src="<c:url value='/scripts/jquery.js'/>"></script>


	 <script type="text/javascript"
	     src="<c:url value='/scripts/ajaxfileupload.js'/>"></script>

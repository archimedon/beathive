	function setGeneralUploader(buttonName , callerParams){
		var storeId = callerParams['storeId'];
		var username = callerParams['username'];
		var button = $('#' + buttonName);
		new Ajax_upload(button,{
		  // Location of the server-side upload script
		  action: '/zazz/producer/sampleAudio.html', 
		  name: 'uploads[0]',
		  // Additional data to send
		  data: {
			  'username':	username,
			  'storeId':		storeId ,
			  'method':		"up"
		  },
		  // Submit file after selection
		  autoSubmit: true,
		  // Fired after the file is selected
		  // Useful when autoSubmit is disabled
		  // You can return false to cancel upload
		  // @param file basename of uploaded file
		  // @param extension of that file
		  onChange: function(file, extension){},
		  // Fired before the file is uploaded
		  // You can return false to cancel upload
		  // @param file basename of uploaded file
		  // @param extension of that file
		  onSubmit: function(file, extension) {
					// change button text, when user selects file			
					button.text('Processing ' +   file);
					
					// If you want to allow uploading only 1 file at time,
					// you can disable upload button
					this.disable();
					
					// Uploding -> Uploading. -> Uploading...
					interval = window.setInterval(function(){
						var text = button.text();
						if (text.length < 20){
							button.text(text + '.');					
						} else {
							button.text('Processing ' +   file);				
						}
					}, 200);
				},
		
		  // Fired when file upload is completed
		  // @param file basename of uploaded file
		  // @param response server response
		  onComplete: function(file, response) {
		  alert('here');
			button.text('Complete');
			window.clearInterval(interval);
			this.enable();
			button.text('Upload');
			var resp = $.trim(response);
			alert("file, response " + file +"\n"+response);
			if (callerParams.callback){
				callerParams.callback(resp +'adfasf');
			}else{
			
			var process_response = "<div style=''position:absolute;top:30px;overflow:auto; height:40px;color:green;border: 1px solid red;width:500px;'>" + resp +"<span style='right:20px; top: 10px;'><a href='javascript:void(msgClose("+(resp.indexOf('completed')>-1 ? 'true' : 'false')+"))'>close<"+"/a><\/span><"+"/div>";
					$.blockUI({
						css: { 
				padding:        0, 
				margin:         0, 
				width:          '50%', 
				top:            '10%', 
				textAlign:      'left', 
				color:          '#000', 
				border:         '3px solid #aaa', 
				backgroundColor:'#fff', 
				cursor:         'hand' ,
				left: 			'10%'
			}, 
		 
			// styles for the overlay 
			overlayCSS:  { 
				backgroundColor:'#556', 
				opacity:        '0.6' 
			}, 
		 
			// styles applied when using $.growlUI 
			growlCSS: { 
				width:    '350px', 
				top:      '10px', 
				left:     '', 
				right:    '10px', 
				border:   'none', 
				padding:  '5px', 
				opacity:  '0.6', 
				color:    '#fff', 
				backgroundColor: '#000', 
				'-webkit-border-radius': '10px', 
				'-moz-border-radius':    '10px' 
			}, 
		 
			// z-index for the blocking overlay 
			baseZ: 1000, 
		 
		 
			// allow body element to be stetched in ie6; this makes blocking look better 
			// on "short" pages.  disable if you wish to prevent changes to the body height 
			allowBodyStretch: true, 
		 
			// be default blockUI will supress tab navigation from leaving blocking content; 
			constrainTabKey: true, 
		 
			// fadeIn time in millis; set to 0 to disable fadeIn on block 
			fadeIn:  200, 
		 
			// fadeOut time in millis; set to 0 to disable fadeOut on unblock 
			fadeOut:  400, 
		 
			// time in millis to wait before auto-unblocking; set to 0 to disable auto-unblock 
			timeout: 0, 
					
			message: process_response
			}); 
		}
		}
		});
	}
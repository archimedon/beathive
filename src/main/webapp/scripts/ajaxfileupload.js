
jQuery.extend({
	


jaxup : function (s) 
{
        // TODO introduce global settings, allowing the client to modify them for all requests, not only timeout		
        s = jQuery.extend({}, jQuery.ajaxSettings, s);
        var id = new Date().getTime()        
		
		
		var form = $('#' + s.fileElementId).parents('form')[0];// fileobj.form;
		
		var domFrame = document.getElementById($(form).attr('target'));
		
		var targetObj = $(domFrame);

		jQuery.event.trigger( "ajaxStart" );

        var requestDone = false;
        // Create the request object
        var xml = {}   
        if (  jQuery.ajaxSettings.global )
            jQuery.event.trigger("ajaxSend", [xml, s]);
                    // Wait for a response to come back
        var uploadCallback = function(isTimeout)
		{		
		
            try 
			{				
				if(domFrame.contentWindow)
				{
					 xml.responseText = domFrame.contentWindow.document.body?domFrame.contentWindow.document.body.innerHTML:null;
                	 xml.responseXML = domFrame.contentWindow.document.XMLDocument?domFrame.contentWindow.document.XMLDocument:domFrame.contentWindow.document;
					 
				
				}
				else	if(domFrame.contentDocument)
				{
					 xml.responseText = domFrame.contentDocument.document.body?domFrame.contentDocument.document.body.innerHTML:null;
                	xml.responseXML = domFrame.contentDocument.document.XMLDocument?domFrame.contentDocument.document.XMLDocument:domFrame.contentDocument.document;
				}						
            }catch(e)
			{
				jQuery.handleError(s, xml, null, e);
			}
            if ( xml || isTimeout == "timeout") 
			{				
                requestDone = true;
                var status;
                try {
                    status = isTimeout != "timeout" ? "success" : "error";
                    // Make sure that the request was successful or notmodified
                    if ( status != "error" )
					{
                        // process the data (runs the xml through httpData regardless of callback)
                        var data = jQuery.uploadHttpData( xml, s.dataType );    
                        // If a local callback was specified, fire it and pass it the data
                        if ( s.success )
                            s.success( data, status );
    
                        // Fire the global callback
                        if( s.global )
                            jQuery.event.trigger( "ajaxSuccess", [xml, s] );
                    } else
                        jQuery.handleError(s, xml, status);
                } catch(e) 
				{
                    status = "error";
                    jQuery.handleError(s, xml, status, e);
                }

                // The request was completed
                if( s.global )
                    jQuery.event.trigger( "ajaxComplete", [xml, s] );

                // Handle the global AJAX counter
                if ( s.global && ! --jQuery.active )
                    jQuery.event.trigger( "ajaxStop" );

                // Process result
                if ( s.complete )
                    s.complete(xml, status);

                jQuery(domFrame).unbind()

                setTimeout(function()
									{	try 
										{
//											$(domFrame).remove();
//											$(form).remove();	
											
										} catch(e) 
										{
											jQuery.handleError(s, xml, null, e);
										}									

									}, 100)

                xml = null

            }
        }

        // Timeout checker
        if ( s.timeout > 0 ) 
		{
            setTimeout(function(){
                // Check to see if the request is still happening
                if( !requestDone ) uploadCallback( "timeout" );
            }, s.timeout);
        }
        try 
		{
        alert(form.id);
            $(form).submit();

        } catch(e) 
		{			
            jQuery.handleError(s, xml, null, e);
        }
		targetObj.load(uploadCallback);
        return {abort: function () {}};	

    } , 

uploadHttpData : function( r, type ) {
        var data = !type;
        data = type == "xml" || data ? r.responseXML : r.responseText;
        // If the type is "script", eval it in global context
        if ( type == "script" )
            jQuery.globalEval( data );
        // Get the JavaScript object, if JSON is used.
        if ( type == "json" )
            eval( "data = " + data );
        // evaluate scripts within html
        if ( type == "html" )
            jQuery("<div>").html(data).evalScripts();
			//alert($('param', data).each(function(){alert($(this).attr('value'));}));
        return data;
    }
    })
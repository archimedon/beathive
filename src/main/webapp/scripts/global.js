// extend Array. add contains(object)
Array.prototype.contains = function(obj) {
  var i = this.length;
  while (i--) {
    if (this[i] === obj) {
      return true;
    }
  }
  return false;
}


function isLoggedIn(){
	var dstat =  {'loggedin':false};
	eval('dstat='+$.trim($.ajax({
		url: CONTEXT_PATH +"/scripts/tool.jsp",
		global: false,
		type: "GET",
		data: ( {'cmd':'userloggedin','js':'1','wild':Math.random()}),
		dataType: "text",
		async: false 
		}
	).responseText) + ';');
	
	return dstat.loggedin;
}

// Show the document's title on the status bar
window.defaultStatus=document.title;

function initializeMainNav(bFlag){
	if (bFlag){
		/* init cart and wishlist buttons */
		var query = {'method.view':'1','rtype':'data','js':'1'};
		$.get(CONTEXT_PATH + '/user/wishList.html' , query , function(resp){
			var hash = {};
			eval('hash='+$.trim(resp));
	
			if ($('.count').length < 1){
				$('[title="View Cart"]').append("&nbsp;[<span class='count'>"+hash['cartsize']+"<"+"/span>]");
				$('[title="Wish List"]').append("&nbsp;[<span class='count'>"+hash['wishsize']+"<"+"/span>]");
			}else{
				$('[title="View Cart"] span.count').text(hash['cartsize']);
				$('[title="Wish List"] span.count').text(hash['wishsize']);
			}
			if (hash['cartsize']){
			}
		});
	}
}



function loadRSSFeed(){
	var feedcontainer=$("#feeddiv")
	var feedurl="http://www.gearwire.com/rss.xml"
	var feedlimit=8
	var feedpointer=new google.feeds.Feed(feedurl) //Google Feed API method
	feedpointer.setNumEntries(feedlimit) //Google Feed API method
	feedpointer.load(function(result){
		var rssoutput="<ul>";
		if (!result.error){
			var thefeeds=result.feed.entries;
			for (var i=0; i<thefeeds.length; i++){
				rssoutput+="<li><a href='" + thefeeds[i].link + "'>" + thefeeds[i].title + "</a></li>";
			}
			rssoutput+="</ul>"
			$("#feeddiv").append(rssoutput);
		} else {
			alert("Error fetching feeds!");
		}
	}); //Google Feed API method
}


$(document).ready(function(){
resize_floats();

$('textarea').maxLength();

	$('a.viewShop').click(function(){
		var loc = new URL(window.location.href);
		var srcq = loc.getQuery();
		var srcparams = qparam(srcq);
		srcparams['page'] = 1;
		var curloc = new URL(this.href);		
		window.location.href=curloc.getURI() +"?"+qstr(srcparams) + "&" + curloc.getQuery();
		return false;
	});

	$('.menuList:not(#primary-nav,#searchTabs)').each(function(){
	
		var ul = this;
		$(ul).children('li').removeClass('selected');
		$(ul).children('a').removeClass('current');
		if(true){
			var lis = $(ul).find('a');
			var current = new URL(window.location.href);
			var currentURI = current.getURI() + (current.getQuery()=='' ? '' : '?' + current.getQuery());
			
			for (var g=0; g < lis.length; g++){
				var li = $(lis[g]).closest('li');
				var link = new URL(lis[g].href);
				var linkURI = link.getURI();
				var flg = false;
				if(link.getQuery()){
					linkURI += '?' + link.getQuery();
					flg = true;
				}

				// user home exception
				if( currentURI == '/user/home.html'|| currentURI == '/user/'){
					currentURI = '/user/editProfile.html';
				}
		
				if(currentURI == linkURI){
					$(li).addClass('selected');
					break;	
				}
		
				if (currentURI.indexOf(linkURI) > -1){
					var inspectParams = link.getParams();
					var currentParams = current.getParams();
					if (flg && currentParams){
						var flag = link.getQuery() !='';
						for (var key in inspectParams){
							if (inspectParams[key] != currentParams[key]){
								flag = false;
								break;
							}
						}
						if (flag){$((li)).addClass('selected');}
						break;
					}
				}
			}
		}
	});


	/**
	 * 
	 */
	$('.showForm').toggle(
		function(){
			$('#'+$(this).attr('ref')).show();
		}
		,
		function(){
			$('#'+$(this).attr('ref')).hide();
		}
	);
	
	$('#searchResultsInnerLoop thead tr th.sortable a').live('click',	
	function(e){	
		var urlstr = getTagUrl(this);
		var aUrl = new URL(urlstr);
		var params = aUrl.getParams();
		params.js=1;
		params.x=1;
		var cnt = $('#searchResultsInnerLoop');
	//		var flip = params['idir'] == 'asc';
		$.get(aUrl.getURI() , params , function(dat){
			var table = $('#searchResultsInnerLoop' , dat);
			cnt.html(table.html());
		});
		return false;
	});
	
	$('.prev a').live('click' , function(e){
		var sel = $(this).parents('ul').find('select');
		if (sel[0].selectedIndex > 0){
			sel[0].selectedIndex--;
			sel.trigger('change');
		}
		e.preventDefault();
		return false;
	});

	$('.next a').live('click' , function(e){
		var sel = $(this).parents('ul').find('select');
		if (sel[0].selectedIndex < sel[0].options.length - 1){
			sel[0].selectedIndex++;
			$(sel).trigger('change');
		}
		e.preventDefault();
		return false;
	});
	
	
	$('.notLoggedIn').live('click', handleNotLoggedIn);

	
});


 function handleNotLoggedIn() {
	var form = $('#loginForm');

	form.find('#j_username').focus().css({
		'background-color' : 'orange'
	});
	form.find('label[for="j_username"]').css({
		color : 'orange'
	});
	setTimeout("revert();", 1500);
	return false;
};
	
 function revert(){
	labelcolor = '#57504A';
	var form = $('#loginForm');
	form.find('#j_username').focus().css({'background-color':'#fff'});
	form.find('label[for="j_username"]').css({'color': labelcolor});
 }

function getTagUrl(but){
	switch(but.tagName.toLowerCase()){
		case 'select': return but.value;
		case 'a': return but.href;
	}
}

function newRow(elem){
	$('.curropen').trigger('click');
	$('.innerres').trigger('close');
	var thisRow = $(elem).closest('tr');
	var cellcount = thisRow.find('td').length;
	var stub = $(elem).hasClass('viewParent')?'<td class="firstCell"></td>':'<td class="firstCell"></td>';
	var newRow =  $('<tr id="componentRow" class="innerres">' + stub + '</tr>').bind('close' , close);
	var iloops = $('<td id="iloops" colspan="' + (cellcount) + '">Loading...</td>');
	newRow.append(iloops);
	newRow.insertAfter(thisRow);
	return [ newRow , iloops ];
}

 function openComps(){
	var but = this;
	var url = but.href;	
	var aUrl = new URL(url);
	
	var params = aUrl.getParams();
	params.js=1;
	var g = newRow(but);
	var componentRow  = g[0];
	var iloops =  g[1];

	$.ajax({
		timeout : 4500,
		global : false,
		type : "GET",
		async : true,
		url : aUrl.getURI(),
		data : params,
		success : function(dat) {
			$(but).find('div').removeClass('expandButton').addClass('openedButton');
			iloops.hide().html(dat);
			var pos = $(but).position();
			$.scrollTo({
				top : (pos.top + 55),
				left : 0
			}, 1000);
			// an exclusion is made for parent view from the loop-result
			var tablist = $(iloops.find('.loopList').filter("[id!='tpview']").get(0));
			tablist.data('url', url);
			tablist.find('tr').each(function(i) {
				if ($(this).find('td.ownertrue').length) {
					$(this).addClass('owned');
				}
				initDisplayTable(tablist.attr('id'));
			});
			$(but).addClass('curropen');
			iloops.fadeIn(500);
		},
		error : function(xhr, textStatus) { }
	});
	return false;
 }

 function closeComps (){
	$('#componentRow').trigger('close');
	$(this).removeClass('curropen');
	$(this).find('div').removeClass('openedButton').addClass('expandButton');
	return false;
 }
 
 function close(){
 	$(this).fadeOut(400 , function(){
 		$('table' , this).empty().remove();
 		$(this).remove();
 	});
 }

 function pagejump(but) {
	but = but || this;
	var urlstr = getTagUrl(but);
	var par = $(but).parents('#iloops');

	var aUrl = new URL(urlstr);
	var params = aUrl.getParams();
	params.js = 1;
	if (par.length > 0) {
		var cnt = $('#searchResultsInnerLoop');

		$.get(aUrl.getURI(), params, function(dat) {
			var table = $('#searchResultsInnerLoop', dat);

			cnt.html(table.html());

			var openloop = $('.curropen');
			if (openloop.length > 0) {
				var f = openloop[0].id.split(/_/)[1];
				$($('#iloops #cid_' + f).parents('tr')[0]).addClass('sameas');
			}

			// an exclusion is made for parent view from the loop-result
			var tablist = $($('#iloops').find('.loopList').filter("[id!='tpview']").get(0));
			tablist.data('url', urlstr);
			tablist.find('tr').each(function(i) {
				if ($(this).find('td.ownertrue').length) {
					$(this).addClass('owned');
				}
				initDisplayTable(tablist.attr('id'));
			});
			return false;
		});
	} else if ((cnt = $('#reportResult')).length > 0) {
		$.post(urlstr, function(ret) {
			$('#reportResult').html($('#itemlist', ret).html());
		});
	} else {
		window.location.href = urlstr;
	}
	return false;
}

function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 

	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length) 
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end) 
	return unescape(value);
}

function deleteCookie(name,path,domain) {
  if (getCookie(name)) {
    document.cookie = name + "=" +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}


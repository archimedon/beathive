  function updateCartButton(num){
  	 $('[title="View Cart"] .count').text(num);
 }
 
  function updateWishButton(num){
  	 $('[title="Wish List"] .count').text(num);
}


function initDisplayTable(id){

	// style the row of owned loops
	$('#'+id+' .loopList').find('tr').each(function(){
		if($(this).find('td.ownertrue').length){
			$(this).addClass('owned');
		}		
	});
	
	// style the row of owned loops
	$('#'+id+' .trackpackList').find('tr').each(function(){
		if($(this).find('td.ownertrue').length){
			$(this).addClass('owned');
		}		
	});
}
function setMeter(amt){
	var meter = $('#meter');
	var imglen = parseInt(meter.css('width')) - 1;
	var intary = [10,25,50,80,100];
	var t = 0;
	for ( t =  intary.length - 1 ; t > 0 ; t--){
		if (amt >= intary[t]){
			break;
		}
	}
	var newpos = -1 * imglen;
	if (t >= intary.length -1){
		newpos = 0;
		meter.find('td').each(function(){
		$(this).addClass('inView');
	});

	}else if (amt >10){
	
	var ratio = (t+1)/4;
	var pt = imglen * (ratio);
		var tr = $(meter.find('tr')[0]).position().left;

	var tds = $('#meter tr td');
	var td = tds[t];
	var upper = intary[t+1];
	var lower = intary[t];
	var tmp =( ( amt - lower ) / (intary[t+1] -  intary[t]) ) * 33;
	newpos = (-1 * imglen) + ((t>0)? imglen * (t/4) :0) + tmp;
	tds.each(function(i){
		if (i <= t){
			$(this).addClass('inView');
		}else{
			$(this).removeClass('inView');
		}
	});
	}
	$('#meter').css('background-position' , newpos +'px');
	
}

//ready
$(document).ready(function(){
var META_TEMPLATE = CONTEXT_PATH +'/component/search/clipMeta.jsp';

$('.loopList tr td.infoMark, .trackpackList tr:not("#componentRow") td.infoMark , #searchResultsInnerLoop tr td.infoMark').live('mouseover' ,		function(){

			var obj = $('#searchCriteria');
			var str  = $($(this).parents('tr')[0]).find('td div.attr').html();
			obj.find('#advancedCriteria').hide();
			var layon =$(str).addClass('seekdest');
			obj.append(layon);
		});
		
$('td.infoMark').live('mouseout' ,		function(){
		$('.seekdest').remove();
		var obj = $('#metaData');
					obj.find('ul').show();
		}
	);

$.fn.serializeForm = function(){
	var params = {};
	$(this)
		.find("input").each(function(){
			if (this.value) {params[ this.name || this.id || this.parentNode.name || this.parentNode.id ] = this.value;}
		});
	return params;
}
$.fn.updateKeyed = function(params){

	if($(this).attr('id')=='cartSummary'){
		for(var key in params){
			if (key=='savings'){
				$('#meter', this).attr('title',params[key]);
			}
			$('#'+key , this).html(params[key]);	
		}
		var flt = parseFloat(params['subTotal'].match(/([^\$]+)/)[0]);
		setMeter(flt);
	}else{
	
		for(var key in params){
			$('#'+key , this).html(params[key]);
		}

	}
}

	$.easing.elasout = function(x, t, b, c, d) {
		var s=1.70158;var p=0;var a=c;
		if (t==0) return b;  if ((t/=d)==1) return b+c;  if (!p) p=d*.3;
		if (a < Math.abs(c)) { a=c; var s=p/4; }
		else var s = p/(2*Math.PI) * Math.asin (c/a);
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b;
	};

	// mark row if user owns the loop
	$('.loopList,.trackpackList').find('tr').each(function(){
		if($(this).find('td.ownertrue').length){
			$(this).addClass('owned');
		}		
	});

	$('.resTab').click(function(){
		var hash = qparam(window.location.search.slice(1));
		hash['type']=$(this).attr('name');
		// when going to trackpacks
		// reset sort and dir if sort was instrumentId
		if (hash['sort']=='instrumentId'){
			hash['sort']='score';
			hash['dir']='desc';
		}
		hash['page'] = 1;
		window.location.search = qstr(hash);
	});
	
	
	$('.toWishlist').live('click',function(e){
		$(this).removeClass('toWishlist').addClass('fromWishlist');
		var url = new URL(this.href);
		var qhash = url.getParams();
		qhash['js']='1';
		qhash['rtype']='summary';

		var actBut = $(this).find('.toWishlistButton')[0];
		$(actBut).removeClass().addClass('working');


/* not the best soln but need to know if user been auth'd before making the request */
		if (! isLoggedIn()){

		$.get(CONTEXT_PATH + '/login.jsp#main' , function(resp){
			var lgb = $('#loginBox');
			var bg = lgb.css('background-color');
			lgb.css('background-color' , '#ffee15');
			$('#j_username' )[0].focus();
			setTimeout(function (a,b) {
  lgb.css('background-color' , b);
				$(actBut).removeClass('working').addClass('toWishlistButton');
},1500,lgb,bg);
		});
			return false;
		} else {
		$.get(url.getURI() , qhash , function(seg){
		if (seg.indexOf('<title>BeatHive | Login</title>')>-1) {
			window.location.href=CONTEXT_PATH + '/login.jsp';
				$(actBut).removeClass('working').addClass('toWishlistButton');
			} else {
				$(actBut).removeClass().addClass('fromWishlistButton');
			}
		});
	}
		return false;
	});

	$('.fromWishlist').live('click',function(e){
		$(this).removeClass('fromWishlist').addClass('toWishlist');
		var row = $(this).parents('tr')[0];
		var actBut = $(this).find('.fromWishlistButton')[0];
		$(actBut).removeClass().addClass('working');

		var doRem = $(this).hasClass('clear');
		var url = new URL(this.href);
		
		var qhash = url.getParams();
		qhash.js=1;
		qhash.rtype='summary';
		if (! isLoggedIn()){
			qhash.redir= window.location.href;
		}

		$.get(url.getURI() , qhash , function(seg){
			if (seg.indexOf('<')>-1){
				alert('Login required');
				$(actBut).removeClass('working').addClass('fromWishlistButton');
			}else{
				$(actBut).removeClass().addClass('toWishlistButton');
			if(doRem){
				$(row).addClass('emphasize').fadeOut(2000 , function(){
					$(this).remove();
					var respd = {};
					eval('respd='+$.trim(seg) +';');
					$('#cartSummary').updateKeyed(respd);
				});
			}
			}
		});
		
		return false;
	});

	$('.sortable a').live('click' , function(){
		var qp = qparam(window.location.search.slice(1));
		delete qp['js'];
		delete qp['x'];
		if (qp['page']){
			var cur = $(this).attr('href');
			$(this).attr('href' , cur +'&page=' + qp['page']);
		}
	});
	

	$('.rateClip').live('click' , function(e){
	
		var tab = $($(this).parents('table.display')[0]);
		var but = this;
		var target = tab.attr('id');
		var tur = new URL(this.href);
		var par = tur.getParams();
		par.js = 1;
		$.get(tur.getURI() , par,function(resp){
			$($(but).parents('div')[0]).removeClass('rateCell').addClass('rateItNot');
			if (par.score=='1'){
				$(but).next().remove();
			}else{
				$(but).prev().remove();
			}
		});
		return false;
	});
		
	$('a.toCart').live('click', function(e){
		var node = this;
		var url = new URL($(node).attr('href'));
		var row = $(node).parents('tr')[0];
		
		var qhash = qparam(url.getQuery());
		if (url.getURI().indexOf('Cart')>-1){qhash['method']='add';}
		qhash['js']='1';
		qhash['rtype']='summary';
		//	var func = $(this)['toggle'];
		var actBut = $(node).find('.toCartButton');
		$(actBut).removeClass('toCartButton').addClass('working');
		
		$(node).removeClass('toCart').addClass('fromCart');
		
		$.get(url.getURI()  , qhash , function(resp){
			var respd = {};
			eval('respd='+$.trim(resp) +';');
			
			if(respd['message']){
				alert(respd['message']);
				$(node).removeClass('fromCart').addClass('toCart');
				$(actBut).removeClass().addClass('toCartButton');
				return false;
			}else{
			$('#cartSummary').updateKeyed(respd);
			
			if (qhash['to']=='cart'){
			
				$(row).fadeOut(function(){$(this).remove();});
			}else{
				$(actBut).removeClass().addClass('fromCartButton');
			}
}
		});
		
		e.stopPropagation();//no need to bother the document
		return false;
	});
	
	
	$('a.fromCart').live('click', function(e){
		var node = this;
		var url = new URL($(node).attr('href'));
		var row = $(node).parents('tr')[0];
		
		var qhash = qparam(url.getQuery());
		qhash['method']='sub';
		qhash['js']='1';
		qhash['rtype']='summary';
		
		var actBut = $(node).find('.fromCartButton');
		$(actBut).removeClass('fromCartButton').addClass('working');
		
		$.get(url.getURI()  , qhash , function(resp){
		var respd = {};
			eval('respd='+$.trim(resp) +';');
			$('#cartSummary').updateKeyed(respd);
			
			$(node).removeClass('fromCart').addClass('toCart');
			$(actBut).removeClass().addClass('toCartButton');
		
		});
		e.stopPropagation();//no need to bother the document
		
		return false;
	});
	
	
	$('.formatSwitch').live('change' , function(e){
		var node = this;
		var row = $(node).parents('tr')[0];
		anchs = $('.wishCell, .cartCell ' , row).find('a');
		var url = null;
		if(anchs.length > 0){
			anchs.each(function(){
				url = new URL($(this).attr('href'));
				url.setParam('fileId' , node.value);
				$(this).attr('href' , url.toString());
			});
		}		
		e.stopPropagation();
		
		return false;
	});
	initDisplayTable('searchResultsLoop');

	/**
	 * View Component / View Trackpack
	 */
	$('.expand , a.viewParent').toggle(openComps , closeComps);
	

});


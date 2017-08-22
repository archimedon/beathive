/**
 *	A collection of standalone utilities.
 *	Date: Jan 19th 2004
 */
 
/**
 *	converts the queryString of a URL to a hash
 */
function qparam(str){
	if (str!=null){
		var query={};
		str.replace(/\b([^&=]*)=([^&=]*)\b/g, function (m, a, d) {
			if (query[a]) {
				if(typeof(query[a]) !='string'){
					query[a].push(d);
				} else{
					query[a]=[query[a] , d];
				}
			} else {
			  query[a] = d;
			}
		});
	}
	return query;
}

// usage:
// var o = getQuery('maps.google.co.uk/maps?f=q&q=brighton&ie=UTF8&iwloc=addr');
function getQuery(s) {
  var query = {};
  s.replace(/\b([^&=]*)=([^&=]*)\b/g, function (m, a, d) {
    if (typeof query[a] != 'undefined') {
      query[a] += ',' + d;
    } else {
      query[a] = d;
    }
  });
  return query;
}

/**
 *	creates a query string from a map
 */
function qstr(param){
	var ary = [];
	for (var g in param){
		ary.push(g+'='+param[g]);
	}
	return ary.join('&');
}
/** a URL helper Object */
function position(str){
	var pts = str.split(/\s+/);
		
	return {
		'left':parseFloat(pts[0].match(/([\-\d]+)/)[0]) ,
		'top':parseFloat(pts[1].match(/([\-\d]+)/)[0])
	};
}

// parseUri 1.2.2
// (c) Steven Levithan <stevenlevithan.com>
// MIT License
URL.options = {
	strictMode: false,
	key: ["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],
	q:   {
		name:   "queryKey",
		parser: /(?:^|&)([^&=]*)=?([^&]*)/g
	},
	parser: {
		strict: /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*)(?::([^:@]*))?)?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
		loose:  /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*)(?::([^:@]*))?)?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/
	}
};


function URL (str) {
	var	o   = URL.options,
		m   = o.parser[o.strictMode ? "strict" : "loose"].exec(str),
		uri = {},
		i   = 14;

	while (i--) uri[o.key[i]] = m[i] || "";

	uri[o.q.name] = {};
	uri[o.key[12]].replace(o.q.parser, function ($0, $1, $2) {
		if ($1) uri[o.q.name][$1] = $2;
	});

	this.uri = uri;

	this.params = qparam(this.uri.query);
	
	this.getQuery = function(){
		return this.uri.query;
	}
	this.getURI = function(){
		return this.uri.path;
	}
	this.getParams = function(){
		return this.params;
	}

	this.setParams = function(pars){
		this.params = pars;
	}

	this.setParam = function(key , val){
		this.params[key] = val;
	}

	this.toString = function(){
		return this.uri.path + '?' + qstr(this.params);
	}
	
	// a reference object to the URI
	this.getRef = function(){
		return this.uri;
	}
};




function parseMeSrc(doc){
	var scripts = doc.getElementsByTagName('script');
	var myScript = scripts[ scripts.length - 1 ];

	var queryString = myScript.src.replace(/^[^\?]+\??/,'');

	return parseQuery( queryString );
	
}

function parseQuery ( query ) {
   var Params = new Object ();
   if ( ! query ) return Params; // return empty object
   var Pairs = query.split(/[;&]/);
   for ( var i = 0; i < Pairs.length; i++ ) {
      var KeyVal = Pairs[i].split('=');
      if ( ! KeyVal || KeyVal.length != 2 ) continue;
      var key = unescape( KeyVal[0] );
      var val = unescape( KeyVal[1] );
      val = val.replace(/\+/g, ' ');
      Params[key] = val;
   }
   return Params;
}



jQuery.fn.maxLength = function(vmax){
	this.each(function(){
		var max = vmax || parseInt($(this).attr('cols')) * parseInt($(this).attr('rows'));
		//Get the type of the matched element
		var type = this.tagName.toLowerCase();
		//If the type property exists, save it in lower case
		var inputType = this.type? this.type.toLowerCase() : null;
		//Check if is a input type=text OR type=password
		if(type == "input" && inputType == "text" || inputType == "password"){
			//Apply the standard maxLength
			this.maxLength = max;
		}
		//Check if the element is a textarea
		else if(type == "textarea"){
			//Add the key press event
			this.onkeypress = function(e){
				//Get the event object (for IE)
				var ob = e || event;
				//Get the code of key pressed
				var keyCode = ob.keyCode;
				//Check if it has a selected text
				var hasSelection = document.selection? document.selection.createRange().text.length > 0 : this.selectionStart != this.selectionEnd;
				//return false if can't write more
				return !(this.value.length >= max && (keyCode > 50 || keyCode == 32 || keyCode == 0 || keyCode == 13) && !ob.ctrlKey && !ob.altKey && !hasSelection);
			};
			//Add the key up event
			this.onkeyup = function(){
				//If the keypress fail and allow write more text that required, this event will remove it
				if(this.value.length > max){
					this.value = this.value.substring(0,max);
				}
			};
		}
	});
};

// This function is used by the login screen to validate user/pass
// are entered. 
function validateRequired(form) {                                    
    var bValid = true;
    var focusField = null;
    var i = 0;                                                                                          
    var fields = new Array();                                                                           
    oRequired = new required();                                                                         
                                                                                                        
    for (x in oRequired) {                                                                              
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
           if (i == 0)
              focusField = form[oRequired[x][0]]; 
              
           fields[i++] = oRequired[x][1];
            
           bValid = false;                                                                             
        }                                                                                               
    }                                                                                                   
                                                                                                       
    if (fields.length > 0) {
       focusField.focus();
       alert(fields.join('\n'));                                                                      
    }                                                                                                   
                                                                                                       
    return bValid;                                                                                      
}

function isValidCreditCard(type, ccnum) {
 var re;
   if (type == "Visa") {
      // Visa: length 16, prefix 4, dashes optional.
     re = new RegExp(/^4\d{3}-?\d{4}-?\d{4}-?\d{4}$/);
   } else if (type == "Master Card") {
      // Mastercard: length 16, prefix 51-55, dashes optional.
      re =  new RegExp(/^5[1-5]\d{2}-?\d{4}-?\d{4}-?\d{4}$/);
   } else if (type == "Discover") {
      // Discover: length 16, prefix 6011, dashes optional.
      re =  new RegExp(/^6011-?\d{4}-?\d{4}-?\d{4}$/);
   } else if (type == "American Express") {
      // American Express: length 15, prefix 34 or 37.
      re =  new RegExp(/^3[4,7]\d{13}$/);
   } else if (type == "Diners") {
      // Diners: length 14, prefix 30, 36, or 38.
      re =  new RegExp(/^3[0,6,8]\d{12}$/);
   }
   if (!re.test(ccnum)) {
		return false;
	}
   // Remove all dashes for the checksum checks to eliminate negative numbers
   ccnum = ccnum.split("-").join("");
   // Checksum ("Mod 10")
   // Add even digits in even length strings or odd digits in odd length strings.
   var checksum = 0;
   for (var i=(2-(ccnum.length % 2)); i<=ccnum.length; i+=2) {
      checksum += parseInt(ccnum.charAt(i-1));
   }
   // Analyze odd digits in even length strings or even digits in odd length strings.
   for (var i=(ccnum.length % 2) + 1; i<ccnum.length; i+=2) {
      var digit = parseInt(ccnum.charAt(i-1)) * 2;
      if (digit < 10) { checksum += digit; } else { checksum += (digit-9); }
   }
   if ((checksum % 10) == 0) return true; else return false;
}


function highlightTableRows(tableId) {
    var previousClass = null;
    var table = document.getElementById(tableId); 
    var tbody = table.getElementsByTagName("tbody")[0];
    var rows;
    if (tbody == null) {
        rows = table.getElementsByTagName("tr");
    } else {
        rows = tbody.getElementsByTagName("tr");
    }
    // add event handlers so rows light up and are clickable
    for (i=0; i < rows.length; i++) {
        rows[i].onmouseover = function() { previousClass=this.className;this.className+=' over' };
        rows[i].onmouseout = function() { this.className=previousClass };
        rows[i].onclick = function() {
            var cell = this.getElementsByTagName("td")[0];
            var link = cell.getElementsByTagName("a")[0];
            location.href = link.getAttribute("href");
            this.style.cursor="wait";
        }
    }
}

function highlightFormElements() {
    // add input box highlighting
    addFocusHandlers(document.getElementsByTagName("input"));
    addFocusHandlers(document.getElementsByTagName("textarea"));
}

function addFocusHandlers(elements) {
    for (i=0; i < elements.length; i++) {
        if (elements[i].type != "button" && elements[i].type != "submit" &&
            elements[i].type != "reset" && elements[i].type != "checkbox" && elements[i].type != "radio") {
            if (!elements[i].getAttribute('readonly') && !elements[i].getAttribute('disabled')) {
                elements[i].onfocus=function() {this.style.backgroundColor='#ffd';this.select()};
                elements[i].onmouseover=function() {this.style.backgroundColor='#ffd'};
                elements[i].onblur=function() {this.style.backgroundColor='';}
                elements[i].onmouseout=function() {this.style.backgroundColor='';}
            }
        }
    }
}

var curropen = "";
	
function setInstrument(groupId , instid){
	select = document.getElementById("instrumentGroup_" + groupId);
	
	var opts = select.options;
	var i = 0;
		opts[0].selected = true;
	for (i = 0 ; i < opts.length; i++){
		if (opts[i].value == instid){
			opts[i].selected = true;
			break;
		}
	}
}

function setGroup(groupId,instid){
	if(isNaN(groupId)){
	groupId = 1;
	}
	var div = document.getElementById("instrumentgroupId_" +groupId);
	var select = document.getElementById("groupId");
	if (div){
	curropen="instrumentgroupId_" +groupId;
		div.style.display = "";
		var opts = select.options;
		var i = 0;
		opts[0].selected = true;
		for (i = 0 ; i < opts.length; i++){
			if (opts[i].value == groupId){
				opts[i].selected = true;
				break;
			}
		}
	setInstrument(groupId , instid);
	}
}

function setOpt(objName , val){
	if (curropen != ''){
		document.getElementById(curropen).style.display = "none";
		document.getElementById(curropen).disabled=true;
	}
	curropen = objName + '_' + val;
	document.getElementById(curropen).style.display = "";
	document.getElementById("instrumentGroup_" + val).disabled=false;
	
	setInst( document.getElementById("instrumentGroup_" + val).value);
	
}
function setInst(val){
		document.getElementById('instrumentId').value =val;
}
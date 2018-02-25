	var CanChangeClass = false;
	var AMenuOff = "subAMenuOff" , AMenuOn ="subAMenuOn";
	
	function onMouseOverChange(obj){
		var aClassName = $(this).find("a:first-child").attr("class");
		if(aClassName != AMenuOn){
			$(this).find("a:first-child").attr("class",AMenuOn);
			CanChangeClass = true;
		}else{
			CanChangeClass = false;
		}
	}
	function onMouseOutChange(obj){
		var aClassName = $(this).find("a:first-child").attr("class");
		if(aClassName == AMenuOn && CanChangeClass ==true){
			$(this).find("a:first-child").attr("class",AMenuOff);
		}
	}
	function showActive(obj){
		$(this).find("a:first-child").attr("class",AMenuOn);
		CanChangeClass = false;
		var lis = $("li");
		for(var i=0;i<lis.length;i++){
			if($(lis[i]).index() != $(this).index()){
				$(lis[i]).find("a:first-child").attr("class",AMenuOff);
			}else{
			}
		}
	}
	function init(){
		var lis = $("li");
		for(var i=0;i<lis.length;i++){
			$(lis[i]).on("click", showActive);
			$(lis[i]).on("mouseover",  onMouseOverChange);
			$(lis[i]).on("mouseout",onMouseOutChange);
			$(lis[i]).find("a:first-child").attr("class",AMenuOff);
		}
	}
	$(document).ready(
		init
	);
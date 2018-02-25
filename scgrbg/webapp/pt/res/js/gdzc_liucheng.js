/*String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};

function replaceRN(id){
	if(document.getElementById('txt'+id)){
		var newContent = document.getElementById('txt'+id).innerHTML;
		newContent=newContent.replaceAll("\n","<br/>");
		newContent=newContent.replaceAll("&lt;","<");
		newContent=newContent.replaceAll("&gt;",">");
		document.getElementById('txt'+id).innerHTML=newContent;
	}
}*/
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};


function replaceRN(id){
	if(document.getElementById('txt'+id)){
		var newContent = document.getElementById('txt'+id).innerHTML;
		var os=newContent.split("|@|");
		newContent="";
		for(var k=1;k<os.length;k++,k++){
			var s=os[k];
			var news="",temps=s,start=0;
			for(var i=0;i<parseInt(1150/s.length);i++){
				var len=parseInt((1150/15)*(i+1));
				if(temps.substring(start,len)){
					if(temps.length>len){
						news+=temps.substring(start,len)+"<BR>　　";
						start=len;
					}else{
						news+=temps.substring(start,len);
						start=len;
					}
				}
			}
			newContent+=news+os[k+1];
		}
		newContent=newContent.replaceAll("\n","<BR>　　");
		newContent=newContent.replaceAll(" ","　");
		newContent=newContent.replaceAll("&lt;","<");
		newContent=newContent.replaceAll("&gt;",">");
		newContent=newContent.replaceAll("<BR><BR>","<BR>");
		document.getElementById('txt'+id).innerHTML="　　"+newContent;
	}
}

function afterFjadd(){
	w('filelist').set('tab2');
}

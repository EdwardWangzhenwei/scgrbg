var DocFrame;
//国产客户端延迟加载
function lazyLoad(){
	var filepath = wg("mbnr");//alert(filepath);
	var file=wg("fullpath")+"pt/res/hdoa/wpsedit/showFj.jsp?filename="+filepath;
	DocFrame=document.getElementById("DocFrame").Application;
	var t1 = window.setInterval(function(){
		if (DocFrame.IsLoad()){
			clearInterval(t1);
	    	var aa = DocFrame.openDocumentRemote(file,true);
	    	if(!aa){
	    	  PT.alert('文件打开失败');
	    	}
	        else{
	        	DocFrame.enableProtect(false);	     
	        }
	     
	       document.getElementById("ed").style.display="none";
	         w('ed').setColor('red');
		}
	}, 100);	
}
//获取客户端操作系统
function detectOS() {
    var sUserAgent = navigator.userAgent;  
    var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");  
    var isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh") || (navigator.platform == "MacIntel");  
    if (isMac) return "Mac";  
    var isUnix = (navigator.platform == "X11") && !isWin && !isMac;  
    if (isUnix) return "Unix";  
    var isLinux = (String(navigator.platform).indexOf("Linux") > -1);  
    if (isLinux) return "Linux";  
    if (isWin) {  
        var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;  
        if (isWin2K) return "Win2000";  
        var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;  
        if (isWinXP) return "WinXP";  
        var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;  
        if (isWin2003) return "Win2003";  
        var isWinVista= sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;  
        if (isWinVista) return "WinVista";  
        var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;  
        if (isWin7) return "Win7";  
    }  
    return "other";  
} 
function isLinux(){
	if(detectOS().indexOf("Linux")!=-1){
		os = "Linux";
		return true;
	}else{
		os = "other";
		return false;
	}
}
//获取客户端浏览器信息
function checkIEVersion(){ 
	var ua = navigator.userAgent; 
	var s = "MSIE"; 
	var i = ua.indexOf(s)          
	if (i >= 0) { 
	   //获取IE版本号 
	    var ver = parseFloat(ua.substr(i + s.length)); 
	   return true;
	} 
	else {
	    //其他情况，不是IE 
	    return false;
	} 
}
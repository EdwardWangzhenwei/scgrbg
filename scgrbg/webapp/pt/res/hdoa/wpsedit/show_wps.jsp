<%@page import="cn.com.huadi.aos.hdoa.common.util.DictUtil"%>
<%@page import="cn.com.huadi.aos.hdoa.systemManagement.whpz.service.WhpzService"%>
<%@page import="cn.com.huadi.aos.dict.service.DictCommonService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Debug"%>
<%@page
	import="cn.com.huadi.aos.hdoa.systemManagement.wjxs.service.WJXSService"%>
<%@page import="cn.com.huadi.aos.hdoa.fawen.service.FaWenService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.service.FlowListService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" import="java.sql.*,java.util.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.*"%>
<%@page import="java.net.URLEncoder"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	System.out.println("basePath:"+basePath);
	session.setMaxInactiveInterval(18000);
	String jsmburl = basePath + "pt/res/hdoa/wpsedit/jsmoban.wpt";
	String ServeUrl = (String) RegServiceServlet.SYSCON.get("loadediturl");
	Map user = (Map)session.getAttribute("USERINFO");
%>
<%
	boolean isTZGJQX = false;
	String currNode = "";//当前流程节点

	String sTZGJQX = request.getParameter("TZGJQX");
	if (sTZGJQX != null && sTZGJQX.equals("true")) {
		isTZGJQX = true;
	}
%>
<%
	String dzwj_id = "",dzwjm = "", wjbm = "",n_id="", fwmb = "", type = "", fileName = "", flag = "", fileExt, noMarkFileName = "", Wjfl = "";
	String TzYzId = "";
	int fwmb_id = 0;
	String isFirstMan = "";
	int Wjzlbz = 0;
	String selfOPen = request.getParameter("Self");
// 	System.out.println("selfOPen======" + selfOPen);
	if (request.getParameter("wjbm") != null
			&& !"".equals(request.getParameter("wjbm"))) {
		wjbm = request.getParameter("wjbm");
	}
	if (request.getParameter("n_id") != null
			&& !"".equals(request.getParameter("n_id"))) {
		n_id = request.getParameter("n_id");
	}
	if (request.getParameter("flag") != null) {
		flag = request.getParameter("flag");
	}
	if (request.getParameter("fileExt") != null) {
		fileExt = request.getParameter("fileExt");
	} else {
		fileExt = "WPS";
	}

	if (fileExt.equals("WPS")) {
		if (user.get("user_name").equals("网络管理处")) {
			fileExt = "Word";
		}
	}
	/*
	 *如果有传入dzwj_id,就使用传入的电子文件编码来查询对应电子文件
	 *如果没有就先找有没有正式件,在有正式件的情况下,使用正式件的电子文件编码
	 *如果没有正式件,再找有没有审批件.wps,使用审批件.wps的电子文件编码
	 *如果都没有,则生成一个审批件.wps
	 */
	 /* 
	 	对页面按钮的控制，针对“保存”、“生成红头”、“另存为正式件”、“另存为版式文件”：
	 	1.在没有“正式件.wps”环节，页面保留“保存”、“生成红头”按钮
	 	2.“生成红头”只能点击一次，之后隐藏，“保存”按钮隐藏，“另存为正式件”显示
	 	3.点击“另存为正式件”后，页面保存操作完毕后自动关闭
	 	4.再次编辑正文时，打开的便是“正式件.wps”，此时页面只保留“保存”、“另存为版式文件”
	 */
	DzwjService dzwjService=new DzwjService();
	FlowListService flowListService=new FlowListService();
	boolean iszsj=false;
	if (request.getParameter("dzwj_id") != null
			&& !request.getParameter("dzwj_id").equals("")) {
		dzwj_id = request.getParameter("dzwj_id");
	} else {
		Map dzwj=dzwjService.queryDzwjByWjbmAndDzwjm("GW_FZXX",wjbm,"正式件%.wps");//筛除.ofd文件
		if(dzwj!=null){
			dzwj_id=String.valueOf(dzwj.get("dzwj_id"));
			iszsj=true;
		}else{
			dzwj=dzwjService.queryDzwjByWjbmAndDzwjm("GW_FZXX",wjbm,"%审批件%");
			if(dzwj!=null){
				dzwj_id=String.valueOf(dzwj.get("dzwj_id"));
			}
		}
	}

	if (!"".equals(dzwj_id)) {
		 Map m=dzwjService.getDzwjById(dzwj_id);
	    if(m!=null){
	    	if(m.get("dzwjm")!=null)
	    		fileName = String.valueOf(m.get("dzwjm"));
	    	else
	    		fileName = String.valueOf(m.get("dzwjms"));
	    		dzwjm = String.valueOf(m.get("dzwjms"));;//修复附件保存操作时 导入文件名 丢失问题--20150804
	    }
	} else {	
		fileName = "审批件.wps";

		isFirstMan = "Y";//没有电子文件编码,认为是第一个打开控件
	}
		
	if(fileName.equals("审批件.wps") || fileName.indexOf("正式件") == 0){
  	/*生成无痕迹文件时的文件名称
  	*如果是审批件.wps第一次生成红头时为“正式件１”
  	*以后每生成一次正式件后面的数字自动加１
  	*/
  	Map dzwj=dzwjService.queryDzwjByWjbmAndDzwjm("GW_FZXX",wjbm,"正式件%.wps");//筛除.ofd文件
  	if(dzwj != null){
  		String dzwjm2 =String.valueOf(dzwj.get("dzwjm"));
  		int tempNum = 0;
  		try{
  			String tempName = dzwjm2.substring(3,dzwjm2.length()-4);
  		    tempNum = Integer.parseInt(tempName);
  		}catch(Exception e){
  			noMarkFileName = "正式件1";
  		}
  		noMarkFileName = "正式件"+String.valueOf(tempNum+1);
	  	}else{
	  		noMarkFileName = "正式件1";
	  	}
	}else{
		noMarkFileName = fileName;
	}
	Map fw=null;
	Map sw=null;
	FaWenService faWenService=new FaWenService();
	String n_fr_name="";
	if (!"".equals(wjbm)) {
		fw=faWenService.getFaWenById(wjbm,n_id);
		if (fw != null) {
			Debug.debugMessage(1, "当前操作流程环节："+fw.get("n_fr_name"));
			n_fr_name=String.valueOf(fw.get("n_fr_name"));
			Wjfl = String.valueOf(fw.get("wjlx"));
			DictCommonService dictCommonService=new DictCommonService();
			Object mj= dictCommonService.getDictionaryInfoByCode(DictUtil.DICT_CODE_MJ, String.valueOf(fw.get("mj"))).get("dict_name");
			Object qxlb= dictCommonService.getDictionaryInfoByCode(DictUtil.DICT_CODE_QXLB, String.valueOf(fw.get("qxlb"))).get("dict_name");
			Object jjcd="";
			if(fw.get("jjcd")!=null){				
				jjcd=dictCommonService.getDictionaryInfoByCode(DictUtil.DICT_CODE_HJ, String.valueOf(fw.get("jjcd"))).get("dict_name");
			}
			fw.put("mj", mj);
			fw.put("qxlb", qxlb);
			fw.put("jjcd", jjcd);
		}
	}
	WJXSService wjxsService=new WJXSService();
	if (fwmb.equals("")) {//如果没有从前传来fwmb，在本页面重新从数据库中读取
		if (fw != null) {
			Map mb=wjxsService.getMbByWjxsIdAndMblx(String.valueOf(fw.get("wjxs_id")), "红头模板");
			if(mb!=null){
				fwmb=String.valueOf(mb.get("mbmc"));
				fwmb_id=Integer.parseInt(String.valueOf(mb.get("mbgl_id"))==null?"0":String.valueOf(mb.get("mbgl_id")));
			}	
		}
		//Debug.debugMessage(1,"===发文模板="+fwmb+"=====发文模板ID=="+fwmb_id+"当前操作附件id="+dzwj_id+"=======文号后缀hz="+fw.get("hz"));
	}
	String mbmc="";
	if (mbmc.equals("")) {//如果没有从前传来fwmb，在本页面重新从数据库中读取
		if (fw != null) {
			Map wjxs=wjxsService.selectById(Integer.parseInt(String.valueOf(fw.get("wjxs_id"))));
			if(wjxs!=null){
				mbmc=String.valueOf(wjxs.get("htgz"));
			}	
			Debug.debugMessage(1,"===发文模板="+mbmc+"=====发文模板ID=="+fwmb_id+"当前操作附件id="+dzwj_id+"=======文号后缀hz="+fw.get("hz"));
		}
	}	
	WhpzService whpzService= new WhpzService();//获取文号显示规则用
	Map whpz=null;
	String lx = "号";
	int n_uperid=-1;
	if (fw != null) {
		Map param=new HashMap();
		param.put("ssdwid", user.get("unit_id"));
		param.put("sybmid", user.get("dept_id"));
		param.put("wjxs_id", fw.get("wjxs_id"));
		param.put("zh", fw.get("zh"));
		param.put("hz", fw.get("hz"));
		whpz=whpzService.getWhPzByWjxsIdAndWh(param);
		lx =String.valueOf(fw.get("hz"));
		currNode=String.valueOf(fw.get("n_fr_name"));
		String sNuperid=String.valueOf(fw.get("n_uperid"));
		if(sNuperid!=null&&!"null".equals(sNuperid)){
			n_uperid=Integer.parseInt(sNuperid);
		}
	}
	boolean isOneNode = n_uperid>0?false:true;//文件是不是工作流第一步,如果是第一步,不加痕迹
	//Debug.debugMessage(1, "是否加痕迹，第一个节点不加痕迹isOneNode="+isOneNode+"  fw.get(\"n_uperid\")="+fw.get("n_uperid"));
	boolean baocun = true;//保存
	
	if (flag.equals("unself")) {
		baocun = false;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="<%=request.getContextPath() %>/common/js/hd.js"></script>
<link rel="StyleSheet" href="../../../res/css/newcss.css"
	type="text/css" />
<title>金山</title>
</head>

<body onLoad="loadedit()" bgcolor="#F3F3F3" leftmargin="6" topmargin="6"
	style="overflow: auto; overflow-y: hidden">
	<center>

		<input type="hidden" name="dzwjm" id="dzwjm" value="<%=dzwjm%>">
		<input type="hidden" name="saveit">
		<div id="Layer1">
			<table width="100%" height="100%" border="0" cellpadding="0"
				cellspacing="0" bgcolor="#FFFFFF">
				<tr>
					<td valign="bottom">
						<center>
							<img src="loading.gif" width="70" height="70">
						</center>
					</td>
				</tr>
				<tr>
					<td valign="top">
						<center>
							<img src="loadtxt.gif" width="425" height="80">
						</center>
					</td>
				</tr>
			</table>
		</div>
		<TABLE BORDER=0 width=500>
			<tr>
				<td align="center"><INPUT type="BUTTON" ID="button9"
					class="gradual-btnitem" width="60px" VALUE="关闭"
					onclick="return closeit()"></td>
			</tr>
		</TABLE>
		<input name="dzwj_id" type="hidden" value="<%=dzwj_id%>"> <input
			name="wjbm" type="hidden" value="<%=wjbm%>"> <input
			id="filename" name="filename" type="hidden" value=""> <input
			id="SaveOFD" name="SaveOFD" type="hidden" value="">
		<style>
button.op {
	width: 90px;
	background-color: #9DC2DB;
	border: 1px #EEEEEE solid;
	cursor: hand;
}
</style>
		<div id="wps" align="right"
			style="width: 98%; height: 95%; top: 65px; left: 10px; position: absolute">
			<object name="webwps" id="webwps_id" type="application/x-wps"
				data="<%=jsmburl%>" width="100%" height="95%">
				<param name="quality" value="high" />
				<param name="bgcolor" value="#ffffff" />
				<param name="Enabled" value="1" />
				<param name="allowFullScreen" value="true" />
			</object>
		</div>

	</center>
</body>
<script type="text/javascript" language="javascript" charset="utf-8">
function getXmlHttp(){
	 var  xmlhttp ;
	 if(window.XMLHttpRequest){
		 xmlhttp = new XMLHttpRequest();
		}
		else{ 
		 xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	 return xmlhttp;
}
function getTagNameValue (xml,tagname){
	var val = '';
	if(xml.getElementsByTagName(tagname)[0].childNodes[0]!=null){
		val=xml.getElementsByTagName(tagname)[0].childNodes[0].nodeValue;
	}
	return val;
}

var OPEN = "";
function loadedit(){
	setTimeout("loadedit1()", "30");
}
	
var obj;
var app;

//获取客户端操作系统
var os="";

var isupdatezt=true;//是否在关闭窗口时需要更改文件阅读状态	
function loadedit1(){
	if(isLinux()){
		obj = document.getElementById("webwps_id");
		//解决新建之后立马能输入
		app = obj.Application;
		window.onblur = function() {
		    console.log("onblur");
		    obj.sltReleaseKeyboard();
		};
	}//app.setDocumentType('函');
	//继续
	setTimeout("outit()",50);
	OPEN = "open3";
}

function outit() {
	document.all.Layer1.style.display="none";
}

//关闭,如果baocun为true会调用保存的方法
function closeit(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	try{
		window.history.go(-1);
	}catch(exception){
		window.close();
	}
}

/****************************************************
*
*        控件初始化WebOffice方法
*
****************************************************/

function init(tagID,width,height){
	var iframe;
	var obj;
	iframe = document.getElementById(tagID);
	var codes = [];
	codes.push('<object id="DocFrame1" height='+height+' width='+width+' ');
	codes.push('data=data:application/x-oleobject;base64,7Kd9juwHQ0OBQYiirbY6XwEABAA7DwMAAgAEAB0AAAADAAQAgICAAAQABAD///8ABQBcAFgAAABLAGkAbgBnAHMAbwBmAHQAIABBAGMAdABpAHYAZQBYACAARABvAGMAdQBtAGUAbgB0ACAARgByAGEAbQBlACAAQwBvAG4AdAByAG8AbAAgADEALgAwAAAA ');
	codes.push('classid=clsid:8E7DA7EC-07EC-4343-8141-88A2ADB63A5F viewastext=VIEWASTEXT></object> ');
	iframe.innerHTML = codes.join("");
	obj = document.getElementById("DocFrame1");

	return obj;
}
function InitFrame(){
	app = init("wps","100%","100%");
}

if(isLinux()){
	var t1 = window.setInterval(function(){
	   newWpsDocument();
	}, 100);
}else{
	InitFrame();
	newWpsDocument();	
}
function newWpsDocument(){
	if(isLinux()){
		if (app.IsLoad()){
			setToolbarAllVisibleF(false);//初始化页面时隐藏【工具栏】
			var name = "<%=user.get("user_name")%>";  //设置修订用户
			open_file();
			//在打开页面两秒之后隐藏痕迹
			<%if(!"Y".equals(isFirstMan)){%>
				setTimeout("showRevision2()",2000);
			<%}%>
			clearInterval(t1);
			app.setUserName(name);
			//因为调用openDocument()方法须在插件完全加载完毕之后，所以放在app.IsLoad()的判断中执行 --20150507 liangwenfeng
			//第一次进入时，询问是否导入本地文件
			<%if("Y".equals(isFirstMan)){ %>
				document.all.Layer1.style.display="none";
				var fileName = "";
				if(confirm("是否需要导入本地文件?")){
					try{
						//导入本地文件
						var ret = openDocument();
						fileName = ret.get_Name();
						var loadfiletype = fileName.substring(fileName.length-3,fileName.length);//获取导入本地文件的文件类型
						loadfiletype = loadfiletype.toUpperCase();//将文件类型大写
						if(loadfiletype == "DOC" || loadfiletype == "WPS"){
						}else{
							//如果导入本地文件不是DOC或WPS格式则重新提示只能导入DOC或WPS格式的文件
							alert("只能导入DOC或WPS格式的文件,请重新选择文件");
							window.location.reload();
						}
						var temp = fileName.split("/");
						document.getElementById("dzwjm").value = temp[temp.length-1];//将文件名称放到myForm的dzwjm的表单中
					}catch(exception){alert(exception.message);
						alert("导入失败,请重新选择文件");
						window.location.reload();
					}
				}else{
			      	var url = "<%=basePath%>pt/res/hdoa/wpsedit/normal.wpt";
			     	app.openDocumentRemote(url,false);			
				}		
				OPEN = "open3";
			<%}%>
			//commandbar();
		}
	}else{
		var name = "<%=user.get("user_name")%>";  //设置修订用户
		open_file();
		//在打开页面两秒之后隐藏痕迹
		<%if(!"Y".equals(isFirstMan)){%>
			setTimeout("showRevision2()",2000);
		<%}%>
		app.setUserName(name);
		<%if("Y".equals(isFirstMan)){ %>
			document.all.Layer1.style.display="none";
			var url = "<%=basePath%>pt/res/hdoa/wpsedit/normal.wpt";
     		app.openDocumentRemote(url,false);					
			OPEN = "open3";
		<%}%>
		commandbar();
	}
} 
function open_file(){
    //打开在线文档
    if("<%=dzwj_id%>"!=""){
     	var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=dzwj_id%>";
      	app.openDocumentRemote(iturl,false);
  	}
	//不是第一个人保留痕迹，即开启修订
	<%if(!"Y".equals(isFirstMan)){%>
		app.enableRevision(true);
	<%}else{%>
	//第一个人不保留痕迹，即关闭修订
		app.enableRevision(false);
	<%}%>	
}
//打开本地文件选择窗口
function openDocument(){
	var docs = app.Documents;
	var ret = docs.Open();
	return ret;
}
  
//接受对文档的所有修订    
function enableRevisionAcceptCommand2(){
	if(isLinux()){
		var doc = app.Documents.ActiveDocument();

		//var revisions = doc.GetRevisions();
		var revisions = doc.get_Revisions();
		var ret = revisions.AcceptAll();
	}else{
		app.ActiveDocument.Revisions.AcceptAll;
	}
} 
//复制修订痕迹
function CopyRevision(){
	var doc = obj.Application.Documents.ActiveDocument();
	doc.Selection.Range.Copy();
}

function showRevision0(){
    app.showRevision(0);
}

function showRevision1(){
    app.showRevision(1);
}

function showRevision2(){
    app.showRevision(2);
}
function commandbar(){
	if(isLinux()){
		app.Refresh();
		//var comms = obj.Application.CommandBars;
	 	app.showCommandByName("Tools", 10, false);//隐藏【工具】-【修订】按钮
	 	if(getVersion().substring(getVersion().length-4,getVersion().length)<4361){
			var comms = app.CommandBars;
			var comm = comms.GetCommandbar(29);
			var comcons = comm.CommandbarControls;
			var comcon = comcons.GetCommandbarcontrol(0);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(1);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(2);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(3);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(4);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(5);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(14);
			comcon.put_Enabled(false);
			comcon = comcons.GetCommandbarcontrol(10);
			comcon.put_Enabled(false);
		}else{
			var comms = app.CommandBars;
			var comm = comms.get_Item(30);
			var comcons = comm.Controls;
			var comcon = comcons.get_Item(1);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(2);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(3);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(4);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(5);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(6);		
			comcon.put_Enabled(false);
			comcon = comcons.get_Item(9);		
			comcon.put_Enabled(false);	
			comcon = comcons.get_Item(11);		
			comcon.put_Enabled(false);
		}
	}else{
		enableCopy();
		enableCut();
		app.enableRevision(false);
		enableRevisionAcceptCommand();
		enableRevisionRejectCommand();
	}
}
//禁止接受修订按钮
function enableRevisionAcceptCommand(){
	var aa = app.enableRevisionAcceptCommand(false);
    //alert (aa);
}
//禁止拒绝修订按钮
function enableRevisionRejectCommand(){
	var bb = app.enableRevisionRejectCommand(false);
    //alert (bb);
}
//显示/隐藏工具条
function setToolbarAllVisibleF(flag){
	//true显示工具条、false隐藏工具条
	var aa = app.setToolbarAllVisible(flag);
    //alert (aa);
}
//禁止拷贝
function enableCopy(){
	var aa = app.enableCopy(false);
    //alert (aa);
}
//禁止剪切
function enableCut(){
	var aa = app.enableCut(false);
    //alert (aa);
}
//获取版本号
function getVersion(){
	var ret = app.get_Build();
	return ret;
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

//获取当前时间
function getNowFormatDate(){
    var day = new Date();
    var Year = 0;
    var Month = 0;
    var Day = 0;
    var CurrentDate = "";
    Year= day.getFullYear();//支持IE和火狐浏览器.
    Month= day.getMonth()+1;
    Day = day.getDate();
    CurrentDate += Year;
    if (Month >= 10 ){
     CurrentDate += Month;
    }
    else{
     CurrentDate += "0" + Month;
    }
    if (Day >= 10 ){
     CurrentDate += Day ;
    }
    else{
     CurrentDate += "0" + Day ;
    }
    return CurrentDate;
 }
 
//获取六位随机数
function MathRand(max) 
{ 
var Num=""; 
for(var i=0;i<max;i++) 
{ 
Num+=Math.floor(Math.random()*10); 
} 
return Num;
}
</script>
</html>
<%@page import="cn.com.huadi.aos.hdoa.common.util.StringUtil"%>
<%@page import="cn.com.huadi.aos.hdoa.ldps.service.LdpsService"%>
<%@page import="cn.com.huadi.aos.dict.service.DictCommonService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Debug"%>
<%@page import="cn.com.huadi.aos.hdoa.systemManagement.wjxs.service.WJXSService"%>
<%@page import="cn.com.huadi.aos.hdoa.shouwen.service.ShouWenService"%>
<%@page import="com.aisino.platform.view.DataMsgBus"%>
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
	session.setMaxInactiveInterval(18000);
	String jsmburl = basePath + "pt/res/hdoa/wpsedit/jsmoban.wpt";
	//String ServeUrl = (String) RegServiceServlet.SYSCON.get("loadediturl");
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
	String dzwj_id = "",dzwjm = "", wjbm = "",n_id="", swmb = "", type = "", fileName = "", flag = "", fileExt, noMarkFileName = "", Wjfl = "";
	String TzYzId = "";
	String mb_id = "";
	String isFirstMan = "";
	int Wjzlbz = 0;
	String selfOPen = request.getParameter("Self");
// 	System.out.println("selfOPen======" + selfOPen);
	if (request.getParameter("wjbm") != null
			&& !"".equals(request.getParameter("wjbm"))) {
		wjbm = request.getParameter("wjbm");
	}
	if (request.getParameter("mb_id") != null
			&& !"".equals(request.getParameter("mb_id"))) {
		mb_id = request.getParameter("mb_id");
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
	 *如果都没有,则生成一个审批件.wps,且是第一个审批件.wps
	 */
	DzwjService dzwjService=new DzwjService();
	FlowListService flowListService=new FlowListService();
	if (request.getParameter("dzwj_id") != null
			&& !request.getParameter("dzwj_id").equals("")) {
		dzwj_id = request.getParameter("dzwj_id");
	} else {
		Map dzwj=dzwjService.queryDzwjByWjbmAndDzwjm("GW_FZXX",wjbm,"正式件%.wps");//筛除.ofd文件
		if(dzwj!=null){
			dzwj_id=String.valueOf(dzwj.get("dzwj_id"));
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
	
	/***************************向流程记录表中添加"开始编辑"********************************************/
	//flowListService.insertFlowlist(wjbm,String.valueOf(user.get("user_name")),"  开始编辑("+fileName+")");
		
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
	DataMsgBus bus=new DataMsgBus();
	Map sw=null;
	ShouWenService shouWenService=new ShouWenService();
	if (!"".equals(wjbm)) {
		bus.put("wjbm",wjbm);
		bus.put("n_id",n_id);
		sw=shouWenService.getShouWenById2(bus);
		if (sw != null) {
			Wjfl = String.valueOf(sw.get("wjlx"));
			DictCommonService dictCommonService=new DictCommonService();
			Map mj= dictCommonService.getDictionaryInfoByCode("mj", String.valueOf(sw.get("mj")));
			Map qxlb= dictCommonService.getDictionaryInfoByCode("qxlb", String.valueOf(sw.get("qxlb")));
			sw.put("mj", mj.get("dict_name"));
			sw.put("qxlb", qxlb==null?"":qxlb.get("dict_name"));
		}
	}
	
	WJXSService wjxsService=new WJXSService();
	if (mb_id.equals("")) {//如果没有从前传来swmb，在本页面重新从数据库中读取
		if (sw != null) {
			Map mb=wjxsService.getMbByWjxsIdAndMblx(String.valueOf(sw.get("wjxs_id")), "审批单模板");
			if(mb!=null){
				swmb=String.valueOf(mb.get("mbmc"));
				mb_id=String.valueOf(mb.get("mbgl_id"))==null?"0":String.valueOf(mb.get("mbgl_id"));
			}	
		}
		Debug.debugMessage(1,"===收文模板="+swmb+"=====收文模板ID=="+mb_id+"当前操作附件id="+dzwj_id+"=======文号后缀hz="+sw.get("hz"));
	}
	String mbmc="";
	if (mbmc.equals("")) {//如果没有从前传来swmb，在本页面重新从数据库中读取
		if (sw != null) {
			Map wjxs=wjxsService.selectById(Integer.parseInt(String.valueOf(sw.get("wjxs_id"))));
			if(wjxs!=null){
				mbmc=String.valueOf(wjxs.get("htgz"));
			}	
		}
		Debug.debugMessage(1,"===收文模板="+mbmc+"=====收文模板ID=="+mb_id+"当前操作附件id="+dzwj_id+"=======文号后缀hz="+sw.get("hz"));
	}
	
	String lx = "号";
	int n_uperid=-1;
	if (sw != null) {
		lx =String.valueOf(sw.get("hz"));
		currNode=String.valueOf(sw.get("n_fr_name"));
		String sNuperid=String.valueOf(sw.get("n_uperid"));
		if(sNuperid!=null&&!"null".equals(sNuperid)){
			n_uperid=Integer.parseInt(sNuperid);
		}
	}
	
	boolean isOneNode = n_uperid>0?false:true;//文件是不是工作流第一步,如果是第一步,不加痕迹
	Debug.debugMessage(1, "是否加痕迹，第一个节点不加痕迹isOneNode="+isOneNode+"  sw.get(\"n_uperid\")="+sw.get("n_uperid"));
	boolean baocun = true;//保存
	boolean bczsj = false;//保存无痕迹文件(正式件)
	boolean bcpdf = false;//保存PDF
	boolean xshj = false;//显示痕迹
	boolean ychj = false;//隐藏痕迹
	boolean dy = true;//打印
	boolean tjtp = true;//填加图片
	
	if (flag.equals("unself")) {
		baocun = false;
	}
	
	//获取当前文件所有领导批示类型
	LdpsService ldpsService=new LdpsService();
	List<Map> ldpslx=ldpsService.getWjxsByWjbm(wjbm);
	//获取当前文件所有意见
	List<Map> ldps=ldpsService.queryLdpsByWjbm(wjbm);
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="hd.js"></script>
<link rel="StyleSheet" href="../../../res/css/newcss.css"
	type="text/css" />
<title>金山</title>
</head>

<body onLoad="loadedit()" onbeforeunload="return gBLC('<%=dzwj_id%>')"
	bgcolor="#F3F3F3" leftmargin="6" topmargin="6"
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
				<!-- <span style="color: red">请注意：为避免文件丢失，系统每间隔五分钟会自动为您保存文件。</span> -->
					<td align="center">
						<INPUT type="BUTTON" ID="button8" class="gradual-btnitem"
							VALUE="打    印" onclick="return print()">
					</td>
					<td align="center"><INPUT type="BUTTON" ID="button9"
						class="gradual-btnitem" width="60px" VALUE="关    闭"
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
//每过5分钟自动保存页面
for ( var i = 1; i < 1000; i++) {
	var tempTime = i * 300000;
	setTimeout("AUTOSAVE('NoTrack')", tempTime);
}

function loadedit(){
	setTimeout("loadedit1()", "30");
}
	
var obj;
var app;

//获取客户端操作系统
var os="";

function writeZTFlag(){
	var xmlhttp2 = getXmlHttp();
	var UrlSetZyzt = "SetZyzt.jsp?dzwj_id=<%=dzwj_id%>&wjbm=<%=wjbm%>";
	xmlhttp2.open("get",UrlSetZyzt,false);
	xmlhttp2.setRequestHeader("Content-Type","text/xml");
	xmlhttp2.send();
	var err = xmlhttp2.responseXML;
	if(getTagNameValue(err.documentElement,"error")!="noerror"){
		alert("写入占用状态失败："+getTagNameValue(err.documentElement,"error"));
		window.close();		
	}	
}
	
function loadedit1(){
	//如果dzwj_id不为空
	if('<%=dzwj_id%>' != ""){
		var xmlhttp1  = getXmlHttp();
		var UrlZyzt = "GetZyzt.jsp?dzwj_id=<%=dzwj_id%>&wjbm=<%=wjbm%>";
		xmlhttp1.open("get",UrlZyzt,false);
		xmlhttp1.setRequestHeader("Content-Type","text/xml");
		xmlhttp1.send();
		var err = xmlhttp1.responseXML;
		if(getTagNameValue(err.documentElement,"error") == "noerror"){
			alert("文件正在被‘"+getTagNameValue(err.documentElement,"UserName")+"’办理");
			window.close();
		}else{
			writeZTFlag();
		}
	}
	if(isLinux()){
		obj = document.getElementById("webwps_id");
		app = obj.Application;
	}
	//继续
	setTimeout("outit()",50);
	OPEN = "open3";
}

function outit() {
	document.all.Layer1.style.display="none";
}

//生成红头的事件
function MakeRedHead(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	//document.all.button1.style.display = "none";
	//***************把现有的保存，用以提高生成红头时，正文内容能够快速插入模板，减少等待时间*********************
	//app.saveAs("/opt/temp.wps");
	//****************************打开红头模板**********************************
	var mbmc = "<%=mbmc %>";
  
	if(mbmc=="" && <%=isTZGJQX%> == true){
		alert("您的部门还没有配置通知模板，请联系系统管理员！");
		return false;
		
	}
	if(mbmc=="" ){
		alert("该文件没有可用的审批单模板！");
		return ;
	}
    
	//打开URL文档
	var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/readdoc.jsp?fromurl=WPSEdit&fwmb_id=<%=mb_id%>";
	app.openDocumentRemote(iturl,false);
	try{
		AfterOpenFromURL();	
	}catch(e){
	}
}

//保存的方法,会在每次"保存"或是"关闭"或是5分钟后调用
function AUTOSAVE(track){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	try{
		HuadiEditToServer("<%=dzwj_id%>,<%=wjbm%>","<%=fileName%>","Save",track);
	}catch(exception){
		alert("保存文件失败,请联系系统管理员");
	}
}

/**
* 华迪保存方法
* ID 电子文件编码,文件编码
* fileName 文件名称
* type 文档是否有痕迹,如果是SaveNoMark则保留无痕迹文档,这个参数是前台的控件用的,不用传到后台去.
* track 是不是要记录流程
*/
function HuadiEditToServer(ID,fileName,type,track){
	var windowW = 400;
	var windowH = 50;
	var wintop=(window.screen.height-windowH)/2;
	var winleft=(window.screen.width-windowW)/2;
	var waitPage = window.open('uploadwait.htm','','width='+windowW+',height='+windowH+',left='+winleft+',top='+wintop+',toolbar=no,scrollbars=no,menubar=no,resizable=yes,l//ocation=no,status=no');
	try{
		//app.enableRevision(false);//停止修订
		var username= "<%=user.get("user_name")%>";
		//encodeURI(encodeURI(username))加密解决在windows环境下乱码问题，下同
		username = encodeURI(encodeURI(username));
		var wjlx ="审批件";
		wjlx = encodeURI(encodeURI(wjlx));
		var dzwjm = document.getElementById("dzwjm").value;
		dzwjm = encodeURI(encodeURI(dzwjm));
		fileName = encodeURI(encodeURI(fileName));
		var sss ;
		//另存无痕迹文档、当前第一个流程节点，去除痕迹
		if(type == "SaveNoMark"||<%=isOneNode%>){
			try{
				//开启修订
				app.enableRevision(true);
				enableRevisionAcceptCommand2();
			}catch(exception){
				waitPage.close();
				alert("无法清除文件痕迹，请联系系统管理员");
				throw exception;
			}
		}
		if(type == "SaveOFD"){
			 var wjlx = "正式件(OFD)";
			 var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&userName="+username+"&t_filename="+encodeURI(encodeURI(wjlx))+"&type=.ofd&os="+os;
			 sss = app.saveURL(url,"ccc.ofd");
		}else if(type == "SavePDF"){
			 var wjlx = "正式件(PDF)";
			 var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&userName="+username+"&t_filename="+encodeURI(encodeURI(wjlx))+"&type=.pdf&os="+os;
			 sss = app.saveURL(url,"ccc.pdf");		
		}else{		
			if(isLinux()){
				app.saveAs("/home/OAFILE/"+ getNowFormatDate()+"-"+MathRand(6) +"-"+decodeURI(decodeURI(fileName)));				
			}else{
				app.saveAs("C:\\\\TEMP\\"+ getNowFormatDate()+"-"+MathRand(6) +"-"+decodeURI(decodeURI(fileName)));
			}
			var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&dzwj_id=<%=dzwj_id%>&userName="+username+"&t_filename="+wjlx+"&type=.wps&os="+os;
			sss = app.saveURL(url,"ccc.wps");
		}
		if(sss){
			waitPage.close();
		}
	}catch(exception){
		waitPage.close();
		alert("保存文件失败，错误代码：3，请联系系统管理员");
	}
}

//关闭,如果baocun为true会调用保存的方法
function closeit(){
	try{
		window.close();
		//保存痕迹
	}catch(exception){
		window.close();
	}
}

//关闭页面之前的操作
function gBLC(dzwj_id){	
}

//不调对话框打印当前文件
function print(){
	 app.print(); 
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
			var name = "<%=user.get("user_name")%>";  //设置修订用户
			//open_file();
			//在打开页面两秒之后隐藏痕迹
			<%if(!"Y".equals(isFirstMan)){%>
				setTimeout("showRevision2()",2000);
			<%}%>
			clearInterval(t1);
			app.setUserName(name);	
			OPEN = "open3";
			commandbar();
			MakeRedHead();
		}
	}else{
		OPEN = "open3";
		MakeRedHead();
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
function AfterOpenFromURL(){
	<%if(sw != null){
		String bmqx="";
		if(sw.get("bmqx")!=null){
			bmqx=String.valueOf(sw.get("bmqx"))+String.valueOf(sw.get("qxlb"));
		}
		String swzh="";
		if(sw.get("zh")!=null){
			swzh=(sw.get("zh")==null?"":sw.get("zh"))+"〔"+(sw.get("nh")==null?"":sw.get("nh"))+"〕"+(sw.get("xh")==null?"":sw.get("xh"))+(sw.get("hz")==null?"":sw.get("hz"));
		}
	%>
		//份号、密级、保密期限、紧急程度、收文机关标志、纪要标示、收文字号、签发人、标题、主送机关、
		//公章、收文机关署名、签发人签名章、成文日期、附注、附件说明、抄送机关、印发机关、印发日期
		app.setDocumentField("份号","");
		app.setDocumentField("密级","<%=sw.get("mj") == null ||"无".equals(String.valueOf(sw.get("mj")))? "" : sw.get("mj")%>");
		app.setDocumentField("保密期限","<%=bmqx%>");
		app.setDocumentField("紧急程度","<%=sw.get("jjcd") == null ||"无".equals(String.valueOf(sw.get("mj")))? "" : sw.get("jjcd")%>");
		app.setDocumentField("收文机关标志","");
		app.setDocumentField("纪要标示","");
		app.setDocumentField("红头文字","<%=mbmc==null?"":mbmc%>");
		app.setDocumentField("收文字号","<%=swzh%>");
		app.setDocumentField("标题","<%=sw.get("bt") == null ? "" : sw.get("bt")%>");
		app.setDocumentField("主送机关","");
		app.setDocumentField("公章","");
		app.setDocumentField("收文机关署名","");
		app.setDocumentField("成文日期","");
		app.setDocumentField("附注","");
		app.setDocumentField("附件说明","");
		app.setDocumentField("印发机关","");
		app.setDocumentField("印发日期","");
		var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=dzwj_id%>";
		app.insertDocument("正文",iturl);	
		
		<%
			if(ldpslx!=null&&ldps!=null&&ldpslx.size()>0&&ldps.size()>0){
				for(int i=0;i<ldpslx.size();i++){
				String code=ldpslx.get(i).get("code")!=null?ldpslx.get(i).get("code").toString():"";
				String name=ldpslx.get(i).get("name")!=null?ldpslx.get(i).get("name").toString():"";
				String yjlx_gz=ldpslx.get(i).get("yjlx_gz")!=null?ldpslx.get(i).get("yjlx_gz").toString():"";
				String yjnr="　　";
				boolean flag2=true;
				for(int j=0;j<ldps.size();j++){
					Map m=ldps.get(j);
					String yjrjb=String.valueOf(m.get("yjrjb"));
					String gzlmc=String.valueOf(m.get("gzlmc"));
					String yjr=String.valueOf(m.get("yjr"));
					String tjr=String.valueOf(m.get("tjr"));
					String tjsj=String.valueOf(m.get("tjsj"));
					
					//领导意见配置规则与领导意见人级别匹配
					//if(("人员级别".equals(yjlx_gz)&&name.contains(yjrjb)&&m.get("gzlmc")==null)||("工作流节点名称".equals(yjlx_gz)&&name.contains(gzlmc))){
					if(("人员级别".equals(yjlx_gz)&&name.contains(yjrjb)&&m.get("gzlmc")==null)||("工作流节点名称".equals(yjlx_gz)&&StringUtil.longestCommonSubstring2(name, gzlmc))){
						String mark="";
						if(m.get("yjr")!=null&&!yjr.equals(tjr)){
							mark="（由"+tjr+tjsj+"代录）";
						}
						
						//判断是否是显示最后一条
						if("是".equals(String.valueOf(ldpslx.get(i).get("xsgz")))){
							//查询结果是降序排列，所以只取第一条
							//if(j==0){
							if(flag2){
								yjnr=yjnr+m.get("yjnr")+"\\n";
								yjnr=yjnr+"　　　　";
								yjnr=yjnr+m.get("yjr");
								yjnr=yjnr+"  --";
								yjnr=yjnr+(m.get("yjsj")==null?"":String.valueOf(m.get("yjsj")).subSequence(0,10)+mark);
								flag2=false;
							}
							//}
						}else{								
							yjnr=yjnr+m.get("yjnr")+"\\n";
							yjnr=yjnr+"　　　　";
							yjnr=yjnr+m.get("yjr");
							yjnr=yjnr+"  --";						
							yjnr=yjnr+(m.get("yjsj")==null?"":String.valueOf(m.get("yjsj")).subSequence(0,10)+mark+"\\n　　");
						}
					}
				}
				//System.out.println("-----"+yjnr);
		%>
			app.setDocumentField("<%=name%>","<%=yjnr%>");
			app.showDocumentField("<%=name%>",true);
		<%}
		}%>
		
		app.showDocumentField("份号",true);
		app.showDocumentField("密级",true);
		app.showDocumentField("保密期限",true);
		app.showDocumentField("紧急程度",true);
		app.showDocumentField("收文机关标志",true);
		app.showDocumentField("纪要标示",true);
		app.showDocumentField("红头文字",true);
		app.showDocumentField("收文字号",true);
		app.showDocumentField("标题",true);
		app.showDocumentField("主送机关",true);
		app.showDocumentField("公章",true);
		app.showDocumentField("收文机关署名",true);
		app.showDocumentField("成文日期",true);
		app.showDocumentField("附注",true);
		app.showDocumentField("附件说明",true);
		app.showDocumentField("印发机关",true);
		app.showDocumentField("印发日期",true);		
		app.showDocumentField("正文",true);		
	<%}else{%>
		var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=dzwj_id%>";
		app.insertDocument("正文",iturl);			
		app.showDocumentField("正文",true);
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
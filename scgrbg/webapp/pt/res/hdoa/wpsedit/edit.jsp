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
	  	/* if(dzwj != null){
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
	  	}  */
	  	//改为只保存一个正式件，所以注释掉以上内容
	  	noMarkFileName="正式件";
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
			if(fw.get("cs")!=null){
				String scs=String.valueOf(fw.get("cs")).replace("，", "，^").replace(",", ",^").replace("、", "、^");
				String[] ary=scs.split("\\^");
				fw.put("cs",ary);				
			}
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
<script type="text/javascript"
	src="wpsjs.js"></script>
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
				<span style="color: red">请注意：为避免文件丢失，系统每间隔五分钟会自动为您保存文件。</span>
				<td align="center"><INPUT type="BUTTON" ID="button1"
					class="gradual-btnitem" width="60px" VALUE="保存"
					onclick="return AUTOSAVE('Track');"></td>
				<td align="center"><INPUT type="BUTTON" ID="button6"
					class="gradual-btnitem" width="60px" VALUE="显示痕迹"
					onclick="return showRevision0()"></td>
				<td align="center"><INPUT type="BUTTON" ID="button7"
					class="gradual-btnitem" width="60px" VALUE="隐藏痕迹"
					onclick="return showRevision2()"></td>
				<%
				//如果文件类型为送审签、请示签，不显示“生成红头操作”
				boolean isMakeRed="送审签".equals(Wjfl)||"请示签".equals(Wjfl);
				if(!"Y".equals(isFirstMan)&&!isMakeRed){ %>
					<!-- 生成红头只能在文书处编号排版环节用 -->
					<%-- <%if(n_fr_name!=null&&(n_fr_name.indexOf("文书处")!=-1&&n_fr_name.indexOf("编号排版")!=-1)){ %>
						<td align="center"><INPUT type="BUTTON" ID="button3"
							class="gradual-btnitem" width="60px" VALUE="生成红头"
							onclick="return MakeRedHead()"></td>
					<%} %> --%>
					<!--如果当前打开文件是正式件，隐藏“生成红头”按钮  -->
					<%if(!iszsj){ %>
					<td align="center"><INPUT type="BUTTON" ID="button3"
						class="gradual-btnitem" width="60px" VALUE="生成红头"
						onclick="return MakeRedHead()"></td>
					<!--四院测试要求，去掉脱痕功能  -->
					<!--如果打开的是正式件，则此按钮不显示  -->
					<td align="center"><INPUT type="BUTTON" id="button2"
						class="gradual-btnitem" width="60px" VALUE="另存为正式件"
						onclick="return SaveNoMark()"></td>
					<%} %>
					<td align="center"><INPUT type="BUTTON" id="button5"
						class="gradual-btnitem" width="60px" VALUE="另存为版式文件"
						onclick="return HUADI_SaveOFD()"></td>
				<%} %>

				<!-- <td align="center"><INPUT type="BUTTON" ID="button9"
					class="gradual-btnitem" width="60px" VALUE="关闭"
					onclick="return closeit()"></td> -->
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
			<%-- <object name="webwps" id="webwps_id" type="application/x-wps"
				data="<%=jsmburl%>" width="100%" height="95%">
				<param name="quality" value="high" />
				<param name="bgcolor" value="#ffffff" />
				<param name="Enabled" value="1" />
				<param name="allowFullScreen" value="true" />
			</object> --%>
		</div>

	</center>
</body>
<script type="text/javascript" language="javascript" charset="utf-8">
function getXmlHttp(){
	var  xmlhttp ;
	if(window.XMLHttpRequest){
 		xmlhttp = new XMLHttpRequest();
	}else{ 
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
	if(is_auto_save){
		setTimeout("AUTOSAVE('NoTrack')", tempTime);
	}
}
var is_auto_save=true;//是否需要自动保存，如果文件套红后没有操作，则不调用自动保存
function loadedit(){
	setTimeout("loadedit1()", "30");
}
	
var obj;
var app;
var WpsObject;
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
var isupdatezt=true;//是否在关闭窗口时需要更改文件阅读状态	
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
			isupdatezt=false;
			window.close();
		}else{
			writeZTFlag();
		}
	}
	/* if(isLinux()){
		obj = document.getElementById("webwps_id");
		//解决新建之后立马能输入
		app = obj.Application;
		window.onblur = function() {
		    console.log("onblur");
		    obj.sltReleaseKeyboard();
		};
	} */
	//继续
	setTimeout("outit()",50);
	OPEN = "open3";
}

function outit() {
	document.all.Layer1.style.display="none";
	//初始化页面时，隐藏“另存为正式件”、“另存为版式文件”按钮
	if(document.all.button2){		
		document.all.button2.style.display="none";
	}
	/* 如果打开的是“正式件.wps”，则不隐藏 */
	<%if(!iszsj){%>
		if(document.all.button5){			
			document.all.button5.style.display="none";
		}
	<%}%>
}

//另存为OFD
function HUADI_SaveOFD(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	//另存为OFD之前先保存一下正文内容
	<%-- HuadiEditToServer("<%=dzwj_id%>,<%=wjbm%>","<%=fileName%>","Save",'NoTrack'); --%>	
		<%if(fw!=null){%>
		app.setDocumentId('<%=fw.get("dzgwbs")%>');//转版前插入公文标识
		<%}%>
	try{
		HuadiEditToServer(",<%=wjbm%>","正式件(OFD).ofd","SaveOFD","Track");
		opener.afterZwedit(true);
	}catch(exception){//alert('exception:'+exception.message);
		alert("保存文件失败,请联系系统管理员，0");
	}
	window.close();
}

function HUADI_SavePDF(){
    if(OPEN != "open3"){
        alert("文档未加载完全,请在文档全部加载后再进行操作");
        return false;
    }
    
    try{
        HuadiEditToServer(",<%=wjbm%>","正式件(PDF).pdf","SavePDF","Track");
		opener.afterZwedit(true);
    }catch(exception){
        alert("保存文件失败,请联系系统管理员，0");
    }
	//window.close();   
}
function SaveNoMark(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	try{
		HuadiEditToServer(",<%=wjbm%>","<%=noMarkFileName%>.wps","SaveNoMark","Track");
		//生成正式件后，隐藏“生成正式件”，显示“另存为版式文件” --start
		document.all.button2.style.display="none";
		document.all.button5.style.display="";
		//生成正式件后，隐藏“生成正式件”，显示“另存为版式文件” --end
		//top.opener.afterZwedit(true);
	}catch(exception){
        alert("保存文件失败,请联系系统管理员，0");
    }
	//window.close();
}

//生成红头的事件
function MakeRedHead(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	//套红之前先判断有没有文号，如果没有文号，则不可套红（因为现在套红后无法再次向文本域添加文号）
	<%
	if(fw!=null){
		if(fw.get("zh")!=null){
		}else{
	%>
			alert("请先生成文号");
			return false;				
	<%
		}
	}%>
	//另存为OFD之前先保存一下正文内容,暂时注释，原因：套红后如果保存的话，会覆盖审批件
	<%-- HuadiEditToServer("<%=dzwj_id%>,<%=wjbm%>","<%=fileName%>","Save",'NoTrack'); --%>	
	is_auto_save=false;
	document.all.button1.style.display = "none";
	//点击“生成红头”后，隐藏“保存”按钮，隐藏“生成红头”按钮，“另存为正式件”显示
	if(document.all.button3){		
		document.all.button3.style.display= "none";
	}
	if(document.all.button2){		
		document.all.button2.style.display= "";
	}
	//***************把现有的保存，用以提高生成红头时，正文内容能够快速插入模板，减少等待时间*********************
	//app.saveAs("/opt/temp.wps");
	//****************************打开红头模板**********************************
	var fwmb = "<%=fwmb %>";
  
	if(fwmb=="" && <%=isTZGJQX%> == true){
		alert("您的部门还没有配置通知模板，请联系系统管理员！");
		return false;		
	}
	if(fwmb=="" ){
		alert("该件没有指定发文模板，不能生成红头！");
		return ;
	}
    
	//打开URL文档
	var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/readdoc.jsp?fromurl=WPSEdit&fwmb_id=<%=fwmb_id%>";
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
		//去掉关闭按钮，在保存操作之后自动关闭窗口
		top.opener.afterZwedit(true);
		window.close();
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
	var waitPage = window.open('uploadwait.html','','width='+windowW+',height='+windowH+',left='+winleft+',top='+wintop+',toolbar=no,scrollbars=no,menubar=no,resizable=yes,location=no,status=no');
	try{
		//app.enableRevision(false);//停止修订
		var username= "<%=user.get("user_name")%>";
		var user_account_name= "<%=user.get("user_account_name")%>";
		//encodeURI(encodeURI(username))加密解决在windows环境下乱码问题，下同
		username = encodeURI(encodeURI(username));
		user_account_name = encodeURI(encodeURI(user_account_name));
		var wjlx ="审批件";
		wjlx = encodeURI(encodeURI(wjlx));
		var dzwjm = document.getElementById("dzwjm").value;
		dzwjm = encodeURI(encodeURI(dzwjm));
		fileName = encodeURI(encodeURI(fileName));
		var sss ;
		//另存无痕迹文档、当前第一个流程节点，去除痕迹，另存版式文件前脱痕
		if(type == "SaveNoMark"||type == "SaveOFD"||<%=isOneNode%>){
			try{
				//开启修订
				app.enableRevision(true);
				//enableRevisionAcceptCommand2();
				//接受所有修订
				WpsObject.acceptAllChanges();
			}catch(exception){
				waitPage.close();
				alert("无法清除文件痕迹，请联系系统管理员");
				throw exception;
			}
		}
		if(type == "SaveOFD"){
			 var wjlx = "正式件(OFD)";
			 var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&userName="+username+"&user_account_name="+user_account_name+"&t_filename="+encodeURI(encodeURI(wjlx))+"&type=.ofd&os="+os;
			 sss = app.saveURL(url,"ccc.ofd");
		}else if(type == "SavePDF"){
			 var wjlx = "正式件(PDF)";
			 var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&userName="+username+"&user_account_name="+user_account_name+"&t_filename="+encodeURI(encodeURI(wjlx))+"&type=.pdf&os="+os;
			 sss = app.saveURL(url,"ccc.pdf");		
		}else{		
			if(isLinux()){
				app.saveAs("/home/OAFILE/"+ getNowFormatDate()+"-"+MathRand(6) +"-"+decodeURI(decodeURI(fileName)));				
			}else{
				app.saveAs("C:\\\\TEMP\\"+ getNowFormatDate()+"-"+MathRand(6) +"-"+decodeURI(decodeURI(fileName)));
			}
			var url = "<%=basePath%>pt/res/hdoa/wpsedit/upload2.jsp?fileName="+fileName+"&ID="+ID+"&track="+track+"&dzwjm="+dzwjm+"&saveType="+type+"&wjbm=<%=wjbm%>&dzwj_id=<%=dzwj_id%>&userName="+username+"&user_account_name="+user_account_name+"&t_filename="+wjlx+"&type=.wps&os="+os;
			sss = app.saveURL(url,"ccc.wps");
		}
		if(sss){
			waitPage.close();
			if(type == "SaveOFD"||type == "SavePDF"){
				alert('保存成功');
			}
		}
	}catch(exception){
		waitPage.close();
		alert("保存文件失败，错误代码：3，请联系系统管理员");
	}
}

//关闭,如果baocun为true会调用保存的方法
function closeit(){
	if(OPEN != "open3"){
		alert("文档未加载完全,请在文档全部加载后再进行操作");
		return false;
	}
	<%if(true){%>
		AUTOSAVE("NoTrack");
	<%}%>
	try{
		//opener.parent.mainFrame.location.reload();
		top.opener.afterZwedit(true);
		window.close();
		//保存痕迹
	}catch(exception){
		window.close();
	}
}

//关闭页面之前的操作
function gBLC(dzwj_id){
	if(isupdatezt){
		var xmlhttp = getXmlHttp();
		xmlhttp.open("GET","updateDzwjBlztState.jsp?dzwj_id=<%=dzwj_id%>",false);
		xmlhttp.setRequestHeader("Content-Type","text/xml");
		xmlhttp.send();
		var err = xmlhttp.responseXML;
		if(getTagNameValue(err.documentElement,"error") == "noerror"){
		}else{
		   // alert("关闭本文件流程出错!");
			  return false;		
		}
	}
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
function init(tagID, w, h){
	var iframe;
	var obj;
	iframe = document.getElementById(tagID);
	var codes=[];   
	//codes.push("");
	codes.push('<object name="webwps" id="webwps_id" type="application/x-wps" data="normal.wpt" width="100%" height="95%"><param name="quality" value="high"/><param name="bgcolor" value="#ffffff"/><param name="Enabled" value="1"/><param name="allowFullScreen" value="true"/></object>');
	//说明：wpsextpara参数决定新建还是打开文档
            //新建语法：wpsextpara='-w'
        //打开语法：wpsextpara='/home/xx/xx.wps'
		//http://192.168.137.80:8080/123.wps
	//若不需要可删除，不可设置为空

	iframe.innerHTML = codes.join("");
	obj = document.getElementById("webwps_id");

	window.onbeforeunload = function() { 
		obj.Application.Quit(); 
	};

	//解决新建之后立马能输入
	/*
		window.onblur = function() {
		    console.log("onblur");
		    obj.sltReleaseKeyboard();
		};
	*/
	
	window.onresize = function() {
	   console.log("ondrag");
	   obj.sltReleaseKeyboard();
	}; 
	return obj;
}
function init2(tagID,width,height){
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
	obj = init("wps", "100%", "100%");
	var Interval_control = setInterval(
	function(){
		app = obj.Application;
		if(app && app.IsLoad()){
			clearInterval(Interval_control);
			//createDocument();
			WpsObject=new Wps2(app);
			var name = "<%=user.get("user_name")%>";  //设置修订用户
			open_file();
			//在打开页面两秒之后隐藏痕迹
			<%if(!"Y".equals(isFirstMan)){%>
				setTimeout("showRevision2()",2000);
			<%}%>
			app.setUserName(name);
			//因为调用openDocument()方法须在插件完全加载完毕之后，所以放在app.IsLoad()的判断中执行 --20150507 liangwenfeng
			app.createDocument("wps");
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
					}catch(exception){
						window.location.reload();
					}
				}else{
			      	var url = "<%=basePath%>pt/res/hdoa/wpsedit/normal.wpt";
			     	app.openDocumentRemote(url,false);			
				}		
				OPEN = "open3";
			<%}%>
			commandbar();
		}
	},500);
}
function InitFrame2(){
	app = init2("wps","100%","100%");
}

if(isLinux()){
	InitFrame();
}else{
	InitFrame2();
	WpsObject=new Wps(app);
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
	<%if(fw != null){
		String yfrq="",cwrq="";
		if(fw.get("yfrq")!=null){
			yfrq=new SimpleDateFormat("yyyy年M月d日").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(fw.get("yfrq"))));
		}
		if(fw.get("cwrq")!=null){
			cwrq=new SimpleDateFormat("yyyy年M月d日").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(String.valueOf(fw.get("cwrq"))));
		}
		String bmqx="";
		if(fw.get("bmqx")!=null){
			if(fw.get("qxlb")!=null){
				bmqx=String.valueOf(fw.get("bmqx"))+String.valueOf(fw.get("qxlb"));
			}else{
				bmqx=String.valueOf(fw.get("bmqx"));
			}
		}
	%>
		//主送机关、印发日期、印发机关、发文机关代字、发文机关署名、发文顺序号、密级、保密期限、年份、成文日期、抄送机关_1、
		//标题、正文、紧急程度、附注、签发人、签发人职务、出席、请假、列席、单位、令号（同份号）、BJ_主送机关_1、附件、
		//FJ_标题（有附件时动态添加到“附件”公文域后面）、FJ_正文（有附件时动态添加到“FJ_标题”公文域后面）、
		
	<%if(fw.get("zs")!=null){%>
		app.setDocumentField("主送机关","<%=fw.get("zs")%>");
		app.showDocumentField("主送机关",true);	
	<%}else{%>	
		app.setDocumentField("主送机关","");
		app.showDocumentField("主送机关",true);
		//如果“主送机关为空”，去掉模板上的“：”
		app.cursorToDocumentField( '主送机关',4 );
		if(isLinux()){						
			app.ActiveDocument.ActiveWindow.Selection.MoveRight();
		}else{					
			WpsObject.moveRight();
		}
		app.backspace();
		app.deleteDocumentField('主送机关');
	<%}%>
		
		app.setDocumentField("印发日期","<%=yfrq%>");
		app.showDocumentField("印发日期",true);	
	
		app.setDocumentField("印发机关","<%=fw.get("yfjg")== null ? "" : fw.get("yfjg")%>");
		app.showDocumentField("印发机关",true);
		
		//暂将模板中份号域置空
		app.setDocumentField("份号","");
		app.showDocumentField("份号",true);
		
		app.setDocumentField("发文机关署名","<%=fw.get("fwjgsm")== null ? "" : fw.get("fwjgsm")%>");
		app.showDocumentField("发文机关署名",true);
		
		app.setDocumentField("发文机关代字","<%=fw.get("zh")== null ? "" : fw.get("zh")%>");
		app.showDocumentField("发文机关代字",true);
		app.setDocumentField("年份","<%=fw.get("nh") == null ? "" : fw.get("nh")%>");
		app.showDocumentField("年份",true);
		app.setDocumentField("发文顺序号","<%=fw.get("xh")== null ? "" : fw.get("xh")%>");
		app.showDocumentField("发文顺序号",true);
		<%
		if(fw.get("zh")!=null){
			String fwzh="";
			if(fw.get("nh")!=null&&fw.get("xh")!=null){
				fwzh=String.valueOf(fw.get("zh"))+"〔"+String.valueOf(fw.get("nh"))+"〕"+String.valueOf(fw.get("xh"))+String.valueOf(fw.get("hz"));
			}
		%>
			app.setDocumentField("令号","<%=fwzh%>");
			app.showDocumentField("令号",true);
			
			app.setDocumentField("纪要编号","<%=fwzh%>");
			app.showDocumentField("纪要编号",true);
		<%
		}
		%>
		
		app.setDocumentField("密级","<%=fw.get("mj") == null || "无".equals(String.valueOf(fw.get("mj")).trim()) ? "" : fw.get("mj")%>");
		app.showDocumentField("密级",true);	
		
		app.setDocumentField("保密期限","<%=bmqx%>");		
		app.showDocumentField("保密期限",true);	
		
		app.setDocumentField("紧急程度","<%=(fw.get("jjcd") == null||"无".equals(String.valueOf(fw.get("jjcd")))) ? "" : fw.get("jjcd")%>");
		app.showDocumentField("紧急程度",true);
		
		app.setDocumentField("成文日期","<%=cwrq%>");
		app.showDocumentField("成文日期",true);	
		//纪要中的公文域
		app.setDocumentField("日期","<%=cwrq%>");
		app.showDocumentField("日期",true);		
		
		//如果当前操作模板没有"抄送机关_1"，则不动态增加"抄送机关_X"公文域
		if(app.isExists("抄送机关_1")){
			app.showDocumentField("抄送机关_1",true);
			<%
			if(fw.get("cs")!=null){
				String[] csdw=(String[])fw.get("cs");
				if(csdw!=null){
					for(int i=0;i<csdw.length;i++){
						if(i==csdw.length-1){
							if(!csdw[i].endsWith("。")){
								csdw[i]=csdw[i]+"。";
							}
						}
			%>
						if(app.setDocumentField("抄送机关_<%=i+1%>",'<%=csdw[i]%>')){
							app.showDocumentField("抄送机关_<%=i+1%>",true);				
						}else{
							if(app.cursorToDocumentField( '抄送机关_<%=i%>',2 )){
								app.insertText(" ");
								if(app.insertDocumentField('抄送机关_<%=i+1%>')){
									app.setDocumentField("抄送机关_<%=i+1%>",'<%=csdw[i]%>');
									app.showDocumentField("抄送机关_<%=i+1%>",true);
								}
							}
						}
			<%
						}
					}
				}else{
			%>
				app.setDocumentField("抄送机关_1","");
				app.showDocumentField("抄送机关_1",true);			
			<%}%>
		}

		app.setDocumentField("标题","<%=fw.get("bt") == null ? "" : fw.get("bt")%>");
		app.showDocumentField("标题",true);

		var iturl = "<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=dzwj_id%>";
		app.insertDocument("正文",iturl);
		app.cursorToDocumentField('正文',3);
		try{
			app.Selection.ParagraphFormat.CharacterUnitFirstLineIndent = 4;
		}catch(e){
			//alert(e.message);
		}
		app.showDocumentField("正文",true);		
		
		<%
		if(fw.get("fz")==null||"".equals(String.valueOf(fw.get("fz")))){
		%>//如果附注没有内容，则删除“附注”公文域，并删除“（”“）”
		  //查询当前模板是否有“附注”公文域，不加判断，会导致光标定位不准确，进而误删其他信息
			if(app.isExists("附注")){				
				try{		
					app.cursorToDocumentField('附注',4);
					if(isLinux()){						
						app.ActiveDocument.ActiveWindow.Selection.MoveRight();
					}else{					
						WpsObject.moveRight();
					}
					app.backspace();
					app.cursorToDocumentField('附注',3);
					app.backspace();
				}catch(e){
					alert(e.message);
				}
				app.deleteDocumentField("附注");	
			}					
		<%}else{
			//从数据库中获取的带换行信息的值需要对换行符处理，在原换行符上加转义符，否则js报错
			//String fz=String.valueOf(fwxx.get("fz")).replace("\r\n", "\\n");
			//String fz=String.valueOf(fwxx.get("fz")).replace("\n", "\\n").replace("\r", "\\r").replace("\r\n", "\\r\\n");
			//在linux环境下不识别\n,会导致套红操作插件崩溃，暂改为全部替换为带转义的\r做换行效果
			String fz=String.valueOf(fw.get("fz")).replace("\n", "\\r").replace("\r", "\\r").replace("\r\n", "\\r\\r");
		%>
			app.setDocumentField("附注","<%=fz%>");
			app.showDocumentField("附注",true);
		<%}%>
		
		app.setDocumentField("签发人","<%=fw.get("qfr") == null ? "" : fw.get("qfr")%>");
		app.showDocumentField("签发人",true);
		app.setDocumentField("签名章","<%=fw.get("qfr") == null ? "" : fw.get("qfr")%>");
		app.showDocumentField("签名章",true);
		
		//特殊文种如纪要、命令（令）、函、议案用 --start
		app.setDocumentField("签发人职务","<%=fw.get("qfrzw") == null ? "" : fw.get("qfrzw")%>");
		app.showDocumentField("签发人职务",true);
		
		if(app.isExists("出席")){
			app.setDocumentField("出席","<%=fw.get("cx") == null ? "" : fw.get("cx")%>");
			app.showDocumentField("出席",true);
		}else{
			app.setDocumentField("出席_1","<%=fw.get("cx") == null ? "" : fw.get("cx")%>");
			app.showDocumentField("出席_1",true);		
		}
		
		if(app.isExists("请假")){
			app.setDocumentField("请假","<%=fw.get("qj") == null ? "" : fw.get("qj")%>");
			app.showDocumentField("请假",true);
		}else{			
			app.setDocumentField("请假_1","<%=fw.get("qj") == null ? "" : fw.get("qj")%>");
			app.showDocumentField("请假_1",true);
		}
		
		if(app.isExists("列席")){
			app.setDocumentField("列席","<%=fw.get("lx") == null ? "" : fw.get("lx")%>");
			app.showDocumentField("列席",true);
		}else{			
			app.setDocumentField("列席_1","<%=fw.get("lx") == null ? "" : fw.get("lx")%>");
			app.showDocumentField("列席_1",true);
		}
		
		app.setDocumentField("单位","<%=fw.get("dw") == null ? "" : fw.get("dw")%>");
		app.showDocumentField("单位",true);
		//特殊文种如纪要、命令（令）、函等用 --end
				
		
	<%
		List<Map> nzfjs=dzwjService.queryNzfjs("GW_FZXX", wjbm,"内置附件");
		if(nzfjs!=null&&nzfjs.size()>0){
			for(int i=0;i<nzfjs.size();i++){
				Map fj=nzfjs.get(i);
				String tmp_dzwjm=String.valueOf(fj.get("dzwjm"));
				tmp_dzwjm=tmp_dzwjm.substring(0,tmp_dzwjm.lastIndexOf("."));//截去文件后缀名部分
		%>
				//新解决方案：
				//1.根据附件数量首先添加好附件用模板，并将模板中公文域名下标更改
				//2.之后将附件名添加到“附件说明”位置，附件标题、附件内容插入到指定域位置
				var fj_wpt='<%=basePath%>pt/res/hdoa/wpsedit/fjmb.wps';
				var fjsm_wpt='<%=basePath%>pt/res/hdoa/wpsedit/fjsm.wpt';

				WpsObject.addAttachment('<%=i+1%>',fj_wpt,fjsm_wpt);
				
				app.setDocumentField("附件<%=i+1%>",'附件<%=i+1%>');
				app.showDocumentField("附件<%=i+1%>",true);	
				var iturl = '<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=fj.get("dzwj_id")%>';
				if(app.isExists('FJ_标题')){					
					app.setDocumentField("FJ_标题",'<%=tmp_dzwjm%>');
					app.insertDocument("FJ_正文",iturl);
				}else if(app.isExists('FJ_标题_<%=i+1%>')){
					app.setDocumentField("FJ_标题_<%=i+1%>",'<%=tmp_dzwjm%>');
					app.insertDocument("FJ_正文_<%=i+1%>",iturl);					
				}
				if('<%=i%>'==0){
					app.setDocumentField("附件说明",'<%=tmp_dzwjm%>');
					app.showDocumentField("附件说明",true);	
				}else{
					var retCur = app.cursorToDocumentField("附件说明",4);
					if (retCur){
						try{											
							app.ActiveDocument.ActiveWindow.Selection.TypeParagraph();										
							var ret2 = app.insertText('<%=tmp_dzwjm%>');
						}catch(e){
							//alert(e.message);
						}
					}
					
				}
	<%		}
		}else{
	%>
		//如果没有附件则删除“附件说明”公文域
		if(app.isExists("附件说明")){
			app.cursorToDocumentField("附件说明",3);
			if(isLinux()){					
				app.Selection.Cells.Delete(2);
			}else{
				WpsObject.deleteRow();
			}
			app.deleteDocumentField("附件说明");	
		}
		if(app.isExists("附件")){
			app.deleteDocumentField("附件");
		}
		if(app.isExists("FJ_标题")){
			app.cursorToDocumentField("FJ_标题",3);
			WpsObject.deleteTable();
			app.deleteDocumentField("FJ_标题");
		}
		if(app.isExists("FJ_正文")){
			app.deleteDocumentField("FJ_正文");
		}	
	<%}%>
		//国产环境中如果当前代码段放在方法执行前部，则无法删除“★”
		<%
		if(fw.get("mj")!=null&&!"".equals(String.valueOf(fw.get("mj")).trim())&&!"无".equals(String.valueOf(fw.get("mj")).trim())&&!"非密".equals(String.valueOf(fw.get("mj")))){
		%>
			app.showDocumentField("密级",true);			
			app.showDocumentField("保密期限",true);	
		<%}else{%>
			//如果密级为“非密”，则不在文件上显示密级和保密期限
			app.deleteDocumentField("密级");			
			app.deleteDocumentField("保密期限");				
			app.cursorToDocumentField('紧急程度',3);
			app.backspace();
		<%}%>
	<%}%>
	WpsObject.gevenNumber();
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

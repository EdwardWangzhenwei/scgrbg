<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@ page contentType="text/html;charset=GBK" %>
<%@ page language="java" import="java.sql.*,java.util.*" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
Map user = (Map)session.getAttribute("USERINFO");
%>
<%
  String dzwj_id = "",wjbm = "955",Fwmb = "",type = "",fileName = "",flag="",fileExt,isFirstMan="",noMarkFileName="";
  int Wjzlbz = 0;
  String selfOPen = request.getParameter("Self");
  if(request.getParameter("wjbm") != null){
    wjbm = request.getParameter("wjbm");
  }
  if(request.getParameter("type") != null){
    type = request.getParameter("type");
  }

  if(request.getParameter("dzwj_id") != null && !request.getParameter("dzwj_id").equals("")){
	  dzwj_id = request.getParameter("dzwj_id");
  }
  
  String sRemoteFileName = "";
  if(!dzwj_id.equals("")){
    DzwjService dzwjService=new DzwjService();
    Map dzwj=dzwjService.getDzwjById(dzwj_id);
    if(dzwj!=null){
    	//sRemoteFileName=String.valueOf(dzwj.get("dzwjm"));
    	sRemoteFileName=String.valueOf(dzwj.get("file_name"));
    }
  }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Foxit OFD Reader FireFox Plugin</title>
<!-- <script type="text/javascript" src="res/npapi/Foxit_npapi.js" charset="UTF-8"></script> -->
<script type="text/javascript" src="foxit.ofd.ocx.js" charset="UTF-8"></script>
</head>

<script type="text/javascript">
var obj;
var xmlparam = "";
function load(){
	var width = document.documentElement.clientWidth;
	var height = document.documentElement.clientHeight;
	obj = OFD.OCX.init("FireFoxOFDDIV", width, height);
	obj.ready(obj._id);
	openFile();
	initSetting();
}

function openFile(){
    var ofdName ="<%=sRemoteFileName%>";
    if(ofdName!=null&&ofdName!=""){
        var iturl = "<%=basePath%>pt/res/hdoa/foxit/ReadOFD.jsp?fileName="+ofdName;//通过流读取文件
        //alert(iturl);
        obj.openFile(iturl);
    }    
}
//插件调用方法
function initSetting() {
	fx_clickbtn();
	obj.addTrackInfo(xmlparam);
	obj.setCompositeVisible("verify",true);
	obj.setCompositeVisible("sign",true);
	//fx_setViewPreference();
	//fx_setLogURL();
	obj.setPrintInfo(1);
}
function closeFile() 
{
    obj.closeFile();window.history.go(-1);
    //window.close();
}
function fx_clickbtn() {
    xmlparam = "<setinfo type=\"barinfo\"><barinfo1><parameter name=\"pages\" value=\"1\"/><parameter name=\"rotate\" value=\"270\"/><parameter name=\"xpostype\" value =\"right\"/><parameter name=\"ypostype\" value=\"middle\"/><parameter name=\"x\" value=\"50\"/><parameter name=\"y\" value=\"150\"/><parameter name=\"w\" value=\"150\"/><parameter name=\"h\" value=\"150\"/><parameter name=\"show\" value=\"1\"/><parameter name=\"print\" value=\"1\"/><parameter name=\"strcont\" font=\"仿宋_GB2312\" size=\"18\" color=\"00FF00\"><setinfo type=\"barinfo\"><barinfo1><parameter name=\"pages\" value=\"1\"/><parameter name=\"rotate\" value=\"270\"/><parameter name=\"xpostype\" value =\"right\"/><parameter name=\"ypostype\" value=\"middle\"/><parameter name=\"x\" value=\"50\"/><parameter name=\"y\" value=\"150\"/><parameter name=\"w\" value=\"150\"/><parameter name=\"h\" value=\"150\"/><parameter name=\"show\" value=\"1\"/><parameter name=\"print\" value=\"1\"/><parameter name=\"strcont\" font=\"仿宋_GB2312\" size=\"18\" color=\"00FF00\">文字水印2</parameter></barinfo2></setinfo ></parameter></barinfo2></setinfo >";
}
</script>

<body onload="load()">
    <div id="buttonDiv"  style="text-align: center;">
   <!--  <input type="button" value="保存" onclick="saveFile();" /> 
    <input type="button" value="关闭" onclick="closeFile();" /> -->
    <input type="button" value="关闭" onclick="closeFile();"  style="margin: 10px 0px 0px 0px;"/>
    </div>
    <div id="FireFoxOFDDIV">
    </div> 
</body>
</html>

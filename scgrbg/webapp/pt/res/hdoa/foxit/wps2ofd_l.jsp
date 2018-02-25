<%@page import="java.util.Map"%>
<%@page import="com.aisino.platform.db.DbSvr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%

	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path + "/";
	String con_table_id = "",con_table="",fileName="",dzwj_id="";
	if (request.getParameter("con_table_id") != null) {
		con_table_id = request.getParameter("con_table_id");
	}
	if (request.getParameter("con_table") != null) {
		con_table = request.getParameter("con_table");
	}
	DbSvr db = DbSvr.getDbService("gwsys");
	try {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from gw_dzwj where con_table=? and dzwjsx='正式件' and dzwjlx like '%wps%' ");
		sql.append("AND con_table_id=? ");
		sql.append("order by dzwjm desc, dzwj_id desc ");
		Map m = db.getOneRecorder(sql.toString(), new Object[] { con_table,con_table_id });

		if (m != null) {
			fileName=m.get("file_name").toString();
			dzwj_id=m.get("dzwj_id").toString();
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		db.release();
	}
%>
</head>

<body onload="InitFrame2()">
正在发送,请不要关闭浏览器。请稍候...
<div id="wps" align="right" style="width:800px; height:800px; left:400px; position:absolute"></div>
</body>
</html>

<script type="text/javascript">


var obj = null;
var app;

function init(tagID, w, h){
	var iframe;
	var obj;
	iframe = document.getElementById(tagID);
	var codes=[];   
	//codes.push("");
	codes.push("<object  name='webwps' id='webwps_id' type='application/x-wps'  data='Normal.dotm'  width='100%'  height='100%'> <param name='Enabled' value='1' />  </object>");
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

	window.onblur = function() {
	    console.log("onblur");
	    obj.sltReleaseKeyboard();
	};

	window.onresize = function() {
	   console.log("ondrag");
	   obj.sltReleaseKeyboard();
	}; 

	return obj;
}
var flag=0;
//初始化
function InitFrame1(){
	obj = init("wps", "100%", "100%");
	var Interval_control = setInterval(
	function(){
		app = obj.Application;
		if(app && app.IsLoad()){
			clearInterval(Interval_control);
			createDocument();
		}
	},500);
	flag = 1;
}

//打开远程文档
function openDocumentRemote1(){	
	var url="<%=basePath%>pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id=<%=dzwj_id%>";
	var aa = app.openDocumentRemote(url, true);
	flag = 2;
} 

//保存到远程
function saveURL(){
	if(flag == 0){
		InitFrame1();
	}else if(flag == 1){
		openDocumentRemote1();
		//setTimeout('console.log("等待5秒后执行")',15000);
	}else if(flag == 2){
		var url="<%=basePath%>pt/res/hdoa/foxit/upload.jsp?con_table_id=<%=con_table_id%>&os=linux";
		//转版之前脱痕 start
		app.enableRevision(true);
		var doc = app.Documents.ActiveDocument();
		var revisions = doc.get_Revisions();
		revisions.AcceptAll();
		//转版之前脱痕 end
		
		var aa = app.saveURL(url,".ofd");
		flag = 3;
		window.close();
	}
}

function InitFrame2(){
	var t=setInterval("saveURL()",1000);
	if(flag == 3){
		clearInterval(t);
	}
}
</script>
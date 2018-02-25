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
	String con_table_id = "",con_table="",fileName="",file_catalog="";
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
			file_catalog=m.get("file_catalog").toString();
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		db.release();
	}
%>

</head>
<script type="text/javascript">

	var DocFrame;
	var MenuItems = {FILE:1<<0, EDIT:1<<1, VIEW:1<<2, INSERT:1<<3, FORMAT:1<<4, TOOL:1<<5, CHART:1<<6, HELP:1<<7};
	var FileSubmenuItems = {NEW:1<<0, OPEN:1<<1, CLOSE:1<<2, SAVE:1<<3, SAVEAS:1<<4, PAGESETUP:1<<5, PRINT:1<<6, PROPERTY:1<<7};

	function init(tagID, width, height) {
		var iframe;
		var obj;
		iframe = document.getElementById(tagID);
		var codes=[];   
		codes.push('<object id=DocFrame1 height=' + height + ' width=' + width + ' ');
		codes.push('data=data:application/x-oleobject;base64,7Kd9juwHQ0OBQYiirbY6XwEABAA7DwMAAgAEAB0AAAADAAQAgICAAAQABAD///8ABQBcAFgAAABLAGkAbgBnAHMAbwBmAHQAIABBAGMAdABpAHYAZQBYACAARABvAGMAdQBtAGUAbgB0ACAARgByAGEAbQBlACAAQwBvAG4AdAByAG8AbAAgADEALgAwAAAA ');
		codes.push('classid=clsid:8E7DA7EC-07EC-4343-8141-88A2ADB63A5F viewastext=VIEWASTEXT></object> ');
		iframe.innerHTML = codes.join("");
		obj = document.getElementById("DocFrame1");
		return obj;
	}
	
	var flag = 0;
	//初始化
	function InitFrame() {
		DocFrame = init("wps", "90%", "90%");
		flag = 1;
	}
	
	//打开远程
	function openDocumentRemote() {
		var url="<%=basePath%>pt/res/hdoa/foxit/ReadOFD_l_w.jsp?fileName=<%=fileName%>";
		var aa = DocFrame.openDocumentRemote(url,true);
        flag = 2;
    }
	
	//保存到远程
	function saveURL(){
		if(flag == 0){
			InitFrame();
		}else if(flag == 1){
			openDocumentRemote();
			setTimeout('console.log("等待5秒后执行")',5000);
		}else if(flag == 2){
			var url="<%=basePath%>pt/res/hdoa/foxit/upload.jsp?con_table_id=<%=con_table_id%>&os=windows";
			DocFrame.enableRevision(true);
			DocFrame.ActiveDocument.Revisions.AcceptAll;
			var ret = DocFrame.saveURL(url,".ofd");
			flag=3;
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
<body onload="InitFrame2()">
正在发送,请不要关闭浏览器。请稍候...
	<div id="wps" align="right"
		style="width: 800px; height: 800px; left: 300px; position: absolute">
	</div>
</body>
</html>
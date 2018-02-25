<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>发文在线编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="StyleSheet" href="../css/newcss.css" type="text/css" />
<script charset="UTF-8" src="../../pt.js"></script>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path + "/";
    String filename = request.getParameter("filename");
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
		openDocumentRemote();
	}
	
	//打开远程
	function openDocumentRemote() {
		var url="<%=basePath%>pt/res/hdoa/wpsedit/showFj.jsp?filename=<%=filename%>";
		var aa = DocFrame.openDocumentRemote(url,true);
       
    }
	
	//保存到远程
	function saveURL(){
	
			var url="<%=basePath%>pt/res/hdoa/wpsedit/upload.jsp";
			var ret = DocFrame.saveURL(url,'<%=filename%>');
			
	}
</script>
<body onload="InitFrame()" >
	 <table width="100%" height="700px" >
        <tr>
            <td height="30">
                <div id="doc" align="center" style="position:inherit">
                 <button onclick="modifyTag()" class="gradual-btnitem" />&nbsp;&nbsp; 添加标签 &nbsp;&nbsp; </button>
	  	         <button onclick="saveURL()" class="gradual-btnitem" />  &nbsp;&nbsp;保存文件&nbsp;&nbsp; </button>
	             <button onclick="top.close();" class="gradual-btnitem" />&nbsp;&nbsp; 关    闭  &nbsp;&nbsp;</button>	
                </div>
                </td>
                </tr>
                 <td  vlign="top" align="center">
                <div id="wps"  style="height:700px">
                </div>
            </td>
      </table>
	
</body>
</html>
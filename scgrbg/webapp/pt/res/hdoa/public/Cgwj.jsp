<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.com.huadi.aos.hdoa.fzxx.service.FzxxService"%>
<%@ page import="cn.com.huadi.aos.dict.service.DictCommonService"%>
<% 
String wjbm = request.getParameter("wjbm");
FzxxService fs = new FzxxService();
Map wjxx = fs.queryWjxxById(wjbm);
String wjlx = (String)wjxx.get("wjlx");

%>
<html>
<head>
<title>大电子文件上传</title>
<style type="text/css">
body{
background-color: #f1f0f0;
}
.td_arrow{
 background:url(../../../img/imgs.png) no-repeat; 
}
.TableLine{
	background-color: #FFFFFF;
	text-align: left;
	height:30px;
	border-top-width: 1px;
	border-left-width: 1px;
	border-top-style: solid;
	border-left-style: solid;
	border-top-color: #cdcdcd;
	border-left-color: #cdcdcd; 
	/* border-top-color: #7F9DB9;
	border-left-color: #7F9DB9;*/
}
.TDLineL{
	font-size: 14px;
	/* background-color: #ECF5FF; */
	
	text-align: right;
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-bottom-color: #cdcdcd;
	border-right-color: #cdcdcd;
	padding-top: 4px;
	height:30px;
	width: 10%;
}
.TDlineL-r {
	font-size: 14px;
	text-align: left;
	
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-bottom-color: #cdcdcd;
	border-right-color: #cdcdcd;
	height:30px;
	width: 20%;
	padding: 0 1 0 3;

}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="StyleSheet" href="../../css/newcss.css" type="text/css" />
</head>

<body>
<form name="form1" enctype="multipart/form-data" method="post">
  <table width="550" height="30" border="0" cellpadding="0" cellspacing="0" align="center" >
    <tr> 
      <td> <table width="550" border="0" cellspacing="0" cellpadding="0" align="center">
          <tr  > 
            <td width="6%" class="td_arrow"></td>
            <td class="titleTxt14">添加电子文件</td>
          </tr>
        </table></td>
    </tr>
  </table>
  <table width="550" border="0" cellspacing="8" cellpadding="0" align="center">
    <tr> 
      <td><table width="550" border="0" cellspacing="0" cellpadding="0" align="center" class="TableLine">
      	  <tr> 
            <td class="TDLineL" nowrap width="10%">文件属性&nbsp;</td>
            <td class="TDlineL-r" nowrap width="80%"> &nbsp;<select name="wjlx" style="width:120px;">
            <option value="文件内容">文件内容</option>
            <option value="附件内容">附件内容</option>
            </select></td>
          </tr>
          <tr> 
            <td class="TDLineL">文档1&nbsp;</td>
            <td class="TDlineL-r"> &nbsp;<input id="file11" name="file1" type="file"></td>
          </tr>
          <tr> 
            <td class="TDLineL">文档2&nbsp;</td>
            <td class="TDlineL-r">&nbsp;<input id="file11" name="file2" type="file"></td>
          </tr>
          <tr> 
            <td class="TDLineL">文档3&nbsp;</td>
            <td class="TDlineL-r"> &nbsp;<input id="file11" name="file3" type="file"></td>
            </select></td>
          </tr>
          <tr> 
            <td class="TDLineL">文档4&nbsp;</td>
            <td class="TDlineL-r">&nbsp;<input id="file11" name="file4" type="file"></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" class="button-table-01">
          <tr> 
            <td> 
              <center>
                <input name="Confirm" type="submit" class="gradual-btnitem" value=" 确 定  " onClick="return uploadit()">
                <input name="Submit22" type="button" class="gradual-btnitem" value=" 关 闭 " onClick="window.close()">
              </center></td>
          </tr>
        </table></td>
    </tr>
  </table>
  </form>
</body>
<script language="javascript">
function uploadit(){
	var file1 = document.form1.file1.value;
	var file2 = document.form1.file2.value;
	var file3 = document.form1.file3.value;
	var file4 = document.form1.file4.value;
	if(file1=='' && file2=='' && file3=='' && file4==''){
		alert('请添加文件!');
		return false;
	} else{
		var Url = "fileUpload.jsp?wjbm="+'<%=wjbm%>';
		document.form1.action = Url;
	}
}
</script>
</html>

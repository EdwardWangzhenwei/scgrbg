<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.com.huadi.aos.hdoa.fzxx.service.FzxxService"%>
<%@ page import="cn.com.huadi.aos.dict.service.DictCommonService"%>
<% 
String wjbm = request.getParameter("wjbm");
FzxxService fs = new FzxxService();
Map wjxx = fs.queryWjxxById(wjbm);
String wjlx = (String)wjxx.get("wjlx");
int mj = Integer.parseInt(wjxx.get("mj")==null?"-1":(String)wjxx.get("mj"));
DictCommonService ds = new DictCommonService();
List<Map> l = ds.getDictionaryByGroupNameAndSecret("mj", mj);
%>
<%!
public String options(int mj, List<Map> l){
	StringBuffer options = new StringBuffer("<option></option>");
	if(l != null){
		for(Map m : l){
			options.append("<option value='").append(m.get("code")).append("'>")
				.append(m.get("name")).append("</option>");
		}
	}
	return options.toString();
}
%>
<html>
<head>
<title>添加电子文件</title>
<style type="text/css">
body{
background-color: #f1f0f0;
 margin: 0px;
  padding: 0px; 
}
.td_arrow{
 background:url(../../../img/imgs.png) no-repeat center center; 
 height:20px;
 width:15px;
 
}

.TableLine{
	background-color: #FFFFFF;
	text-align: left;
	height:40px;
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
	height:40px;

	
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
	height:40px;
	padding: 0 1 0 3;

}
.TDlineL-c {
	font-size: 14px;
	text-align: center;	
	border-right-width: 1px;
	border-bottom-width: 1px;
	border-right-style: solid;
	border-bottom-style: solid;
	border-bottom-color: #cdcdcd;
	border-right-color: #cdcdcd;
	height:40px;
	padding: 0 1 0 3;

}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="StyleSheet" href="../../css/newcss.css" type="text/css" />
</head>

<body >
<form name="form1" enctype="multipart/form-data" method="post">
  <table width="100%" height="32" border="0" cellpadding="0" cellspacing="0"  align="center" id="titleLayout" >
         <tr  > 
           <td width="1%" ></td>
            <td  class="td_arrow"></td>
            <td class="titleTxt14">&nbsp;添加电子文件</td>
        
    </tr>
  </table>
  <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr> 
      <td colspan="4" height="5px">
      </td>
      </tr>
    <tr> 
      <td><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="TableLine">
      	  <tr> 
            <td class="TDLineL" nowrap width="10%">文件属性&nbsp;</td>
            <td class="TDlineL-r" nowrap width="80%"> &nbsp;<select name="wjlx" style="width:120px;">
            <%
            if("发文".equals(wjlx)){%>
            <option value="文件内容">文件内容</option>
            <option value="办文参考">办文参考</option>
            <%}else if("收文".equals(wjlx)){%>
          	<option value="来文内容">来文内容</option>
            <option value="附件内容">附件内容</option>
            <%}%>
            </select></td>
            <td class="TDlineL-c" nowrap width="10%" >密级</td>
          </tr>
          <tr> 
            <td class="TDLineL" nowrap  width="10%">文档1&nbsp;</td>
            <td class="TDlineL-r"  width="80%"> &nbsp;<input name="file1" type="file"  style="width:500px;height: 30px !important;"></td>
            <td class="TDlineL-r" nowrap  width="10%">&nbsp;<select name="mj1" style="width:60px;height: 30px !important;">
            <%out.print(options(mj,l)); %>
            </select></td>
          </tr>
          <tr> 
            <td class="TDLineL" nowrap>文档2&nbsp;</td>
            <td class="TDlineL-r">&nbsp;<input name="file2" type="file"  style="width:500px;height: 30px !important;" ></td>
            <td class="TDlineL-r">&nbsp;<select name="mj2" style="width:60px;height: 30px !important;">
             <%out.print(options(mj,l)); %>
            </select></td>
          </tr>
          <tr> 
            <td class="TDLineL" nowrap>文档3&nbsp;</td>
            <td class="TDlineL-r"> &nbsp;<input name="file3" type="file" style="width:500px;height: 30px !important;" ></td>
            <td class="TDlineL-r">&nbsp;<select name="mj3" style="width:60px;height: 30px !important;">
             <%out.print(options(mj,l)); %>
            </select></td>
          </tr>
          <tr> 
            <td class="TDLineL" nowrap>文档4&nbsp;</td>
            <td class="TDlineL-r">&nbsp;<input name="file4" type="file" style="width:500px;height: 30px !important;" ></td>
            <td class="TDlineL-r">&nbsp;<select name="mj4" style="width:60px;height: 30px !important;">
             <%out.print(options(mj,l)); %>
            </select></td>
          </tr>
        </table></td>
    </tr>
    <tr> 
      <td><table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" class="button-table-01">
          <tr> 
            <td> 
              <center>
                <button name="Confirm"  class="gradual-btnitem"  onMouseOver="this.className='gradual-btnitemMouseover'" onMousedown="this.className='gradual-btnitemMousedown'"  onMouseout="this.className='gradual-btnitem'"  style="width:60px;text-align:center;"  onClick="return uploadit()" >确定</button>
               <button  name="Submit"  class="gradual-btnitem"   onMouseOver="this.className='gradual-btnitemMouseover'" onMousedown="this.className='gradual-btnitemMousedown'" onMouseout="this.className='gradual-btnitem'"  style="width:60px;text-align:center" onClick="window.close()" />关闭</button>
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
	var file2 = document.form1.file1.value;
	var file3 = document.form1.file1.value;
	var file4 = document.form1.file1.value;
	if(file1=='' && file2=='' && file3=='' && file4==''){
		alert('请添加文件!');
		return false;
	} else if(checkMj('file1', 'mj1') || checkMj('file2', 'mj2') || checkMj('file3', 'mj3') || checkMj('file4', 'mj4')){
		alert('请选择密级！');
		return false;
	}else{
		var Url = "fileUpload.jsp?wjbm="+'<%=wjbm%>';
		document.form1.action = Url;
		document.form1.submit();
	}
}

function checkMj(file, mj){
	var a = eval('document.form1.'+file+'.value');
	var b = eval('document.form1.'+mj+'.value');
	if(a != ''){
		if(b == ''){
			return true;
		}
	}
	return false;
}
</script>
</html>

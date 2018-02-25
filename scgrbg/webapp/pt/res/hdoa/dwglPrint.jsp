<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="cn.com.huadi.aos.hdoa.dwgl.service.ExeclHSSF"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="cn.com.huadi.aos.hdoa.dwgl.service.ExcelService"%>
<%@ include file="sessionCheck.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style>  
	 @media print  
	 {     
	     .Noprint{DISPLAY:none;}     
	     .PageNext{PAGE-BREAK-AFTER:always}     
	 }   
	</style>  
	<script type="text/javascript">
	
	
	var hkey_root,hkey_path,hkey_key;
	hkey_root="HKEY_CURRENT_USER";
	hkey_path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";

	// 设置网页打印的页眉页脚为空
	function pagesetup_null()
	{
	try{
	  var RegWsh = new ActiveXObject("WScript.Shell");
	  hkey_key="header";    
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	  hkey_key="footer";
	  RegWsh.RegWrite(hkey_root+hkey_path+hkey_key,"");
	}catch(e){ alert(e); }
	}
	
		function btnPrintClick(){  
        window.print();  
    }  
    
    function preview()    
     {    
    	
        bdhtml=window.document.body.innerHTML;    
        sprnstr="<!--startprint-->";    
        eprnstr="<!--endprint-->";    
        prnhtml=bdhtml.substring(bdhtml.indexOf(sprnstr)+17);    
        prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));    
        window.document.body.innerHTML=prnhtml;    
        window.print();    
	}  
</script>  

  </head>
    
  <body>

 
  <!--startprint-->
  <%
  		String   dwmc=Chinese.ISO8859ToUTF8(request.getParameter("dwmc"));
		String   xzqy_id=Chinese.ISO8859ToUTF8(request.getParameter("xzqy_id"));
		String   lb=Chinese.ISO8859ToUTF8(request.getParameter("lb"));
		String   gjc=Chinese.ISO8859ToUTF8(request.getParameter("gjc"));
		
		System.out.println("xzqy_id:"+xzqy_id+":lb"+lb+":gjc:"+gjc);
 	 	ExcelService  excel=new ExcelService();
		List<Map> resultList=excel.QueryHygdByTj(request,dwmc,xzqy_id,lb,gjc);
		String dwmc1 = "";
		String lb1 = "";
		String tjcd1 = "";
		String gjc1 = "";
		String gjc2 = "";
		String gjc3 = "";
		%>

<table id= 'tab1' border="1"  bordercolor="#000" width="980px" cellspacing="0"  style="border-collapse:collapse" cellpadding="0" align="center"> 
   <caption> <span style="font-size: 40px;font-weight: bold;">点位管理表</span></caption>
 					<tr>
					  <th class="award-name">点位名称</th>
					  <th class="award-name">类别</th>
					 <th class="award-name">推荐程度</th>
					  <th class="award-name">关键词1</th>
					  <th class="award-name">关键词2</th>
					 <th class="award-name">关键词3</th>
				 	</tr> 
				 	
 <%		if(resultList!=null&&resultList.size()>0){
		for(int k=0;k<resultList.size();k++){
			Map map = resultList.get(k);
			//清除数据
			dwmc1 = "";
			lb1 = "";
			tjcd1 = "";
			gjc1 = "";
			gjc2 = "";
			gjc3 = "";
			//赋值
			if(String.valueOf(map.get("dwmc"))!=null&&!String.valueOf(map.get("dwmc")).equals("")&&!String.valueOf(map.get("dwmc")).equals("null")){
				dwmc1=String.valueOf(map.get("dwmc"));
			}
			if(String.valueOf(map.get("lb"))!=null&&!String.valueOf(map.get("lb")).equals("")&&!String.valueOf(map.get("lb")).equals("null")){
				lb1=String.valueOf(map.get("lb"));
			}
			if(String.valueOf(map.get("tjcd"))!=null&&!String.valueOf(map.get("tjcd")).equals("")&&!String.valueOf(map.get("tjcd")).equals("null")){
				tjcd1=String.valueOf(map.get("tjcd"));
			}
			if(String.valueOf(map.get("gjc1"))!=null&&!String.valueOf(map.get("gjc1")).equals("")&&!String.valueOf(map.get("gjc1")).equals("null")){
				gjc1=String.valueOf(map.get("gjc1"));
			}
			if(String.valueOf(map.get("gjc2"))!=null&&!String.valueOf(map.get("gjc2")).equals("")&&!String.valueOf(map.get("gjc2")).equals("null")){
				gjc2=String.valueOf(map.get("gjc2"));
			}
			if(String.valueOf(map.get("gjc3"))!=null&&!String.valueOf(map.get("gjc3")).equals("")&&!String.valueOf(map.get("gjc3")).equals("null")){
				gjc3=String.valueOf(map.get("gjc3"));
			}
		
			
			 %>  
		     <tr class="textA">     
	            <td align="center"  class="award-name"><%=dwmc1 %></td>  
	          	<td align="center" class="award-name"><%=lb1 %></td>  
	           	<td align="center" class="award-name"><%=tjcd1 %></td>  
	  			 <td align="center" class="award-name"><%=gjc1 %></td>  
	  			 <td align="center" class="award-name"><%=gjc2 %></td>  
	  			  <td align="center" class="award-name"><%=gjc3 %></td>
    		</tr> 
		
	 <%    	}	}else{
		 
	 }
  %>
  </table>

   
	 <style>
	 
	 table{table-layout: fixed;border:0px solid #fff;}
	 .award-name{ font-family:'宋体'; font-weight: bold;}
	 #tab1 td,#tab1 tr{
	 	min-height: 50px;
	 	
	 	
	 }
	 caption{
	   font-family:'宋体';
	 }
	 .textA{
	  font-family:'宋体';
	  font-size: 14px;
	 }
	 th{
	  text-align:center;
	  font-family:'宋体';
	  font-size: 20px;
	 }
    #btPrint {
        position: relative;
        height: 30px;
        margin: 0 auto;
        background: #fff;
    }
    input {
        position: absolute;
        top: 20px;
        left:50%;
        margin-left:-40px;
        width: 80px;
        height: 30px;
    }
    
    #tabfoot{
    	margin-top: 10px;
    }
	</style>
   <!--endprint-->
   <div id='btPrint'><input id="btnPrint" value="打印" type="button"  onclick="preview()"/></div>
  </body>
   
</html>
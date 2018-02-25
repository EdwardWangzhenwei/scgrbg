<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=utf-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="cn.com.huadi.aos.hdoa.dwgl.service.ExeclHSSF"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="cn.com.huadi.aos.hdoa.dwgl.service.ExcelService"%>

<%@ include file="sessionCheck.jsp"%>
<%
	String   dwmc= Chinese.ISO8859ToUTF8(request.getParameter("dwmc"));
	String   xzqy_id= Chinese.ISO8859ToUTF8(request.getParameter("xzqy_id"));
	String   lb= Chinese.ISO8859ToUTF8(request.getParameter("lb"));
	String   gjc= Chinese.ISO8859ToUTF8(request.getParameter("gjc"));
	
	ExcelService  excel=new ExcelService();
	List<Map> resultList=excel.QueryHygdByTj(request,dwmc,xzqy_id,lb,gjc);
	if(resultList !=null){
		String filename="点位管理查询导出";
    	response.setContentType("application/vnd.ms-excel;charset=UTF-8");
    	response.setCharacterEncoding("utf-8");
		response.addHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(filename)+".xls\"");    
		//确保IE识别本次为下载文件   
		response.setHeader("Content-Transfer-Encoding","binary");   
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		response.setHeader("Pragma", "public"); 
		
		ExeclHSSF hsf = new ExeclHSSF(6);
		
		hsf.setCellOneData(0, 0, "点位名称","center", 1);	
		hsf.setColumnWidth(0, 160);
		hsf.setCellOneData(0, 1, "类别","center", 1);	
		hsf.setColumnWidth(1, 100);
		hsf.setCellOneData(0, 2, "推荐程度","center", 1);	
		hsf.setColumnWidth(2, 70);
		hsf.setCellOneData(0, 3, "关键词1","center", 1);	
		hsf.setColumnWidth(3, 100);
		hsf.setCellOneData(0, 4, "关键词2","center", 1);	
		hsf.setColumnWidth(4, 100);
		hsf.setCellOneData(0, 5, "关键词3","center", 1);	
		hsf.setColumnWidth(5, 100);
		
		
		String dwmc1 = "";
		String lb1 = "";
		String tjcd1 = "";
		String gjc1 = "";
		String gjc2 = "";
		String gjc3 = "";
		
		
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
			if(map.get("dwmc") != null){
				dwmc1=String.valueOf(map.get("dwmc"));
			}
			if(map.get("lb") != null){
				lb1=String.valueOf(map.get("lb"));
			}
			if(map.get("tjcd") != null){
				tjcd1=String.valueOf(map.get("tjcd"));
			}
			if(map.get("gjc1") != null){
				gjc1=String.valueOf(map.get("gjc1"));
			}
			if(map.get("gjc2") != null){
				gjc2=String.valueOf(map.get("gjc2"));
			}
			if(map.get("gjc3") != null){
				gjc3=String.valueOf(map.get("gjc3"));
			}
			
			hsf.setCellOneData(k+1, 0, dwmc1,"center", 1);	
			hsf.setCellOneData(k+1, 1, lb1,"center", 1);	
			hsf.setCellOneData(k+1, 2, tjcd1,"center", 1);	
			hsf.setCellOneData(k+1, 3, gjc1,"center", 1);	
			hsf.setCellOneData(k+1, 4, gjc2,"center", 1);	
			hsf.setCellOneData(k+1, 5, gjc3,"center", 1);	
		}
		
	    out.clear(); 
	    out = pageContext.pushBody(); 		
		hsf.setTitle("共有"+resultList.size()+"条记录",resultList.size()+1,"right");
	 	java.io.OutputStream  stream = response.getOutputStream();
	    hsf.execl(stream); 
	  }else {%>
			<script language="javascript">
			  	alert("查询结果为空,没有数据导出,请重新查询");
			  	window.close();
			</script>	  
	  <%} %>
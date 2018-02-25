<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import="cn.com.huadi.aos.hdoa.gudingzican.zcwx.service.ExeclHSSF"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="cn.com.huadi.aos.hdoa.gudingzican.zcwx.service.ExcelService"%>
<%
	String   bxsj=Chinese.ISO8859ToUTF8(request.getParameter("bxsj"));
	String   bxbm=Chinese.ISO8859ToUTF8(request.getParameter("bxbm"));
	String   wxsj=Chinese.ISO8859ToUTF8(request.getParameter("wxsj"));
	String   zcbh=Chinese.ISO8859ToUTF8(request.getParameter("zcbh"));
	String   zcmc=Chinese.ISO8859ToUTF8(request.getParameter("zcmc"));
	String bxsj1="";
	String bxsj2="";
	String wxsj1="";
	String wxsj2="";	
	if(bxsj.equals("")){
		bxsj1="";
		bxsj2="";
	}else{
		String[] str = bxsj.split(",");
		bxsj1=str[0];
		bxsj2=str[1];		
	}
	if(wxsj.equals("")){
		wxsj1="";
		wxsj2="";		
	}else{
		String[] str = wxsj.split(",");
		wxsj1=str[0];
		wxsj2=str[1];		
	}	
	ExcelService  excel=new ExcelService();
	List<Map> map=excel.QueryWxByTj(bxsj1, bxsj2, bxbm, wxsj1, wxsj2, zcbh, zcmc);
	if(map !=null){
		String filename1="维修记录查询导出";
    	String filename = new String(filename1.getBytes("GBK"),"iso8859-1");
    	response.setContentType("text/html;charset=iso8859-1");   
		response.addHeader("Content-Disposition", "attachment; filename=\""+filename+".xls\"");    
		//确保IE识别本次为下载文件   
		response.setHeader("Content-Transfer-Encoding","binary");   
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		response.setHeader("Pragma", "public"); 
		
		ExeclHSSF hsf = new ExeclHSSF(8);
		hsf.setCellOneData(0, 0, "资产编号","center", 1);	
		hsf.setCellOneData(0, 1, "资产名称","center", 1);	
		hsf.setCellOneData(0, 2, "报修部门","center", 1);	
		hsf.setCellOneData(0, 3, "报修时间","center", 1);	
		hsf.setCellOneData(0, 4, "故障描述","center", 1);	
		hsf.setCellOneData(0, 5, "维修时间","center", 1);	
		hsf.setCellOneData(0, 6, "维修价格","center", 1);	
		hsf.setCellOneData(0, 7, "维修描述","center", 1);	
		int  k=1;
		String bxsjFormat="";
		String wxsjFormat="";
		String zcbh1="";
		String zcmc1="";
		String bxbm1="";
		String gzms1="";
		String wxjg1="";
		String xlms1="";
		for(Map  list:map){
			bxsjFormat="";
			wxsjFormat="";
			zcbh1="";
			zcmc1="";
			bxbm1="";
			gzms1="";
			wxjg1="";
			xlms1="";			
			 if(list.get("bxsj") !=null){
				 bxsjFormat=new SimpleDateFormat("yyyy-MM-dd").format(list.get("bxsj"));
			 }
			 if(list.get("wxsj") !=null){
				 wxsjFormat=new SimpleDateFormat("yyyy-MM-dd").format(list.get("wxsj"));
			 }
			 if(list.get("zcbh")!=null){
				 zcbh1=String.valueOf(list.get("zcbh"));
			 }
			 if(list.get("zcmc")!=null){
				 zcmc1=String.valueOf(list.get("zcmc"));
			 }
			 if(list.get("bxbm")!=null){
				 bxbm1=String.valueOf(list.get("bxbm"));
			 }
			 if(list.get("gzms")!=null){
				 gzms1=String.valueOf(list.get("gzms"));
			 }
			 if(list.get("wxjg")!=null){
				 wxjg1=String.valueOf(list.get("wxjg"));
			 }
			 if(list.get("xlms")!=null){
				 xlms1=String.valueOf(list.get("xlms"));
			 }
				hsf.setCellOneData(k, 0, zcbh1,"center", 1);	
				hsf.setCellOneData(k, 1, zcmc1,"center", 1);	
				hsf.setCellOneData(k, 2, bxbm1,"center", 1);	
				hsf.setCellOneData(k, 3,bxsjFormat,"center", 1);	
				hsf.setCellOneData(k, 4,gzms1,"center", 1);	
				hsf.setCellOneData(k, 5, wxsjFormat,"center", 1);	
				hsf.setCellOneData(k, 6, wxjg1,"center", 1);	
				hsf.setCellOneData(k, 7, xlms1,"center", 1);				 
				k++;		
		}
	    out.clear(); 
	    out = pageContext.pushBody(); 		
		hsf.setTitle("共有"+map.size()+"条记录",k,"right");
	 	java.io.OutputStream  stream = response.getOutputStream();
	    hsf.execl(stream);
	  }else {%>
			<script language="javascript">
			  	alert("查询结果为空,没有数据导出,请重新查询");
			  	window.close();
			</script>	  
	  <%} %>
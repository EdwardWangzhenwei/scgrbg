<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.poi.hssf.usermodel.*"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@page import="cn.com.huadi.aos.hdoa.gudingzican.zcwx.service.ExeclHSSF"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="cn.com.huadi.aos.hdoa.qingjiaguanli.huiyiqingjia.meetinglistsum.service.ExcelList" %>
<%	

	int   hygl_id=Integer.valueOf(request.getParameter("hygl_id"));
	ExcelList  lls=new ExcelList();
	List<Map>  map=lls.QueryAttendList(hygl_id);
	List<Map>  map1=lls.QueryLeaveList(hygl_id);
	if(map !=null||map1 !=null){
	//	String filename1="会议名单导出";
    //	String filename = new String(filename1.getBytes("GBK"),"iso8859-1");
    //	response.setContentType("text/html;charset=iso8859-1");   
	//	response.addHeader("Content-Disposition", "attachment; filename=\""+filename+".xls\"");    
		//确保IE识别本次为下载文件   
		response.setHeader("Content-Transfer-Encoding","binary");   
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");        
		response.setHeader("Pragma", "public"); 
		
		String path=request.getServletContext().getRealPath("pt/res/excelTemplate/人员名单汇总模板.xls");
		InputStream in = new FileInputStream(new File(path)); 
		HSSFWorkbook work = new HSSFWorkbook(in);  
		HSSFSheet sheet = work.getSheetAt(0);
		HSSFRow rowCellStyle = sheet.getRow(2);  
		HSSFCellStyle columnOne = rowCellStyle.getCell(0).getCellStyle();
		HSSFRow row = sheet.getRow(0); 
		HSSFCell cell = row.getCell(2);  
		int  k=1;
		short s1=0;
		short s2=1;
		for(Map  list:map){
				row = sheet.createRow(k);// 得到行  
				cell = row.createCell(s1);// 得到第0个单元格  
				
				String bmrxm=String.valueOf(list.get("bmrxm"));
				String ssdwmc=String.valueOf(list.get("ssdwmc"));
				if(bmrxm.equals("null") ){
					bmrxm="";
				}
				if(ssdwmc.equals("null")){
					ssdwmc="";
				}
				if(!bmrxm.equals("null")||!bmrxm.equals("") ){
				}				
				cell.setCellValue(bmrxm);
				cell.setCellStyle(columnOne);
				cell = row.createCell(s2);
				cell.setCellValue(ssdwmc);
				cell.setCellStyle(columnOne);
				k++;		
		}
		HSSFSheet sheet01 = work.getSheetAt(1);
		HSSFRow rowCellStyle01 = sheet01.getRow(2);  
		HSSFCellStyle columnOne1 = rowCellStyle01.getCell(0).getCellStyle();
		HSSFRow row01 = sheet01.getRow(0); 
		HSSFCell cell01 = row01.getCell(3);  
		int  k1=1;
		short s11=0;
		short s22=1;
		short s33=2;
		for(Map  list:map1){
			row01 = sheet01.createRow(k1);// 得到行  
			cell01 = row01.createCell(s11);
			
			String bmrxm=String.valueOf(list.get("bmrxm"));
			String ssdwmc=String.valueOf(list.get("ssdwmc"));
			String qjsy=String.valueOf(list.get("qjsy"));
			if(bmrxm.equals("null")){
				bmrxm="";
			}
			if(ssdwmc.equals("null")){
				ssdwmc="";
			}
			if(qjsy.equals("null")){
				qjsy="";
			}
			cell01.setCellValue(bmrxm);
			cell01.setCellStyle(columnOne1);
			cell01 = row.createCell(s22);
			cell01.setCellValue(ssdwmc);
			cell01.setCellStyle(columnOne1);
			cell01 = row.createCell(s33);
			cell01.setCellValue(qjsy);
			cell01.setCellStyle(columnOne1);
			k1++;		
	}
		OutputStream os = response.getOutputStream();// 取得输出流  
		String filename1="会议名单导出";
	   String filename = new String(filename1.getBytes("GBK"),"iso8859-1");
	    response.setContentType("text/html;charset=iso8859-1");   
		response.addHeader("Content-Disposition", "attachment; filename=\""+filename+".xls\"");   
		work.write(os);
	    out.clear(); 
	    out = pageContext.pushBody(); 
		os.close();  
		
		
		/*ExeclHSSF hsf = new ExeclHSSF(2,"出席人名单");
		hsf.setCellOneData(0, 0, "出席人员","center", 1);	
		hsf.setCellOneData(0, 1, "所属单位","center", 1);	
		int  k=1;
		for(Map  list:map){
				String bmrxm=String.valueOf(list.get("bmrxm"));
				String ssdwmc=String.valueOf(list.get("ssdwmc"));
				if(bmrxm.equals("null") ){
					bmrxm="";
				}
				if(ssdwmc.equals("null")){
					ssdwmc="";
				}
				hsf.setCellOneData(k, 0,bmrxm,"center", 1);	
				hsf.setCellOneData(k, 1, ssdwmc,"center", 1);	
				k++;		
		}
		hsf.setTitle("共有"+map.size()+"条记录",k,"right");		
		hsf.addSheet("请假人名单");
		hsf.setCellOneData(0, 0, "出席人员","center", 1);	
		hsf.setCellOneData(0, 1, "所属单位","center", 1);	
		hsf.setCellOneData(0, 2, "请假事由","center", 1);
		k=1;
		for(Map  list:map1){
				String bmrxm=String.valueOf(list.get("bmrxm"));
				String ssdwmc=String.valueOf(list.get("ssdwmc"));
				String qjsy=String.valueOf(list.get("qjsy"));
				System.err.print(ssdwmc);
				System.err.println("ssdwmc="+list.get("ssdwmc"));
				if(bmrxm.equals("null")){
					bmrxm="";
				}
				if(ssdwmc.equals("null")){
					ssdwmc="";
				}
				if(qjsy.equals("null")){
					qjsy="";
				}
				hsf.setCellOneData(k, 0, bmrxm,"center", 1);	
				hsf.setCellOneData(k, 1, ssdwmc,"center", 1);	
				hsf.setCellOneData(k, 2, qjsy,"center", 1);	
				k++;		
		}		
	    out.clear(); 
	    out = pageContext.pushBody(); 		
		hsf.setTitle("共有"+map.size()+"条记录",k,"right");
	 	java.io.OutputStream  stream = response.getOutputStream();
	    hsf.execl(stream);*/
	  } else {%>
			<script language="javascript">
			  	alert("查询结果为空,没有数据导出,请重新查询");
			  	window.close();
			</script>	  
	  <%} %>
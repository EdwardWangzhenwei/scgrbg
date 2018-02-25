<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%
	try {
		
	
        String fileType =  "application/octet-stream";
		String filePath =  (String)request.getParameter("filename");
		System.out.println(filePath);
		String realName = filePath.substring(filePath.lastIndexOf(File.separator ),filePath.length());
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IOException("您要找的文件不存在！");
		}
		ServletOutputStream os = null;
		FileInputStream in = null;
		response.setHeader("Content-Disposition", "attachment; filename="
						+ realName);
		try {
			os = response.getOutputStream();
			in = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int bytes = -1;
			while ((bytes = in.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytes);
			}
			os.flush();
			os.close();
			
			os=null;  
		   response.flushBuffer();  
			out.clear();  
			out = pageContext.pushBody();  


		} catch (Exception e) {
			throw new IOException(
					"文件下载失败 \n erorr in downloadFileFromSystem()!");
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (Exception ex) {
					
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
				}
			}
		}

	} catch (Exception e) {
		e.printStackTrace();
		out.println("文件不存在或下载出错！");
	}
%>
<html>
<head>
<%@ page language="java" import="java.io.*" %>
<%@ page language="java" import="java.lang.*" %>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--<%@ page language="java" contentType="text/html" pageEncoding="utf-8" %> -->
<title>This page for response</title>
</head>
<body>   
<%
   try {
		String os = request.getParameter("os");
		String path = request.getParameter("path");
		if (request.getContentLength() > 0) 
		{           

			if (os!=null&&"linux".equals(os.toLowerCase())) {
				InputStream in = request.getInputStream();
				File f = new File(path);
				FileOutputStream o = new FileOutputStream(f);
				byte b[] = new byte[1024];
				int n;
				while ((n = in.read(b)) != -1) {
					o.write(b, 0, n);
				}
				o.close();
				in.close();
			} else {
				InputStream in = request.getInputStream();
				byte b[] = new byte[4096];
				//固定128字节
				byte filename[] = new byte[128];
				int contentlen;
				int filenamelen;
				String name = "test.....";
				if ((filenamelen = in.read(filename)) != -1)
				{
					int i;
					for (i = 0; i < filename.length && filename[i] != 0; i++) { }
					name = new String(filename, 0, i);
				}
				
				String realFileName = name;


				System.out.println("name " + name);
				System.out.println("realFileName " + realFileName);

				File f = new File(realFileName);
				FileOutputStream o = new FileOutputStream(f);
				while ((contentlen = in.read(b)) != -1)
				{                             
					o.write(b, 0, contentlen);
				}
				o.close();
				in.close();
			}
		}
	} 
	catch (IOException e){
		
		e.printStackTrace();
	}
%>    
</body>
</html>
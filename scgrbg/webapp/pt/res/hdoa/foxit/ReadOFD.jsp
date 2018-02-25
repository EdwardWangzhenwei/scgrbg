<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%
	request.setCharacterEncoding("utf-8");
	String fileName = "";
	if(request.getParameter("fileName") != null && !"".equals(request.getParameter("fileName"))){
		fileName = request.getParameter("fileName");
	}
	File file = null;

	String path = (String)RegServiceServlet.SYSCON.get("uploadPath");
	file = new File(path+fileName);
	if (file.exists()) {
		BufferedInputStream bis = null;
		FileInputStream instream = null;
		BufferedOutputStream bos = null;
		ServletOutputStream output = null;
		int requestlen = (new Long(file.length())).intValue();
		try {

			String agent = request.getHeader("User-Agent");
			response.setStatus(200);
			response.setHeader("Content-disposition", "inline; filename="
					+ fileName);
			response.setContentLength(requestlen);
			response.setContentType("application/ofd");
			output = response.getOutputStream();
			bos = new BufferedOutputStream(output);

			instream = new FileInputStream(file);
			bis = new BufferedInputStream(instream);

			byte[] buffer = new byte[4096];
			int readsize = -1;
			while (instream != null && (readsize = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, readsize);

			}
			bos.flush();
		} catch (IOException exp) {
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	} else {

	}
%>
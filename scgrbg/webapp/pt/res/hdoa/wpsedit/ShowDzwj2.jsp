<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.io.*"%>

<%
	try {
		//直接指定文件路径下载
		String filepath = request.getParameter("filepath");
		filepath=new String(filepath.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@打开的文件:"
				+ filepath);
		File file = new File(filepath);
		if (!file.exists()) {
			throw new IOException("您要找的文件已经被删除！");
		}
		ServletOutputStream os = null;
		FileInputStream in = null;

		String agent = (String) request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") == -1) {
			String enableFileName = "";
			try {
				enableFileName = new String(String.valueOf(
						"附件.zip").getBytes("UTF-8"),
						"ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.addHeader("Content-Disposition",
					"attachment; filename=" + enableFileName);
		} else {
			try {
				response.addHeader(
						"Content-Disposition",
						"attachment;filename="
								+ java.net.URLEncoder.encode(
										String.valueOf("附件.zip"),
										"UTF-8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}

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
			os = null;
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
	} catch (Exception e) { //错误处理
		e.printStackTrace();
		PrintWriter toClient = response.getWriter(); //得到向客户端输出文本的对象
		response.setContentType("text/html;charset=gb2312");
		toClient.write("无法打开文件!");
		toClient.close();
	}
%>
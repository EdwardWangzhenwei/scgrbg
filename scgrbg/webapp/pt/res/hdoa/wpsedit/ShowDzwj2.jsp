<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.io.*"%>

<%
	try {
		//ֱ��ָ���ļ�·������
		String filepath = request.getParameter("filepath");
		filepath=new String(filepath.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("@@@@@@@@@@@@@@@@@@@@@�򿪵��ļ�:"
				+ filepath);
		File file = new File(filepath);
		if (!file.exists()) {
			throw new IOException("��Ҫ�ҵ��ļ��Ѿ���ɾ����");
		}
		ServletOutputStream os = null;
		FileInputStream in = null;

		String agent = (String) request.getHeader("USER-AGENT");

		if (agent != null && agent.indexOf("MSIE") == -1) {
			String enableFileName = "";
			try {
				enableFileName = new String(String.valueOf(
						"����.zip").getBytes("UTF-8"),
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
										String.valueOf("����.zip"),
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
					"�ļ�����ʧ�� \n erorr in downloadFileFromSystem()!");
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
	} catch (Exception e) { //������
		e.printStackTrace();
		PrintWriter toClient = response.getWriter(); //�õ���ͻ�������ı��Ķ���
		response.setContentType("text/html;charset=gb2312");
		toClient.write("�޷����ļ�!");
		toClient.close();
	}
%>
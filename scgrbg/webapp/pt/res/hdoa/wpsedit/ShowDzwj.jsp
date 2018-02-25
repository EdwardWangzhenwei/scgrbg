<%@page import="javax.sound.midi.SysexMessage"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.GetFileContentType"%>
<%@page import="java.util.Map"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%
	try {
		String dzwj_id = request.getParameter("dzwj_id");
		if (dzwj_id != null && !"".equals(dzwj_id)) {

			DzwjService dzwjService = new DzwjService();
			Map m = dzwjService.getDzwjById(dzwj_id);

			if (m != null) {//�ļ�����Ӳ����
				String filePath = String.valueOf(m.get("file_catalog"))
						+ String.valueOf(m.get("file_name"));
				System.out.println("@@@@@@@@@@@@@@@@@@@@@�򿪵��ļ�:"
						+ filePath);
				File file = new File(filePath);
				if (!file.exists()) {
					throw new IOException("��Ҫ�ҵ��ļ��Ѿ���ɾ����");
				}
				ServletOutputStream os = null;
				FileInputStream in = null;
				String fileCon = "";
				if (m.get("dzwjlx") != null) {
					fileCon = String.valueOf(m.get("dzwjlx"));
				}
				/* response.setContentType(GetFileContentType
						.getContentType(fileCon)); */
				String agent = (String) request.getHeader("USER-AGENT");

				if (agent != null && agent.indexOf("MSIE") == -1) {
					String enableFileName = "";
					try {
						enableFileName = new String(String.valueOf(
								m.get("dzwjm")).getBytes("UTF-8"),
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
												String.valueOf(m
														.get("dzwjm")),
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
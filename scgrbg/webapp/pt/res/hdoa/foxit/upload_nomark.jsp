<%@page import="com.aisino.platform.db.Eso"%>
<%@page import="com.aisino.platform.db.Crud"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@page import="com.sun.org.apache.xalan.internal.xsltc.compiler.sym"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.aisino.platform.db.DbSvr"%>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>

<%
	String con_table_id = "";
	if (request.getParameter("con_table_id") != null) {
		con_table_id = request.getParameter("con_table_id");
	} else {
%>
<script type="text/javascript">
	alert("附件ID为空！");
	windows.close();
</script>
<%
	}

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String H_fileAddress = (String) RegServiceServlet.SYSCON
			.get("uploadPath");
	String random = String.valueOf(Math.random() * 10000000.0D);
	String H_fileName = new SimpleDateFormat("yyyyMMddHHmmss")
			.format(new Date()) + "-" + random.substring(0, 6) + ".ofd";
	int dzwj_size;
	try {
		if (request.getContentLength() > 0) {
			try {
				String os = request.getParameter("os");
				if (request.getContentLength() > 0) {
					dzwj_size=request.getContentLength();
					if (os != null && "linux".equals(os.toLowerCase())) {
						InputStream in = request.getInputStream();
						File f = new File(H_fileAddress + H_fileName);
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
						if ((filenamelen = in.read(filename)) != -1) {
							int i;
							for (i = 0; i < filename.length
									&& filename[i] != 0; i++) {
							}
							name = new String(filename, 0, i);
						}

						File f = new File(H_fileAddress + H_fileName);
						FileOutputStream o = new FileOutputStream(f);
						while ((contentlen = in.read(b)) != -1) {
							o.write(b, 0, contentlen);
						}
						o.close();
						in.close();
					}
					DbSvr db = DbSvr.getDbService("gwsys");
					try {
						Object dzwj_id = db
								.executeQueryOne(
										"select seq_dzwj.nextval as id  from dual",
										null).toString();
						String col = "dzwj_id;dzwjm;dzwjlx;dzwjsx;dzwj_size;file_catalog;file_name;llsj;zt;con_table;con_table_id";
						Object[] values = new Object[] { dzwj_id,
								"正式件(OFD)1.ofd", "ofd", "正式件",dzwj_size,
								H_fileAddress, H_fileName, new Date(),
								0, "GW_FZXX", con_table_id };
						Crud crud = new Crud("gw_dzwj");
						crud.define(col, values);
						Eso eso = crud.getInsertEso();
						db.execute(eso);
						db.commit();
						db.release();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						db.release();
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		} else {
			//Debug.debugMessage(1,"没有文件");
%>
<script type="text/javascript">
	alert("没有文件！");
</script>
<%
	}
	} catch (IOException e) {
		//Debug.debugMessage(1,"文件上传失败");
		e.printStackTrace();
	}
%>
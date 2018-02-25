<%@page import="cn.com.huadi.aos.hdoa.common.util.DataBus"%>
<%@page import="com.aisino.platform.util.SessUtil"%>
<%@page import="cn.com.huadi.aos.hdoa.common.service.FlowListService"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Debug"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@page import="java.io.DataInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	try {
		Debug.debugMessage(1,"************UPLOAD WPS***********");
		request.setCharacterEncoding("utf-8");

		String sType = Chinese.ISO8859ToGBK(request.getParameter("type"));
		String ID = Chinese.ISO8859ToGBK(request.getParameter("ID"));
		//如果saveType为NoTrack不记录流程
		String track = Chinese.ISO8859ToGBK(request.getParameter("track"));
		Debug.debugMessage(1, "sType====" + sType);
		String wjbm = request.getParameter("wjbm");
		String os = request.getParameter("os");
		String dzwj_id = Chinese.ISO8859ToGBK(request.getParameter("dzwj_id"));
		String dzwjm="";
		String userName="",user_account_name="";
		String t_filename="";
		String fileName =""; 
		//中文转码有以下几种情况：国产服务器+国产终端、国产服务器+x86终端、x86服务器+国产终端、x86服务器+x86终端
		String web_os=System.getProperty("os.name").toLowerCase();//服务器操作系统
		if("Linux".equals(os)){
			dzwjm = URLDecoder.decode(URLDecoder.decode(request.getParameter("dzwjm"),"utf-8"),"utf-8");			
			userName = URLDecoder.decode(URLDecoder.decode(request.getParameter("userName"),"utf-8"),"utf-8");
			user_account_name = URLDecoder.decode(URLDecoder.decode(request.getParameter("user_account_name"),"utf-8"),"utf-8");
			t_filename = URLDecoder.decode(URLDecoder.decode(request.getParameter("t_filename"),"utf-8"),"utf-8");
			fileName = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileName"),"utf-8"),"utf-8");
		}else{
			if(web_os.indexOf("windows")!=-1){//x86服务器+x86终端			
				dzwjm = Chinese.ISO8859ToUTF8(request.getParameter("dzwjm"));
				userName = Chinese.ISO8859ToUTF8(request.getParameter("userName"));
				user_account_name = Chinese.ISO8859ToUTF8(request.getParameter("user_account_name"));
				t_filename = Chinese.ISO8859ToUTF8(request.getParameter("t_filename"));
				fileName = Chinese.ISO8859ToUTF8(request.getParameter("fileName"));
			}else{
				dzwjm = URLDecoder.decode(URLDecoder.decode(request.getParameter("dzwjm"),"utf-8"),"utf-8");			
				userName = URLDecoder.decode(URLDecoder.decode(request.getParameter("userName"),"utf-8"),"utf-8");
				user_account_name = URLDecoder.decode(URLDecoder.decode(request.getParameter("user_account_name"),"utf-8"),"utf-8");
				t_filename = URLDecoder.decode(URLDecoder.decode(request.getParameter("t_filename"),"utf-8"),"utf-8");
				fileName = URLDecoder.decode(URLDecoder.decode(request.getParameter("fileName"),"utf-8"),"utf-8");
			}
		}
		
		if ((t_filename == null) || ("".equals(t_filename))) {
			t_filename = "审批件";
		}
		String saveType = Chinese.ISO8859ToGBK(request.getParameter("saveType"));
		Debug.debugMessage(1, "userName====" + userName + "===wjbm==="+ wjbm + "===dzwj_id===" + dzwj_id + "===t_filename==="+ t_filename);

		String random = String.valueOf(Math.random() * 10000000.0D);
		String H_fileAddress = (String) RegServiceServlet.SYSCON.get("uploadPath");
		String H_fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ "-"+ random.substring(0, 6)+ sType;

		Debug.debugMessage(1,"=============H_fileName="+ H_fileAddress + H_fileName);
		int dzwj_size;
		try {
			dzwj_size = request.getContentLength();
			Debug.debugMessage(1, "上传文件长度：" + dzwj_size);
			Debug.debugMessage(1, "上传文件格式：" + request.getContentType());
			if (request.getContentLength() > 0) {
				if (os.equals("Linux")) {
					InputStream in = request.getInputStream();
					String realFileName = H_fileAddress + H_fileName;
					File f = new File(realFileName);
					FileOutputStream o = new FileOutputStream(f);
					byte b[] = new byte[1024];
					int n;
					while ((n = in.read(b)) != -1) {
						o.write(b, 0, n);
					}
					o.close();
					in.close();
					Debug.debugMessage(1, "文件上传成功！");
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

					String realFileName = H_fileAddress + H_fileName;

					Debug.debugMessage(1, "文件的保存路径： " + realFileName);

					File f = new File(realFileName);
					FileOutputStream o = new FileOutputStream(f);
					while ((contentlen = in.read(b)) != -1) {
						o.write(b, 0, contentlen);
					}
					o.close();
					in.close();
					Debug.debugMessage(1, "文件上传成功！路径 : " + realFileName);
				}
			} else {
				Debug.debugMessage(1, "没有文件");
			}
			if ((sType == null) || ("".equals(sType))) {
				sType = ".wps";
			}
			String dzwjbm = "";
			boolean isOK = true;

			Debug.debugMessage(1, "ID：" + ID);
			String[] temp = ID.split(",");
			dzwjbm = temp[0];
			wjbm = temp[1];

			DzwjService dzwjService = new DzwjService();

			Map m = null;
			if (("".equals(dzwjbm)) || (dzwjbm == null)) {
				m = dzwjService.getDzwjInfo("GW_FZXX", wjbm, "正式件");
				if (m != null) {
					dzwjbm = String.valueOf(m.get("dzwj_id"));
				} else {
					m = dzwjService.getDzwjInfo("GW_FZXX", wjbm, "审批件%");
					if (m != null) {
						dzwjbm = String.valueOf(m.get("dzwj_id"));
					}
				}
			}

			String noMarkFileName = "";
			String flowlist_name=fileName;//记录流程追踪时用
			//无痕迹文档文件名已经在edit.jsp页面生成，即fileName，此处不必对此操作，故注释以下内容 --lwf 20161128
			/* if (fileName.equals("审批件.wps")
					|| fileName.indexOf("正式件") != -1) {*/
				/*生成无痕迹文件时的文件名称
				 *如果是审批件.wps第一次生成红头时为“正式件１”
				 *以后每生成一次正式件后面的数字自动加１
				 */
				/*List<Map> dzwjInfos = dzwjService.getDzwjInfos(
						"GW_FZXX", wjbm, "正式件%.wps");
				if (dzwjInfos != null) {
					String dzwjm2 = String.valueOf(dzwjInfos.get(dzwjInfos.size()-1)
							.get("dzwjm"));
					int tempNum = 0;
					try {
						String tempName = dzwjm2.substring(3,
								dzwjm2.length() - 4);
						tempNum = Integer.parseInt(tempName);
					} catch (Exception e) {
						noMarkFileName = "正式件1.wps";
					}
					noMarkFileName = "正式件"
							+ String.valueOf(tempNum + 1) + ".wps";
					flowlist_name=noMarkFileName;
				} else {
					noMarkFileName = "正式件1.wps";
					flowlist_name="正式件1.wps";
				}
			} else {
				noMarkFileName = fileName;
				flowlist_name=fileName;
			} */

			try {
				String addorupdate = "";
				boolean isDzwjbm = true;
				Map dzwjInfo = new HashMap();
				List<Map> dzwjInfos = dzwjService.getDzwjInfos(
						"GW_FZXX", wjbm, "正式件(OFD)%");
				Debug.debugMessage(
						1,
						dzwjInfos != null ? String.valueOf(dzwjInfos
								.get(0).get("dzwjm"))
								: "dzwjInfos no data------");

				if (!dzwjbm.equals("")) {
					isDzwjbm = false;

					addorupdate = "update";
					if (saveType.equals("SaveOFD")) {
						if (dzwjInfos != null
								&& dzwjInfos.get(0) != null) {
							dzwjbm = String.valueOf(dzwjInfos.get(0)
									.get("dzwj_id"));//如果有正式件(OFD).ofd文件,将dzwjInfos[0].iDzwjbm赋值给dzwjbm，执行update操作
						} else {
							isDzwjbm = true;
							addorupdate = "insert";
						}
					} else if (saveType.equals("SaveNoMark")) {
						//fileName = noMarkFileName;
						//如果有正式件，则后续操作覆盖之前文件，保留最后一次生成的文件
						List<Map> dzwjInfos2 = dzwjService.getDzwjInfos(
								"GW_FZXX", wjbm, "正式件%.wps");
						if (dzwjInfos2 != null&& dzwjInfos2.get(0) != null) {
							dzwjbm = String.valueOf(dzwjInfos2.get(0)
									.get("dzwj_id"));//如果有正式件.wps文件,将dzwjInfos[0].iDzwjbm赋值给dzwjbm，执行update操作
							addorupdate = "update";
						} else {
							isDzwjbm = true;
							addorupdate = "insert";
						}
						noMarkFileName = "正式件.wps";
						flowlist_name="正式件.wps";
						//isDzwjbm = true;
						//addorupdate = "insert";
					}
				} else {
					if (saveType != null && saveType.equals("SaveNoMark")) {
						//fileName = noMarkFileName;
					}
					addorupdate = "insert";
				}
				if ((saveType != null) && (saveType.equals("SaveOFD"))) {
					dzwjInfo.put("dzwjlx", "ofd");
					dzwjInfo.put("dzwjm", "正式件(OFD).ofd");
					dzwjInfo.put("dzwjms", "正式件(OFD).ofd");
					//如果有OFD文件，则后续操作覆盖之前文件，保留最后一次生成的版式文件
					/* if (dzwjInfos != null && dzwjInfos.size() != 0) {
						addorupdate = "insert";
						dzwjInfo.put("dzwjsx", "正式件");
						String lastPDF = String.valueOf(dzwjInfos.get(0).get("dzwjm"));
						String lastNum = lastPDF.substring(lastPDF
								.lastIndexOf(')') + 1);
						lastNum = lastNum.substring(0,
								lastNum.lastIndexOf('.'));
						int num = 1;
						if (!"".equals(lastNum)) {
							num = (Integer.valueOf(lastNum) + 1);
						}
						flowlist_name="正式件(OFD)" + num + ".ofd";
						dzwjInfo.put("dzwjm", "正式件(OFD)" + num + ".ofd");
						dzwjInfo.put("dzwjms", "正式件(OFD)" + num+ ".ofd");
					}else{
						flowlist_name="正式件(OFD)1.ofd";
						dzwjInfo.put("dzwjm", "正式件(OFD)1.ofd");
						dzwjInfo.put("dzwjms", "正式件(OFD)1.ofd");						
					} */
					flowlist_name="正式件(OFD).ofd";
				}else if((saveType != null) && (saveType.equals("SavePDF"))){
					dzwjInfo.put("dzwjlx", "pdf");
					dzwjInfo.put("dzwjm", "正式件(PDF).pdf");
					dzwjInfo.put("dzwjms", "正式件(PDF).pdf");
					dzwjInfo.put("dzwjsx", "正式件");
					addorupdate = "insert";
					List<Map> dzwjpdfs = dzwjService.getDzwjInfos(
							"GW_FZXX", wjbm, "正式件(PDF)%");
					Debug.debugMessage(
							1,
							dzwjInfos != null ? String.valueOf(dzwjpdfs
									.get(0).get("dzwjm"))
									: "dzwjpdfs no data------");
					if (dzwjpdfs != null && dzwjpdfs.size() != 0) {
						String lastPDF = String.valueOf(dzwjpdfs.get(0).get("dzwjm"));
						String lastNum = lastPDF.substring(lastPDF
								.lastIndexOf(')') + 1);
						lastNum = lastNum.substring(0,
								lastNum.lastIndexOf('.'));
						int num = 1;
						if (!"".equals(lastNum)) {
							num = (Integer.valueOf(lastNum) + 1);
						}
						dzwjInfo.put("dzwjm", "正式件(PDF)" + num + ".pdf");
						dzwjInfo.put("dzwjms", "正式件(PDF)" + num+ ".pdf");
					}else{
						dzwjInfo.put("dzwjm", "正式件(PDF)1.pdf");
						dzwjInfo.put("dzwjms", "正式件(PDF)1.pdf");						
					}					
				} else {
					dzwjInfo.put("dzwjlx", "wps");
					dzwjInfo.put("dzwjm", fileName);
					dzwjInfo.put("dzwjms", fileName);
					//dzwjInfo.put("dzwjms", dzwjm);
				}
				dzwjInfo.put("dzwj_size", dzwj_size);
				dzwjInfo.put("file_catalog", H_fileAddress);
				dzwjInfo.put("file_name", H_fileName);
				if (saveType.equals("SaveET")) {
					isDzwjbm = false;
					addorupdate = "update";
					dzwjInfo.put("dzwjsx", "附件");
					if (sType == ".et") {
						dzwjInfo.put("dzwjlx", "et");
					} else {
						dzwjInfo.put("dzwjlx", "excel");
					}
				}
				if (isDzwjbm) {
					if (fileName.equals("审批件.wps")) {
						dzwjInfo.put("dzwjsx", "审批件");
					} else if ((fileName.indexOf("正式件") == 0)
							|| (fileName.equals("正式件(OFD).ofd"))) {
						dzwjInfo.put("dzwjsx", "正式件");
					} else {
						dzwjInfo.put("dzwjsx", "附件(脱痕)");
					}
				}

				Debug.debugMessage(1, "wjbm：" + wjbm);
				Debug.debugMessage(1, "isOK：" + isOK);
				Debug.debugMessage(1, "addorupdate：" + addorupdate);

				if (addorupdate.equals("insert")) {
					isOK = true;
				} else {
					Map m2 = new HashMap();
					m2.put("dzwj_id", dzwjbm);
					m2.put("dzwj_size", dzwj_size);
					m2.put("file_catalog", H_fileAddress);
					m2.put("file_name", H_fileName);
					Debug.debugMessage(1, "新文件名：" + H_fileName);
					try {
						Map oldDzwj=dzwjService.getDzwjById(dzwjbm);
						String oldFileAddress=String.valueOf(oldDzwj.get("file_catalog"));
						String oldFileName=String.valueOf(oldDzwj.get("file_name"));
						
						dzwjService.updateDzwj2(m2);
						//更新文件内容成功时，应删除源文件
			        	Debug.debugMessage(1, "删除文件存放目录:"+oldFileAddress);
			        	Debug.debugMessage(1, "删除文件名:"+oldFileName);
						File outF1 = new File(oldFileAddress+oldFileName);
						outF1.delete();
						isOK = false;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				dzwjInfo.put("ydzt", "0");
				dzwjInfo.put("llr", userName);

				if (isOK) {

					try {
						FlowListService flowListService = new FlowListService();
						dzwjInfo.put("zt", 0);
						dzwjInfo.put("con_table", "GW_FZXX");
						dzwjInfo.put("con_table_id", wjbm);
						int dzwjid=dzwjService.saveDzwj(dzwjInfo);
						DataBus.bus.put(user_account_name, dzwjid);
						Map flowlist = new HashMap();
						 if(wjbm!=null&&!wjbm.startsWith("-")){							
							flowlist.put("wjbm", wjbm);
						}
						Debug.debugMessage(1, "userName="+userName);
						flowlist.put("name", userName);
						if(!"NoTrack".equals(track)){
							if ("update".equals(addorupdate)) {
								flowlist.put("action",
										" 修改 "
												+ flowlist_name);
							} else {
								flowlist.put("action",
										" 上传 "
												+ flowlist_name);
							}
							int flowlistid=flowListService.insertFlowlist(flowlist);
							//DataBus.bus.put(user_account_name+"flowlistid", flowlistid);
						} 
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} catch (Exception e) {
				System.out.println("发生错误: " + e.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
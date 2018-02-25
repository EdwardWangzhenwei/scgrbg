<%@page import="cn.com.huadi.aos.hdoa.buinessObject.DzwjListInfo"%>
<%@page import="cn.com.huadi.aos.hdoa.buinessObject.DzwjInfo"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.regex.*"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.apache.commons.fileupload.*"%>
<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%
session = request.getSession();
Map user = (Map)session.getAttribute("USERINFO");
String wjbm = request.getParameter("wjbm");
/*
 * req页面请求
 * uploadPath上传后文件存放路径
 * tempPath大文件上传时的临时路径
 * MaxSize最大可以上传文件的大小 XX
 * v_errorType 不允许上传的文件类型 ".exe", ".com", ".cgi", ".asp"
 * v_realType  只允许上传的文件类型 .ceb
 */
String[] v_errorType = {};
String v_realType = "";
String returnValue = "";
//String tempPath = "c:\\temp";
String uploadPath = String.valueOf(RegServiceServlet.SYSCON.get("uploadPath"));
int MaxSize = 15;System.out.println("当前上传文件路径："+uploadPath);
Map<String, DzwjInfo> map = new HashMap<String, DzwjInfo>();
DzwjListInfo listInfo = new DzwjListInfo();
List<DzwjInfo> list = new ArrayList<DzwjInfo>();
try{
	DiskFileItemFactory factory = new DiskFileItemFactory();
	
	//允许设置内存中存储数据的门限，单位：字节
	//factory.setSizeThreshold(4096);
	
	//如果文件大小大于SizeThreshold，则保存到临时目录
	//factory.setRepository(new File(tempPath));
	ServletFileUpload upload = new ServletFileUpload(factory);
	upload.setHeaderEncoding("UTF-8");
	//最大上传文件，单位：字节
	upload.setSizeMax(MaxSize * 1024 * 1024);
	try {
		List fileItems = upload.parseRequest(request);
		Iterator iter = fileItems.iterator();
		
		// 正则匹配，过滤路径取文件名
		//String regExp = ".+\\\\(.+)$";			
		String regExp = "(.+)$";			
		// 过滤掉的文件类型
		String[] errorType = v_errorType;

		//Pattern p = Pattern.compile(regExp);
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField()) {
				String name = item.getName();
				name = name.substring(name.lastIndexOf("\\")+1, name.length());
				//System.out.println(name);
				long size = item.getSize();
				if ((name == null || name.equals("")) && size == 0)
					continue;
				//Matcher m = p.matcher(name);
				boolean result = true;
				if (result) {
					for (int temp = 0; temp < errorType.length; temp++) {
						if (name.toUpperCase().endsWith(errorType[temp].toUpperCase())) {
							returnValue = name + ": 此类型文件不允许上传";
							throw new IOException(name + ": 此类型文件不允许上传");
						}
					}
					
					if (!name.toUpperCase().endsWith(v_realType.toUpperCase())) {
						returnValue = ": 只允许上传"+v_realType.substring(1,v_realType.length()).toUpperCase()+"文件";
						throw new IOException("只允许上传"+v_realType.substring(1,v_realType.length()).toUpperCase()+"文件");
					}
					
					try {
						String localFile = name; //文件名
						
						/*
						 * 如果上传后修改上传文件的名称
						 */
						int index = localFile.lastIndexOf(".");//取文件扩展名用
						String fileExt = localFile.substring(index);
						String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"-"+String.valueOf(Math.random() * 10000000).substring(0,6)+fileExt;   //因为分发时一次可以插入多条记录,这样加一个随机数来区分
						item.write(new File(uploadPath + fileName));
						//System.out.println(item.getFieldName());
						
						String key = item.getFieldName();
						DzwjInfo file = new DzwjInfo();
						file.con_table_id = Integer.parseInt(wjbm);
						file.dzwjm = name;
						file.dzwjms = name;
						file.dzwjlx = file.dzwjm.substring(file.dzwjm.lastIndexOf("."));
						file.file_name = fileName;
						file.dzwj_size = String.valueOf(new File(uploadPath + fileName).length());
						map.put(key, file);
					} catch (Exception e) {
						e.printStackTrace();
						returnValue = e.toString();
					}
				} else {
					returnValue = "上传失败,请联系系统管理员";
					throw new IOException("fail to upload");
				}
			}
		}
		
		String skey = "file";
		String wjlx = null;
		Iterator iter1 = fileItems.iterator();
		while(iter1.hasNext()){
			FileItem item = (FileItem)iter1.next();
			//System.out.println(item.getFieldName());
			if (item.isFormField()) {
				//file1、mj1
				if("wjlx".equals(item.getFieldName())){
					wjlx = item.getString("UTF-8");
				}
				DzwjInfo file = map.get(skey+item.getFieldName().substring(item.getFieldName().length()-1));
				if(file != null){
					file.mj = item.getString();
				} 
			
			}
		}
		for(String key : map.keySet()){
			list.add(map.get(key));
		}
		listInfo.list = list;
		listInfo.con_table_id = Integer.parseInt(wjbm);
		listInfo.dzwjsx = wjlx;
		listInfo.file_catalog = uploadPath;
		listInfo.llr = (String)user.get("user_name");
		listInfo.llsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		listInfo.ydzt = "0";
		listInfo.zt = "0";
		listInfo.ssdwid = user.get("unit_id").toString();
		listInfo.con_table = "GW_FZXX";
		DzwjService ds = new DzwjService();
		if(!ds.addAll(listInfo)){
			returnValue = "插入数据库失败,请联系系统管理员";
			throw new Exception("fail to insert");
		}
		
	} catch (FileUploadException e) {
		e.printStackTrace();
		if(e.toString().indexOf("exceeds the configured maximum") > 1){
			returnValue = "上传文件不能超过"+MaxSize+"MB";
		}
	}
	
}catch(Exception e){
	e.printStackTrace();
	returnValue = "出错！";
}
if(!"".equals(returnValue)){
%>
<script language="javascript">
alert("<%=returnValue%>");
</script>
<%	
} else {
%>
<script>
//alert("上传成功！");
top.opener.afterFjadd();
window.close();
</script>
<%	
}
%>

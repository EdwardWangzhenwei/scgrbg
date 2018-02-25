<%@page import="java.util.Map"%>
<%@page import="cn.com.huadi.aos.hdoa.systemManagement.wjxs.service.WJXSService"%>
<%@page import="cn.com.huadi.aos.hdoa.wjxs.service.WjxsService"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Debug"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.Chinese"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="javax.servlet.*"%>
<%@ page import="javax.servlet.http.*"%>
<%@page import="java.net.URLDecoder"%>
<%
String fwmb_id="";
byte[] outbyte = null;
if(request.getParameter("fwmb_id") != null){
	fwmb_id = URLDecoder.decode(request.getParameter("fwmb_id"),"UTF-8");
}
try{
  if(!fwmb_id.equals("")){
	 WJXSService wjxsService=new WJXSService();
	 Map mb=wjxsService.getMbById(fwmb_id);
	 
	if(mb!=null&&mb.get("mbnr")!=null){//文件存在硬盘上
		String filePath = String.valueOf(mb.get("mbnr"));
		Debug.debugMessage(1, "filePath:"+filePath);
		File file = new File(filePath);
		Debug.debugMessage(1, "file.exists():"+file.exists());
		if(!file.exists()){
			throw new IOException("您要找的文件已经被删除！");
		}
		ServletOutputStream os = null;
		FileInputStream in = null; 	   
	    String V_FileName = new String(String.valueOf(mb.get("mbmc")).getBytes(),"ISO8859-1");
	    response.setContentType("application/octet-stream");  //"application/octet-stream" ;
	    response.addHeader("content-disposition","attachment;filename=\"" + V_FileName + "\"");

		try{
			os = response.getOutputStream();
			in = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int bytes = -1;
			while ((bytes = in.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytes);
			}
			
			os.flush();
			
		}catch(Exception e){
			throw new IOException("文件下载失败 \n erorr in downloadFileFromSystem()!");
		}finally{
				if(os!=null){
						try{
							os.close();
						}catch(Exception ex){						
						}
					}
					if(in!=null){
						try{
							in.close();
						}catch(Exception ex){						
						}
					}
		}
	}else{//文件存在数据库中
	}
  }
}catch(Exception e){ //错误处理
	e.printStackTrace();
PrintWriter toClient = response.getWriter(); //得到向客户端输出文本的对象
response.setContentType("text/html;charset=gb2312");
toClient.write("无法打开文件!");
    out.clear();  
    out = pageContext.pushBody();   
}

%>

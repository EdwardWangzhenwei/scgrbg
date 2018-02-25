<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="cn.com.huadi.aos.hdoa.common.servlet.RegServiceServlet"%>
<%

response.setHeader("Cache-Control","no-store");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",0);
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path + "/";

String urlr = (String) RegServiceServlet.SYSCON.get("portalUrl");

if (session.getAttribute("USERINFO")==null){
%>
	<script type="text/javascript">  
		alert("登录信息过期,请重新登录!");
		if (opener==null){
			if(window.dialogArguments){
				  //window.dialogArguments.top.location='<%=basePath%>canvas?formid=login';
				  //跳转到门户登录页面
				  window.dialogArguments.top.location='<%=urlr%>';
				  window.close();
			}
			else{
			
			//top.location='/Web/index.jsp';
			   //top.location='<%=basePath%>canvas?formid=login';
			   //跳转到门户登录页面
			   top.location='<%=urlr%>';   
			}
			
		}else{
			//opener.top.location='/Web/index.jsp';
		  if(opener.top.top!=null){
			  //opener.top.top.location='<%=basePath%>canvas?formid=login';
			  //跳转到门户登录页面
			  opener.top.top.location='<%=urlr%>';
		  }
		  else{
			//opener.top.location='<%=basePath%>canvas?formid=login';
			//跳转到门户登录页面
			opener.top.location='<%=urlr%>';
			}
			window.open('','_top'); 
	        window.close();
		}
	</script>
	
<%	
	return;
}
%>

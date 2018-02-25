<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.HashMap"%>
<%@page import="com.aisino.platform.veng.servlet.SimulateLogin"%>
<%@page import="com.aisino.platform.util.SessUtil"%>
<%@page import="java.util.List"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.DESUtil"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>督察督办</title>
<link rel="StyleSheet" href="css/css.css" type="text/css" />

<link href="css/menu.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%
String username=request.getParameter("user_account_name");	
String password=request.getParameter("user_password");
String url=request.getParameter("url");
url=url.replace("|^|", "&");
if(username!=null){
	//国产环境中创中间件发布时需要用UTF-8编码
	//username=new String(username.getBytes("ISO-8859-1"),"UTF-8");
	username=new String(request.getParameter("user_account_name").getBytes("ISO-8859-1"),"GBK");
	System.out.println("-------------------------------------------------------");
	 System.out.println("1."+request.getParameter("user_account_name"));
		System.out.println("2."+new String(request.getParameter("user_account_name").getBytes("ISO-8859-1"),"UTF-8"));
		System.out.println("3."+new String(request.getParameter("user_account_name").getBytes("ISO-8859-1"),"GBK"));
		System.out.println("4."+new String(request.getParameter("user_account_name").getBytes("UTF-8"),"GBK"));
		System.out.println("5."+new String(request.getParameter("user_account_name").getBytes("UTF-8"),"ISO-8859-1"));
		System.out.println("6."+new String(request.getParameter("user_account_name").getBytes("GBK"),"UTF-8"));
		System.out.println("7."+new String(request.getParameter("user_account_name").getBytes("GBK"),"ISO-8859-1"));
		System.out.println("-------------------------------------------------------");
		
	password=DESUtil.decrypt(password);
	//username=new String(username.getBytes("ISO-8859-1"),"GBK");
	System.out.println("用户名："+username+"\t密码："+password);  
     if ( !username.equals(SessUtil.getSessionValue("user_name"))) { 
           SimulateLogin sl=new SimulateLogin("login",url,"login"); 
           Map<String,String> m=new HashMap<String,String>();
           m.put("username",username);
           m.put("userpwd",password); 
           sl.loginToRedirect(request,response,m); 
    }
}else{
%>	
<script type="text/javascript">
<!--
window.close();
//-->
</script>
<%}
%>
</body>
</html>

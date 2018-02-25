<%@page import="cn.com.huadi.aos.hdoa.common.service.HuadiLogService"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%

	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	//记录登出日志
	String ipAddress = request.getRemoteAddr();


	Map<String ,Object> userInfo = (Map<String ,Object>) session.getAttribute("USERINFO");
	Map<String ,Object> param= new HashMap();
	if(userInfo!=null){
	param.put("user_account_name", userInfo.get("user_account_name"));
	param.put("user_id", userInfo.get("user_id"));	
	param.put("unit_name", userInfo.get("unit_name"));
	param.put("unit_id", userInfo.get("unit_id"));
	param.put("ip",ipAddress);
	param.put("operate_name", "退出");
	param.put("operate_object",  userInfo.get("user_account_name")+"退出");
	param.put("subsystemname", "公文处理系统");	
	param.put("operate_description", userInfo.get("user_account_name")+"成功退出公文处理系统-人员ID为"+userInfo.get("user_id"));
	param.put("type", "公文处理系统");
	
	new HuadiLogService().privsysAndBusinessLoginLog(param);

	session.invalidate();	
	}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	  <base href="<%=basePath%>">
	   <base target="_top">
	   <title></title>
	   <script language="JavaScript">
         window.close();
        </script>
	</head>
	<body>
		<br>
		<a href="login.faces">登录</a>
	</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%@ page import="java.util.Map"%>
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.GrwdService"%>
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.BmwdService"%>
<%@ page import="cn.com.huadi.aos.hdoa.bjgl.service.GrdbService"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<%
	//消息提醒部分实现：从后台获取msg数据
	GrwdService grwdService=new GrwdService();
	GrdbService grdbService=new GrdbService();
	/* ActionContext ac = ActionContext.getContext();
	HttpServletRequest request =(HttpServletRequest)ac.get(ServletActionContext.HTTP_REQUEST); */
	/* HttpServletRequest request = ServletActionContext.getRequest();
	String id=request.getParameter("id"); */
	String mess_id=request.getParameter("mess_id").toString();
	int num = grwdService.queryStateById(Integer.parseInt(mess_id));
	
	HttpSession session1 = request.getSession();
	Map userInfo = (Map) session1.getAttribute("USERINFO");
	if(num != 1){
		//说明不是第一次打开这个链接不需要更新
		//arg0.setReturn("failure");
	
		// 是第一次打开这个链接需要更新update view根据mess_id;此时需要更新public_todolist中的view信息,根据main_table及primary_key_value确定同一条记录。
		grwdService.updateStateById(Integer.parseInt(mess_id),userInfo);
		
		// 针对public_todolit表的更新操作:1.查询出public_message里面的main_table和primary_key_value信息；
		Map arg1=grwdService.getKeyById(Integer.parseInt(mess_id));
		// 2.确认public——todolist里面是否有相同的key并且view为空；
	/* 	map.get("main_table");
		map.get("primary_key_value"); */
		arg1.put("userInfo", userInfo);
		int numKey=grdbService.queryStateByKey(arg1);
		if(numKey != 1){
			
			// 3.存在，则做更新
			grdbService.updateStateByKey(arg1);
		}


	}


%>
<body>

</body>
</html>
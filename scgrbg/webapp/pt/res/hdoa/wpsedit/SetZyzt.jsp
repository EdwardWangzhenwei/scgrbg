<?xml version="1.0" encoding="GBK"?>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/xml;charset=GBK"%>
<%
	response.setHeader("Cache-Control", "no-store");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", 0);

	int iDzwjId = -1;
	int iWjbm = -1;
	String name1 = "";
	String showButton = "";
	Map user = (Map) session.getAttribute("USERINFO");
	if(request.getParameter("wjbm") != null && !"".equals(request.getParameter("wjbm"))){
		iWjbm = Integer.parseInt(request.getParameter("wjbm"));
	}
	if(request.getParameter("dzwj_id") != null && !"".equals(request.getParameter("dzwj_id"))){
		iDzwjId = Integer.parseInt(request.getParameter("dzwj_id"));
		if(iDzwjId<1){
			DzwjService dzwjService=new DzwjService();
			List<Map> dzwjInfos=dzwjService.queryDzwjByWjbm(String.valueOf(iWjbm));
			if(dzwjInfos!=null&&dzwjInfos.size()>0){
				iDzwjId = Integer.parseInt(String.valueOf(dzwjInfos.get(0).get("dzwj_id")));
			}
		}
	}

	try {
		DzwjService dzwjService = new DzwjService();
		Map m = new HashMap();
		m.put("dzwj_id", iDzwjId);
		m.put("con_table_id", iWjbm);
		m.put("ydr", user.get("user_name"));
		m.put("ydzt", 1);
		m.put("ydr_id", user.get("user_id"));
		m.put("ksydsj", "sysdate");
		//dzwjService.updateDzwj(m);
		dzwjService.setDzwjIsWork(m);
		out.println("<list>");
		out.println("<error>noerror</error>");
		out.println("</list>");
	} catch (Exception e) {
		out.println("<list>");
		out.println("<error>haveerror</error>");
		out.println("</list>");
	}
%>
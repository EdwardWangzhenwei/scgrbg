<?xml version="1.0" encoding="GBK"?>
<%@page import="java.util.List"%>
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
	DzwjService dzwjService = new DzwjService();
	if (request.getParameter("wjbm") != null
			&& !"".equals(request.getParameter("wjbm"))) {
		iWjbm = Integer.parseInt(request.getParameter("wjbm"));
	}
	if (request.getParameter("dzwj_id") != null
			&& !"".equals(request.getParameter("dzwj_id"))) {
		iDzwjId = Integer.parseInt(request.getParameter("dzwj_id"));
		if (iDzwjId < 1) {
			List<Map> dzwjInfos = dzwjService.queryDzwjByWjbm(String
					.valueOf(iWjbm));
			if (dzwjInfos != null && dzwjInfos.size() > 0) {
				iDzwjId = Integer.parseInt(String.valueOf(dzwjInfos
						.get(0).get("dzwj_id")));
			}
		}
	}

	Map dzwj = dzwjService.getDzwjById(String.valueOf(iDzwjId));
	if (dzwj != null) {
		String userid = String.valueOf(dzwj.get("ydr_id"));
		String userName = (String) dzwj.get("ydr");

		if (userid!=null&&!"null".equals(userid)&&!String.valueOf(user.get("user_id")).equals(userid)) {
			out.println("<list>");
			out.println("<error>noerror</error>");
			out.println("<UserName>" + userName + "</UserName>");
			out.println("<DzwjId>" + iDzwjId + "</DzwjId>");
			out.println("</list>");
		} else {
			out.println("<list>");
			out.println("<error>haveusererror</error>");
			out.println("<UserName></UserName>");
			out.println("</list>");
		}
	} else {
		out.println("<list>");
		out.println("<error>havedataerror</error>");
		out.println("</list>");
	}
%>
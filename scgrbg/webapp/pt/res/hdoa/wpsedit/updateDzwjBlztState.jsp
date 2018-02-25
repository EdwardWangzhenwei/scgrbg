<?xml version="1.0" encoding="GBK"?>
<%@page import="cn.com.huadi.aos.hdoa.dzwj.service.DzwjService"%>
<%@ page contentType="text/xml;charset=GBK"%>
<%
	DzwjService dzwjService = new DzwjService();
	String dzwj_id = request.getParameter("dzwj_id");
System.out.println("---------------------dzwj_id"+dzwj_id);
	if (dzwj_id != null && !dzwj_id.equals("")) {
		try {
			dzwjService.updateDzwjNoWork(dzwj_id);

			out.println("<list>");
			out.println("<error>noerror</error>");
			out.println("</list>");
		} catch (Exception e) {
			out.println("<list>");
			out.print("<error>haveerror</error>");
			out.println("</list>");
		}
	}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.aisino.platform.util.SessUtil"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page
	import="cn.com.huadi.aos.hdoa.richangguanli.agenda.service.AgendaService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<%
	String path = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + request.getContextPath();

	String month = request.getParameter("month");
	String user_id = request.getParameter("userid");

	AgendaService as = new AgendaService();
	List<Map> list = as.QueryAgenda(month, user_id);
%>
<script type="text/javascript">
	function  openwin(rc_id){
		var iWidth=800; //弹出窗口的宽度;
		var iHeight=600; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open("<%=path%>/canvas?formid=detail&flag=1&rc_id="+rc_id,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
		
	}

</script>
<body background="">
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<th width="200PX" align="center"><font size="5">时间</font></th>
			<th><font size="5">活动</font></th>
		</tr>
		<%
			int k = 10;
			if (list != null) {
				int size = list.size();
				System.out.print(list.size());
				if (size >= 10) {
					for (Map map : list) {
		%>
		<tr>
			<td align="center"><%=map.get("sj")%></td>
			<td><span style="color:blue;" onclick="openwin(<%=map.get("rc_id")%>)"><%=map.get("zt")%></span></td>
		</tr>
		<%
			}
				} else {
					for (Map map : list) {
						k--;
		%>
		<tr>
			<td align="center"><%=map.get("sj")%></td>
			<td><span style="color: blue;cursor:pointer;"  onclick="openwin(<%=map.get("rc_id")%>)"><%=map.get("zt")%></span></td>
		</tr>
		<%
			}
					for (int i = 1; i <= k; i++) {
		%>
		<tr>
			<td>&nbsp</td>
			<td>&nbsp</td>
		</tr>
		<%
			}
				}
			} else {
				for (int i = 1; i <= k; i++) {
		%>
		<tr>
			<td>&nbsp</td>
			<td>&nbsp</td>
		</tr>

		<%
			}
			}
		%>
	</table>
</body>
</html>
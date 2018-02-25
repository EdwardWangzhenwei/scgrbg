<%@page import="cn.com.huadi.aos.hdoa.common.service.FlowListService"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.Date,java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
String wjbm=request.getParameter("wjbm");
FlowListService flowListService=new FlowListService();
List<Map> list=null;
list=flowListService.queryFlowListByWjbm(wjbm);
StringBuffer sbuffer=new StringBuffer("");
  if(list != null && list.size()>0){
  	for(Map m:list){
  		sbuffer.append("--->");
    	sbuffer.append(m.get("name"));
    	sbuffer.append(" 在 ");
    	sbuffer.append(m.get("time"));
    	sbuffer.append("  "+m.get("action"));
    	sbuffer.append("<br>");
  	}
  }	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>页签</title>
<link href="../../res/css/newcss.css" rel="stylesheet" type="text/css">
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="8">
  <tr> 
    <td valign="top"><TABLE width="100%" height="100%" border=0 cellPadding=0 cellSpacing=0  class="table-contbg">
        <TBODY>
          <TR> 
            <TD> 
            </TD>
          </TR>
          <TR> 
            <TD height="100%">
<TABLE width="100%" height="100%" 
            border=0 cellPadding=0 cellSpacing=5  class="tdiframebk_lrb">
                <TBODY>
                  <TR> 
                    <TD height="100%" valign="top"> 
                      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="TableLine-tlrb">
                        <tr> 
                          <td width="85%" 
                          style="line-height:150%"
                          height="100%" valign="top"><%= sbuffer.toString()%> </td>
                        </tr>
                      </table></TD>
                  </TR>
                </TBODY>
              </TABLE></TD>
          </TR>
        </TBODY>
      </TABLE></td>
  </tr>
</table>
</body>
</html>


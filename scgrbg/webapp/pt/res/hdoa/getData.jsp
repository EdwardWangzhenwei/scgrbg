<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.GrwdService"%>
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.BmwdService"%>
<%@ page import="cn.com.huadi.aos.hdoa.bjgl.service.GrdbService"%>
<%@ page import="com.aisino.platform.veng.json.dauglas.JSONObject"%>
<%

	response.setHeader("Cache-Control","no-store");
	response.setHeader("Pragma","no-cache");
	response.setDateHeader("Expires",0);

	Map userinfo = (Map) session.getAttribute("USERINFO");//(Map)SessUtil.getSessionValue("USERINFO");
	String username = null;
	String unitname = null;
	String uperunitname = null;
	String user_account_name = null;
	String deptname=null;
	int userid = 0;
	int deptid = 0;
	int unitid = 0;
	//System.out.println("************"+userinfo);
	if (userinfo != null) {
		username = (String) userinfo.get("user_name");
		unitname = (String) userinfo.get("unit_name_short");
		deptname =(String) userinfo.get("dept_name_short");
		
		uperunitname = (String) userinfo.get("uper_unit_name")==null?"":(String) userinfo.get("uper_unit_name");
		userid = Integer.parseInt(String.valueOf(userinfo
				.get("user_id")));
		deptid = Integer.parseInt(String.valueOf(userinfo
				.get("dept_id")));
		unitid = Integer.parseInt(String.valueOf(userinfo
				.get("unit_id")));
		user_account_name = (String) userinfo.get("user_account_name");
	}
	
	// 给消息提醒使用的用户密级
		Integer user_secret = (userinfo.get("user_secret") == null ? 10
				: Integer.valueOf(userinfo.get("user_secret").toString()) );
	//消息提醒部分实现：从后台获取msg数据
	/* JSONObject warnMess = new JSONObject(); */
	JSONObject json=new JSONObject();
	GrwdService grwdService=new GrwdService();
	
	 List<Map> warnMess=grwdService.queryListWarn(unitid,deptid,userid,user_secret);
	 if(warnMess != null){
		 json.put("warn",warnMess.size());
		 json.put("warnMess", warnMess);
	 }
	 
	 out.println(json); 
	/* String warn="";
	if(warnMess!=null){
		 warn=warnMess.size()+""; 
		
		 for(int i=0;i<warnMess.size();i++){
			Object subsystem=warnMess.get(i).get("subsystem");
			Object title=warnMess.get(i).get("title");
			Object send_unit=warnMess.get(i).get("send_unit");
			Object sender=warnMess.get(i).get("sender");
			Object send_time=warnMess.get(i).get("send_time").toString().substring(0, 16);
			/* warn+="〔"+subsystem+"〕"+title+"</br>"+send_unit+"+"+sender+"（"+send_time+"）"+"</br>"; 
		}  */

		
		/* 	 if (grwdService.queryListWarn(unitid,deptid,userid,user_secret) == null){
			      if(gwListInfos!=null){
			          int len = gwListInfos.length;
			          text.append(""+len+"").append("!#");
			          for(int ii=0;ii<gwListInfos.length;ii++){
			              GwListInfo gw = gwListInfos[ii];
			              String wz = groupList.getName(gw.iWjzlbz);
			              text.append(gw.sNSender).append("ÔÚ").append(new SimpleDateFormat("yy.MM.dd HH:mm").format(gw.dNSendDate))
			              .append("´«µÝÁË").append(wz).append("ÎÄ¼þ").append("\"").append(gw.sWjbt).append("\""+"!#");
			          }
			      }
			      else{
			          text = new StringBuffer("haveerror");
			      }
			      out.println("<list>");
			      out.println("<error>"+text.toString()+"</error>");
			      out.println("</list>");
			    }else{
			      out.println("<list>");
			      out.print("<error>haveerror</error>");
			      out.println("</list>");
			    }
 */
%>
			
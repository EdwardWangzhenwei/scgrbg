<%@page import="java.net.URLEncoder"%>
<%@page import="com.aisino.platform.db.DbSvr"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.aisino.platform.veng.servlet.SimulateLogin"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.StringUtils"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.DESUtil"%>
<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html;charset=GBK"%>
<%
    String doctype="";
    String lxtm="";
    String user_account_name="";
    String user_password="";
    String url=request.getParameter("url");
    url=url.replace("|^|", "&");
    url=url+"&ProID="+request.getParameter("ProID");
    url=url+"&DocID="+request.getParameter("DocID");
    System.out.println("�����ַ���:"+request.getQueryString());
    System.out.println("url:"+url);
    if(request.getParameter("doctype")!=null&&!"".equals(request.getParameter("doctype"))){
    	doctype=URLDecoder.decode(URLDecoder.decode(request.getParameter("doctype"),"utf-8"),"utf-8");
    	//���ܴ��䣬�����������
    	doctype=StringUtils.encrypt(doctype);
    	url=url+"&doctype="+doctype;
    }
    if(request.getParameter("lxtm")!=null&&!"".equals(request.getParameter("lxtm"))){
    	lxtm=URLDecoder.decode(URLDecoder.decode(request.getParameter("lxtm"),"utf-8"),"utf-8");
    	//���ܴ��䣬�����������
    	lxtm=StringUtils.encrypt(lxtm);
    	url=url+"&lxtm="+lxtm;
    }
    //System.out.println("����="+doctype+"----------������Ŀ="+lxtm+"----------δת����Ŀ="+request.getParameter("lxtm"));
    if(request.getParameter("UserID")!=null&&!"".equals(request.getParameter("UserID"))){
    	user_account_name=StringUtils.decrypt(request.getParameter("UserID"));
    }
    if(request.getParameter("IdCard")!=null&&!"".equals(request.getParameter("IdCard"))){
    	user_password=URLDecoder.decode(URLDecoder.decode(request.getParameter("IdCard"),"utf-8"),"utf-8");
    }
    //����������¼��֤ʱ����Ҫ����
    user_password=DESUtil.decrypt(user_password);
    if(!"".equals(user_account_name)&&!"".equals(user_password)){
    	System.out.println("����url��������"+url);
        SimulateLogin sl=new SimulateLogin("login",url,"login"); 
        Map<String,String> m=new HashMap<String,String>();
        m.put("username",user_account_name);
        m.put("userpwd",user_password); 
        sl.loginToRedirect(request,response,m);   	
    }else{
%>
        <script>
	       window.close();
		</script>
<%} %>
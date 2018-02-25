<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.aisino.platform.db.DbSvr"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.StringUtils"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="cn.com.huadi.aos.hdoa.common.util.StringUtil"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="cn.com.huadi.aos.hdoa.common.util.StringUtil"%>
<%@ page import=" com.aisino.platform.util.SessUtil"%>
<%@ page import=" cn.com.huadi.aos.hdoa.login.service.MenuService"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公文管理系统</title>
<link rel="StyleSheet" href="css/css.css" type="text/css" />

<link href="css/menu.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/menu.js"></script>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/subMenu.js"></script>
<!-- 以下是tab页签的引用资源 added by hbj 2016-07-07 -->
 <link rel="Stylesheet" href="context/themes/base/jquery-ui.css" type="text/css" />
<script src="scripts/jquery.js" type="text/javascript"></script>
<script src="scripts/jquery-ui.js" type="text/javascript"></script>
<script src="scripts/jquery.contextMenu.js" type="text/javascript"></script>
 <script src="scripts/jquery.cleverTabs.js" type="text/javascript"></script>

</head>
<%
	Map userinfo = (Map) session.getAttribute("USERINFO");
	String username = null;
	String unitname = null;
	String deptname = null;
	int userid = 0;
	String oldunitname = unitname;
	String oldusername = username;
	if (userinfo != null) {
		username = (String) userinfo.get("user_name");
		unitname = (String) userinfo.get("unit_name");
		deptname = (String) userinfo.get("dept_name");
		userid  = Integer.parseInt(String.valueOf(userinfo.get("user_id")));
		/* if (unitname != null) {
			unitname = StringUtil.subTextString(unitname, 30);
		} */

		if (deptname != null) {
			deptname = StringUtil.subTextString(deptname, 10);
		}
		if (username != null) {
			username = StringUtil.subTextString(username, 10);
		}
	}
	MenuService service = new MenuService();
	int defaultTopMenuid = 0;
	String defaultHomeurl = "../../../canvas?formid=home";
	String defaultHomename = "首页";
	if(request.getParameter("function_id")!=null){
		defaultTopMenuid = Integer.parseInt( String.valueOf(request.getParameter("function_id")));
	}
	if(request.getParameter("function_id")!=null){
		defaultTopMenuid = Integer.parseInt( String.valueOf(request.getParameter("function_id")));
	}
	if(request.getParameter("homeurl")!=null){
		if(!("").equals(request.getParameter("homeurl"))){
		   defaultHomeurl =  String.valueOf(request.getParameter("homeurl"));
		   if(defaultHomeurl.endsWith("asset_xxlist")){
			   defaultHomename = "资产信息管理";
		   }
		}
	}
///////////////////////////////////督查系统起草文件 start/////////////////////////////////////
String ProID="";
String DocID="";
String doctype="";
String lxtm="";
String wjxs_id="";
String wjxs="";

    if(request.getParameter("ProID")!=null&&!"".equals(request.getParameter("ProID"))){
    	ProID=request.getParameter("ProID");
    }
    if(request.getParameter("DocID")!=null&&!"".equals(request.getParameter("DocID"))){
    	DocID=request.getParameter("DocID");
    }
    if(request.getParameter("doctype")!=null&&!"".equals(request.getParameter("doctype"))){
    	doctype=StringUtils.decrypt(request.getParameter("doctype"));
    }
    if(request.getParameter("lxtm")!=null&&!"".equals(request.getParameter("lxtm"))){
    	lxtm=StringUtils.decrypt(request.getParameter("lxtm"));
    }
    System.out.println("类型="+doctype+"----------立项题目="+lxtm+"------------ProID="+ProID+"---------DocID="+DocID);
    //根据要起草的文件类型，查询指定的文件形式
    if (userinfo != null) {
    	String unit_id=String.valueOf(userinfo.get("unit_id"));
    	String sql="select * from gw_wjxs where wjlx='发文' and wjxs like '%"+doctype+"%' and ssdwid="+unit_id;
    	DbSvr dbSvr=DbSvr.getDbService("gwsys");
    	Map m=dbSvr.getOneRecorder(sql, null);
    	dbSvr.release();
    	if(m!=null){
    		wjxs_id=String.valueOf(m.get("wjxs_id"));
    		wjxs=String.valueOf(m.get("wjxs"));
    	}
    }
///////////////////////////////////督查系统起草文件 end///////////////////////////////////////
	//从门户首页待办列表点击进来后的页签地址
	String formid="",wjbm="",n_id="",wjlx="";
	if(request.getParameter("formid")!=null){
		formid = request.getParameter("formid");
	}	
	if(request.getParameter("wjbm")!=null){
		wjbm = request.getParameter("wjbm");
	}	
	if(request.getParameter("n_id")!=null){
		n_id = request.getParameter("n_id");
	}	
	if(request.getParameter("wjlx")!=null){
		wjlx =new String(request.getParameter("wjlx").getBytes("ISO-8859-1"),"UTF-8");
	}	
	//查询公文系统的顶级菜单
	List topList = service.queryTopMenu(userid, "010");
	if(topList!=null){
		if(topList.size()>0&&defaultTopMenuid==0){
			Map data = (Map)topList.get(0);
			defaultTopMenuid = Integer.parseInt(String.valueOf(data.get("function_id")));
			
		}
	}
	//System.out.println("top:"+defaultTopMenuid);
	String menuHtmlString = service.getMenuHtml(userid,defaultTopMenuid);
%>
<body>

	<table class="zw-box">
	   <tr>
	     <td>
	      <table class="zw-header">
		  <tr>
		  <!-- 
		   <td width="10%">
			
			</td>
			 -->
		    <td width="45%">
			 <div class="logo">
				<img src="img/logo.png" />
			 </div>
			</td>
		  
			<td width="55%" nowrap > 
			  <table>
			  <tr>
			   <td>
				<span class="m-top"> <!--20160903 lwf注释 deptname unit_name已经是各级组织机构拼接 -->
				<span>欢迎 </span>&nbsp;<span	title="<%=unitname%>"><%=unitname%></span>&nbsp;<%-- <span title="<%=deptname%>"><%=deptname%></span> --%>&nbsp;<span	title="<%=username%>"><%=username%></span> &nbsp;<span id="nowdate"></span></span>
			    </td>
			    </tr>
			    </table>
			</td>
			</tr>
          </table>
          </td>
          </tr>
          
            <tr>
				<td align="right"  bgcolor="#fd7400">
				<%
					if (topList != null) {
						for (int ii = 0; ii < topList.size(); ii++) {
							Map data = (Map) topList.get(ii);
							String functionName = (String) data.get("function_name");
							Object id = data.get("function_id");
							Object url = data.get("function_discription");
				%>
				<div class="header-menu">
					<div class="one">
						<a href="#" class="one-btn"
							onclick="chageMenu('<%=id%>','<%=url%>','<%=functionName%>')"><%=functionName%></a>
					</div>
				</div>
				<%
					}
					}
				%>
				
				</td>
			</tr> 
          
        
		<!--头部开始-->
		<!--头部结束-->
		<!--菜单开始-->
		<tr>
	     <td>
	      <table >
	       <tr>
	        <td valign="top" width="200px">
		 <div class="zw-menu">
			<div class="top"></div>
			<div class="box">
				<!--<h2>可高亮带缓冲的二级折叠菜单</h2>-->
				<ul class="menu" id="m1">
					<%=menuHtmlString %> 
					<li class="level1"><a href="ssologinOut.jsp">退出系统</a></li>
				</ul>
			</div>

		</div>
		<!--菜单结束-->
		<!--内容开始-->
		 </td>
		
		 <td valign="top">
		<div id="zw-content2" class="zw-content2" >
	    <div id="tabs" style="align:center;">
          <ul>
          </ul>
       </div>	
		</div>
		</td>
		</tr>
	   </table>
		</td>
		</tr>
		</table>
		
		<!--内容结束-->


	<SCRIPT>
		var d = new Date();
		var dd = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate()
				+ "日";
		document.getElementById("nowdate").innerHTML = dd;
		function chageMenu(id,url,functionName) {
			
			if(url=='null'){
				url = "";
			}
			window.location.href = "../../res/hdoa/index.jsp?function_id="
					+ id+"&homeurl="+url;
			
		   }    
		    var tabs;
	        var tmpCount = 0;
	        var inheight = 0;
	        var inwidth = 0;
	        $(function () {
	            tabs = $('#tabs').cleverTabs({
	            	//去掉右键菜单
	            	  setupContextMenu: false
	            });
	            $(window).bind('resize', function () {
	                tabs.resizePanelContainer();
	            });
	            //添加默认首页'../../../canvas?formid=daiban_list'
	            tabs.add({
	                url: '<%=defaultHomeurl%>',
	                label: '<%=defaultHomename%>'
	               
	            });
	            <%-- if('<%=formid%>'!=''&&'<%=wjbm%>'!=''&&'<%=n_id%>'!=''){
		            tabs.add({
		                url: '../../../canvas?formid=<%=formid%>&wjbm=<%=wjbm%>&n_id=<%=n_id%>',
		                label: '<%=wjlx%>办理'		               
		            });	            	
	            } --%>
	            if('<%=formid%>'!=''){
		            tabs.add({
		                url: '../../../canvas?formid=fw_add&wjxs_id=<%=wjxs_id%>&wjxs=<%=wjxs%>&proid=<%=ProID%>&docid=<%=DocID%>&doctype=<%=doctype%>&lxtm=<%=lxtm%>',
		                label: '起草<%=doctype%>'		               
		            });	            	
	            }
	          //如果点击<a herf链接  判断是否添加tab页
	            $("#m1 a").click(function(){  
	            	  var url = $(this).attr("href");
	            	  var name = $(this).text();
	            	 // if(tabs.element.find('>li').size() >5){
	            	//	  alert("只能打开6个页签!请先关闭其他页签");
	            	//	  return false;
	            	 // }
	                
	                 
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			
	            	      tabs.add({
	                      url: url,
	                      label: name
	                     });
	            	    return false;
	            	    }
	            	  }
	            	 
	            	});
	          
	        });
	    
	 
		function SetWinHeight(obj)
		{
			var win= obj;
		    var height;
		    var width;
		 	if (document.getElementById)
		 	{
			  if (win && !window.opera)
			  	{
					if (win.contentDocument && win.contentDocument.body.scrollHeight)
					{	
					
						height = win.contentDocument.body.scrollHeight;
						width = win.contentDocument.body.scrollWidth;
						if(height<document.body.scrollHeight-120){
					  	    	height = document.body.scrollHeight-120;
					  	 }
				  		inheight = height;
						inwidth = width;
				  	    win.height = height+"px";
				  	    win.parentNode.style.width = width+"px";
				    	win.parentNode.style.height = height+"px";
				    	//win.parentNode.parentNode.style.width = width+"px";
				    	//win.parentNode.parentNode.style.height = height+"px";
				    	//win.parentNode.parentNode.parentNode.style.width = width+"px";
				    	//win.parentNode.parentNode.parentNode.style.height = height+"px";
				    	  	  // win
					   // document.getElementById("tabs").style.height =height+"px";
					   // document.getElementById("zw-content2").style.height =height+"px";
					   // document.getElementById("tabs").style.width =width+"px";
					   // document.getElementById("zw-content2").style.width =width+"px";
					
					 }
				    	//win.width = win.contentDocument.body.offsetWidth;
				   	else if(win.Document && win.Document.body.scrollHeight)
				   	{	
				   	
				   	   height = win.Document.body.scrollHeight;
				      	width = win.Document.body.scrollWidth;
				        if(height<document.body.scrollHeight-120){
				  	    	height = document.body.scrollHeight-120;
				  	    }
				    	win.height = height+"px";
				    	inheight = height;
				    	inwidth = width;
				        height = height;
					  	width = width;
				    	win.parentNode.style.width = width+"px";
				    	win.parentNode.style.height = height+"px";
				    	//win.parentNode.parentNode.style.width = width+"px";
				    	//win.parentNode.parentNode.style.height = height+"px";
				    	//win.parentNode.parentNode.parentNode.style.width = width+"px";
				    //	win.parentNode.parentNode.parentNode.style.height = height+"px";
				    //	win.width = width+"px";
				   
					   
					  }
				
				     
				}
				
		 	}
		 	
		}
			</script>
	</SCRIPT>
</body>
</html>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.aisino.platform.view.basicWidget.Int"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import=" cn.com.huadi.aos.hdoa.login.service.MenuService"%>
<%@ page import=" com.aisino.platform.util.SessUtil"%>
<%@ page import="cn.com.huadi.aos.hdoa.common.util.StringUtil"%>
<%@ include file="sessionCheck.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一待办和消息提醒</title>
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
	Map userinfo = (Map) session.getAttribute("USERINFO");//(Map)SessUtil.getSessionValue("USERINFO");
	String username = null;
	String unitname = null;
	String uperunitname = null;
	String user_account_name = null;
	int userid = 0;
	//System.out.println("************"+userinfo);
	if (userinfo != null) {
		username = (String) userinfo.get("user_name");
		unitname = (String) userinfo.get("unit_name");
		uperunitname = (String) userinfo.get("uper_unit_name")==null?"":(String) userinfo.get("uper_unit_name");
		userid = Integer.parseInt(String.valueOf(userinfo
				.get("user_id")));
		user_account_name = (String) userinfo.get("user_account_name");
	}
	MenuService service = new MenuService();
	int defaultTopMenuid = 0;
	String defaultHomeurl = "../../../canvas?formid=home";
	String defaultHomename = "统一待办和消息提醒首页";
	if(request.getParameter("function_id")!=null){
		defaultTopMenuid = Integer.parseInt( String.valueOf(request.getParameter("function_id")));
	}
	if(request.getParameter("homeurl")!=null){
		if(!("").equals(request.getParameter("homeurl"))){
		   defaultHomeurl =  String.valueOf(request.getParameter("homeurl"));
		   
		}
	}
	/* String defaultHomeurl1 = "../../../canvas?formid=home1";
	String defaultHomename1 = "来宾登记列表";
	if(request.getParameter("function_id")!=null){
		defaultTopMenuid = Integer.parseInt( String.valueOf(request.getParameter("function_id")));
	}
	if(request.getParameter("homeurl")!=null){
		if(!("").equals(request.getParameter("homeurl"))){
		   defaultHomeurl1 =  String.valueOf(request.getParameter("homeurl"));
		   
		}
	} */
	List topList = service.queryTopMenu(userid, "104");
	 if(topList!=null){
		if(topList.size()>0&&defaultTopMenuid==0){
			Map data = (Map)topList.get(0);
			defaultTopMenuid = Integer.parseInt(String.valueOf(data.get("function_id")));
			
		}
	} 
	//System.out.println("top:"+defaultTopMenuid);
	//String menuHtmlString = service.getMenuHtml(userid,defaultTopMenuid);
	defaultTopMenuid = 105;
	String menuHtmlString = service.getMenuHtml(userid,105);
	String oldunitname = unitname;
	String olduperunitname = uperunitname;
	String oldusername = username;
	if(unitname!=null){
		unitname = StringUtil.subTextString(unitname, 30);
	}
	if(uperunitname!=null){
		uperunitname = StringUtil.subTextString(uperunitname, 20);
	}
	if(username!=null){
		username = StringUtil.subTextString(username, 10);
	}
%>
<body>

<table class="zw-box">
	   <tr>
	     <td>
	      <table class="zw-header">
		  <tr>
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
					<span>欢迎 </span>
					&nbsp;
					<span	title="<%=unitname%>"><%=unitname%></span>
					&nbsp;
					
					<span	title="<%=username%>"><%=username%></span> 
					&nbsp;
					<span id="nowdate"></span>
				</span>
			    </td>
			    </tr>
			    
			    </table>
			</td>
			</tr>
          </table>
          </td>
          </tr>
          
           <tr>
          			   <td align="right"  bgcolor="#ffd3c9">
				<div  class="header-menu">
					<div class="one">
					 <a href="#" class="one-btn">统一待办和消息提醒</a>
					 </div>
				</div>
				
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
						<%String home = (String)RegServiceServlet.SYSCON
							.get("home"); %>     
					<%--  <li class="level1"><a href="javascript:window.parent.location.href='<%=home%>';">退出系统</a></li> --%>

			<!-- 		<li class="level1"><a href="#"  target=ifrm>返回首页</a></li>
					<li class="level1"><a href="../../../canvas?formid=home"  target=ifrm>接待登记</a></li>
					<li class="level1"><a href="../../../canvas?formid=jddj_show"  target=ifrm>查看接待登记</a></li>
					<li class="level1"><a href="../../../canvas?formid=lbdj"  target=ifrm>来宾信息</a></li>
					<li class="level1"><a href="../../../canvas?formid=lbdj_show"  target=ifrm>查看来宾信息</a></li>
					<li class="level1"><a href="../../../canvas?formid=jdrw"  target=ifrm>接待任务</a></li>
					<li class="level1"><a href="../../../canvas?formid=jdrw_show"  target=ifrm>查看接待任务</a></li>
					
					<li class="level1"><a href="javascript:void(0)"  target=ifrm>点位管理</a>
						<ul class="level2" style="display:block;">
							<li class=""><a href="../../../canvas?formid=xzqy"  target=ifrm>行政区域管理</a></li>
							<li class=""><a href="../../../canvas?formid=dwgl_index"  target=ifrm>点位管理</a></li>
						</ul>
					</li>-->
					<li class="level1"><a href="javascript:window.parent.location.href='../../../canvas?formid=login'" >退出系统</a></li> 
				</ul>
			</div>

		</div>
		<!--菜单结束-->
		<!--内容开始-->
		 <!-- <div class="zw-content2">
			<iframe frameborder="0" src="../../../canvas?formid=home" marginheight="0"
				marginwidth="0" height="555px" width="100%" scrolling="yes" name="ifrm" id="ifrm"></iframe>
		</div>  -->
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
	            //添加默认首页
	            tabs.add({
	                url: '<%=defaultHomeurl%>',
	                label: '<%=defaultHomename%>'
	               
	            });
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
	            	  }else{
	            	    return false;
	            		  
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
						  	    	height = document.body.scrollHeight-146;
						  	 }
					  		inheight = height;
							inwidth = width;
					  	    win.height = height+"px";
					  	    win.parentNode.style.width = width+"px";
					    	win.parentNode.style.height = height+"px";
						 }
					   	else if(win.Document && win.Document.body.scrollHeight)
					   	{	
					   	
					   	   height = win.Document.body.scrollHeight;
					      	width = win.Document.body.scrollWidth;
					        if(height<document.body.scrollHeight-120){
					  	    	height = document.body.scrollHeight-146;
					  	    }
					    	win.height = height+"px";
					    	inheight = height;
					    	inwidth = width;
					        height = height;
						  	width = width;
					    	win.parentNode.style.width = width+"px";
					    	win.parentNode.style.height = height+"px";
						   
						  }
					
					     
					}
					
			 	}
			 	
			}
			</script>
	</SCRIPT>
</body>
</html>

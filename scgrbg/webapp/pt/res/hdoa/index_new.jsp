<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.aisino.platform.view.basicWidget.Int"%>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import=" cn.com.huadi.aos.hdoa.login.service.MenuService"%>
<%@ page import=" com.aisino.platform.util.SessUtil"%>
<%@ page import="cn.com.huadi.aos.hdoa.common.util.StringUtil"%>
<!-- 消息提醒部分 -->
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.GrwdService"%>
<%@ page import="cn.com.huadi.aos.hdoa.xxgl.service.BmwdService"%>
<%@ include file="sessionCheck.jsp"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8">
    <title>个人办公桌面</title>
    <link rel="stylesheet" href="css/reset.css">
    <link rel="stylesheet" href="css/style.css">
    <link href="css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <!-- <link href="data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="files/登录首页/styles.css" type="text/css" rel="stylesheet"/> -->
    <script src="js/jquery.min.js"></script>
    <script src="js/index.js"></script>
 <!-- 以下是tab页签的引用资源 added by hbj 2016-07-07 -->
 <link rel="Stylesheet" href="context/themes/base/jquery-ui.css" type="text/css" />
 <script src="scripts/jquery-ui.js" type="text/javascript"></script>
 <script src="scripts/jquery.cleverTabs.js" type="text/javascript"></script>
</head>
<%	
	String refreshTime=(String)RegServiceServlet.SYSCON.get("refreshTime");
	String server=(String)RegServiceServlet.SYSCON.get("client_server");
	Map userinfo = (Map) session.getAttribute("USERINFO");//(Map)SessUtil.getSessionValue("USERINFO");
	// 获取当前时间
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String today=df.format(new Date());
	
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
	MenuService service = new MenuService();
	int defaultTopMenuid = 0;
	String defaultHomeurl = "../../../canvas?formid=home";
	String defaultHomename = "首页";
	if(request.getParameter("function_id")!=null){
		defaultTopMenuid = Integer.parseInt( String.valueOf(request.getParameter("function_id")));
	}
	if(request.getParameter("homeurl")!=null){
		if(!("").equals(request.getParameter("homeurl"))){
		   defaultHomeurl =  String.valueOf(request.getParameter("homeurl"));
		}
	}
	List topList = service.queryTopMenu(userid, "210");
	 if(topList!=null){
		if(topList.size()>0&&defaultTopMenuid==0){
			Map data = (Map)topList.get(0);
			defaultTopMenuid = Integer.parseInt(String.valueOf(data.get("function_id")));
			
		}
	} 
	//System.out.println("top:"+defaultTopMenuid);
	//String menuHtmlString = service.getMenuHtml(userid,defaultTopMenuid);
	defaultTopMenuid = 210;
	// 点击左侧菜单，在右半部分显示相应内容
	String menuHtmlString = service.getMenuHtmlNew(userid,defaultTopMenuid);
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
	
	//消息提醒部分实现：从后台获取msg数据:
	GrwdService grwdService=new GrwdService();
	
	
	//WaitDisposeService waitDisposeService=new WaitDisposeService();
	Map userInfo = (Map) session.getAttribute("USERINFO");
	List<Map> warnMess=grwdService.queryListWarn(unitid,deptid,userid,user_secret);
	String warn="";
	if(warnMess!=null){
		 warn=warnMess.size()+""; 
		
		 for(int i=0;i<warnMess.size();i++){
			Object subsystem=warnMess.get(i).get("subsystem");
			Object title=warnMess.get(i).get("title");
			Object send_unit=warnMess.get(i).get("send_unit");
			Object sender=warnMess.get(i).get("sender");
			Object send_time=warnMess.get(i).get("send_time").toString().substring(0, 16);
			/* warn+="〔"+subsystem+"〕"+title+"</br>"+send_unit+"+"+sender+"（"+send_time+"）"+"</br>"; */
		} 
		
	} 
%>

<body onload="showtime();">
<style type="text/css" >
#winpop { width:230px; position:absolute; right:1px; bottom:5px; border:1px solid #999999; margin:0; padding:1px; overflow:hidden; display: none; height:0px; background:url(http://www.niutw.com/images/bg.png) #FFFFFF}
/* #winpop .title { width:100%; height:20px; line-height:20px; background:#FFF8D7; font-weight:bold; text-align:center; font-size:12px;} */
#winpop .title { width:100%; height:20px; line-height:20px; background:#FF0000; font-weight:bold; text-align:center; font-size:12px;}
#winpop .con { width:100%; height:120px; line-height:20px; font-size:12px; color:#000000; text-align:left; overflow-y:auto}
/* #winpop .con { width:100%; height:80px; line-height:20px; font-weight:bold; font-size:12px; color:#FF0000; text-decoration:underline; text-align:left; overflow-y:auto} */
#silu { font-size:13px; color:#999999; position:absolute; right:0; text-align:right; text-decoration:underline; line-height:22px;}
.hide { position:absolute; right:20px;  color:#000000; cursor:pointer}
.close { position:absolute; right:4px;  color:#000000; cursor:pointer}
</style>
<div id="header">
    <img class="logo_img" src="images/topleftbg.png" alt="">
    <h1 class="main_title"><img src="images/leftfontbg.png" alt=""></h1>
   <!--  <h2 class="second_title"><img src="images/SystemName/rightfontbg3.png" alt=""></h2>  -->
    <h4 class="second_title"><a href="#" onclick="showTxb()"><span>通讯簿  </span></a>
    <a href="#" onclick="showZlk1()"><span>  共享目录</span></a> 
    <a href="#" onclick="showZlk2()"><span>  共享文件</span></a></h4> 
   <!--  <h2 class="second_title">共享文件</h2>   -->
    <div class="login">
        <!-- 需要显示单位  人名  时间  -->
        <div><p></p>
        <p><span id="time"></span></p></div>
        <div><p><span class="user"><%=username %></span></p>
        <p></p></div>
        <div><p><span class="unit" title="<%=unitname%>"><%=unitname%></span><span class="unit_name" title="<%=deptname%>"><%=deptname%></span></p>
        <p></p></div>

    </div>
   <%--  <%-- <div class="login">
        <!-- 需要显示单位  人名  时间  -->
        <p><span class="user"><%=username %></span></p>
        <p><span class="unit" title="<%=unitname%>"><%=unitname%></span><span class="unit_name" title="<%=deptname%>"><%=deptname%></span></p>
        <!-- <p><span>欢迎: </span></p> -->
        
       
    </div> --%>
    <img class="logo_right" src="images/toprightbg.png" alt="">  
</div>
<div class="line">
    <!-- <ul class="fir_nav">
        <li class="active"><a href="javascript:;"><span class="tab_left"></span>一级导航一<span class="tab_right"></span></a></li>
        <li><a href="javascript:;"><span class="tab_left"></span>一级导航二<span class="tab_right"></span></a></li>
    </ul> -->
</div>
<div id="content">
    <div id="nav">
    
     <!-- Unnamed (矩形) -->
      <div id="u8" class="ax_default box_1">
        <div id="u8_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u9" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u10" class="ax_default box_1">
        <div id="u10_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u11" class="text" style="visibility: visible;">
          <p><span>&nbsp;&nbsp;&nbsp; 待办事项</span></p>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u12" class="ax_default label">
        <div id="u12_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u13" class="text" style="visibility: visible;">
          <a href="#" id="db1" onclick="showBj()"><p><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">公文待办件(</span><span style="font-family:'Arial Negreta', 'Arial';font-weight:700;color:#FF0000;">3</span><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">)</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u14" class="ax_default label">
        <div id="u14_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u15" class="text" style="visibility: visible;">
          <a href="#" id="db2" onclick="showBj()"><p><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">刊物待办件(</span><span style="font-family:'Arial Negreta', 'Arial';font-weight:700;color:#FF0000;">2</span><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">)</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u16" class="ax_default label">
        <div id="u16_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u17" class="text" style="visibility: visible;">
          <a href="#" id="db3" onclick="showBj()"><p><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">值班待办件(</span><span style="font-family:'Arial Negreta', 'Arial';font-weight:700;color:#FF0000;">1</span><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">)</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u18" class="ax_default label">
        <div id="u18_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u19" class="text" style="visibility: visible;">
          <a href="#" id="db4" onclick="showBj()"><p><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">会议待办件(</span><span style="font-family:'Arial Negreta', 'Arial';font-weight:700;color:#FF0000;">2</span><span style="font-family:'Arial Normal', 'Arial';font-weight:400;">)</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u20" class="ax_default box_1">
        <div id="u20_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u21" class="text" style="visibility: visible;">
          <p><span>&nbsp;&nbsp;&nbsp; 业务系统</span></p>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u22" class="ax_default label">
        <div id="u22_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u23" class="text" style="visibility: visible;">
          <a href="#" id="gwxt" onclick="showSystem1()"><p><span style="text-decoration:underline;">公文处理系统</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u24" class="ax_default label">
        <div id="u24_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u25" class="text" style="visibility: visible;">
          <a href="#" id="kwxt" onclick="showSystem2()"><p><span style="text-decoration:underline;">刊物管理系统</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u26" class="ax_default label">
        <div id="u26_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u27" class="text" style="visibility: visible;">
          <a href="#" id="zbxt" onclick="showSystem3()"><p><span style="text-decoration:underline;">值班管理系统</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u28" class="ax_default label">
        <div id="u28_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u29" class="text" style="visibility: visible;">
          <a href="#" id="hyxt" onclick="showSystem4()"><p><span style="text-decoration:underline;">会议管理系统</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
      <div id="u30" class="ax_default label">
        <div id="u30_div" class=""></div>
        <!-- Unnamed () -->
        <div id="u31" class="text" style="visibility: visible;">
          <a href="#" id="txb" onclick="showSystem5()"><p><span style="text-decoration:underline;">通讯簿</span></p></a>
        </div>
      </div>

      <!-- Unnamed (矩形) -->
     <!--  <div id="u32" class="ax_default box_1">
        <div id="u32_div" class=""></div>
        Unnamed ()
        <div id="u33" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div> -->

      <!-- Unnamed (矩形) -->
   <!--    <div id="u34" class="ax_default box_1">
        <div id="u34_div" class=""></div>
        Unnamed ()
        <div id="u35" class="text" style="display: none; visibility: hidden">
          <p><span></span></p>
        </div>
      </div> -->
    
    </div>
    <div id="container">
    <div id="zw-content2" class="zw-content2" >
         
              	 <div class="zw-content2" >
			  <div id="tabs"  style="align:center;">
			   <ul>
			   </ul>
				</div>  
          
		</div>
    
    </div>
</div>
<div id="footer"><span class="welcome">欢迎登录四川省委电子政务内网</span><span class="Copyright">版权所有：华迪计算机集团有限公司</span></div>
	<!-- 这里是显示消息提醒的部分 -->
	<!-- <div id="winpop">
			<div class="title">您有新的消息(<span id="warn"></span>)<span class="hide" onclick="hide_div()"><img id="change_gif" src="../../img/blankout.png" alt="" /></span>
									   			  <span class="close" onclick="tips_pop()"><img src="../../img/Cancellation.gif" alt="" /></span>						   
			</div>
		  <div class="con" id="winpop_con">
		 </div>
	</div> -->
	<script type="text/javascript">

	
	<%--  $(function () {
		refreshDiv();
   timer= setInterval("refreshDiv()", eval('<%=refreshTime%>')); 
    	
    }); 
    function refreshDiv(){
    	tips_pop(function(){
    		  $.ajax({
    	    		url: 'getData.jsp',
    	    		success: function(data) {
    	    			// 这里取到warn,warnWess  然后刷新id=winpop的DIV  拼接显示DIV
    	    			
    	    			 var str=JSON.stringify(data); 
    	    			 var obj = eval( "(" + data + ")" );//转换后的JSON对象
    	    			 
    	    			 $("#warn").html(obj.warn);
    	    			 //2.赋值给每一个a标签
    	    			/*  console.info(m); */
    	    		
    	    			 var dest="";
    	    			 var server='<%=server%>';
    	    			
    	    			 var m=obj.warnMess;
    	    			if(m !=null){
    	    			for(var i=0;i<obj.warnMess.length;i++){
    	    			 	var st="["+m[i].subsystem.toString()+"]"+m[i].title.toString()+"，"+m[i].send_unit.toString()+m[i].sender.toString()+"（"+m[i].send_time.toString().substring(0, 16)+"）"+"</br></br>";
    	   			 	  
    	    			 	if(m[i].open_url != null && m[i].open_type != null){
    	    			 		
    	   				     var url = m[i].open_url.toString();
    	   				  	 var open_type = m[i].open_type.toString();
    	   				     var id=m[i].mess_id.toString();
    	   				     var obj_url=server+url;	
    	   					/* dest+="< a href='#' onclick='waitDispose('"+obj_url+"','"+id+"','"+open_type+"');tips_pop();'><font color='black' >"+st+"</font></a>"; */
    	   					dest+='<a href="#" onclick="waitDispose(\''+obj_url+'\',\''+id+'\',\''+open_type+'\');tips_pop();"><font color=black>'+st+'</font></a>';  
    	    			 	}else{
    	    			 	 var nurl = m[i].open_url;
      	   				  	 var nopen_type = m[i].open_type;
      	   				     var id=m[i].mess_id.toString();
      	   				     var nobj_url=server+nurl;
      	   					dest+='<a href="#" onclick="waitDispose(\''+nobj_url+'\',\''+id+'\',\''+nopen_type+'\');tips_pop();"><font color=black>'+st+'</font></a>';  
    	    			 	}
    					} 
    	    			}
    	    			show=setInterval("changeH('up')",2);
    	    			$("#winpop_con").html(dest); 
    	    		}
    	    	});  
    	});
    	
    } --%>
    
  
	</script>
	<!-- 这里是点击“通讯簿”、“共享文件”后，触发的事件 -->
	<script type="text/javascript">
		// 显示当前时间
		function showtime(){   
	
			
		    var today,hour,second,minute,year,month,date;  
			var strDate ;  
			today=new Date();  
			var n_day = today.getDay();  
			 
		switch (n_day){  
		  
		    case 0:{  
		      strDate = "星期日";  
		    }break;  
		    case 1:{  
		      strDate = "星期一";  
		    }break;  
		    case 2:{  
		      strDate ="星期二";  
		    }break;  
		    case 3:{  
		      strDate = "星期三";  
		    }break;  
		    case 4:{  
		      strDate = "星期四";  
		    }break;  
		    case 5:{  
		      strDate = "星期五";  
		    }break;  
		    case 6:{  
		      strDate = "星期六";  
		    }break;  
		    case 7:{  
		      strDate = "星期日";  
		    }break;  
		}  
			year = today.getYear();  
			month = today.getMonth()+1;  
			date = today.getDate();  
			hour = today.getHours();  
			minute =today.getMinutes();  
			second = today.getSeconds();  
			/* if(hour<10){
				hour="0"+hour;
			} */
			if(minute<10){
				minute="0"+minute;
			}
			if(second<10){
				second="0"+second;
			}
			document.getElementById('time').innerHTML = year + "年" + month + "月" + date + "日  " + strDate +"   " + hour + ":" + minute + ":" + second; 
			setTimeout("showtime();", 1000); //设定函数自动执行时间为 1000 ms(1 s)    
		} 
	</script>
	<script type="text/javascript">
	    var server='<%=server%>';
		// 点击触发事件，跳转到通讯簿  formid=txb_txl
		function showTxb(){
			
			alert("通讯簿展示成功");
			alert(server);
			window.open(server+"/txb/canvas?formid=txb_txl");
		}
	
		// 点击触发事件，跳转到资料库  共享文件、共享目录 gx_wj gx_ml
		function showZlk1(){
			
			alert("资料库展示成功");
			alert(server+"/sczlk/canvas?formid=gx_ml");
			// 共享目录
			window.open(server+"/sczlk/canvas?formid=gx_ml");
		}
		// 点击触发事件，跳转到资料库  共享文件、共享目录 gx_wj gx_ml
		function showZlk2(){
			
			alert("资料库展示成功");
			alert(server+"/sczlk/canvas?formid=gx_wj");
			// 共享文件
			window.open(server+"/sczlk/canvas?formid=gx_wj");
		}
	
	</script>
	<script type="text/javascript">
	// 点击事件:有三种打开方式“新窗口”、“页签”、“页面跳转”
	function waitDispose(url,id,open_type){
		var tabs = parent.tabs;
		// 使用startWith方法需要引入的
		String.prototype.startWith = function(compareStr){
			return this.indexOf(compareStr) == 0;
			}
		if(open_type=='页签'){			
			var obj=url.split("&");
			var label="";	
			var url_abs="";
			for(var i=0;i<obj.length;i++){
			
				if(obj[i].startWith("label")){
				
					var labelName=obj[i].split("=");
					label=labelName[1];
					
				}else{
					if(i==obj.length-1){
						url_abs+=obj[i];
					}else{
						var aa=obj[i]+"&";
						url_abs+=aa;						
					}
				}
			}
		
			// 异步请求后台数据
			 $.ajax({
				url:'xxtx.jsp',
				data:{mess_id:id},
				dataType:"json"
			});	 
			
			var tab = tabs.getTabByUrl(url);
			 if(tab != null){
	                      tab.load(url);
	                      tab.activate();
                     }else{
	                      tabs.add({
		                  url:url_abs,
		                  label:label
	                 })
	                 }
		
			
		}else if(open_type=='新窗口'){
			/*PT.showModalForm('+open_url+',null,null,null,null);*/
			// 异步请求后台数据
			 $.ajax({
				url:'xxtx.jsp',
				data:{mess_id:id},
				dataType:"json"
			});	 
			window.open(url);
			
		}else if(open_type=='页面跳转'){
			
		
		
			var obj=document.getElementById("div_ifm"); 
			var tabs=document.getElementById("tabs"); 
			// 异步请求后台数据
			 $.ajax({
				url:'xxtx.jsp',
				data:{mess_id:id},
				dataType:"json"
			});	 
			
			obj.style.display="block";
			tabs.style.display="none";
			$("#ifm").attr("src",url);
			/* window.parent.location.href=server+open_url; */
			
		}else if(open_type==null){
			//window.alert('没有数据');
			// 异步请求后台数据
			
			 $.ajax({
				url:'xxtx.jsp',
				data:{mess_id:id},
				dataType:"json"
			});	 
		}		
	}
	
	<%-- var flag="<%=msg%>"; --%>
	var flag2="<%=warn%>";
	
	// 这里是一个点击事件:1.将显示框最小化；2.点击最大化窗口实现最大化
	function hide_div(){
		var MsgPop=document.getElementById("winpop");//获取窗口这个对象,即ID为winpop的对象
		var popH=parseInt(MsgPop.style.height);//用parseInt将对象的高度转化为数字,以方便下面比较
		if (popH<=22){         //如果窗口的高度是0
			/* if(flag!=""||flag2!=""){ */
			if(flag2!=""){
				MsgPop.style.display="block";//那么将隐藏的窗口显示出来
				show=setInterval("changeD('up')",2);//开始以每0.002秒调用函数changeH("up"),即每0.002秒向上移动一次
			}
			
		}
		else {         //否则
			hide=setInterval("changeD('down')",2);//开始以每0.002秒调用函数changeH("down"),即每0.002秒向下移动一次
		}
	}
	
	function changeD(str) {
		var MsgPop=document.getElementById("winpop");
		var popH=parseInt(MsgPop.style.height);
		if(str=="up"){     //如果这个参数是UP
			$("#change_gif").attr('src',"../../img/blankout.png");
			if (popH<=150){    //如果转化为数值的高度小于等于100
				MsgPop.style.height=(popH+4).toString()+"px";//高度增加4个象素
			}
			else{
				clearInterval(show);//否则就取消这个函数调用,意思就是如果高度超过100象度了,就不再增长了
			}
		}
		if(str=="down"){
			if (popH>=22){       //如果这个参数是down
				MsgPop.style.height=(popH-4).toString()+"px";//那么窗口的高度减少4个象素
			}
			else{        //否则
				clearInterval(hide);    //否则就取消这个函数调用,意思就是如果高度小于4个象度的时候,就不再减了
				//MsgPop.style.display="none";  //因为窗口有边框,所以还是可以看见1~2象素没缩进去,这时候就把DIV隐藏掉
				document.getElementById("winpop");
				$("#change_gif").attr('src',"../../img/room.png");
			}
		}
	}
	
	
	// 这里是一个点击事件
	function tips_pop(fun){
		
	window.fun=fun;
		var MsgPop=document.getElementById("winpop");//获取窗口这个对象,即ID为winpop的对象
		var popH=parseInt(MsgPop.style.height);//用parseInt将对象的高度转化为数字,以方便下面比较
		if (popH==0){         //如果窗口的高度是0
			/* if(flag!=""||flag2!=""){ */
			if(flag2!=""){
				MsgPop.style.display="block";//那么将隐藏的窗口显示出来
				show=setInterval("changeH('up',window.fun)",2);//开始以每0.002秒调用函数changeH("up"),即每0.002秒向上移动一次
			}
			
		}
		else {         //否则
			hide=setInterval("changeH('down',window.fun)",2);//开始以每0.002秒调用函数changeH("down"),即每0.002秒向下移动一次
		}
	}
	
	
	function changeH(str,fun) {
		var MsgPop=document.getElementById("winpop");
		var popH=parseInt(MsgPop.style.height);
		if(str=="up"){     //如果这个参数是UP
			MsgPop.style.display="block";
			if (popH<=150){    //如果转化为数值的高度小于等于100
				MsgPop.style.height=(popH+4).toString()+"px";//高度增加4个象素
			}
			else{
				clearInterval(show);//否则就取消这个函数调用,意思就是如果高度超过100象度了,就不再增长了
				if(fun)
				fun();
			}
		}
		if(str=="down"){
			if (popH>=4){       //如果这个参数是down
				MsgPop.style.height=(popH-4).toString()+"px";//那么窗口的高度减少4个象素
			}
			else{        //否则
				clearInterval(hide);    //否则就取消这个函数调用,意思就是如果高度小于4个象度的时候,就不再减了
				MsgPop.style.display="none";  //因为窗口有边框,所以还是可以看见1~2象素没缩进去,这时候就把DIV隐藏掉
				if(fun)
				fun();
			}
		}
	}
	
	
	document.getElementById('winpop').style.height='0px';//我不知道为什么要初始化这个高度,CSS里不是已经初始化了吗,知道的告诉我一下
	//setTimeout("tips_pop()",1500);  
	</script>
</body>

<SCRIPT>
		/* var d = new Date();
		var dd = d.getFullYear() + "年" + (d.getMonth() + 1) + "月" + d.getDate()
				+ "日";
		document.getElementById("nowdate").innerHTML = dd; */
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
	        	
	        	// 隐藏消息提醒用的DIV，否则影响页面的显示
				/* var ifm=document.getElementById("div_ifm"); */
				var tabb=document.getElementById("tabs");
			
				
	            tabs = $('#tabs').cleverTabs({
	            	//去掉右键菜单
	            	  setupContextMenu: false
	            });
	            $(window).bind('resize', function () {
	                tabs.resizePanelContainer();
	            });
	            //添加默认首页
	            <%-- tabs.add({
	                url: '<%=defaultHomeurl%>',
	                label: '<%=defaultHomename%>'
	               
	            }); --%>
	          //如果点击<a herf链接  判断是否添加tab页
	            $("#db1").click(function(){  
	            	
	            	
	            	/* ifm.style.display="none"; */
					tabb.style.display="block";
					
	            	  var url = $(this).attr("href");
	            	 
	            	  var name = $(this).text();
	            	  alert(name);
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			var tab =tabs.getTabByUrl(url);
	            			if(tab){
	            				tab.activate();
	            				tab.refresh();
	            			}else{
	            				 tabs.add({
	       	                      url: '../../../canvas?formid=gwdb',
	       	                      label: '公文待办件'
	       	                     });
	            			}
	                    /*  $("#ifm").attr("src",url);  */
	            	    return false;
	            	    }
	            	  }else{
	            		  return false;
	            	  }
	            	 
	            	});
	          //如果点击<a herf链接  判断是否添加tab页
	            $("#db2").click(function(){  
	            	
	            	
	            	/* ifm.style.display="none"; */
					tabb.style.display="block";
					
	            	  var url = $(this).attr("href");
	            	 
	            	  var name = $(this).text();
	            	  alert(name);
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			var tab =tabs.getTabByUrl(url);
	            			if(tab){
	            				tab.activate();
	            				tab.refresh();
	            			}else{
	            				 tabs.add({
	       	                      url: '../../../canvas?formid=kwdb',
	       	                      label: '刊物待办件'
	       	                     });
	            			}
	                    /*  $("#ifm").attr("src",url);  */
	            	    return false;
	            	    }
	            	  }else{
	            		  return false;
	            	  }
	            	 
	            	});
	          //如果点击<a herf链接  判断是否添加tab页
	            $("#db3").click(function(){  
	            	
	            	
	            	/* ifm.style.display="none"; */
					tabb.style.display="block";
					
	            	  var url = $(this).attr("href");
	            	 
	            	  var name = $(this).text();
	            	  alert(name);
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			var tab =tabs.getTabByUrl(url);
	            			if(tab){
	            				tab.activate();
	            				tab.refresh();
	            			}else{
	            				 tabs.add({
	       	                      url: '../../../canvas?formid=grbj&id=4',
	       	                      label: '值班待办件'
	       	                     });
	            			}
	                    /*  $("#ifm").attr("src",url);  */
	            	    return false;
	            	    }
	            	  }else{
	            		  return false;
	            	  }
	            	 
	            	});
	          //如果点击<a herf链接  判断是否添加tab页
	            $("#db4").click(function(){  
	            	
	            	
	            	/* ifm.style.display="none"; */
					tabb.style.display="block";
					
	            	  var url = $(this).attr("href");
	            	 
	            	  var name = $(this).text();
	            	  alert(name);
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			var tab =tabs.getTabByUrl(url);
	            			if(tab){
	            				tab.activate();
	            				tab.refresh();
	            			}else{
	            				 tabs.add({
	       	                      url: '../../../canvas?formid=grbj',
	       	                      label: '会议待办件'
	       	                     });
	            			}
	                    /*  $("#ifm").attr("src",url);  */
	            	    return false;
	            	    }
	            	  }else{
	            		  return false;
	            	  }
	            	 
	            	});
/* 	          //如果点击<a herf链接  判断是否添加tab页
	            $("#m1 dt,#m1 dd").click(function(){  
	            	
	            	ifm.style.display="none";
					tabb.style.display="block";
	            	  var url = $(this).attr("href");
	            	  var name = $(this).text();
	            	  if(url!="javascript:void(0)"){
	            		if(url.indexOf("loginOut.jsp")<0){
	            			var tab =tabs.getTabByUrl(url);
	            			if(tab){
	            				tab.activate();
	            				tab.refresh();
	            			}else{
	            				 tabs.add({
	       	                      url: '../../../canvas?formid=grbj',
	       	                      label: name
	       	                     });
	            			}
	                    /*  $("#ifm").attr("src",url);  
	            	    return false;
	            	    }
	            	  }else{
	            		  return false;
	            	  }
	            	 
	            	}); */
	          
	        });
	    
	 
	   	 
	        
	        var browserVersion = window.navigator.userAgent.toUpperCase();
	        var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
	        var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
	        var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
	        var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
	        var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
	        var isIE9More = (! -[1, ] == false);
	        function SetWinHeight(obj) {
	            try {
	                var iframe = obj;
	                var bHeight = 0;
	                if (isChrome == false && isSafari == false)
	                    bHeight = iframe.contentWindow.document.body.scrollHeight;

	                var dHeight = 0;
	                if (isFireFox == true)
	                    dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
	                else if (isIE == false && isOpera == false)
	                    dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
	                else if (isIE == true && isIE9More) {//ie9+
	                    var heightDeviation = bHeight - eval("window.IE9MoreRealHeight" + iframe.id);
	                    if (heightDeviation == 0) {
	                        bHeight += 3;
	                    } else if (heightDeviation != 3) {
	                        eval("window.IE9MoreRealHeight" + iframe.id + "=" + bHeight);
	                        bHeight += 3;
	                    }
	                }
	                else//ie[6-8]、OPERA
	                    bHeight += 3;

	                var height = Math.max(bHeight, dHeight);
	                if (height < 500) height = 500;
	                iframe.style.height = height + "px";
	            } catch (ex) { }
	        } 
	        
	        // 获取页面传递的PT对象
	 
			</script>
			
			
			<script type="text/javascript">
				// 获取URL的前半部分
				var server='<%=server%>';
				// 公文系统的跳转  HDOA/pt/res/hdoa/index_new.jsp
				function showSystem1(){
					alert('公文系统');
					alert(server);
					window.open(server+"/HDOA/pt/res/hdoa/index_new.jsp");
				}
				// 刊物系统的跳转   sckw/pt/res/hdoa/index_new.jsp
				function showSystem2(){
					alert('刊物系统');
					alert(server);
					window.open(server+"/sckw/pt/res/hdoa/index_new.jsp");
				}
				// 会议系统的跳转  sczb/pt/res/hdoa/index_new.jsp
				function showSystem3(){
					alert('值班管理系统');
					alert(server);
					window.open(server+"/sczb/pt/res/hdoa/index_new.jsp");
				}
				// 通讯簿系统的跳转  schy/pt/res/hdoa/index_new.jsp
				function showSystem4(){
					alert('会议系统');
					alert(server);
					window.open(server+"/schy/pt/res/hdoa/index_new.jsp");
				}
				// 值班管理系统的跳转   sctxb/pt/res/hdoa/index_new.jsp
				function showSystem5(){
					alert('通讯簿');
					alert(server);
					window.open(server+"/sctxb/pt/res/hdoa/index_new.jsp");
				}
			</script>
</html>
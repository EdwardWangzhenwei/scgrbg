<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import=" java.text.SimpleDateFormat" %>
<%@ page import="com.aisino.platform.util.SessUtil"%>
<%@ page
	import="cn.com.huadi.aos.hdoa.richangguanli.mycalendar.service.MyCalenderService"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href='../css/fullcalendar.css' rel='stylesheet' />
<link href='../css/fullcalendar.print.css' rel='stylesheet'
	media='print' />
<script type="text/javascript" src="../js/jquery-1.9.1.min.js"></script>
<script src='../js/moment.min.js'></script>
<script type="text/javascript" src="../js/jquery-ui.custom.min.js"></script>
<script type="text/javascript" src="../js/fullcalendar.js"></script>
<%
	Map userInfo = (Map) session.getAttribute("USERINFO");
	String path = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + request.getContextPath();

	Object user_id=userInfo.get("user_id");
	
	MyCalenderService cs = new MyCalenderService();
	List<Map> list = cs.QueryMyCalender(user_id);
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String defaultDate = sf.format(date);
	
%>
<script type="text/javascript">

var defaultDate1 = '<%=date%>';

	$(document).ready(
			function() {
				var initialLangCode = 'zh-cn';	
				var defaultDate = '<%=defaultDate%>';
				$('#calendar').fullCalendar(
						{
							header : {
								left : 'prevYear,nextYear,  prev,next,  today2',
								center : 'title',
								right : 'month,agendaWeek,agendaDay '
							},
							buttonText : {
								prev : '<', // ‹
								next : '>', // ›
								today2 : '今天',
								prevYear:'上一年',
								nextYear:'下一年',
								month : '月',
								week : '周',
								day : '日',
							},
				               titleFormat: {  
				                    month: 'YYYY年MM月',  
				                    week: 'YYYY年MM月DD日',  
				                    day: 'YYYY年MM月DD日 dddd'  
				                },  
				            defaultDate: defaultDate,
							monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
									'七月', '八月', '九月', '十月', '十一月', '十二月' ],
							monthNamesShort : [ '1', '2', '3', '4', '5', '6',
									'7', '8', '9', '10', '11', '12' ],
							dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四',
									'星期五', '星期六' ],
							dayNamesShort : [ '周日', '周一', '周二', '周三', '周四',
									'周五', '周六' ],
							allDaySlot : true,
							allDayText : '全天',
							axisFormat : 'HH:mm',
							isRTL : false,
							height:500,
							slotMinutes : 120,
							lang : initialLangCode,
							handleWindowResize:true,
							buttonIcons : false, // show the prev/next text
							weekNumbers : false,
							editable : false,
							eventLimit : true, // allow "more" link when too many events
							 events : [
							          <%
							          if(list!=null){
							          for (int i = 0; i < list.size(); i++) {
				if (i == (list.size() - 1)) {%>
							          {	 
							        	  id:'<%=String.valueOf(list.get(i).get("rc_id"))%>',
							        	  title: '<%=String.valueOf(list.get(i).get("zt"))%>',
							        	  start: '<%=String.valueOf(list.get(i).get("sj"))%>',
							        	  end:'<%=list.get(i).get("jssj")%>'
							          }
							          <%} else {%>
									          {	
									        	  id:'<%=String.valueOf(list.get(i).get("rc_id"))%>',
									        	  title: '<%=String.valueOf(list.get(i).get("zt"))%>',
									        	  start: '<%=String.valueOf(list.get(i).get("sj"))%>',
									        	  end:'<%=list.get(i).get("jssj")%>'
									          },	  
							        		  <%}
			}
			
			}%>
							          ],
							          eventClick: function(xEvent, jsEvent, view) {
							      		var iWidth=800; //弹出窗口的宽度;
							    		var iHeight=600; //弹出窗口的高度;
							    		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
							    		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
							        	//open("<%=path%>/canvas?formid=detail&flag=2&rc_id="+xEvent.id,"","height=600,width=800,top=200,left=500,scrollbars=no,location=no");
							    		//flag==1   弹出的页面可编辑  flag==2 弹出的页面不可编辑 
							        	open("<%=path%>/canvas?formid=detail&flag=1&rc_id="+xEvent.id,"","height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);   
							        	/*alert("Title: " + xEvent.title             //Get the event title
							        	          + "\n StartTime: " + xEvent.start    //Get the event start date
							        	          + "\n EndTime: " + xEvent.end
							        	          + "\n Endid: " + xEvent.id //Get the event end date
							        	    );*/

							        	   // console.log(xEvent); //See your console for all event properties/objects
							        	},
							        	dayClick: function(date, allDay, jsEvent, view) {
							        		 //alert("11111111111");
							        		},
										//selectable: true,
										//selectHelper: true,
										//select: function(start, end) {
											
											//alert("start=="+start);
											//alert("end=="+end);
											
										//	var title = prompt('Event Title:');
										//	var eventData;
										//	if (title) {
										//		eventData = {
										//			title: title,
										//			start: start,
										//			end: end
										//		};
										//		$('#calendar').fullCalendar('renderEvent', eventData, true); // stick? = true
										//	}
										//	$('#calendar').fullCalendar('unselect');
										//}						        	
						
						});
				
			});
</script>
<style>
body {
	margin: 40px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
}

#calendar {
	max-width: 900px;
	margin: 0 auto;
}
</style>
</head>
<body>
	<div id='calendar'></div>
</body>

</html>
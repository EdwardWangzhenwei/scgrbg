$.ajaxSetup({ 
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(xhr, textStatus) {
		//session timeout
		var sessionstatus = xhr.getResponseHeader("sessionstatus"); 
		if (sessionstatus == 'timeout') {
			alert("登录信息过期,请重新登录!");
			if (opener==null){
				if(window.dialogArguments){
					  window.dialogArguments.top.location='canvas?formid=login';
					  window.close();
				}
				else{
				//top.location='/Web/index.jsp';
				   top.location='canvas?formid=login';
				}
				
			}else{
				//opener.top.location='/Web/index.jsp';
			  if(opener.top.top!=null){
				  opener.top.top.location='canvas?formid=login';
			  }
			  else{
				opener.top.location='canvas?formid=login';}
				window.open('','_top'); 
		        window.close();
			}
			return;
		}
	}

});
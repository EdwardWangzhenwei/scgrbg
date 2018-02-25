//显示除督查文件资料外其他资料详细信息
var showZl={
		openZl:function(){
			 var map=w('list0').getSelected();
	    	 var zlxxid=map.zlxxid;
	    	 PT.showModal("archives_detail","zlxxid="+zlxxid,800,400,null) ;
		}
}
//显示督查文件资料详细信息
var showZl1={
		openZl1:function(){
			 var map=w('list0').getSelected();
	    	 var zlxxid=map.zlxxid;
	    	 PT.showModal("archives_detail1","zlxxid="+zlxxid,800,400,null) ;
		}
}
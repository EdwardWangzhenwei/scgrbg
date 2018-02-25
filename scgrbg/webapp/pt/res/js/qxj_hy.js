//查看请假人数名单
function  qjmd(){
	var map=w('list').getSelected();
	 var hygl_id=map.hygl_id;
	 var hybmd_id=map.hybmd_id;
	 PT.showModal("attendlist","hygl_id="+hygl_id+"&hybmd_id="+hybmd_id,800,550,null);
}
function  cxmd(){
	var map=w('list').getSelected();
	 var hygl_id=map.hygl_id;
	 var hybmd_id=map.hybmd_id;
	 PT.showModal("leavelist","hygl_id="+hygl_id+"&hybmd_id="+hybmd_id,800,550,null);
}
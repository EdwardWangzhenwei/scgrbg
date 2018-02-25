//销假申请
function xiaojia() {
	var map=w('list').getSelected();
	 var wcqjsq_id=map.wcqjsq_id;
	 PT.showForm("xiaojia","wcqjsq_id="+wcqjsq_id);

}
//请假单
function qingjiadan() {
	var map=w('list').getSelected();
	var wjbm=map.qjwjbm;
	var n_id=map.qj_n_id;
	PT.s('writeNvt',null,'n_id='+n_id+'&wjbm='+wjbm);
	PT.showModal("qingjiadan","wjbm="+wjbm+"&n_id="+n_id+"&cd="+1,1024,700,null);
	
}
//销假单
function xiaojiadan() {
	var map=w('list').getSelected();
	var wjbm=map.xjwjbm;
	var n_id=map.xj_n_id;
	PT.s('writeNvt',null,'n_id='+n_id+'&wjbm='+wjbm);
	PT.showModal("xiaojiadan","wjbm="+wjbm+"&n_id="+n_id+"&cd="+1,1024,700,null);
	
}
//出差撤销申请
function chexiao() {
	var map=w('list').getSelected();
	 var ccsq_id=map.ccsq_id;
	 PT.showForm("qxchuchai","ccsq_id="+ccsq_id);

}
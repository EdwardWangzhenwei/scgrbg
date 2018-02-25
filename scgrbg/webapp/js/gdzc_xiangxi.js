//查看详情
function detail1() {
	var map=w('Assetjhlist').getSelected();
	 var zcxx_id=map.zcxx_id;
	 PT.showModal("asset_detail","zcxx_id="+zcxx_id,1020,700,null);

}
function detail2() {
	var map=w('Assetczlist').getSelected();
	var zcxx_id=map.zcxx_id;
	PT.showModal("asset_detail","zcxx_id="+zcxx_id,1020,700,null);
	
}
function detail3() {
	var map=w('AssetBglist').getSelected();
	var zcxx_id=map.zcxx_id;
	PT.showModal("asset_detail","zcxx_id="+zcxx_id,1020,700,null);
	
}
function detail4() {
	var map=w('Assetwxlist').getSelected();
	var zcxx_id=map.zcxx_id;
	PT.showModal("asset_detail","zcxx_id="+zcxx_id,1020,700,null);
	
}
function del() {
	w('list').deleteSelect();
}

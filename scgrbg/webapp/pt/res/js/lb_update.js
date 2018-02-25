
//附件列表新增页面使用
function createFjTab1(res){
	var zsjs=document.getElementById("zsjs");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="附件上传" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd();">附件上传</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		
		for(var i=0;i<res.length;i++){
			if(i>=1){
				alert("只允许上传一个附件，请删除当前附件后增加！");
			}
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}
//附件列表修改页面使用
function createFjTab(res){
	var zsjs=document.getElementById("zsjs");
	// 此时如果已经有附件，就给出提示，确认则删除原来附件；取消什么也不做
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="附件上传" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd1();">附件上传</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj1(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}

//附件列表详情
function createFjTab2(res){
	var zsjs=document.getElementById("zsjs");
	zsjs.innerHTML='';
	if(res&&res.length>0){
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjms+'</a></span><br>';
					
		}
		}
	
		w('dzwj_id').set(dzwj_ids);
};
//接待附件新增
function fjadd(){
	var res=PT.showModal('lbfj','union_table=jd_lbxx&union_table_id='+wg('lbxx_id'),550,250,null);
	if(res!=null){
		w('lbxx_id').set(res);
		var s=PT.ns('loadfj',null);	
		/*if(s=='addFail'){
			alert("只允许上传一个附件，请删除原附件后上传！");
			return;
		}*/
			createFjTab1(s);	
	}
}
//会议附件修改
function fjadd1(){
	var res=PT.showModal('lbfj','union_table=jd_lbxx&union_table_id='+wg('lbxx_id'),550,250,null);
	if(res!=null){
		w('lbxx_id').set(res);
		var s=PT.ns('loadfj',null);
			createFjTab(s);	
	}
}
//会议新增删除电子文件
function deletefj(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab1(s);
	}
}


//会议修改删除电子文件
function deletefj1(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab(s);
	}
}



//拟制方案附件列表新增页面使用
function createFjTab3(res){
	var zsjs=document.getElementById("zsjs");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd3();">上传附件</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj3(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}
//拟制方案附件列表修改页面使用
function createFjTab4(res){
	var zsjs=document.getElementById("zsjs");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd4();">上传附件</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj4(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}

//方案附件新增
function fjadd3(){
	var res=PT.showModal('fj','union_table=hy_nzfa&union_table_id='+wg('fa_id'),550,250,null);
	if(res!=null){
		w('fa_id').set(res);
		var s=PT.ns('loadfj',null);
			createFjTab3(s);	
	}
}
//方案附件修改
function fjadd4(){
	var res=PT.showModal('fj','union_table=hy_nzfa&union_table_id='+wg('fa_id'),550,250,null);
	if(res!=null){
		w('fa_id').set(res);
		var s=PT.ns('loadfj',null);
			createFjTab4(s);	
	}
}
//方案新增删除电子文件
function deletefj3(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab3(s);
	}
}


//方案修改删除电子文件
function deletefj4(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab4(s);
	}
}


//拟制通知附件列表新增页面使用
function createFjTab6(res){
	var zsjs=document.getElementById("zsjs");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd6();">上传附件</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj6(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}
//拟制方案附件列表修改页面使用
function createFjTab7(res){
	var zsjs=document.getElementById("zsjs");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd7();">上传附件</button></span><br>';
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	zsjs.innerHTML=btn;
	//发文整个过程都显示上传按钮
	//nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj7(\''+res[i].dzwj_id+'\');\"/></span><br>';
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}

//通知附件新增
function fjadd6(){
	var res=PT.showModal('fj','union_table=hy_nztz&union_table_id='+wg('tz_id'),550,250,null);
	if(res!=null){
		w('tz_id').set(res);
		var s=PT.ns('loadfj',null);
			createFjTab6(s);	
	}
}
//通知附件修改
function fjadd7(){
	var res=PT.showModal('fj','union_table=hy_nztz&union_table_id='+wg('tz_id'),550,250,null);
	if(res!=null){
		w('tz_id').set(res);
		var s=PT.ns('loadfj',null);
			createFjTab7(s);	
	}
}
//方案新增删除电子文件
function deletefj6(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab6(s);
	}
}


//通知修改删除电子文件
function deletefj7(dzwj_id){
	var res=PT.ns('delete',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var s=PT.ns('loadfj',null);
		createFjTab7(s);
	}
}

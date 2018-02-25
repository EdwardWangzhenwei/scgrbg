function windowOpen(url,title,width,height){
    var l=( screen.availWidth - width ) / 2;
    var  t = ( screen.availHeight - height ) / 2;
    window.open(url, '', 'Width='+width+', Height='+height+',top='+t+',left='+l+',scroll=no,resizable=yes, edge=raised, status=0,help=0');
}

function zwedit(wjbm,n_id){
	window.open('pt/res/hdoa/wpsedit/edit.jsp?wjbm='+wjbm+'&n_id='+n_id, '', 'Width='+(window.screen.availWidth-10)+', Height='+(window.screen.availHeight-30)+',top=0,left=0,scroll=no,resizable=yes, edge=raised, status=0,help=0');
}
function openspd(wjbm,n_id){
	window.open('pt/res/hdoa/wpsedit/spdview.jsp?wjbm='+wjbm+'&n_id='+n_id, '', 'Width='+(window.screen.availWidth-10)+', Height='+(window.screen.availHeight-30)+',top=0,left=0,scroll=no,resizable=yes, edge=raised, status=0,help=0');
}
function openspd2(wjbm,n_id,mb_id){
	window.open('pt/res/hdoa/wpsedit/spdview.jsp?wjbm='+wjbm+'&n_id='+n_id+'&mb_id='+mb_id, '', 'Width='+(window.screen.availWidth-10)+', Height='+(window.screen.availHeight-30)+',top=0,left=0,scroll=no,resizable=yes, edge=raised, status=0,help=0');
}

function afterZwedit(tmp){
	//PT.refresh();//与门户集成时不能正常刷新，换用以下方式
	/*if(tmp){
		w('filelist').set('tab2');
	}*/
	PT.showForm('fw_update','wjbm='+wg('wjbm')+'&n_id='+wg('n_id'));
	//现在附件展示在第一个页签上，没有单独的附件列表页签，所以不用下列方式--lwf 20161021
	//var fname=w('filelist').getData().name;//获取当前选中页签的name
	//如果当前没有选中“附件”页签，则执行w('filelist').set('tab2')，否则执行w('frame2').frame().PT.wid('list')
	/*if(fname.indexOf('附件')<0){
		w('filelist').set('tab2');
	}else{
		var fjframe=w('frame2').frame();
		if(fjframe){
			fjframe.PT.wid('list').reload();
		}	
	}*/
}
function fjadd(wjbm){
	windowOpen('pt/res/hdoa/public/DzwjAdd.jsp?wjbm='+wjbm,'',750,300);
}
function afterFjadd(){
	//PT.refresh();
	//现在附件展示在第一个页签上，没有单独的附件列表页签，所以不用下列方式--lwf 20161021
	//var fname=w('filelist').getData().name;//获取当前选中页签的name
	//如果当前没有选中“附件”页签，则执行w('filelist').set('tab2')，否则执行w('frame2').frame().PT.wid('list')
	/*if(fname.indexOf('附件')<0){
		w('filelist').set('tab2');
	}else{
		var fjframe=w('frame2').frame();
		if(fjframe){
			fjframe.PT.wid('list').reload();
		}	
	}*/
}
String.prototype.replaceAll = function(s1, s2) {
	return this.replace(new RegExp(s1, "gm"), s2);
};


function replaceRN(id){
	if(window['console']){		
		console.log('开始解析意见字符串 -start');
	}
	if(document.getElementById('txt'+id)){
		var newContent = document.getElementById('txt'+id).innerHTML;
		var os=newContent.split("|@|");
		newContent="";
		for(var k=1;k<os.length;k++,k++){
			var s=os[k];
			var news="",temps=s,start=0;
			for(var i=0;i<parseInt(1150/s.length);i++){
				var len=parseInt((1150/15)*(i+1));
				if(temps.substring(start,len)){
					if(temps.length>len){
						news+=temps.substring(start,len)+"<BR>　　";
						start=len;
					}else{
						news+=temps.substring(start,len);
						start=len;
					}
				}
			}
			newContent+=news+os[k+1];
		}
		newContent=newContent.replaceAll("\n","<BR>　　");
		//newContent=newContent.replaceAll(" ","　");
		newContent=newContent.replaceAll("&lt;","<");
		newContent=newContent.replaceAll("&gt;",">");
		newContent=newContent.replaceAll("<BR><BR>","<BR>");
		document.getElementById('txt'+id).innerHTML="　　"+newContent;
	}
	if(window['console']){	
		console.log('开始解析意见字符串 -end');
	}
}

//附件列表
function createFjTab2(res){
	var zsjs=document.getElementById("zsjs");
	var nzfjlists=document.getElementById("nzfjlists");
	var wzfjlists=document.getElementById("wzfjlists");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd3();">上传附件</button></span><br>';
	var n_uperid=wg('n_uperid');
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	nzfjlists.innerHTML='';
	wzfjlists.innerHTML='';
	//var downloadBtn='';//“批量下载”和“上传附件”不同时显示
	/*if(n_uperid&&n_uperid>0){
		//如果没有附件，则不显示“批量下载”按钮
		//if(res&&res.length>0){//四院测试，暂时注释“批量下载”功能--lwf 20170301
			//downloadBtn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="批量下载" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="downloadZip();">批量下载</button></span><br>';
		//}
	}else{
		nzfjlists.innerHTML=btn;
	}*/
	//发文整个过程都显示上传按钮
	nzfjlists.innerHTML=btn;
	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
			if(res[i].dzwjsx=='正式件'||res[i].dzwjsx=='审批件'){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				//如果是OFD文件，在线打开
				if(res[i].dzwjlx.indexOf('ofd')!=-1){					
					if(n_uperid&&n_uperid>0){//pt/res/hdoa/foxit/OFDNpapi.jsp?dzwj_id='+res[i].dzwj_id
					   	if(detectOS() == 'Linux'){
							zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi_linux.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a></span><br>';
						}
						if(detectOS()=='Win7'){
							zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a></span><br>';
						}
					}else{
					   	if(detectOS() == 'Linux'){
							zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi_linux.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj2('+res[i].dzwj_id+');\"/></span><br>';
						}
						if(detectOS()=='Win7'){
							zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj2('+res[i].dzwj_id+');\"/></span><br>';
						}
					}				
				}else{					
					if(n_uperid&&n_uperid>0){
						zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
					}else{				
						zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj2('+res[i].dzwj_id+');\"/></span><br>';
					}				
				}
			}else if(res[i].dzwjsx=='内置附件'){				
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				if(n_uperid&&n_uperid>0){
					nzfjlists.innerHTML = nzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
				}else{				
					nzfjlists.innerHTML = nzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj2('+res[i].dzwj_id+');\"/></span><br>';
				}
			}else if(res[i].dzwjsx=='外置附件'){	
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				if(n_uperid&&n_uperid>0){
					wzfjlists.innerHTML = wzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
				}else{				
					wzfjlists.innerHTML = wzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj2('+res[i].dzwj_id+');\"/></span><br>';
				}				
			}
		}
		w('dzwj_id').set(dzwj_ids);
	}
	//fjlists.innerHTML=fjlists.innerHTML+downloadBtn;
}

//附件列表，初始化发文查看页面用
function createFjTab3(res){
	var zsjs=document.getElementById("zsjs");
	var nzfjlists=document.getElementById("nzfjlists");
	var wzfjlists=document.getElementById("wzfjlists");
	var n_uperid=wg('n_uperid');
	//每次初始化时只保留“上传附件”按钮（注：只针对起始节点，其他节点没有上传操作，没有“上传附件”按钮时，显示“批量下载”按钮，将文件打包下载）
	nzfjlists.innerHTML='';
	wzfjlists.innerHTML='';

	if(res&&res.length>0){
		//初始化页面dzwj_id值
		var dzwj_ids='';
		for(var i=0;i<res.length;i++){
			if(res[i].dzwjsx=='正式件'||res[i].dzwjsx=='审批件'){
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				//如果是OFD文件，在线打开
				if(res[i].dzwjlx.indexOf('ofd')!=-1){					
					if(detectOS() == 'Linux'){
						zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi_linux.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a></span><br>';
					}
					if(detectOS()=='Win7'){
						zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">下载</a></span><br>';
					}			
				}else if(res[i].dzwjlx.indexOf('wps')!=-1){					
					zsjs.innerHTML = zsjs.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/show_wps.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';				
				}
			}else if(res[i].dzwjsx=='内置附件'){				
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				nzfjlists.innerHTML = nzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
			}else if(res[i].dzwjsx=='外置附件'){	
				if(i==res.length-1){
					dzwj_ids=dzwj_ids+res[i].dzwj_id;
				}else{				
					dzwj_ids=dzwj_ids+res[i].dzwj_id+',';
				}
				wzfjlists.innerHTML = wzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
			}
			w('dzwj_id').set(dzwj_ids);
		}
	}
}
//附件列表
function createFjTab4(res){
	var zsjs=document.getElementById("zsjs");
	var nzfjlists=document.getElementById("nzfjlists");
	var wzfjlists=document.getElementById("wzfjlists");
	var btn='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><button rawclass="gradual-btnitem" class="gradual-btnitem" id="fjadd2" title="上传附件" style="line-Height:27px;height:27px;width:70px;" _mc="PT.FlatBtn" onClick="fjadd4();">上传附件</button></span><br>';
	//每次初始化时只保留“上传附件”按钮
	nzfjlists.innerHTML='';
	nzfjlists.innerHTML=btn;
	wzfjlists.innerHTML='';
	var n_uperid=wg('n_uperid');
	if(res&&res.length>0){
		for(var i=0;i<res.length;i++){
			if(res[i].dzwjsx=='内置附件'){				
				if(n_uperid&&n_uperid>0){
					nzfjlists.innerHTML = nzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
				}else{				
					nzfjlists.innerHTML = nzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj('+res[i].dzwj_id+');\"/></span><br>';
				}
			}else if(res[i].dzwjsx=='外置附件'){				
				if(n_uperid&&n_uperid>0){
					wzfjlists.innerHTML = wzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a></span><br>';
				}else{				
					wzfjlists.innerHTML = wzfjlists.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj('+res[i].dzwj_id+');\"/></span><br>';
				}
			}
		}
	}
}
//批量下载附件，即将附件压缩成zip压缩包下载
function downloadZip(){
	var filepath=PT.s('downloadZip',null);
	if(filepath!='-1'){		
		window.location.href="pt/res/hdoa/wpsedit/ShowDzwj2.jsp?filepath="+encodeURI(filepath);
	}
}
//删除电子文件
function deletefj(dzwj_id){
	var res=PT.s('deletefj',null,"dzwj_id="+dzwj_id);
	var wjbm=wg('wjbm');
	var n_id=wg('n_id');
	if(res=='ok'){
		//PT.refresh();
		var dzwj_ids=wg('dzwj_id');//删除之后，应从dzwj_id中将被删除的去掉（用-1代替），重新加载
		dzwj_ids = dzwj_ids.replace(dzwj_id,'-1');
		/*var res2=PT.s('loadfj',null);
		createFjTab2(res2);*/
		PT.showForm('fw_update','wjbm='+wjbm+'&n_id='+n_id);
		//createFjTab4(res2);
	}
}
//删除电子文件
function deletefj2(dzwj_id){
	var res=PT.s('deletefj',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		PT.showForm('fw_update','wjbm='+wg('wjbm')+'&n_id='+wg('n_id'));
		/*var res2=PT.s('loadfj',null);
		createFjTab2(res2);*/
	}
}
function fjadd3(){
	var res=PT.showModal('nowjbm_fj_add','con_table=GW_FZXX&con_table_id='+wg('wjbm'),550,250,null);
	var wjbm=wg('wjbm');
	var n_id=wg('n_id');
	if(res==''){		
	}else if(!isNaN(res)){
		var dzwj_id=wg('dzwj_id');
		if(dzwj_id!=-1){
			w('dzwj_id').set(dzwj_id+=','+res);
		}else{
			w('dzwj_id').set(res);
		}
		/*var res2=PT.s('loadfj',null);
		createFjTab2(res2);*/
		PT.showForm('fw_update','wjbm='+wjbm+'&n_id='+n_id);
	}
}
function fjadd4(){
	var res=PT.showModal('nowjbm_fj_add','con_table=GW_FZXX',550,250,null);
	if(res==''){		
	}else if(!isNaN(res)){
		var dzwj_id=wg('dzwj_id');
		if(dzwj_id!=-1){
			w('dzwj_id').set(dzwj_id+=','+res);
		}else{
			w('dzwj_id').set(res);
		}
		var res2=PT.s('loadfj',null);
		createFjTab4(res2);
	}
}
//在办理页面，用于保存领导签署意见时上传附件
function fjadd5(){
	var res=PT.showModal('nowjbm_fj_add','con_table=GW_LDPS&con_table_id='+wg('text_ldps_id'),550,250,null);
	if(res==''){		
	}else if(!isNaN(res)){
		var ldps_dzwj_id=wg('ldps_dzwj_id');
		if(ldps_dzwj_id!=-1){
			w('ldps_dzwj_id').set(ldps_dzwj_id+=','+res);
		}else{
			w('ldps_dzwj_id').set(res);
		}
		ldps_dzwj_id=wg('ldps_dzwj_id');
		//如果上传附件，则实时显示在意见框下方
		var fjs=PT.s('getyjfj',null,'dzwj_ids='+ldps_dzwj_id);
		createyjfjdiv(fjs);
	}
}
function createyjfjdiv(fjs){}
//初始化意见附件列表
function createyjfjdiv1(fjs){
	var yjfj=document.getElementById("fj-div");
	
	if(fjs){
		//将附件id放入ldps_dzwj_id隐藏域中
		var ldps_dzwj_id='';
		if(!yjfj){
	        //添加附件实时显示容器div
	        var div_fj = document.createElement("div");  
	        div_fj.id = "fj-div";
	    	var text_ldps_id=wg('text_ldps_id');
	    	text_ldps_id='text_edit_'+text_ldps_id;
	    	var div=document.getElementById(text_ldps_id);
	    	if(div){
				var parent=div.parentNode;
		        parent.appendChild(div_fj);
		        div_fj.innerHTML='';
		        for(var i=0;i<fjs.length;i++){
		        	if(i==fjs.length-1){	        		
		        		ldps_dzwj_id=ldps_dzwj_id+fjs[i].dzwj_id;
		        	}else{
		        		ldps_dzwj_id=ldps_dzwj_id+fjs[i].dzwj_id+',';	        		
		        	}
		        	div_fj.innerHTML = div_fj.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+fjs[i].dzwj_id+'\">'+fjs[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deleteyjfj('+fjs[i].dzwj_id+');\"/></span><br>';
		        }
	        }
		}else{
			yjfj.innerHTML='';
			for(var i=0;i<fjs.length;i++){				
				if(i==fjs.length-1){	        		
					ldps_dzwj_id=ldps_dzwj_id+fjs[i].dzwj_id;
				}else{
					ldps_dzwj_id=ldps_dzwj_id+fjs[i].dzwj_id+',';	        		
				}
				yjfj.innerHTML = yjfj.innerHTML+'<span id="btn_span" class="bsw-c" style="height:27px;width:auto;margin-left:25px;"><a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+fjs[i].dzwj_id+'\">'+fjs[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deleteyjfj('+fjs[i].dzwj_id+');\"/></span><br>';
			}
		}
		w('ldps_dzwj_id').set(ldps_dzwj_id);
	}else{
		if(yjfj){			
			yjfj.innerHTML='';//针对只有一个附件时，删除后页面不刷新
		}
	}	
}
//删除意见关联附件
function deleteyjfj(dzwj_id){
	var res=PT.s('deleteyjfj',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		var ldps_dzwj_id=wg('ldps_dzwj_id');
		ldps_dzwj_id = ldps_dzwj_id.replace(dzwj_id,'-1');
		w('ldps_dzwj_id').set(ldps_dzwj_id);
		var fjs=PT.s('getyjfj',null,'dzwj_ids='+ldps_dzwj_id);
		createyjfjdiv(fjs);
	}
}
function timeout(){
	var text_ldps_id=wg('text_ldps_id');
	text_ldps_id='text_edit_'+text_ldps_id;
	var div=document.getElementById(text_ldps_id);
	//如果div不为空，则添加“常用意见按钮”
	if(div){
		var parent=div.parentNode;
		//在意见文本域框之上添加“常用意见”下拉框
		var div_cyyj = document.createElement("div");  
        var cyyj = document.createElement("select");  
        cyyj.id = "cyyj"; 
        cyyj.title="常用意见";
        cyyj.style.marginLeft='900px';
        cyyj.style.marginBottom='5px';
        cyyj.style.width='200px';
        div_cyyj.appendChild(cyyj);
        cyyj.onchange=function(){
        	var sel=document.getElementById("cyyj");
        	var selected_value=sel.options[sel.selectedIndex].value;
        	if(selected_value){
        		PT.w(text_ldps_id).set(selected_value);//将选中的常用意见添加到意见文本域框
        	}        	
        };
        //添加附件实时显示容器div --签署意见不需要上传附件--lwf20161206
        //var div_fj = document.createElement("div");  
        //div_fj.id = "fj-div"; 
        //在意见文本域框下方添加“上传附件”按钮
        //var div_fj_upload = document.createElement("div");  
        //var fj_upload_btn = document.createElement("input");  
        //fj_upload_btn.id = "uploadfj"; 
        //fj_upload_btn.type="button";
        //fj_upload_btn.style.marginLeft='20px';
        //fj_upload_btn.className="gradual-btnitem";
        //fj_upload_btn.value="上传附件";
        //div_fj_upload.appendChild(fj_upload_btn);
        //fj_upload_btn.onclick=function(){
        //	fjadd5();   	
        //};
        parent.insertBefore(div_cyyj,parent.childNodes[0]);
        //parent.appendChild(div_fj);
        //parent.appendChild(div_fj_upload);
		var res=PT.s('loadcyyj');//获取常用意见信息
		var sel=document.getElementById("cyyj");
		if(res){
			//每次加载都清空原数据
			sel.options.length=0;
			sel.options.add(new Option('常用意见','常用意见'));
			for(var i=0;i<res.length;i++){
				sel.options.add(new Option(res[i].code,res[i].code));
			}
		}else{
			sel.options.length=0;
			sel.options.add(new Option('常用意见','常用意见'));			
		}
	}
}
//页面加载完毕后读取意见添加的文本域对象，不延迟执行可能取不到（文本域还未生成）
setTimeout('timeout()',500);
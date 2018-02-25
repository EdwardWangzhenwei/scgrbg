function windowOpen(url,title,width,height){
    var l=( screen.availWidth - width ) / 2;
    var  t = ( screen.availHeight - height ) / 2;
    window.open(url, '', 'Width='+width+', Height='+height+',top='+t+',left='+l+',scroll=no,resizable=yes, edge=raised, status=0,help=0');
}

function fjadd(wjbm){
	//windowOpen('pt/res/hdoa/public/DzwjAdd.jsp?wjbm='+wjbm,'',750,300);
	var res=PT.showModal('nowjbm_fj_add1','con_table=GW_FZXX&con_table_id='+wjbm,550,250,null);
	if(res==''){		
	}else{
		var res=PT.s('loadfj',null);
		createFjTab(res,false);
	}
}

function afterFjadd(){
	PT.refresh();
}
//附件列表
function createFjTab(res,isreadonly){
	var fjlist=document.getElementById('lwxx1');
	var table=document.getElementById("lwxx1.table");
	var tr,td;
	var n_uperid=wg('n_uperid');
	var n_fr_name=wg('n_fr_name');
	//如果当前环节n_fr_name为“交承办人”，则显示“删除按钮”，否则不显示
	if(res&&res.length>0){
	  	for(var i=0;i<res.length;i++){
			tr = table.insertRow();
			
			td = tr.insertCell();
			td.setAttribute("width", "20%");
			td.style.cssText="padding-left:18px;";
			td.colSpan="3";
			/*if(n_fr_name.indexOf('承办人')!=-1){*/	
			if((n_uperid&&n_uperid>0)||isreadonly){
				td.innerHTML = res[i].dzwjsx+'    <a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>';				
			}else{	
				if(res[i].dzwjsx=='正式件'&&res[i].dzwjlx.indexOf('ofd')!=-1){
				   	if(detectOS() == 'Linux'){
				   		td.innerHTML =  res[i].dzwjsx+td.innerHTML+'    <a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi_linux.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a><br>';
					}
					if(detectOS()=='Win7'){
						td.innerHTML =  res[i].dzwjsx+td.innerHTML+'    <a name=\"tm1\" href=\"pt/res/hdoa/foxit/OFDNpapi.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a><br>';
					}					
				}else{					
					td.innerHTML = res[i].dzwjsx+'    <a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>&nbsp;<input type=\"button\" class=\"gradual-btnitem\" style=\"border:0px;width:27px;height:27px;margin-left:50px;background:url(pt/res/js/img/delete_cron.png) no-repeat;cursor:pointer;\" onclick=\"deletefj('+res[i].dzwj_id+');\"/>';
				}
			}
				/*}else{
				td.innerHTML = res[i].dzwjsx+'    <a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='+res[i].dzwj_id+'\">'+res[i].dzwjm+'</a>';				
			}*/
			tr.appendChild(td);
	  	}
	  	fjlist.appendChild(table);
	}
}
//删除电子文件
function deletefj(dzwj_id){
	var res=PT.s('deletefj',null,"dzwj_id="+dzwj_id);
	if(res=='ok'){
		PT.refresh();
	}
}

function openswspd(wjbm,n_id){
	window.open('pt/res/hdoa/wpsedit/swspdview.jsp?wjbm='+wjbm+'&n_id='+n_id, '', 'Width='+(window.screen.availWidth-10)+', Height='+(window.screen.availHeight-30)+',top=0,left=0,scroll=no,resizable=yes, edge=raised, status=0,help=0');
}
function openswspd2(wjbm,n_id,mb_id){
	window.open('pt/res/hdoa/wpsedit/swspdview.jsp?wjbm='+wjbm+'&n_id='+n_id+'&mb_id='+mb_id, '', 'Width='+(window.screen.availWidth-10)+', Height='+(window.screen.availHeight-30)+',top=0,left=0,scroll=no,resizable=yes, edge=raised, status=0,help=0');
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
function createyjfjdiv(fjs){}
//初始化意见附件列表
function createyjfjdiv1(fjs){
	yjfj=document.getElementById("fj-div");
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
        //添加附件实时显示容器div
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
			sel.options.add(new Option('常用意见',''));
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
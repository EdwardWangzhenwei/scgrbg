var hd ={
	
	/**	���ݲ�ͬ��������ؿ��õ� XMLHttpRequest 	**/
	createXHR:function(){
		var xmlhttp;
		if (window.XMLHttpRequest){
		  // code for IE7+, Firefox, Chrome, Opera, Safari
			//alert("XMLHttpRequest");
		  xmlhttp=new XMLHttpRequest();
		}
		else{
			// code for IE6, IE5
			//alert("ActiveXObject");
		  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	},
		
		
	/**
	 * ajax Get��ʽ�ύ���󣬸÷�������IE�������chrome �������
	 * url �����ַ
	 * successCallBack �������ɹ���Ӧ�ص�����
	 * errorCallBack �������쳣��Ӧ�ص�����
	 */
	ajaxGet:function(url,successCallBack,errorCallBack){
		var xmlhttp = this.createXHR();
		xmlhttp.onreadystatechange = function(){
  			if (xmlhttp.readyState==4 && xmlhttp.status==200){
  				if(successCallBack){
  					successCallBack(xmlhttp.responseText);
  				}
  			}else{
  				if(errorCallBack){
  					errorCallBack();
  				}
  			}
  		}
  		xmlhttp.open("GET",url,true);
  		xmlhttp.send();
	},
	
	/**
	 * ajax POST��ʽ�ύ���󣬸÷�������IE�������chrome �������
	 * url �����ַ
	 * successCallBack �������ɹ���Ӧ�ص�����
	 * errorCallBack �������쳣��Ӧ�ص�����
	 */
	ajaxPost:function(url,successCallBack,errorCallBack){
		var xmlhttp = this.createXHR();
		xmlhttp.onreadystatechange = function(){
  			if (xmlhttp.readyState==4 && xmlhttp.status==200){
  				if(successCallBack){
  					successCallBack(responseText);
  				}
  			}else{
  				if(errorCallBack){
  					errorCallBack();
  				}
  			}
  		}
  		xmlhttp.open("POST",url,true);
  		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
  		xmlhttp.send();
	},
	
	/**
	 * ͨ��xml�ַ�����ȡ XML DOM ���������� IE,���,chrome �������
	 * 
	 */
	getXmlDom:function(xml){
		var xmlDom = null;
		if (window.ActiveXObject){
			//IE �����
			xmlDom = new ActiveXObject('Microsoft.XMLDOM');
			if(!xmlDom)
				xmlDom = new ActiveXObject("MSXML2.DOMDocument.3.0");
			xmlDom.async = false;  
			xmlDom.loadXML(xml);  
		}else{
			var domParser = new DOMParser();
			xmlDom = domParser.parseFromString(xml, 'text/xml'); 
		}
		return xmlDom;
	},
	
	tt:function(){
		
		alert("hd.tt");
	},
	
	
	/**
	 * 
	 * ��ȡxml�ı�ǩֵ,��ʵ��
	 * 
	 */
	getTagNameValue:function(xml,tagname){
		var xmlDom = this.getXmlDom(xml);
		var val = null;
		if(xmlDom){
			val=xmlDom.getElementsByTagName(tagname)[0].childNodes[0].nodeValue;
		}
		return val;
	},
	
	/**
	 * 
	 * 
	 * 
	 */
	getTagNameValue1:function(xml,tagname){
		var xmlDom = this.getXmlDom(xml);
		var val = null;
		
		if(xmlDom){
			val=xmlDom.getElementsByTagName(tagname).childNodes[0];
		}
		return val;
	},
	
	
	/***********************************************һ�������xml���ݵ������ķ����Ľ�***********************************************************/
	
	/****************************���ԭxml���ݵ���Ҫ��д�����¸�ʽ***********************************/
	/**    <FunctionList id="_functionlist" >
	 * 	   	<FunctionInfo sid='1' sValue='1' sText='1'></FunctionInfo>
	 * 		<FunctionInfo></FunctionInfo>
	 * 					.......
	 *     </FunctionList>                **/
	
	/**
	 * selectid: ��ǩID
	 * selecttar: ��ǩ����
	 * target : ��ǩ����������
	 *      v : ����ֵ
	 * return : �������� ����ʹ��$.attr()��ʽȡ�ö������Ե�ֵ
	 */
	selectTargetValueToArray:function(selectid,selecttar,target,v){
		var nodelist = new Array();
		$('#'+selectid+" "+selecttar).each(function(i){
			var obj = $(this);
			if(obj.attr(target)==v){
				nodelist.push(obj);
			}
		});
		return nodelist;
	},
	/**
	*	curpage ��ǰҳ
	*	pagesize Ĭ�Ϸ�ҳ��С
	**/
	pagewapper:function(curpage,pagesize){
		//������
		var total_size = $("#xmldso").find("GwListInfo").length;
		var total_page = (total_size%pagesize==0?total_size/pagesize:parseInt(total_size/pagesize)+1);
		var start;
		if(curpage=='-1'){
			start = (parseInt(total_page)-1)*pagesize;
			curpage = parseInt(total_page)-1;
		}
		else if(curpage=='0'){
			start = 0;
		}	
		else{
			start = curpage*pagesize;
		}
		if(curpage>=total_page)
			return ;
		var end_page = 0;
		if((curpage*pagesize+pagesize)<=total_size)
			end_page = curpage*pagesize+pagesize;
		else
			//end_page = (total_size-(curpage*pagesize));
			end_page = total_size;
		if(curpage=='-1')
			end_page = total_size;
		$("#_CurrPage_").html(curpage+1);
		$("#TotalPages").html(total_page);
		$("#_CurrPage_").attr("curpage",curpage);
		var tb_tr_tpl = $("TBODY #show tr:first").hide();
		var $table= $("#tbl");
		$table.find("tr:gt(1)").remove();
		var tb_tr_td_html = "";
//		alert("start = "+start+" end_page = "+end_page);
		$("#xmldso").find("GwListInfo").each(function(i){
			if(start<=i && i<end_page){
				var tb_tr_tpl_temp = tb_tr_tpl.clone();
				$(this).children().each(function() {  
					var _tagName =this.tagName;  
					var _tagValue = $(this).text();
					
					tb_tr_tpl_temp.find("td input").each(function(){
						if($(this).attr("DATAFLD").toLowerCase()==_tagName.toLowerCase()){
							$(this).val(_tagValue);
						}
					});
					tb_tr_tpl_temp.find("td SPAN").each(function(){
						if($(this).attr("DATAFLD").toLowerCase()==_tagName.toLowerCase()){
							$(this).append(_tagValue);		
						}
					});
					tb_tr_tpl_temp.find("td a").each(function(){
						if($(this).attr("DATAFLD").toLowerCase()==_tagName.toLowerCase()){
							$(this).attr("href",_tagValue);
						}
					});
				});
				if(tb_tr_td_html==""){
					tb_tr_td_html =tb_tr_tpl_temp.html();
				}
				else{
					tb_tr_td_html = "<tr>"+tb_tr_td_html+"</tr>"+"<tr>"+tb_tr_tpl_temp.html()+"</tr>";
				}
			}
			
		});
		if(tb_tr_td_html!="")
			$table.append("<tr>"+tb_tr_td_html+"</tr>");
	},
	
	//��һҳ
	showTop:function(){
		var curpage = $("#_CurrPage_").attr("curpage");
		if(curpage!=0){
			hd.pagewapper(0, 2);
		}
	},
	
	//��һҳ
	showPrev:function(){
		var curpage = $("#_CurrPage_").attr("curpage");
		if(curpage>0)
			hd.pagewapper(parseInt(curpage)-1, 2);
	},
	
	//��һҳ
	showNext:function(){
		var curpage = $("#_CurrPage_").attr("curpage");
		hd.pagewapper(parseInt(curpage)+1, 2);
	},
	
	//���һҳ
	showLast:function(){
		//var curpage = $("#_CurrPage_").attr("curpage");
		hd.pagewapper(-1, 2);
	}
	
	
	
};
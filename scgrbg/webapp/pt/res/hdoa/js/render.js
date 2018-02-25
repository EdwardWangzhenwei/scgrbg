//0为已完成；1为进行中；2为未处理
var img={"0":"status0","1":"status1","2":"status2"};
var ImageRender={
		render:function(v,r,c,i,t){
			if(v==null){
				v=2;
			}
			return "<div style='height:20px;width:20px;'  class='"+img[v]+"'>&nbsp;<div>";
		}
}

//密级的状态
var security = {"10":"无","20":"秘密","30":"机密"};
var StRender = {
		render:function(v,r,c,i,t){
			if(!v){
				v=1;
			}
			return security[v];
		}
};

var FjRender={
		render:function(v, r, c, i, t){
			var res=PT.ns('queryfj',null,'tz_id='+v);
			if(res&&res.length>0){
				var str='';
				for(var i=0;i<res.length;i++){
					str+='<span id="btn_span" class="bsw-c" style="height:27px;width:auto;'
						+'margin-left:25px;">'
						+'<a name=\"tm1\" href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id='
						+res[i].dzwj_id+'\">'+res[i].dzwjms+'</a></span>&nbsp;';
				}
				return str;
				}
		}
		
};

var ARender = {
		render : function(v, r, c, i, t) {
			return "<a href=\"pt/res/hdoa/wpsedit/ShowDzwj.jsp?dzwj_id="
					+ r.dzwj_id + '\">' + v + "</a>";
		}
	};
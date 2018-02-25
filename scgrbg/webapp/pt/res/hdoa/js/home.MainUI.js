function addIframeOnload($, _) {
	if ($.attachEvent)
		$.attachEvent("onload", _);
	else
		$.onload = _
}
function delIframeOnload($, _) {
	if ($.detachEvent)
		$.detachEvent("onload", _);
	else
		$.onload = null
}
var MainUI = function($) {
	if ($)
		for ( var _ in $)
			this[_] = $[_]
};
MainUI.prototype.init = function() {
	var $ = function(A) {
		if (!A) {
			this.redirect();
			return
		}
		for ( var _ in A)
			mainUI.basicInfo[_] = A[_];
		this.initCustomParam();
		if (mainUI.basicInfo.hintInfo && mainUI.basicInfo.hintInfo != null
				&& mainUI.basicInfo.hintInfo != "")
			document.getElementById("northInfo").innerHTML = genHtml(
					mainUI.basicInfo.hintInfo, A);
		else
			document.getElementById("info").style.display = "none";
		if (mainUI.basicInfo.kpInfo) {
			var $ = document.createElement("script");
			$.src = mainUI.basicInfo.kpInfo;
			document.getElementsByTagName("head")[0].appendChild($)
		}
		this.tabOpenerMap = {};
		this.northInit();
		this.initPageChecker();
		document.title = this.basicInfo.projectTitle
	};
	this.header = document.getElementById(this.header);
	this.main = document.getElementById(this.main);
	ajax("aos_mainui_home", {
		_func : "initData",
		randomValue : Math.random()
	}, $, this)
};
MainUI.prototype.initCustomParam = function() {
	if (window.homePageFirst)
		mainUI.homePageFirst = window.homePageFirst;
	if (window.homePagePath)
		mainUI.homePagePath = window.homePagePath;
	if (window.logoPath)mainUI.logoPath=window.logoPath;
	if(window.welcomePath)
		mainUI.welcomePath=window.welcomePath;
	if(window.funcIcons)mainUI.funcIcons=window.funcIcons;
	if(window.maxTabNum)mainUI.maxNum=window.maxTabNum};
MainUI.prototype.showDefaultIcons=function(){
	var F=[],D=typeof(mainUI.funcIcons)=="function"?mainUI.funcIcons():mainUI.funcIcons,E;
	if(D!=null&&D.length>0)for(var G=0;G<D.length;G++)
		if(D[G].name=="xtxx"){MainUI.noticeNum=0;
		var $=IS_IE6?"notice noticeie6 notice_off":"notice notice_off";
		F.push("<div id=\"noticeIcon\"><div id=\"reminder\" class=\""+$+"\" title=\"\u7cfb\u7edf\u6d88\u606f\"><i style=\"display: block; top: 0px; \"><u><b>&nbsp;</b><var id=\"number\">0</var></u><em>&nbsp;</em></i></div></div>")}
		else{$=IS_IE6?D[G].name+" "+D[G].name+"ie6":D[G].name;if(D[G].name=="home")
			E="<div class=\"btnFrame\"><div class=\""+$+"\" title=\""+D[G].title+"\"></div></div>";
		else F.push("<div class=\"btnFrame\"><div class=\""+$+"\" title=\""+D[G].title+"\"></div></div>")}
	if(E)F.push(E);var A=document.getElementById("iconBtns");
	A.innerHTML=F.join("");var C=A.childNodes;for(G=0;G<C.length;G++)
	{var _=C[G],B=_.childNodes[0].className.split(" ")[0];_.onclick=mainUI["btn_"+B];
	_.onmouseover=function(){this.className+=(" "+"Hover")};
	_.onmouseout=function(){this.className=this.className.split(" ")[0]}}};
	MainUI.prototype.linkHomeCss=function(){var _=getCookie("aos_theme");
	if(_==null)_=mainUI.basicInfo["defaultTheme"];var $=new RegExp("{theme}","ig"),
	C=new RegExp("%7Btheme%7D","ig"),
	A=document.getElementsByTagName("LINK");
	for(var D=0;D<A.length;D++){var B=A[D].href;if(B.indexOf("{theme}")!=-1)
		A[D].href=B.replace($,_);else if(B.indexOf("%7Btheme%7D")!=-1)
			A[D].href=B.replace(C,_)}};
			MainUI.prototype.resizeTabPanel=function(){if(mainUI.isTabMax)
			{mainUI.header.style.display="none";mainUI.main.style.paddingTop=0}
			else{mainUI.header.style.display="";mainUI.main.style.paddingTop=mainUI.headHeight}
			var $=document.body.offsetWidth,_=document.body.offsetHeight-(mainUI.isTabMax?0:mainUI.headHeight);this.tabPanel.setSize($,_)};MainUI.prototype.northInit=function(){this.linkHomeCss();if(window.logoPath||window.getLogoPath){var F=window.logoPath?window.logoPath:window.getLogoPath(),B=document.getElementById("logo");if(IS_IE6)B.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+F+"',sizingMethod='scale')";else B.style.background="url("+F+") no-repeat top left"}var C=[{id:"aosnav",text:"\u529f\u80fd\u5bfc\u822a",width:100,icon:"../images/tab/tab_icon.gif",url:(window.navPage&&window.navPage!="")?window.navPage:mainUI.navPage}],A={id:"aosnavblank",width:13,disabled:true},_=mainUI.basicInfo["mypageinfo"];if(_!=null&&!_.behind)C.push(_);C.push(A);if(_!=null&&_.behind)C.push(_);mainUI.defaultTabs=C;var D=0;for(var H=0;H<C.length;H++)if(C[H].selected){D=H;break}var G=function(){mainUI.isTabMax=!mainUI.isTabMax;mainUI.resizeTabPanel()},E=function($){if(!$)return;$.selectVersion=this.selectVersion++},$=function(_,$){};this.tabPanel=new TabPanel({id:"tabpanel",border:false,selectIndex:D,selectVersion:0,onSelect:E,onTabDblClick:G,onTabClose:$,tabs:this.defaultTabs});this.showDefaultIcons();if(window.showMyIcons)showMyIcons()};MainUI.prototype.getDefaultTabNum=function(){if(this.defaultTabNum)return this.defaultTabNum;var $=mainUI.defaultTabs,_=0;for(var A=0;A<$.length;A++){_++;if($[A].id=="aosnavblank")break}this.defaultTabNum=_;return _};MainUI.prototype.openNewTab=function(B){var A=B["link"],$=B["title"];if($){$=$.replace("<","\uff1c");$=$.replace(">","\uff1e")}if(A){A=(A.indexOf("http://")!=-1)||(A.indexOf("https://")!=-1)?A:_webRoot+A;B.link=A}if(B.callback&&B.callback.indexOf("modalWin")>-1){var C=B.link;if(C.indexOf("?")>-1)C+="&";else C+="?";C=C+"pt_fitSize=true";var _="status:no;center:yes;scroll:auto;resizable:yes;help:no;;dialogWidth:1px;dialogHeight:1px;";window.showModalDialog(C,window,_);return}this.openTabWin({id:B.winName,text:$,url:A,closed:true,limitPanel:B.limitPanel,callback:B.callback})};MainUI.prototype.openTabWin=function(C){var B=this.tabPanel;if(!C.text&&!C.id)return;if(!C.text)C.text=C.id;if(!C.id)C.id="autoID"+(TabPanel.ID++);var _=C.id,$=B.getSelectedTab()?B.getSelectedTab().id:null,A;if(B.hasTab(_)){A=B.tabMap[C.id];this.switchTabContent(C,$,A)}else if(C.limitPanel!=false&&(B.tabItems.length>=this.maxNum+this.getDefaultTabNum())){if(this.loopOpenTab){A=this.tabPanel.getOldestTab();this.switchTabContent(C,$,A)}else alert("\u5df2\u7ecf\u8fbe\u5230\u7a97\u53e3\u6700\u5927\u6570\u91cf,\u8bf7\u5173\u95ed\u4e00\u4e9b\u7a97\u53e3\u518d\u8bd5\u3002")}else{this.tabOpenerMap[_]=$;A=B.addTab(C,true);A.panel.contentWindow.name=C.id;this.newTabContent(A,C.callback,$)}};MainUI.prototype.switchTabContent=function(H,_,B){var F=this.tabPanel,E=B.id,$=B.panel,D=H.url.handleUrl(),A=H.callback,C=this,G=function(){try{try{$.contentWindow}catch(I){return}$.contentWindow.name=H.id;if($.contentWindow.location.href=="about:blank"){if(_)$.contentWindow._tabOpener=_;for(var J in H)B[J]=H[J];C.tabOpenerMap[H.id]=_;if(H.id!==E){delete C.tabOpenerMap[E];$.setAttribute("id",B.id);F.tabMap[B.id]=F.tabMap[E];delete F.tabMap[E];F.changeTabTitle(B.id,H.text)}}else{if(_)$.contentWindow._tabOpener=_;if(A)$.contentWindow.eval(A);delIframeOnload($,G);try{$.contentWindow.document.body.focus()}catch(D){}}}catch(I){alert("\u9875\u9762\u521d\u59cb\u5316\u9519\u8bef!\n\u8bf7\u91cd\u8bd5!\n")}};F.select(B.id);addIframeOnload($,G);$.src="about:blank";setTimeout(function(){if($.contentWindow.location.href=="about:blank")$.src=D;else delIframeOnload($,G)},10)};MainUI.prototype.newTabContent=function(B,A,_){var $=B.panel,C=function(){try{if(_)$.contentWindow._tabOpener=_}catch(D){return}try{if(A)$.contentWindow.eval(A);delIframeOnload($,C)}catch(D){alert("\u9875\u9762\u521d\u59cb\u5316\u9519\u8bef!\n\u8bf7\u91cd\u8bd5!\n")}try{$.contentWindow.document.body.focus()}catch(B){}};addIframeOnload($,C)};MainUI.prototype.refresh=function(){var $=window.frames[0];$.tree1SelectNode=null;$.graphConfig=null;$.tree1.reloadRoot(function(){$.PT.f().reloadTree()});$.spanel.refresh()};MainUI.prototype.getActivePages=function(){var B=this.tabPanel.tabItems,$=[],_,A;for(var C=0;C<B.length;C++){_=B[C];if(_.disabled)continue;A=_.panel?_.panel.contentWindow:null;if(A==null)continue;$[$.length]=A.pt_theformid?A.pt_theformid:A._rescId}B=panel=_=A=null;return $};MainUI.prototype.pageCheckerHandler=function(re){if(re&&re.noticenum!=null)this.setNotice(re.noticenum);if(re&&re.pageTimerCallback!=null)eval(re.pageTimerCallback)};MainUI.prototype.setNotice=function(A){var B=document.getElementById("reminder");if(!B)return;var _=document.getElementById("number");if(A<=0){var $=IS_IE6?"notice noticeie6 notice_off":"notice notice_off";B.className=$;this.clear_flash_title()}else{$=IS_IE6?"notice noticeie6 notice_on notice_onie6":"notice notice_on";B.className=$;_.innerHTML=A>99?"99+":A;MainUI.noticeNum=A;fadeSize(document.getElementById("noticeIcon"),1,{top:{from:0,to:-10}},15,false,function(){fadeSize(document.getElementById("noticeIcon"),1,{top:{from:-10,to:0}},15)});this.flash_title("\u65b0\u6d88\u606f")}};MainUI.prototype.initPageChecker=function(){if(this.pageCheckerProperties.enable){var $=this,_=function(){var _=$.getActivePages();ajax("aos_mainui_home",{_func:"timer",_checkNotice:$.iNotice,_pages:_},$.pageCheckerHandler,$)};setTimeout(_,800);this.activePageChecker=setInterval(_,2==1?15000:this.pageCheckerProperties.interval)}};MainUI.prototype.flash_title=function($){if(this.inFlash==true)return;if(this.inFlash===undefined||this.inFlash===false)this.inFlash=true;var _=0;if(!this.oldTitle)this.oldTitle=document.title;this.flashTitleHandler=setInterval(function(){_++;if(_==3)_=1;if(_==1)document.title="\u3010"+$+"\u3011";if(_==2)document.title=mainUI.oldTitle},1000)};MainUI.prototype.clear_flash_title=function(){if(!this.oldTitle)return;this.inFlash=false;document.title=this.oldTitle;clearInterval(this.flashTitleHandler)};MainUI.prototype.btn_orgn=function(){var $=PT.showModalForm("aos_mainui_switch_orgn");if($&&$.orgnId&&mainUI.tabPanel)mainUI.tabPanel.removeAll(function(){var _=PT.submit("aos_mainui_home",null,{_func:"changeOrgn",orgnId:$.orgnId,randomValue:Math.random()});if("true"==_){window.switchingOrgn=true;window.location.replace(window.location.href)}})};MainUI.prototype.btn_help=function(){mainUI.openNewTab({winName:"\u5e2e\u52a9",link:"help/default.htm",limitPanel:true})};MainUI.prototype.btn_quit=function(){mainUI.logout()};MainUI.prototype.btn_notice=function(){mainUI.openNewTab({winName:"\u6d88\u606f\u5217\u8868",link:"pt/canvas?formid=aos_mainui_notice_list",limitPanel:true})};MainUI.prototype.btn_pwd=function(){PT.showModalForm("aos_mainui_personal_set",null,"420px","260px",null)};MainUI.prototype.btn_home=function(){mainUI.tabPanel.select(0);window.frames[0].homePage()};MainUI.prototype.redirect=function(){var $=function(){var B=window.document.URL,A=new RegExp("fromForm"+"=\\w*","ig"),_=B.match(A);if(_!=null){var $=_[0].split("=");if($[1]!=null)return $[1];else return""}else return""},_=$();if(_==""||_==null){window.opener=null;window.open("","_self");window.close();return}if(mainUI.tabPanel)mainUI.tabPanel.removeAll(function(){ajax("aos_mainui_home",{_func:"logout"},null,mainUI);setTimeout(function($){window.location=mainUI.LoginPath+_},500)});else window.location=this.LoginPath+_};MainUI.prototype.closeTab=function($){this.tabPanel.removeTab($||this.tabPanel.selectIndex)};MainUI.prototype.getOpenerTabWindow=function(_){var $=_||top.window.mainUI.tabOpenerMap[top.window.mainUI.tabPanel.getSelectedTab().id];if(!$)return null;return this.tabPanel.getIframeWindow($)};MainUI.prototype.setTabTitle=function(_,$){this.tabPanel.changeTabTitle(_||this.tabPanel.selectIndex,$)};MainUI.prototype.collapsePanel=function($){};MainUI.prototype.logout=function(){if(confirm("\u786e\u5b9a\u8981\u6ce8\u9500\u5f53\u524d\u7528\u6237\u5417?"))this.redirect()};MainUI.prototype.setTheme=function($){document.getElementById("themeCSS").href="../theme/"+$+"/css/home.css";var A=mainUI.tabPanel.tabItems;for(var _=0;_<A.length;_++)if(A[_].panel&&A[_].panel.contentWindow&&A[_].panel.contentWindow.relinkCss)A[_].panel.contentWindow.relinkCss()}
 var TabItem=function(E){this.disabled=false;this.width=125;for(var B in E)this[B]=E[B];var $=this.headDom=document.createElement("div");$.style.width=this.width;$.className="tpn-tab"+(this.disabled?" t-dsb":"");if(this.text){$.innerHTML="<div class='tpn-tab-l tpn-tab-s' unselectable=on><div class='tpn-tab-r tpn-tab-s' unselectable=on><div class=tpn-tab-c unselectable=on><div class=tab-closed unselectable=on></div><div class=tab-head><nobr><img align='absmiddle'/><span unselectable=on></span></nobr></div></div></div></div>";var A=$.lastChild.lastChild.lastChild;if(!this.closed)A.firstChild.style.display="none";var D=A.lastChild.lastChild.lastChild;D.innerHTML=D.title=this.text;var _=A.lastChild.lastChild.firstChild;if(this.icon===false)_.style.display="none";else _.src=this.icon||TabPanel.imagePath+"t-icon.gif"}if(!this.id)this.id="autoID"+(TabPanel.ID++);if(this.panel)this.panel=document.getElementById(this.panel);else if(this.url){var C=this.panel=document.createElement("iframe");C.width="100%";C.height="100%";C.frameBorder=C.border=0;C.id=this.id;if(!this.asyn){this.ready=true;C.src=this.url.handleUrl()}}},TabPanel=function(_){this.selectIndex=0;this.tabItems=[];this.tabMap={};this.headTabsWidth=17;for(var $ in _)this[$]=_[$];this.init()};TabPanel.prototype.init=function(){this.dom=document.getElementById(this.id);this.tabStrip=document.createElement("div");this.tabStrip.innerHTML="<div class=tpn-btn-closeall unselectable=on></div><div class=tpn-btn unselectable=on style='background-position:17px 0'></div><div class=tpn-btn unselectable=on></div><div class='tpn-s-body' unselectable=on><div></div></div>";var _=this.tabStrip.childNodes;this.closeAllBtn=_[0];this.rbtn=_[1];this.lbtn=_[2];this.tabWrap=_[3];this.tabBody=this.tabWrap.firstChild;this.tabBody.style.width="10px";this.tabPanel=document.createElement("div");this.tabPanel.className="tab-panel";this.dom.appendChild(this.tabPanel);var B=this;this.closeAllBtn.onclick=function(){B.removeAll()};this.rbtn.onclick=function(){B.moveStrip(125,1)};this.lbtn.onclick=function(){B.moveStrip(125,-1)};if(this.border!=false)this.dom.className="tpn-border";this.setMode(this.mode);this.setSize(this.width,this.height);if(this.tabs){for(var C=0;C<this.tabs.length;C++){var A=this.addTab(this.tabs[C]);try{A.panel.contentWindow.name=this.tabs[C].id}catch($){}}this.select(this.selectIndex,true)}};TabPanel.prototype.setSize=function($,_){if(!$)$=this.dom.offsetWidth;if(!_)_=this.dom.offsetHeight;this.dom.style.height=_;this.tpWidth=this.dom.style.width=$;if(!IS_IE)this.tpHeight=this.tabPanel.style.height=_-63;else this.tpHeight=this.tabPanel.style.height=_-27;this.doStrip()};TabPanel.prototype.setMode=function(_){var $="top";if(_=="btm"){this.dom.appendChild(this.tabStrip);this.tabStrip.className="tpn-s-b";$="btm"}else{this.dom.insertBefore(this.tabStrip,this.tabPanel);this.tabStrip.className="tpn-s-t"}if(IS_IE)this.tabStrip.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale,src='"+TabPanel.imagePath+"tab_strip_"+$+".gif')";this.doStrip()};TabPanel.prototype.addTab=function(B,_){var D=this,C=new TabItem(B),$=C.headDom,A=this.tabItems.length;$.index=A;$.onclick=function($){!$&&($=event);var _=$.srcElement||$.target;I=this.index;if(D.tabItems[I].disabled)return;if(_.className.indexOf("tab-closed")>=0)D.removeTab(I);else D.select(I)};$.onmouseover=function($){!$&&($=event);if(IS_IE)$.cancelBubble=true;else $.stopPropagation();var A=$.srcElement||$.target;I=this.index;if(D.tabItems[I].disabled)return;var _;if(A.className.indexOf("tpn-tab-c")>=0){_=A.firstChild;_.className+=" tab-closed-show"}if(A.className.indexOf("tab-head")>=0){_=A.parentNode.firstChild;_.className+=" tab-closed-show"}if(A.tagName.toLowerCase()=="img"||A.tagName.toLowerCase()=="span"){_=A.parentNode.parentNode.parentNode.firstChild;_.className+=" tab-closed-show"}if(A.className.indexOf("tab-closed")>=0)A.className+=" tab-closed-show tab-closed-active"};$.onmouseout=function($){!$&&($=event);if(IS_IE)$.cancelBubble=true;else $.stopPropagation();var _=$.srcElement||$.target;I=this.index;if(D.tabItems[I].disabled)return;if(_.className.indexOf("tpn-tab-c")>=0){closeIcon=_.firstChild;this.className=="tpn-tab-focus"?closeIcon.className="tab-closed tab-closed-show":closeIcon.className="tab-closed"}if(_.className.indexOf("tab-head")>=0){closeIcon=_.parentNode.firstChild;this.className=="tpn-tab-focus"?closeIcon.className="tab-closed tab-closed-show":closeIcon.className="tab-closed"}if(_.tagName.toLowerCase()=="img"||_.tagName.toLowerCase()=="span"){closeIcon=_.parentNode.parentNode.parentNode.firstChild;this.className=="tpn-tab-focus"?closeIcon.className="tab-closed tab-closed-show":closeIcon.className="tab-closed"}if(_.className.indexOf("tab-closed")>=0)this.className=="tpn-tab-focus"?closeIcon.className="tab-closed tab-closed-show":closeIcon.className="tab-closed"};if(this.onTabDblClick)$.ondblclick=function(){D.onTabDblClick(D,C)};this.headTabsWidth+=parseInt(parseInt(C.width)+2);this.tabBody.style.width=this.headTabsWidth;this.tabBody.appendChild($);if(!C.id)C.id="autoID"+(TabPanel.ID++);if(C.panel){this.tabPanel.appendChild(C.panel);C.panel.style.display="none";C.panel.setAttribute("id",C.id)}this.tabItems[A]=C;this.tabMap[C.id]=C;this.doStrip();if(_)this.select(A);return C};TabPanel.prototype.select=function(B,_){if(typeof B=="string")B=this.getIndexById(B);if(B<0||B>=this.tabItems.length||this.tabItems[B].disabled)return false;if(!_&&this.selectIndex==B)return;var A;if(this.selectIndex>-1){A=this.tabItems[this.selectIndex];try{A.headDom.className="tpn-tab";A.headDom.firstChild.firstChild.firstChild.firstChild.className="tab-closed"}catch($){}if(A.panel)A.panel.style.display="none"}this.selectIndex=B;A=this.tabItems[B];A.headDom.className="tpn-tab-focus";A.headDom.firstChild.firstChild.firstChild.firstChild.className+=" tab-closed-show";if(this.onSelect)this.onSelect(A);if(A.panel)A.panel.style.display="";if(A.url&&A.asyn&&!A.ready){A.ready=true;A.panel.src=A.url.handleUrl();A.panel.id=A.id;try{A.panel.focus()}catch($){}}setTimeout(function(){try{if(A.panel&&A.panel.contentWindow.document.body)A.panel.contentWindow.document.body.focus()}catch($){}},500)};TabPanel.prototype.disable=function(_,A){if(typeof _=="string")_=this.getIndexById(_);if(_<0||_>=this.tabItems.length)return false;var $=this.tabItems[_];$.disabled=A;$.headDom.className=A?"tpn-tab t-dsb":"tpn-tab";if(this.selectIndex==_)this.selectIndex=-1};TabPanel.prototype.getIframeWindow=function($){return this.tabMap[$].panel.contentWindow};TabPanel.prototype.changeTabTitle=function(B,$){if(typeof B=="string")B=(this.getIndexById(B)||this.getIndexByName(B));if(B<0||B>=this.tabItems.length)return;var A=this.tabItems[B],_=A.headDom.lastChild.lastChild.lastChild.lastChild.lastChild.lastChild;A.title=_.innerHTML=_.title=$};TabPanel.prototype.removeTab=function(F,$){if(typeof F=="string")F=(this.getIndexById(F)||this.getIndexByName(F));if(F<0||F>=this.tabItems.length){if($)$.apply(this,[false]);return}var B=this.tabItems[F];if(!B.closed){if($)$.apply(this,[false]);return}var G=B.id;if(this.onTabClose)this.onTabClose(this,B);var A=mainUI.tabOpenerMap[G];if(B.url){var C=this,_=B.panel,D=false,E=function(){D=true;C.tabBody.removeChild(B.headDom);if(_)C.tabPanel.removeChild(_);C.headTabsWidth-=B.width+2;C.tabBody.style.width=C.headTabsWidth;delete C.tabMap[B.id];for(var E in B)B[E]=null;C.tabItems.splice(F,1);delete mainUI.tabOpenerMap[G];C.orderIndex();if(C.selectIndex==F){C.selectIndex=-1;if(A&&C.getIndexById(A)>-1&&A!="autoID0")C.select(A);else if(C.tabItems.length>F)C.select(F);else if((F)>C.tabs.length)C.select(F-1);else C.select(0)}else if(C.selectIndex>F)C.selectIndex--;C.doStrip();if($)$.apply(C,[true])};addIframeOnload(_,E);_.src="about:blank"}};TabPanel.prototype.orderIndex=function(){for(var $=0;$<this.tabItems.length;$++)this.tabItems[$].headDom.index=$};TabPanel.prototype.removeAll=function($){window.tempIndex=this.tabItems.length-1;var _=function(A){this.removeTab(A,function(B){if(A>=mainUI.getDefaultTabNum()&&!B){if(this.tabItems.length>0)this.select(A);else this.selectIndex=-1;return}if(window.tempIndex>=0)_.call(this,--window.tempIndex);else{this.doStrip();if(this.tabItems.length>0)this.select(0);else this.selectIndex=-1;if($)$.call(this)}})};_.call(this,tempIndex)};TabPanel.prototype.removeOther=function(){var $=this.getSelectedTab();for(var _=this.tabItems.length;_>=0;_--)if(this.tabItems[_]!=$)this.removeTab(_);this.doStrip();if(this.tabItems.length>0)this.select($.id);else this.selectIndex=-1};TabPanel.prototype.getSelectedTab=function(){for(var _=this.tabItems.length;_>=0;_--){var $=this.tabItems[_];if($&&$.headDom&&$.headDom.className=="tpn-tab-focus")return $}if(this.selectIndex<0)return null;return this.tabItems[this.selectIndex]};TabPanel.prototype.getIndexById=function($){for(var _=0;_<this.tabItems.length;_++)if(this.tabItems[_].id==$)return _;return-1};TabPanel.prototype.getIndexByIdOrText=function($){for(var _=0;_<this.tabItems.length;_++)if(this.tabItems[_].id==$||this.tabItems[_].text==$)return _;return-1};TabPanel.prototype.getIndexByName=function($){for(var _=0;_<this.tabItems.length;_++)if(this.tabItems[_].name==$)return _;return-1};TabPanel.prototype.hasTab=function($){for(var _=0;_<this.tabItems.length;_++)if(this.tabItems[_].id==$||this.tabItems[_].text==$)return true;return false};TabPanel.prototype.getTab=function($){if(typeof $=="string")$=this.getIndexById($);return this.tabItems[$]};TabPanel.prototype.getOldestTab=function(){var A=this.selectVersion,$=null;for(var B=0;B<this.tabItems.length;B++){var _=this.tabItems[B];if(!_.closed)continue;if(_.selectVersion<A){A=_.selectVersion;$=_}}return $};TabPanel.prototype.moveStrip=function(_,$){var C,A=this,B=function(){_-=8;C=_;if(C>8)C=8;A.tabWrap.scrollLeft+=C*$;if(C<8)A.doStrip(A.headTabsWidth);else setTimeout(B,1)};B()};TabPanel.prototype.doStrip=function(){this.isLMove=this.tabWrap.scrollLeft!=0;this.isRMove=this.headTabsWidth-this.tabWrap.scrollLeft+45>this.tpWidth;if(this.isLMove||this.isRMove){this.lbtn.style.backgroundPosition=this.isLMove?"0px 17px":"0px 0px";this.rbtn.style.backgroundPosition=this.isRMove?"17px 17px":"17px 0px";this.lbtn.style.display="";this.rbtn.style.display=""}else{this.lbtn.style.display="none";this.rbtn.style.display="none"}};TabPanel.ID=0;TabPanel.imagePath="../images/tab/"
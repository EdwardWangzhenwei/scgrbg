 var subItemClick=function($){alert($.text+" "+$.id)},VTabItem=function(E,D){this.disabled=false;this.tabPanel=D;for(var B in E)this[B]=E[B];var _=this.headDom=document.createElement("div");_.className="Tab";var C;if(IS_IE6&&this.icon&&this.icon.toLowerCase().indexOf(".png")>0)C="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=\"true\", sizingMethod=\"crop\", src="+(VTabPanel.oldImagePath+this.icon)+");";else C="background: url(\""+(this.icon?(VTabPanel.oldImagePath+this.icon):(VTabPanel.imagePath+"t-icon.gif"))+"\")";if(this.tabPanel.hasIcon)_.innerHTML="<DIV class=TabHead></DIV><DIV class=TabIcon><DIV id='iconDiv' style='"+C+"'></DIV></DIV><DIV class=TabText>"+(this.text||"\u65b0\u9875\u7b7e")+"</DIV><DIV class=TabTail></DIV>";else _.innerHTML="<DIV class=TabHead></DIV><DIV class=TabText>"+(this.text||"\u65b0\u9875\u7b7e")+"</DIV><DIV class=TabTail></DIV>";_.onmouseover=function($){if(D.selectIndex==this.index)return;this.className="Tab"+(this.isCurrent?" TabCurrent":"")+" TabMouseEnter"};_.onmouseout=function($){if(D.selectIndex==this.index)return;this.className="Tab"+(this.isCurrent?" TabCurrent":"")};var $=this.panel=document.createElement("div");$.className="TabStripPanel";var A=this.children;for(var F=0;F<A.length;F++)this.addItem(A[F],F)};VTabItem.prototype.addItem=function(C,A){var B=document.createElement("div");B.className="VTabStripSubItem";B.style.top=(10+A*42)+"px";var $=VTabPanel.oldImagePath+C.icon;if(!C.icon)$=VTabPanel.oldImagePath+VTabPanel.defaultIcon;B.innerHTML="<span style=\"background: url('"+$+"') no-repeat\">"+C.text+"</span>";var _=this.tabPanel,D=this;B.onclick=function(){if(this==_.selectedItem)return;if(_.selectedItem)_.selectedItem.className="VTabStripSubItem";this.className="VTabStripSubItem VTabStripSubItemSelected";if(_.onclick)_.onclick(C,_);_.selectedItemData=C;_.selectedItem=this;D.selectedItemIdx=A};this.panel.appendChild(B)};var VTabPanel=function(_){this.selectIndex=-1;this.tabItems=[];for(var $ in _)this[$]=_[$];this.init()};VTabPanel.prototype.init=function(){var A=document.getElementById(this.id),$=this.dom=document.createElement("div");A.appendChild($);$.className="VTabStrip";$.innerHTML="<TABLE class=TabStripTable cellSpacing=0 cellPadding=0><TBODY><TR><TD class=TabStripTabArea vAlign=top></TD><TD class=TabStripPanelArea></TD></TR></TBODY></TABLE>";var _=$.firstChild.rows[0].cells;this.tabHead=_[0];this.tabBody=_[1];if(this.tabs){for(var B=0;B<this.tabs.length;B++)this.addTab(this.tabs[B]);this.select(0,true)}};VTabPanel.prototype.addTab=function(B,_){var D=this,C=new VTabItem(B,this),$=C.headDom,A=this.tabItems.length;$.index=A;$.onclick=function($){I=this.index;if(D.tabItems[I].disabled)return;D.select(I,true)};this.tabHead.appendChild($);this.tabBody.appendChild(C.panel);if(!C.id)C.id="autoID"+(VTabPanel.ID++);this.tabItems[A]=C;if(_)this.select(A)};VTabPanel.prototype.select=function(C,A){if(typeof C=="string")C=this.getIndexById(C);if(C<0||C>=this.tabItems.length||this.tabItems[C].disabled)return false;if(this.selectIndex==C)return;var B;if(this.selectIndex>-1){B=this.tabItems[this.selectIndex];B.headDom.style.zIndex=0;B.headDom.className="Tab";B.headDom.isCurrent=0;var _=B.headDom.childNodes[1];if(!A)tooglePic(_.firstChild,false);if(B.panel)B.panel.style.display="none"}this.selectIndex=C;B=this.tabItems[C];B.headDom.className="Tab TabCurrent";B.headDom.style.zIndex=1;B.headDom.isCurrent=1;tooglePic(B.headDom.childNodes[1].firstChild,true);if(B.panel){B.panel.style.display="inline";if(!B.selectedItemIdx)B.selectedItemIdx=0;var $=B.panel.childNodes[B.selectedItemIdx];if($&&$.onclick)$.onclick()}if(this.onSelect)this.onSelect(B)};VTabPanel.prototype.getIndexById=function($){for(var _=0;_<this.tabItems.length;_++)if(this.tabItems[_].id==$)return _;return-1};var tooglePic=function(_,$){if(!_||!_.style||!_.style.background)return;if(_.style.background){var A=_.style.background;A=$?A.replace("01.","02."):A.replace("02.","01.");_.style.background=A}else if(_.style.filter){A=_.style.filter;A=$?A.replace("01.","02."):A.replace("02.","01.");_.style.filter=A}};VTabPanel.ID=0;VTabPanel.imagePath="res/mainui/images/nav";VTabPanel.oldImagePath="mainui/a8/icon/";VTabPanel.defaultIcon=VTabPanel.imagePath+"item.gif"
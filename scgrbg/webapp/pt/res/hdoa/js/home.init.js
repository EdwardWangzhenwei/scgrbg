init = {
	maxNum : 6,
	iNotice : true,
	hasLoginpage : true,
	navPage : "pt/canvas?formid=supervision_main_nav",
	LoginPath : "../../../canvas?formid=",
	headHeight : 40,
	isTabMax : false,
	loopOpenTab : true,
	tabMap : {},
	header : "header",
	main : "main",
	homePageFirst : false,
	homePagePath : false,
	funcIcons : [ {
		name : "quit",
		title : "\u9000\u51fa\u7cfb\u7edf"
	}, {
		name : "help",
		title : "\u7cfb\u7edf\u5e2e\u52a9"
	}, {
		name : "pwd",
		title : "\u4fee\u6539\u5bc6\u7801"
	} ],
	pageCheckerProperties : {
		enable : true,
		interval : 60000
	},
	basicInfo : {}
};
window.mainUI = new MainUI(init);
document
		.write("<link type='text/css' href='../css/custom.css' rel='stylesheet'/>");
document.write("<script charset='UTF-8' src='../js/custom.js'></script>");
window.onload = function() {
	mainUI.init();
	var $ = getCookie("maxTabNum");
	if ($)
		window.maxTabNum = parseInt($)
};
window.onresize = function() {
	mainUI.resizeTabPanel()
};
window.logoutWhenUnload = function() {
	if (window.switchingOrgn || !mainUI.hasLoginpage)
		return;
	if (window.closeCard)
		closeCard();
	PT.submit("supervision_main_home", null, {
		_func : "logout",
		randomValue : Math.random()
	}, function($) {
	}, function($) {
	}, this)
};
if (window.attachEvent)
	window.attachEvent("onunload", logoutWhenUnload);
else if (window.addEventListener)
	window.addEventListener("onunload", logoutWhenUnload, false);
ajax = function(A, B, $, _) {
	PT.asySubmit(A || "supervision_main_home", null, B, $, function($) {
	}, _)
};
function genHtml(tpl, M) {
	var evals = [], re = /(?:<%)((\n|\r|.)*?)(?:%>)/ig, ret;
	while ((ret = re.exec(tpl)) != null)
		evals[evals.length] = ret;
	var sb = [ "myFn=function(sArr,fnName,args){var A=[];var B;if(fnName){eval(fnName+'.apply(this,args)');return A.join('')};" ], start = 0, str, evstr, strArr = [];
	for ( var i = 0, L = evals.length; i < L; i++) {
		var ev = evals[i];
		str = tpl.substring(start, ev.index);
		if (str) {
			var len = strArr.length;
			strArr[len] = str;
			sb[sb.length] = "A[A.length]=sArr[" + len + "];"
		}
		evstr = ev[1];
		if (evstr.charAt(0) == "=") {
			sb[sb.length] = "B" + evstr + ";";
			sb[sb.length] = "A[A.length]=B;"
		} else
			sb[sb.length] = "" + evstr;
		start = ev.index + ev[0].length
	}
	str = tpl.substring(start, tpl.length);
	if (str) {
		len = strArr.length;
		strArr[len] = str;
		sb[sb.length] = "A[A.length]=sArr[" + len + "];"
	}
	sb[sb.length] = "return A.join('');}";
	eval(sb.join(""));
	return myFn.call(M, strArr)
}
var fadeSizeTime;
function fadeSize(_, D, C, A, $, B) {
	var E = 0;
	if (_ && _.inAnimation === true)
		return;
	_.inAnimation = true;
	fadeSizeTime = setInterval(function() {
		setSize(_, {
			width : C.width,
			height : C.height,
			left : C.left,
			top : C.top,
			pos : (D > 0 ? E : 100 - E)
		});
		E += 5;
		if (E == 105)
			if ($) {
				E = 0;
				D *= -1
			} else {
				clearFadeSize();
				_.inAnimation = false;
				if (B)
					B.apply()
			}
	}, A)
}
function clearFadeSize() {
	if (fadeSizeTime) {
		clearInterval(fadeSizeTime);
		fadeSizeTime = null
	}
}
function setSize($, _) {
	if (_.width)
		$.style.width = _.width * _.pos / 100 + "px";
	if (_.height)
		$.style.height = _.height * _.pos / 100 + "px";
	if (_.left)
		$.style.left = _.left.from + (_.left.to - _.left.from) * _.pos / 100
				+ "px";
	if (_.top)
		$.style.top = _.top.from + (_.top.to - _.top.from) * _.pos / 100 + "px"
}
function clickMenuNode(C, A) {
	if (!C || typeof (C) != "string") {
		alert("\u6ca1\u6709\u914d\u7f6e\u83dc\u5355id\u8def\u5f84\u6216\u8005\u83dc\u5355id\u8def\u5f84\u4e0d\u662fstring\u7c7b\u578b!");
		return
	}
	var $ = C.split("/");
	if (!$ || $.length > 2) {
		alert("\u83dc\u5355id\u8def\u5f84\u914d\u7f6e\u4e0d\u6b63\u786e\uff0c\u76ee\u524d\u6700\u591a\u652f\u63012\u7ea7\u83dc\u5355!");
		return
	}
	var _ = window.frames[0].tree1, B = _.getNodePath(_.getSelectedNodeID());
	if (!_.getNode($[0])) {
		alert("\u8be5\u83dc\u5355\u4e0d\u5b58\u5728\u6216\u8005\u6ca1\u6709\u6743\u9650\u67e5\u770b\u3002");
		return
	}
	var D = function(_) {
		if (window.frames[0].tree1.getNode($[_])) {
			window.frames[0].tree1.selectNode($[_]);
			if (A == true)
				window.frames[0].homePage()
		} else {
			alert("\u8be5\u83dc\u5355\u4e0d\u5b58\u5728\u6216\u8005\u6ca1\u6709\u6743\u9650\u67e5\u770b\u3002");
			if (B)
				clickMenuNode(B, true)
		}
	};
	if ($.length == 1)
		D(0);
	else if ($.length == 2)
		_.expand($[0], D, 1)
}
window.onhelp = function() {
	return false
};
function mousewheel() {
	if (event.ctrlKey || event.shiftKey)
		event.returnValue = false
}
function keydown() {
	if ((window.event.altKey)
			&& ((window.event.keyCode == 37) || (window.event.keyCode == 39)))
		event.returnValue = false;
	if ((event.keyCode == 116) || (event.ctrlKey && event.keyCode == 82)) {
		event.keyCode = 0;
		event.returnValue = false
	}
	if (event.keyCode == 8 && event.srcElement.type != "password"
			&& event.srcElement.type != "textarea"
			&& event.srcElement.type != "text") {
		event.keyCode = 0;
		event.returnValue = false
	}
	if ((event.ctrlKey) && (event.keyCode == 78))
		event.returnValue = false;
	if ((event.shiftKey) && (event.keyCode == 121))
		event.returnValue = false;
	if (window.event.srcElement.tagName == "A" && window.event.shiftKey)
		window.event.returnValue = false;
	if ((window.event.altKey) && (window.event.keyCode == 115)) {
		window.showModelessDialog("about:blank", "",
				"dialogWidth:0px;dialogheight:0px");
		return false
	}
	if ((event.ctrlKey) && (event.keyCode == 65))
		return false
}
function selectstart() {
	if (event.srcElement.type != "password"
			&& event.srcElement.type != "textarea"
			&& event.srcElement.type != "text")
		return false
}
document.attachEvent("onkeydown", keydown);
document.attachEvent("onselectstart", selectstart);
document.attachEvent("onmousewheel", mousewheel)
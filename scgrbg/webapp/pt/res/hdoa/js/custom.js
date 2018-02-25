var A = null;
try {
	A = new ActiveXObject("Msxml2.XMLHTTP")
} catch (e) {
	try {
		A = new ActiveXObject("Microsoft.XMLHTTP")
	} catch (oc) {
		A = null
	}
}
if (!A && typeof XMLHttpRequest != "undefined")
	A = new XMLHttpRequest();
var xmlhttp = A;
xmlhttp.open("GET", "../../../../setting/logo.png", false);
xmlhttp.send();
if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	window.logoPath = "../../../../setting/logo.png";
else
	window.logoPath = "../images/logo.png";
window.welcomePath = "res/mainui/images/a8-welcome.jpg";
window.funcIcons = [ {
	name : "quit",
	title : "\u9000\u51fa\u7cfb\u7edf"
}, {
	name : "xtxx",
	title : "\u7cfb\u7edf\u6d88\u606f"
}, {
	name : "help",
	title : "\u7cfb\u7edf\u5e2e\u52a9"
}, {
	name : "pwd",
	title : "\u4e2a\u4eba\u8bbe\u7f6e\u4e0e\u8f6f\u4ef6\u4fe1\u606f"
} ];
window.maxTabNum = 6;
window.useNotice = true;
window.navPage = "pt/canvas?formid=aos_mainui_nav_new&pt_control_action=init"
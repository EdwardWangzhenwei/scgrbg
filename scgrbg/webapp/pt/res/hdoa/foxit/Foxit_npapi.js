/*
 * OFD的ActiveX控件使用的js，负责加载和提供接口。
 * js压缩：http://tool.css-js.com/ 
 */
//通用对象
var OFD = {
    // 保存页面中所有的OCX实例
    _OCX_Array: [],
    // 使用id查找实例
    find: function (id) {
        var ocx = null;
        this._each(this._OCX_Array, function (i, e) {
            if (e._id == id) {
                ocx = e;
                return false;
            }
        });
        return ocx;
    },
    // 合并对象
    _extend: function (defs, target) {
        var r = target;
        if (this._isNull(r)) {
            if (this._isArray(defs)) {
                r = [];
            } else {
                r = {};
            }
        }
        this._each(defs, function (n, v) {
            if (!(n in r)) {
                r[n] = v;
            }
        });
        return r;
    },
    // 判断参数是否是数组
    _isArray: function (v) {
        return Object.prototype.toString.call(v) === "[object Array]";
    },
    // 判断是否为纯粹对象,like jquery.isPlainObject
    _isPlainObject: function (v) {
        // Must be an Object.
        // Because of IE, we also have to check the presence of the constructor
        // property.
        // Make sure that DOM nodes and window objects don't pass through, as
        // well
        if (!v || v.toString() !== "[object Object]" || v.nodeType
				|| "setInterval" in v) {
            return false;
        }
        try {
            // Not own constructor property must be Object
            if (v.constructor && !v.hasOwnProperty("constructor")
					&& !v.constructor.prototype.hasOwnProperty("isPrototypeOf")) {
                return false;
            }
        } catch (e) {
            // IE8,9 Will throw exceptions on certain host objects #9897
            return false;
        }
        // Own properties are enumerated firstly, so to speed up,
        // if last one is own, then all properties are own.
        var key;
        for (key in v) {
        }
        return key === undefined || v.hasOwnProperty(key);
    },
    // 判断参数是否是undefined或null
    _isNull: function (v) {
        return typeof v == "undefined" || (v != 0 && !v);
    },
    // 判断参数是有有效
    _isValid: function (v) {
        return !this._isNull(v);
    },
    // getElementById
    _$: function (id) {
        return document.getElementById(id);
    },
    // createElement
    _new: function (tag) {
        return document.createElement(tag);
    },
    // for each like jquery
    _each: function (o, fn) {
        if (this._isArray(o)) {
            for (var i = 0, ol = o.length, val = o[0]; i < ol
					&& fn.call(val, i, val) !== false; val = o[++i]) {
            }
        } else {
            for (var i in o) {
                if (fn.call(o[i], i, o[i]) === false) {
                    break;
                }
            }
        }
        return o;
    },
    // 一些页面方法
    Page: {
        // 获取窗口宽度
        width: function () {
            var w = 0;
            if (window.innerWidth) {
                w = window.innerWidth;
            } else if ((document.body) && (document.body.clientWidth)) {
                w = document.body.clientWidth;
            }
            // 通过深入Document内部对body进行检测，获取窗口大小
            if (document.documentElement
					&& document.documentElement.clientHeight
					&& document.documentElement.clientWidth) {
                w = document.documentElement.clientWidth;
            }
            return w;
        },
        // 获取窗口高度
        height: function () {
            var h = 0;
            if (window.innerHeight) {
                h = window.innerHeight;
            } else if ((document.body) && (document.body.clientHeight)) {
                h = document.body.clientHeight;
            }
            // 通过深入Document内部对body进行检测，获取窗口大小
            if (document.documentElement
					&& document.documentElement.clientHeight
					&& document.documentElement.clientWidth) {
                h = document.documentElement.clientHeight;
            }
            return h;
        },
        // 兼容FF和IE的事件，当e未定义的时候返回window.event
        dEvent: function (e) {
            if (!e) {
                return window.event;
            } else {
                return e;
            }
        }
    }
};

//OCX类型及方法
OFD.OCX = function (options) {
    // OCX的Object ID
    this._id;
    // 配置
    this.opts = OFD._extend({
        div: null,
        width: "100%",
        height: OFD.Page.height() + "px",

        loadMsg: "<span>正在加载控件，请稍候....</span>",
        // 控件安装程序的下载路径
        //downURL : "res/ocx/FoxitOFDReaderSetup_1.4.1.26.exe",
        // 是否检查控件已经安装װ
        checkInstalled: true
    }, options);
    // 控件对象
    this.ax;
    // 缓存用户操作.因为某些情况下,用户操作时,控件还没有初始化完毕
    this._optCache = {
        compsite: [],
        callback: [],
        convert: [],
        perform: [],
        svcaddr: [],
        savefile: [],
        closefile: [],
        open: [],
        enable: [],
        sealname: [],
        sealid: [],
        sealmethod: [],
        opts: {// 属性名称和方法的映射
            "compsite": "setCompositeVisible",
            "callback": "setCallback",
            "convert": "convertFile",
            "perform": "performClick",
            "svcaddr": "setServiceAddr",
            "open": "_open",
            "enable": "setCompsiteEnable",
            "savefile": "saveFile",
            "closefile": "closeFile",
            "sealname": "setSealName",
            "sealid": "setSealId",
            "sealmethod": "setSealSignMethod"
        }
    };

    // 加载控件
    this.load = function () {
        var rand = this._randomString(10);
        if (OFD._isNull(this.opts.div)) {
            div.innerHTML = "<span style='color:red;'>启动FireFox控件环境未准备好!</span>";
        }
        var div = OFD._$(this.opts.div);
        div.innerHTML = this.opts.loadMsg;
        this._id = "ofd_ocx_" + rand;
        OFD._OCX_Array.push(this); // 放入队列,以方便查找使用
        this._writeOCX();
        this.ready();

        return this;
    };

    // 加载配置,完成准备工作,只执行一次
    this.ready = function () {
        if (this.ax) {// 已经初始化
            return this;
        }
        var o = OFD._$(this._id);
        if (!o)//|| !("openURL" in o)) 
        {
            div.innerHTML = "<span>ActiveX控件未正确初始化!</span>";
            return;
        }
        this.ax = o; // 赋值,很重要
        var _me = this;
        // 控制初始化时的组件显示
        var compsite = this.opts.compsite;
        if (compsite) {
            OFD._each(compsite, function (n, v) {
                //_me.setCompositeVisible(n, v);
            });
        }

        // 加载完毕前的动作都执行一遍
        OFD._each(this._optCache, function (n, v) {
            if (OFD._isArray(v) && v.length > 0) {
                var fn = _me[_me._optCache.opts[n]];
                if (fn) {
                    OFD._each(v, function (i, e) {
                        var args = [];
                        OFD._each(e, function (ni, vi) {
                            args.push(vi);
                        });
                        try {
                            fn.apply(_me, args);
                        } catch (e) {
                        }
                    });
                }

                v.length = 0; // clear
            }
        });
        return this;
    };

    // 内部函数请勿调用-生成随机串
    this._randomString = function (l) {
        var x = "0123456789qwertyuioplkjhgfdsazxcvbnm";
        var tmp = "";
        for (var i = 0; i < l; i++) {
            tmp += x.charAt(Math.ceil(Math.random() * 100000000) % x.length);
        }
        return tmp;
    };


    // 内部函数请勿调用-输出控件的html
    this._writeOCX = function () {
        var w = this.opts.width;
        if (OFD._isNull(w) || w == "auto") {
            w = "100%";
        }
        var h = this.opts.height;
        if (OFD._isNull(h) || h == "auto") {
            h = OFD.Page.height() + "px";
        }

        OFD._$(this.opts.div).innerHTML = "<embed id='" + this._id // id
				+ "' width='" + w// width
				+ "' height='" + h// heigth
				+ "' type='application/ofd"
                + "'>";
    };

    // 内部函数请勿调用-检查组件是否准备完毕
    this._check = function () {
        return OFD._isValid(this.ax);
    };

    // 内部函数请勿调用-打开文件
    this._open = function (type, path, name, breadonly) {
        if (type == "file") {
           return  this.ax.openFile(path, breadonly);
        }
    };

    // 显示和隐藏组件
    this.setCompositeVisible = function (name, visible) {
        if (this._check()) {
            var o = this.ax;
            if (OFD._isArray(name)) {
                OFD._each(name, function (i, n) {
                    o.setCompositeVisible(n, visible);
                });
            } else {
                o.setCompositeVisible(name, visible);
            }
        } else {
            this._optCache.compsite.push(
			{
			    n: name,
			    v: visible
			});
        }
        return this;
    };

    // 打开本地计算机上的文件，URL可取值如C:/123.ofd
    this.openFile = function (path, breadonly) {
        return this._open("file", path, name, breadonly);
    };

    // 将加载或缓存的文件保存到本地磁盘
    this.saveFile = function (path) {
        if (this._check()) {
            return this.ax.saveFile(path);
        } else {
            this._optCache.savefile.push(
			{
			    p: path
			});
        }
    };
    // 关闭当前文件
    this.closeFile = function () {
        if (this._check()) {
            return this.ax.closeFile();
        } else {
            this._optCache.closefile.push({});
        }
    };
    // 设置印章名称
    this.setSealName = function (path) {
        if (this._check()) {
            this.ax.setSealName(path);
        } else {
            this._optCache.setSealName.push({
                p: path
            });
        }
        return this;
    };
    // 设置印章标识
    this.setSealId = function (strSealId) {
        if (this._check()) {
            this.ax.setSealId(strSealId);
        } 
    };
    // 设置签名算法
    this.setSealSignMethod = function (path) {
        if (this._check()) {
            this.ax.setSealSignMethod(path);
        } else {
            this._optCache.setSealSignMethod.push({
                p: path
            });
        }
        return this;
    };
    this.setViewPreference = function (key, value) {
        if (this._check()) {
            var o = this.ax;

            o.setViewPreference(key, value);

        }
        return this;
    };
    //
    this.setLogURL = function (strurl, oid) {
        if (this._check()) {
            var ox = this.ax;
            ox.setLogURL(strurl, oid);
        }
        return this;
    };
    this.addTrackInfo = function (xmlParam) {
        if (this._check()) {
            var ox = this.ax;
            ox.addTrackInfo(xmlParam);
        }
        return this;
    };
    this.setPrintInfo = function (num) {
        if (this._check()) {
            var ox = this.ax;
            ox.setPrintInfo(parseInt(num));
        }
        return this;
    };
    //正常打印
    this.printFile = function (title, bgray) {
        if (this._check()) {
            var ox = this.ax;
            ox.PrintFile(title, bgray, 0);
        }
    }
    //静默打印
    this.quietPrintFile = function (title, bgray, isquietprint) {
        if (this._check()) {
            var ox = this.ax;
            ox.PrintFile(title, bgray, isquietprint);
        }
    }
	//获取打印日志
     this.getLogFileContent = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getLogFileContent();
        }
    }
	//获取打印日志路径----新增
    this.getLogFilePath = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getLogFilePath();
        }
    }
	
	
	//设置组件是否可用setCompositeEnable  -----新增
	this.setCompositeEnable = function (scmpname, bisenable) {
        if (this._check()) {
            var o = this.ax;
            if (OFD._isArray(scmpname)) {
                OFD._each(scmpname, function (i, n) {
                    o.setCompositeEnable(n, bisenable);
                });
            } else {
                o.setCompositeEnable(scmpname, bisenable);
            }
        } else {
            this._optCache.enable.push(
			{
			    n: scmpname,
			    v: bisenable
			});
        }
        return this;
    };
	
	
    //设置阅读模式
    this.setDisPlayMode = function (disPlayMode) {
        if (this._check()) {
            var ox = this.ax;
            ox.setDisPlayMode(parseInt(disPlayMode));
        }
    }
    //设置显示宽度
    this.setZoomMode = function (zoomMode) {
        if (this._check()) {
            var ox = this.ax;
            ox.setZoomMode(parseInt(zoomMode));
        }
    }
    //获取版本号
    this.getPluginVersion = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getPluginVersion();
        }
    }
    //注销
    this.destroyOCX = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.DestroyOCX();
        }
    }
    //获取公文域位置
    this.getTaggedPosition = function (docdomain) {
        if (this._check()) {
            var ox = this.ax;
            return ox.getTaggedPosition(docdomain);
        }
    }
    //获取公文域内容
    this.getTaggedText = function (docdomain) {
        if (this._check()) {
            var ox = this.ax;
            return ox.getTaggedText(docdomain);
        }
    }
    //设置打开文档权限
    this.removeAppPermission = function (permission) {
        if (this._check()) {
            var ox = this.ax;
            return ox.removeAppPermission(permission);
        }
    }
    //判断是否正在打印
    this.isQuietPrinting = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.IsQuietPrinting();
        }
    }
    //判断是否正在签章
    this.isSigning = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.IsSigning();
        }
    }
    //静默打印设置
    this.printSetting = function () {
        if (this._check()) {
            var ox = this.ax;
            ox.printSetting();
        }
    }
    //
    this.saveToLocal = function (path) {
        if (this._check()) {
            var ox = this.ax;
            return ox.saveToLocal(path);
        }
    }
    //获取文件包内文档数量
    this.getDocumentCount = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getDocumentCount();
        }
    }
    //获取指定文档的页面数量
    this.getPageCount = function (docIndex) {
        if (this._check()) {
            var ox = this.ax;
            return ox.getPageCount(parseInt(docIndex));
        }
    }
    //渲染指定页面
    this.saveImage = function (docIndex, pageIndex, dpi, filepath) {
        if (this._check()) {
            var ox = this.ax;
			//filepath
            return ox.saveImage(parseInt(docIndex), parseInt(pageIndex), parseInt(dpi), filepath);
        }
    }
    //渲染文件包内所有页面
    this.saveAllImage = function (dpi, filepath) {
        if (this._check()) {
            var ox = this.ax;
            return ox.saveAllImage(parseInt(dpi), filepath);
        }
    }
    //设置当前套件版本作用
    this.setPerformanceTesting = function (bPerformanceTesting) {
        if (this._check()) {
            var ox = this.ax;
            ox.setPerformanceTesting(parseInt(bPerformanceTesting));
        }
    }
    //获取当前页的页码
    this.getCurPageIndex = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.GetCurPageIndex();
        }
    }
    //获取当前文档页数
    this.getPageCount = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getPageCount();
        }
    }
    //翻页
    this.gotoPage = function (index) {
        if (this._check()) {
            var ox = this.ax;
            return ox.GotoPage(parseInt(index));
        }
    }
    //打开首页上半部分盖有多个印章的文档
    this.openSignFile = function (url) {
        if (this._check()) {
            var ox = this.ax;
            return ox.openSignFile(url);
        }
     }
	 //设置套件版本作用
	 this.setPerformanceTesting=function(bPerformanceTesting){
		         if (this._check()) {
            var ox = this.ax;
            ox.setPerformanceTesting(bPerformanceTesting);
        }
	 }
	 //回调
	 this.jsCallbackFun_ElapsedTime=function(){
		    if (this._check()) {
            var ox = this.ax;
            ox.JsCallbackFun_ElapsedTime=ElapsedTime;
        }
	 }
	 //回调initSetting
	 this.jsCallbackFun_InitSetting=function(){
		    if (this._check()) {
            var ox = this.ax;
            ox.JsCallbackFun_InitSetting=initSetting;
        }
	 }
	 //回调UpdateInfo
	 this.jsCallbackFun_UpdateInfo=function(){
		    if (this._check()) {
            var ox = this.ax;
            ox.JsCallbackFun_UpdateInfo=UpdateInfo;
        }
	 }
	 //隐藏侧边栏
	 this.hidePanels = function (nHide) {
			if (this._check()) {
				var ox = this.ax;
				ox.HidePanels(parseInt(nHide));
			}
		}
	 //获取签章个数
	    this.countSignatures = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.CountSignatures();
        }
    }
    //增加元数据
    this.setMetaData = function (key, value) {
        if (this._check()) {
            var ox = this.ax;
            return ox.setMetaData(key, value);
        }
       
    }
    //获取元数据
    this.getMetaData = function (key) {
        if (this._check()) {
            var ox = this.ax;
            return ox.getMetaData(key);
        }
       
    }
    //添加用户名
    this.setUserName = function (uname) {
        if (this._check()) {
            var ox = this.ax;
            return ox.setUserName(uname);
        }
       
    }
    //获取用户名
    this.getUserName = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getUserName();
        }
       
    }
    //清除水印
    this.clearTrackInfo = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.clearTrackInfo();
        }
    }
    //设置缩放比例
    this.setZoomRadio = function (zoomvalue) {
        if (this._check()) {
            var ox = this.ax;
            ox.setZoomRadio(zoomvalue);
        }
    }
    //获取缩放比例
    this.getZoomRadio = function () {
        if (this._check()) {
            var ox = this.ax;
            return ox.getZoomRadio();
        }
    }
    //设置上传地址
    this.setLogSvrURL = function (url) {
        if (this._check()) {
            var ox = this.ax;
            ox.setLogSvrURL(url);
        }
    }
};

/**
 * 快速加载并初始化OCX控件,返回OCX实例
 * 
 * @param div
 *            div的id,ocx放置于此div内.如果为null,则自动创建一个div并追加于body的最后
 * @param width
 *            宽度,如果为null或auto,则设置为100%,也可以设置为具体像素(如500px)
 * @param width
 *            高度,如果为null或auto,则设置为100%,也可以设置为具体像素(如500px)
 * @param downURL
 *            控件安装程序的下载地址,只有在检查到控件未安装时才有用.若未设置,则不会出现下载地址.
 */
OFD.OCX.init = function (div, width, height, downURL) {
    var config = {};
    var tmp = arguments.callee.toString().match(/\(.*?\)/)[0];
    var names = tmp.replace(/[()\s]/g, '').split(',');
    var array = Array.prototype.slice.call(arguments, 0);
    OFD._each(array, function (i, e) {
        if (OFD._isValid(e)) {
            if (OFD._isPlainObject(e)) {
                OFD._extend(e, config);
            } else {
                config[names[i]] = e;
            }
        }
    });
    return new OFD.OCX(config).load();
};

var foxit = {};
//加载并初始化阅读器OCX控件
foxit.ofdReaderInit = function (divID, width, height) {
    return OFD.OCX.init(divID, width, height);
};
//加载并初始化转换器OCX控件
foxit.ofdCreatorInit = function (divID, width, height) {
    return OFD.OCX.init(divID, width, height);
};

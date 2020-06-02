var baseURL = "http://127.0.0.1:8080/";//本地

var C = {
    setCache: setCache,
    getCache: getCache,
    removeCache: removeCache,
    getNow: getNow,
    arraySort: arraySort,
    randomNum: randomNum,
    parseURL: parseURL,
    getTreeNode: getTreeNode,
    promiseFactory: promiseFactory,
    save: save,
    find: find
};


 var num = new Object;
	function save(cp_key,cp_value){
		num.cp_keynum = cp_key;
		num.cp_num_value = cp_value;
		var str = JSON.stringify(num);
		localStorage.setItem(num.cp_keynum,str);
		//layer.msg("存储成功");
	}
	//查找数据
	function find(cp_key){
		var cp_keynum = cp_key;
		var str = localStorage.getItem(cp_keynum);
		if(str == null){
			return 1;
		}else{
			var num = JSON.parse(str);
			return num.cp_num_value;	
		}
		
	}


/**
 * 设置缓存和过期时间 
 * @param cacheName
 * @param cacheValue
 * @param cacheExpTime(单位 秒)
 */
function setCache(cacheName, cacheValue, cacheExpTime) {
    if (cacheExpTime === undefined || isNaN(cacheExpTime) || cacheExpTime.length <= 0) {
        $.cookie(cacheName, JSON.stringify(cacheValue));
    } else {
        var expiresDate = new Date();
        expiresDate.setTime(expiresDate.getTime() + (cacheExpTime * 1000));
        $.cookie(cacheName, JSON.stringify(cacheValue), {expires: expiresDate})
    }
}

/**
 *
 * @param cacheName
 * @returns {jQuery|*|*|null}
 */
function getCache(cacheName) {
    var result = $.cookie(cacheName);
    if (result === undefined || result === null) {
        var e = new Error("没有取到值,可能已经过期或没存储");
        e.name = "Cache";
        console.info(e)
        return null;
    } else {
        return JSON.parse(result);
    }

}

/**
 * 删除缓存
 * @param cacheName
 */
function removeCache(cacheName) {
    $.cookie(cacheName, null);
}


/**
 * 返回系统当前时间
 * @returns {*}
 */
function getNow() {
    return new Date().Format("yyyy-MM-dd hh:mm:ss");
}

function arraySort(array, key, sort) {
    switch (sort) {
        case 'desc':
            array.sort((a, b) => {
                b[key] - a[key];
            });
            break;
        case 'asc':
            array.sort((a, b) => {
                a[key] - b[key];
            });
            break;
    }
}

function randomNum(minNum, maxNum, count) {
    var list = [];
    var numer = 0;
    if (arguments.length === 3) {
        for (var i = 0; i < count; i++) {
            numer = parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10);
            if (list.indexOf(numer) !== -1) {
                i--;
                continue;
            }
            list.push(numer);
        }
        return list;
    } else {
        if (arguments.length === 1) {
            return parseInt(Math.random() * minNum + 1, 10);
        } else {
            if (arguments.length === 2) {
                return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10);
            }
        }
    }
}

function parseURL(url) {
    var a = document.createElement('a');
    a.href = url;
    return {
        host: a.hostname,
        port: a.port,
        query: a.search,
        params: (function () {
            var ret = {},
                seg = a.search.replace(/^\?/, '').split('&'),
                len = seg.length,
                i = 0,
                s;
            for (; i < len; i++) {
                if (!seg[i]) {
                    continue;
                }
                s = seg[i].split('=');
                ret[s[0]] = s[1];
            }
            return ret;
        })(),
        hash: a.hash.replace('#', '')
    };
}

function promiseFactory(params) {
    params = params || {};
    var formatPara = function (data) {
        var arr = [];
        for (var i in data) {
            arr.push(`${i}=${data[i]}`);
        }
        return arr.join('&');
    };
    if (params.url) {
        console.log(params.url);
    } else {
        var error = new Error(`请求创建失败 参数url值出现错误 当前url:${params.url}`);
        error.name = `selfRequest`;
        console.error(error);
        return new Promise((resolve, reject) => {
        });
    }
    if (!params.async) {
        params.async = true;
    }
    var p = new Promise((resolve, reject) => {
        let xhr = XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject('Microsoft.XMLHTTP');
        switch (params.type.toUpperCase()) {
            case 'GET':
                params.data = formatPara(params.data);
                try {
                    xhr.open(params.type, params.url, params.async);
                    xhr.send();
                } catch (e) {
                    if (e.message.indexOf('Invalid URL') != -1) {
                        var error = new Error(`请求创建失败 参数url值出现错误 当前url:${params.url}`);
                        error.name = `selfRequest`;
                        console.error(error);
                        return;
                    }
                    console.log(e.message);
                }
                console.log('进入GET方法');
                break;
            case 'POST':
                try {
                    xhr.open(params.type, params.url, params.async);
                    xhr.send(params.data);
                } catch (e) {
                    if (e.message.indexOf('Invalid URL') != -1) {
                        var error = new Error(`请求创建失败 参数url值出现错误 当前url:${params.url}`);
                        error.name = `selfRequest`;
                        console.error(error);
                        return;
                    }
                    console.log(e.message);
                }
                console.log('进入POST方法');
                break;
            default:
                var error = new Error(`请求创建失败 参数type值出现错误 当前type:${params.type}`);
                error.name = `selfRequest`;
                console.error(error);
                break;
        }
        xhr.onload = function () {
            if (xhr.status == 0) {
                var error = new Error('跨域请求:${params.url}');
                error.name = '出现跨域请求';
                console.error(error);
                return;
            }
            let res = {};
            var frontEnd = [400, 401, 402, 403, 404, 405, 406, 407, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417];
            var backEnd = [500, 501, 502, 503, 504, 505];
            if (xhr.readyState === 4) {
                res = JSON.parse(xhr.responseText);
                resolve(res);
            } else {
                var errorInfo = {
                    400: '错误请求,如语法错误',
                    401: '请求授权失败',
                    402: '保留有效ChargeTo头响应',
                    403: '请求不允许',
                    404: '没有发现文件、 查询或URl',
                    405: '用户在Request - Line字段定义的方法不允许',
                    406: '根据用户发送的Accept拖,请求资源不可访问',
                    407: '类似401,用户必须首先在代理服务器上得到授权',
                    408: '客户端没有在用户指定的饿时间内完成请求',
                    409: '对当前资源状态,请求不能完成',
                    410: '服务器上不再有此资源且无进一步的参考地址',
                    411: '服务器拒绝用户定义的Content - Length属性请求',
                    412: '一个或多个请求头字段在当前请求中错误',
                    413: '请求的资源大于服务器允许的大小',
                    414: '请求的资源URL长于服务器允许的长度',
                    415: '请求资源不支持请求项目格式',
                    416: '请求中包含Range请求头字段,在当前请求资源范围内没有range指示值,请求也不包含If - Range请求头字段',
                    417: '服务器不满足请求Expect头字段指定的期望值,如果是代理服务器,可能是下一级服务器不能满足请求',
                    500: '服务器产生内部错误',
                    501: '服务器不支持请求的函数',
                    502: '服务器暂时不可用,有时是为了防止发生系统过载',
                    503: '服务器过载或暂停维修',
                    504: '关口过载,服务器使用另一个关口或服务来响应用户,等待时间设定值较长',
                    505: '服务器不支持或拒绝支请求头中指定的HTTP版本'
                };
                if (frontEnd.indexOf(xhr.status) != -1 || backEnd.indexOf(xhr.status) != -1) {
                    res = JSON.parse(xhr.responseText);
                    reject(res);
                }
            }
        };
    });
    return p;
}

C.getSearch = function () {
    var search = null;
    console.log(window)
    if (window.location.search.length > 0) {
        var locaSearch = window.location.search.split("?")[1].split("&");
        search = {};
        for (var i = 0; i < locaSearch.length; i++) {
            var key = null;
            key = locaSearch[i].split("=")[0]
            var value = decodeURI(locaSearch[i].split("=")[1]);
            search[key] = value;
        }
    }
    return search;
}
//日期格式化
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
/**
 * 数组中是否存在
 * @param thisPage
 * @returns {boolean}
 */
Array.prototype.includes = function (thisPage) {
    var pageList = this;
    var exist = pageList.indexOf(thisPage) != -1 ? true : false;
    return exist;
}

Array.prototype.getItem = function (key) {
    var item = null;
    this.forEach(function (el, i) {
        if (key == el.key) {
            item = el;
        }
    });
    return item;
}

Array.prototype.getItemTow = function (key, search) {
    var item = null;
    this.forEach(function (el, i) {
        if (search == el[key]) {
            item = el;
        }
    });
    return item;
}

/**
 * 处理树结构
 * @param list
 * @param idUp
 * @param idKey
 */
function getTreeNode(list, idUp, idKey, child) {
    if (child == undefined) {
        child = 'child';
    }
    var fa = function (upId) {
        var _array = [];
        for (var i = 0; i < list.length; i++) {
            var n = list[i];
            if (n[idUp] === upId) {
                n[child] = fa(n[idKey]);
                _array.push(n);
            }
        }
        return _array;
    }
    return fa(list[0][idUp], idUp, idKey);
}


var token = C.getCache('token');

/*$.ajaxSetup({
    dataType: "json",
    cache: false,
    headers: {
        "x-auth-token": token
    },
    xhrFields: {
        withCredentials: true
    },
    complete: function (xhr) {
        //token过期，则跳转到登录页面
        if (xhr.responseJSON && xhr.responseJSON.meta.code >= 400) {
            parent.location.href = 'login.html';
        }
    }
});*/

var t = setInterval(function () {
    if (location.pathname.indexOf("index")!=-1){
        token = C.getCache('token');
        if (token == null) {
            /*parent.layer.msg("获取登录信息失败,请重新登录后在试!", function () {
                parent.window.location.replace("login.html");
            })*/
            clearInterval(t);
            return false;   //这里要改成false
        }
    }
}, 100)


function back() {
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });
}

back();



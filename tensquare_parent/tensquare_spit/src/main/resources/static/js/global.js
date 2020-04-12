var util = {
    strCode: "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ",
    randKey: function (length) {
        var s = '';
        var i;
        for (i = 0; i < length; i++) {
            s += this.randChar();
        }
        return s;
    },
    randChar: function () {
        return this.strCode[parseInt(Math.random() * this.strCode.length)];
    },
    fromUnicode: function (s) {
        s = s.replace(/u([\w]{4})/g, '\\u$1');
        try {
            eval("s='" + s + "'");
        } catch (e) {
            // console.log(s);
        }
        return s;
    }
};

var G = (function (window) {

    var G = function () {

    };

    G.highLightKw = function (value, kw, color) {
        color = color || '#11a1f8';
        if (!kw) {
            return value;
        }
        var replaceStr = '<span style=\'color:'+color+'\'>$1</span>';
        var reg = new RegExp('(' + kw + ')', 'ig');
        return value.replace(reg, replaceStr);
    };


    G.go = function (url, open) {
        if (open) {
            window.open(url);
        } else {
            document.location.href = url;
        }
    };
    G.browser = (function () {
        var u = navigator.userAgent;
        return {
            trident: u.indexOf('Trident') > -1,
            presto: u.indexOf('Presto') > -1,
            webKit: u.indexOf('AppleWebKit') > -1,
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') === -1,
            mobile: !!u.match(/AppleWebKit.*Mobile.*/),
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
            android: u.indexOf('Android') > -1 || u.indexOf('Adr') > -1,
            iPhone: u.indexOf('iPhone') > -1,
            iPad: u.indexOf('iPad') > -1,
            webApp: u.indexOf('Safari') === -1,
            weixin: u.indexOf('MicroMessenger') > -1,
            qq: u.match(/\sQQ/i) === ' qq'
        };
    })();
    G.submit = function (url, data) {
        data._token = _CSRF;
        var form = document.createElement('form');
        var ip, i;
        for (i in data) {
            ip = document.createElement('input');
            ip.type = 'hidden';
            ip.name = i;
            ip.value = data[i];
            form.appendChild(ip);
        }

        form.method = 'post';
        form.action = url;
        document.body.appendChild(form);
        form.submit();
    };

    G.upload = function (url, arg, file, key_name, progress_callback, ok_callback, err_callback) {
        var formData = new FormData(),
            oXHR = new XMLHttpRequest();
        var i;

        oXHR.upload.addEventListener('progress', progress_callback, false);
        oXHR.addEventListener('load', function (e) {
            var d;
            try {
                d = JSON.parse(e.target.response);
                if (d.code && d.code > 0) {
                    ok_callback && ok_callback(d.code, d.args);
                } else {
                    this.err_callback(d.code, d.message);
                }
            } catch (e) {
                err_callback(-1, '上传出错');
                // console.log(e);
            }
        }, false);

        for (i in arg) {
            formData.append(i, arg[i]);
        }

        formData.append(key_name, file);

        oXHR.open('POST', G.callURL(url));
        oXHR.send(formData);
    };

    G.call = function (a, b, c, d) {
        var url, arg, func_ok, func_er;
        if (arguments.length == 4) {
            url = a;
            arg = b;
            func_ok = c;
            func_er = d;
        } else if (arguments.length == 3) {
            url = a;
            if (typeof b == "function") {
                func_ok = b;
                func_er = c;
            } else {
                arg = b;
                func_ok = c;
            }
        } else if (arguments.length == 2) {
            url = a;
            if (typeof b == "function") {
                func_ok = b;
            } else {
                arg = b;
            }
        } else {
            url = a;
        }
        arg = arg || {};

        var xhr = new XMLHttpRequest();
        xhr.handleerror = function (d) {
            // console.log(d);
            if (!d) {
                // console.error("Connetction error.");
            } else if (typeof d == "string") {
                // console.error("XHRParseError");
                // console.log([a, b, c, d]);
                // console.log(d);
            } else if (d.code !== undefined) {
                func_er && func_er(d.code, d.data);
            } else if (d.httpStatus === 422) {
                func_er && func_er(d.httpStatus, d.message, '格式错误');
            } else {
                // console.error("XHRIllegal");
                // console.log([a, b, c, d]);
                // console.log(d);
            }
        };
        xhr.onreadystatechange = function () {
            var d;
            if (this.readyState == 4) {
                if (this.status == 200) {
                    d = this.responseText;
                    try {
                        d = JSON.parse(d);
                    } catch (e) {
                        console.error("JSONParseError or CallbackError");
                        console.error(e);
                        this.handleerror(d);
                        return;
                    }
                    if (d.code !== undefined && d.code === 0) {
                        func_ok && func_ok(d.code, d.data);
                    } else {
                        this.handleerror(d);
                    }
                } else if (this.status === 422) {
                    d = this.responseText;
                    try {
                        d = JSON.parse(d);
                    } catch (e) {
                        console.error("JSONParseError or CallbackError");
                        console.error(e);
                    } finally {
                        d.httpStatus = 422;
                        this.handleerror(d);
                    }
                } else {
                    this.handleerror();
                }
            }
        };
        // xhr.open("POST", G.callURL(url), true);
        xhr.open("POST", url, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.setRequestHeader('Accept', 'application/json, text/plain, */*');
        if (window._CONFIG._CSRF) {
            xhr.setRequestHeader('X-CSRF-TOKEN', _CSRF);
        }
        xhr.send(G.serialize(arg));
    };

    G.serialize = function (o) {
        var a = [], i;
        for (i in o) {
            a.push(i + '=' + encodeURIComponent(o[i]));
        }
        return a.join('&');
    };

    G.error = function (str) {
        if (window.ui) {
            ui.notify(str, true) || ui.alert(str);
        } else {
            alert(str);
        }
    };
    G.request = (function (url) {
        var map = {}, j,
            i = url.indexOf('?'),
            m = url.indexOf('#');
        if (i < 0) {
            return map;
        }
        j = m > 0 ? (url.slice(i + 1, m)) : j = url.slice(i + 1);
        m = j.split('&');
        for (i = 0; i < m.length; i++) {
            j = m[i].indexOf('=');
            if (j <= 0) {
                continue;
            }
            map[m[i].slice(0, j)] = decodeURIComponent(m[i].slice(j + 1));
        }
        return map;
    })(document.location.href);

    G.url = function (c, a, args, flag) {
        var url, i, fi = true;
        var preUrl = window._CONFIG.APP_URL;
        if (window._CONFIG.ENV !== 'production') {
            var origin = window.location.protocol + '//' + window.location.host;
            preUrl = origin || window._CONFIG.APP_URL;
        }
        url = preUrl + '/' + c + '/' + a;
        if (flag) {
            url = url + '/' + args.join('/');
        } else {
            if (args) {
                for (i in args) {
                    if (fi) {
                        fi = false;
                        url += '?' + i + '=' + encodeURIComponent(args[i]);
                    } else {
                        url += '&' + i + '=' + encodeURIComponent(args[i]);
                    }
                }
            }
        }
        return url;
    };

    G.callURL = function (url) {
        return G.url('core', 'call', {
            _m: url
        });
    };

    G.statistics = function (appid, siteid, type) {
        type = type || 'visit';
        var url = 'http://statistics.gd.gov.cn/visit/' + type + '?site=' + siteid;
        url = type === 'visit' ? url + '&appid=' + appid : url + '&post=' + appid;
        var $body = $('body');
        var img = '<img  src="' + url + '"/>';
        var $img = $(img).on('load', function () {
            $img.remove();
        });
        $body.append($img);
    };


    (function () {

        var d = document.createElement('div');
        var to_test = ['t', 'webkitT', 'mozT'];
        var i;
        for (i = 0; i < to_test.length; i++) {
            if ((to_test[i] + 'ransform') in d.style) {
                G.cssPrefix = to_test[i];
                return;
            }
        }
    })();

    return G;

})(window);

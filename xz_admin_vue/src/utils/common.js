// TODO:时间日期函数

/**
 * 转化时间对象为指定格式
 * @param {*} fmt
 * @param {*} date
 */
export function dateFtt(fmt, date) { //author: meizz
    var o = {
        "M+": date.getMonth() + 1, //月份
        "d+": date.getDate(), //日
        "h+": date.getHours(), //小时
        "m+": date.getMinutes(), //分
        "s+": date.getSeconds(), //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ?
                (o[k]) :
                (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

/**
 * 格式化时间为时、分、秒；常用于倒计时
 * @param {*} value
 */
export function formatSeconds(value) {
    var secondTime = parseInt(value);
    var minuteTime = 0;
    var hourTime = 0;
    if (secondTime > 60) { //如果秒数大于60，将秒数转换成整数
        //获取分钟，除以60取整数，得到整数分钟
        minuteTime = parseInt(secondTime / 60);
        //获取秒数，秒数取佘，得到整数秒数
        secondTime = parseInt(secondTime % 60);
        //如果分钟大于60，将分钟转换成小时
        if (minuteTime > 60) {
            //获取小时，获取分钟除以60，得到整数小时
            hourTime = parseInt(minuteTime / 60);
            //获取小时后取佘的分，获取分钟除以60取佘的分
            minuteTime = parseInt(minuteTime % 60);
        }
    }
    var result = "" + parseInt(secondTime) + "秒";
    if (minuteTime > 0) {
        result = "" + parseInt(minuteTime) + "分" + result;
    }
    if (hourTime > 0) {
        result = "" + parseInt(hourTime) + "小时" + result;
    }
    return result;
}

// TODO: 对象函数

/**
 * 判断对象是否有属性值为空的属性
 * @param {*} object
 */
export function noNullProp(object) {
    if (JSON.stringify(object) == '{}') {
        return false;
    } else {
        for (var prop in object) {
            if (object[prop] === '' || object[prop].length == 0) {
                return false;
            }
        }
        return true;
    }
};
/**
 * 判断是否为一个对象
 * @param {*} target
 */
export function isObject(target) {
    if (typeof target == 'Object' && target != null) {
        return true;
    } else {
        return false;
    }
}
/**
 * 判断是否为FormData对象
 * @param {*} target
 */
export function isFormData(target) {
    if (target instanceof FormData) {
        return true;
    } else {
        return false;
    }
}
/**
 * 判断是否为undefined
 * @param {*} target
 */
export function isUndefined(target) {
    if (typeof target == 'undefined') {
        return true;
    } else {
        return false;
    }
}
/**
 * 深拷贝
 * @param {*} source
 */
export function deepcopy(source) {
    if (!source) {
        return source;
    }
    let sourceCopy = source instanceof Array ? [] : {};
    for (let item in source) {
        sourceCopy[item] = typeof source[item] === 'object' ? deepcopy(source[item]) : source[item];
    }
    return sourceCopy;
};
/**
 * 合并对象
 * @param {*} target
 * @param {*} source
 */
export function objectMerge(target, source) {
    if (typeof target !== 'object') {
        target = {}
    }
    if (Array.isArray(source)) {
        return source.slice()
    }
    for (const property in source) {
        if (source.hasOwnProperty(property)) {
            const sourceProperty = source[property]
            if (typeof sourceProperty === 'object') {
                target[property] = objectMerge(target[property], sourceProperty)
                continue
            }
            target[property] = sourceProperty
        }
    }
    return target
}

// TODO:字符串函数
/**
 * 返回浏览器地址参数，用于调用微信接口时获取code和state
 * @param {*} name
 */
export function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); // escape解密
    return null;
}

/**
 * 将json对象转为字符串参数
 * @param {*} json
 */
export function jsonToParam(json) {
    if (!json) return ''
    return cleanArray(Object.keys(json).map(key => {
        if (json[key] === undefined) return ''
        return encodeURIComponent(key) + '=' +
            encodeURIComponent(json[key])
    })).join('&')
}
/**
 * 获取浏览器地址的参数对象
 * @param {*} url
 */
export function getQueryObject(url) {
    url = url == null ? window.location.href : url
    const search = url.substring(url.lastIndexOf('?') + 1)
    const obj = {}
    const reg = /([^?&=]+)=([^?&=]*)/g
    search.replace(reg, (rs, $1, $2) => {
        const name = decodeURIComponent($1)
        let val = decodeURIComponent($2)
        val = String(val)
        obj[name] = val
        return rs
    })
    return obj
}
/**
 * 根据身份证号获取年龄
 * @param {*} identityCard
 */
export function getAge(identityCard) {
    var len = (identityCard + "").length;
    if (len == 0) {
        return 0;
    } else {
        if ((len != 15) && (len != 18)) //身份证号码只能为15位或18位其它不合法
        {
            return 0;
        }
    }
    var strBirthday = "";
    if (len == 18) //处理18位的身份证号码从号码中得到生日和性别代码
    {
        strBirthday = identityCard.substr(6, 4) + "/" + identityCard.substr(10, 2) + "/" + identityCard.substr(12, 2);
    }
    if (len == 15) {
        strBirthday = "19" + identityCard.substr(6, 2) + "/" + identityCard.substr(8, 2) + "/" + identityCard.substr(10, 2);
    }
    //时间字符串里，必须是“/”
    var birthDate = new Date(strBirthday);
    var nowDateTime = new Date();
    var age = nowDateTime.getFullYear() - birthDate.getFullYear();
    //再考虑月、天的因素;.getMonth()获取的是从0开始的，这里进行比较，不需要加1
    if (nowDateTime.getMonth() < birthDate.getMonth() || (nowDateTime.getMonth() == birthDate.getMonth() && nowDateTime.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}
/**
 * 获取浏览器的 BASE_URL
 */
export function getContextPath() {
    var pathName = document.location.pathname;
    var curWwwPath = window.document.location.href;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPath = curWwwPath.substring(0, pos);
    var length = pathName.split("/");
    var result = '';
    if (length.length > 3) {
        if (length[1] != 'index') {
            result = '/' + length[1];
        }
    }
    var url = localhostPath + result + "/";
    return (url);
};
/**
 * 生成随机的字符码
 */
export function createUniqueString() {
    const timestamp = +new Date() + ''
    const randomNum = parseInt((1 + Math.random()) * 65536) + ''
    return (+(randomNum + timestamp)).toString(32)
}

/*******************验证类***********************/

/**
 * 合法url
 * @param {*} textval
 */
export function validateURL(textval) {
    const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
    return urlregex.test(textval)
}

/**
 * 小写字母
 * @param {*} str
 */
export function validateLowerCase(str) {
    const reg = /^[a-z]+$/
    return reg.test(str)
}

/**
 * 大写字母
 * @param {*} str
 */
export function validateUpperCase(str) {
    const reg = /^[A-Z]+$/
    return reg.test(str)
}

/**
 * 大小写字母
 * @param {*} str
 */
export function validatAlphabets(str) {
    const reg = /^[A-Za-z]+$/
    return reg.test(str)
}
/**
 * 邮箱
 * @param {*} email
 */
export function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    return re.test(email)
}

// TODO:数组函数
/**
 * 数组去重
 * @param {*} arr
 */
export function unique(arr) {
    var res = [arr[0]];
    for (var i = 1; i < arr.length; i++) {
        var repeat = false;
        for (var j = 0; j < res.length; j++) {
            if (arr[i] == res[j]) {
                repeat = true;
                break;
            }
        }
        if (!repeat) {
            res.push(arr[i]);
        }
    }
    return res;
}

/**
 * 去除数组中的空值
 * @param {*} actual
 */
export function cleanArray(actual) {
    const newArray = []
    for (let i = 0; i < actual.length; i++) {
        if (actual[i]) {
            newArray.push(actual[i])
        }
    }
    return newArray
}

// TODO: 布局函数
/**
 * px转rem
 */
export function pxToRem() {
    (function (doc, win) {
        var docEl = doc.documentElement,
            resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
            recalc = function () {
                var clientWidth = docEl.clientWidth;
                if (!clientWidth) return;
                docEl.style.fontSize = 100 * (clientWidth / 750) + 'px';
            };
        if (!doc.addEventListener) return;
        win.addEventListener(resizeEvt, recalc, false);
        doc.addEventListener('DOMContentLoaded', recalc, false);
    })(document, window);
}

// 功能函数
/**
 * 打开新的页面内弹窗
 * @param {*} url
 * @param {*} title
 * @param {*} w
 * @param {*} h
 */
export function openWindow(url, title, w, h) {
    // Fixes dual-screen position                            Most browsers       Firefox
    const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left
    const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top

    const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width
    const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height

    const left = ((width / 2) - (w / 2)) + dualScreenLeft
    const top = ((height / 2) - (h / 2)) + dualScreenTop
    const newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left)

    // Puts focus on the newWindow
    if (window.focus) {
        newWindow.focus()
    }
}

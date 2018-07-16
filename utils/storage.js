// 引入事件通知
const bus = require('../event')

// 状态信息
let storageObj = {
    // 登录状态
    loginStatus : false,
    // 用户加密信息上报状态
    loginForMore : false,
    // 初始化状态信息
    init (){
        this.token = null
        this.loginStatus = false
        this.loginForMore = false
        this.sessionKey = null
        this.prevPic = null
    }
}

// 属性调用数组
let callList = []

// 描述器
let handler = {
    get: function(obj, prop) {
        // console.log(`获取【${prop}】的属性值`);
        return obj[prop];
    },
    /**
     * setter方法监听
     * @param {*} obj 对象
     * @param {*} prop 属性
     * @param {*} value 值
     */
    set(obj, prop, value) {
        // console.log(`【${prop}】 的值变更为：【${JSON.stringify(value)}】`);
        if(value && value.constructor === Object){
            // 如果下一级是个对象也添加代理功能
            obj[prop] = dep(value)
            return true
        }
        // 在当前对象上修改某个属性值的值
        obj[prop] = value
        return true
    }
}

/**
 * 递归添加
 * @param {*} obj
 * @returns
 */
const dep = (obj) => {
    return new Proxy(obj,handler)
}

let temp = new Proxy(storageObj,handler)

module.exports = temp
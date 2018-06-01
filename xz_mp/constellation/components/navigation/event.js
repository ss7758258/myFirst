// 默认可以操作的共享数据
let state = {
    len : 0
}

/**
 * 事件处理器
 */
function EventFun (){
    // 事件集合
    this.events = [];
    // 事件名称集合
    this.eventNames = [];
    // 事件对象集合
    this.eventThis = [];
}

EventFun.prototype = {
    /**
     * 创建监听事件 匹配elem对象
     * @param {string} [eventName=''] 
     * @param {any} [cb=() => {}] 
     * @param {string} [elem=''] 
     * @returns 
     */
    on (eventName = '', cb = () => {} , elem = ''){
        if(eventName !== ''){
            return false
        }
        this.events.push(eventName)
        this.eventNames.push(cb)
        this.eventThis.push(elem)
    },
    /**
     * 触发相应自定义事件
     * @param {string} [eventName=''] 
     * @param {any} [opts={}] 
     * @param {string} [elem=''] 
     */
    emit (eventName = '' , opts = {}, elem = ''){
        let me = this;
        this.eventNames.forEach( (v,ind) => {
            v === eventName && me.eventThis === elem ? me.events[ind](opts,state) : ''
        });
    },
    /**
     * 获取共享数据
     * @param {string} [key=''] 
     * @returns 
     */
    getState (key = ''){
        if(key === ''){
            return state;
        }
        let temp = undefined;
        try {
            temp = evel('state.' + key);
        } catch (error) {
            temp = undefined;
        }
        return temp
    },
    setState (key = '', val = ''){
        console.log('输入日志',key,val)
        if(key === ''){
            return false;
        }
        state[key] = val;
    }
}

let c = new EventFun();

module.exports = c;
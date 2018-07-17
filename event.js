// 默认可以操作的共享数据
let state = {
    len : 0
}

/**
 * 事件处理器
 */
function EventFun (){
    // 事件集合
    this.events = []
    // 事件名称集合
    this.eventNames = []
    // 事件对象集合
    this.eventThis = []
    // 尚未定义的事件队列集合
    this.notQueue = []
    // 尚未定义的事件队列集合名称
    this.notQueueNames = []
    // 尚未定义的事件的参数队列
    this.notQueueOpts = []
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
        if(eventName === ''){
            return false
        }
        let me = this
        this.events.push(cb)
        this.eventNames.push(eventName)
        this.eventThis.push(elem)
        // 需要移除的下标数组
        let temps = []
        // 查找队列中是否还有当前事件没有触发的
        this.notQueueNames.forEach( (v,ind) => {
            if(v === eventName && me.notQueueOpts[ind] === elem){
                temps.push(ind)
                // 将参数传递
                cb(me.notQueue[ind],state)
            }
        });
        // 遍历删除队列中的内容
        temps.forEach( (v) => {
            me.notQueue.splice(v,1)
            me.notQueueNames.splice(v,1)
            me.notQueueOpts.splice(v,1)
        })
        // 返回当前事件在列表中的下标
        return (this.eventNames.length - 1)
    },
    /**
     * 触发相应自定义事件
     * @param {string} [eventName='']
     * @param {*} [opts={}]
     * @param {string} [elem='']
     * @param {boolean} [flag=true]
     */
    emit (eventName = '' , opts = {}, elem = '',flag = true){
        let me = this
        let isflag = false
        this.eventNames.forEach( (v,ind) => {
            if(v === eventName && me.eventThis[ind] === elem){
                isflag = true
                me.events[ind](opts,state)
            }
        });
        if(!isflag && flag){
            // 放入未创建事件列表队列中
            me.notQueue.push(opts)
            me.notQueueNames.push(eventName)
            me.notQueueOpts.push(elem)
        }
    },
    /**
     * 根据下标移除
     * @param {*} [ind=-1]
     */
    remove(ind = -1){
        if(ind > -1 && ind < this.eventNames.length){
            this.events.splice(ind,1)
            this.eventNames.splice(ind,1)
            this.eventThis.splice(ind,1)
        }
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
const bus = require('../../event')

Component({
    /**
     * 组件的属性列表
     * 
     */
    properties: {
        opts : Object
    },

    /**
     * 组件的初始数据
     */
    data: {
        showLogin : true
    },
    ready(){
        let me = this
        console.log(this.data.showUpdate)
        bus.on('no-login-app',(res) => {
            me.setData({
                showLogin : true
            })
        },'app')
        
        bus.on('login-success',(res) => {
            me.setData({
                showLogin : false
            })
        },'app')
    },
    /**
     * 组件的方法列表
     */
    methods: {
        confirmUpdate(){
            let me = this
            me.setData({
                showUpdate : false
            })
            wx.showLoading({
                title: '正在为您升级',
                mask: true,
            });
            // 更新成功
            bus.emit('update_ready',{},'app')
        }
    }
})

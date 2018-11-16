// title : '个人中心',
// state : 'root',
// isRoot : false,
// isIcon : true,
// iconPath : '',
// root : '',
// isTitle : true,
// centerPath : '/pages/center/center'
// bg : 
const bus = require('../../event')
const Storage = require('../../utils/storage')
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
        status : 'back',
        path : '',
        root : '',
        isIPhoneX : false
    },
    ready(){
        getSystemInfo(this)
        // bus.on('')
    },
    /**
     * 组件的方法列表
     */
    methods: {
        /**
         * 进入个人中心
         */
        goCenter (){
            console.log(this.data.opts)
            wx.navigateTo({
                url: this.data.opts.centerPath, 
                success: function(res){
                    // success
                },
                fail: function() {
                    wx.showToast({
                        title: '跳转失败',
                    })
                },
                complete: function() {
                    // complete
                }
            })
        },
        goBack (){
            wx.navigateBack({
                delta: 1, 
                success: function(res){
                    // success
                },
                fail: function() {
                    wx.showToast({
                        title: '回退失败',
                    })
                },
                complete: function() {
                    // complete
                }
            })
        },
        goHome (){
            wx.reLaunch({
                url: this.data.opts.root + '?fromSource=nav', 
                success: function(res){
                    // success
                },
                fail: function() {
                    wx.showToast({
                        title: '返回首页失败',
                    })
                },
                complete: function() {
                    // complete
                }
            })
        }
    }
})

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self){
    self.setData({
        isIPhoneX : Storage.iPhoneX
    })
    
    self.triggerEvent('nav-height',Storage.iPhoneX ? 89 : 64)
}
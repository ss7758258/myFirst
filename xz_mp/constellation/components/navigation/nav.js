// title : '个人中心',
// state : 'root',
// isRoot : false,
// isIcon : true,
// iconPath : '',
// root : '',
// isTitle : true,
// centerPath : '/pages/center/center'
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
                url: this.data.opts.root, 
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
	let res = wx.getSystemInfoSync();
	if(res){
        console.log('验证是不是iPhone X的时候到了：',res)
        if(res.model.indexOf('iPhone X') != -1){
            self.setData({
                isIPhoneX : true
            })
        }
	}
}
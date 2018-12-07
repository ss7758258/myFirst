// title : '个人中心',
// state : 'root',
// isRoot : false,
// isIcon : true,
// iconPath : '',
// root : '',
// isTitle : true,
// centerPath : '/pages/center/center'
// bg : 
// showContent 控制slot展示
// color:'black' 返回按钮颜色控制
// fontColor: 'black' 文字颜色控制
// showPop: true  显示弹窗
// showTabbar: true 是否有tabbar
// tabbar: {} tabbar的选中配置项
// pop: {} 弹窗的配置项
const bus = require('../../event')
const Storage = require('../../utils/storage')
Component({
    
    options: {
        // 在组件定义时的选项中启用多slot支持
        multipleSlots: true
    },
    /**
     * 组件的属性列表
     * 
     */
    properties: {
        opts : Object,
        pop : Object,
        tabbar : Object
    },

    /**
     * 组件的初始数据
     */
    data: {
        status : 'back',
        path : '',
        root : '',
        isIPhoneX : false,
        height: 64
    },
    ready(){
        setSystemInfo(this)
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
                url: this.data.opts.centerPath
            })
        },
        goBack (){
            wx.navigateBack({
                delta: 1
            })
        },
        goHome (){
            wx.reLaunch({
                url: this.data.opts.root + '?fromSource=nav'
            })
        }
    }
})

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function setSystemInfo(self){
    self.setData({
        isIPhoneX : Storage.iPhoneX,
        height: Storage.iPhoneX ? 89 : 64
    })
    
    self.triggerEvent('nav-height',Storage.iPhoneX ? 89 : 64)
}
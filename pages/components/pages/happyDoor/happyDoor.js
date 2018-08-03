let $vm = getApp()
const mta = require('../../../../utils/mta_analysis.js')
const Storage = require('../../../../utils/storage')
const API = require('../../../../utils/api')
Page({
    data: {
        navConf: {
            title: '幸福大门',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true,
            bg:'#9262FB'
            // centerPath: '/pages/center/center'
        },
		xiaodaka: {
			appId: 'wx855c5d7718f218c9',
			path: '/pages/index/index?wxID=45ae12&scene=gzhgl1109804',
			openType: 'navigate',
			extra: '',
			txt: '打卡',
			version: 'release'
		},
        focus : false, // 获取焦点
        text : '',
        isOpen : false, // 门是否打开
        version : true
    },
    onUnload(){
        this.setData({
            'navConf.bg': ''
        })
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
        this.setData({
            isOpen : wx.getStorageSync('opengate') || 0,
            version : Storage.miniPro
        })
    },
    // 低版本跳转小打卡
    _goXDK(){
        wx.navigateToMiniProgram({
            appId: this.data.xiaodaka.appId,
            path: this.data.xiaodaka.path
        })
    },
    // 获取输入框的焦点
    _bindButtonTap(){
        this.setData({
            focus : true
        })
    },
    // 用户输入信息
    _getModel(e) {
        let {value : val, keyCode = 'cc'} = e.detail
        console.log(val,keyCode)
        if(val.length > 4){
            if(keyCode.toString() === '0'){
                this.setData({
                    text : val.substring(0,3)
                })
            }
            return
        }
        console.log('用户输入：', val)
        this.setData({
            text : val
        })
        if(val.length === 4){
            let str = val.substring(0,4)
            this._verSecret(str)
        }
        
    },
    // 验证秘钥是否正确
    _verSecret(key = '0000'){
        let self = this
        API.verSecret({
            notShowLoading : true,
            secretKey : key
        }).then( res => {
            console.log(res)
            // res = 1
            if(res == 1){
                wx.setStorageSync('opengate',1)
                self.setData({
                    isOpen : 1
                })
                return
            }
            wx.showToast({
                title:'秘钥错误',
                icon : 'none',
                mask : true,
                duration : 1500
            })
        }).catch(err => {
            wx.showToast({
                title:'秘钥错误',
                icon : 'none',
                mask : true,
                duration : 1500
            })
        })
    },
    // 前往关注页面
    _goGZ(){
        wx.navigateTo({
            url : '/pages/components/pages/attention/attention'
        })
    }
})
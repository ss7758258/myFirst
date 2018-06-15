const imgs = require('./imgs')
const $vm = getApp()
const api = $vm.api
Page({
    data: {
        navConf: {
            title: '个人中心',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true
            // root : '/pages/home/home' //
        },
        imgs,
        isFlag: true, //通知的开关 默认开启
        clockStatus: false, //小打卡开关
        iconPath: imgs.icon,
        nickName: ''
    },
    /**
     * 生命周期初始化组件
     * @param {any} opts 
     */
    onLoad(opts) {
        let noticeStatus = wx.getStorageSync('noticeStatus')
        let clockStatus = wx.getStorageSync('clockStatus')
        // 通知开关状态判断
        if (noticeStatus != null && noticeStatus != undefined) {
            this.setData({
                isFlag: (noticeStatus === 0 ? false : true)
            })
        }
        // 打开开关状态判断
        if (clockStatus != null && clockStatus != undefined) {
            this.setData({
                clockStatus: parseInt(clockStatus) === 0 ? false : true
            })
        }
        if (wx.getStorageSync('icon_Path')) {
            this.setData({
                iconPath: wx.getStorageSync('icon_Path')
            })
        }
        let userInfo = wx.getStorageSync('userInfo')
        if (userInfo) {
            this.setData({
                nickName: userInfo.nickName ? userInfo.nickName : ''
            })
        }
    },

    // 运势提醒切换
    handleRemindToggle(e) {
        let isFlag = this.data.isFlag //是否开启推送 true：开启，false：关闭
        let noticeStatus = isFlag ? 1 : 0 //是否开启推送 1：开启，0：关闭
        this.setData({
            isFlag: !isFlag
        })
        api.setUserSetting({
            noticeStatus,
            notShowLoading: !!noticeStatus
        }).then((res) => {
            noticeStatus = !isFlag ? 1 : 0
            this.setData({ isFlag: !isFlag })
            wx.setStorageSync('noticeStatus', noticeStatus)
        })
    },
    // 点击小打卡
    handleClockinClick(e) {
        wx.navigateToMiniProgram({
            appId: 'wx855c5d7718f218c9',
            path: '/pages/index/index?wxID=ad6377&scene=gzhgl922689'
        })
    },
    // 点击小星星
    handleWalletClick(e) {
        wx.navigateTo({
            url: '/pages/myAccount/myAccount'
        })
    },
    // 点击我要吐槽
    handleFeedbackClick: function () {
        // 跳转至“腾讯吐个槽”
        wx.navigateToMiniProgram({
            appId: 'wx8abaf00ee8c3202e',
            path: '/pages/index-v2/index-v2',
            extraData: {
                id: '29914'
            }
            // envVersion: 'develop',
        })
    }
})
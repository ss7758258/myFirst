const imgs = require('./imgs')
const $vm = getApp()
const api = $vm.api
const Storage = require('../../utils/storage')

Page({
    data : {
		navConf : {
			title : '个人中心',
			state : 'root',
			isRoot : false,
			isIcon : true,
			iconPath : '',
            root : '',
            isTitle : true
            // root : '/pages/home/home'
        },
        imgs,
        isFlag : false,  //通知的开关 默认关闭
        clockStatus : false,  //小打卡开关
        iconPath : imgs.icon,
        nickName : '',
        showOpen : true,
        starNum : 0,// 星星数量
        version : true,
        miniConf:{
            appId: 'wx8abaf00ee8c3202e',
            path: '/pages/index-v2/index-v2',
			openType: 'navigate',
            extraData: {
                id: '29914'
            },
			version: 'release'
        }
    },
    /**
     * 生命周期初始化组件
     * @param {any} opts 
     */
    onLoad (opts){
        let noticeStatus = wx.getStorageSync('noticeStatus');
        let clockStatus = wx.getStorageSync('clockStatus');
        
        // 小星星  ios上关闭打开
        // if(Storage.sys === 'ios'){
        //     this.setData({
        //         showOpen : Storage.openIos === 1
        //     })
        // }else{
        //     this.setData({
        //         showOpen : Storage.openAndriod === 1
        //     })
        // }

        console.log(noticeStatus === 0 ? false : true)
        // 通知开关状态判断
        if(noticeStatus != null &&  noticeStatus != undefined){
            this.setData({
                isFlag : (noticeStatus === 0 ? false : true)
            })
        }
        // 打开开关状态判断
        if(clockStatus != null &&  clockStatus != undefined){
            this.setData({
                clockStatus : parseInt(clockStatus) === 0 ? false : true
            })
        }
        if(wx.getStorageSync('icon_Path')) {
            this.setData({
                iconPath: wx.getStorageSync('icon_Path')
            })
        }
        console.log(wx.getStorageSync('userInfo').nickName)
        let userInfo = Storage.userInfo
        if(userInfo){
            this.setData({
                nickName: userInfo.nickName ? userInfo.nickName : ''
            })
        }
        this._getBlance();
    },

    onShow(){
        console.log('冷启动')
        // 激活下刷新金额
        this._getBlance();
        this.setData({
            version : Storage.miniPro
        })
    },
    switchOn (e){
        console.log('触发？：',e)
        this.setData({
            isFlag : !this.data.isFlag
        })
        // 开关状态转化0 1 
        let ye = this.data.isFlag ? 1 : 0;
        
        api.setUserSetting({
            noticeStatus : ye,
            notShowLoading : true
        }).then((res) => {
            console.log('通知开关成功：',res)
            if(!res){
                this.setData({
                    isFlag : !this.data.isFlag
                })
                wx.setStorageSync('noticeStatus', this.data.isFlag ? 1 : 0)
                return false;
            }
            wx.setStorageSync('noticeStatus', this.data.isFlag ? 1 : 0)
        }).catch( () => {
            console.log('通知开关失败：',res)
            this.setData({
                isFlag : !this.data.isFlag
            })
            wx.setStorageSync('noticeStatus', this.data.isFlag ? 1 : 0)
        })
    },
    /**
     * 前往小打卡
     * @param {any} e 
     */
    goXiaodaka (e){
        wx.navigateToMiniProgram({
            appId: 'wx855c5d7718f218c9',
            path: '/pages/index/index?wxID=ad6377&scene=gzhgl922689',
            success(res) {
                // 打开成功
            }
        })
    },
    /**
     * 前往吐个槽
     * @param {any} e 
     */
    clickHandle (e){
        console.log('事件',e)
        wx.navigateToMiniProgram({
            appId: 'wx8abaf00ee8c3202e',
            path: '/pages/index-v2/index-v2',
            extraData: {
                id: '29914'
            },
            // envVersion: 'develop',
            success(res) {
                // 打开成功
            }
        })
    },
    /**
     * 前往充值页面
     */
    goPay(){
        wx.navigateTo({
            url: '/pages/myAccount/myAccount',
            success: function(res){
                // success
            },
            fail: function() {
                // fail
            },
            complete: function() {
                // complete
            }
        })
    },
    
	/**
     * 获取用户钱包信息
     */
    _getBlance() {
		api.getBlance({notShowLoading:true}).then(res => {
			console.log('获取钱包信息：',res)
			this.setData({
				starNum : res.balance
			})
		})
	}
})
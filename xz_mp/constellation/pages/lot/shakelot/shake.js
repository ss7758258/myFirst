// pages/lot/shakelot/shake.js

let $vm = getApp()
let _GData = $vm.globalData;
const { parseLot } = $vm.utils
const getUserInfo = $vm.utils.wxPromisify(wx.getUserInfo)
const Storage = require('../../../utils/storage')
var mta = require('../../../utils/mta_analysis.js')
let imgs = require('./imgs.js')

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;

Page({

    /**
     * 页面的初始数据
     */
    data: {
        isLoading: false,
        hasReturn: false,
        hasAuthorize: true,
        isFromShare: false,
        isOther: false,
        //摇签状态 
        shakeLotSpeed: false,
        potPath: false,
        userInfo: _GData.userInfo,
        imgs: imgs,
        dot: false,
        navConf: {
            title: '摇一摇抽签',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true
            // root : '/pages/home/home'
        },
        fromPage:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        mta.Page.init()
        console.log('输出参数：', options)
        // 来源统计
        parseFrom(this,options)

        const _self = this
        const _SData = this.data
        
        _self.setData({
            userInfo: _GData.userInfo,
            fromPage:  options.from || ''
        })
        let login_timer = setInterval(() => {
			if(!Storage.loginStatus){
				return false
			}
			// 清除等待
			clearInterval(login_timer)
            wx.getUserInfo({
                success: function (res) {
                    console.log('是否授权成功===================================',res)
                    console.log(res)
                    if (res.userInfo) {
                        wx.setStorage({
                            key: 'userInfo',
                            data: res.userInfo,
                        })
                        if(!Storage.loginForMore){
                            // 上报用户加密信息
                            $vm.api.loginForMore({
                                encryptedData: res.encryptedData,
                                iv: res.iv,
                                sessionKey : Storage.sessionKey,
                                nickName: res.userInfo.nickName,
                                headImage: res.userInfo.avatarUrl,
                                notShowLoading: true,
                            }).then(result => {
                                // 确定用户信息已经上报
                                Storage.loginForMore = true
                            }).catch(err => {
                                Storage.loginForMore = false
                                wx.redirectTo({
                                    url: '/pages/checklogin/checklogin?from=' + _SData.fromPage + '&and=shake'
                                })
                            })
                        }
                        
                        _GData.userInfo = res.userInfo
                        _self.setData({
                            userInfo: _GData.userInfo
                        })
                        // 获取一签盒数据状态
                        getX510(_self);
                    }
                },
                fail: function (res) {
                    console.log('是否授权失败===================================',res)
                    // 查看是否授权
                    wx.getSetting({
                        success: function (res) {
                            if (res.authSetting || !res.authSetting['scope.userInfo']) {
                                _self.setData({
                                    hasAuthorize: false
                                })
                                console.log('=====' + _SData.fromPage)
                                wx.redirectTo({
                                    url: '/pages/checklogin/checklogin?from=' + _SData.fromPage + '&and=shake'
                                })
                            }
                        }
                    })
                }
            })
        },200)
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.shakeFun()
        this.setData({
          hasReturn: false,
        })
        // 获取一签盒数据状态
        getX510(this);
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
        wx.stopAccelerometer({

        })
        this.setData({
          hasReturn: true,
        })
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {
        this.setData({
            hasReturn: true,
        })
        wx.stopAccelerometer({

        })
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

        var shareImg = '/assets/images/share_tong.jpg'
        var shareMsg = '每日抽一签，赛过活神仙。'
        var sharepath = '/pages/lot/shakelot/shake?from=share&where=shake'
        return {
            title: shareMsg,
            imageUrl: shareImg,
            path: sharepath,
            success: function (res) {
                // 转发成功
            },
            fail: function (res) {
                // 转发失败
            }
        }
    },
    drawLots: function () {
        _GData = $vm.globalData;
        mta.Event.stat("ico_shake_shake", {})
        const _self = this
        const _SData = this.data
        if (_self.data.shakeLotSpeed) {
            return
        }
        if (_self.data.hasReturn || _self.data.isLoading || (!_self.data.hasAuthorize)) {
            return
        }
        const innerAudioContext = wx.createInnerAudioContext()
        innerAudioContext.autoplay = true
        innerAudioContext.src = '/assets/shake.mp3'
        innerAudioContext.onPlay(() => {
            console.log('开始播放')
        })

        // 加快 摇动速度
        this.setData({
            shakeLotSpeed: true,
            potPath: true,
            isLoading: true
        })

        // 获取摇签数据
        if(wx.getStorageSync('token')){
            // 拉取摇签数据
            getX504(_self,_SData)
        }else{
            // 重新登录拉取
            againLogin(_self,_GData,function(){
                getX504(_self,_SData)
            })
        }

    },
    shakeFun: function () { // 摇一摇方法封装
        const _self = this
        var numX = 0.15 //x轴
        var numY = 0.15 // y轴
        var numZ = 0.15 // z轴
        var stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
        var positivenum = 0 //正数 摇一摇总数

        wx.onAccelerometerChange(function (res) { //小程序api 加速度计
            
            if (_self.data.hasReturn || _self.data.isLoading) {
                return
            }

            if (numX < res.x && numY < res.y) { //个人看法，一次正数算摇一次，还有更复杂的
                positivenum++
                setTimeout(() => { positivenum = 0 }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (numZ < res.z && numY < res.y) { //可以上下摇，上面的是左右摇
                positivenum++
                setTimeout(() => { positivenum = 0 }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (positivenum == 1 && stsw) { //是否摇了指定的次数，执行成功后的操作
                stsw = false

                _self.drawLots()
                console.log('摇一摇成功')
                wx.stopAccelerometer({})
                setTimeout(() => {
                    positivenum = 0 // 摇一摇总数，重新0开始，计算
                    stsw = true
                }, 2000)
            }
        })
    },
    // 显示我的签列表
    showLotList: function (e) {
        let formid = e.detail.formId

        mta.Event.stat("ico_shake_to_list", {})
        $vm.api.getX610({ notShowLoading: true, formid: formid })
        wx.navigateTo({
            url: '/pages/lot/lotlist/lotlist?formid=' + formid
          // url: '/pages/lot/aSignWall/aSignWall?formid=' + formid
        })
    },
    //分享的返回主页
    onclickHome: function (e) {
        let formid = e.detail.formId

        mta.Event.stat("ico_shake_home", {})
        $vm.api.getX610({ notShowLoading: true, formid: formid })
        wx.reLaunch({
            url: '/pages/home/home',
        })
    },
    bindGetUserInfo: function (e) {
        if (e.detail.userInfo) {
            wx.setStorage({
                key: 'userInfo',
                data: e.detail.userInfo,
            })
            this.setData({
                hasAuthorize: true
            })
            _GData.userInfo = e.detail.userInfo
            $vm.api.getSelectx100({
                nickName: e.detail.userInfo.nickName,
                headImage: e.detail.userInfo.avatarUrl,
                notShowLoading: true,
            }).then(res => {

            })
        }

    },
    
})

/**
 * 获取一签盒列表数据
 * @param {number} [pageNum=1]
 * @param {number} [pageSize=10]
 */
const getX510 = (self, pageNum = 1, pageSize = 10) => {
    let clicks = wx.getStorageSync('click_list') || [];
    $vm.api.getX510({ notShowLoading : true,pageNum, pageSize }).then(res => {
        console.log('一签盒列表：', res)
        if (res && res.constructor === Array) {
            let red_dot = false;
            res.forEach(v => {
                // 如果有状态为0并且点击列表中又不存在点击行为的确定为未点击状态
                if (v.status === 0 && clicks.indexOf(v.id) === -1) {
                    red_dot = true
                }
            })

            // 设置红点是否显示状态
            self.setData({
                dot: red_dot
            })
            // 本地缓存下数据
            wx.setStorageSync('sign_lists', res)
            return false;
        }
        // 本地缓存下数据
        wx.setStorageSync('sign_lists', '')
    })
}

// 发送请求次数
let sendLen = 0;

// 重置请求次数
const resetLen = () => {
    sendLen = 0;
}
/**
 * 获取签的数据信息
 * @param {*} _self
 * @param {*} _SData
 */
const getX504 = (_self,_SData) => {
    if(sendLen > 1) return (sendLen = 0)
    $vm.api.getX504({ notShowLoading: true, })
    .then(res => {
        // throw err = new Error('自定义错误')
        if (_self.data.hasReturn) {
            return
        }
        _self.setData({
            isLoading: false
        })
        res.isMyQian = 1
        res.alreadyOpen = 1
        var lotDetail = parseLot(res)

        _GData.lotDetail = lotDetail
        // throw err = new Error('312')
        if (res.status === 0) {
            sendLen = 0
            setTimeout(() => {
                if (_self.data.hasReturn) {
                    return
                }
                // 摇出一个签
                _self.setData({
                    shakeLotSpeed: false
                })

                if (_SData.isFromShare) {
                    wx.navigateTo({
                        url: '/pages/lot/lotdetail/lotdetail?sound=1',
                    })
                } else {
                    wx.redirectTo({
                        url: '/pages/lot/lotdetail/lotdetail?sound=1',
                    })
                }
            }, 1500)
        } else if (res.status == 1) { //没有签了
            sendLen = 0
            setTimeout(() => {
                if (_self.data.hasReturn) {
                    return
                }
                // 摇出一个签
                _self.setData({
                    shakeLotSpeed: false
                })

                if (_SData.isFromShare) {
                    wx.navigateTo({
                        url: '/pages/lot/emptylot/emptylot',
                    })
                } else {
                    wx.redirectTo({
                        url: '/pages/lot/emptylot/emptylot',
                    })
                }
            }, 1000)
        } else {
            sendLen++
            if(sendLen > 1){
                sendLen = 0
                setTimeout(() => {
                    // 变更UI状态
                    _self.setData({
                        potPath: false,
                        isLoading: false,
                        shakeLotSpeed: false
                    })
                    
                    // 重新登录拉取
                    againLogin(_self,_GData,function(){
                        getX504(_self,_SData)
                    })
                    _self.shakeFun()
                }, 1000)
                return
            }
            getX504(_self,_SData)
        }
    })
    .catch(err => {
        sendLen++
        if(sendLen > 1){
            sendLen = 0
            setTimeout(() => {
                // 变更UI状态
                _self.setData({
                    potPath: false,
                    isLoading: false,
                    shakeLotSpeed: false
                })
                // 重新登录拉取
                againLogin(_self,_GData,function(){
                    console.log('静默登录')
                })
                _self.shakeFun()
            }, 1000)
            return
        }
        getX504(_self,_SData)
    })
}

/**
 * 来源统计
 * @param {*} self
 * @param {*} options
 */
function parseFrom(self,options){
    let pageFrom = options.from
    if (pageFrom == 'share') {
        self.setData({
            isFromShare: true,
            "navConf.root": '/pages/home/home'
        })
        if (pageFrom == 'list') {
            mta.Event.stat("ico_in_from_list", {})
        } else if (pageFrom == 'detail') {
            mta.Event.stat("ico_in_from_detail", {})
        } else if (pageFrom == 'shake') {
            if (options.hotapp == 1) {
                mta.Event.stat("ico_in_from_shake_qrcode", {})
            } else {
                mta.Event.stat("ico_in_from_shake", {})
            }
        }

    } else if (pageFrom == 'activity') {
        self.setData({
            isFromShare: true,
            "navConf.root": '/pages/home/home'
        })
        console.log('ico_in_from_shake_activity')
        mta.Event.stat("ico_in_from_shake_activity", {})
    } else if (pageFrom == 'outer' && options.id) {
        self.setData({
            isFromShare: true,
            "navConf.root": '/pages/home/home'
        })
        if (reg.test(options.id)) {
            mta.Event.stat('outer_' + options.id, {})
        } else {
            mta.Event.stat('outer_unknown', {})
        }
    } else if(pageFrom === 'spread'){ // 活动推广统计
        self.setData({
            isFromShare: true,
            "navConf.root": '/pages/home/home'
        })
        console.log('输出活动来源',options.id)
        if (reg.test(options.id)) {
            mta.Event.stat('spread_' + options.id, {})
        } else {
            mta.Event.stat('spread_unknown', {})
        }
    }
    // 统计特殊来源
    if(options.source && options.source.constructor === String && options.source !== ''){
        self.setData({
            isFromShare: true,
            "navConf.root": '/pages/home/home'
        })
        console.log('输出活动来源',options.id)
        if (reg.test(options.id)) {
            mta.Event.stat(options.source + '_' + options.id, {})
        } else {
            mta.Event.stat(options.source + '_unknown', {})
        }
    }
}
/**
 * 重新登录
 * @param {*} self
 * @param {*} GData
 */
function againLogin(self,GData,cb){
    wx.getNetworkType({
		success: function(res) {
			console.log('输出当前网络状态：',res)
			if(res.networkType === 'none'){
				wx.hideLoading()
				wx.showModal({
					title: '网络异常',
					content: '非常抱歉，小主您的网络尚未打开',
					showCancel: false,
					confirmText: '稍后再试',
					confirmColor: '#3CC51F',
					success: res => {
                        // 变更UI状态
                        self.setData({
                            potPath: false,
                            isLoading: false,
                            shakeLotSpeed: false
                        })
					}
				});
            }else{
                // 重新进行登录
                $vm.getLogin().then(data => {
                    console.log(data)
                    wx.setStorageSync('token', data.token)
                    // 登录状态
                    Storage.loginStatus = true
                    Storage.sessionKey = data.sessionKey
                    wx.getUserInfo({
                        success: function (res) {
                            console.log(res)
                            if (res.userInfo) {
                                wx.setStorage({
                                    key: 'userInfo',
                                    data: res.userInfo,
                                })
                                // 上报用户加密信息
                                $vm.api.loginForMore({
                                    encryptedData: res.encryptedData,
                                    iv: res.iv,
                                    sessionKey : Storage.sessionKey,
                                    nickName: res.userInfo.nickName,
                                    headImage: res.userInfo.avatarUrl,
                                    notShowLoading: true,
                                }).then(result => {
                                    // 确定用户信息已经上报
                                    Storage.loginForMore = true
                                    cb && cb.constructor === Function ? cb() : ''
                                }).catch(err => {
                                    Storage.loginForMore = false
                                    wx.showModal({
                                        title: '网络开小差了',
                                        content: '小主，请您检查网络后再试',
                                        showCancel: false,
                                        confirmText: '再试一次',
                                        success: function (res) {
                                            // 变更UI状态
                                            self.setData({
                                                potPath: false,
                                                isLoading: false,
                                                shakeLotSpeed: false
                                            })
                                        }
                                    })
                                })
                                $vm.api.getSelectx100({
                                    constellationId: GData.selectConstellation.id,
                                    nickName: res.userInfo.nickName,
                                    headImage: res.userInfo.avatarUrl,
                                    notShowLoading: true,
                                }).then(res => {
                
                                })
                                GData.userInfo = res.userInfo
                                self.setData({
                                    userInfo: GData.userInfo
                                })
                                
                            }
                        },
                        fail: function (res) {
                            wx.redirectTo({
                                url: '/pages/checklogin/checklogin'
                            })
                        }
                    })
                }).catch(err => {
                    wx.showModal({
                        title: '网络开小差了',
                        content: '小主，请您检查网络后再试',
                        showCancel: false,
                        confirmText: '再试一次',
                        success: function (res) {
                            // 变更UI状态
                            self.setData({
                                potPath: false,
                                isLoading: false,
                                shakeLotSpeed: false
                            })
                        }
                    })
                })
            }
        }
    })
    
}
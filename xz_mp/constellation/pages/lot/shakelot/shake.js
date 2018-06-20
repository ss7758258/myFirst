// pages/lot/shakelot/shake.js

const $vm = getApp()
const _GData = $vm.globalData;
const {
    parseLot
} = $vm.utils
var mta = require('../../../utils/mta_analysis.js')
let imgs = require('./imgs.js')

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;

Page({

    // 页面的初始数据
    data: {
        isLoading: false,
        hasReturn: false,
        hasAuthorize: true,
        isFromShare: false,
        isOther: false,
        shakeLotSpeed: false, //摇签状态,是否在摇动 
        potPath: false,
        userInfo: _GData.userInfo,
        isShaking:false, // 是否正在摇
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
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad (options) {
        mta.Page.init()
        let fromPage = options.from || ''
        this.setData({
            fromPage
        })
        if (fromPage == 'share') {
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            if (fromPage == 'list') {
                mta.Event.stat("ico_in_from_list", {})
            } else if (fromPage == 'detail') {
                mta.Event.stat("ico_in_from_detail", {})
            } else if (fromPage == 'shake') {
                if (options.hotapp == 1) {
                    mta.Event.stat("ico_in_from_shake_qrcode", {})
                } else {
                    mta.Event.stat("ico_in_from_shake", {})
                }
            }

        } else if (fromPage == 'activity') {
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            mta.Event.stat("ico_in_from_shake_activity", {})
        } else if (fromPage == 'outer' && options.id) {
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            if (reg.test(options.id)) {
                mta.Event.stat('outer_' + options.id, {})
            } else {
                mta.Event.stat('outer_unknown', {})
            }
        } else if (fromPage === 'spread') { // 活动推广统计
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            if (reg.test(options.id)) {
                mta.Event.stat('spread_' + options.id, {})
            } else {
                mta.Event.stat('spread_unknown', {})
            }
        }
        // 统计特殊来源
        if (options.source && options.source.constructor === String && options.source !== '') {
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            if (reg.test(options.id)) {
                mta.Event.stat(options.source + '_' + options.id, {})
            } else {
                mta.Event.stat(options.source + '_unknown', {})
            }
        }

        const _self = this
        const _SData = this.data

        this.setData({
            userInfo: _GData.userInfo,
            shakeLotSpeed:false
        })

        wx.getUserInfo({
            success: (res)=> {
                if (res.userInfo) {
                    wx.setStorage({
                        key: 'userInfo',
                        data: res.userInfo,
                    })

                    _GData.userInfo = res.userInfo
                    _self.setData({
                        userInfo: _GData.userInfo
                    })
                    // 获取一签盒数据状态
                    $vm.api.getSelectx100({
                        constellationId: _GData.selectConstellation.id,
                        nickName: res.userInfo.nickName,
                        headImage: res.userInfo.avatarUrl,
                        notShowLoading: true,
                    })
                }
            },
            fail: (res)=> {
                // 查看是否授权
                wx.getSetting({
                    success: (res)=> {
                        if (!res.authSetting['scope.userInfo']) {
                            this.setData({
                                hasAuthorize: false
                            })
                            wx.redirectTo({
                                url: '/pages/checklogin/checklogin?from=' + _SData.fromPage + '&and=shake'
                            })
                        }
                    }
                })
            }
        })
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow () {
        this.shakeFun()
        this.setData({
            hasReturn: false,
            potPath: false,
            shakeLotSpeed:false,  // 摇晃回正
            "navConf.isIcon":true // 显示返回
        })
        // 获取一签盒数据状态
        getX510(this)
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
        wx.stopAccelerometer({ })
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
        wx.stopAccelerometer({ })
    },
    
    //用户点击右上角分享
    onShareAppMessage () {
        const shareImg = '/assets/images/share_tong.jpg'
        const shareMsg = '每日抽一签，赛过活神仙。'
        const sharepath = '/pages/lot/shakelot/shake?from=share&where=shake'
        return {
            title: shareMsg,
            imageUrl: shareImg,
            path: sharepath
        }
    },

    handleShakingClick() {
        mta.Event.stat("ico_shake_shake", {})
        const _self = this
        const _SData = this.data
        // if (_SData.shakeLotSpeed||_SData.hasReturn || _SData.isLoading || !_SData.hasAuthorize)  return
        console.log('是否摇动了？？',_self.data.shakeLotSpeed,_self.data.hasReturn, _self.data.isLoading,!_self.data.hasAuthorize)
       
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
            isLoading: true,
            "navConf.isIcon":false
        })

        // 获取摇签数据
        if (wx.getStorageSync('token')) {
            // 拉取摇签数据
            getX504(_self, _SData)
        } else {
            $vm.getLogin().then(res => {
                wx.setStorageSync('token', res.token)
                // 拉取摇签数据
                getX504(_self, _SData)
            }).catch(err => {
                $vm.getLogin().then(res => {
                    wx.setStorageSync('token', res.token)
                    // 拉取摇签数据
                    getX504(_self, _SData)
                })
            })
        }

    },
    shakeFun() { // 摇一摇方法封装

        const _SData = this.data
        const numX = 0.15 //x轴
        const numY = 0.15 // y轴
        const numZ = 0.15 // z轴
        let _this = this
        let positivenum = 0 //正数 摇一摇总数

        wx.onAccelerometerChange((res) => { //小程序api 加速度计
            if (_SData.hasReturn || _SData.isLoading) return

            if ((numX < res.x && numY < res.y) || (numZ < res.z && numY < res.y)) { //左右摇 或者上下摇
                positivenum++
                setTimeout(() => {
                    positivenum = 0
                }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (positivenum === 1) { //是否摇了指定的次数，执行成功后的操作
                _this.handleShakingClick()
                console.log('摇动了没',_this)
                wx.stopAccelerometer({})
                setTimeout(() => {
                    positivenum = 0 // 摇一摇总数，重新0开始，计算
                    _this.setData( {shakeLotSpeed:false} )
                }, 2000)
            }
        })
    },
    // 显示我的签列表
    showLotList: function (e) {
        let formid = e.detail.formId

        mta.Event.stat("ico_shake_to_list", {})
        $vm.api.getX610({
            notShowLoading: true,
            formid: formid
        })
        wx.navigateTo({
            url: '/pages/lot/lotlist/lotlist?formid=' + formid
        })
    },
    //分享的返回主页
    onclickHome: function (e) {
        let formid = e.detail.formId

        mta.Event.stat("ico_shake_home", {})
        $vm.api.getX610({
            notShowLoading: true,
            formid: formid
        })
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
    $vm.api.getX510({
        notShowLoading: true,
        pageNum,
        pageSize
    }).then(res => {
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
const getX504 = (_self, _SData) => {
    if (sendLen > 1) return (sendLen = 0)
    $vm.api.getX504({
            notShowLoading: true,
        })
        .then(res => {
            
            if (_self.data.hasReturn) {
                return
            }
            _self.setData({
                isLoading: false
            })
            if (!res) {
                wx.navigateTo({
                    url: '/pages/lot/lotlist/lotlist'
                })
                return
            }
            res.isMyQian = 1
            res.alreadyOpen = 1
            var lotDetail = parseLot(res)

            _GData.lotDetail = lotDetail

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
                if (sendLen > 1) {
                    sendLen = 0
                    setTimeout(() => {
                        wx.showModal({
                            title: '网络开小差了',
                            content: '小主，请您检查网络后再试',
                            showCancel: false,
                            confirmText: '再试一次',
                            success: function (res) {},
                            fail: function (res) {},
                            complete: function (res) {},
                        })
                        // 变更UI状态
                        _self.setData({
                            potPath: false,
                            isLoading: false,
                            shakeLotSpeed: false
                        })
                        _self.shakeFun()
                    }, 1000)
                    return
                }
                getX504(_self, _SData)
            }
        })
        .catch(err => {
            sendLen++
            if (sendLen > 1) {
                sendLen = 0
                setTimeout(() => {
                    console.log('err',err)
                    wx.showModal({
                        title: '网络开小差了',
                        content: '小主，请您检查网络后再试',
                        showCancel: false,
                        confirmText: '再试一次'
                    })
                    // 变更UI状态
                    _self.setData({
                        potPath: false,
                        isLoading: false,
                        shakeLotSpeed: false
                    })
                    _self.shakeFun()
                }, 1000)
                return
            }
            getX504(_self, _SData)
        })
}
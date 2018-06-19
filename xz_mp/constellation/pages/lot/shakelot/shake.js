// pages/lot/shakelot/shake.js

const $vm = getApp()
const _GData = $vm.globalData
const { parseLot } = $vm.utils
// const getUserInfo = $vm.utils.wxPromisify(wx.getUserInfo)
const mta = require('../../../utils/mta_analysis.js')

const imgs = require('./imgs.js')
const reg = /^\d{6}$/ // 验证Id是否位6位纯数字

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
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
        mta.Page.init()
        let pageFrom = options.from || '' // 页面来源
        let { id ,hotapp,source} = options
        let isPureNumber = reg.test(id)

        this.setData({
            fromPage: pageFrom
        })
        switch (pageFrom) {
            case 'share':
                this.setData({
                    isFromShare: true,
                    "navConf.root": '/pages/home/home'
                })
                switch (pageFrom) {
                    case 'list':
                        mta.Event.stat("ico_in_from_list", {})
                    case 'detail':
                        mta.Event.stat("ico_in_from_detail", {})
                    case 'shake':
                        mta.Event.stat(hotapp === 1 ? "ico_in_from_shake_qrcode" : "ico_in_from_shake", {})
                }
            case 'activity':
                this.setData({
                    isFromShare: true,
                    "navConf.root": '/pages/home/home'
                })
                mta.Event.stat("ico_in_from_shake_activity", {})
                switch (pageFrom) {
                    case 'list':
                        mta.Event.stat("ico_in_from_list", {})
                    case 'detail':
                        mta.Event.stat("ico_in_from_detail", {})
                    case 'shake':
                        mta.Event.stat(hotapp === 1 ? "ico_in_from_shake_qrcode" : "ico_in_from_shake", {})
                }
            case 'outer':
                if (id) {
                    this.setData({
                        isFromShare: true,
                        "navConf.root": '/pages/home/home'
                    })
                }
                mta.Event.stat(isPureNumber ? `outer_${id}` : `outer_unknown`, {})
            case 'spread':
                this.setData({
                    isFromShare: true,
                    "navConf.root": '/pages/home/home'
                })
                mta.Event.stat(isPureNumber ? `spread_${id}` : `spread_unknown`, {})
        }

        // if (pageFrom === 'share') {
        //     this.setData({
        //         isFromShare: true,
        //         "navConf.root": '/pages/home/home'
        //     })
        //     if (pageFrom == 'list') {
        //         mta.Event.stat("ico_in_from_list", {})
        //     } else if (pageFrom == 'detail') {
        //         mta.Event.stat("ico_in_from_detail", {})
        //     } else if (pageFrom == 'shake') {
        //         if (options.hotapp == 1) {
        //             mta.Event.stat("ico_in_from_shake_qrcode", {})
        //         } else {
        //             mta.Event.stat("ico_in_from_shake", {})
        //         }
        //     }

        // } else if (pageFrom == 'activity') {
        //     this.setData({
        //         isFromShare: true,
        //         "navConf.root": '/pages/home/home'
        //     })
        //     mta.Event.stat("ico_in_from_shake_activity", {})
        // } else if (pageFrom == 'outer' && options.id) {
        //     this.setData({
        //         isFromShare: true,
        //         "navConf.root": '/pages/home/home'
        //     })
        //     if (reg.test(options.id)) {
        //         mta.Event.stat('outer_' + options.id, {})
        //     } else {
        //         mta.Event.stat('outer_unknown', {})
        //     }
        // } else if (pageFrom === 'spread') { // 活动推广统计
        //     this.setData({
        //         isFromShare: true,
        //         "navConf.root": '/pages/home/home'
        //     })
        //     if (reg.test(options.id)) {
        //         mta.Event.stat('spread_' + options.id, {})
        //     } else {
        //         mta.Event.stat('spread_unknown', {})
        //     }
        // }


        // 统计特殊来源
        if (source && source.constructor === String) {
            this.setData({
                isFromShare: true,
                "navConf.root": '/pages/home/home'
            })
            mta.Event.stat(isPureNumber?`${source}_${id}`:source, {})
        }

        this.setData({
            userInfo: _GData.userInfo
        })

        wx.getUserInfo({
            success: (res) => {
                if (res.userInfo) {
                    wx.setStorage({
                        key: 'userInfo',
                        data: res.userInfo,
                    })

                    _GData.userInfo = res.userInfo
                    this.setData({
                        userInfo: _GData.userInfo
                    })
                    // 获取一签盒数据状态
                    // getX510(_self)
                    $vm.api.getSelectx100({
                        constellationId: _GData.selectConstellation.id,
                        nickName: res.userInfo.nickName,
                        headImage: res.userInfo.avatarUrl,
                        notShowLoading: true,
                    })
                }
            },
            fail: () => {
                // 查看是否授权
                wx.getSetting({
                    success: (res) => {
                        if (!res.authSetting['scope.userInfo']) {
                            this.setData({
                                hasAuthorize: false
                            })
                            wx.redirectTo({
                                url: '/pages/checklogin/checklogin?from=' + this.data.fromPage + '&and=shake'
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
    onShow() {
        this.shakeFun()
        this.setData({
            hasReturn: false,
        })
        // 获取一签盒数据状态
        getX510(this)
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {
        wx.stopAccelerometer({

        })
        this.setData({
            hasReturn: true,
        })
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {
        this.setData({
            hasReturn: true,
        })
        wx.stopAccelerometer({

        })
    },
    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

        const shareImg = '/assets/images/share_tong.jpg'
        const shareMsg = '每日抽一签，赛过活神仙。'
        const sharepath = '/pages/lot/shakelot/shake?from=share&where=shake'
        return {
            title: shareMsg,
            imageUrl: shareImg,
            path: sharepath
        }
    },
    drawLots() {
        mta.Event.stat("ico_shake_shake", {})
        const _self = this
        const _SData = this.data
        const {
            shakeLotSpeed,
            hasReturn,
            isLoading,
            hasAuthorize
        } = _SData
        if (shakeLotSpeed || hasReturn || isLoading || !hasAuthorize) return

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

        $vm.api.getX504({
                notShowLoading: true,
            })
            .then(res => {
                if (_SData.hasReturn) return
                this.setData({
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
                let lotDetail = parseLot(res)

                _GData.lotDetail = lotDetail

                if (res.status === 0) {
                    setTimeout(() => {
                        if (_SData.hasReturn) return
                        // 摇出一个签
                        this.setData({
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
                    }, 1000)
                } else if (res.status === 1) { //没有签了
                    setTimeout(() => {
                        if (_SData.hasReturn) return
                        // 摇出一个签
                        this.setData({
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
                    setTimeout(() => {
                        wx.showModal({
                            title: '网络开小差了',
                            content: '请您检查网络后再试',
                            showCancel: false,
                            confirmText: '再摇一次',
                            success: function (res) {},
                            fail: function (res) {},
                            complete: function (res) {},
                        })

                        this.setData({
                            potPath: false,
                            isLoading: false,
                            shakeLotSpeed: false
                        })
                    }, 1000)
                }
            })
            .catch(err => {
                $vm.getLogin().then(res => {
                    console.log(res)
                    wx.setStorage({
                        key: 'token',
                        data: res.token
                    })
                }).catch(err => {
                    wx.showToast({
                        title: err,
                        icon: 'none'
                    })
                })
                setTimeout(() => {
                    wx.showModal({
                        title: '网络开小差了',
                        content: '请您检查网络后再试',
                        showCancel: false,
                        confirmText: '再摇一次'
                    })

                    this.setData({
                        potPath: false,
                        isLoading: false,
                        shakeLotSpeed: false
                    })
                }, 1000)
            })

    },
    // drawLots () {

    //     mta.Event.stat("ico_shake_shake", {})
    //     const _self = this
    //     const _SData = this.data
    //     if (_self.data.shakeLotSpeed) {
    //         return
    //     }
    //     if (_self.data.hasReturn || _self.data.isLoading || (!_self.data.hasAuthorize)) {
    //         return
    //     }
    //     const innerAudioContext = wx.createInnerAudioContext()
    //     innerAudioContext.autoplay = true
    //     innerAudioContext.src = '/assets/shake.mp3'
    //     innerAudioContext.onPlay(() => {
    //         console.log('开始播放')
    //     })

    //     // 加快 摇动速度
    //     this.setData({
    //         shakeLotSpeed: true,
    //         potPath: true,
    //         isLoading: true
    //     })


    //     $vm.api.getX504({
    //             notShowLoading: true,
    //         })
    //         .then(res => {
    //             if (_self.data.hasReturn) {
    //                 return
    //             }
    //             console.log(res)
    //             this.setData({
    //                 isLoading: false
    //             })
    //             if (!res) {
    //                 wx.navigateTo({
    //                     url: '/pages/lot/lotlist/lotlist'
    //                 })
    //                 return
    //             }
    //             res.isMyQian = 1
    //             res.alreadyOpen = 1
    //             let lotDetail = parseLot(res)

    //             _GData.lotDetail = lotDetail

    //             if (res.status === 0) {
    //                 setTimeout(() => {
    //                     if (_self.data.hasReturn) {
    //                         return
    //                     }
    //                     // 摇出一个签
    //                     this.setData({

    //                         shakeLotSpeed: false
    //                     })

    //                     if (_SData.isFromShare) {
    //                         wx.navigateTo({
    //                             url: '/pages/lot/lotdetail/lotdetail?sound=1',
    //                         })
    //                     } else {
    //                         wx.redirectTo({
    //                             url: '/pages/lot/lotdetail/lotdetail?sound=1',
    //                         })
    //                     }
    //                 }, 1500)
    //             } else if (res.status == 1) { //没有签了
    //                 setTimeout(() => {
    //                     if (_self.data.hasReturn) {
    //                         return
    //                     }
    //                     // 摇出一个签
    //                     this.setData({

    //                         shakeLotSpeed: false
    //                     })

    //                     if (_SData.isFromShare) {
    //                         wx.navigateTo({
    //                             url: '/pages/lot/emptylot/emptylot',
    //                         })
    //                     } else {
    //                         wx.redirectTo({
    //                             url: '/pages/lot/emptylot/emptylot',
    //                         })
    //                     }
    //                 }, 1000)
    //             } else {
    //                 setTimeout(() => {
    //                     wx.showModal({
    //                         title: '网络开小差了',
    //                         content: '请您检查网络后再试',
    //                         showCancel: false,
    //                         confirmText: '再摇一次',
    //                         success: function (res) {},
    //                         fail: function (res) {},
    //                         complete: function (res) {},
    //                     })

    //                     this.setData({
    //                         potPath: false,
    //                         isLoading: false,
    //                         shakeLotSpeed: false
    //                     })
    //                 }, 1000)
    //             }
    //         })
    //         .catch(err => {
    //             console.log(err)
    //             $vm.getLogin().then(res => {
    //                 console.log(res)
    //                 wx.setStorage({
    //                     key: 'token',
    //                     data: res.token
    //                 })
    //             }).catch(err => {
    //                 wx.showToast({
    //                     title: err,
    //                     icon: 'none'
    //                 })
    //             })
    //             setTimeout(() => {
    //                 wx.showModal({
    //                     title: '网络开小差了',
    //                     content: '请您检查网络后再试',
    //                     showCancel: false,

    //                     confirmText: '再摇一次',
    //                     success: function (res) {},
    //                     fail: function (res) {},
    //                     complete: function (res) {},
    //                 })


    //                 //  
    //                 this.setData({
    //                     potPath: false,
    //                     isLoading: false,
    //                     shakeLotSpeed: false
    //                 })
    //             }, 1000)

    //         })

    // },
    shakeFun: function () { // 摇一摇方法封装
        const _self = this
        const numX = 0.2 //x轴
        const numY = 0.2 // y轴
        const numZ = 0.2 // z轴
        let stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
        let positivenum = 0 //正数 摇一摇总数

        wx.onAccelerometerChange(function (res) { //小程序api 加速度计
            if (_self.data.hasReturn || _self.data.isLoading) return

            if (numX < res.x && numY < res.y) { //个人看法，一次正数算摇一次，还有更复杂的
                positivenum++
                setTimeout(() => {
                    positivenum = 0
                }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (numZ < res.z && numY < res.y) { //可以上下摇，上面的是左右摇
                positivenum++
                setTimeout(() => {
                    positivenum = 0
                }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (positivenum == 2 && stsw) { //是否摇了指定的次数，执行成功后的操作
                stsw = false

                _self.drawLots()
                wx.stopAccelerometer({})
                setTimeout(() => {
                    positivenum = 0 // 摇一摇总数，重新0开始，计算
                    stsw = true
                }, 2000)
            }
        })
    },
    // 显示我的签列表
    showLotList(e) {
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
    onclickHome(e) {
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
    bindGetUserInfo(e) {
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
            })
        }
    }

})

/**
 * 获取一签盒列表数据
 * @param {number} [pageNum=1]
 * @param {number} [pageSize=10]
 */
const getX510 = (self, pageNum = 1, pageSize = 10) => {
    let clicks = wx.getStorageSync('click_list') || []

    $vm.api.getX510({
        notShowLoading: true,
        pageNum,
        pageSize
    }).then(res => {
        console.log('一签盒列表：', res)
        if (res && res.constructor === Array) {
            let red_dot = false
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
            return false
        }
        // 本地缓存下数据
        wx.setStorageSync('sign_lists', '')
    })
}
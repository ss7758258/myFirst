// pages/lot/shakelot/shake.js

const $vm = getApp()
const _GData = $vm.globalData
const {
    parseLot
} = $vm.utils
const mta = require('../../../utils/mta_analysis.js')
const imgs = require('./imgs.js')
const reg = /^\d{6}$/ // 验证Id是否位6位纯数字

Page({
    // 页面的初始数据
    data: {
        isLoading: false,
        hasReturn: false,
        hasAuthorize: true,
        isFromShare: false,
        isOther: false,
        shakeLotSpeed: false,
        potPath: false,
        userInfo: _GData.userInfo,
        imgs: imgs,
        dot: false,
        timer: null, //摇一摇定时器
        isShaking: false, //是否正在摇动
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
        let {
            id,
            hotapp,
            source
        } = options
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

        // if (pageFrom == 'share') {
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
        //     console.log('ico_in_from_shake_activity')
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
        // } else if(pageFrom === 'spread'){ // 活动推广统计
        //     this.setData({
        //         isFromShare: true,
        //         "navConf.root": '/pages/home/home'
        //     })
        // 	console.log('输出活动来源',options.id)
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
            mta.Event.stat(isPureNumber ? `${source}_${id}` : source, {})
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
                    // getX510(this)
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
        console.log("timer:", this.data.timer)
        this.setData({
            hasReturn: false,
            timer: null
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
        const _SData = this.data

        if (_SData.isShaking) return

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
        console.log("autoplay",innerAudioContext.autoplay)

        // 加快 摇动速度
        this.setData({
            shakeLotSpeed: true,
            potPath: true,
            isLoading: true,
            isShaking: true
        })

        $vm.api.getX504({
                notShowLoading: true,
            })
            .then(res => {
                if (_SData.hasReturn) return

                if (!res) {
                    wx.navigateTo({
                        url: '/pages/lot/lotlist/lotlist'
                    })
                    return
                }

                let lotDetail = parseLot(res)

                this.setData({
                    isLoading: false
                })

                res.isMyQian = 1
                res.alreadyOpen = 1

                _GData.lotDetail = lotDetail

                if (res.status === 0) {
                    _SData.timer = setTimeout(() => {
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
                    _SData.timer = setTimeout(() => {
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
                    _SData.timer = setTimeout(() => {
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
                }
            })
            .catch(err => {
                $vm.getLogin().then(res => {
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

    // 摇一摇方法封装
    shakeFun() {
        const numX = 0.2 //x轴
        const numY = 0.2 // y轴
        const numZ = 0.2 // z轴
        const _SData = this.data
        let stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
        let positivenum = 0 //正数 摇一摇次数

        if (_SData.timer) {
            clearTimeout(_SData.timer)
        }
        this.setData({
            isShaking: true
        })

        wx.onAccelerometerChange((res) => { //小程序api 加速度计

            if (_SData.hasReturn || _SData.isLoading) return

            // 左右摇动或者上下摇动
            if ((numX < res.x && numY < res.y) || (numZ < res.z && numY < res.y)) { 
                positivenum++
                setTimeout(() => {
                    positivenum = 0
                }, 2000) //计时两秒内没有摇到指定次数，重新计算
            }
            if (positivenum == 2 && stsw) { //是否摇了指定的次数，执行成功后的操作
                stsw = false
                this.drawLots()
                wx.stopAccelerometer({})
                setTimeout(() => {
                    positivenum = 0 // 摇一摇总数，重新0开始，计算
                    stsw = true
                    this.setData({
                        isShaking: false
                    })
                    // 摇完了 跳转
                    debugger
                }, 2200)
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
            return
        }
        // 本地缓存下数据
        wx.setStorageSync('sign_lists', '')
    })
}
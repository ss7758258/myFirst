
const mta = require('../../../utils/mta_analysis.js')
let $vm = getApp()
let _GData = $vm.globalData;
const { parseLot } = $vm.utils
let getUserInfo = $vm.utils.wxPromisify(wx.getUserInfo)
const bus = require('../../../event')
const Storage = require('../../../utils/storage')
const methods = require('./util')

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;

// 配置参数
const conf = {
    data: {
        // 导航数据
        navConf: {
            title: '摇一摇',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true
        },
        bannerConf : {
            openId : '',
            appId : 'wxedc8a06ed85ce4df',
            pageNum : 1,
            pageSize : 20
        },
        // 摇动状态
        shakeLotSpeed : false,
        // 来源数据
        fromPage:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let pageFrom = options.from
        let self = this
        let _SData = this.data
        $vm = getApp()
        _GData = $vm.globalData
        // 调用数据分析进行统计
        methods.analytics(self,options,pageFrom,mta)
        console.log('输出参数：', options)

        // 监听事件
        bus.on('login-success', () => {
            console.log('登录标识')
            Storage.shakeLogin = true

            self.setData({
                userInfo: Storage.userInfo
            })
            // 上报选择星座
            methods.setUserInfo(Storage.userC,_GData.selectConstellation.id)

        }, 'login-com')
        
    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function () {
        this.shakeFun()
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function () {
        wx.stopAccelerometer({})
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function () {
        wx.stopAccelerometer({})
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
                console.log('转发成功：',res)
            },
            fail: function (res) {
                // 转发失败
            }
        }
    },
    drawLots: function () {
        if(!Storage.shakeLogin) return
        mta.Event.stat("ico_shake_shake", {})
        const _self = this
        const _SData = this.data
        // 已经zai
        if (_self.data.shakeLotSpeed) {
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

        // 拉取摇签数据
        getX504(_self,_SData)

    },
    shakeFun: function () { // 摇一摇方法封装
        const _self = this
        var numX = 0.12 //x轴
        var numY = 0.12 // y轴
        var numZ = 0.12 // z轴
        var stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
        var positivenum = 0 //正数 摇一摇总数

        wx.onAccelerometerChange(function (res) { //小程序api 加速度计
            
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
        })
    }
}

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
    // 等待请求5s时间
    setTimeout(function(){

    },5000)
    $vm.api.getX504({ notShowLoading: true, })
    .then(res => {
        console.log('摇出的签数据：',res)
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
                return false
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
                    wx.showModal({
                        title: '网络开小差了',
                        content: '小主，请您检查网络后再试',
                        showCancel: false,
                        confirmText: '再试一次',
                        success: function (res) { },
                        fail: function (res) { },
                        complete: function (res) { },
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
            getX504(_self,_SData)
        }
    })
    .catch(err => {
        sendLen++
        if(sendLen > 1){
            sendLen = 0
            setTimeout(() => {
                wx.showModal({
                    title: '网络开小差了',
                    content: '小主，请您检查网络后再试',
                    showCancel: false,
                    confirmText: '再试一次',
                    success: function (res) { },
                    fail: function (res) { },
                    complete: function (res) { },
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
        getX504(_self,_SData)
    })
}

Page(conf)
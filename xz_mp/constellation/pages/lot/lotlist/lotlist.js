// pages/lot/lotlist/lotlist.js
const $vm = getApp()
const _GData = $vm.globalData
var mta = require('../../../utils/mta_analysis.js')
let imgs = require('../lotdetail/imgs')
var pageNum = 1
Page({

    /**
     * 页面的初始数据
     */
    data: {
        isMore: false,
        noList: true,
        navConf: {
            title: '一签盒',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true
                // root : '/pages/home/home'
        },
        imgs
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
        const _self = this
            // 获取签列表
        getLists(_self);
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {
        mta.Event.stat("ico_list_share", {})
        var shareImg = '/assets/images/share_tong.jpg'
        var shareMsg = '要想日子过的好，每日一签少不了。'
        var sharepath = '/pages/lot/shakelot/shake?from=share&where=list'
        return {
            title: shareMsg,
            imageUrl: shareImg,
            path: sharepath,
            success: function(res) {
                // 转发成功
            },
            fail: function(res) {
                // 转发失败
            }
        }
    },
    moreLot: function(e) {
        const _self = this
        if (!_self.data.isMore) {
            return
        }

        pageNum++
        let formid = e.detail.formId

        mta.Event.stat("ico_list_more", {})

        var list = _self.data.lotList.list
        $vm.api.getX510({ pageNum: pageNum, pageSize: 10 })
            .then(res => {
                console.log(res)
                if (res.length < 10) {
                    _self.setData({
                        isMore: false
                    })
                } else {
                    _self.setData({
                        isMore: true
                    })
                }
                for (var i = 0; i < res.length; i++) {
                    let dd = res[i]
                    list.push({
                        index: _self.data.lotList.list.length + i,
                        time: dd.qianDate,
                        status: dd.status,
                        id: dd.id
                    })
                }
                _self.setData({
                    'lotList.count': _self.data.lotList.list.length,
                    'lotList.list': list
                })
            })
    },
    onItemClick: function(e) {
        let clicks = wx.getStorageSync('click_list') || [];
        let timers = wx.getStorageSync('timer_list') || [];
        const _self = this
        let lot = e.detail.target.dataset.item
        let t = 86400000000000,dt = new Date(),i = -1;//86400000
        
        for(let ind = timers.length - 1 ; ind >= 0 ; ind--){
            console.log(dt.getTime() - timers[ind] > t)
            console.log('日期数据：',dt.getTime() , timers[ind] )
            if(dt.getTime() - timers[ind] > t){
                i = ind;
                break
            }
        }

        if(i !== -1){
            // 删除已经过期的数据id
            timers = timers.slice(i + 1,timers.length)
            console.log('下标：',i)
            clicks = clicks.slice(i + 1,clicks.length)
            wx.setStorageSync('timer_list',timers)
            wx.setStorageSync('click_list', clicks)
        }

        console.log(clicks)
        // 不存在当前签的情况下
        if(clicks.indexOf(lot.id) === -1){
            let date = new Date();
            // 将签的ID放入列表
            clicks.push(lot.id)
            timers.push(date.getTime())
            wx.setStorageSync('click_list', clicks)
            wx.setStorageSync('timer_list',timers)
            let temp_obj = {}
            // 将点击的红点消除
            temp_obj['lotList.list[' + lot.index + '].isClick'] = false;
            _self.setData(temp_obj)
        }
        let formid = e.detail.formId

        mta.Event.stat("ico_list_detail", {})
        $vm.api.getX610({ notShowLoading: true, formid: formid })
        wx.navigateTo({
            url: '/pages/lot/lotdetail/lotdetail?formid=' + formid + '&from=list&lotId=' + lot.id,
            success: function(res) {},
            fail: function(res) {},
            complete: function(res) {},
        })
    }
})

/**
 * 加载签列表
 * @param {*} self
 */
const getLists = (self) => {
    let result = wx.getStorageSync('sign_lists')
    let clicks = wx.getStorageSync('click_list') || [];
    let list = []
    if (result && result !== '') {
        console.log('一签盒列表：', result)
        if (result.length < 10 || !result) {
            self.setData({
                isMore: false
            })
        } else {
            self.setData({
                isMore: true
            })
        }
        if (result.length > 0) {
            self.setData({
                noList: false
            })
        }
        for (let i = 0; i < result.length; i++) {
            let temp = result[i]
            list.push({
                index: i,
                time: temp.qianDate,
                status: temp.status,
                id: temp.id,
                isClick: temp.status === 0 && clicks.indexOf(temp.id) === -1 ? true : false
            })
        }
        self.setData({
            'lotList.list': list,
            'lotList.count': result.length,
        })
    } else {
        $vm.api.getX510({ pageNum: pageNum, pageSize: 10 }).then(res => {
            console.log('一签盒列表：', res)
            if (res.length < 10 || !res) {
                self.setData({
                    isMore: false
                })
            } else {
                self.setData({
                    isMore: true
                })
            }
            if (res.length > 0) {
                self.setData({
                    noList: false
                })
            }
            for (var i = 0; i < res.length; i++) {
                let temp = res[i]
                list.push({
                    index: i,
                    time: temp.qianDate,
                    status: temp.status,
                    id: temp.id,
                    isClick: temp.status === 0 && clicks.indexOf(temp.id) !== -1 ? true : false
                })
            }
            self.setData({
                'lotList.list': list,
                'lotList.count': res.length,
            })
        })
    }
}
const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')
let Storage = require('../../utils/storage')
const bus = require('../../components/banner/event')
const conf = require('../../conf')[require('../../config')] || {}
const q = require('../../utils/source')
console.log('配置信息：',conf)

let openIdTimer = null
let timer = null
// 弹窗消失时间
let startime = 2000
// 弹窗定时器
let starTimer = null

// Page对象的配置参数
const Conf = {
    data : {
        navConf : {
			title : '小哥推荐',
			state : 'root',
			isRoot : false,
			isIcon : true,
			iconPath : '',
            root : '',
            isTitle : true
            // root : '/pages/home/home'
        },
        bannerConf : {
            appId : 'wxedc8a06ed85ce4df',
            pageNum : 1,
            pageSize : 20
        },
        // 星星个数
        starNum : 0,
        // 是否展示星星弹窗
        starShow : false,
        // 默认高度
        height : 'calc(100% - 2.9962546816479403vh - 6.6vh)'
    },
    /**
     * 加载内容信息
     * @param {*} options
     */
    onLoad(options) {
        // 数据来源分析
        q.sourceHandle(options)
        console.log('-------------------------',options.shareform)
        if(options.shareform){
            this.setData({
                'navConf.root' : '/pages/home/home'
            })
        }
        // openId处理
        openIdHandle(this)
        // 心跳
        tick(this)
        // 事件处理
        eventHandle(this)
    },
    // 卸载
    onUnload(){
        clearTimeout(timer)
    },
    // 分享
    onShareAppMessage: function() {
        mta.Event.stat('banner_share',{})
        return {
            title : '真好玩，根本停不下来!',
            path : '/pages/home/home?to=banner&from=share&source=share&id=999999&tid=123456&shareform=banner&m=0',
            imageUrl : '/assets/images/share-banner.png'
        }
    }
}

/**
 * 获取广告位的占位信息
 */
function getAdInfo(self){
    let query = wx.createSelectorQuery()
    query.select('.ad_banner').boundingClientRect()
    query.exec(function (res) {
        console.log('输出banner——----------ad信息：',res[0])
        if(res[0]){
            self.setData({
                height : `calc(100% - 2.9962546816479403vh - 6.6vh - ${res[0].height}px)`
            })
        }
    })
}

/**
 * 心跳
 * @param {*} self
 */
function tick(self){
    timer = setTimeout(() => {
        getAdInfo(self)
        tick(self)
    },2000)
}

/**
 * 事件处理
 * @param {*} self
 */
function eventHandle(self){
    if(Storage.resourceRemoveId){
        bus.remove(Storage.resourceRemoveId)
    }
    if(Storage.resOpenRemoveId){
        bus.remove(Storage.resOpenRemoveId)
    }
    if(Storage.resFailRemoveId){
        bus.remove(Storage.resFailRemoveId)
    }

    Storage.resourceRemoveId = bus.on('resource_click',(res) => {
        console.log('点击了资源：',res)
        wx.reportAnalytics('banner_click', {
            resource_id: res.res.id,
            appid: res.res.appId,
            resource_name: res.res.appName,
            openid: res.opts.openId,
            desc: '点击跳转外链',
        });
        if(res.res.received){
            return
        }
        API.setStar({
            id : res.res.id,
            balance : res.res.starAmount,
            notShowLoading : true
        }).then(data => {
            let temp = {}
            console.log('加星星：',data)
            if(data && data.status === 'SUCCESS'){
                console.log('加星星成功',res.res)
                // 已经领取
                res.res.received = 1
                if(res.index != -1){
                    temp['list[' + res.index + ']'] = res.res
                    res.self.setData(temp)
                }
                self.setData({
                    starNum : res.res.starAmount,
                    starShow : true
                })
                wx.reportAnalytics('star_receive', {
                    resource_id: res.res.id,
                    appid: res.res.appId,
                    resource_name: res.res.appName,
                    openid: res.opts.openId,
                    star_num : res.res.starAmount,
                    desc: '领取小星星',
                });
                clearTimeout(starTimer)
                starTimer = setTimeout(() => {
                    self.setData({
                        starShow : false
                    })
                },startime)
            }else{
                console.log('加星星失败')
            }
        }).catch(err => {
            console.log('领取失败')
        })
    }, 'banner-app')

    Storage.resOpenRemoveId = bus.on('resource_open_success',(res) => {
        console.log('打开成功：',res)
        wx.reportAnalytics('resource_open_success', {
            resource_id: res.res.id,
            appid: res.res.appId,
            resource_name: res.res.appName,
            openid: res.opts.openId,
            desc: '跳转成功',
        });
    }, 'banner-app')

    Storage.resFailRemoveId = bus.on('resource_open_fail',(res) => {
        console.log('打开失败：',res)
        wx.reportAnalytics('banner_open_fail', {
            resource_id: res.res.id,
            appid: res.res.appId,
            resource_name: res.res.appName,
            openid: res.opts.openId,
            desc: '跳转失败',
        });
    }, 'banner-app')
}

/**
 * 用于获取openId的信息
 * @param {*} self
 */
function openIdHandle(self){
    let openId = wx.getStorageSync('openId')

    if(!openId){
        openIdTimer = setInterval(() => {
            let openId = wx.getStorageSync('openId')
            if(openId){
                clearInterval(openIdTimer)
                self.setData({
                    'bannerConf.miniPro' : Storage.miniPro,
                    'bannerConf.openId' : openId
                })
            }
        },1000)
    }else{
        self.setData({
            'bannerConf.miniPro' : Storage.miniPro,
            'bannerConf.openId' : openId
        })
    }
}

// 创建Banner页
Page(Conf)
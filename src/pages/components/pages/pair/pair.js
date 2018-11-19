
const bus = require('../../../../event')
const mta = require('../../../../utils/mta_analysis')
const Storage = require('../../../../utils/storage')
const API = require('../../../../utils/api')
const star = require('../../../../utils/star')
const q = require('../../../../utils/source')
const env = 'local'
let $vm = getApp()

const conf = {
    data : {
        cdn : env === 'local' ? '../../source' : 'https://xingzuo-1256217146.file.myqcloud.com',
		navConf : {
			title : '星座配对',
			state : '',
			isRoot : false,
			isIcon : true,
			iconPath : '',
			root : '',
			isTitle : true
        },
		opts: {
			appId: 'wx855c5d7718f218c9',
			path: '/pages/index/index?wxID=45ae12&scene=gzhgl1109804',
			openType: 'navigate',
			extra: '',
			txt: '打卡',
			version: 'release'
		},
        // 星座信息
        star : star,
        // 选择星座
        showPair : false,
        starXZ : {
            id : 1
        },
        // 最佳匹配星座
        defaultId : 12,
        // 用户性别
        sex : 'woman',
        // 选中的最佳匹配
        pair : {
            constellationId : 12
        },
        // 选中的星座
        select : {
            constellationId : 12
        },
        // 小打卡圈子开关状态
        clockStatus : Storage.clockStatus || 0,
        // 是否已经打开大门
        isOpenGate : wx.getStorageSync('opengate') || 0,
        // 分享的朋友圈是否已经有人进入配对模式
        isOpenPair : wx.getStorageSync('openSharePair') || false,
        userInfo : Storage.userInfo || { nickName : ''},
        version : true,
        notice: { isShow: true },  //公告组件
    },
    onLoad(options){
        // 数据来源分析
        q.sourceHandle(options)
        mta.Page.init()
        this.methods.init.call(this)
    },
    onShow(){
        this.setData({
            'isOpenGate' : wx.getStorageSync('opengate') || 0,
            isOpenPair : wx.getStorageSync('openSharePair') || false,
            version : Storage.miniPro
        })

        this.getNotice()
    },
    // 分享
    onShareAppMessage: function() {
        mta.Event.stat('pair_share_click',{})
        return {
            title : '想知道和你最配的人是谁吗',
            path : '/pages/home/home?to=pair&from=share&source=share&id=999998&tid=123455&shareform=pair&m=0',
            imageUrl : '/assets/images/share-pair.png'
        }
    },
    methods: {
        init () {
            
            let self = this
            console.log('此时用户性别为：',Storage.AccountSex)
            self.setData({
                starXZ : Storage.starXz || {
                    id : 1
                },
                sex : Storage.AccountSex || 'woman',
                userInfo:Storage.userInfo,
                clockStatus : Storage.clockStatus || 0,
                isOpenGate : wx.getStorageSync('opengate') || 0
            })

            // wx.showLoading({
            //     title: '加载中...'
            // })
            let params = {
                notShowLoading:true
            }
            // 确认男女性别
            this.data.sex === 'man' ? params.maleConstellationId = this.data.starXZ.id : params.femaleConstellationId = this.data.starXZ.id
            
            Storage.pairList = [{
                id : self.data.starXZ.id,
                sex : self.data.sex
            }]
            
            API.getPair(params).then(res => {
                console.log('最佳匹配星座：',res)
                if(res){
                    self.setData({
                        defaultId : res,
                        'pair.constellationId' : res,
                        'select.constellationId' : res
                    })
                    Storage.pairList.push({
                        id : self.data.select.constellationId,
                        sex : self.data.sex === 'man' ? 'woman' : 'man'
                    })
                }
                setTimeout(() => {
                    wx.hideLoading()
                },500)
            }).catch(err => {
                Storage.pairList.push({
                    id : self.data.select.constellationId,
                    sex : self.data.sex === 'man' ? 'woman' : 'man'
                })
                wx.hideLoading()
                wx.showToast({
                    icon: 'none',
                    title : '获取最配星座失败',
                    duration : 2000,
                    mask : true
                })
            })
        }
    },
    // 低版本跳转小打卡
    _goXDK(){
        wx.navigateToMiniProgram({
            appId: this.data.opts.appId,
            path: this.data.opts.path
        })
    },
    // 前往分享配对页面
    _goPairCus(){
        mta.Event.stat('pair_to_paircus',{})
        wx.navigateTo({
            url : '/pages/components/pages/pairCus/pairCus'
        })
    },
    // 确定星座
    _confirm(){
        mta.Event.stat('pair_switch_confirm',{})
        this.setData({
            'pair.constellationId' : this.data.select.constellationId,
            showPair : false // 关闭选择星座
        })
        Storage.pairList[1] = {
            id : this.data.select.constellationId,
            sex : this.data.sex === 'man' ? 'woman' : 'man'
        }
    },
    // 打开星座选择
    _openSwitch(){
        mta.Event.stat('pair_switch_open',{})
        this.setData({
            showPair : true
        })
    },
    // 关闭选择星座
    closeSwitch(){
        mta.Event.stat('pair_switch_close',{})
        this.setData({
            'select.constellationId' : this.data.pair.constellationId,
            showPair : false
        })
    },
    // 切换星座
    _switchStar (e){
        mta.Event.stat('pair_switch_star',{})
        const self = this
        const {item :selectConstellation} = e.currentTarget.dataset
        console.log(selectConstellation)
        self.setData({
            'select.constellationId' : selectConstellation.id 
        })
    },
    // 前往寻找
    _seekPair(){
        mta.Event.stat('pair_go_pair',{})
        wx.navigateTo({
            url : '/pages/components/pages/result/result'
        })
    },
    /**
     * 上报formId
     * @param {*} e
     */
    _reportFormId(e){
        console.log(e.detail.formId)
        let formid = e.detail.formId
        API.getX610({ notShowLoading: true, formid: formid })
    },

    // 获取公告数据
    getNotice() {
        
    },
}

Page(conf)


const bus = require('../../../../event')
const mta = require('../../../../utils/mta_analysis')
const Storage = require('../../../../utils/storage')
const API = require('../../../../utils/api')
const star = require('../../../../utils/star')
const env = 'local'

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
			path: '/pages/index/index?wxID=ad6377&scene=gzhgl922689',
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
        userInfo : Storage.userInfo || { nickName : ''}
    },
    onLoad(options){
        console.log(this.data.starXZ)
        mta.Page.init()
        this.methods.init.call(this)
    },
    
    onShareAppMessage(){

    },
    methods: {
        init () {
            
            let self = this
            console.log(Storage.AccountSex)
            self.setData({
                starXZ : Storage.starXz || {
                    id : 1
                },
                sex : Storage.AccountSex || 'woman',
                clockStatus : Storage.clockStatus || 0,
                isOpenGate : wx.getStorageSync('opengate') || 0
            })

            wx.showLoading({
                title: '加载中...'
            })
            let params = {
                notShowLoading:true
            }
            // 确认男女性别
            this.data.sex === 'man' ? params.maleConstellationId = this.data.starXZ.id : params.femaleConstellationId = this.data.starXZ.id
            
            API.getPair(params).then(res => {
                console.log('最佳匹配星座：',res)
                if(res){
                    self.setData({
                        defaultId : res,
                        'pair.constellationId' : res,
                        'select.constellationId' : res
                    })
                    
                    Storage.pairList = [{
                        id : self.data.starXZ.constellationId,
                        sex : self.data.sex
                    },
                    {
                        id : self.data.select.constellationId,
                        sex : self.data.sex === 'man' ? 'woman' : 'man'
                    }]
                }
                setTimeout(() => {
                    wx.hideLoading()
                },500)
            }).catch(err => {
                wx.hideLoading()
                wx.showToast({
                    icon: 'none',
                    title : '获取最配星座失败',
                    duration : 1000,
                    mask : true
                })
            })
        }
    },
    // 确定星座
    _confirm(){
        this.setData({
            'pair.constellationId' : this.data.select.constellationId,
            showPair : false // 关闭选择星座
        })
    },
    // 打开星座选择
    _openSwitch(){
        this.setData({
            showPair : true
        })
    },
    // 关闭选择星座
    closeSwitch(){
        this.setData({
            'select.constellationId' : this.data.pair.constellationId,
            showPair : false
        })
    },
    // 切换星座
    _switchStar (e){
        const self = this
        const {item :selectConstellation} = e.currentTarget.dataset
        console.log(selectConstellation)
        self.setData({
            'select.constellationId' : selectConstellation.id 
        })
    },
    // 前往寻找
    _seekPair(){
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
    }
}

Page(conf)

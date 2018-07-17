const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')
let Storage = require('../../utils/storage')
const bus = require('../../components/banner/event')
const conf = require('../../conf')[require('../../config')] || {}
console.log('配置信息：',conf)
let timer = null
// Page对象的配置参数
const Conf = {
    data : {
        navConf : {
			title : '更多好玩',
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
        }
    },
    /**
     * 加载内容信息
     * @param {*} options
     */
    onLoad(options) {
        console.log(Storage)
        this.setData({
            'bannerConf.openId' : wx.getStorageSync('openId') || ''
        })
        
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
            API.setStar({
                id : res.res.id,
                balance : res.res.starAmount,
                notShowLoading : true
            }).then(data => {
                console.log('加星星：',data)
            })
        }, 'banner-app')
        Storage.resOpenRemoveId = bus.on('resource_open_success',(res) => {

            wx.showModal({
                title: '成功',
                content: '恭喜你成功打开' + res.res.appId,
                showCancel: false,
                confirmText: '确认',
                confirmColor: '#3CC51F',
                success: res => {
                    
                }
            });
            console.log('打开成功：',res)
        }, 'banner-app')
        Storage.resFailRemoveId = bus.on('resource_open_fail',(res) => {
            console.log('打开失败：',res)
        }, 'banner-app')
    }

}


// 创建Banner页
Page(Conf)
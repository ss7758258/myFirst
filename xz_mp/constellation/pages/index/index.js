
const mta = require('../../utils/mta_analysis.js')
const bus = require('../../components/banner/event')
Page({

    data: {
        bannerConf : {
            appId : 'wxedc8a06ed85ce4df',
			pageNum : 1,
			pageSize : 20
        },
        navConf : {
			title : '小哥星座',
			state : 'root',
			isRoot : true,
			isIcon : true,
			iconPath : '',
			root : '',
			isTitle : true,
			centerPath : '/pages/center/center'
		}
    },

    onLoad: function(options) {
        console.log(mta)
        this.setData({
            'bannerConf.openId' : wx.getStorageSync('openId') || ''
        })
        bus.on('resource_click',(res) => {
            console.log('点击了资源：',res)
        }, 'banner-app')
        bus.on('resource_open_success',(res) => {

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
        bus.on('resource_open_fail',(res) => {
            console.log('打开失败：',res)
        }, 'banner-app')
    },
    onShareAppMessage(){
        return {

        }
    }

});

const mta = require('../../utils/mta_analysis.js')
Page({

    data: {
        bannerConf : {
            appId : 'wxedc8a06ed85ce4df',
			pageNum : 1,
			pageSize : 20
		}
    },

    onLoad: function(options) {
        console.log(mta)
        this.setData({
            'bannerConf.openId' : wx.getStorageSync('openId') || ''
        })
    },
    onShareAppMessage(){
        return {

        }
    }

});
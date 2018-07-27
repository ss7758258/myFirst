let $vm = getApp()
const mta = require('../../../../utils/mta_analysis.js')
Page({
    data: {
        navConf: {
            title: '关注公众号',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true,
            centerPath: '/pages/center/center'
        },
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
    },

    onShow: function () {
        
    },
   

})
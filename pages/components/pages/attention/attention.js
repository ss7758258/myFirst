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
    // 上报formid
    reported(e){
        wx.showLoading({
            title: '保存图片ing',
        })
        mta.Event.stat("attention_save", {})
        wx.getImageInfo({
            src: '../../img_subPackages/qrcode.png',
            success: res=>{
                console.log(res)
                let path = res.path
                wx.saveImageToPhotosAlbum({
                    filePath: path,
                    success: function (res) {
                        wx.showToast({
                            title: '保存图片成功',
                            icon: 'success',
                        })
                    },
                    fail: function (res) {
                        wx.showToast({
                            title: '抱歉，由于网络问题失败了呢',
                            icon:'none'
                        })
                        return
                    }
                })
            },
            fail: function(res) {
                wx.showToast({
                    title: '抱歉，由于网络问题失败了呢',
                    icon: 'none'
                })
                return
            }
        })

        console.log(e)
        let formid = e.detail.formId
        if (formid == "the formId is a mock one"){
            wx.showToast({
                title: '拒绝模拟器操作',
                icon: 'none',
            })
            return
        }
        
        $vm.api.getX610({ formid: formid}).then(res=>{
            console.log(res)
        })
    },
    


    onShow: function () {
        
    },
    /**
     * 获取本周运势 and 本月运势
     * 
     */
   

})
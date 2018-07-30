let $vm = getApp()
const mta = require('../../../../utils/mta_analysis.js')
const store = require('../../../../utils/storage.js')
Page({
    data: {
        navConf: {
            title: '配对结果',
            state: 'root',
            isRoot: false,
            isIcon: true,
            root: '',
            isTitle: true,
        },
        paidList:[],
        manid:1,
        womanid:12,
        flag:false, //开关
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
        if(store.pairList){
            this.setData({
                manid: store.pairList[0].id,
                womanid: store.pairList[1].id
            })
            this.getpair()
        }else{

            this.setData({
                manid: options.maleConstellationId,
                womanid: options.femaleConstellationId
            })
            this.getpair()
        }

    },

    onShow: function () {
        
    },

    // 获取星座配对数据
    getpair(){
        $vm.api.pair({ maleConstellationId: this.data.manid, femaleConstellationId: this.data.womanid}).then(res=>{
            console.log(res)
            if(res){
                this.setData({
                    paidList:res
                })
            }else{
                wx.showToast({
                    title: '抱歉您的网络有点问题呢，请稍后再试',
                    icon: 'none'
                })
            }
        }).catch(err=>{
            console.log('返回错误星座配对数据：',err)
            wx.showToast({
                title: '抱歉您的网络有点问题呢，请稍后再试',
                icon: 'none'
            })
        })
    },

    // 上报formid
    formid(e) { 
        mta.Event.stat("result_find", {})
        // console.log(e)
        $vm.api.getX610({ formid: e.detail.formId })
        mta.Event.stat("find_btn", {})
        let clockStatus = wx.getStorageInfo('clockStatus') ? wx.getStorageInfo('clockStatus') : 0
        if(clockStatus == 0){
            wx.showToast({
                title: '即将开启，敬请期待',
                icon: 'none'
            })
            console.log(0)
        } else if (clockStatus == 1){
            let now=new Date().getTime()   //当前时间戳
            let c = new Date(new Date().toLocaleDateString()).getTime() //当天0点时间戳
            let time19=c+1000*60*60*19,timer21=c+1000*60*60*21
            if(now > time19 && now < time21){
                wx.navigateTo({
                    url: '####'   //跳转链接
                })
            }else{
                wx.showToast({
                    title: '每天19：00-21：00开放',
                    icon: 'none'
                })
            }
        }else{
            console.log(clockStatus)
        }
        


    },   

})
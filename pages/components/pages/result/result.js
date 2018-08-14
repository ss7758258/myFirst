let $vm = getApp()
const mta = require('../../../../utils/mta_analysis.js')
const store = require('../../../../utils/storage.js')
const star = require('../../../../utils/star')
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
		xiaodaka: {
			appId: 'wx855c5d7718f218c9',
			path: '/pages/index/index?wxID=45ae12&scene=gzhgl1109804',
			openType: 'navigate',
			extra: '',
			txt: '打卡',
			version: 'release'
		},
        paidList:false,
        man:false,
        woman:false,
        showNav : false,
        flag:false, //开关
        star:star, //星座信息
        isIPhoneX:false, //iphonex适配
        version : true
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
        getSystemInfo(this)
        if(store.pairList){
            console.log('aaaaaaaaaaaaaaaaa',store.pairList)
            this.setData({
                man: store.pairList[0],
                woman: store.pairList[1]
            })
            this.getpair()
        }else{
            console.log(11111111111111111,options)
            this.setData({
                man: options.maleConstellationId,
                woman: options.femaleConstellationId
            })
            this.getpair()
        }

    },

    onShow: function () {
        let open = wx.getStorageSync('opengate')
        let clock = wx.getStorageSync('clockStatus')
        this.setData({
            showNav : open == 1 && clock == 1 ? true : false,
            version : store.miniPro
        })
    },
    // 低版本跳转小打卡
    _goXDK(){
        wx.navigateToMiniProgram({
            appId: this.data.xiaodaka.appId,
            path: this.data.xiaodaka.path
        })
    },
    // 获取星座配对数据
    getpair(){
        $vm.api.pair({ maleConstellationId: this.data.man.id, femaleConstellationId: this.data.woman.id}).then(res=>{
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
    // 寻找按钮展示
    _seek(){
        this.setData({
            seek : true
        })
    },
    // 前往朋友圈配对
    _goPairCus(){
        wx.navigateTo({
            url : '/pages/components/pages/pairCus/pairCus'
        })
    },
    // 上报formid
    formid(e) { 
        mta.Event.stat("result_find", {})
        // console.log(e)
        $vm.api.getX610({ formid: e.detail.formId, notShowLoading:true })
        mta.Event.stat("find_btn", {})
        let clockStatus = wx.getStorageSync('clockStatus') || 0
        let open = wx.getStorageSync('opengate')
        if(clockStatus == 1 && open == 1){
            return
        }
        if(!clockStatus){
            wx.showToast({
                title: '即将开启，敬请期待',
                icon: 'none',
                duration: 1750,
                mask: true
            })
            console.log(0)
        } else if (clockStatus == 1){
            let now=new Date().getTime()   //当前时间戳
            let c = new Date(new Date().toLocaleDateString()).getTime() //当天0点时间戳
            let time19 = c + 1000 * 60 * 60 * 19
            let time21 = c + 1000 * 60 * 60 * 21
            if(now > time19 && now < time21){
                wx.navigateTo({
                    url: '../happyDoor/happyDoor'   //跳转链接
                })
            }else{
                wx.showToast({
                    title: '每天19：00-21：00开放',
                    icon: 'none',
                    duration: 1750,
                    mask: true
                })
            }
        }else{
            console.log(clockStatus)
        }
        


    },   

})

function getSystemInfo(self) {
    let res = wx.getSystemInfoSync();
    console.log('设备信息：', res);
    if (res) {
        // 长屏手机适配
        if (res.screenWidth <= 375 && res.screenHeight >= 750) {
            self.setData({
                isIPhoneX: true
            })
        }
    }
}
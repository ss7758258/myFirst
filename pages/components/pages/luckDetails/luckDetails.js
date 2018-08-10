let $vm = getApp()
const mta = require('../../../../utils/mta_analysis.js')
const Storage = require('../../../../utils/storage')
Page({
    data: {
        navConf: {
            title: '运势',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true,
            centerPath: '/pages/center/center'
        },
        swiper:{
            autoplay:false,
            interval:'5000',
            current:0,
            num:1
        },
        hei: 64,
        headerlist: ['今日运势', '本周运势','本月运势'], //导航栏数据
        current: 0, //导航栏下标
        contentlist: ['综合指数', '爱情指数', '财富指数', '工作指数'],
        list: false,       //页面渲染数据
        dta:{           //运势接口数据
            day: {},
            week: {},
            month: {},
        }
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        wx.showLoading({
            title: '加载ing',
            mask: true,
        })
        mta.Page.init()
        if (!Storage.lucky) {
            this._getDayResult()
            this._getData()
        }else{
            this.setData({
                'dta.day': Storage.lucky
            })
            this.selected()
            this._getData()
            wx.hideLoading()
        }
    },

    onShow: function () {
        
    },
    // 设置高度
    setH(e){
        this.setData({
            hei : e.detail || 64
        })
    },
    /**
     * 获取本周运势 and 本月运势
     * 
     */
    _getData(){
        let constellationId = $vm.globalData.selectConstellation.id //选择星座id
        // 本周运势
        $vm.api.luckyweek({ constellationId: constellationId }).then(res => {
            // console.log('运势详情数据：', res)
            if (res != '') {
                this.setData({
                    'dta.week': res
                })
                console.log('本周运势数据bbbb:', this.data.dta.week)
            }
        }).catch(err => {
            console.log('本周运势详情返回报错数据：', err)
            wx.showToast({
                title: '抱歉您的网络有点问题，请稍后再试',
                icon: 'none',
            })
        })

        // 本月运势
        $vm.api.luckymonth({ constellationId: constellationId }).then(res => {
            // console.log('本月运势详情数据：', res)
            if (res != '') {
                this.setData({
                    'dta.month': res
                })
            }
        }).catch(err => {
            console.log('本月运势详情返回报错数据：', err)
            wx.showToast({
                title: '抱歉您的网络有点问题，请稍后再试',
                icon: 'none',
            })
        })
        
    },
    /**
     * 获取今天的运势
     */
    _getDayResult (){
        // 今日运势
        let constellationId = $vm.globalData.selectConstellation.id
        $vm.api.luckyday({ constellationId: constellationId}).then(res => {
            // console.log('今日运势详情数据：', res)
            if(res!=''){
                this.setData({
                    'dta.day': res
                })
                this.selected()
                wx.hideLoading()
            }
        }).catch(err => {
            console.log('今日运势详情返回报错数据：', err)
            wx.showToast({
                title: '抱歉，您的网络有点问题，请稍后再试',
                icon: 'none',
            })
        })
    },
    // 选择运势
    selected(e, swiper){
        mta.Event.stat("luckDetails_selected", {})
        let current = 0, dta = this.data.dta.day;
        console.log('dtadtadtadtadta:',dta)
        if(e){
            let index = e.target.dataset.selected
            if (index == 0) {
                current = 0
                dta = this.data.dta.day
            } else if (index == 1) {
                current = 1
                dta = this.data.dta.week
                console.log(dta)
            } else {
                current = 2
                dta = this.data.dta.month
            }
        }
        
        this.setData({
            current: current,
            'swiper.current':current,
            list: dta
        })
        // console.log('list页面渲染eeeeee:',this.data.list) 
    },

    // 运势详情
    swiper(e){
        mta.Event.stat("luckDetails_swiper", {})
        let current = e.detail.current, dta = this.data.dta
        // console.log('eeeeeeeeeeeeeeeeeee',e)
        
        if (current == 0){
            dta=dta.day
        } else if (current == 1){
            dta=dta.week
        }else{
            dta=dta.month
        }
        
        this.setData({
            current: current,
            list:dta
        })
    }

})
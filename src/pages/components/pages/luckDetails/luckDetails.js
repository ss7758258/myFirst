let $vm = getApp()
const API = require('../../../../utils/api')
const mta = require('../../../../utils/mta_analysis.js')
const Storage = require('../../../../utils/storage')
const star = require('../../../../utils/star')
const util = require('../../../../utils/util')
const WxParse = require('../../../../wxParse/wxParse.js');
Page({
    data: {
        navConf: {
          title: '今日运势',
          state: 'root',
          isRoot: false,
          bg: '#fff',
          isIcon: true,
          iconPath: '',
          root: '',
          isTitle: true,
          showContent: true,
          color: 'black',
          fontColor: 'black',
          showPop: false,
          showTabbar: false,
          tabbar: {},
          pop: {},
          pbg: '#FAFAFC'
        },
        // 星座信息
        star,
        swiper:{
            autoplay:false,
            interval:'5000',
            current:0,
            num:1
        },
		// 是否显示选择星座
        showChoice : false,
        // 选择的星座
        current: 1, 
        showFollow : false, // 关注服务号开关
        noticeBtnStatus : false, // 好运提醒开关
        hei: 64,
        IPX : false,
        // 基础内容选项卡
        baseCurrent: 0,
        // tab选择
        tabCurrent: 0,
        // swiper选择
        swCurrent: 0,
        dta:{           //运势接口数据
            day: {},
            week: {},
            month: {},
        },
        moreText: '查看更多',
        day:{},
        week:{},
        // 存放日期
        dList:[]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        mta.Page.init()
        let selectId = wx.getStorageSync('luck_current_id');
        console.log('运势详情页：',options)
        console.log('运势Storage参数：',Storage)
        console.log(this.data.star,this.data.star[this.data.current])
        if(selectId){
            this.setData({
                current: selectId
            })
        }else{
            this.setData({
                current: Storage.starXz.id
            })
        }
          
        wx.showLoading({
            title: '加载ing',
            mask: true,
        })
        let dates = util.getWeek()
        let tmp = (dates[0].getMonth() + 1) + '.' + dates[0].getDate() + '~' + (dates[1].getMonth() + 1) + '.' + dates[1].getDate()
        this.setData({
            weekDate: tmp
        })
        this._getDayList()
        this._getDayResult()
        this._getData()
    },

    onShow: function () {
        
    },
    // 显示更多
    _moreShow(e){
        let flag = this.data.moreText == '查看更多' ? true : false
        let moreText = this.data.moreText == '查看更多' ? '收起' : '查看更多'
        this.setData({
            more: flag,
            moreText
        })
    },
    // 切换基础内容
    _switchBase(e){
        let { index } = e.currentTarget.dataset
        this.setData({
            more: false,
            moreText: '查看更多',
            baseCurrent: parseInt(index)
        })
    },
    // 切换tab
    _switch(e){
        let { index } = e.currentTarget.dataset
        index = parseInt(index)
        this.setData({
            tabCurrent: index,
            swCurrent: index,
            'navConf.title': index == 2 ? '本月运势' : (index == 1 ? '本周运势' : '今日运势')
        })
    },
    // 切换日期
    _switchDay(e){
        let { index } = e.currentTarget.dataset
        if(index == 3){
            return
        }
        
        wx.showModal({
            title: '提示',
            content: '小哥暂时无法提供更多运势，先看看今日运势吧',
            showCancel: false,
        })
    },
    // 切换Tab
    _changeTab(e){
        let { current } = e.detail
        console.log('tab切换：',current)
        this.setData({
            tabCurrent: current,
            'navConf.title': current == 2 ? '本月运势' : (current == 1 ? '本周运势' : '今日运势')
        })
    },
    // 打开选择星座页
    _selectStar(){
        this.setData({
            showChoice: true
        })
    },
    // 设置星座信息
    choiceStar(e){
        console.log(e)
        let { item } = e.currentTarget.dataset
        this.setData({
            showChoice: false,
            current: item.id
        })
        wx.showLoading({
            title: '加载ing',
            mask: true,
        })
        wx.setStorageSync('luck_current_id', item.id ? item.id : 1);
        this._getDayResult()
        this._getData()
        console.log(item)
    },
    // 设置高度
    setH(e){
        let height = e.detail || 64
        this.setData({
            hei : height,
            IPX : height === 64 ? false : true
        })
    },
    // 生成日期列表
    _getDayList(){
        let date = new Date()
        let list = []
        let year = date.getFullYear()
        let month = date.getMonth() + 1
        date.setDate(date.getDate() - 3)
        console.log(date)
        for(let i = 0; i < 7; i++){
            list.push(date.getDate())
            date.setDate(date.getDate() + 1)
        }
        this.setData({
            dayCurrent: 3,
            dList: list,
            year,
            month
        })
    },
    /**
     * 获取本周运势 and 本月运势
     * 
     */
    _getData(){
        // 选择星座id
        let constellationId = this.data.current 
        // 本周运势
        $vm.api.luckyweek({ constellationId }).then(res => {
            // console.log('运势详情数据：', res)
            if (res != '') {
                this.setData({
                    week: res
                })
                WxParse.wxParse('weekgeneralTxt', 'html', this.data.week.generalTxt || 'false', this, 5);
                WxParse.wxParse('weekloveTxt', 'html', this.data.week.loveTxt || 'false', this, 5);
                WxParse.wxParse('weekworkTxt', 'html', this.data.week.workTxt || 'false', this, 5);
                WxParse.wxParse('weekmoneyTxt', 'html', this.data.week.moneyTxt || 'false', this, 5);
                console.log('本周运势数据:', this.data.week)
            }
        }).catch(err => {
            console.log('本周运势详情返回报错数据：', err)
            wx.showToast({
                title: '抱歉您的网络有点问题，请稍后再试',
                icon: 'none',
            })
        })

        // 本月运势
        $vm.api.luckymonth({ constellationId }).then(res => {
            console.log('本月运势详情数据：', res)
            if (res != '') {

                this.setData({
                    monthData: res
                })
                WxParse.wxParse('generalTxt', 'html', this.data.monthData.generalTxt || 'false', this, 5);
                WxParse.wxParse('loveTxt', 'html', this.data.monthData.loveTxt || 'false', this, 5);
                WxParse.wxParse('workTxt', 'html', this.data.monthData.workTxt || 'false', this, 5);
                WxParse.wxParse('moneyTxt', 'html', this.data.monthData.moneyTxt || 'false', this, 5);
                
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
        let constellationId = this.data.current 
        $vm.api.luckyday({ constellationId }).then(res => {
            console.log('今日运势详情数据：', res)
            if(res!=''){
                res.tip1 = '学习，放松'
                res.tip2 = '打游戏'
                res.mahjong = '麻将牌运普通的日子，今日搓麻将可先以娱乐心态进入，再以赌一把的心态投入，险中求胜美不胜收。'
                res.landlord = '今天的思念之情特别浓烈，和恋人分开时表现出的依依不舍，易让对方更加爱你；朋友到你家做客的机率很大，难免要让你有些破费，不过因此能换来好心情；很懂得处理与同事间的关系，相处会很融洽。'
                let pokerTip = `斗地主赢牌必看：<br />赢牌颜色：${res.pokerColor || ''}<br/>赢牌食物：${res.pokerFood || ''}<br/>赢牌方向：${res.pokerPosition || ''}<br/>Tips：${res.pokerTips || ''}`
                res.pokerTip = pokerTip
                this.setData({
                    day: res
                })
                WxParse.wxParse('pokerTip', 'html', this.data.day.pokerTip || 'false', this, 5);
                WxParse.wxParse('daygeneralTxt', 'html', this.data.day.generalTxt || 'false', this, 5);
                WxParse.wxParse('dayloveTxt', 'html', this.data.day.loveTxt || 'false', this, 5);
                WxParse.wxParse('dayworkTxt', 'html', this.data.day.workTxt || 'false', this, 5);
                WxParse.wxParse('daymoneyTxt', 'html', this.data.day.moneyTxt || 'false', this, 5);
                WxParse.wxParse('healthTxt', 'html', this.data.day.healthTxt || 'false', this, 5);
                wx.hideLoading()
            }
        }).catch(err => {
            console.log('今日运势详情返回报错数据：', err)
            wx.showToast({
                title: '抱歉，您的网络有点问题，请稍后再试',
                icon: 'none',
            })
        })
    }
    

})
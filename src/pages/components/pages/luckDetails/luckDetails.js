let $vm = getApp()
const API = require('../../../../utils/api')
const mta = require('../../../../utils/mta_analysis.js')
const Storage = require('../../../../utils/storage')
const star = require('../../../../utils/star')
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
          pop: {}
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
        swCurrent: 0,
        headerlist: ['今日运势', '本周运势','本月运势'], //导航栏数据
        contentlist: ['综合指数', '爱情指数', '财富指数', '工作指数'],
        list: false,       //页面渲染数据
        dta:{           //运势接口数据
            day: {},
            week: {},
            month: {},
        },
        moreText: '查看更多',
        day:{},
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
        wx.setStorageSync('luck_current_id', item.id ? item.id : 1);
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
            console.log('今日运势详情数据：', res)
            if(res!=''){
                res.tip1 = '学习，放松'
                res.tip2 = '打游戏'
                res.mahjong = '麻将牌运普通的日子，今日搓麻将可先以娱乐心态进入，再以赌一把的心态投入，险中求胜美不胜收。'
                res.landlord = '今天的思念之情特别浓烈，和恋人分开时表现出的依依不舍，易让对方更加爱你；朋友到你家做客的机率很大，难免要让你有些破费，不过因此能换来好心情；很懂得处理与同事间的关系，相处会很融洽。'
                this.setData({
                    // 'dta.day': res
                    day: res
                })
                // this.selected()
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
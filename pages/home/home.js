// pages/home/home.js
let $vm = getApp()
const mta = require('../../utils/mta_analysis.js')
const star = require('../../utils/star')
const Storage = require('../../utils/storage')
const methods = require('./methods')
const desc = require('./desc')

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		// 默认不打开星座描述
		showStarDesc : false,
		// 滚动高度
		scrolltop : 0,
		// 星座信息数据
		desc : desc,
		// 是否显示选择星座
		showChoice : false,
		// 星座信息
		star : star,
		// 多少人在玩
		listNum : [{},{}],
		xz : {
			constellationId : 1,
			healthy : 55,
			luckyColor : '紫',
			luckyNum : 9
		},
		isLoading: false,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		// 星座信息
		myConstellation: {},
		// 每日提醒内容
		dayNotice: '好运伴随你',
		timer: null,
		xiaodaka: {
			appId: 'wx855c5d7718f218c9',
			path: '/pages/index/index?wxID=ad6377&scene=gzhgl922689',
			openType: 'navigate',
			extra: '',
			txt: '打卡',
			version: 'release'
		},
		navConf : {
			title : '小哥星座',
			state : 'root',
			isRoot : true,
			isIcon : false,
			iconPath : '',
			bg : '#9262FB',
			root : '',
			isTitle : true,
			centerPath : '/pages/center/center'
		},
		// 导航的高度
		hei : 64,
		showPair : false,
		showBanner : false,
		clockStatus : false,  //小打卡开关
		isBanner : false, // 广告位开关
		isIPhoneX : false,
		PX : false, // iPhone X的适配
		isLogin : false, // 是否登录完成
		noticeBtnStatus : false, // 通知开关
		showFollow : false, // 关注服务号开关
		// 待领星星文案
		more_startext : '0颗待领',
        notice: {isShow: false},  //公告组件
		showCollect : false, // 控制收藏弹窗的显示
		showCollectBtn : false // 控制收藏按钮的显示
	},
	
	// 初始化
	onLoad : methods.onLoad,
	// 收集formId
	setFormId : methods.reportFormId,
	// 前往更多运势
	goLuck : methods.goLuck,
	// 显示
	onShow : methods.onShow,
	// 选择星座
	choiceStar : methods.choiceStar,
	// 获取星座数据信息
	onShowingHome : methods.onShowingHome,
	// 前往选择星座
	goChoiceStar : methods.goChoiceStar,
	// 前往Banner页面
	goBanner : methods.goBanner,
    // //页面关闭
    // onHide:methods.onHide,
	// 用户点击右上角分享
	onShareAppMessage: function (res) {
		return {
			title: '星座运势,唯我独准',
			imageUrl: '/assets/images/share_home.jpg'
		}
	},
	// 前往星座配对
	goPair(){
		wx.navigateTo({
			url:'/pages/components/pages/pair/pair'
		})
	},
	// 前往星座配对
	goPairPYQ(){
		wx.navigateTo({
			url : '/pages/components/pages/pairCus/pairCus'
		})
	},
	// 前往摇签
	goShake(){
		wx.navigateTo({
			url:'/pages/lot/shake/shake'
		})
	},
	// 打开星座描述
	openDesc(){
		this.setData({
			showStarDesc : true,
			scrolltop : 0
		})
	},
	// 关闭信息描述
	closeDesc(){
		this.setData({
			showStarDesc : false
		})
	},
	// 前往运势详情页
	toDay(e) {
		mta.Event.stat("ico_home_to_today", {})
		wx.navigateTo({
			// url: '/pages/today/today'
            url:'/pages/components/pages/luckDetails/luckDetails'
		})
	},
	// 代开客服
	openContact(){
		this.hideFollow()
		// console.log('打开了客服',arguments)
		mta.Event.stat("spread_123437", {})
	},
	//  重置用户登录锁
	_resetToken(){
		// 重置登录锁
		Storage.loginLock = false
	},
	// 关闭弹窗
	hideFollow(){
		console.log('触发关闭')
		this.setData({
			showFollow : false
		})
	},
	// 打开通知并且隐藏
	openNotice(){
		let me = this
		me.setData({
			showFollow : true
		})
		mta.Event.stat("spread_123438", {})
		console.log('弹窗')
		return false
	},
    // 打开收藏
	openCollect(){
		let me = this
		me.setData({
			showCollect : true
		})
		mta.Event.stat("open_collect", {})
		return false
	},
	// 关闭弹窗
	closeCollect(){
		this.setData({
			showCollect : false
		})
	},
	// 关闭收藏提示
	closeCollectBtn(){
		this.setData({
			showCollectBtn : false
		})
		wx.setStorage({
			key:'showCollectBtn',
			data : true
		})
	},
    // 获取公告数据
    getNotice() {
        if (this.data.showChoice){
            this.setData({
                'notice.isShow':false
            })
        }else{
            this.setData({
                'notice.isShow': true
            })
        }

        $vm.api.notice({ page: 1, notShowLoading: true }).then(res => {
            console.log('11111111111111111111', res)
            if (res) {
                let top = [] || 0, bottom=[] || 0
                res.forEach(value => {
                    if (value.type == 1) {
                        top.push(value)
                    } else if (value.type == 2) {
                        bottom.push(value)
                    }
                })

                console.log('top:', top, 'bottom:', bottom)
                if(top==0 && bottom==0){
                    this.setData({
                        'notice.isShow': false
                    })
                }
                
                if(top.length == 1){
                    top.push(top[0])
                }
                if(bottom.length == 1){
                    bottom.push(bottom[0])
                }

                console.log('top:',top,'bottom:',bottom)
                this.setData({
                    'notice.top': top || 0,
                    'notice.bottom': bottom || 0
                })

                console.log(this.data.notice)
                
            }
        }).catch(err => {
            console.log(err)
        })
    },


})



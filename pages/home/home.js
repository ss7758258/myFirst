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
		// 默认不打开星座展示
		showStarDesc : false,
		// 星座信息数据
		desc : desc,
		// 选择星座
		showChoice : true,
		// 星座信息
		star : star,
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
		dayNotice: '',
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
			root : '',
			isTitle : true,
			centerPath : '/pages/center/center'
		},
		clockStatus : false,  //小打卡开关
		isBanner : false, // 广告位开关
		isIPhoneX : false,
		isLogin : false, // 是否登录完成
		noticeBtnStatus : false, // 通知开关
		showFollow : false, // 关注服务号开关
		// 待领星星文案
		more_startext : '0颗待领'
	},

	goMore (e){
		console.log('触犯了更多事件')
	},
	
	// 初始化
	onLoad : methods.onLoad,
	// 收集formId
	setFormId : methods.reportFormId,
	// 前往更多运势
	goLuck : methods.goLuck,
	// 显示
	onShow : methods.onLoad,
	// 选择星座
	choiceStar : methods.choiceStar,
	// 获取星座数据信息
	onShowingHome : methods.onShowingHome,
	// 前往选择星座
	goChoiceStar : methods.goChoiceStar,
	// 前往Banner页面
	goBanner : methods.goBanner,
	// 用户点击右上角分享
	onShareAppMessage: function (res) {
		return {
			title: '用小哥星座，得最全最准的运势预测！',
			imageUrl: '/assets/images/share_home.jpg'
		}
	},
	goPair(){
		wx.navigateTo({
			url:'/pages/components/pages/pair/pair'
		})
	},
	// 打开星座描述
	openDesc(){
		this.setData({
			showStarDesc : true
		})
	},
	// 关闭信息描述
	closeDesc(){
		this.setData({
			showStarDesc : false
		})
	},
	/**
	 * 前往一言
	 * @param {*} Data
	 * @returns
	 */
	goPage (Data) {
		console.log('===============')
		var shouldGo = false
		if (Data.pageFrom == 'share') {
			if (Data.toPage == 'brief') {
				wx.switchTab({
					url: '/pages/onebrief/brief?from=share'
				})
			}
			shouldGo = true
		}
		return shouldGo
	},
	
	/**
	 * 运势
	 * @param {*} e
	 */
	toDay(e) {
		mta.Event.stat("ico_home_to_today", {})
		wx.navigateTo({
			// url: '/pages/today/today'
            url:'/pages/components/pages/luckDetails/luckDetails'
		})
	},
	show_card (e){
		console.log('展示卡片数据：',e)
	},
	openContact(){
		this.hideFollow()
		// console.log('打开了客服',arguments)
		mta.Event.stat("spread_123437", {})
	},
	catchHide(){

	},
	/**
	 * 重置用户登录锁
	 */
	_resetToken(){
		// 重置登录锁
		Storage.loginLock = false
	},
	/**
	 * 关闭弹窗
	 */
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
	}
})


/**
 * 获取一言图片信息
 */
function getDay(){
	$vm.api.getDayx400({ notShowLoading: true })
	.then((res) => {
		console.log(res)
		if (res) {
			let env = 'dev';
			Storage.prevPic = res.prevPic ? "https://xingzuo-1256217146.file.myqcloud.com" + (env === 'dev' ? '' : '/prod') + res.prevPic :
			"";
		}
		
	}).catch((err) => {
		Storage.prevPic = null
	})
}


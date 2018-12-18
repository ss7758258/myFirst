// pages/home/home.js
let $vm = getApp()
const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')
const star = require('../../utils/star')
console.log('输出星座信息:', star)
const Storage = require('../../utils/storage')
const params = require('../../utils/share')
const methods = require('./methods')
const desc = require('./desc')

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		cdn: 'https://xingzuo-1256217146.file.myqcloud.com',
		// 默认不打开星座描述
		showStarDesc: false,
		// 滚动高度
		scrolltop: 0,
		// 星座信息数据
		desc: desc,
		// 是否显示选择星座
		showChoice: false,
		// 星座信息
		star: star,
		// 多少人在玩
		listNum: [{}, {}],
		xz: {
			constellationId: 1,
			healthy: 55,
			luckyColor: '紫',
			luckyNum: 9,
			generalTxt: ''
		},
		isLoading: false,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		// 星座信息
		myConstellation: {},
		timer: null,
		navConf: {
			title: '小哥星座',
			state: 'root',
			isRoot: false,
			isIcon: true,
			iconPath: '',
			bg: '#fff',
			color: 'black',
			fontColor: 'black',
			isTitle: true,
			showContent: false
		},
		// 导航的高度
		hei: 64,
		// 文字动画所需
		sc: 0,
		scIndex: 0,
		scDuration: 800,
		showPair: false,
		showBanner: false,
		clockStatus: false, //小打卡开关
		isBanner: false, // 广告位开关
		isIPhoneX: false,
		PX: false, // iPhone X的适配
		isLogin: false, // 是否登录完成
		noticeBtnStatus: false, // 通知开关
		showFollow: false, // 关注服务号开关
		// 待领星星文案
		more_startext: '0颗待领',
		showDialog: false, // 弹窗提示
		dialo: {},
		notice: {
			isShow: false
		}, //公告组件
		showCollect: false, // 控制收藏弹窗的显示
		showCollectBtn: false, // 控制收藏按钮的显示
		swData: {
			show: false
		}
	},

	// 初始化
	onLoad: methods.onLoad,
	// 收集formId
	setFormId: methods.reportFormId,
	// 显示
	onShow: methods.onShow,
	// 选择星座
	choiceStar: methods.choiceStar,
	// 获取星座数据信息
	onShowingHome: methods.onShowingHome,
	// 前往选择星座
	goChoiceStar: methods.goChoiceStar,
	// 前往Banner页面
	_moreBanner: methods.goBanner,
	_editDate(res){
		let { xz: selectConstellation } = res.detail
		let data = res.detail
		console.log('接收到的参数：',res.detail,data)
		const self = this
		
		if(data){
			let tmp = new Date(data.date)
			self.setData({
				birthDate: (tmp.getMonth() + 1) + '月' + tmp.getDate() + '日'
			})
		}

		$vm = getApp()
		let _GData = $vm.globalData
		
		mta.Event.stat('add_account_info', {
			'constellation': selectConstellation.name
		})
		_GData.selectConstellation = selectConstellation
		// 星座信息
		Storage.starXz = selectConstellation
		
		this.setData({
			myConstellation: selectConstellation,
			'navConf.isIcon': true,
		})
		setTimeout(() => {
			this.setData({
				'swData.show': false,
			})
		}, 200);
		self.onShowingHome()
	},
	// 用户点击右上角分享
	onShareAppMessage: function (res) {
		mta.Event.stat("home_share", {})
		return {
			title: '星座运势,唯我独准',
			imageUrl: '/assets/images/share_home.jpg'
		}
	},
	// 前往一言
	_moreYan() {
		mta.Event.stat("home_brief_click", {})
		wx.navigateTo({
			url:'/pages/onebrief/brief'
		})
	},
	// 前往星座配对
	_morePair() {
		mta.Event.stat("pair_pair_click", {})
		wx.navigateTo({
			url: '/pages/components/pages/pair/pair'
		})
	},
	// 前往个人中心
	_moreCenter(){
		mta.Event.stat("head_click", {})
		wx.switchTab({
			url: '/pages/center/center'
		})
	},
	// 前往星座配对
	goPairPYQ() {
		mta.Event.stat("pair_pyq_click", {})
		wx.navigateTo({
			url: '/pages/components/pages/pairCus/pairCus'
		})
	},
	// 前往摇签
	_moreShake() {
		mta.Event.stat("shake_click", {})
		wx.navigateTo({
			url: '/pages/lot/shake/shake'
		})
	},
	// 前往商品详情
	goExc(e){
    let { res } = e.currentTarget.dataset
    mta.Event.stat('page_goods_click',{id:res.id})
    console.log(res)
    wx.navigateTo({
			url:'/pages/components/pages/goodsInfo/goodsInfo?id=' + res.id
		})
  },
	// 前往占卜测试
	_moreTest() {
		mta.Event.stat("divine_click", {})
		wx.navigateTo({
			url: '/pages/components/pages/divineList/divine'
		})
	},
	// 跳转到精选好物
	_moreExchange() {
		mta.Event.stat("goods_click", {})
		wx.navigateTo({
			url: '/pages/components/pages/goods/goods'
		})
	},
	// 打开星座描述
	_moreDesc() {
		mta.Event.stat("star_desc_click", {})
		this.setData({
			showStarDesc: true,
			scrolltop: 0
		})
	},
	// 获取导航栏高度
	_setHeight(e) {
		console.log('高度信息：',e.detail)
		this.setData({
			nav: e.detail || 64
		})
	},
	// 关闭信息描述
	closeDesc() {
		this.setData({
			showStarDesc: false
		})
	},
	// 前往运势详情页
	_moreLuck(e) {
		mta.Event.stat("ico_home_to_today", {})
		wx.navigateTo({
			url: '/pages/components/pages/luckDetails/luckDetails'
		})
	},
	// 代开客服
	openContact() {
		this.hideFollow()
		// console.log('打开了客服',arguments)
		mta.Event.stat("spread_123437", {})
	},
	//  重置用户登录锁
	_resetToken() {
		// 重置登录锁
		Storage.loginLock = false
	},
	// 关闭弹窗
	hideFollow() {
		console.log('触发关闭')
		this.setData({
			showFollow: false
		})
	},
	// 打开通知并且隐藏
	openNotice() {
		let me = this
		me.setData({
			showFollow: true
		})
		mta.Event.stat("spread_123438", {})
		console.log('弹窗')
		return false
	},
	// 打开收藏
	openCollect() {
		let me = this
		me.setData({
			showCollect: true
		})
		mta.Event.stat("open_collect", {})
		return false
	},
	// 关闭弹窗
	closeCollect() {
		this.setData({
			showCollect: false
		})
	},
	// 关闭收藏提示
	closeCollectBtn() {
		this.setData({
			showCollectBtn: false
		})
		wx.setStorage({
			key: 'showCollectBtn',
			data: true
		})
	},
	// 获取更新信息
	_getUpdate() {
		let self = this
		if (!wx.getStorageSync('update_first_status')) {
			wx.setStorageSync('update_first_status', '999999');
			return
		}
		if (this.data.showChoice) {
			return
		}
		if (wx.getStorageSync('update_dialo_status')) {
			wx.clearStorage({
				key: 'update_dialo_status'
			})
		}

		let _start = (new Date(new Date().toLocaleDateString())).getTime()
		let _end = _start + 1000 * 60 * 60 * 24
		let start = wx.getStorageSync('update_start_time') || _start
		let end = wx.getStorageSync('update_end_time') || _end
		let openNum = wx.getStorageSync('update_open_num') || 0
		let date = (new Date()).getTime()
		if (start < date && date < end && openNum > 0) {
			return
		}
		if (this.options.share_notice === 'shake' || this.options.share_notice === 'lotdetail') {
			return
		}
		API.getUpdate({
			notShowLoading: true
		}).then(res => {
			console.log('输出当前版本更新信息：', res)
			// console.log(JSON.stringify(res))

			if (res && res.prevPic) {
				wx.setStorageSync('update_start_time', _start)
				wx.setStorageSync('update_end_time', _end)
				wx.setStorageSync('update_open_num', 1)
				self.setData({
					showDialog: true,
					dialo: res
				})
			} else {
				self.setData({
					showDialog: false
				})
			}
		})
	},
	// 前往页面
	_goPage() {
		mta.Event.stat(`sure_click`, {})
		let url = this.data.dialo.url
		// url = 'from=home&to=shake&type=nav'
		let _param = {}
		url.split('&').map((v) => {
			let temp = v.split('=')
			_param[temp[0]] = temp[1]
			return 0
		})
		console.log(params)
		let from = _param.from
		let to = _param.to
		let temp = params[to]
		if (temp && temp.constructor === Object) {
			mta.Event.stat(`${from}_${to}`, {})

			if (temp.type === 'tab') {
				wx.switchTab({
					url: temp.path + '?' + url
				})
			} else {
				wx.navigateTo({
					url: temp.path + '?' + url
				})
			}
			this.setData({
				showDialog: false
			})
		} else {
			this.setData({
				showDialog: false
			})
		}
	},
	// 关闭更新提示
	_closeUpdate() {
		mta.Event.stat(`close_notice`, {})
		this.setData({
			showDialog: false
		})
	},
	// 将更新提示放入缓存不在展示
	_cancel() {
		mta.Event.stat(`cancel_notice`, {})
		wx.setStorage({
			key: 'update_dialo_status',
			data: 99999
		})
		this.setData({
			showDialog: false
		})
	}
})
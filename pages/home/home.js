// pages/home/home.js
let $vm = getApp()
const api = $vm.api
const mta = require('../../utils/mta_analysis.js')
const confing = require('../../conf')
const conf = confing[require('../../config')] || {}
const Storage = require('../../utils/storage')
const bus = require('../../event')
const {parseIndex} = $vm.utils
let _GData = $vm.globalData

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;
let timer = null

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		isLoading: false,
		selectBack: false,
		showHome: false,
		hasAuthorize: true,
		signList: $vm.utils.constellation,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		myConstellation: {},
		remindToday: '',
		myLuck: [
			{
				name : '爱情运',
				count : 1,
				color : '#9262FB'
			},
			{
				name : '升值运',
				count : 2,
				color : '#DA6AE4'
			},
			{
				name : '暴富运',
				count : 3,
				color : '#B3B4FF'
			},
			{
				name : '健康运',
				count : 4,
				color : '#88BB74'
			}
		],
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
			isIcon : true,
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
		shareCard : {
			list:[]
		},
		// 待领星星文案
		more_startext : '0颗待领'
	},

	goMore (e){
		console.log('触犯了更多事件')
	},
	selectSign: function (e) {
		const _self = this
		const selectConstellation = e.detail.target.dataset.item
		mta.Event.stat('ico_home_select', { 'constellation': selectConstellation.name })
		_GData.selectConstellation = selectConstellation
		wx.setStorage({
			key: 'selectConstellation',
			data: e.detail.target.dataset.item
		})
		_self.setData({
			myConstellation: selectConstellation,
			selectBack: false,
			showHome: true,
			'navConf.isIcon' : true,
			'selectStatus.current': selectConstellation.id - 1,
			'selectStatus.selected': true
		})
		_self.onShowingHome()
	},

	onShowingHome: function () {
		Storage.prevPic = null
		const _self = this
		const _SData = this.data
		$vm.api.getSelectx100({
			constellationId: _GData.selectConstellation.id,
			nickName: Storage.userInfo.nickName,
			headImage: Storage.userInfo.avatarUrl,
			notShowLoading: true,
		}).then(res => {
			// 获取一言图片
			getDay()
			console.log('输出百分值：',res)
			var myLuck = parseIndex(res)
			this.setData({
				myLuck: myLuck,
				'shareCard.list': formatShareCard(res),
				remindToday: res.remindToday ? res.remindToday : ''
			})
			if (!_self.goPage(_SData)) {
				const myLuckLen = myLuck.length
				_self.circleDynamic()();
			}
		}).catch(err => {
			console.log(err)
		})

	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		// 重置登录信息
		Storage.homeLogin = false
		getSystemInfo(this);
		mta.Page.init()
		Storage.forMore = false
		let self = this
		Storage.homeSelf = this

		// 获取乐摇摇推广信息
		getLeYaoyao(self,options)
		
		if(Storage.loadUserConfRemoveId){
			bus.remove(Storage.loadUserConfRemoveId)
		}

		// 注册监听事件
		Storage.loadUserConfRemoveId = bus.on('loadUserConf',() => {
			if(Storage.forMore){
				// 加载用户配置
				getUserConf(self)
				getStarNum(self)
			}
		},'home')
		
		// 用于解析用户来源
		parseForm(self,options)

		let handle = () => {

			$vm = getApp()
			_GData = $vm.globalData
			
			// 登录状态
			Storage.homeLogin = true

			Storage.homeSelf.setData({
				isLogin : true
			})

			// 加载用户配置的依赖
			Storage.forMore = true
			// 触发加载用户配置函数
			bus.emit('loadUserConf',{},'home')

			// 获取选中星座的数据
			getContent(Storage.homeSelf,_GData.selectConstellation)

			console.log('用户信息======================：',Storage.userInfo)
			Storage.homeSelf.setData({
				'navConf.iconPath' : Storage.userInfo.avatarUrl
			})

			_GData.userInfo = Storage.userInfo

			// 获取配置信息
			getConfing(Storage.homeSelf);

			// 保存头像信息
			wx.setStorageSync('icon_Path', Storage.userInfo.avatarUrl)
		}
		
		// 是否是首次注册
		// if(!Storage.firstHome){
		// 	Storage.firstHome = true
		// }
		// 移除事件
		if(Storage.homeRemoveId){
			bus.remove(Storage.homeRemoveId)
		}
		if(Storage.homeLoginRemoveId){
			bus.remove(Storage.homeLoginRemoveId)
		}
		Storage.homeLoginRemoveId = bus.on('login-success', handle , 'login-com')
		Storage.homeRemoveId = bus.on('login-success', handle , 'home')
		
		// 如果已经存在用户信息触发登录标识
		if(Storage.userInfo){
            // 已经触发过登录不在触发
			if(Storage.homeLogin){
				return
			}
			bus.emit('login-success', {}, 'home')
		}
	},

	onShow(){
		// console.log('是否已经登录：',Storage.isLogin)
		// if(!Storage.isLogin){
		// 	bus.emit('no-login-app', {} , 'app')
		// 	return
        // }
		// 触发加载用户配置函数
		bus.emit('loadUserConf',{},'home')
	},
	/**
	 * app隐藏时判断
	 */
	onHide(){
		if(!Storage.isLogin){
			wx.redirectTo({
				url : '/pages/home/home'
			})
		}
	},
	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function (res) {
		if (res.from = 'menu') {
			mta.Event.stat("ico_from_home", {})
		}

		return {
			title: '用小哥星座，得最全最准的运势预测！',
			imageUrl: '/assets/images/share_home.jpg',
			success: function (res) {
				// 转发成功
			},
			fail: function (res) {
				// 转发失败
			}
		}
	},
	/**
	 * 前往一言
	 * @param {*} _SData
	 * @returns
	 */
	goPage (_SData) {
		console.log('===============')
		var shouldGo = false
		if (_SData.pageFrom == 'share') {
			if (_SData.toPage == 'brief') {
				wx.navigateTo({
					url: '/pages/onebrief/brief?from=share'
				})
			}
			shouldGo = true
		}
		return shouldGo
	},

	tween: function (t, b, c, d) {
		return c * t / d + b;
	},
	//圈圈的动态
	circleDynamic: function (n) {

		const _self = this
		const _SData = this.data
		const myLuckList = _SData.myLuck
		let keys = [], counts = [];

		myLuckList.forEach((v, ind) => {
			keys.push('myLuck[' + ind + '].count');
			counts.push(myLuckList[ind].count);
		});

		let t = 0, b = 0, d = 15;

		function price() {
			if (!_self.data.showHome) {
				return
			}
			t++
			if (t > d) {
				return
			} else {
				keys.forEach((v, ind) => {
					_self.setData({
						[v]: Math.floor(_self.tween(t, b, counts[ind], d))
					})
				})
			}
			// 记录timer
			timer = setTimeout(price, 15);
			
		}
		return price
	},

	onClickConstellation: function () {
		// 清除定时
		clearTimeout(timer ? timer : '');
		mta.Event.stat("ico_home_unselect", {})
		wx.setStorage({
			key: 'selectConstellation',
			data: null,
		})
		_GData.selectConstellation = null
		this.setData({
			selectBack: true,
			showHome: false,
			'navConf.isIcon' : false,
			'selectStatus.current': -1,
			'selectStatus.selected': false
		})

	},
	/**
	 * 摇签页面
	 * @param {*} e
	 */
	onelot(e) {
		const _self = this
		if (_self.data.isLoading) {
			return
		}
		_self.setData({
			isLoading: true
		})
		let formid = e.detail.formId
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_shake", {})
		wx.navigateTo({
			url: '/pages/lot/shake/shake?fromSource=home&formid=' + formid,
			complete: function (res) {
				_self.setData({
					isLoading: false
				})
			}
		})
	},
	oneword: function (e) {
		console.log('进入一言',e)
		let type = e.detail.target.dataset.key || 'yiyan';
		let formid = e.detail.formId
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		if(type === 'more'){
			mta.Event.stat("ico_home_to_banner", {})
			wx.navigateTo({
				url: '/pages/banner/banner?formid=' + formid
			})
			return false;
		}
		mta.Event.stat("ico_home_to_brief", {})
		wx.navigateTo({
			url: '/pages/onebrief/brief?formid=' + formid
		})
	},
	/**
	 * 运势
	 * @param {*} e
	 */
	today(e) {
		let formid = e.detail.formId
		let me = this
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_today", {})
		
		let temp = wx.getStorageSync('userInfo') || {nickName : ''}
		wx.navigateTo({
			url: '/pages/today/today?formid=' + formid
		})
	},
	show_card (e){
		console.log('展示卡片数据：',e)
	},
	openContact(){
		this.hideFollow()
		mta.Event.stat("spread_123437", {})
		// console.log('打开了客服',arguments)
	},
	catchHide(){

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
 * 获取需要token的用户配置
 * @param {*} me
 */
function getUserConf(me){
	
	api.getUserSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成---------用户:',res);
		if(!res){
			console.log('----------------输出错误信息----------用户配置错误')
			return false;
		}
		// res.noticeStatus = 0
		// 确认小打卡配置信息
		me.setData({
			noticeBtnStatus :  res.noticeStatus === 0,
			clockStatus : res.clockStatus === 1
		})
		
		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
		
	}).catch( err => {
		console.log('加载用户配置失败---------------------------------用户配置错误')
	})
}

/**
 * 获取配置信息
 * @param {*} me
 */
function getConfing(me){

	api.globalSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成---------全局：',res);
		if(!res){
			return false;
		}

		// 变更状态
		me.setData({
			isBanner : res.bannerStatus === 1,
			clockStatus : res.clockStatus === 1
		})

		// res.adBtnText = '开始'
		wx.setStorageSync('adBtnText', res.adBtnText ? res.adBtnText : '查看');
		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
	}).catch( err => {
		console.log('加载失败---------------------------------全局配置')
	})
}


/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self){
	let res = Storage.systemInfo
	console.log('设备信息：',res);
	if(res){
		// 长屏手机适配
		if(res.screenWidth <= 375 && res.screenHeight >= 750){
			wx.setStorageSync('IPhoneX', true);
			self.setData({
				isIPhoneX : true
			})
		}
	}
}

/**
 * 将对象解析成所需要的数组
 * @param {*} res
 */
function formatShareCard(res){
	if(!res) return
	let temps = []
	for(let ind = 0;ind < 4; ind++){
		temps.push({
			imgUrl : conf.cdn + '/' + res['luckyImg' + (ind + 1)] || '',
			content : res['luckyContent' + (ind + 1)] || '',
			text_score : (res['luckyType' + (ind + 1)] || '') + ('（' + res['luckyScore' + (ind + 1)] + '）' || ''),
			score : parseHandle(res['luckyScore' + (ind + 1)] || 10),
			nickName : _GData.userInfo.nickName
		})
	}
	console.log('卡片数据信息：',temps)
	return temps
}

function parseHandle(res){
    let temp = parseInt(res.replace('%'))
    // console.log(Math.ceil(temp / 10))
    return Math.ceil(temp / 10)
}

/**
 * 解析来源
 * @param {*} self
 */
function parseForm(self,options){
	let fromwhere = options.from
	let to = options.to
	if (fromwhere == 'share' || fromwhere == 'activity') {
		self.setData({
			toPage: to,
			pageFrom: fromwhere
		})
		if (to == 'brief') {
			if (options.hotapp == 1) {
				mta.Event.stat("ico_in_from_brief_qrcode", {})
			} else {
				mta.Event.stat("ico_in_from_brief", {})
			}

		} else if (to == 'today') {
			if (options.hotapp == 1) {
				mta.Event.stat("ico_in_from_today_qrcode", {})
			} else if (fromwhere == 'activity') {
				console.log('ico_in_from_brief_activity')
				mta.Event.stat("ico_in_from_brief_activity", {})
			} else {
				mta.Event.stat("ico_in_from_today", {})
			}

		}
	}else if(fromwhere === 'spread'){ // 活动推广统计
		console.log('输出活动来源',options.id)
		if (reg.test(options.id)) {
			mta.Event.stat('spread_' + options.id, {})
		} else {
			mta.Event.stat('spread_unknown', {})
		}
	}
	
	// 统计特殊来源
	if(options.source && options.source.constructor === String && options.source !== ''){
		console.log('输出活动来源',options.id)
		if (reg.test(options.id)) {
			mta.Event.stat(options.source + '_' + options.id, {})
		} else {
			mta.Event.stat(options.source + '_unknown', {})
		}
	}
}

/**
 * 获取每日的幸运值
 * @param {*} self
 * @param {*} selectConstellation
 */
function getContent(self,selectConstellation){
	if (selectConstellation && !selectConstellation.isFirst) {
		self.setData({
			myConstellation: selectConstellation,
			selectBack: false,
			showHome: true,
			'navConf.isIcon' : true
		})
		self.onShowingHome()
	} else {
		self.setData({
			showHome: false,
			'navConf.isIcon' : false
		})
	}
}

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

/**
 * 乐摇摇外链数据获取
 * @param {*} self
 * @param {*} options
 */
function getLeYaoyao(self,options){
	console.log(options)
	if(!options.q) return
	console.log('输出用户来源参数：',decodeURIComponent(options.q))
	mta.Event.stat('spread_123435', {})
	let url = decodeURIComponent(options.q)
	console.log('链接地址：',url)
	if (String(url).indexOf('leyaoyao?') > 0 ) {
		let temps = []
		temps.push(url.split('leyaoyao?')[1])
		temps.push(`&appid=${confing.appId}`)
		// 拉取乐摇摇数据信息
		api.getLeYaoyao(temps.join('')).then(res => {
			console.log('乐摇摇返回信息：',res)
			if(res && res.data && res.data.constructor === Object){
				// res.data.result = 0
				switch (res.data.result) {
					case 0:
						wx.showModal({
							title: '游戏币已到账',
							content: '更多好玩，尽在小哥星座',
							showCancel: false,
							confirmText: '马上体验',
							confirmColor: '#3CC51F',
							success: res => {
								
							}
						});
						break;
					default:
						errorToast()
					break;
				}
			}
		}).catch(err => {
			errorToast()
			console.log('乐摇摇返回异常=========：',err)
		})
	}
}

/**
 * 乐摇摇错误提示
 */
function errorToast(){
	wx.showToast({
		title:'领取失败',
		icon : 'none',
		mask : true,
		duration : 3000
	})
}
/**
 * 获取星星数量
 * @param {*} self
 */
function getStarNum(self){
	wx.request({
		url : 'https://micro.yetingfm.com/appwall/front/star/unreceived_num',
		method: 'GET',
		data: {
			openId : Storage.openId,
			appId : confing.appId
		},
		success (res){
			if(res.statusCode === 200){
			
				console.log(`星星数量${res.data.data}`)

				let text =  res.data.data || 0
				self.setData({
					more_startext : text + '颗待领'
				})
			}
		},
		fail(){

		}
	})
}
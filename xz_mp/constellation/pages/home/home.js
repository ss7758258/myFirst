const $vm = getApp() // 顶级对象实例
const _GData = $vm.globalData
const {
	parseIndex
} = $vm.utils
const api = $vm.api
const mta = require('../../utils/mta_analysis.js') //腾讯云数据分析
const reg = /^\d{6}$/ // 验证Id是否位6位纯数字

Page({
	//页面的初始数据
	data: {
		isLoading: false, //是否加载
		selectBack: false, // 回退
		showHome: false, //显示主页
		hasAuthorize: true,
		signList: $vm.utils.constellation,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		myConstellation: {},
		remindToday: '',
		myLuck: [{
				name: '爱情运',
				count: 1,
				color: '#9262FB'
			},
			{
				name: '升值运',
				count: 2,
				color: '#DA6AE4'
			},
			{
				name: '暴富运',
				count: 3,
				color: '#B3B4FF'
			},
			{
				name: '健康运',
				count: 4,
				color: '#88BB74'
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
		navConf: {
			title: '小哥星座',
			state: 'root',
			isRoot: true,
			isIcon: true,
			iconPath: '',
			root: '',
			isTitle: true,
			centerPath: '/pages/center/center'
		},
		clockStatus: false, //小打卡开关
		isBanner: false, // 广告位开关
		isIPhoneX: false //判断是否是iPhoneX
	},

	//生命周期函数--监听页面加载
	onLoad: function (options) {
		getSystemInfo(this) //获取设备信息，判断是否是长屏手机
		mta.Page.init() //页面初始化
		const selectConstellation = _GData.selectConstellation

		if (selectConstellation && !selectConstellation.isFirst) {
			this.setData({
				myConstellation: selectConstellation,
				selectBack: false,
				showHome: true,
				'navConf.isIcon': true
			})
			this._renderLuckCircle()
		} else {
			this.setData({
				showHome: false,
				'navConf.isIcon': false
			})
		}

		let fromwhere = options.from
		let to = options.to

		if (fromwhere === 'share' || fromwhere === 'activity') {
			this.setData({
				toPage: to,
				pageFrom: fromwhere
			})
			if (to === 'brief') {
				if (options.hotapp === 1) {
					mta.Event.stat("ico_in_from_brief_qrcode", {})
				} else {
					mta.Event.stat("ico_in_from_brief", {})
				}

			} else if (to === 'today') {
				if (options.hotapp === 1) {
					mta.Event.stat("ico_in_from_today_qrcode", {})
				} else if (fromwhere === 'activity') {
					mta.Event.stat("ico_in_from_brief_activity", {})
				} else {
					mta.Event.stat("ico_in_from_today", {})
				}
			}
		} else if (fromwhere === 'spread') { // 活动推广统计
			if (reg.test(options.id)) {
				mta.Event.stat('spread_' + options.id, {})
			} else {
				mta.Event.stat('spread_unknown', {})
			}
		}

		// 统计特殊来源
		if (options.source && options.source.constructor === String && options.source !== '') {
			if (reg.test(options.id)) {
				mta.Event.stat(options.source + '_' + options.id, {})
			} else {
				mta.Event.stat(options.source + '_unknown', {})
			}
		}

		wx.getUserInfo({
			success:  (res)=> {
				let { userInfo } = res
				if (userInfo) {
					wx.setStorage({
						key: 'userInfo',
						data: userInfo
					})
					// 获取配置信息
					getConfig(this)
					wx.setStorageSync('icon_Path', userInfo.avatarUrl)
					this.setData({
						hasAuthorize: true,
						'navConf.iconPath': userInfo.avatarUrl
					})
					_GData.userInfo = userInfo
					api.getSelectx100({
						constellationId: _GData.selectConstellation.id,
						nickName: userInfo.nickName,
						headImage: userInfo.avatarUrl,
						notShowLoading: true,
					})
				}
			},
			fail: function () {
				// 检查是否授权
				wx.getSetting({
					success:  (res)=> {
						if (!res.authSetting['scope.userInfo']) {
							this.setData({
								hasAuthorize: false
							})
							wx.redirectTo({
								url: '/pages/checklogin/checklogin?from=' + fromwhere + '&to=' + to
							})
						}
					}
				})
			}
		})
	},

	// 点击右上角分享
	onShareAppMessage: function (res) {
		mta.Event.stat("ico_from_home", {})
		return {
			title: '用小哥星座，得最全最准的运势预测！',
			imageUrl: '/assets/images/share.jpg'
		}
	},

	// 获取幸运值数据并渲染
	_renderLuckCircle() {
		api.getSelectx100({
			constellationId: _GData.selectConstellation.id,
			nickName: _GData.userInfo.nickName,
			headImage: _GData.userInfo.avatarUrl,
			notShowLoading: true,
		}).then(res => {
			var myLuck = parseIndex(res)
			this.setData({
				myLuck: myLuck,
				remindToday: res.remindToday ? res.remindToday : ''
			})

			if (!this._pageSource(this.data)) {
				this._circleDynamic()()
			}
		})
	},

	// 判断 来源是“分享” 并且 前往页面是 “一言”
	_pageSource(_SData) {
		let shouldGo = false
		if (_SData.pageFrom === 'share' && _SData.toPage === 'brief') {
			wx.navigateTo({
				url: '/pages/onebrief/brief?from=share'
			})
			shouldGo = true
		}
		return shouldGo
	},

	//圈圈的动态
	_circleDynamic() {
		const _SData = this.data
		const myLuckList = _SData.myLuck

		let keys = []
		let counts = []
		let countOffset = (t, b, c, d) => c * t / d + b

		myLuckList.forEach((v, ind) => {
			keys.push('myLuck[' + ind + '].count')
			counts.push(myLuckList[ind].count)
		})

		let t = 0
		let b = 0
		let d = 15

		function price() {
			if (!this.data.showHome) return
			t++
			if (t > d) return
			keys.forEach((v, ind) => {
				this.setData({
					[v]: Math.floor(countOffset(t, b, counts[ind], d))
				})
			})
			this.data.timer = setTimeout(price, 15)
		}
		return price
	},

	// 点击选择某个星座触发事件
	handleConstellationClick(e) {

		// 获取该元素的dom信息
		const selectConstellation = e.detail.target.dataset.item

		// 自定义事件上报
		mta.Event.stat('ico_home_select', {
			'constellation': selectConstellation.name
		})

		// 更新全局数据的selectConstellation
		_GData.selectConstellation = selectConstellation

		// 设置本地缓存
		wx.setStorage({
			key: 'selectConstellation',
			data: selectConstellation,
		})

		this.setData({
			myConstellation: selectConstellation,
			selectBack: false,
			showHome: true,
			'navConf.isIcon': true,
			'selectStatus.current': selectConstellation.id - 1,
			'selectStatus.selected': true
		})

		this._renderLuckCircle()
	},

	//点击当前星座，清空_GData和Storage
	handleCurrentConstellationClick(e) {

		// 清除定时器,重新绘制幸运值圆圈
		if (this.data.timer) {
			clearTimeout(this.data.timer)
		}

		mta.Event.stat("ico_home_unselect", {})
		wx.setStorage({
			key: 'selectConstellation',
			data: null,
		})
		_GData.selectConstellation = null
		this.setData({
			selectBack: true,
			showHome: false,
			'navConf.isIcon': false,
			'selectStatus.current': -1,
			'selectStatus.selected': false
		})
	},

	//点击“一签”触发事件
	handleOneLotClick(e) {
		if (this.data.isLoading) return
		this.setData({
			isLoading: true
		})

		let formid = e.detail.formId

		api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_shake", {})

		wx.navigateTo({
			url: '/pages/lot/shakelot/shake?formid=' + formid,
			complete() {
				this.setData({
					isLoading: false
				})
			}
		})
	},

	// 点击“一言”触发事件
	handleOneWordClick(e) {
		let key = e.detail.target.dataset.key || 'yiyan'
		let formid = e.detail.formId

		api.getX610({
			notShowLoading: true,
			formid: formid
		})

		if (key === 'more') {
			mta.Event.stat("ico_home_to_banner", {})
			wx.navigateTo({
				url: '/pages/banner/banner?formid=' + formid
			})
			return
		}

		mta.Event.stat("ico_home_to_brief", {})
		wx.navigateTo({
			url: '/pages/onebrief/brief?formid=' + formid
		})
	},

	// 点击跳转运势详情
	handleLuckDetailClick(e) {
		let formid = e.detail.formId
		api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_today", {})
		wx.navigateTo({
			url: '/pages/today/today?formid=' + formid
		})
	},

	bindGetUserInfo(e) {
		if (e.detail.userInfo) {
			wx.setStorage({
				key: 'userInfo',
				data: e.detail.userInfo,
			})
			this.setData({
				hasAuthorize: true
			})
			_GData.userInfo = e.detail.userInfo
			api.getSelectx100({
				nickName: e.detail.userInfo.nickName,
				headImage: e.detail.userInfo.avatarUrl,
				notShowLoading: true,
			})
		}
	}
})

/**
 * 获取配置信息
 * @param {*} obj
 */
function getConfig(obj) {
	// 默认请求参数
	const param = {
		notShowLoading: true
	}

	// 加载失败信息
	const FailMsg = function (option) {
		return Object.assign({}, {
			title: '加载配置失败，请小主检查网络后再试',
			icon: 'none',
			mask: true
		}, option)
	}

	// 获取用户信息
	api.getUserSetting(param).then(res => {
		if (!res) {
			wx.showToast(FailMsg)
			return
		}
		// 确认小打卡配置信息
		obj.setData({
			clockStatus: res.clockStatus && res.clockStatus === 1
		})
		// 保存通知开关状态
		wx.setStorageSync('noticeStatus', res.noticeStatus || 0)

		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus || 0)

	}).catch(err => {
		wx.showToast(FailMsg)
	})

	// 获取用户信息
	api.globalSetting(param).then(res => {
		if (!res) {
			wx.showToast(FailMsg)
			return
		}

		obj.setData({
			isBanner: res.bannerStatus && res.bannerStatus === 1,
			clockStatus: res.clockStatus && res.clockStatus === 1
		})

		// res.adBtnText默认是'开始'
		wx.setStorageSync('adBtnText', res.adBtnText || '查看')

		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus || 0)

	}).catch(err => {
		wx.showToast(FailMsg)
	})
}

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self) {
	let res = wx.getSystemInfoSync()

	if (res) {
		// 长屏手机适配
		if (res.screenWidth <= 375 && res.screenHeight >= 750) {
			self.setData({
				isIPhoneX: true
			})
		}
	}
}
// pages/home/home.js
const $vm = getApp()
const _GData = $vm.globalData
const api = $vm.api
var mta = require('../../utils/mta_analysis.js')
const conf = require('../../conf')[require('../../config')] || {}

const {
	parseIndex
} = $vm.utils

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;

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
		noticeBtnStatus : false, // 通知开关
		shareCard : {
			list:[]
		}
	},

	goMore (e){
		console.log('触犯了更多事件')
	},
	selectSign: function (e) {
		const _self = this
		const _SData = this.data

		const selectConstellation = e.detail.target.dataset.item
		mta.Event.stat('ico_home_select', { 'constellation': selectConstellation.name })
		_GData.selectConstellation = selectConstellation
		wx.setStorage({
			key: 'selectConstellation',
			data: e.detail.target.dataset.item,
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
		const _self = this
		const _SData = this.data
		$vm.api.getSelectx100({
			constellationId: _GData.selectConstellation.id,
			nickName: _GData.userInfo.nickName,
			headImage: _GData.userInfo.avatarUrl,
			notShowLoading: true,
		}).then(res => {
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
		getSystemInfo(this);
		mta.Page.init()
		const _self = this
		const _SData = this.data
		const selectConstellation = _GData.selectConstellation
		if (selectConstellation && !selectConstellation.isFirst) {
			_self.setData({
				myConstellation: selectConstellation,
				selectBack: false,
				showHome: true,
				'navConf.isIcon' : true
			})
			_self.onShowingHome()
		} else {
			_self.setData({
				showHome: false,
				'navConf.isIcon' : false
			})
		}

		console.log('输出参数：', options)
		let fromwhere = options.from
		let to = options.to
		if (fromwhere == 'share' || fromwhere == 'activity') {
			_self.setData({
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
		}
		else if(fromwhere === 'spread'){ // 活动推广统计
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
		
		let me = this;
		wx.getUserInfo({
			success: function (res) {
				console.log('获取用户配置成功：',res)
				if (res.userInfo) {
					wx.setStorage({
						key: 'userInfo',
						data: res.userInfo,
					})
					$vm.getLogin().then(res => {
						wx.setStorageSync('token', res.token)
						// 获取配置信息
						getConfing(me);
					})
					wx.setStorageSync('icon_Path', res.userInfo.avatarUrl)
					_self.setData({
						hasAuthorize: true,
						'navConf.iconPath' : res.userInfo.avatarUrl
					})
					_GData.userInfo = res.userInfo
					$vm.api.getSelectx100({
						constellationId: _GData.selectConstellation.id,
						nickName: res.userInfo.nickName,
						headImage: res.userInfo.avatarUrl,
						notShowLoading: true,
					}).then(res => {

					})
				}
			},
			fail: function (res) {
				// 查看是否授权
				wx.getSetting({
					success: function (res) {
						if (!res.authSetting['scope.userInfo']) {

							_self.setData({
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

	onShow(){
		if(wx.getStorageSync('noticeStatus') === 1){
			this.setData({
				noticeBtnStatus : false
			})
		}else if(wx.getStorageSync('noticeStatus') === 0){
			// 在用户未点击 且状态关闭时 按钮开关为打开状态下开启
			this.setData({
				noticeBtnStatus : true
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
			imageUrl: '/assets/images/share.jpg',
			success: function (res) {
				// 转发成功
			},
			fail: function (res) {
				// 转发失败
			}
		}
	},

	goPage: function (_SData) {
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
			let temp = setTimeout(price, 15);

			_self.setData({
				timer: temp
			})

		}
		return price
	},

	onClickConstellation: function () {

		clearTimeout(this.data.timer ? this.data.timer : '');
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
	bindGetUserInfo: function (e) {


		if (e.detail.userInfo) {
			wx.setStorage({
				key: 'userInfo',
				data: e.detail.userInfo,
			})
			this.setData({
				hasAuthorize: true
			})
			_GData.userInfo = e.detail.userInfo
			$vm.api.getSelectx100({
				nickName: e.detail.userInfo.nickName,
				headImage: e.detail.userInfo.avatarUrl,
				notShowLoading: true,
			}).then(res => {

			})
		}

	},
	onelot: function (e) {
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
			url: '/pages/lot/shakelot/shake?formid=' + formid,
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
	today: function (e) {
		let formid = e.detail.formId
		let me = this
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_today", {})
		
		let temp = wx.getStorageSync('userInfo') || {nickName : ''}
		console.log('/pages/home/home?id=' + _GData.selectConstellation.id + '&nickName=' + temp.nickName)
		// wx.navigateToMiniProgram({
		// 	appId: 'wx0c094a79c17431cc',
		// 	path: '/pages/home/home?id=' + _GData.selectConstellation.id + '&nickName=' + temp.nickName,
		// 	success(res) {
		// 		// 打开成功
		// 	}
		// })
		wx.navigateTo({
			url: '/pages/today/today?formid=' + formid
		})
	},
	show_card (e){
		console.log('展示卡片数据：',e)
	},
	// 打开通知并且隐藏
	openNotice(){
		let me = this
		// 更改通知设置 
		api.setUserSetting({
            noticeStatus : 1,
            notShowLoading : true
        }).then((res) => {
            console.log('通知开关成功：',res)
            if(res){
                me.setData({
                    noticeBtnStatus : false
                })
				// 保存通知开关状态
				wx.setStorageSync('noticeStatus', 1);
				wx.showToast({
					title: '已为您开启提醒，关闭请前往个人中心',
					icon: 'none',
					duration: 2000
				})
                return false;
            }
			me.setData({
				noticeBtnStatus : false
			})
        }).catch( () => {
            console.log('通知开关失败：',res)
            me.setData({
				noticeBtnStatus : false
			})
			// 保存通知开关状态
			wx.setStorageSync('noticeStatus', 0);
        })
	}
})

/**
 * 获取配置信息
 * @param {*} me
 */
function getConfing(me){
	api.getUserSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成：',res);
		if(!res){
			wx.showToast({
				title : '加载配置失败，请小主检查网络后再试',
				icon: 'none',
				mask: true
			})
			return false;
		}
		// 确认小打卡配置信息
		me.setData({
			clockStatus : res.clockStatus && res.clockStatus === 1 ? true : false
		})
		// 保存通知开关状态
		wx.setStorageSync('noticeStatus', res.noticeStatus ? res.noticeStatus : 0);
		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
		let noticeFlag = true
		// 开启了通知
		if(wx.getStorageSync('noticeStatus') === 1){
			noticeFlag = false
		}
		// 用户关闭了通知
		if(wx.getStorageSync('noticeStatus') === 0){
			noticeFlag = true
		}
		me.setData({
			noticeBtnStatus : noticeFlag
		})
	}).catch( err => {
		wx.showToast({
			title : '加载配置失败，请小主检查网络后再试',
			icon: 'none',
			mask: true
		})
	})

	api.globalSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成：',res);
		if(!res){
			wx.showToast({
				title : '加载配置失败，请小主检查网络后再试',
				icon: 'none',
				mask: true
			})
			return false;
		}

		// 变更状态
		me.setData({
			isBanner : res.bannerStatus && res.bannerStatus === 1 ? true : false,
			clockStatus : res.clockStatus && res.clockStatus === 1 ? true : false
		})

		// res.adBtnText = '开始'
		wx.setStorageSync('adBtnText', res.adBtnText ? res.adBtnText : '查看');
		// 默认小打卡是关闭状态
		wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
	}).catch( err => {
		wx.showToast({
			title : '加载配置失败，请小主检查网络后再试',
			icon: 'none',
			mask: true
		})
	})
}


/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self){
	let res = wx.getSystemInfoSync();
	console.log('设备信息：',res);
	if(res){
		// 长屏手机适配
		if(res.screenWidth <= 375 && res.screenHeight >= 750){
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
    console.log(Math.ceil(temp / 10))
    return Math.ceil(temp / 10)
}
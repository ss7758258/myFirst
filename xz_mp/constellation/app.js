//app.js
const aldstat = require("./utils/ald-stat.js")
const utils = require('./utils/util.js')
const api = require('./utils/api.js')
const mta = require('./utils/mta_analysis.js')
const bus = require('./event')
const Storage = require('./utils/storage')
const methods = require('./server/login')
const updateManager = wx.getUpdateManager()
App({
	onLaunch: function (options) {
		
		Storage.isLogin = false
		console.log('开始获取设备信息')
		// 获取用户的设备信息
		getSystemInfo()
		getGlobal()
		const _self = this
		const _SData = this.globalData
		// 检查用户的登录信息
		methods.checkLogin()
		// updateHandle();
		// 处理登录问题
		// loginHandle(this)
		_SData.selectConstellation = wx.getStorageSync('selectConstellation') || { id: 1, name: "白羊座", time: "3.21-4.19", img: "/assets/images/aries.png", isFirst: true }
		_SData.userInfo = wx.getStorageSync('userInfo')
		mta.App.init({
			"appID": "500613478",
			"eventID": "500614618", // 高级功能-自定义事件统计ID，配置开通后在初始化处填写
			"lauchOpts": options, //渠道分析,需在onLaunch方法传入options,如onLaunch:function(options){...}
			"statPullDownFresh": true, // 使用分析-下拉刷新次数/人数，必须先开通自定义事件，并配置了合法的eventID
			"statShareApp": true, // 使用分析-分享次数/人数，必须先开通自定义事件，并配置了合法的eventID
			"statReachBottom": true // 使用分析-页面触底次数/人数，必须先开通自定义事件，并配置了合法的eventID
		})
	},
	/**
	 * 分享触发消息
	 * @param {*} res
	 */
	onShow (res){
		console.log('触发全局实例：',res)
	},
	getLogin() {
		// wx.removeStorageSync('token')
		// 初始化状态值
		// Storage.init()
		return new utils.Promise((resolve, reject) => {
			return utils.login().then(res => {
				console.log('获取到的code信息：',res)
				api.getLogin({
					notShowLoading: true,
					code: res.code
				}).then(data => {
					resolve(data)
				}).catch(err => {
					reject(err)
				})
			})
		})
	},
   
	globalData: {
		selectConstellation: wx.getStorageSync('selectConstellation') || { id: 1, name: "白羊座", time: "3.21-4.19", img: "/assets/images/aries.png", isFirst: true },
		userInfo: null,
		lotDetail: {},
	},
	//初始化
	utils,
	api
})

/**
 * 版本更新处理
 */
function updateHandle(){
	// 检查是否有更新
	updateManager.onCheckForUpdate(function (res) {
		// 请求完新版本信息的回调
		console.log('版本更新：',res.hasUpdate)
		if(res.hasUpdate){
			// 触发弹窗提示
			bus.emit('check_update',{ res },'app')
		}
	})
	// 当小程序下载完成后
	updateManager.onUpdateReady(function () {
		// 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
		bus.on('update_ready',(res) => {
			updateManager.applyUpdate()
		},'app')
	})
	// 当小程序下载失败
	updateManager.onUpdateFailed(function () {
		// 新的版本下载失败
		wx.hideLoading();
		wx.showToast({
			title: '升级失败，请重新尝试',
			icon: 'none',
			duration: 1500,
		});
		setTimeout(function(){
			bus.emit('update_fail',{},'app')
		},1500)
	})
}

/**
 * 获取系统比例加入比例标识
 */
function getSystemInfo(){
	let res = wx.getSystemInfoSync();
	console.log('app实例下的设备信息：',res)
	if(res){
		Storage.systemInfo = res
		wx.setStorage({
			key: 'systemInfo',
			data: res
		});
		if(res.screenWidth <= 375 && res.screenHeight >= 750){
			// 是否是长屏机型
			Storage.LongScreen = true
			wx.setStorageSync('LongScreen', true);
		}
        if(res.model.indexOf('iPhone X') != -1){
			wx.setStorage({
				key: 'iPhoneX',
				data: true
			});
			Storage.iPhoneX = true
        }else{
			wx.setStorage({
				key: 'iPhoneX',
				data: false
			});
			Storage.iPhoneX = false
		}
		// 确认是不是ios系统
		if(res.system.toLowerCase().indexOf('ios') != -1){
			Storage.sys = 'ios'
		}else{
			Storage.sys = 'android'
		}
	}
}

/**
 * 获取全局ios功能 以及星星价格
 */
function getGlobal(){
	api.globalSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成---------全局：',res);
		if(!res){
			return false;
		}
		// 默认开
		// res.openIos = 1
		// 星星加个
		Storage.starPrice = res.price || 0
		Storage.openIos = res.openIos || 0
		Storage.openAndriod = res.openAndriod || 0
	}).catch( err => {
		console.log('加载失败---------------------------------全局配置')
	})
}
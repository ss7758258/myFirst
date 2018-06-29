//app.js
const aldstat = require("./utils/ald-stat.js")
const utils = require('./utils/util.js')
const api = require('./utils/api.js')
const mta = require('./utils/mta_analysis.js')
const Storage = require('./utils/storage')
App({
	onLaunch: function (options) {
		const _self = this
		const _SData = this.globalData
		// 处理登录问题
		loginHandle(this)
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

	getLogin() {
		return new utils.Promise((resolve, reject) => {
			return utils.login().then(res => {
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
		selectConstellation: null,
		userInfo: null,
		lotDetail: {},
	},
	//初始化
	utils,
	api
})

/**
 * 处理登录功能
 * @param {*} self
 */
function loginHandle(self,len = 0){

	self.getLogin().then(res => {
		console.log(res)
		// throw err = new Error( '用户自定义异常信息' )
		wx.setStorageSync('token', res.token)
		// 登录状态
		Storage.loginStatus = true
		Storage.sessionKey = res.sessionKey
	}).catch(err => {
		len++
		if(len === 3){
			wx.redirectTo({
				url: '/pages/checklogin/checklogin'
			})
		}else{
			loginHandle(self,len)
		}
	})
}
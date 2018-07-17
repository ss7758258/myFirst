const Promise = require('./Promise')
const env = require('../config')
const Storage = require('./storage')
const bus = require('../event')
const login = require('../server/login')

function requstGet(url, data) {
	return requst(url, 'GET', data)
}

function requstPost(url, data) {
	return requst(url, 'POST', data)
}


const DOMAIN = env === 'dev' ? 'https://xingzuoapi.yetingfm.com/xz_api/' : 'https://xingzuoapi-prod.yetingfm.com/xz_api/'
// const DOMAIN = 'http://193.112.130.148:8888/xz_api/'
// 登录失败的锁
Storage.loginLock = false
// 小程序上线需要https，这里使用服务器端脚本转发请求为https
function requst(url, method, data = {}) {
	var notShowLoading = data.notShowLoading
    var loadingStr = data.loaingStr?data.loaingStr:'加载中ing'
	// if (!loadingStr) {
	// 	loadingStr = '加载中...'
	// }
	delete (data.notShowLoading)
	delete (data.loaingStr)
	// wx.showNavigationBarLoading()
	if (!notShowLoading) {
		wx.showLoading({
			title: loadingStr,
		})
	}

	var rewriteUrl = url
	return new Promise((resove, reject) => {
		wx.request({
			url: DOMAIN + rewriteUrl,
			data:
			{
				requestHeader: JSON.stringify({
					token: wx.getStorageSync('token')
					// token : Storage.token
				}),
				requestBody: JSON.stringify(data)
			}
			,
			header: {
				'Accept': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded',
			},

			method: method.toUpperCase(), // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
			success: function (res) {
				if (res.data && ('LOGINERROR' === res.data.status)){
					console.log('用户token过期或者解析失败，登录锁：',Storage.loginLock)
					console.log('用户token过期或者解析失败，进入---------------静默登录')
					if(Storage.loginLock){
						reject()
						wx.hideLoading()
						wx.hideToast()
						return
					}
					Storage.loginLock = true
					console.log('用户token过期或者解析失败，进入---------------静默登录')
					// wx.removeStorageSync('token')
					login.silentLogin(function(){
						Storage.loginLock = false
						console.log('解除登录锁：',loginLock)
					})
					reject()
					wx.hideLoading()
					wx.hideToast()
					// wx.showToast({
					// 	title: '小主，登录信息时效，请重新登录',
					// 	icon: 'none',
					// 	duration: 2000,
					// 	mask : true
					// })
					// setTimeout(() => {
					// 	// 用户token过期
					// 	bus.emit('no-login-app', res , 'app')
					// }, 2000);
					return
				}
				console.log(url)
				if (url == 'statisticsConstellation/x610') {
					console.log(data)
				}
				if (res.data && res.data.responseBody && ('SUCCESS' == res.data.responseBody.status)) {
					// loginLock = false
					if (url == 'selectConstellation/x100' && !data.constellationId) {
						resove(res.data.responseBody)
					}else if(url == 'loginConstellation/loginForMore'){
						resove(res.data.responseBody)
					} else {
						resove(res.data.responseBody.data)
					}

				} else {
					if (res.data && res.data.responseBody && reject) {
						reject(res.data.responseBody.message)
					} else {
						// reject('fail')
						reject('网络不好呦，请小主重新刷新')
					}
				}

			},
			fail: function (msg) {
				if (reject) {
					reject('网络不好呦，请小主重新刷新')
				} else {
					wx.showToast({
						title: '网络不好呦，请小主重新刷新',
						icon: 'none'
					})
				}
			},
			complete: function (res) {
				// wx.hideNavigationBarLoading()
				if (!notShowLoading) {
					wx.hideLoading()
				}

			}

		})
	})
}

function getLogin(data) {//登录
	return requstPost('loginConstellation/x000', data)
}

// 上报用户的加密信息
function loginForMore(data) {
	return requstPost('loginConstellation/loginForMore', data)
}

function getSelectx100(data) {//选择星座
	return requstPost('selectConstellation/x100', data)
}

// function getSelectx101(data) {//选中星座
//   return requstPost('selectConstellation/x101', data)
// }

// function getSelectx102(data) {
//   return requstPost('selectConstellation/x102', data)
// }

function getIndexx200(data) {
	return requstPost('indexConstellation/x200', data)
}

function getMorex300(data) {
	return requstPost('moreConstellation/x300', data)
}

function getDayx400(data) {
	return requstPost('everydayWords/x400', data)
}
function getDayx401(data) {
	return requstPost('everydayWords/x401', data)
}

function getX500(data) {
	return requstPost('everydayQian/x500', data)
}


function getX501(data) {
	return requstPost('everydayQian/x501', data)
}


function getX503(data) {
	return requstPost('everydayQian/x503', data)
}


function getX504(data) {
	return requstPost('everydayQian/x504', data)
}


function getX506(data) {//拆签   签id
	return requstPost('everydayQian/x506', data)
}


function getX507(data) {
	return requstPost('everydayQian/x507', data)
}

function getX510(data) {
	return requstPost('everydayQian/x510', data)
}

function getX511(data) {
	return requstPost('everydayQian/x511', data)
}

function getX610(data) {
	return requstPost('statisticsConstellation/x610', data)
}
function getX600(path, data) {
	return requstPost('statisticsConstellation/x' + path, data)
}

// 获取用户设置
const getUserSetting = (data) => {
	return requstPost('userSetting/get', data)
}

// 获取用户设置
const globalSetting = (data) => {
	return requstPost('globalSetting/get', data)
}

// 保存用户设置
const setUserSetting = (data) => {
	return requstPost('userSetting/save', data)
}
// 获取Banner列表
const getBannerList = (data) => {
	return requstPost('ad/list', data)
}

/*---------------------------------------
	 充值页面
 ---------------------------------------*/
// 获取商品列表
const getGoods = function (data) {
	return requstPost('pay/getgoods', data)
}
// 获取钱包信息
const getBlance = function(data) {
	return requstPost('pay/getbalance', data)
}
// 充值操作
const getRecharge = function(data) {
	return requstPost('pay/recharge', data)
}
// 购买小星星
const buyStar = function(data) {
	return requstPost('pay/buylook', data)
}
// 增加小星星数量
const setStar = (data) => {
	return requstPost('pay/topup', data)
}
// 获取乐摇摇的数据信息
const getLeYaoyao = function(params,data){
	return new Promise((resolve,reject) => {
		wx.request({
			url: 'https://m.leyaoyao.com/customer/task/coins/ma?' + params,
			data: data,
			method: 'GET', 
			success: resolve,
			fail:reject
		})
	})
	
}

module.exports = {
	Promise,
	get: requstGet,
	post: requstPost,
	requst,

	//接口
	getLogin,
	getSelectx100,
	// getSelectx101,
	// getSelectx102,
	getIndexx200,
	getMorex300,
	getDayx400,
	getDayx401,
	getX500,
	getX501,
	getX503,
	getX504,
	getX506,
	getX507,
	getX510,
	getX511,

	getX600,
	getX610,
	getUserSetting,
	setUserSetting,
	getBannerList,
	globalSetting,
	loginForMore,
	getGoods,
	getBlance,
	getRecharge,
	getLeYaoyao,
	buyStar,
	setStar
}
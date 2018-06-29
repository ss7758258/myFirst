const $vm = getApp()
const _GData = $vm.globalData
const api = $vm.api
var mta = require('../../utils/mta_analysis.js')
let loginErrorNum = 0

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		isClicked: false,
		canIUse: wx.canIUse('button.open-type.getUserInfo')
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		mta.Page.init()
		console.log('输出参数：', options)
		const _self = this
		let qId = options.lotId
		let fromwhere = options.from
		if (fromwhere) {
			_self.setData({
				pageFrom: fromwhere,
				qId: qId
			})
		}
		let to = options.to
		if (to) {
			_self.setData({
				toPage: to,
			})
		}
		let and = options.and
		if (and) {
			_self.setData({
				and: and
			})
		}
	},

	bindGetUserInfo: function (e) {
		const _self = this
		const _SData = this.data
		console.log('用户授权信息：',e)
		if (e.detail.userInfo) {
			wx.showLoading({
				title: '登录中...',
			})
			wx.setStorage({
				key: 'userInfo',
				data: e.detail.userInfo,
			})
			this.setData({
				hasAuthorize: true
			})
			_GData.userInfo = e.detail.userInfo
			$vm.getLogin().then(res => {
				wx.setStorageSync('token', res.token)
				$vm.api.getSelectx100({
					constellationId : _GData.selectConstellation.id || 1,
					nickName: e.detail.userInfo.nickName,
					headImage: e.detail.userInfo.avatarUrl,
					notShowLoading: true,
				}).then(res => {
					// throw err = new Error( '用户自定义异常信息' )
					if (_SData.pageFrom == 'shake') {
						wx.redirectTo({
							url: '/pages/lot/shakelot/shake?from=detail',
						})
					} else if (_SData.pageFrom == 'activity' && _SData.and == 'shake') {
						wx.redirectTo({
							url: '/pages/lot/shakelot/shake?from=activity',
						})
					} else if (_SData.pageFrom == 'share' && _SData.and == 'shake') {
						wx.redirectTo({
							url: '/pages/lot/shakelot/shake?from=share',
						})
					} else if (_SData.pageFrom == 'share' && _SData.qId) {
						wx.redirectTo({
							url: '/pages/lot/lotdetail/lotdetail?from=' + _SData.pageFrom + '&lotId=' + _SData.qId,
						})
					} else {
						wx.redirectTo({
							url: '/pages/home/home?from=' + _SData.pageFrom + '&to=' + _SData.toPage,
						})
					}	
				}).catch(err => {
					errorHandle()
				})
			}).catch(err => {
				errorHandle()
			})
			
		}

	},
	//点击重试按钮
	checkLogin: function (e) {
		var that = this
		if (that.data.isClicked) {
			return;
		}
		that.setData({
			isClicked: true
		})

		wx.openSetting({
			success: (res) => {
				if (res.authSetting["scope.userInfo"]) {////如果用户重新同意了授权登录
					wx.getUserInfo({
						success: function (res) {
							_GData.userInfo = res.userInfo;

							$vm.api.getSelectx100({
								constellationId : _GData.selectConstellation.id || 1,
								nickName: res.userInfo.nickName,
								headImage: res.userInfo.avatarUrl,
								notShowLoading: true,
							}).then(res => {

								wx.redirectTo({
									url: '/pages/home/home',
								})
							})

						}, fail: (res) => {
							console.log("拒接登录01");
							wx.redirectTo({
								url: '/pages/checklogin/checklogin'
							})
						}
					})
				} else {
					//用户未授权
					// wx.redirectTo({
					//   url: '/pages/checklogin/checklogin'
					// })
				}
			}
		})


	},
})


/**
 * 错误提示
 */
function errorHandle(){
	wx.getNetworkType({
		success: function(res) {
			console.log('输出当前网络状态：',res)
			if(res.networkType === 'none'){
				wx.hideLoading()
				wx.showModal({
					title: '网络异常',
					content: '非常抱歉，小主您的网络尚未打开',
					showCancel: false,
					confirmText: '稍后再试',
					confirmColor: '#3CC51F',
					success: res => {
						
					}
				});
			}else{
				loginErrorNum++
				if(loginErrorNum === 5){
					loginErrorNum = 0
					wx.hideLoading()
					wx.showModal({
						title: '警告',
						content: '服务器异常，程序员哥哥正在加班维修',
						showCancel: false,
						confirmText: '稍后再试',
						confirmColor: '#3CC51F',
						success: res => {
							
						}
					});
					return false
				}
				wx.showToast({
					title: '小主，为您加载失败了',
					icon: 'none',
					duration: 1000
				})
			}
		}
	})
}
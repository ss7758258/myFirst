// pages/lot/lotdetail/lotdetail.js
const $vm = getApp()
const _GData = $vm.globalData
const {
	canvasTextAutoLine,
	parseLot
} = $vm.utils
var mta = require('../../../utils/mta_analysis.js');
const imgs = require('./imgs.js')
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		isFromShare: false,
		huan: false, //拆签成功
		showCanvas: false,
		imgs: imgs,
		lock: false, //锁
		navConf: {
			title: '拆签',
			state: 'root',
			isRoot: false,
			isIcon: true,
			iconPath: '',
			root: '',
			isTitle: true
		},
		lotDetail: {
			qianOpenSize: 3,
			showChai: true,
			hasChai: false,
			lotNotCompleted: true,
			troops: []
		},
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		mta.Page.init()
		console.log(options)
		wx.hideShareMenu({
			success: function (res) {},
			fail: function (res) {},
			complete: function (res) {},
		})
		if (options.sound) {
			const innerAudioContext = wx.createInnerAudioContext()
			innerAudioContext.autoplay = true
			innerAudioContext.src = '/assets/incoming.m4a'
			innerAudioContext.onPlay(() => {
				console.log('开始播放')
			})
		}

		const _self = this
		let qId = options.lotId
		let pageFrom = options.from
		_self.setData({
			userInfo: _GData.userInfo
		})
		if (!_GData.userInfo) {
			wx.getUserInfo({
				success: function (res) {
					if (res.userInfo) {
						wx.setStorage({
							key: 'userInfo',
							data: res.userInfo,
						})

						_GData.userInfo = res.userInfo
						_self.setData({
							userInfo: _GData.userInfo
						})
						$vm.api.getSelectx100({
							constellationId: _GData.selectConstellation.id,
							nickName: res.userInfo.nickName,
							headImage: res.userInfo.avatarUrl,
							notShowLoading: true,
						})
					}
				},
				fail: (res) => {
					// 查看是否授权
					wx.getSetting({
						success: (res) => {
							if (!res.authSetting['scope.userInfo']) {
								_self.setData({
									hasAuthorize: false
								})
								wx.redirectTo({
									url: '/pages/checklogin/checklogin?from=' + pageFrom + '&lotId=' + qId
								})
							}
						}
					})
				}
			})
		}

		if (pageFrom == 'share' || pageFrom == 'list' || pageFrom == 'form') {
			if (pageFrom == 'share' || pageFrom == 'form') {
				_self.setData({
					isFromShare: true,
					"navConf.root": '/pages/home/home'
				})
			}
			let token = wx.getStorageSync('token')
			if (token) {
				getQian(qId, _self)
				// $vm.api.getX511({ id: qId })
				// 	.then(res => {
				// 		console.log('签的数据===================：', res)
				// 		// res.qianContent = '近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜'
				// 		var lotDetail = parseLot(res)
				// 		_self.setData({
				// 			lotDetail: lotDetail
				// 		})
				// 	})
			} else {
				$vm.getLogin().then(res => {
					wx.setStorageSync('token', res.token)
					getQian(qId, _self)
					// $vm.api.getX511({ id: qId })
					// 	.then(res => {
					// 		console.log('签的数据：', res)
					// 		// res.qianContent = '近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜'
					// 		var lotDetail = parseLot(res)
					// 		_self.setData({
					// 			lotDetail: lotDetail
					// 		})
					// 	})
					// 	.catch(err => {
					// 		wx.showToast({
					// 			icon: 'none',
					// 			title: '服务器开了小差，请稍后再试',
					// 		})
					// 	})
				}).catch(err => {
					wx.getSetting({
						success: function (res) {
							if (!res.authSetting['scope.userInfo']) {

								_self.setData({
									hasAuthorize: false
								})
								wx.redirectTo({
									url: '/pages/checklogin/checklogin?from=' + pageFrom + '&lotId=' + qId
								})
							} else {

							}
						}
					})
				})
			}
		} else {
			_self.setData({
				lotDetail: _GData.lotDetail
			})

		}
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function (res) {
		if (res.from === 'menu') {
			mta.Event.stat("ico_shake_right_share", {})
		}
		const _self = this
		const SData = this.data
		var shareImg = '/assets/images/share_qian.jpg'
		var shareMsg = '快来戳，真的是令人脸红心跳的结果！'
		var sharepath = '/pages/lot/lotdetail/lotdetail?from=share&lotId=' + SData.lotDetail.id
		if (!SData.lotDetail.lotNotCompleted) {
			shareImg = '/assets/images/share_tong.jpg'
			shareMsg = '快来戳，真的是令人脸红心跳的结果！'
			sharepath = '/pages/lot/shakelot/shake?from=share&where=detail'
		}
		return {
			title: shareMsg,
			imageUrl: shareImg,
			path: sharepath,
			success: (res) => {
				// 转发成功
			},
			fail: (res) => {
				// 转发失败
			}
		}
	},
	//拆签或者去
	chai: function (e) {
		let formid = e.detail.formId
		if (this.data.lock && !this.data.lotDetail.hasChai) {
			return false
		}
		this.data.lock = true
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		const _self = this
		const _Sdata = this.data

		if (_Sdata.lotDetail.hasChai) {
			wx.navigateTo({
				url: '/pages/lot/shakelot/shake?formid=' + formid
			})
			return
		}
		_self.setData({
			showHaoren: true,
		})
		$vm.api.getX506({
				id: _Sdata.lotDetail.id,
				notShowLoading: true
			})
			.then(res => {
				var lotDetail = parseLot(res)
				if (res.status == 1) {
					mta.Event.stat("ico_chai_completed", {})
					_self.setData({
						huan: true,
					})
					setTimeout(function () {
						_self.setData({
							lotDetail: lotDetail,
						})
					}, 2000)
				} else {
					_self.setData({
						lotDetail: lotDetail
					})
				}

				setTimeout(function () {
					_self.setData({
						showHaoren: false,
					})
					_self.data.lock = true;
				}, 2800)
			})
			.catch(err => {

			})
	},
	onclickShareFriend: function (e) {
		mta.Event.stat("ico_detail_share", {})
		let formid = e.detail.formId

		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
	},
	//保存图片
	onclickShareCircle: function (e) {
		mta.Event.stat("ico_detail_save", {})
		let formid = e.detail.formId

		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		const _self = this
		const _SData = _self.data
		wx.showLoading({
			title: '图片生成中...',
			mask: true
		})
		_self.setData({
			showCanvas: true,
		})

		const ctx = wx.createCanvasContext('shareCanvas')
		ctx.drawImage('/assets/images/share1Bg.png', 0, 0, 750, 750)
		// 签类型
		ctx.setTextAlign('center') // 文字居中
		ctx.setFillStyle('#ffffff') // 文字颜色：白色
		ctx.setFontSize(40) // 文字字号：22px
		ctx.fillText(_SData.lotDetail.qianName, 750 / 2, 77 * 2 + 40)


		var s = _SData.lotDetail.qianContent.split('\n')

		if (s.length == 1) {
			ctx.setTextAlign('left')
			ctx.setFontSize(29)
			canvasTextAutoLine(ctx, _SData.lotDetail.qianContent, 64, 125 * 2 + 32, 40, 64)
		} else {
			ctx.setTextAlign('center')
			ctx.setFontSize(29)
			for (var i = 0; i < s.length; i++) {
				ctx.fillText(s[i], 375, 125 * 2 + (32 + 10) * i, 310 * 2)
			}
		}

		ctx.setTextAlign('left')
		ctx.setFontSize(28)
		const metrics1 = ctx.measureText(_SData.lotDetail.ownerNickName).width / 2
		ctx.fillText(_SData.lotDetail.ownerNickName, 750 - metrics1 - 64 * 2 - 32, 205 * 2 + 28, 310 * 2)
		let timer = new Date();
		let newDate = timer.getFullYear() + '-' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '-' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate());
		// 计算文本长度
		const metrics2 = ctx.measureText(newDate).width / 2

		ctx.fillText(newDate, 750 - metrics2 - 64 * 2 - 32, 225 * 2 + 28, 310 * 2)

		const qrImgSize = 110
		ctx.drawImage('/assets/images/qrcodeonelot.png', 297 * 2, 306 * 2, qrImgSize, qrImgSize)
		ctx.stroke()
		ctx.draw()
		setTimeout(function () {
			wx.canvasToTempFilePath({
				canvasId: 'shareCanvas',
				success: function (res) {
					wx.saveImageToPhotosAlbum({
						filePath: res.tempFilePath,
						success: (res) => {
							wx.hideLoading()
							wx.showModal({
								title: '保存成功',
								content: '图片已经保存到相册，可以分享到朋友圈了',
								showCancel: false
							})
						},
						fail: (res) => {},
						complete: (res) => {
							// wx.hideLoading()
							_self.setData({
								showCanvas: false
							})
						}
					})
				},
				fail: (res) => {
					wx.hideLoading()
					wx.showToast({
						title: '保存失败',
						icon: 'none',
					})
					_self.setData({
						showCanvas: false
					})
				}
			})
		}, 1000)
	},
	//分享的返回主页
	onclickHome: function (e) {
		mta.Event.stat("ico_shake_home", {})
		let formid = e.detail.formId
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		wx.reLaunch({
			url: '/pages/home/home',
		})
	}
})


/**
 * 重新加载数据
 * @param {*} qId
 */
function getQian(qId, _self) {
	wx.getNetworkType({
		success: (res) => {
			if (res.networkType === 'none') {
				wx.showLoading({
					title: '加载中...',
					mask: true
				})
				setTimeout(function () {
					$vm.api.getX511({
							id: qId
						})
						.then(res => {
							let lotDetail = parseLot(res)
							_self.setData({
								lotDetail: lotDetail
							})
						}).catch(err => {
							_self.setData({
								isError: true
							})
							wx.showModal({
								title: '网络错误',
								content: '小主您的网络有点小问题哦,请重新尝试',
								confirmText: '重新尝试',
								showCancel: false,
								success() {
									getQian(qId, _self);
								}
							})
						})
				}, 3000)
			} else {
				$vm.api.getX511({
						id: qId
					})
					.then(res => {
						let lotDetail = parseLot(res)
						_self.setData({
							lotDetail: lotDetail
						})
					}).catch(err => {
						_self.setData({
							isError: true
						})
						wx.showModal({
							title: '网络错误',
							content: '小主您的网络有点小问题哦,请重新尝试',
							confirmText: '重新尝试',
							showCancel: false,
							success() {
								getQian(qId, _self);
							}
						})
					})
			}
		}
	})
}
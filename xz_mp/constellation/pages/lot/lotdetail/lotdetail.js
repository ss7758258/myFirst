const $vm = getApp()
const _GData = $vm.globalData
const {
	canvasTextAutoLine,
	parseLot
} = $vm.utils
const mta = require('../../../utils/mta_analysis.js')
const imgs = require('./imgs.js')
Page({

	// 页面的初始数据
	data: {
		isFromShare: false, //是否是从分享进入
		isOpend: false, //拆签成功
		showCanvas: false,
		imgs: imgs, //图片
		lock: false, //锁
		hasAuthorize: false, //是否授权，默认未授权
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
			isOpen: false, //是否拆开
			lotNotCompleted: true,
			troops: []
		},
	},

	// 监听页面加载
	onLoad(options) {
		mta.Page.init()
		wx.hideShareMenu({})
		if (options.sound) {
			const innerAudioContext = wx.createInnerAudioContext()
			innerAudioContext.autoplay = true
			innerAudioContext.src = '/assets/incoming.m4a'
			innerAudioContext.onPlay(() => {
				console.log('开始播放')
			})
		}

		const _self = this
		const _SData = this.data
		let lotId = options.lotId // “签”的ID
		let pageFrom = options.from


		this.setData({
			userInfo: _GData.userInfo
		})

		if (!_GData.userInfo) {
			wx.getUserInfo({
				success: (res) => {
					const userInfo = res.userInfo

					if (userInfo) {

						_GData.userInfo = res.userInfo

						// 存入用户信息
						wx.setStorage({
							key: 'userInfo',
							data: userInfo,
						})

						_self.setData({
							userInfo: _GData.userInfo
						})

						$vm.api.getSelectx100({
							constellationId: _GData.selectConstellation.id,
							nickName: userInfo.nickName,
							headImage: userInfo.avatarUrl,
							notShowLoading: true
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
									url: '/pages/checklogin/checklogin?from=' + pageFrom + '&lotId=' + lotId
								})
							}
						}
					})
				}
			})
		}

		// 判断来访页面
		if (pageFrom === 'share' || pageFrom === 'list' || pageFrom === 'form') {

			const token = wx.getStorageSync('token') //获取token值

			_self.setData({
				isFromShare: true,
				"navConf.root": '/pages/home/home'
			})
			if (token) {
				getLotContent(lotId, _self)
				// $vm.api.getX511({ id: lotId })
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
					
					console.log("res3============",res)
					wx.setStorageSync('token', res.token)
					getLotContent(lotId, _self)
					$vm.api.getX511({ id: lotId })
						.then(res => {
							// res.qianContent = '近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜'
							var lotDetail = parseLot(res)
							_self.setData({
								lotDetail: lotDetail
							})
						})
						.catch(err => {
							wx.showToast({
								icon: 'none',
								title: '服务器开了小差，请稍后再试',
							})
						})
				}).catch(err => {
					wx.getSetting({
						success: (res) => {

							// 如果没有授权
							if (!res.authSetting['scope.userInfo']) {

								_self.setData({
									hasAuthorize: false
								})
								wx.redirectTo({
									url: '/pages/checklogin/checklogin?from=' + pageFrom + '&lotId=' + lotId
								})
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
	onShareAppMessage(res) {

		res.from === 'menu' && mta.Event.stat("ico_shake_right_share", {})

		const {
			lotNotCompleted,
			id
		} = this.data.lotDetail //是否已经拆开
		const title = '快来戳，真的是令人脸红心跳的结果！' //分享弹框的信息
		const imageUrl = lotNotCompleted ? `/assets/images/share_qian.jpg` : `/assets/images/share_tong.jpg` //分享弹框的图片路由
		const path = lotNotCompleted ? `/pages/lot/lotdetail/lotdetail?from=share&lotId=${id}` : '/pages/lot/shakelot/shake?from=share&where=detail' //分享弹框的跳转路由

		return {
			title,
			imageUrl,
			path
		}
	},

	//点击拆签
	handleOpenLotClick(e) {
		let formid = e.detail.formId
		const _self = this
		const _Sdata = this.data
		if (_Sdata.lock && !_Sdata.lotDetail.isOpen) return

		this.setData({
			lock: true
		})

		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})

		if (_Sdata.lotDetail.isOpen) {
			wx.navigateTo({
				url: '/pages/lot/shakelot/shake?formid=' + formid
			})
			return
		}
		this.setData({
			showHaoren: true,
		})

		// 获取”签“的内容
		$vm.api.getX506({
				id: _Sdata.lotDetail.id,
				notShowLoading: true
			})
			.then(res => {
				let lotDetail = parseLot(res)

				if (res.status === 1) {
					mta.Event.stat("ico_chai_completed", {})
					_self.setData({
						isOpend: true,
					})
					setTimeout(() => {
						_self.setData({
							lotDetail
						})
					}, 200)
				} else {
					_self.setData({
						lotDetail
					})
				}

				// 对方拆签时气泡动画延迟
				setTimeout(() => {
					_self.setData({
						showHaoren: false,
						lock: true
					})
				}, 2800)
			})
	},

	// 邀请好友帮忙
	handleInviteClick(e) {
		mta.Event.stat("ico_detail_share", {})
		let formid = e.detail.formId

		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
	},

	//保存图片
	handleSaveImgClick(e) {
		mta.Event.stat("ico_detail_save", {})
		let formid = e.detail.formId
		const _self = this
		const _SData = _self.data

		// 签名，签内容
		const {
			qianName,
			qianContent,
			ownerNickName
		} = _SData.lotDetail

		let qianContentArray = qianContent.split('\n')

		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})

		wx.showLoading({
			title: "图片生成中...",
			mask: true
		})

		this.setData({
			showCanvas: true
		})


		// 画布初始化
		const ctx = wx.createCanvasContext('shareCanvas')

		ctx.drawImage('/assets/images/share1Bg.png', 0, 0, 750, 750)
		ctx.setTextAlign('center') // 文字居中  
		ctx.setFillStyle('#ffffff') // 文字颜色：白色
		ctx.setFontSize(40) // 文字字号：22px
		ctx.fillText(qianName, 750 / 2, 77 * 2 + 40)

		// 判断返回字体段落长度
		if (qianContentArray.length === 1) {
			ctx.setTextAlign('left')
			ctx.setFontSize(29)
			canvasTextAutoLine(ctx, qianContent, 64, 125 * 2 + 32, 40, 64)
		} else {
			ctx.setTextAlign('center')
			ctx.setFontSize(29)
			for (var i = 0; i < qianContentArray.length; i++) {
				ctx.fillText(qianContentArray[i], 375, 125 * 2 + (32 + 10) * i, 310 * 2)
			}
		}

		ctx.setTextAlign('left')
		ctx.setFontSize(28)
		const metrics1 = ctx.measureText(ownerNickName).width / 2
		ctx.fillText(ownerNickName, 750 - metrics1 - 64 * 2 - 32, 205 * 2 + 28, 310 * 2)
		let timer = new Date()
		let newDate = timer.getFullYear() + '-' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '-' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate())

		// 计算文本长度
		const metrics2 = ctx.measureText(newDate).width / 2
		const qrImgSize = 110 //二维码尺寸

		ctx.fillText(newDate, 750 - metrics2 - 64 * 2 - 32, 225 * 2 + 28, 310 * 2)
		ctx.drawImage('/assets/images/qrcodeonelot.png', 297 * 2, 306 * 2, qrImgSize, qrImgSize)
		ctx.stroke()
		ctx.draw()

		setTimeout(() => {
			wx.canvasToTempFilePath({
				canvasId: 'shareCanvas',
				success: (res) => {
					console.log("res.tempFilePath", res.tempFilePath)

					// 生成缩略图
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
						fail: (res) => {
							console.log(res)
						},
						complete: (res) => {
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
	}

	//分享的返回主页
	// onclickHome: function (e) {
	// 	mta.Event.stat("ico_shake_home", {})
	// 	let formid = e.detail.formId
	// 	$vm.api.getX610({
	// 		formid,
	// 		notShowLoading: true,
	// 	})
	// 	wx.reLaunch({
	// 		url: '/pages/home/home',
	// 	})
	// }
})

// 根据当前网络环境重新加载“签”内容
function getLotContent(lotId, obj) {

	const reload = () => {
		$vm.api.getX511({
				id: lotId
			})
			.then(res => {
				// 设置“签”的内容
				obj.setData({
					lotDetail: parseLot(res)
				})
			}).catch(err => {
				obj.setData({
					isError: true
				})
				wx.showModal({
					title: '网络错误',
					content: '小主您的网络有点小问题哦,请重新尝试',
					confirmText: '重新尝试',
					showCancel: false,
					success() {
						getLotContent(lotId, obj)
					}
				})
			})
	}

	// 判断当前信号环境  
	wx.getNetworkType({
		success: (res) => {
			const { networkType } = res // 当前网络环境 2g/3g/4g/wifi/none
			if (networkType === 'none') {
				wx.showLoading({
					title: '加载中...',
					mask: true
				})
				setTimeout(() => {
					reload()
				}, 1000)
			}
		}
	})
}
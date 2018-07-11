// pages/lot/lotdetail/lotdetail.js
const $vm = getApp()
const API = require('../../../utils/api')
let _GData = $vm.globalData
const { canvasTextAutoLine, parseLot } = $vm.utils
const mta = require('../../../utils/mta_analysis.js');
const bus = require('../../../event')
const Storage = require('../../../utils/storage')
// 星星数量
let starNum = Storage.starPrice

const config = {
	data: {
		isFromShare: false,
		huan: false,//拆签成功
		showCanvas: false,
		// 帮好友拆签的锁
		lock: false,
		// 总拆签人数
		disNum: 3,
		// 头像的定位样式
		flyStyle: '',
		// 头像飞入哪里
		fly: '',
		// 是否已经拆签完成
		disLotSuccess: false,
		// ios的关闭打开问题
		iosOpen : false,
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
			// 问候语句
			helloText: '你好！',
			ownerHeadImage: '/assets/images/default_head.png',
			troops: [{ photo: '/assets/images/default_head.png' }],
			qianOpenSize: 3,
			showChai: true,
			// 是否已经拆了
			hasChai: false,
			// 是否是自己的签
			isOther: true,
			// 描述语句
			lotTitleHint: '下面是你的每日一签，快找好友帮你拆签吧~',
			lotNotCompleted: true
		},
        // 是否是长屏机
        longScreen : Storage.LongScreen
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad(options) {

		if (options.lotId) {
			wx.showLoading({
				title: '加载中...',
				mask: true,
			})
		}
		// 重置状态
		Storage.lotLogin = false
		// 获取星星
		starNum = Storage.starPrice
        // Storage.openIos = 1
		// ios上关闭打开
		if(Storage.sys === 'ios' && Storage.openIos){
            this.setData({
                iosOpen : true
            })
        }else if(Storage.sys === 'ios' && !Storage.openIos){
            this.setData({
                iosOpen : false
            })
        }else{
            this.setData({
                iosOpen : true
            })
		}
		// 缓存对象
		let self = this
		// 缓存签详情的来源参数
		Storage.lotOpts = options
		console.log('签页面参数：',options)
		mta.Page.init()

		// 隐藏分享
		wx.hideShareMenu()

		let handle = () => {
			console.log('------------------登录标识-----------------------')
			// 签详情页登录状态
			Storage.lotLogin = true
			self.setData({
				userInfo: Storage.userInfo
			})
			// 获取签的数据
			getTokenQian(options.from, self, options.lotId, _GData)
		}

		// 监听事件
		bus.on('login-success', handle, 'login-com')
		bus.on('login-success', handle, 'lotdetail-app')

		// 如果已经存在用户信息触发登录标识
		if(Storage.userInfo){
			this.setData({
				// 是否是长屏机
				longScreen : Storage.LongScreen
			})
			// 已经触发过登录不在触发
			if(Storage.lotLogin){
				return
			}
			bus.emit('login-success', {}, 'lotdetail-app')
		}
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage(res) {
		if (res.from === 'menu') {
			mta.Event.stat("ico_shake_right_share", {})
		}
		
		const SData = this.data
		var shareImg = '/assets/images/share_lot.jpg'
		var shareMsg = '从未想过，世上竟有如此神奇的操作'
		var sharepath = '/pages/lot/lotdetail/lotdetail?from=share&lotId=' + SData.lotDetail.id

		console.log("onShareAppMessage-qId===" + SData.lotDetail.id)
		return {
			title: shareMsg,
			imageUrl: shareImg,
			path: sharepath,
			success: function (res) {
				console.log('转发成功的数据：',res)
			},
			fail: function (res) {
				// 转发失败
				console.log('转发失败：',res)
			}
		}
	},
	/**
	 * 获取formId进行上报
	 * @param {*} e
	 */
	getFormId(e) {
		console.log('获取到的formId', e)
		let formid = e.detail.formId
		$vm.api.getX610({ notShowLoading: true, formid: formid })
	},
	//保存图片
	saveShare(e) {
		mta.Event.stat("ico_detail_save", {})

		wx.showLoading({
			title: '图片生成中...',
			mask: true
		})

		let self = this
		let lotdetail = this.data.lotDetail
		console.log(lotdetail)
		console.log('头像地址：',Storage.userInfo.avatarUrl)
		wx.getImageInfo({ //将头像转路径
			src: Storage.userInfo.avatarUrl, //图片的路径，可以是相对路径，临时文件路径，存储文件路径，网络图片路径,
			success: res => {
				console.log('头像本地路径', res.path)
				let face = res.path

				// 画图
				const ctx = wx.createCanvasContext('openSign')

				ctx.drawImage('/assets/img/background.png', 0, 0, 375, 535) //背景图
				ctx.drawImage('/assets/img/card.png', 0, 75, 375, 275) //拆签数据图
				ctx.drawImage('/assets/images/qrcodelot.png', 150, 360, 75, 75) //小哥星座二维码图
				ctx.drawImage('/assets/img/text.png', 97, 445, 184, 45) //小哥星座文字图

				// 签类型
				ctx.save()
				ctx.setFillStyle('#333333')  // 文字颜色：白色          
				ctx.font = "normal bold 20px ''"        //文字大小为20px并加粗
				ctx.fillText(lotdetail.qianName, 30, 130)
				ctx.restore()

				//用户名称
				// const mea_username = ctx.measureText(lotdetail.ownerNickName).width / 2
				ctx.setFillStyle('#333333')
				ctx.setFontSize(14)
				ctx.setTextAlign('right')
				ctx.fillText(lotdetail.ownerNickName, 345, 131)

				// 签内容
				var s = lotdetail.qianContent.split('\n')
				console.log(s)
				ctx.setTextAlign('center')
				ctx.setFontSize(16)
				for (var i = 0; i < s.length; i++) {
					ctx.fillText(s[i], 187.5, 168 + ((4-i)*45/2))
				}
				
				// 时间
				ctx.setTextAlign('center')
				ctx.setFontSize(12)
				let timer = new Date();
				let newDate = '一 ' + timer.getFullYear() + '.' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '.' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate()) + ' 一';
				// console.log('输出日期：', newDate)
				// 计算文本长度
				const mea_date = ctx.measureText(newDate).width / 2
				ctx.fillText(newDate, 187.5, 290)

				ctx.setShadow(0, 3, 6, 'rgba(0,0,0,.2)')
				ctx.arc(187.5, 85, 25, 0, 2 * Math.PI)
				ctx.fill()

				// 头像
				ctx.save()
				ctx.setShadow(0, 3, 6, '#000000')
				ctx.arc(187.5, 85, 25, 0, 2 * Math.PI)
				ctx.clip()
				console.log('头像路径', face)
				ctx.drawImage(face, 162.5, 60, 50, 50)
				ctx.restore()

				ctx.draw()
				console.log('画图完成===================')

				setTimeout(function () {
					wx.canvasToTempFilePath({
						canvasId: 'openSign',
						success: function (res) {
							console.log(res.tempFilePath)
							wx.saveImageToPhotosAlbum({
								filePath: res.tempFilePath,
								success(res) {
									wx.hideLoading()
									wx.showModal({
										title: '保存成功',
										content: '图片已经保存到相册，可以分享到朋友圈了',
										showCancel: false,
									})
									// wx.hideLoading()
								},
								fail() {
									wx.showToast({
										title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
										icon: 'none',
										duration: 3000
									})
								}
							})
						},
						fail: function (res) {
							console.log(res)
							wx.showToast({
								title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
								icon: 'none',
								duration: 3000
							})
							// wx.hideLoading()
						},
					})
				}, 1000)
			}
		})

	},

	/**
	 * 前往支付页面
	 */
	goPay() {
		let self = this
		console.log('前往支付')
		wx.showModal({
			title: '\t\n',
			content: '快速查看需要消耗' + starNum + '颗小星星确定\t\n快速查看？',
			showCancel: true,
			cancelColor: '#999999',
			cancelText: '我在想想',
			confirmText: '确定',
			confirmColor: '#9262FB',
			success: function (res) {
				console.log(res)
				if (res.confirm) {
					API.getBlance({ notShowLoading: true }).then(data => {
						if (!data) {
							return
						}
						console.log('钱包星星数量：', data)
						// data.balance = 1
						// 当小星星不足时进行提示
						if (data.balance < starNum) {
							wx.showModal({
								title: '\t\n',
								content: '账号余额不足，请先去买些小星星吧！',
								showCancel: true,
								cancelColor: '#999999',
								cancelText: '取消',
								confirmText: '去购买',
								confirmColor: '#9262FB',
								success: function (res) {
									if (res.confirm) {
										// 跳转到小星星页面
										wx.navigateTo({
											url: '/pages/myAccount/myAccount'
										})
									}
								}
							})
						} else {
							API.buyStar({
								id: self.data.lotDetail.id
							}).then(res => {
								console.log('购买结果：', res)
								if (res.retcode === 0) {
									console.log('购买成功')
									// 重新加载一遍数据
									getQian(Storage.detailLotId, self)
								}
							})
						}
					})
				}
			}
		})
	},
	/**
	 * 帮助好友拆签，或者查看自己的一签
	 */
	openDis() {
		// 如果已经拆过了进入每日一签
		if (this.data.lotDetail.hasChai) {
			wx.navigateTo({
				url: '/pages/lot/shake/shake?fromSource=lotdetail'
			})
			return
		}
		// 加载中上锁
		this.data.lock = true

		// 确定动画类型
		let animatype = this.data.disNum - this.data.lotDetail.troops.length
		let self = this
		// 创建选择器
		let query = wx.createSelectorQuery()
		query.select('.heart_big').boundingClientRect()
		query.exec(function (res) {
			console.log('输出节点信息：', res)
			self.setData({
				flyStyle: `top:${res[0].top}px;left:${res[0].left}px;transform: scale(1);`
			})
			let query = wx.createSelectorQuery()
			query.select('.head_pto_0').boundingClientRect()
			query.exec(function (v) {
				console.log('输出坐标：', v)
				$vm.api.getX506({ id: self.data.lotDetail.id, notShowLoading: true })
					.then(res => {
						console.log('未知数据：', res)
						// 拆签失败
						if (!res) {
							wx.showModal({
								title: '提示',
								content: '小主，拆签失败了',
								confirmText: '重新尝试',
								showCancel: false,
								success() { }
							})
							self.setData({
								flyStyle: `transform: scale(0);`
							})
							return
						}
						self.setData({
							flyStyle: `top:${v[0].top}px;left:${v[0].left}px;transform: scale(1);`
						})
						// 解析一遍数据
						let lotDetail = parseLot(res)

						// 解锁操作
						self.data.lock = false

						// 确认是否已经解签
						if (res.status == 1) {
							mta.Event.stat("ico_chai_completed", {})
						}

						setTimeout(() => {
							self.setData({
								lotDetail: lotDetail
							})

							// 如果为购买的签
							if (lotDetail.isOpen) {
								// 拆签动画
								self.setData({
									disLotSuccess: true
								})
							}
						}, 500)
					}).catch(err => {
						wx.hideLoading()
						wx.showModal({
							title: '提示',
							content: '小主，拆签失败了',
							confirmText: '重新尝试',
							showCancel: false,
							success() { }
						})
						self.setData({
							flyStyle: `transform: scale(0);`
						})
						return
					})
			})

		})

	}
}

Page(config)


/**
 * 重新加载数据
 * @param {*} qId
 */
function getQian(qId, self, GData) {
	console.log('加载签详情')
	$vm.api.getX511({ id: qId, notShowLoading: true })
	.then(res => {
		console.log('签的数据===================：', res)

		let lotDetail = parseLot(res)

		setTimeout(() => {
			self.setData({
				lotDetail: lotDetail
			})
			
			// 如果为购买的签
			if (lotDetail.isOpen || !lotDetail.lotNotCompleted) {
				// 拆签动画
				self.setData({
					disLotSuccess: true
				})
			}

			wx.hideLoading()

		}, 500)

	}).catch(err => {
		wx.hideLoading()
		wx.showModal({
			title: '网络错误',
			content: '小主您的网络有点小问题哦,请重新尝试',
			confirmText: '重新尝试',
			showCancel: false,
			success() { }
		})
		console.log('进入错误状态')
	})
}

/**
 * 获取用户信息后进行登录拉取签的数据
 * @param {*} pageFrom
 * @param {*} self
 * @param {*} qId
 * @param {*} _GData
 */
function getTokenQian(pageFrom, self, qId, _GData) {
	
	// 保存当前的签 
	Storage.detailLotId = qId
	console.log('进入token页')
	if (pageFrom == 'share' || pageFrom == 'list' || pageFrom == 'form') {
		if (pageFrom == 'share' || pageFrom == 'form') {
			self.setData({
				isFromShare: true,
				"navConf.root": '/pages/home/home'
			})
		}
		getQian(qId, self)
	} else if (Storage.lotOpts.fromSource && Storage.lotOpts.fromSource !== '') {
		setTimeout(() => {
			self.setData({
				lotDetail: Storage.lotDetail
			})

			// 如果为购买的签
			if (Storage.lotDetail.isOpen || !Storage.lotDetail.lotNotCompleted) {
				// 拆签动画
				self.setData({
					disLotSuccess: true
				})
			}
			wx.hideLoading()

		}, 500)
	}
}

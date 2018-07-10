// pages/lot/lotdetail/lotdetail.js
const $vm = getApp()
const API = require('../../../utils/api')
let _GData = $vm.globalData
const { canvasTextAutoLine, parseLot } = $vm.utils
const mta = require('../../../utils/mta_analysis.js');
const bus = require('../../../event')
const Storage = require('../../../utils/storage')
// 星星数量
const starNum = 9

const config = {
	data : {
		isFromShare: false,
		huan: false,//拆签成功
		showCanvas: false,
		// 帮好友拆签的锁
		lock: false, 
		// 总拆签人数
		disNum : 3,
		// 头像的定位样式
		flyStyle : '',
		// 头像飞入哪里
		fly : '',
		// 是否已经拆签完成
		disLotSuccess : false,
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
			helloText : '你好！',
			ownerHeadImage : '/assets/images/default_head.png',
			troops : [{photo : '/assets/images/default_head.png'}],
			qianOpenSize: 3,
			showChai: true,
			// 是否已经拆了
			hasChai: false,
			// 是否是自己的签
			isOther : true,
			// 描述语句
			lotTitleHint : '下面是你的每日一签，快找好友帮你拆签吧~',
			lotNotCompleted : true
		}
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad (options) {
		// 缓存对象
		let self = this
		// 缓存签详情的来源参数
		Storage.lotOpts = options

		if(options.lotId){
			wx.showLoading({
				title: '加载中...',
				mask: true,
			})
		}
		mta.Page.init()
		console.log(options)
		
		// 隐藏分享
		wx.hideShareMenu()

		if (options.sound) {
			const innerAudioContext = wx.createInnerAudioContext()
			innerAudioContext.autoplay = true
			innerAudioContext.src = '/assets/incoming.m4a'
			innerAudioContext.onPlay(() => {
				console.log('开始播放')
			})
		}
		let handle =  () => {
            console.log('------------------登录标识-----------------------')
            self.setData({
                userInfo: Storage.userInfo
            })

			// 获取签的数据
			getTokenQian(options.from,self,options.lotId,_GData)
		}
		
		// 监听事件
		bus.on('login-success',handle , 'login-com')
		bus.on('login-success',handle , 'lotdetail-app')

        // 来源
        if(options.fromSource){
            switch (options.fromSource) {
				case 'shake':
				case 'lotlist':
                    // 手动触发登录状态 
                    bus.emit('login-success', {}, 'lotdetail-app')
                    break;
                default:
                    break;
            }
        }
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage(res) {
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
		console.log("shareImg-qId===" + shareImg)
		console.log("onShareAppMessage-qId===" + SData.lotDetail.id)
		return {
			title: shareMsg,
			imageUrl: shareImg,
			path: sharepath,
			success: function (res) {
				// 转发成功
			},
			fail: function (res) {
				// 转发失败
			}
		}
	},
	/**
	 * 获取formId进行上报
	 * @param {*} e
	 */
	getFormId(e) {
		console.log('获取到的formId',e)
		let formid = e.detail.formId
		$vm.api.getX610({ notShowLoading: true, formid: formid })
	},
	//保存图片
	saveShare (e) {
		mta.Event.stat("ico_detail_save", {})
		let formid = e.detail.formId

		$vm.api.getX610({ notShowLoading: true, formid: formid })
		const _self = this
		const _SData = _self.data
		wx.showLoading({
			title: '图片生成中...',
			mask: true
		})
		_self.setData({
			showCanvas: true,
		})

		console.log(e)
		const ctx = wx.createCanvasContext('shareCanvas')
		ctx.drawImage('/assets/images/share1Bg.png', 0, 0, 750, 750)
		// 签类型
		ctx.setTextAlign('center')    // 文字居中
		ctx.setFillStyle('#ffffff')  // 文字颜色：白色
		ctx.setFontSize(40)         // 文字字号：22px
		ctx.fillText(_SData.lotDetail.qianName, 750 / 2, 77 * 2 + 40)


		var s = _SData.lotDetail.qianContent.split('\n')

		console.log(s)
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
		console.log('输出日期：', newDate)
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
					console.log(res.tempFilePath)
					wx.saveImageToPhotosAlbum({
						filePath: res.tempFilePath,
						success(res) {
							wx.hideLoading()
							wx.showModal({
								title: '保存成功',
								content: '图片已经保存到相册，可以分享到朋友圈了',
								showCancel: false
							})

						}, fail(res) {
							wx.showToast({
								title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
								icon: 'none',
								duration: 3000
							})
							_self.setData({
								showCanvas: false,
							})
						}, complete(res) {
							// wx.hideLoading()
							_self.setData({
								showCanvas: false
							})
						}
					})
				},
				fail: function (res) {
					console.log(res)
					wx.hideLoading()
					wx.showToast({
						title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
						icon: 'none',
						duration: 3000
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
		$vm.api.getX610({ notShowLoading: true, formid: formid })
		wx.reLaunch({
			url: '/pages/home/home',
		})
	},
	
	/**
	 * 前往支付页面
	 */
	goPay (){
		let self = this
		console.log('前往支付')
		wx.showModal({
			title: '\t\n',
			content: '快速查看需要消耗' + starNum + '颗小星星确定\t\n快速查看？',
			showCancel: true,
			cancelColor : '#999999',
			cancelText : '我在想想',
			confirmText: '确定',
			confirmColor : '#9262FB',
			success: function (res) {
				console.log(res)
				if(res.confirm){
					API.getBlance({notShowLoading:true}).then(data => {
						if(!data){
							return
						}
						console.log('钱包星星数量：',data)
						// data.balance = 1
						// 当小星星不足时进行提示
						if(data.balance < starNum){
							wx.showModal({
								title: '\t\n',
								content: '账号余额不足，请先去买些小星星吧！',
								showCancel: true,
								cancelColor : '#999999',
								cancelText : '取消',
								confirmText: '去购买',
								confirmColor : '#9262FB',
								success: function (res) {
									if(res.confirm){
										// 跳转到小星星页面
										wx.navigateTo({
											url: '/pages/myAccount/myAccount'
										})
									}
								}
							})
						}else{
							API.buyStar({
								id : self.data.lotDetail.id
							}).then(res => {
								console.log('购买结果：',res)
								if(res.retcode === 0){
									console.log('购买成功')
									// 重新加载一遍数据
									getQian(Storage.detailLotId,self)
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
	openDis(){
		// 如果已经拆过了进入每日一签
		if(this.data.lotDetail.hasChai){
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
		query.exec(function(res){
			console.log('输出节点信息：',res)
			self.setData({
				flyStyle : `top:${res[0].top}px;left:${res[0].left}px;transform: scale(1);`
			})
			let query = wx.createSelectorQuery()
			query.select('.head_pto_0').boundingClientRect()
			query.exec(function(v){
				console.log('输出坐标：',v)
				$vm.api.getX506({ id: self.data.lotDetail.id, notShowLoading: true })
				.then(res => {
					console.log('未知数据：',res)
					// 拆签失败
					if(!res){
						wx.showModal({
							title: '提示',
							content: '小主，拆签失败了',
							confirmText : '重新尝试',
							showCancel: false,
							success (){ }
						})
						self.setData({
							flyStyle : `transform: scale(0);`
						})
						return
					}
					self.setData({
						flyStyle : `top:${v[0].top}px;left:${v[0].left}px;transform: scale(1);`
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
						if(lotDetail.isOpen){
							// 拆签动画
							self.setData({
								disLotSuccess: true
							})
						}
					},500)
				}).catch(err => {
					wx.showModal({
						title: '提示',
						content: '小主，拆签失败了',
						confirmText : '重新尝试',
						showCancel: false,
						success (){ }
					})
					self.setData({
						flyStyle : `transform: scale(0);`
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
function getQian(qId,self,GData){
	wx.getNetworkType({
		success: function(res) {
			console.log('输出当前网络状态：',res)
			if(res.networkType === 'none'){
				wx.showLoading({
					title : '加载中...',
					mask : true
				})
				setTimeout(function(){
					$vm.api.getX511({ id: qId })
					.then(res => {
						console.log('签的数据===================：', res)
						let lotDetail = parseLot(res)
						// 默认用户没有拆签
						// lotDetail.hasChai = false
						
						self.setData({
							lotDetail: lotDetail
						})
						console.log(lotDetail.isOpen)
						// lotDetail.isOpen = false
						// 如果为购买的签
						if(lotDetail.isOpen || !lotDetail.lotNotCompleted){
							// 拆签动画
							self.setData({
								disLotSuccess: true
							})
						}
						setTimeout(() => {
							wx.hideLoading()
						},500)
					}).catch(err =>{
						console.log('进入错误状态')
						wx.showModal({
							title: '网络错误',
							content: '小主您的网络有点小问题哦,请重新尝试',
							confirmText : '重新尝试',
							showCancel: false,
							success (){ }
						})
					})
				},3000)
			}else{
				$vm.api.getX511({ id: qId })
				.then(res => {
					console.log('签的数据===================：', res)
					let lotDetail = parseLot(res)
					// 默认用户没有拆签
					// lotDetail.hasChai = false
					
					self.setData({
						lotDetail: lotDetail
					})
					console.log(lotDetail.isOpen)
					// lotDetail.isOpen = false
					// 如果为购买的签
					if(lotDetail.isOpen || !lotDetail.lotNotCompleted){
						// 拆签动画
						self.setData({
							disLotSuccess: true
						})
					}
					setTimeout(() => {
						wx.hideLoading()
					},500)
				}).catch(err =>{
					wx.showModal({
						title: '网络错误',
						content: '小主您的网络有点小问题哦,请重新尝试',
						confirmText : '重新尝试',
						showCancel: false,
						success (){ }
					})
					console.log('进入错误状态')
				})
			}
		}
	})
}

/**
 * 获取用户信息后进行登录拉取签的数据
 * @param {*} pageFrom
 * @param {*} _self
 * @param {*} qId
 * @param {*} _GData
 */
function getTokenQian(pageFrom,_self,qId,_GData){
	// 保存当前的签 
	Storage.detailLotId = qId

	if (pageFrom == 'share' || pageFrom == 'list' || pageFrom == 'form') {
		if (pageFrom == 'share' || pageFrom == 'form') {
			_self.setData({
				isFromShare: true,
				"navConf.root": '/pages/home/home'
			})
		}
		getQian(qId,_self)
	} else if(Storage.lotOpts.fromSource && Storage.lotOpts.fromSource === 'shake'){
		_self.setData({
			lotDetail: Storage.lotDetail
		})
		wx.hideLoading()
	}
}

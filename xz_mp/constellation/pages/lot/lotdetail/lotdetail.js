// pages/lot/lotdetail/lotdetail.js
const $vm = getApp()
let _GData = $vm.globalData
const { canvasTextAutoLine, parseLot } = $vm.utils
const mta = require('../../../utils/mta_analysis.js');
const imgs = require('./imgs.js')
const bus = require('../../../event')
const Storage = require('../../../utils/storage')

const config = {
	data : {
		
		isFromShare: false,
		huan: false,//拆签成功
		showCanvas: false,
		imgs: imgs,
		lock: false, //锁
		// 问候语句
		helloText : '你好！',
		// 描述语句
		descText : '下面是你的每日一签,快去拆签吧!',
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
			ownerHeadImage : '/assets/images/default_head.png',
			troops : [{photo : '/assets/images/default_head.png'}],
			qianOpenSize: 3,
			showChai: true,
			hasChai: false,
			isOther : true,
			lotNotCompleted : true,
			troops : []
		}
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
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
		
		// 监听事件
        bus.on('login-success', () => {
            console.log('------------------登录标识-----------------------')
            self.setData({
                userInfo: Storage.userInfo
            })

			// 获取签的数据
			getTokenQian(options.from,self,options.lotId,_GData)

        }, 'login-com')

        // 来源
        if(options.fromSource){
            switch (options.fromSource) {
                case 'shake':
                    // 手动触发登录状态 
                    bus.emit('login-success', {}, 'login-com')
                    break;
                default:
                    break;
            }
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
	//拆签或者去
	chai: function (e) {
		let formid = e.detail.formId
		if (this.data.lock && !this.data.lotDetail.hasChai) {
			return false;
		}
		this.data.lock = true;
		$vm.api.getX610({ notShowLoading: true, formid: formid })
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
		$vm.api.getX506({ id: _Sdata.lotDetail.id, notShowLoading: true })
			.then(res => {
				console.log('未知数据：',res)
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

		$vm.api.getX610({ notShowLoading: true, formid: formid })
	},
	//保存图片
	onclickShareCircle: function (e) {
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
	}

}

Page(config)


/**
 * 重新加载数据
 * @param {*} qId
 */
function getQian(qId,_self,GData){
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
						var lotDetail = parseLot(res)
						_self.setData({
							lotDetail: lotDetail
						})
						setTimeout(() => {
							wx.hideLoading()
						},500)
					}).catch(err =>{
						_self.setData({
							isError : true
						})
						wx.showModal({
							title: '网络错误',
							content: '小主您的网络有点小问题哦,请重新尝试',
							confirmText : '重新尝试',
							showCancel: false,
							success (){
								login(_self,GData,qId)
							}
						})
						console.log('进入错误状态')
					})
				},3000)
			}else{
				$vm.api.getX511({ id: qId })
				.then(res => {
					console.log('签的数据===================：', res)
					var lotDetail = parseLot(res)
					_self.setData({
						lotDetail: lotDetail
					})
				}).catch(err =>{
					_self.setData({
						isError : true
					})
					wx.showModal({
						title: '网络错误',
						content: '小主您的网络有点小问题哦,请重新尝试',
						confirmText : '重新尝试',
						showCancel: false,
						success (){
							// getQian(qId,_self);
							login(_self,GData,qId)
						}
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

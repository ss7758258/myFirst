const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const util = require('../../../../utils/util')
const getImageInfo = util.wxPromisify(wx.getImageInfo)
let timer = null
let query = wx.createSelectorQuery()
// 确定按钮是否可点击
let sure = false
// 时间框是否选择时间
let dateSure = false
// 是否处在分享状态
let isShare = false
// 已经购买的数量
let ids = []
// 结果值
let ran = 0
const pageConf = {

	data: {
		navConf: {
			title: '占卜测试',
			state: 'root',
			isRoot: false,
			isIcon: true,
			root: '',
			bg: '#9262FB',
			isTitle: true,
		},
		// 根据导航高度做出适配
		height: 64,
		IPX: false,
		res: {
			bg: '../../tmp/bg_2.png',
			title: '../../tmp/title_2.png',
			button: '../../tmp/button_2.png',
			desc: '../../tmp/center.png',
			code: '/assets/images/qrcodetoday.png'
		},
		baseUrl: 'https://xingzuo-1256217146.file.myqcloud.com',
		// 参与人数
		text: util.random(10000, 25000),
		title: '',
		results: [1, 2, 3],
		// 当前选择性别
		current: 'man',
		// 当前测试第几步
		status: 1,
		// 结果图
		resImg: '../../tmp/res_2.png',
		resBg: '#ccc',
		head: '/assets/images/default_head.png',
		nickName: '',
		warn: 'rgba(151,151,151,1)',
		dateWarn: 'rgba(151,151,151,1)',
		warnText: '请输入姓名',
		date: '请输入生日',
		mask: true,
		system: {},
		animate: '',
		endDate : '1990-01-01'
	},

	onLoad: function (options) {
		let endDate = new Date()
		console.log('系统信息：', Storage.systemInfo)
		// 确定按钮是否可点击
		sure = false
		dateSure = false
		isShare = false
		ids = []
		// 结果值
		ran = 0
		mta.Page.init()
		this._handleShare(options)
		setTimeout(() => {
			this.setData({
				endDate : `${endDate.getFullYear()}-${endDate.getMonth() + 1}-${endDate.getDate()}`,
				head: Storage.userInfo.avatarUrl,
				nickName: Storage.userInfo.nickName,
				system: Storage.systemInfo
			})
		}, 150)

		console.log('分享的信息参数：', options)
	},

	onShow: function () {
		if (this.data.mask && isShare) {
			this.setData({
				animate: 'fly-animate'
			})
			setTimeout(() => {
				this.setData({
					mask: false
				})
			}, 1700)
		}
	},

	onHide: function () {

	},

	onShareAppMessage: function () {
		mta.Event.stat('divine_one_share_click', {
			gameid: this.data.gameId
		})
		// 正在分享状态
		isShare = true
		return {
			title: this.data.title,
			imageUrl: '../../source/share_test.png',
			path: '/pages/home/home?to=divine_three&from=share&source=share&id=999993&tid=123451&shareform=divine&gameId=' + this.data.gameId,
		}
	},
	// 变更date
	_ChangeVal(e) {
		let {
			value: val
		} = e.detail

		if (val.length > 0) {
			dateSure = true
			this.setData({
				date: val ? val : '1960-01-01'
			})
			return
		}
		dateSure = false
	},
	// 支付小星星购买
	_payStar() {
		let self = this
		let starNum = 9
		console.log('前往支付')
		mta.Event.stat('pay_click_test', {})
		wx.showModal({
			title: '确定快速查看？',
			content: '快速查看需要消耗' + starNum + '颗小星星',
			showCancel: true,
			cancelColor: '#999999',
			cancelText: '我再想想',
			confirmText: '确定',
			confirmColor: '#9262FB',
			success: function (res) {
				console.log(res)
				mta.Event.stat('pay_click_test_success', {})
				if (res.confirm) {
					API.getBlance({
						notShowLoading: true
					}).then(data => {
						if (!data) {
							return
						}
						console.log('钱包星星数量：', data)
						// data.balance = 2000
						// 当小星星不足时进行提示
						if (data.balance < starNum) {

							mta.Event.stat('pay_test_fail', {})

							if (!Storage.isLogin) {
								return
							}

							wx.showModal({
								title: '余额不足',
								content: '请先去获取一些小星星吧',
								showCancel: true,
								cancelColor: '#999999',
								cancelText: '我再想想',
								confirmText: '立即获取',
								confirmColor: '#9262FB',
								success: function (res) {
									if (res.confirm) {
										// 跳转到小星星页面
										wx.navigateTo({
											url: '/pages/banner/banner'
										})
									}
								}
							})
						} else {

							mta.Event.stat('pay_test_success', {})
							wx.showLoading({
								title: '购买中...'
							})

							API.setStar({
								id: 0,
								balance: -starNum,
								notShowLoading: true
							}).then(data => {
								console.log('购买结果：', data)
								wx.hideLoading()
								if (data && data.status === 'SUCCESS') {
									self.setData({
										animate: 'fly-animate'
									})
									console.log(ids)
									ids.push(parseInt(self.data.gameId))
									wx.setStorage({
										key: 'divineIds',
										data: ids
									})
									setTimeout(() => {
										self.setData({
											mask: false
										})
									}, 1700)
								} else {
									console.log('购买失败')
									wx.showModal({
										title: '失败提示',
										content: '未知错误，请联系我们',
										showCancel: false,
										confirmText: '确定',
										confirmColor: '#9262FB'
									})
								}
							}).catch(err => {
								wx.hideLoading()
								console.log('购买失败')
								wx.showModal({
									title: '失败提示',
									content: '购买失败',
									showCancel: false,
									confirmText: '确定',
									confirmColor: '#9262FB'
								})
							})
						}
					})
				}
			}
		})
	},
	// 开始游戏
	_startGame() {
		mta.Event.stat('start_game', {
			gameid: this.data.gameId
		})
		this.setData({
			status: 2
		})
		console.log('打开游戏')
	},
	_headError() {
		this.setData({
			head: '/assets/images/default_head.png'
		})
	},
	// 输入监听
	_input(e) {
		let {
			value: val
		} = e.detail
		if (val.length > 0) {
			sure = true
			this.setData({
				warn: 'rgba(151,151,151,1)'
			})
			return
		}
		sure = false
		console.log(val)
	},
	// 生成测试结果
	_randomResult(e) {
		let self = this
		let tmp = false

		mta.Event.stat('create_res', {
			gameid: this.data.gameId
		})

		if (!sure) {
			this.setData({
				warn: 'red',
				warnText: '姓名不能为空'
			})
			tmp = true
		}
		if (!dateSure) {
			this.setData({
				dateWarn: 'red',
				date: '生日不能为空'
			})
			tmp = true
		}
		if (tmp) {
			return
		}

		ran = util.random(0, this.data.results.length - 1)

		this.setData({
			warn: 'rgba(151,151,151,1)',
			warnText: '请输入姓名',
			resImg: this.data.results[ran]
		})

		wx.showLoading({
			title: '正在计算结果',
			mask: true,
		})
		wx.getStorage({
			key: 'divineIds',
			complete: function (v) {
				console.log('输出游戏Id', v)
				let mask = true
				
				if (v.data && v.data.constructor === Array && v.data.indexOf(parseInt(self.data.gameId)) != -1) {
					mask = false
				}

				ids = v.data ? v.data : []

				setTimeout(() => {
					self.setData({
						status: 3,
						mask: mask,
						'navConf.title': '测试结果'
					})
					wx.hideLoading();
				}, 1600)
			}
		})
	},
	// 处理分享参数
	_handleShare(opts) {
		if (opts) {
			if (!opts.gameId) {
				wx.showToast({
					title: '当前测试已失踪，正在为你返回',
					icon: 'none',
					duration: 1600,
					mask: true
				});
				return
			}
			this.setData({
				gameId: opts.gameId
			})
			if (opts.from === 'share') {
				mta.Event.stat('divine_share_come', {
					gameid: opts.gameId
				})
			} else {
				mta.Event.stat('divine_click_come', {
					gameid: opts.gameId
				})
			}
			// 总量统计
			mta.Event.stat('two_from_count', {
				gameid: opts.gameId
			})
			// 获取游戏的内容
			this._getGameInfo()
		}
	},
	// 切换性别
	_switchTab(e) {
		let {
			type
		} = e.currentTarget.dataset
		this.setData({
			current: type
		})
	},
	// 获取内容信息
	_getGameInfo() {
		console.log('执行了', this.data.gameId)
		API.getGameInfo({
			id: this.data.gameId
		}).then(res => {
			console.log('玩法结果', res)
			if (!res) {
				wx.showToast({
					title: '当前测试已失踪，正在为你返回',
					icon: 'none',
					duration: 1600,
					mask: true,
					success() {
						setTimeout(() => {
							wx.navigateBack({
								delta: 1
							});
						}, 1600)
					}
				});
				return
			}
			let tmp = res.test
			let data = this.data
			console.log(tmp)
			try {
				let _res = {
					bg: `${data.baseUrl}${tmp.picA}`,
					title: `${data.baseUrl}${tmp.picB}`,
					button: `${data.baseUrl}${tmp.picC}`,
					desc: `${data.baseUrl}${tmp.picD}`,
					code: `${data.baseUrl}${tmp.picE}`
				}
				let _list = res.test.results.map((v) => {
					return `${data.baseUrl}${v}`
				})
				console.log(_list)
				this.setData({
					res: _res,
					text: tmp.testnum == 0 ? util.random(10000, 25000) : tmp.testnum,
					title: tmp.name,
					results: _list,
					resBg: tmp.rgb,
					'navConf.title': tmp.name
				})
			} catch (error) {

				wx.showToast({
					title: '当前测试已失踪，正在为你返回',
					icon: 'none',
					duration: 1600,
					mask: true,
					success() {
						setTimeout(() => {
							wx.navigateBack({
								delta: 1
							});
						}, 1600)
					}
				});
			}
		}).catch(err => {
			console.log(err)
			wx.showToast({
				title: '当前测试已失踪，正在为你返回',
				icon: 'none',
				duration: 1600,
				mask: true,
				success() {
					setTimeout(() => {
						wx.navigateBack({
							delta: 1
						});
					}, 1600)
				}
			});
		})
		// this._resizePanel()
	},

	// 前往摇一摇
	_goShake() {
		mta.Event.stat("divine_shake_click", {})
		wx.navigateTo({
			url: '/pages/lot/shake/shake'
		})
	},
	// 前往好友配对
	_goPairPYQ() {
		mta.Event.stat("divine_pair_click", {})
		wx.navigateTo({
			url: '/pages/components/pages/pair/pair'
		})
	},
	// 绘制图片生成图片并将图片展示到页面上
	_drawCode() {
		mta.Event.stat("test_pic_toimg", {
			gameid: this.data.gameId
		})
		let head = ''
		let self = this
		wx.showToast({
			title: '生成图片中...',
			icon: 'loading',
			mask: true
		})
		// wx.request({
		//     url : 'https://cli.im/mina/generate_qrcode',
		//     header: {
		// 		'Content-Type': 'application/x-www-form-urlencoded',
		// 	},
		//     data : {
		//         tpl_id : '17792',
		//         code_type : 'wxcode',
		//         'param_value[0]' : 'share',
		//         'param_value[1]' : 'divine_two',
		//         'param_value[2]' : this.data.gameId,
		//     },
		//     success(res){
		//         if(res && res.statusCode === 200 && res.data){
		//             let data = res.data
		//             if(data.code === 1 && data.data){

		//                 head = data.data.replace('http://','https://')
		// console.log('二维码网络地址:',head)
		// self.data.results[ran] = ''
		// console.log('二维码：',data,self.data.results[ran],)
		util.Promise.all([
			getImageInfo({
				src: self.data.res.code
			}),
			getImageInfo({
				src: self.data.resImg
			}),
			getImageInfo({
				src: Storage.userInfo.avatarUrl === '' ? '/assets/images/default_head.png' : Storage.userInfo.avatarUrl
			})
		]).then(res => {
			console.log('图片信息', res)
			// 画图
			const ctx = wx.createCanvasContext('shareCanvas')
			ctx.save()
			ctx.setFillStyle(self.data.resBg)
			ctx.fillRect(0, 0, 750, 1436)
			ctx.drawImage(res[0].path, 0, 0, 750, 1206) //背景图
			// 临时
			// ctx.drawImage('../../tmp/res_2.png', 0, 0, 750, 1206)
			ctx.drawImage(res[1].path, 0, 0, 750, 1206) //结果图

			ctx.save()
			ctx.arc(60, 70, 40, 0, 2 * Math.PI, false)
			ctx.fill()
			ctx.clip()
			ctx.drawImage(res[2].path, 20, 30, 80, 80); // 头像
			ctx.restore()


			ctx.setFillStyle('#fff')
			util.roundRect(44, 1236, 670, 174, 6, ctx)
			ctx.fill()

			// 二维码
			ctx.drawImage(res[0].path, 520, 1246, 150, 150);

			ctx.setTextAlign('left')
			ctx.setFontSize(36)
			ctx.fillText(Storage.userInfo.nickName, 120, 85)
			ctx.setFillStyle('#000')
			ctx.setLineWidth(5)
			ctx.fillText('小哥星座', 80, 1310)
			ctx.setFillStyle('#999999')
			ctx.setFontSize(24)
			ctx.fillText('长按识别二维码,获取你的测试结果', 80, 1370)


			ctx.draw()
			console.log('画图完成===================')
			setTimeout(() => {
				wx.canvasToTempFilePath({
					x: 0,
					y: 0,
					width: 750,
					height: 1436,
					destWidth: 750,
					destHeight: 1436,
					canvasId: 'shareCanvas',
					success: function (res) {
						console.log(res.tempFilePath)
						wx.saveImageToPhotosAlbum({
							filePath: res.tempFilePath,
							success(data) {
								setTimeout(() => {
									wx.hideLoading()
									wx.showModal({
										title: '保存成功',
										content: '图片已经保存到相册，可以分享到朋友圈了',
										showCancel: false,
									})
								}, 300)
							},
							fail() {
								wx.hideLoading()
								wx.showToast({
									title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
									icon: 'none',
									duration: 1700
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
							duration: 1700
						})
					},
				})
			}, 500)
		}).catch(err => {
			wx.showToast({
				title: '生成分享信息失败',
				icon: 'none',
				duration: 1700,
				mask: true
			})
		})
		//             }else{
		//                 wx.showToast({
		//                     title: '生成分享信息失败',
		//                     icon: 'none',
		//                     duration: 1700,
		//                     mask: true
		//                 })
		//             }
		//         }else{
		//             wx.showToast({
		//                 title: '生成分享信息失败',
		//                 icon: 'none',
		//                 duration: 1700,
		//                 mask: true
		//             })
		//         }
		//     },
		//     fail(){
		//         wx.showToast({
		//             title: '生成分享信息失败',
		//             icon: 'none',
		//             duration: 1700,
		//             mask: true
		//         })
		//     }
		// })
	},

	// 根据导航设置高度
	_setHeight(e) {
		let temp = e.detail || 64
		this.setData({
			height: temp,
			IPX: temp === 64 ? false : true
		})
	},
	/**
	 * 上报formId
	 * @param {*} e
	 */
	_reportFormId(e) {
		console.log(e.detail.formId)
		let formid = e.detail.formId
		API.getX610({
			notShowLoading: true,
			formid: formid
		})
	}
}

Page(pageConf);
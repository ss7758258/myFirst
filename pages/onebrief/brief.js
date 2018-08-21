// pages/onebrief/brief.js
const $vm = getApp()
const _GData = $vm.globalData
const getImageInfo = $vm.utils.wxPromisify(wx.getImageInfo)
var mta = require('../../utils/mta_analysis.js')
const Storage = require('../../utils/storage')
const bus = require('../../event')
const q = require('../../utils/source')
let env = 'dev'
let timer=false
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		picUserName: '',//用户昵称
		isFromShare: false, //未调用
		prevPic: "",
		isShow: false,
		navConf: {
			title: '一言',
			state: 'root',
			isRoot: false,
			isIcon: true,
			iconPath: '',
			root: '',
			isTitle: true
		},
		// 适配高度
		hei : 64,
		isIPhoneX : false,
        current:0,//当前滑块
        isFirst:false, //是否是第一次进来
        list:true,//页面渲染数据
        emptylist:false,//页面数据为空所加载
        tomorrow:{
            year:false,
            month:false,
            day:false,
            hour:false,
            minute:false,  
            sec:false,
            timer:true,
		},
		page:1,//默认分页
		// 版本
        version : true
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		console.log('----------------------------------------------brief onLoad')
		let self=this
		if(options.from === 'qrcode'){
			mta.Event.stat('qrcode_brief',{})
		}
		q.sourceHandle(options)
		
        let handle = () => {
            console.log('登录标识')
            // self.shakeLotBox()
            Storage.briefLogin = true
            self.setData({
                userInfo: Storage.userInfo,
                picUserName: Storage.userInfo.nickName
            })
            console.log(self.data.userInfo)
            this.getwordlist() //获取一言数据
            getSystemInfo(this)
            wx.hideShareMenu()  //隐藏转发按钮
        }

        if (Storage.briefRemoveId) {
            bus.remove(Storage.briefRemoveId)
        }
        if (Storage.briefLoginRemoveId) {
            bus.remove(Storage.briefLoginRemoveId)
        }
        Storage.briefRemoveId = bus.on('login-success', handle, 'login-com')
        Storage.briefLoginRemoveId = bus.on('login-success', handle, 'brief-app')

        // 如果已经存在用户信息触发登录标识
        if (Storage.userInfo) {
            // 已经触发过登录不在触发
            // if (Storage.briefLogin) {
            //     return
            // }
            bus.emit('login-success', {}, 'brief-app')
        }
        
		mta.Page.init()
	},

    onShow:function(){
        this.setData({
            version : Storage.miniPro
        })
        let starXz = Storage.starXz || {}
        console.log(starXz.id, wx.getStorageSync('constellationId'))
        this.gettomorrow() //获取日期时间，及倒计时时间
        if (starXz.id && starXz.id != wx.getStorageSync('constellationId')){ //判断星座id是否有变动
            this.getwordlist() //获取一言数据
        }

        
        let isFirst=wx.getStorageInfoSync().keys
        if (isFirst.indexOf('isFirst') == -1) {
            
            this.setData({
                isFirst: true
            })
        }

        // this.countdown()    //倒计时
    },

    onHide:function(){
        clearInterval(timer)
    },
	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function () {
		return {
			title : '这不是简单的句子，全是你内心的独白',
            imageUrl: '/assets/images/share-brief.jpg',
			path : '/pages/home/home?to=brief&from=share&source=share&id=999998&tid=123455&shareform=brief&m=0',
		}
	},

	/**
	 * 保存图片
	 */
	saveSelect: function (e) {
		mta.Event.stat('brief_save_pic',{})
        console.log('eeeeeeeee',e)
		let formid = e.detail.formId
        let img = e.currentTarget.dataset.img
		$vm.api.getX610({ notShowLoading: true, formid: formid })
		mta.Event.stat("ico_brief_save", {})
		const _self = this
		const _SData = _self.data
		wx.showLoading({
			title: '图片生成中...',
			mask: true,
		})
		_self.setData({
			showCanvas: true,
		})
		$vm.utils.Promise.all([
			getImageInfo({
				src: img,
			})
			
		]).then((res) => {
			console.log(res)
			const ctx = wx.createCanvasContext('shareCanvas')
			ctx.setFillStyle('white')
			ctx.fillRect(0, 0, 375, 667)
			ctx.drawImage(res[0].path, 0, 0, 375, 375.0 / res[0].width * res[0].height)

			ctx.setTextAlign('center') // 文字居中
			ctx.setFillStyle('#333333') // 文字颜色：黑色
			ctx.setFontSize(12) // 文字字号：22px
			ctx.fillText(_GData.userInfo.nickName, 375 / 2, 570 / 2)
			ctx.stroke()
			const qrImgSize = 100
			ctx.drawImage('/assets/images/qrcodebrief.png', (375 - qrImgSize) / 2, 518, qrImgSize, qrImgSize)
			ctx.stroke()
			ctx.setTextAlign('center') // 文字居中
			ctx.setFillStyle('#333333') // 文字颜色：黑色
			ctx.setFontSize(12) // 文字字号：22px
			ctx.fillText("来自一言", 375 / 2, 631 + 12)

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
									showCancel: false,
								})
								_self.setData({
									showCanvas: false,
								})
								// wx.hideLoading()
							},
							fail() {
								wx.showToast({
									title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
									icon: 'none',
									duration: 3000
								})
								_self.setData({
									showCanvas: false,
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
						_self.setData({
							showCanvas: false,
						})
						// wx.hideLoading()
					},
				})
			}, 1000)
		})
		.catch((err) => {
			wx.hideLoading()
            console.log('保存图片错误信息',err)
			wx.showToast({
				icon: 'none',
				title: '加载失败了，请检查网络',
			})
		})
	},
	onLodingListener: function (e) {
		console.log('图片加载完成时：', e)
		wx.hideLoading()
		const _self = this
		if (e.detail.height && e.detail.width) {
			_self.setData({
				// picUserName: _GData.userInfo.nickName,
				// 开启图片展示
				isShow: true,
			})
		}
	},

	/**
	 *
	 * 图片加载失败
	 */
	errorOpen(){
		let self = this
		$vm.api.getDayx400({ notShowLoading: true })
		.then((res) => {
			console.log(res)
			wx.hideLoading()
			if (res) {
				self.setData({
					prevPic:
						res.prevPic ? "https://xingzuo-1256217146.file.myqcloud.com" + (env === 'dev' ? '' : '/prod') + res.prevPic :
							"",
				})
			} else {
				self.setData({
					networkError: true,
					prevPic: "/assets/images/loading.png",
				})
			}
		}).catch((err) => {
			wx.hideLoading()
			wx.showToast({
				icon: 'none',
				title: '加载失败了，请小主稍后再试',
			})
		})
	},
	onclickHome: function (e) {
		mta.Event.stat("ico_brief_home", {})
		let formid = e.detail.formId
		$vm.api.getX610({ notShowLoading: true, formid: formid })
		wx.reLaunch({
			url: '/pages/home/home',
		})
	},
    // 上报formid
    formid(e){
        let isFirst = wx.getStorageInfoSync().keys
        if (isFirst.indexOf('isFirst') == -1) {
            wx.setStorageSync('isFirst', '1')
            this.setData({
                isFirst: false
            })
        }
        
        $vm.api.getX610({ formid: e.detail.formId, notShowLoading: true})
    },
    
    // 获取一言列表数据
    getwordlist(){
		let self=this
		// let page=this.data.page
        $vm.api.wordlist({ constellationId: _GData.selectConstellation.id, startpage: this.data.page, notShowLoading: true}).then(res=>{
            // wx.showLoading({
            //     title: '加载ing',
            //     mask: true,
            // })
            wx.setStorageSync('constellationId', _GData.selectConstellation.id)  // 设置星座id缓存
            console.log('获取一言数据:', res)
            if(res.wordlist.length > 0){
				let url = 'https://xingzuo-1256217146.file.myqcloud.com' + (env === 'dev' ? '' : '/prod')
				res.wordlist.forEach(function (value) {
					value.prevPic = url + value.prevPic
				})
				res.wordlist.push({  //添加明日展示
					prevPic: false
				})

				
				this.setData({
					list: res.wordlist,
					current: res.wordlist.length - 2
				})


				// if(page == 1){	
				// 	res.wordlist.push({  //添加明日展示
				// 		prevPic: false
				// 	})
	
					
				// 	this.setData({
				// 		list: res.wordlist,
				// 		current: res.wordlist.length - 2
				// 	})
					
					
				// 	console.log('getwordlist打印数据',this.data.list)
				// }else{
				// 	wx.showLoading({
				// 		title:'加载中ing'
				// 	})
				// 	this.setData({
				// 		list:res.wordlist.concat(this.data.list),
				// 		current:res.wordlist.length
				// 	})
				// }
				
				wx.hideLoading()
				console.log('getwordlist打印数据',this.data.list)
            }else{
                wx.hideLoading()
                this.setData({
                    list:false
                })
            }
            
        }).catch(res=>{
            wx.hideLoading()
            this.setData({
                list:false
            })
        })
    },

    // 获取明日数据
    gettomorrow(){
        let monthE = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']

        console.log(this.data.tomorrow.timer,this.data.list)

        if(this.data.tomorrow.timer){
            let b = new Date(new Date().getTime() + 60 * 60 * 24 * 1000)
            let year = b.getFullYear()
            let month = monthE[b.getMonth()]
            let day = b.getDate() > 9 ? b.getDate() : '0' + b.getDate()

            let c = new Date(new Date().toLocaleDateString()).getTime() + 60 * 60 * 24 * 1000 //下一天0点时刻时间戳
            let tomorrow = c - new Date().getTime()
            let tomorrow_timer = new Date(tomorrow - 8 * 60 * 60 * 1000)
            let hour = tomorrow_timer.getHours()
            let minute = tomorrow_timer.getMinutes()
            let sec = tomorrow_timer.getSeconds()

            console.log(`日期：${b},年：${year},月：${month},日：${day},倒计时：${hour}:${minute}:${sec}`)

            this.setData({
                'tomorrow.year': year,
                'tomorrow.month': month,
                'tomorrow.day': day,
                'tomorrow.hour': hour,
                'tomorrow.minute': minute,
                'tomorrow.sec': sec
            })
            this.countdown(hour, minute, sec)    //倒计时
        }else{   //空页面
            let date=new Date()
            let year = date.getFullYear()
            let month = monthE[date.getMonth()]
            let day = date.getDate() > 9 ? date.getDate() : '0' + date.getDate()
            this.setData({
                'tomorrow.year': year,
                'tomorrow.month': month,
                'tomorrow.day': day
            })
        }
        
    },

    // 明日倒计时
    countdown(hour,minute,sec){
        let self=this
        console.log(`倒计时：${hour}:${minute}:${sec}`)
            timer = setInterval(function () {
                sec--
                // console.log(sec)

                if (hour == 0 && minute == 0 && sec == 0) {
                    clearInterval(timer)
					self.getwordlist()
					self.gettomorrow()
					return
                    // self.setData({
                    //     'tomorrow.timer': false
                    // })
                }

                if (sec >= 0) {
                    self.setData({
                        'tomorrow.sec': sec
                    })
                } else if (sec < 0 && minute > 0) {
                    self.setData({
                        'tomorrow.minute': minute - 1,
                        'tomorrow.sec': 59
                    })
                    sec = 59, minute -= 1
                } else if (sec < 0 && minute == 0 && hour > 0) {
                    self.setData({
                        'tomorrow.hour': hour - 1,
                        'tomorrow.minute': 59,
                    })
                    sec = 59, minute = 59, hour -= 1
                }
            }, 1000)
        
    },

    // 更多好玩
    moregame(e){
        $vm.api.getX610({ formid: e.detail.formId })
		wx.navigateToMiniProgram({
            appId: 'wx865935599617fbdb',
            path: 'pages/home/home?source=XGstars&type=in&id=110000'
        })
    },
    // 获取导航栏高度
    _setHeight(e){
        console.log(e.detail)
        this.setData({
            hei : e.detail || 64
        })
    },
    // 再试一次
    tryagain(e){
        $vm.api.getX610({ formid: e.detail.formId })
        wx.reLaunch({
            url: '/pages/home/home'
        })
	},
	// 获取上一页数据
	day(e){
		console.log(e)
        if (e.detail.source == "touch") {
            //防止swiper控件卡死
            if (this.data.current == 0 && this.data.preIndex > 1) {//卡死时，重置current为正确索引
                this.setData({ current: this.data.preIndex });
            }else {//正常轮转时，记录正确页码索引
                this.setData({ preIndex: this.data.current });
            }
        }
		// if(e.detail.current == 0){
		// 	this.setData({
		// 		page:++this.data.page
		// 	})
		// 	this.getwordlist()
		// }
	}
})

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self){
	let res = wx.getSystemInfoSync();
	console.log('设备信息：',res);
	if(res){
		// 长屏手机适配
		if(res.screenWidth <= 375 && res.screenHeight >= 750){
			self.setData({
				isIPhoneX : true
			})
		}
	}
}
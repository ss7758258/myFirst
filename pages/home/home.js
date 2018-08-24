// pages/home/home.js
let $vm = getApp()
const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')
const star = require('../../utils/star')
const Storage = require('../../utils/storage')
const params = require('../../utils/share')
const methods = require('./methods')
const desc = require('./desc')

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		cdn : 'https://xingzuo-1256217146.file.myqcloud.com',
		// 默认不打开星座描述
		showStarDesc : false,
		// 滚动高度
		scrolltop : 0,
		// 星座信息数据
		desc : desc,
		// 是否显示选择星座
		showChoice : false,
		// 星座信息
		star : star,
		// 多少人在玩
		listNum : [{},{}],
		xz : {
			constellationId : 1,
			healthy : 55,
			luckyColor : '紫',
			luckyNum : 9
		},
		isLoading: false,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		// 星座信息
		myConstellation: {},
		// 每日提醒内容
		dayNotice: '好运伴随你',
		timer: null,
		xiaodaka: {
			appId: 'wx855c5d7718f218c9',
			path: '/pages/index/index?wxID=ad6377&scene=gzhgl922689',
			openType: 'navigate',
			extra: '',
			txt: '打卡',
			version: 'release'
		},
		navConf : {
			title : '小哥星座',
			state : 'root',
			isRoot : true,
			isIcon : false,
			iconPath : '',
			bg : '#9262FB',
			root : '',
			isTitle : true,
			centerPath : '/pages/center/center'
		},
		// 导航的高度
		hei : 64,
		showPair : false,
		showBanner : false,
		clockStatus : false,  //小打卡开关
		isBanner : false, // 广告位开关
		isIPhoneX : false,
		PX : false, // iPhone X的适配
		isLogin : false, // 是否登录完成
		noticeBtnStatus : false, // 通知开关
		showFollow : false, // 关注服务号开关
		// 待领星星文案
		more_startext : '0颗待领',
		showDialog : false, // 弹窗提示
		dialo : {},
        notice: {isShow: false},  //公告组件
		showCollect : false, // 控制收藏弹窗的显示
		showCollectBtn : false // 控制收藏按钮的显示
	},
	
	// 初始化
	onLoad : methods.onLoad,
	// 收集formId
	setFormId : methods.reportFormId,
	// 前往更多运势
	goLuck : methods.goLuck,
	// 显示
	onShow : methods.onShow,
	// 选择星座
	choiceStar : methods.choiceStar,
	// 获取星座数据信息
	onShowingHome : methods.onShowingHome,
	// 前往选择星座
	goChoiceStar : methods.goChoiceStar,
	// 前往Banner页面
	goBanner : methods.goBanner,
    // //页面关闭
    // onHide:methods.onHide,
	// 用户点击右上角分享
	onShareAppMessage: function (res) {
		mta.Event.stat("home_share", {})
		return {
			title: '星座运势,唯我独准',
			imageUrl: '/assets/images/share_home.jpg'
		}
	},
	// 前往星座配对
	goPair(){
		wx.navigateTo({
			url:'/pages/components/pages/pair/pair'
		})
	},
	// 前往星座配对
	goPairPYQ(){
		mta.Event.stat("pair_pyq_click", {})
		wx.navigateTo({
			url : '/pages/components/pages/pairCus/pairCus'
		})
	},
	// 前往摇签
	goShake(){
		mta.Event.stat("shake_click", {})
		wx.navigateTo({
			url:'/pages/lot/shake/shake'
		})
	},
	// 打开星座描述
	openDesc(){
		mta.Event.stat("star_desc_click", {})
		this.setData({
			showStarDesc : true,
			scrolltop : 0
		})
	},
    // 获取导航栏高度
    _setHeight(e){
        console.log(e.detail)
        this.setData({
            nav : e.detail || 64
        })
    },
	// 关闭信息描述
	closeDesc(){
		this.setData({
			showStarDesc : false
		})
	},
	// 前往运势详情页
	toDay(e) {
		mta.Event.stat("ico_home_to_today", {})
		wx.navigateTo({
			// url: '/pages/today/today'
            url:'/pages/components/pages/luckDetails/luckDetails'
		})
	},
	// 代开客服
	openContact(){
		this.hideFollow()
		// console.log('打开了客服',arguments)
		mta.Event.stat("spread_123437", {})
	},
	//  重置用户登录锁
	_resetToken(){
		// 重置登录锁
		Storage.loginLock = false
	},
	// 关闭弹窗
	hideFollow(){
		console.log('触发关闭')
		this.setData({
			showFollow : false
		})
	},
	// 打开通知并且隐藏
	openNotice(){
		let me = this
		me.setData({
			showFollow : true
		})
		mta.Event.stat("spread_123438", {})
		console.log('弹窗')
		return false
	},
    // 打开收藏
	openCollect(){
		let me = this
		me.setData({
			showCollect : true
		})
		mta.Event.stat("open_collect", {})
		return false
	},
	// 关闭弹窗
	closeCollect(){
		this.setData({
			showCollect : false
		})
	},
	// 关闭收藏提示
	closeCollectBtn(){
		this.setData({
			showCollectBtn : false
		})
		wx.setStorage({
			key:'showCollectBtn',
			data : true
		})
	},
	// 获取更新信息
	_getUpdate(){
		let self = this
		if(!wx.getStorageSync('update_first_status')){
			wx.setStorageSync('update_first_status', '999999');
			return
		}
		if(this.data.showChoice){
			return
		}
		console.log(wx.getStorageSync('update_dialo_status'))
		if(wx.getStorageSync('update_dialo_status')){
			return
		}
		let _start = (new Date(new Date().toLocaleDateString())).getTime()
		let _end = _start + 1000 * 60 * 60 * 24
		let start = wx.getStorageSync('update_start_time') || _start
		let end = wx.getStorageSync('update_end_time') || _end
		let openNum = wx.getStorageSync('update_open_num') || 0
		let date = (new Date()).getTime()
		if(start < date && date < end && openNum > 0){
			return
		}
		if(this.options.share_notice === 'shake' || this.options.share_notice === 'lotdetail'){
			return
		}
		API.getUpdate({
			notShowLoading : true
		}).then(res => {
			console.log('输出当前版本更新信息：',res)
			// console.log(JSON.stringify(res))
			
			if(res && res.prevPic){
				wx.setStorageSync('update_start_time', _start)
				wx.setStorageSync('update_end_time', _end)
				wx.setStorageSync('update_open_num', 1)
				self.setData({
					showDialog : true,
					dialo : res
				})
			}else{
				self.setData({
					showDialog : false
				})
			}
		})
	},
	// 前往页面
	_goPage(){
		mta.Event.stat(`sure_click`,{})
		let url = this.data.dialo.url
		// url = 'from=home&to=shake&type=nav'
		let _param = {}
		url.split('&').map((v) => {
			let temp = v.split('=')
			_param[temp[0]] = temp[1]
			return 0
		})
		console.log(params)
		let from = _param.from
		let to = _param.to
		let temp = params[to]
		if(temp && temp.constructor === Object){
			mta.Event.stat(`${from}_${to}`,{})
			
			if(temp.type === 'tab'){
				wx.switchTab({
					url : temp.path + '?' + url
				})
			}else{
				wx.navigateTo({
					url : temp.path + '?' + url
				})
			}
			this.setData({
				showDialog : false
			})
		}
	},
	// 关闭更新提示
	_closeUpdate(){
		mta.Event.stat(`close_notice`,{})
		this.setData({
			showDialog : false
		})
	},
	// 将更新提示放入缓存不在展示
	_cancel(){
		mta.Event.stat(`cancel_notice`,{})
		wx.setStorage({
			key: 'update_dialo_status',
			data: 99999
		})
		this.setData({
			showDialog : false
		})
	},
    // 获取公告数据
    getNotice() {
        if (this.data.showChoice){
            this.setData({
                'notice.isShow':false
            })
        }else{
            this.setData({
                'notice.isShow': true
            })
        }

        $vm.api.notice({ page: 1, notShowLoading: true }).then(res => {
            console.log('11111111111111111111', res)
            if (res) {
                let top = [] || 0, bottom=[] || 0
                res.forEach(value => {
                    if (value.type == 1) {
                        top.push(value)
                    } else if (value.type == 2) {
                        bottom.push(value)
                    }
                })

                console.log('top:', top, 'bottom:', bottom)
                if(top==0 && bottom==0){
                    this.setData({
                        'notice.isShow': false
                    })
                }
                
                if(top.length == 1){
                    top.push(top[0])
                }
                if(bottom.length == 1){
                    bottom.push(bottom[0])
                }

                console.log('top:',top,'bottom:',bottom)
                this.setData({
                    'notice.top': top || 0,
                    'notice.bottom': bottom || 0
                })

                console.log(this.data.notice)
                
            }
        }).catch(err => {
            console.log(err)
        })
    },


})



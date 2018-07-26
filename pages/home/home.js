// pages/home/home.js
let $vm = getApp()
const api = $vm.api
const mta = require('../../utils/mta_analysis.js')
const star = require('./star')
const Storage = require('../../utils/storage')
const bus = require('../../event')
const methods = require('./methods')
const {parseIndex} = $vm.utils
let _GData = $vm.globalData

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;
let timer = null

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		// 选择星座
		showChoice : true,
		// 星座信息
		star : star,
		xz : {
			constellationId : 1,
			healthy : 55,
			luckyColor : '紫',
			luckyNum : 9
		},
		isLoading: false,
		selectBack: false,
		showHome: false,
		hasAuthorize: true,
		signList: $vm.utils.constellation,
		selectStatus: {
			selected: false,
			current: -1
		},
		pageFrom: null,
		myConstellation: {},
		dayNotice: '',
		myLuck: [
			{
				name : '爱情运',
				count : 1,
				color : '#9262FB'
			},
			{
				name : '升值运',
				count : 2,
				color : '#DA6AE4'
			},
			{
				name : '暴富运',
				count : 3,
				color : '#B3B4FF'
			},
			{
				name : '健康运',
				count : 4,
				color : '#88BB74'
			}
		],
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
			root : '',
			isTitle : true,
			centerPath : '/pages/center/center'
		},
		clockStatus : false,  //小打卡开关
		isBanner : false, // 广告位开关
		isIPhoneX : false,
		isLogin : false, // 是否登录完成
		noticeBtnStatus : false, // 通知开关
		showFollow : false, // 关注服务号开关
		shareCard : {
			list:[]
		},
		// 待领星星文案
		more_startext : '0颗待领'
	},

	goMore (e){
		console.log('触犯了更多事件')
	},
	selectSign: function (e) {
		const _self = this
		const selectConstellation = e.detail.target.dataset.item
		mta.Event.stat('ico_home_select', { 'constellation': selectConstellation.name })
		_GData.selectConstellation = selectConstellation
		wx.setStorage({
			key: 'selectConstellation',
			data: e.detail.target.dataset.item
		})
		_self.setData({
			myConstellation: selectConstellation,
			selectBack: false,
			showHome: true,
			'navConf.isIcon' : true,
			'selectStatus.current': selectConstellation.id - 1,
			'selectStatus.selected': true
		})
		_self.onShowingHome()
	},

	onShowingHome: function () {
		Storage.prevPic = null
		const self = this
		const _SData = this.data
		Storage.userInfo = Storage.userInfo || {}
        
        $vm.api.choice({ notShowLoading : true, constellationId: _GData.selectConstellation.id}).then(res=>{
            // 获取一言图片
            // getDay()
            console.log('choice运势数据',res)
            if(res !=''){
                Storage.lucky = res
                let luckyindex = [res.summaryPercentage || 1, res.lovePercentage || 2, res.moneyPercentage || 3, res.workPercentage || 4]
                let luckyname = ['综合指数', '爱情指数', '财富指数', '工作指数'], mylucky = []
                let luckycolor = ['#9262FB', '#DA6AE4', '#B3B4FF', '#88BB74']
                for (let i = 0; i < 4; i++) {
                    mylucky.push({
                        name: luckyname[i],
                        count: luckyindex[i],
                        color: luckycolor[i]
                    })
				}
				res.healthy = res.summaryPercentage + 30
				if(res.healthy > 100){
					res.healthy = 96
				}
                console.log('指数数据===',mylucky)
                self.setData({
					xz : res,
                    myLuck: mylucky,
                    dayNotice: res.dayNotice ? res.dayNotice : ''
                })

                if (!self.goPage(_SData)) {
                    // const myLuckLen = myLuck.length
                    self.circleDynamic()();
                }
            }
            

        }).catch(res=>{
            console.log('choice运势报错返回数据',res)
        })
	},
	// 初始化
	onLoad : methods.onLoad,
	// 收集formId
	setFormId : methods.reportFormId,
	// 前往更多运势
	goLuck : methods.goLuck,
	// 显示
	onShow : methods.onLoad,

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function (res) {
		if (res.from = 'menu') {
			mta.Event.stat("ico_from_home", {})
		}

		return {
			title: '用小哥星座，得最全最准的运势预测！',
			imageUrl: '/assets/images/share_home.jpg',
			success: function (res) {
				// 转发成功
			},
			fail: function (res) {
				// 转发失败
			}
		}
	},
	/**
	 * 前往一言
	 * @param {*} _SData
	 * @returns
	 */
	goPage (_SData) {
		console.log('===============')
		var shouldGo = false
		if (_SData.pageFrom == 'share') {
			if (_SData.toPage == 'brief') {
				wx.navigateTo({
					url: '/pages/onebrief/brief?from=share'
				})
			}
			shouldGo = true
		}
		return shouldGo
	},

	tween: function (t, b, c, d) {
		return c * t / d + b;
	},
	//圈圈的动态
	circleDynamic: function (n) {

		const _self = this
		const _SData = this.data
		const myLuckList = _SData.myLuck
		let keys = [], counts = [];

		myLuckList.forEach((v, ind) => {
			keys.push('myLuck[' + ind + '].count');
			counts.push(myLuckList[ind].count);
		});

		let t = 0, b = 0, d = 15;

		function price() {
			if (!_self.data.showHome) {
				return
			}
			t++
			if (t > d) {
				return
			} else {
				keys.forEach((v, ind) => {
					_self.setData({
						[v]: Math.floor(_self.tween(t, b, counts[ind], d))
					})
				})
			}
			// 记录timer
			timer = setTimeout(price, 15);
			
		}
		return price
	},

	onClickConstellation: function () {
		// 清除定时
		clearTimeout(timer ? timer : '');
		mta.Event.stat("ico_home_unselect", {})
		wx.setStorage({
			key: 'selectConstellation',
			data: null,
		})
		_GData.selectConstellation = null
		this.setData({
			selectBack: true,
			showHome: false,
			'navConf.isIcon' : false,
			'selectStatus.current': -1,
			'selectStatus.selected': false
		})

	},
	/**
	 * 摇签页面
	 * @param {*} e
	 */
	onelot(e) {
		const _self = this
		if (_self.data.isLoading) {
			return
		}
		_self.setData({
			isLoading: true
		})
		let formid = e.detail.formId
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_shake", {})
		wx.navigateTo({
			url: '/pages/lot/shake/shake?fromSource=home&formid=' + formid,
			complete: function (res) {
				_self.setData({
					isLoading: false
				})
			}
		})
	},
	oneword: function (e) {
		console.log('进入一言',e)
		let type = e.detail.target.dataset.key || 'yiyan';
		let formid = e.detail.formId
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		if(type === 'more'){
			mta.Event.stat("ico_home_to_banner", {})
			wx.navigateTo({
				url: '/pages/banner/banner?formid=' + formid
			})
			return false;
		}
		mta.Event.stat("ico_home_to_brief", {})
		wx.navigateTo({
			url: '/pages/onebrief/brief?formid=' + formid
		})
	},
	/**
	 * 运势
	 * @param {*} e
	 */
	today(e) {
		let formid = e.detail.formId
		let me = this
		$vm.api.getX610({
			notShowLoading: true,
			formid: formid
		})
		mta.Event.stat("ico_home_to_today", {})
		
		let temp = wx.getStorageSync('userInfo') || {nickName : ''}
		wx.navigateTo({
			// url: '/pages/today/today?formid=' + formid
            url:'/pages/components/pages/luckDetails/luckDetails'
		})
	},
	show_card (e){
		console.log('展示卡片数据：',e)
	},
	openContact(){
		this.hideFollow()
		mta.Event.stat("spread_123437", {})
		// console.log('打开了客服',arguments)
	},
	catchHide(){

	},
	/**
	 * 重置用户登录锁
	 */
	_resetToken(){
		// 重置登录锁
		Storage.loginLock = false
	},
	/**
	 * 关闭弹窗
	 */
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
	}
})




/**
 * 将对象解析成所需要的数组
 * @param {*} res
 */
function formatShareCard(res){
	if(!res) return
	let temps = []
	for(let ind = 0;ind < 4; ind++){
		temps.push({
			imgUrl : conf.cdn + '/' + res['luckyImg' + (ind + 1)] || '',
			content : res['luckyContent' + (ind + 1)] || '',
			text_score : (res['luckyType' + (ind + 1)] || '') + ('（' + res['luckyScore' + (ind + 1)] + '）' || ''),
			score : parseHandle(res['luckyScore' + (ind + 1)] || 10),
			nickName : _GData.userInfo.nickName
		})
	}
	console.log('卡片数据信息：',temps)
	return temps
}

function parseHandle(res){
    let temp = parseInt(res.replace('%'))
    // console.log(Math.ceil(temp / 10))
    return Math.ceil(temp / 10)
}


/**
 * 获取每日的幸运值
 * @param {*} self
 * @param {*} selectConstellation
 */
function getContent(self,selectConstellation){
	if (selectConstellation && !selectConstellation.isFirst) {
		self.setData({
			myConstellation: selectConstellation,
			selectBack: false,
			showHome: true,
			'navConf.isIcon' : true
		})
		self.onShowingHome()
	} else {
		self.setData({
			showHome: false,
			'navConf.isIcon' : false
		})
	}
}

/**
 * 获取一言图片信息
 */
function getDay(){
	$vm.api.getDayx400({ notShowLoading: true })
	.then((res) => {
		console.log(res)
		if (res) {
			let env = 'dev';
			Storage.prevPic = res.prevPic ? "https://xingzuo-1256217146.file.myqcloud.com" + (env === 'dev' ? '' : '/prod') + res.prevPic :
			"";
		}
		
	}).catch((err) => {
		Storage.prevPic = null
	})
}


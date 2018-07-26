const bus = require('../../event')
const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis')
const Storage = require('../../utils/storage')
const c = require('../../config')
const confing = require('../../conf')
const conf = confing[c] || {}
let $vm = null
let _GData = null

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self){
	let res = Storage.systemInfo
	if(res){
		// 长屏手机适配
		if(res.screenWidth <= 375 && res.screenHeight >= 750){
			wx.setStorageSync('IPhoneX', true);
			self.setData({
				isIPhoneX : true
			})
		}
	}
}

/**
 * 乐摇摇外链数据获取
 * @param {*} self
 * @param {*} options
 */
function getLeYaoyao(self,options){
	if(!options.q) return
	console.log('输出用户来源参数：',decodeURIComponent(options.q))
	mta.Event.stat('spread_123435', {})
	let url = decodeURIComponent(options.q)
	
	if (String(url).indexOf('leyaoyao?') > 0 ) {
		let temps = []
		temps.push(url.split('leyaoyao?')[1])
		temps.push(`&appid=${confing.appId}`)
		// 拉取乐摇摇数据信息
		API.getLeYaoyao(temps.join('')).then(res => {
			console.log('乐摇摇返回信息：',res)
			if(res && res.data && res.data.constructor === Object){
				// res.data.result = 0
				switch (res.data.result) {
					case 0:
						wx.showModal({
							title: '游戏币已到账',
							content: '更多好玩，尽在小哥星座',
							showCancel: false,
							confirmText: '马上体验',
							confirmColor: '#3CC51F',
							success: res => {
								
							}
						});
						break;
					default:
						errorToast()
					break;
				}
			}
		}).catch(err => {
			errorToast()
			console.log('乐摇摇返回异常=========：',err)
		})
	}
}

/**
 * 乐摇摇错误提示
 */
function errorToast(){
	wx.showToast({
		title:'领取失败',
		icon : 'none',
		mask : true,
		duration : 3000
	})
}
/**
 * 获取星星数量
 * @param {*} self
 */
function getStarNum(self){
	wx.request({
		url : c === 'dev' ? 'https://micro.yetingfm.com/appwall/front/star/unreceived_num' : 'https://appwallapi.yetingfm.com/appwall-api/front/star/unreceived_num',
		method: 'GET',
		data: {
			openId : Storage.openId,
			appId : confing.appId
		},
		success (res){
			if(res.statusCode === 200){
			
				console.log(`星星数量${res.data.data}`)

				let text =  res.data.data || 0
				self.setData({
					more_star_show : text > 0 ,
					more_startext : text + '颗待领'
				})
			}
		},
		fail(){

		}
	})
}

/**
 * 解析来源
 * @param {*} self
 */
function parseForm(self,options){
	let fromwhere = options.from
	let to = options.to
	if (fromwhere == 'share' || fromwhere == 'activity') {
		self.setData({
			toPage: to,
			pageFrom: fromwhere
		})
		if (to == 'brief') {
			if (options.hotapp == 1) {
				mta.Event.stat("ico_in_from_brief_qrcode", {})
			} else {
				mta.Event.stat("ico_in_from_brief", {})
			}

		} else if (to == 'today') {
			if (options.hotapp == 1) {
				mta.Event.stat("ico_in_from_today_qrcode", {})
			} else if (fromwhere == 'activity') {
				console.log('ico_in_from_brief_activity')
				mta.Event.stat("ico_in_from_brief_activity", {})
			} else {
				mta.Event.stat("ico_in_from_today", {})
			}

		}
	}else if(fromwhere === 'spread'){ // 活动推广统计
		console.log('输出活动来源',options.id)
		if (reg.test(options.id)) {
			mta.Event.stat('spread_' + options.id, {})
		} else {
			mta.Event.stat('spread_unknown', {})
		}
	}
	
	// 统计特殊来源
	if(options.source && options.source.constructor === String && options.source !== ''){
		console.log('输出活动来源',options.id)
		if (reg.test(options.id)) {
			mta.Event.stat(options.source + '_' + options.id, {})
		} else {
			mta.Event.stat(options.source + '_unknown', {})
		}
	}
}

/**
 * 获取配置信息
 * @param {*} me
 */
function getConfing(me){

	API.globalSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成---------全局：',res);
		if(!res){
			return false;
		}

		// 变更状态
		me.setData({
			isBanner : res.bannerStatus === 1,
			// clockStatus : res.clockStatus === 1
		})
		// 默认小打卡是关闭状态
		// wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
	}).catch( err => {
		console.log('加载失败---------------------------------全局配置')
	})
}
/**
 * 获取需要token的用户配置
 * @param {*} me
 */
function getUserConf(me){
	
	API.getUserSetting({
		notShowLoading: true
	}).then( res => {
		console.log('加载配置完成---------用户:',res);
		if(!res){
			console.log('----------------输出错误信息----------用户配置错误')
			return false;
		}
		// res.noticeStatus = 0
		// 确认小打卡配置信息
		me.setData({
			noticeBtnStatus :  res.noticeStatus === 0,
			// clockStatus : res.clockStatus === 1
		})
		
		// 默认小打卡是关闭状态
		// wx.setStorageSync('clockStatus', res.clockStatus ? res.clockStatus : 0);
		
	}).catch( err => {
		console.log('加载用户配置失败---------------------------------用户配置错误')
	})
}

const me = {
    /**
     * 初始化
     */
    init(){
        $vm = getApp()
		_GData = $vm.globalData
		
		me._getStar.call(this)
        me._eventHandle.call(this)
    },
    _getStar(){
        let self = this
		let selectConstellation = _GData.selectConstellation
        if (selectConstellation && !selectConstellation.isFirst) {
			self.setData({
				myConstellation: selectConstellation,
				selectBack: false,
				showChoice: false,
				'navConf.isIcon' : true
			})
		}else{
            self.setData({
                showChoice: true,
                'navConf.isIcon' : true
            })
		}
	},
    /**
     * 获取星座数据
     */
    _getContent(){
        let self = this
		let selectConstellation = _GData.selectConstellation
		
        if (selectConstellation && !selectConstellation.isFirst) {
			self.setData({
				myConstellation: selectConstellation,
				selectBack: false,
				showChoice: false,
				'navConf.isIcon' : true
			})
            self.onShowingHome()
        } else {
            self.setData({
                showChoice: true,
                'navConf.isIcon' : true
            })
        }
    },

    /**
     * 处理登录加载的事件
     */
    _eventHandle(){
        let self = this
        let options = this.options
		mta.Page.init()
		
		// 重置登录信息
        Storage.homeLogin = false
        // 上报状态
        Storage.forMore = false
        // 设置系统信息
		getSystemInfo(this);
		// 获取乐摇摇推广信息
		getLeYaoyao(self,options)
		
		if(Storage.loadUserConfRemoveId){
			bus.remove(Storage.loadUserConfRemoveId)
		}

		// 注册监听事件
		Storage.loadUserConfRemoveId = bus.on('loadUserConf',() => {
			console.log('用户信息上报完成')
			if(Storage.forMore){
				// 加载用户配置
				getUserConf(self)
			}
		},'home')
		
		// 用于解析用户来源
		parseForm(self,options)

		setTimeout(() => {
			if(!self.data.isLogin){
				bus.emit('no-login-app', {} , 'app')
				self.setData({
					isLogin : true
				})
			}
		},5000)

		let handle = () => {
			console.log('--------------------------登录触发')
			// 登录状态
			Storage.homeLogin = true

			// 加载用户配置的依赖
			Storage.forMore = true
			// 触发加载用户配置函数
			bus.emit('loadUserConf',{},'home')

			_GData.userInfo = wx.getStorageSync('userInfo') || {}

			// 获取选中星座的数据
			me._getContent.call(this)

			console.log('用户信息======================：',Storage.userInfo)
			self.setData({
				'navConf.iconPath' : Storage.userInfo.avatarUrl
			})
			
			setTimeout(() => {
				self.setData({
					isLogin : true
				})
			},1000)

			// 获取配置信息
			getConfing(self);

			// 保存头像信息
			wx.setStorageSync('icon_Path', Storage.userInfo.avatarUrl)
		}
		
		// 移除事件
		if(Storage.homeRemoveId){
			bus.remove(Storage.homeRemoveId)
		}
		if(Storage.homeLoginRemoveId){
			bus.remove(Storage.homeLoginRemoveId)
		}
		Storage.homeLoginRemoveId = bus.on('login-success', handle , 'login-com')
		Storage.homeRemoveId = bus.on('login-success', handle , 'home')

		// 如果已经存在用户信息触发登录标识
		if(Storage.userInfo){
            // 已经触发过登录不在触发
			if(Storage.homeLogin){
				return
			}
			bus.emit('login-success', {}, 'home')
		}
    }
}

const methods = function(){
    return {
        /**
         * 获取用户信息，处理登录之后的逻辑
         * @param {*} self
         * @param {*} selectConstellation
         * @param {*} options
         */
        getUserInfo(self,selectConstellation,options){
            bus.on('login-success-app',(res) => {
                // 获取选中星座的数据
                getContent(self,selectConstellation)
            },'app')
        },
        /**
         * 初始化页面
         * @param {*} options
         */
        onLoad(options){
		    console.log('onLoad-------------------------------参数：',options)
            me.init.call(this)
        },
        /**
         * 显示方案
         */
        onShow(opts){
            // 触发加载用户配置函数
            bus.emit('loadUserConf',{},'home')
            getStarNum(this)
            if(Storage.userInfo){
                this.setData({
                    'navConf.iconPath' : Storage.userInfo.avatarUrl || ''
                })
            }
		},
		/**
		 * 选择星座
		 * @param {*} e
		 */
		choiceStar(e){
			const self = this
			const {item :selectConstellation} = e.currentTarget.dataset
			console.log(selectConstellation)
			mta.Event.stat('ico_home_select', { 'constellation': selectConstellation.name })
			_GData.selectConstellation = selectConstellation
			wx.setStorage({
				key: 'selectConstellation',
				data: selectConstellation
			})
			self.setData({
				myConstellation: selectConstellation,
				// selectBack: false,
				showChoice: false,
				'navConf.isIcon' : true,
				'selectStatus.current': selectConstellation.id - 1,
				'selectStatus.selected': true
			})
			self.onShowingHome()
		},
		/**
		 * 选择星座获取参数进行格式化
		 */
		onShowingHome: function () {
			const self = this
			
			$vm.api.choice({ notShowLoading : true, constellationId: _GData.selectConstellation.id}).then(res=>{
				console.log('choice运势数据',res)
				if(res !=''){
					Storage.lucky = res
					res.healthy = res.summaryPercentage + 30
					if(res.healthy > 100){
						res.healthy = 96
					}
					
					self.setData({
						xz : res,
						dayNotice: res.dayNotice ? res.dayNotice : ''
					})
				}
			}).catch(res=>{
				console.log('choice运势报错返回数据',res)
			})
		},
		/**
		 * 前往选择星座页面
		 */
		goChoiceStar(){
			mta.Event.stat("ico_home_unselect", {})
			wx.setStorage({
				key: 'selectConstellation',
				data: null,
			})
			_GData.selectConstellation = null
			this.setData({
				selectBack: true,
				showChoice: true,
				'navConf.isIcon' : false,
				'selectStatus.current': -1,
				'selectStatus.selected': false
			})
		},
		goBanner(){
			mta.Event.stat("ico_home_to_banner", {})
			wx.navigateTo({
				url: '/pages/banner/banner'
			})
		},
        /**
         * 上报formId
         * @param {*} e
         */
        reportFormId(e){
			console.log(e.detail)
            let formid = e.detail.formId
            API.getX610({ notShowLoading: true, formid: formid })
        },
        /**
         * 前往更多运势
         */
        goLuck(){
            
        }
    }
}

module.exports = methods()
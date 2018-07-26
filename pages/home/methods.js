const bus = require('../../event')
const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis')
const Storage = require('../../utils/storage')
let $vm = null
let _GData = null

const me = {
    /**
     * 初始化
     */
    init(){
        $vm = getApp()
        _GData = $vm.globalData
        console.log(this)
        me._getContent.call(this)
    },
    
    /**
     * 获取星座数据
     */
    _getContent(){
        let selectConstellation = _GData.selectConstellation
        console.log(selectConstellation)

        let self = this
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
    },

    /**
     * 处理登录加载的事件
     */
    _eventHandle(){
        let self = this
		mta.Page.init()
		
		console.log('是否重新加载------------------------------：')
		// 重置登录信息
		Storage.homeLogin = false
		getSystemInfo(this);
		Storage.forMore = false

		// 获取乐摇摇推广信息
		getLeYaoyao(self,options)
		
		if(Storage.loadUserConfRemoveId){
			bus.remove(Storage.loadUserConfRemoveId)
		}

		// 注册监听事件
		Storage.loadUserConfRemoveId = bus.on('loadUserConf',() => {
			console.log('是否已经上传用户信息：',Storage.forMore)
			if(Storage.forMore){
				// 加载用户配置
				getUserConf(self)
				getStarNum(self)
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

			$vm = getApp()
			_GData = $vm.globalData
			
			// 登录状态
			Storage.homeLogin = true

			// 加载用户配置的依赖
			Storage.forMore = true
			// 触发加载用户配置函数
			bus.emit('loadUserConf',{},'home')

			_GData.userInfo = wx.getStorageSync('userInfo') || {}

			// 获取选中星座的数据
			getContent(self,_GData.selectConstellation)

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
		
		// 是否是首次注册
		// if(!Storage.firstHome){
		// 	Storage.firstHome = true
		// }
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
            console.log('-------------------------------------',this)
        },
        /**
         * 上报formId
         * @param {*} e
         */
        reportFormId(e){
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
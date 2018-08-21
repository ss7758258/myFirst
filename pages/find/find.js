
const bus = require('../../event')
const c = require('../../config')
const confing = require('../../conf')
const API = require('../../utils/api')
const tab = require('../../template/tabbar/tabbar')
const Storage = require('../../utils/storage')
const mta = require('../../utils/mta_analysis')

Page({

    data: {
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
		// 多少人在玩
		listNum : [{},{}],
		// 导航的高度
        height : 64,
        nav : 64,
        // banner开关
        isBanner : false,
        // 显示弹窗
        showDialog : false,
        // 控制星座配对人数显示
        showPair : false,
        // 控制banner人数显示
		showBanner : false,
        more_startext : '0颗待领'
    },

    onLoad: function(options) {
        let self = this
        wx.hideTabBar({})
        console.log('onLoad-------------------------------参数：',options)
        // 初始化tab
        tab.initTab(this,1)
        let c = tab.getHeight()
        this.setData({
            height : c.nav + c.tab
        })
        if(Storage.loadFindRemoveId){
			bus.remove(Storage.loadFindRemoveId)
		}

		// 注册监听事件
		Storage.loadFindRemoveId = bus.on('loadUserConf',() => {
			console.log('用户信息上报完成')
            self._getListNum()
            self._getStarNum()
        },'find')
        
        const handle = function(){
            // 触发加载用户配置函数
            bus.emit('loadUserConf',{},'find')
        }

        // 移除事件
		if(Storage.findRemoveId){
			bus.remove(Storage.findRemoveId)
		}
		if(Storage.findLoginRemoveId){
			bus.remove(Storage.findLoginRemoveId)
		}
		Storage.findLoginRemoveId = bus.on('login-success', handle , 'login-com')
		Storage.findRemoveId = bus.on('login-success', handle , 'find')

		// 如果已经存在用户信息触发登录标识
		if(Storage.userInfo){
            // 已经触发过登录不在触发
			if(Storage.findLogin){
				return
			}
			bus.emit('login-success', {}, 'find')
		}
    },

    onShow: function() {
        tab.switchTab(1,'',this)
        tab.show()
        this._getConfing()
        // 触发加载用户配置函数
        bus.emit('loadUserConf',{},'home')
    },

    onHide: function() {

    },
    // 前往banner页面
    _goBanner(){
        mta.Event.stat("ico_home_to_banner", {})
        mta.Event.stat("find_to_banner", {})
        API.setPlayer({notShowLoading:true}).then(res => {})
        wx.navigateTo({
            url: '/pages/banner/banner'
        })
        this._close()
    },
	// 前往星座配对
	_goPair(){
		wx.navigateTo({
			url:'/pages/components/pages/pair/pair'
		})
    },
    // 前往一言
    _goBrief(){
		wx.navigateTo({
			url:'/pages/onebrief/brief'
		})
    },
    // 关闭弹窗
    _close(){
        this.setData({
            showDialog : false
        })
    },
    // 未开放提示信息
    _goUn(){
        this.setData({
            showDialog : true
        })
    },
    // 获取导航栏高度
    _setHeight(e){
        console.log(e.detail)
        this.setData({
            nav : e.detail || 64
        })
    },
	// 获取当前正在玩的人数
    _getListNum(){
		let self = this
		API.getList({notShowLoading:true}).then(res => {
			console.log('输出获取的对象信息：',res)
			if(res){
				let temp = {listNum : res}
				res.forEach((v,ind) => {
					if(v.content.indexOf('0人') != -1 && v.content.indexOf('0人') === 0){
						temp[ind === 0 ? 'showPair' : 'showBanner'] = false
					}else{
						temp[ind === 0 ? 'showPair' : 'showBanner'] = true
					}
				})
				console.log(temp)
				self.setData(temp)
				console.log(self)
			}
		})
    },
    // 获取星星数量
    _getStarNum(){
        let self = this
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
    },
    // 获取banner的默认配置信息
    _getConfing(){
        let self = this
        API.globalSetting({
            notShowLoading: true
        }).then( res => {
            console.log('加载配置完成---------全局：',res);
            if(!res){
                return false;
            }
            // 变更状态
            self.setData({
                isBanner : res.bannerStatus === 1
            })
        }).catch( err => {
            console.log('')
        })
    },
    /**
     * 上报formId
     * @param {*} e
     */
    _reportFormId(e){
        console.log(e.detail.formId)
        let formid = e.detail.formId
        API.getX610({ notShowLoading: true, formid: formid })
    }
});

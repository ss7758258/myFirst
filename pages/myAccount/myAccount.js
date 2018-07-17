const $vm = getApp()
const api = $vm.api
const mta = require('../../utils/mta_analysis')
const Storage = require('../../utils/storage')

Page({
	data: {
		navConf: {
			title: '我的账户',
			state: 'root',
			isRoot: false,
			isIcon: true,
			color: 'white',
			root: '',
			isTitle: true
		},
		balance: 0, // 默认余额
		currentItem: {}, //当前选中项,默认无
		payBtnDisabled: true, // 支付按钮禁用状态，默认禁用
		starList: [], // 星星列表
		starIndex : 0, // 充值星星的下标
		btnText : '微信支付', //支付按钮文字
		bg:false //支付按钮状态
	},

	// 生命周期初始化组件
	onLoad(options) {
		mta.Page.init()
		this._getBlance()
		this._getGoodsList()
		this._getSystemInfo()
	},

	/**
	 * 根据系统和后台返回数据判定是否支持内购
	 */
	_getSystemInfo(){
		if(Storage.sys === 'ios'){
            this.setData({
				btnText : Storage.openIos === 1 ? '微信支付' : '小程序暂时不支持购买',
				bg : Storage.openIos === 1
            })
        }else{
            this.setData({
                btnText : Storage.openAndriod === 1 ? '微信支付' : '小程序暂时不支持购买',
				bg : Storage.openAndriod === 1
            })
		}
	},

	// 获取用户钱包信息
	_getBlance() {
		api.getBlance().then(res => {
			console.log('获取钱包信息：',res)
			let balance = res.balance
			this.setData({
				balance
			})
		})
	},

	// 获取商品列表
	_getGoodsList() {
		let params = { startpage: 1 }
		api.getGoods(params).then(res => {
			console.log('获取商品列表：',res)
			let starList = res.goods
			this.setData({
				'currentItem.id' : starList[0] ? starList[0].id : 1,
				'currentItem.index' : 0,
				starList
			})
		})
	},

	// 选择充值某个数量的小星星
	handleSelectOrderClick(ev) {
		const currentItem = ev.currentTarget.dataset
		this.setData({
			currentItem,
			payBtnDisabled: false
		})
	},

	/**
	 * 前往更多好玩
	 */
	goMore(){
		mta.Event.stat('pay_to_banner',{})
		wx.reportAnalytics('pay_to_banner', {
			key: '前往更多好玩',
			sys: Storage.sys || 'unknown'
		})
		wx.navigateTo({
			url : '/pages/banner/banner'
		})
	},

	// 支付操作
	handlePayClick(ev) {
		let { id,index,price } = this.data.currentItem
		
		if(!this.data.bg){
			return
		}
		wx.showLoading({
			title:'购买中...',
			mask : true
		})
		let params = { id , notShowLoading: true }
		mta.Event.stat('to_pay',{id,price})
		wx.reportAnalytics('to_pay', {
			desc: '点击支付功能',
			id: id,
			money: `${price}元`,
			sys: Storage.sys || 'unknown'
		})
		// 充值变更
		api.getRecharge(params).then(res => {
			console.log('支付后的返回：',res)
			res.payResponse.package = res.payResponse.packAge
			delete res.payResponse.packAge

			// 选项参数
			const payConfig = {
				...res.payResponse,
				success: () => {
					// 支付成功的统计
					mta.Event.stat('to_pay_success',{id,price})
					wx.reportAnalytics('to_pay_success', {
						desc: '支付成功',
						id: id,
						money: `${price}元`,
						sys: Storage.sys || 'unknown'
					})
					console.log('完成支付后：',arguments)
					// 更新余额
					this._getBlance()
					wx.hideLoading()
				},
				fail: (res) => {
					wx.hideLoading()
					mta.Event.stat('to_pay_fail',{id,price})
					wx.reportAnalytics('to_pay_fail', {
						desc: '支付失败',
						id: id,
						money: `${price}元`,
						sys: Storage.sys || 'unknown'
					})
				}
			}

			// 微信支付
			wx.requestPayment(payConfig)
		})
	}
})
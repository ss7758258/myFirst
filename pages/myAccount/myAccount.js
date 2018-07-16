const $vm = getApp()
const api = $vm.api

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
		starList: [] // 星星列表
	},

	// 生命周期初始化组件
	onLoad(options) {
		this._getBlance()
		this._getGoodsList()
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

	// 支付操作
	handlePayClick(ev) {
		let { id } = this.data.currentItem
		wx.showLoading({
			title:'购买中...',
			mask : true
		})
		let params = { id , notShowLoading: true }
		// 充值变更
		api.getRecharge(params).then(res => {
			console.log('支付后的返回：',res)
			res.payResponse.package = res.payResponse.packAge
			delete res.payResponse.packAge

			// 选项参数
			const payConfig = {
				...res.payResponse,
				success: () => {
					console.log('完成支付后：',arguments)
					// 更新余额
					this._getBlance()
					wx.hideLoading()
				},
				fail: (res) => {
					wx.hideLoading()
				}
			}

			// 微信支付
			wx.requestPayment(payConfig)
		})
	}
})
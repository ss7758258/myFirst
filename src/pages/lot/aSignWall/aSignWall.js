// pages/aSignWall/aSignWall.js
const app = getApp()
const bus = require('../../../event.js')
const mta = require('../../../utils/mta_analysis')
const { parseLot } = app.utils
const Storage = require('../../../utils/storage')
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		navConf: { //导航定义
			title: '一签墙',
			state: 'root',
			isRoot: false,
			isIcon: true,
			iconPath: '',
			root: '',
			isTitle: true,
			centerPath: '/pages/center/center'
		},
		date_list: [
			// { id: 1, date: 2018.07, day: 3, status: 1 },
			// { id: 1, date: 2018.07, day: 2, status: 3 },
		],
		pageNum: 1,//页数
		lotBox_id:[],//签列表缓存id
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		mta.Page.init()
		this.useApi()
		// bus.on('login-success-report',(res) => {
		// this.useApi()
		// },'app')
	},

	useApi() {  // 摇签数据
		let self = this;
		let pageNum = this.data.pageNum; // 页数
		console.log(pageNum)
		app.api.getX510({ pageNum: pageNum, pageSize: 10 }).then(res => {
			console.log('=======================一签盒子：', res)
			if (res.length > 0 && pageNum > 1) {  // 分页
				self.setData({
					date_list: self.data.date_list.concat(res)
				})
			} else if (res.length < 1 && pageNum > 1) { //没有更多数据了
				wx.showToast({
					title: '没有更多数据了呢！',
					icon: 'none',
					mask: true,
				})
			} else {
				self.setData({
					date_list: res
				})
			}

			self.data.date_list.forEach((value, key) => {  //对数据进行处理
				// let day = `date_list[${key}].day`;
				let date = `date_list[${key}].date`;
				let status = `date_list[${key}].status`;
				// self.setData({
				// 	[day]: value.qianDate.substring(8),
				// 	[date]: value.qianDate.substring(0, 7).split('-').join('.'),
				// 	[status]: value.status
				// })
        self.setData({
            [date]: value.qianDate.split('-').join('.'),
            [status]: value.status
        })
			})

		}).catch(err => {
			if(!Storage.isLogin){
				return
			}
			wx.showToast({
				title: '抱歉您的网络出了点问题呢',
				icon: 'none',
				mask: true,
			})
			console.log('bbbb', err)
		})
	},
	/**
	 * 跳转详情页
	 * @param {*} e
	 */
	openDetail(e){ 
		//摇一摇一签盒动画判断
		if (e.currentTarget.dataset.item) {
			let lot = e.currentTarget.dataset.item
			Storage.lotDetail = parseLot(lot)
			
			let clicks = wx.getStorageSync('click_list') || [];
			let timers = wx.getStorageSync('timer_list') || [];
			
			// let lot = e.detail.target.dataset.item
			let t = 86400000000000,dt = new Date(),i = -1;//86400000
			
			for(let ind = timers.length - 1 ; ind >= 0 ; ind--){
				// console.log(dt.getTime() - timers[ind] > t)
				// console.log('日期数据：',dt.getTime() , timers[ind] )
				if(dt.getTime() - timers[ind] > t){
					i = ind;
					break
				}
			}

			if(i !== -1){
				// 删除已经过期的数据id
				timers = timers.slice(i + 1,timers.length)
				console.log('下标：',i)
				clicks = clicks.slice(i + 1,clicks.length)
				wx.setStorageSync('timer_list',timers)
				wx.setStorageSync('click_list', clicks)
			}

			console.log(clicks)
			// 不存在当前签的情况下
			if(clicks.indexOf(lot.id) === -1){
				let date = new Date();
				// 将签的ID放入列表
				clicks.push(lot.id)
				timers.push(date.getTime())
				wx.setStorageSync('click_list', clicks)
				wx.setStorageSync('timer_list',timers)
				// let temp_obj = {}
				// 将点击的红点消除
				// temp_obj['lotList.list[' + lot.index + '].isClick'] = false;
				// _self.setData(temp_obj)
			}

			mta.Event.stat("ico_list_detail", {})
			wx.navigateTo({
				url: '/pages/lot/lotdetail/lotdetail?fromSource=lotlist&from=list&lotId=' + lot.id
			})
		
		}
 },
	reachBottom(e) {

		this.setData({
			pageNum: ++this.data.pageNum
		})
		console.log('嗯哼', this.data.pageNum)
		this.useApi()
	}

})
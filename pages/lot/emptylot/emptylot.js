// pages/lot/emptylot/emptylot.js

var mta = require('../../../utils/mta_analysis.js')
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		navConf: {
			title: '摇签',
			state: 'root',
			isRoot: false,
			isIcon: true,
			iconPath: '',
			root: '',
			isTitle: true
		},
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		mta.Page.init()
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function () {
		var shareImg = '/assets/images/share_tong.jpg'
		var shareMsg = '要想日子过的好，每日一签少不了。'
		var sharepath = '/pages/lot/shake/shake?from=share&where=empty'
		return {
			title: shareMsg,
			imageUrl: shareImg,
			path: sharepath,
			success: function (res) {
				// 转发成功
			},
			fail: function (res) {
				// 转发失败
			}
		}
	},
	onclickqian: function (e) {
		let formid = e.detail.formId
		mta.Event.stat("ico_emptylot", { "formid": formid, "topage": "签列表" })
		wx.navigateTo({
			url: '/pages/lot/aSignWall/aSignWall',
		})
	}
})
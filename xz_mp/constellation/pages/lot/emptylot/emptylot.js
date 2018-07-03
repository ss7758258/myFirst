// pages/lot/emptylot/emptylot.js

const mta = require('../../../utils/mta_analysis.js')
Page({

  // 页面的初始数据
  data: {
    navConf: {
      title: '摇一摇抽签',
      state: 'root',
      isRoot: false,
      isIcon: true,
      iconPath: '',
      root: '',
      isTitle: true
      // root : '/pages/home/home'
    }
  },

  // 监听页面加载
  onLoad(options) {
    mta.Page.init()
    wx.setNavigationBarTitle({
      title: "title"
    })
  },

  // 用户点击右上角分享
  onShareAppMessage() {
    const shareImg = '/assets/images/share_tong.jpg'
    const shareMsg = '要想日子过的好，每日一签少不了。'
    const sharepath = '/pages/lot/shakelot/shake?from=share&where=empty'
    return {
      title: shareMsg,
      imageUrl: shareImg,
      path: sharepath
    }
  },

  // 点击查看拆签历史
  handleTurnPageClick(ev) {
    let formid = ev.detail.formId
    mta.Event.stat("ico_emptylot", {
      "formid": formid,
      "topage": "签列表"
    })
    wx.navigateTo({
      url: '/pages/lot/lotlist/lotlist?formid=' + formid,
    })
  },

  // 返回
  handleBackClick() {
    wx.navigateBack({
      delta: 1,
      fail: () => {
        wx.showToast({
          title: '回退失败'
        })
      }
    })
  }
})
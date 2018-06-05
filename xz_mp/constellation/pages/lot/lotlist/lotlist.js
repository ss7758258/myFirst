// pages/lot/lotlist/lotlist.js
const $vm = getApp()
const _GData = $vm.globalData
var mta = require('../../../utils/mta_analysis.js')
let imgs = require('../lotdetail/imgs')
var pageNum = 1
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isMore: false,
    noList: true,
    navConf : {
			title : '一签盒',
			state : 'root',
			isRoot : false,
			isIcon : true,
			iconPath : '',
      root : '',
      isTitle : true
      // root : '/pages/home/home'
    },
    imgs
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    mta.Page.init()
    const _self = this
    $vm.api.getX510({ pageNum: pageNum, pageSize: 10 })
      .then(res => {
        console.log(res)
        var list = []
        if (res.length < 10 || !res) {
          _self.setData({
            isMore: false
          })
        } else {
          _self.setData({
            isMore: true
          })
        }
        if (res.length > 0) {
          _self.setData({
            noList: false
          })
        }
        for (var i = 0; i < res.length; i++) {
          let dd = res[i]
          list.push({
            index: i,
            time: dd.qianDate,
            status: dd.status,
            id: dd.id
          })
        }
        _self.setData({
          'lotList.list': list,
          'lotList.count': res.length,
        })
      })

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () { 
    mta.Event.stat("ico_list_share", {})
    var shareImg = '/assets/images/share_tong.jpg'
    var shareMsg = '要想日子过的好，每日一签少不了。'
    var sharepath = '/pages/lot/shakelot/shake?from=share&where=list'
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
  moreLot: function (e) {
    const _self = this
    if (!_self.data.isMore) {
      return
    }

    pageNum++
    let formid = e.detail.formId

    mta.Event.stat("ico_list_more", {})

    var list = _self.data.lotList.list
    $vm.api.getX510({ pageNum: pageNum, pageSize: 10 })
      .then(res => {
        console.log(res)
        if (res.length < 10) {
          _self.setData({
            isMore: false
          })
        } else {
          _self.setData({
            isMore: true
          })
        }
        for (var i = 0; i < res.length; i++) {
          let dd = res[i]
          list.push({
            index: i,
            time: dd.qianDate,
            status: dd.status,
            id: dd.id
          })
        }
        _self.setData({
          'lotList.list': list,
          'lotList.count': res.length,
        })
      })
  },
  onItemClick: function (e) {

    const _self = this
    let lot = e.detail.target.dataset.item
    // var id = e.currentTarget.dataset.id
    // let lot = _self.data.lotList.list[index]
    let formid = e.detail.formId

    mta.Event.stat("ico_list_detail", {})
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    wx.navigateTo({
      url: '/pages/lot/lotdetail/lotdetail?formid=' + formid + '&from=list&lotId=' + lot.id,
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
  }
})
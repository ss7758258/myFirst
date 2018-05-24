// pages/checklogin/checklogin.js
const $vm = getApp()
const _GData = $vm.globalData
const api = $vm.api
var mta = require('../../utils/mta_analysis.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isClicked: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    mta.Page.init()
    const _self = this
    let qId = options.lotId
    let fromwhere = options.from
    if (fromwhere) {
      _self.setData({
        pageFrom: fromwhere,
        qId: qId
      })
    }
    let to = options.to
    if (to) {
      _self.setData({
        toPage: to,
      })
    }

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  bindGetUserInfo: function (e) {
    const _self = this
    const _SData = this.data
    if (e.detail.userInfo) {
      wx.setStorage({
        key: 'userInfo',
        data: e.detail.userInfo,
      })
      this.setData({
        hasAuthorize: true
      })
      _GData.userInfo = e.detail.userInfo
      $vm.api.getSelectx100({
        nickName: e.detail.userInfo.nickName,
        headImage: e.detail.userInfo.avatarUrl,
        notShowLoading: true,
      }).then(res => {
        if (_SData.pageFrom == 'shake') {
          wx.redirectTo({
            url: '/pages/lot/shakelot/shake?from=detail',
          })
        } else if (_SData.pageFrom == 'share' && _SData.qId) {
          wx.redirectTo({
            url: '/pages/lot/lotdetail/lotdetail?from=' + _SData.pageFrom + '&lotId=' + _SData.qId,
          })
        } else {
          wx.redirectTo({
            url: '/pages/home/home?from=' + _SData.pageFrom + '&to=' + _SData.toPage,
          })
        }

      })
    }

  },
  //点击重试按钮
  checkLogin: function (e) {
    var that = this
    if (that.data.isClicked) {
      return;
    }
    that.setData({
      isClicked: true
    })

    wx.openSetting({
      success: (res) => {
        if (res.authSetting["scope.userInfo"]) {////如果用户重新同意了授权登录
          wx.getUserInfo({
            success: function (res) {
              _GData.userInfo = res.userInfo;

              $vm.api.getSelectx100({
                nickName: res.userInfo.nickName,
                headImage: res.userInfo.avatarUrl,
                notShowLoading: true,
              }).then(res => {

                wx.redirectTo({
                  url: '/pages/home/home',
                })
              })

            }, fail: (res) => {
              console.log("拒接登录01");
              wx.redirectTo({
                url: '/pages/checklogin/checklogin'
              })
            }
          })
        } else {
          //用户未授权
          // wx.redirectTo({
          //   url: '/pages/checklogin/checklogin'
          // })
        }
      }
    })


  },
})
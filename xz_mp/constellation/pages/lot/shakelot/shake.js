// pages/lot/shakelot/shake.js

const $vm = getApp()
const _GData = $vm.globalData
const { parseLot } = $vm.utils
const getUserInfo = $vm.utils.wxPromisify(wx.getUserInfo)
var mta = require('../../../utils/mta_analysis.js')
let imgs = require('./imgs.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isLoading: false,
    hasReturn: false,
    hasAuthorize: true,
    isFromShare: false,
    isOther: false,
    //摇签状态 
    shakeLotSpeed: false,
    potPath: false,
    imgs: imgs
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    mta.Page.init()
    let pageFrom = options.from
    if (pageFrom == 'share') {
      this.setData({
        isFromShare: true,
      })
      if (options.where = 'list') {
        mta.Event.stat("ico_in_from_list", {})
      } else if (options.where = 'detail') {
        mta.Event.stat("ico_in_from_detail", {})
      } else if (options.where = 'shake') {
        if (options.hotapp == 1) {
          mta.Event.stat("ico_in_from_shake_qrcode", {})
        }else{
          mta.Event.stat("ico_in_from_shake", {})
        }
      }
      
    }
    console.log(options)

    const _self = this
    const _SData = this.data
    if (!_GData.userInfo) {
      // wx.getSetting({
      //   success: function (res) {
      //     if (res.authSetting['scope.userInfo']) {
      //       _self.setData({
      //         hasAuthorize: true
      //       })
            getUserInfo()
              .then(res => {
                $vm.api.getSelectx100({
                  nickName: res.userInfo.nickName,
                  headImage: res.userInfo.avatarUrl,
                  notShowLoading: true,
                }).then(res => {
                  if (res.userInfo) {
                    wx.setStorage({
                      key: 'userInfo',
                      data: res.userInfo,
                    })
                    _self.setData({
                      hasAuthorize: true
                    })
                    _GData.userInfo = res.userInfo
                    $vm.api.getSelectx100({
                      nickName: res.userInfo.nickName,
                      headImage: res.userInfo.avatarUrl,
                      notShowLoading: true,
                    }).then(res => {

                    })
                  }
                })
              })
              .catch(err => {
                console.log(err)
              })
      //     } else {
      //       _self.setData({
      //         hasAuthorize: false
      //       })
      //       wx.showToast({
      //         title: '请先同意授权',
      //         icon: 'none',
      //         mask: true,
      //       })
      //     }
      //   },
      //   fail: function (res) { },
      //   complete: function (res) { },
      // // })


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
    this.shakeFun()
    this.setData({
      hasReturn: false,
    })
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
    wx.stopAccelerometer({

    })
    this.setData({
      hasReturn: true,
    })
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
    wx.stopAccelerometer({

    })
    this.setData({
      hasReturn: true,
    })
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

    var shareImg = '/assets/images/share_tong.jpg'
    var shareMsg = '要想日子过的好，每日一签少不了。'
    var sharepath = '/pages/lot/shakelot/shake?from=share&where=shake'
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
  drawLots: function () {

    mta.Event.stat("ico_shake_shake", {})
    const _self = this
    if (_self.data.shakeLotSpeed) {
      return
    }
    if (_self.data.hasReturn || _self.data.isLoading || (!_self.data.hasAuthorize)) {
      return
    }
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = '/assets/shake.mp3'
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    })

    // 加快 摇动速度
    this.setData({

      shakeLotSpeed: true,
      potPath: true,
      isLoading: true
    })


    $vm.api.getX504({ notShowLoading: true, })
      .then(res => {
        if (_self.data.hasReturn) {
          return
        }
        console.log(res)
        this.setData({

          isLoading: false
        })
        if (!res) {
          wx.navigateTo({
            url: '/pages/lot/lotlist/lotlist'
          })
          return
        }
        res.isMyQian = 1
        res.alreadyOpen = 1
        var lotDetail = parseLot(res)

        _GData.lotDetail = lotDetail

        if (res.status === 0) {
          setTimeout(() => {
            if (_self.data.hasReturn) {
              return
            }
            // 摇出一个签
            this.setData({

              shakeLotSpeed: false
            })


            wx.redirectTo({
              url: '/pages/lot/lotdetail/lotdetail?sound=1',
            })

          }, 1500)
        } else if (res.status == 1) {//没有签了
          setTimeout(() => {
            if (_self.data.hasReturn) {
              return
            }
            wx.redirectTo({
              url: '/pages/lot/emptylot/emptylot',
            })

          }, 1000)
        }
      })

  },
  shakeFun: function () { // 摇一摇方法封装
    const _self = this
    var numX = 0 //x轴
    var numY = 0 // y轴
    var numZ = 0 // z轴
    var stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
    var positivenum = 0 //正数 摇一摇总数

    wx.onAccelerometerChange(function (res) {  //小程序api 加速度计
      if (_self.data.hasReturn || _self.data.isLoading) {
        return
      }

      if (numX < res.x && numY < res.y) {  //个人看法，一次正数算摇一次，还有更复杂的
        positivenum++
        setTimeout(() => { positivenum = 0 }, 2000) //计时两秒内没有摇到指定次数，重新计算
      }
      if (numZ < res.z && numY < res.y) { //可以上下摇，上面的是左右摇
        positivenum++
        setTimeout(() => { positivenum = 0 }, 2000) //计时两秒内没有摇到指定次数，重新计算
      }
      if (positivenum == 2 && stsw) { //是否摇了指定的次数，执行成功后的操作
        stsw = false

        _self.drawLots()
        console.log('摇一摇成功')
        wx.stopAccelerometer({

        })
        setTimeout(() => {
          positivenum = 0 // 摇一摇总数，重新0开始，计算
          stsw = true
        }, 2000)
      }
    })
  },
  // 显示我的签列表
  showLotList: function (e) {
    let formid = e.detail.formId

    mta.Event.stat("ico_shake_to_list", {})
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    wx.navigateTo({
      url: '/pages/lot/lotlist/lotlist?formid=' + formid
    })
  },
  //分享的返回主页
  onclickHome: function (e) {
    let formid = e.detail.formId

    mta.Event.stat("ico_shake_home", {})
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    wx.reLaunch({
      url: '/pages/home/home',
    })
  },
  bindGetUserInfo: function (e) {
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

      })
    }

  },

})
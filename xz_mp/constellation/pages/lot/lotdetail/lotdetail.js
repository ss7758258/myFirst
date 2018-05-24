// pages/lot/lotdetail/lotdetail.js
const $vm = getApp()
const _GData = $vm.globalData
const { canvasTextAutoLine, parseLot } = $vm.utils
var mta = require('../../../utils/mta_analysis.js');
const imgs = require('./imgs.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    isFromShare: false,
    huan: false,//拆签成功
    showCanvas: false,
    imgs: imgs,

    lotDetail: {
      qianOpenSize: 3,
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    mta.Page.init()
    console.log(options)
    wx.hideShareMenu({
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
    if (options.sound) {
      const innerAudioContext = wx.createInnerAudioContext()
      innerAudioContext.autoplay = true
      innerAudioContext.src = '/assets/incoming.m4a'
      innerAudioContext.onPlay(() => {
        console.log('开始播放')
      })
    }

    const _self = this

    let qId = options.lotId
    let pageFrom = options.from
    if (pageFrom == 'share' || pageFrom == 'list' || pageFrom == 'form') {
      if (pageFrom == 'share' || pageFrom == 'form') {
        _self.setData({
          isFromShare: true,
        })
      }
      // qId = 156;
      $vm.api.getX511({ id: qId })
        .then(res => {
          console.log(res)
          // res.qianContent = '近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜\n近朱者赤近你者甜'
          var lotDetail = parseLot(res)
          _self.setData({
            lotDetail: lotDetail,
          })
        })
        .catch(err => {
          wx.showToast({
            title: '签不存在',
          })
        })
    } else {
      _self.setData({
        lotDetail: _GData.lotDetail,
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
  onShareAppMessage: function (res) {
    if (res.from === 'menu') {
      mta.Event.stat("ico_shake_right_share", {})
    }
    const _self = this
    const SData = this.data
    var shareImg = '/assets/images/share_qian.jpg'
    var shareMsg = '要想生活过的好，每日一签少不了'
    var sharepath = '/pages/lot/lotdetail/lotdetail?from=share&lotId=' + SData.lotDetail.id
    if (!SData.lotDetail.lotNotCompleted) {
      shareImg = '/assets/images/share_tong.jpg'
      shareMsg = '要想日子过的好，每日一签少不了。'
      sharepath = '/pages/lot/shakelot/shake?from=share&where=detail'
    }
    console.log("shareImg-qId===" + shareImg)
    console.log("onShareAppMessage-qId===" + SData.lotDetail.id)
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
  //拆签或者去
  chai: function (e) {
    let formid = e.detail.formId

    $vm.api.getX610({ notShowLoading: true, formid: formid })
    const _self = this
    const _Sdata = this.data

    if (_Sdata.lotDetail.hasChai) {
      $vm.api.getX504({
        notShowLoading: true,
      })
        .then(res => {
          console.log(res)
          if (!res) {
            wx.navigateTo({
              url: '/pages/lot/shakelot/shake?formid=' + formid
            })
          } else {
            if (res.status === 0) {
              wx.navigateTo({
                url: '/pages/lot/shakelot/shake?formid=' + formid
              })
            } else if (res.status == 1) { //没有签了
              wx.navigateTo({
                url: '/pages/lot/emptylot/emptylot?formid=' + formid
              })
            }
          }

        })
      return
    }

    $vm.api.getX506({ id: _Sdata.lotDetail.id, notShowLoading: true })
      .then(res => {
        console.log(res)
        var lotDetail = parseLot(res)
        if (res.status == 1) {
          mta.Event.stat("ico_chai_completed", {})
          _self.setData({
            huan: true,
          })
          setTimeout(function () {
            _self.setData({
              lotDetail: lotDetail,
            })
          }, 2000)
        } else {
          _self.setData({
            lotDetail: lotDetail,
            hasChai: true,
            showHaoren: true,
          })
        }

        setTimeout(function () {
          _self.setData({
            showHaoren: false,
          })
        }, 2800)
      })
      .catch(err => {

      })
  },
  onclickShareFriend: function (e) {
    mta.Event.stat("ico_detail_share", {})
    let formid = e.detail.formId

    $vm.api.getX610({ notShowLoading: true, formid: formid })
  },
  //保存图片
  onclickShareCircle: function (e) {
    mta.Event.stat("ico_detail_save", {})
    let formid = e.detail.formId

    $vm.api.getX610({ notShowLoading: true, formid: formid })
    const _self = this
    const _SData = _self.data
    wx.showLoading({
      title: '努力保存中...',
      mask: true
    })
    _self.setData({
      showCanvas: true,
    })

    console.log(e)
    const ctx = wx.createCanvasContext('shareCanvas')
    ctx.drawImage('/assets/images/share1Bg.png', 0, 0, 750, 750)
    // 签类型
    ctx.setTextAlign('center')    // 文字居中
    ctx.setFillStyle('#ffffff')  // 文字颜色：白色
    ctx.setFontSize(40)         // 文字字号：22px
    ctx.fillText(_SData.lotDetail.qianName, 750 / 2, 77 * 2 + 40)


    var s = _SData.lotDetail.qianContent.split('\n')

    console.log(s)
    if (s.length == 1) {
      ctx.setTextAlign('left')
      ctx.setFontSize(29)
      canvasTextAutoLine(ctx, _SData.lotDetail.qianContent, 64, 125 * 2 + 32, 40, 64)
    } else {
      ctx.setTextAlign('center')
      ctx.setFontSize(29)
      for (var i = 0; i < s.length; i++) {
        ctx.fillText(s[i], 375, 125 * 2 + (32 + 10) * i, 310 * 2)
      }
    }

    ctx.setTextAlign('left')
    ctx.setFontSize(28)
    const metrics1 = ctx.measureText(_SData.lotDetail.ownerNickName).width / 2
    ctx.fillText(_SData.lotDetail.ownerNickName, 750 - metrics1 - 64 * 2 - 32, 205 * 2 + 28, 310 * 2)
    const metrics2 = ctx.measureText(_SData.lotDetail.qianDate).width / 2

    ctx.fillText(_SData.lotDetail.qianDate, 750 - metrics2 - 64 * 2 - 32, 225 * 2 + 28, 310 * 2)

    const qrImgSize = 110
    ctx.drawImage('/assets/images/qrcodeonelot.png', 297 * 2, 306 * 2, qrImgSize, qrImgSize)
    ctx.stroke()
    ctx.draw()
    setTimeout(function () {
      wx.canvasToTempFilePath({
        canvasId: 'shareCanvas',
        success: function (res) {
          console.log(res.tempFilePath)
          wx.saveImageToPhotosAlbum({
            filePath: res.tempFilePath,
            success(res) {

              wx.showToast({
                title: '已保存到相册',
                duration: 3000
              })

            }, fail(res) {
              console.log(res)
            }, complete(res) {
              wx.hideLoading()
              _self.setData({
                showCanvas: false
              })
            }
          })
        },
        fail: function (res) {
          console.log(res)
          wx.hideLoading()
          wx.showToast({
            title: '保存失败',
          })
          _self.setData({
            showCanvas: false
          })
        }
      })
    }, 1000)
  },
  //分享的返回主页
  onclickHome: function (e) {
    mta.Event.stat("ico_shake_home", {})
    let formid = e.detail.formId
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    wx.reLaunch({
      url: '/pages/home/home',
    })
  }


})
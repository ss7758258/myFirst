// pages/onebrief/brief.js
const $vm = getApp()
const _GData = $vm.globalData
const getImageInfo = $vm.utils.wxPromisify(wx.getImageInfo)
var mta = require('../../utils/mta_analysis.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    picUserName: _GData.userInfo.nickName,
    isFromShare: false,
    prevPic: ""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    mta.Page.init()
    wx.hideShareMenu({
      success: function (res) { },
      fail: function (res) { },
      complete: function (res) { },
    })
    var fromwhere = options.from
    console.log(options)
    if (fromwhere == 'share') {
      this.setData({
        isFromShare: true
      })
    }

    const _self = this
    $vm.api.getDayx400({})
      .then(res => {
        console.log(res)
        if (res) {
          _self.setData({
            prevPic:
            res.prevPic ? "https://xingzuo-1256217146.file.myqcloud.com" + res.prevPic :
              ""
          })

        } else {
          _self.setData({
            networkError: true,
            prevPic: "/assets/images/loading.png"
          })

        }

      }).catch(err => {
        console.log(err)
      })

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
    return {
      path: '/pages/onebrief/brief?from=share&to=brief',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
  /**
   * 保存图片
   */
  saveSelect: function (e) {
    let formid = e.detail.formId
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    mta.Event.stat("ico_oneword", { "formid": formid, "business": "保存" })
    const _self = this
    const _SData = _self.data
    wx.showLoading({
      title: '努力保存中...',
      mask: true
    })
    _self.setData({
      showCanvas: true,
    })
    $vm.utils.Promise.all([
      getImageInfo({
        src: _SData.prevPic
      }),
      // getImageInfo({
      //   src: 'https://xingzuo-1256217146.file.myqcloud.com/bb733a041549436ba3177cde06ed8346_1661223abb6d4429904326d15723679a.png'
      // })
    ]).then(res => {

      console.log(res)
      const ctx = wx.createCanvasContext('shareCanvas')
      ctx.setFillStyle('white')
      ctx.fillRect(0, 0, 375, 667)
      ctx.drawImage(res[0].path, 0, 0, 375, 375.0 / res[0].width * res[0].height)

      ctx.setTextAlign('center')    // 文字居中
      ctx.setFillStyle('#333333')  // 文字颜色：黑色
      ctx.setFontSize(12)         // 文字字号：22px
      ctx.fillText(_GData.userInfo.nickName, 375 / 2, 570 / 2)
      ctx.stroke()
      const qrImgSize = 120
      ctx.drawImage('/assets/images/qrcodebrief.png', 128, 506, qrImgSize, qrImgSize)
      ctx.stroke()
      ctx.setTextAlign('center')    // 文字居中
      ctx.setFillStyle('#333333')  // 文字颜色：黑色
      ctx.setFontSize(12)         // 文字字号：22px
      ctx.fillText("长按识别二维码 查看你的每日一言", 375 / 2, 631 + 12)
      
      ctx.draw()
      setTimeout(function () {
        wx.canvasToTempFilePath({
          canvasId: 'shareCanvas',
          success: function (res) {
            console.log(res.tempFilePath)
            wx.saveImageToPhotosAlbum({
              filePath: res.tempFilePath,
              success(res) {
                wx.hideLoading()
                wx.showToast({
                  title: '已保存到相册',
                  duration: 3000
                })
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
    })
      .catch(err => {
        wx.hideLoading()
        wx.showToast({
          icon: 'none',
          title: '加载失败了，请检查网络',
        })
      })
  },
  onclickHome: function (e) {
    let formid = e.detail.formId
    $vm.api.getX610({ notShowLoading: true, formid: formid })
    wx.reLaunch({
      url: '/pages/home/home',
    })
  }
})
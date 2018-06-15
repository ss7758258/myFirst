// pages/onebrief/brief.js
const $vm = getApp()
const _GData = $vm.globalData
const api = $vm.api
const getImageInfo = $vm.utils.wxPromisify(wx.getImageInfo)
const mta = require('../../utils/mta_analysis.js')

Page({
  data: {
    picUserName: '',
    isFromShare: false,
    prevPic: "",
    isShow: false,
    navConf: {
      title: '一言',
      state: 'root',
      isRoot: false,
      isIcon: true,
      iconPath: '',
      root: '',
      isTitle: true
      // root : '/pages/home/home'
    }
  },

  onLoad(options) {
    this._showImg()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: '/pages/onebrief/brief?from=share&to=brief'
    }
  },
  _showImg() {
    wx.showLoading({ title: '加载中...' })
    mta.Page.init()
    let env = 'dev'
    api.getDayx400({
      notShowLoading: true
    }).then(res => {
      if (res) {
        this.setData({
          prevPic: res.prevPic ? "https://xingzuo-1256217146.file.myqcloud.com" + (env === 'dev' ? '' : '/prod') + res.prevPic : ""
        })
      } else {
        this.setData({
          networkError: true,
          prevPic: "/assets/images/loading.png"
        })

      }
    }).catch(err => {
      wx.hideLoading()
      wx.showToast({
        icon: 'none',
        title: '加载失败了，请小主稍后再试',
      })
    })
  },

  // 保存图片
  saveSelect: function (e) {
    let formid = e.detail.formId
    api.getX610({
      notShowLoading: true,
      formid: formid
    })
    mta.Event.stat("ico_brief_save", {})
    const _self = this
    const _SData = _self.data
    wx.showLoading({
      title: '图片生成中...',
      mask: true
    })
    this.setData({
      showCanvas: true,
    })
    $vm.utils.Promise.all([
        getImageInfo({
          src: _SData.prevPic
        })
      ]).then(res => {

        const ctx = wx.createCanvasContext('shareCanvas')
        const qrImgSize = 100 //二维码尺寸
        ctx.setFillStyle('white')
        ctx.fillRect(0, 0, 375, 667)
        ctx.drawImage(res[0].path, 0, 0, 375, 375.0 / res[0].width * res[0].height)

        ctx.setTextAlign('center') // 文字居中
        ctx.setFillStyle('#333333') // 文字颜色：黑色
        ctx.setFontSize(12) // 文字字号：22px
        ctx.fillText(_GData.userInfo.nickName, 375 / 2, 570 / 2)
        ctx.stroke()
        ctx.drawImage('/assets/images/qrcodebrief.png', (375 - qrImgSize) / 2, 518, qrImgSize, qrImgSize)
        ctx.stroke()
        ctx.setTextAlign('center') // 文字居中
        ctx.setFillStyle('#333333') // 文字颜色：黑色
        ctx.setFontSize(12) // 文字字号：22px
        ctx.fillText("来自一言", 375 / 2, 631 + 12)

        ctx.draw()
        setTimeout(function () {
          wx.canvasToTempFilePath({
            canvasId: 'shareCanvas',
            success:  (res)=> {
              wx.saveImageToPhotosAlbum({
                filePath: res.tempFilePath,
                success:(res)=> {
                  wx.hideLoading()
                  wx.showModal({
                    title: '保存成功',
                    content: '图片已经保存到相册，可以分享到朋友圈了',
                    showCancel: false
                  })
                  this.setData({
                    showCanvas: false
                  })
                }
              })
            },
            fail:  (res)=> {
              wx.showToast({
                title: '保存失败',
                icon: 'none',
              })
              this.setData({
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
  onLodingListener: function (e) {
    wx.hideLoading()
    if (e.detail.height && e.detail.width) {
      this.setData({
        picUserName: _GData.userInfo.nickName,
        isShow: true // 开启图片展示
      })
    }
  },
  onclickHome: function (e) {
    mta.Event.stat("ico_brief_home", {})
    let formid = e.detail.formId
    api.getX610({
      notShowLoading: true,
      formid: formid
    })
    wx.reLaunch({
      url: '/pages/home/home',
    })
  }
})
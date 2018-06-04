// pages/today/today.js

const $vm = getApp()
const _GData = $vm.globalData
const { parseToady, canvasTextAutoLine } = $vm.utils
var mta = require('../../utils/mta_analysis.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    todayList: [],
    isFromShare: false,
    time: null,
    showCanvas: false,
    navConf : {
			title : '运势详情',
			state : 'root',
			isRoot : false,
			isIcon : true,
			iconPath : '',
            root : '',
            isTitle : true
            // root : '/pages/home/home'
		},
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
    if (fromwhere == 'share' || fromwhere == 'form') {
      this.setData({
        isFromShare: true
      })
    }

    const _self = this
    $vm.api.getMorex300({ constellationId: _GData.selectConstellation.id })
      .then(res => {
        console.log(res)

        var todayList = parseToady(res)
        _self.setData({
          time: res.createTime.substring(0, 10),

          todayList: todayList
        })
      })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      path: '/pages/today/today?from=share&to=today',
      success: function (res) {
        // 转发成功
      },
      fail: function (res) {
        // 转发失败
      }
    }
  },
  savePic: function (e) {//保存图片
    let formid = e.detail.formId
    mta.Event.stat("ico_today_save", {})
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

    const ctx = wx.createCanvasContext('shareCanvas')
    ctx.drawImage('/assets/images/share2Bg.png', 0, 0, 750, 1334)//画背景

    for (var i = 0; i < _SData.todayList.length; i++) {
      let today = _SData.todayList[i]

      ctx.drawImage(today.img, 34 * 2, 59 * 2 + (70 * 2) * i, 34 * 2, 34 * 2)
      ctx.setTextAlign('left')
      ctx.setFillStyle('#ffffff')  // 文字颜色：白色
      ctx.setFontSize(36)
      ctx.fillText(today.name, 71 * 2, 36 + 62 * 2 + (70 * 2) * i)

      ctx.setFontSize(23)
      if (i == _SData.todayList.length - 1) {
        canvasTextAutoLine(ctx, today.content, 37 * 2, 28 + 95 * 2 + (70 * 2) * i, 32, 96)
      } else {
        ctx.fillText(today.content, 37 * 2, 28 + 95 * 2 + (70 * 2) * i)
      }
      ctx.setStrokeStyle('white')
      ctx.lineWidth = 1
      ctx.moveTo(36 * 2, 121 * 2 + (70 * 2) * i)
      if (today.width == 8) {
        ctx.lineTo(36 * 2 + 100 * 2, 121 * 2 + (70 * 2) * i)
      }
      // else if (today.width == null) {
      //   ctx.lineTo(36 * 2 + 265 * 2, 121 * 2 + (70 * 2) * i)
      // }
      ctx.stroke()
      if (i < 4) {
        for (var k = 0; k < today.level; k++) {
          ctx.drawImage('/assets/images/stars1.png', 147 * 2 + 25 * 2 * k, 64 * 2 + (70 * 2) * i, 48, 48)
        }
        for (var k = today.level; k < 5; k++) {
          ctx.drawImage('/assets/images/stars2.png', 147 * 2 + 25 * 2 * k, 64 * 2 + (70 * 2) * i, 48, 48)
        }
      }
    }
    let name = _GData.userInfo.nickName
    let signName = _GData.selectConstellation.name
    let aa = name + " " + signName
    ctx.setFillStyle('#000000')  // 文字颜色：黑色
    ctx.setFontSize(32)
    const metrics1 = ctx.measureText(aa).width / 2
    ctx.fillText(aa, 40, 1200 + 32)
    let timer = new Date();
    let newDate = timer.getFullYear() + '-' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '-' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate());
    console.log('输出日期：',newDate)
    // ctx.fillText(_SData.time, 40, 625 * 2 + 32)
    ctx.fillText(newDate, 40, 625 * 2 + 32)
    ctx.setFontSize(28)
    var half = (214 * 2 - (40 + metrics1 * 2)) / 2
    var left = 214 * 2 - half
    console.log("left=" + left)
    const metrics = ctx.measureText('小哥星座').width / 2
    ctx.fillText('小哥星座', 214 * 2, 616 * 2 + 28)
    ctx.beginPath()
    ctx.setStrokeStyle('black')
    ctx.lineWidth = 2
    ctx.moveTo(left, 608 * 2)
    ctx.lineTo(left, 638 * 2)
    ctx.stroke()
    const qrImgSize = 140
    ctx.drawImage('/assets/images/qrcodetoday.png', 282 * 2, 587 * 2, qrImgSize, qrImgSize)

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

            },
            complete(res) {
              wx.hideLoading()
              _self.setData({
                showCanvas: false
              })
            },
            fail(res) {
              wx.showToast({
                title: '取消保存',
                duration: 3000
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
  onclickHome: function () {
    wx.reLaunch({
      url: '/pages/home/home',
    })
  }
})
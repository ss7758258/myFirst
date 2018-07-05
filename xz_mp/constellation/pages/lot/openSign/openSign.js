// pages/lot/openSign/openSign.js
const $vm = getApp()
const { canvasTextAutoLine, parseLot } = $vm.utils
Page({
  data: {
    lotdetail:{
      hasChai:true,
      id:646857,
      isOther:false,
      lotNotCompleted:false,
      lotTitleHint:"你的好友已帮你完成拆签",
  ownerHeadImage:"https://wx.qlogo.cn/mmopen/vi_32/4G6xECdj7HZjOhwiaE97ccpEmdKKffBjsv7MFD4GFFggl4icElb5cIjokANFnCQ4fThjSamlET3IXQCyh0rVyc9Q/132",
      ownerNickName:"小白是鱼骨头啊丶",
      qianContent:"风雨交加过后也会有彩虹\n春暖花开过后也会有凋零\n每个人生命里的每一刻钟\n都会是无比珍贵的永恒",
      qianDate:"2018-06-29",
      qianName:"理性签",
      qianOpenSize:3,
      showChai:false,
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // this.canvasCard()
  },

  canvasCard(){
    wx.showLoading({
			title: '图片生成中...',
			mask: true
		})
    let _self=this
    let lotdetail=this.data.lotdetail
    wx.getImageInfo({ //将头像转路径
      src: $vm.globalData.userInfo.avatarUrl, //图片的路径，可以是相对路径，临时文件路径，存储文件路径，网络图片路径,
      success: res => {
        console.log('头像本地路径',res.path)
        let face=res.path

        // 画图
          const ctx = wx.createCanvasContext('openSign')

          ctx.drawImage('/assets/img/background.png', 0, 0, 375, 535) //背景图
          ctx.drawImage('/assets/img/card.png', 0, 75, 375, 275) //拆签数据图
          ctx.drawImage('/assets/images/qrcodebrief.png', 150, 360, 75, 75) //小哥星座二维码图
          ctx.drawImage('/assets/img/text.png', 97, 445, 184, 45) //小哥星座文字图

          // 签类型
          ctx.setFillStyle('#333333')  // 文字颜色：白色
          ctx.font = "20px bold"        //文字大小为20px并加粗
          ctx.fillText(lotdetail.qianName, 30, 130)

          //用户名称
          // const mea_username = ctx.measureText(lotdetail.ownerNickName).width / 2
          ctx.setFillStyle('#333333')
          ctx.setFontSize(14)
          ctx.setTextAlign('right')
          ctx.fillText(lotdetail.ownerNickName, 345, 131)

          // 签内容
          ctx.beginPath()
          var s = lotdetail.qianContent.split('\n')
          console.log(s)
          if (s.length == 1) {
            ctx.setTextAlign('left')
            ctx.setFontSize(16)
            canvasTextAutoLine(ctx, lotdetail.qianContent, 32, 168, 40, 64)
          } else {
            ctx.setTextAlign('center')
            ctx.setFontSize(16)
            for (var i = 0; i < s.length; i++) {
              ctx.fillText(s[i], 187.5, 180 + 24 * (i))
            }
          }

          // 时间
          ctx.setTextAlign('center')
          ctx.setFontSize(12)
          let timer = new Date();
          let newDate = '一 ' + timer.getFullYear() + '.' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '.' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate()) + ' 一';
          // console.log('输出日期：', newDate)
          // 计算文本长度
          const mea_date = ctx.measureText(newDate).width / 2

          ctx.fillText(newDate, 187.5, 290)

          // 头像
          ctx.arc(187.5, 85, 25, 0, 2 * Math.PI)
          ctx.clip()
          console.log('头像路径', face)
          ctx.drawImage(face, 162.5, 60, 50, 50)
          ctx.restore()

          ctx.draw()  
          console.log('画图完成===================')

          wx.getSetting({
            success(res) {
              if (res.authSetting['scope.writePhotosAlbum']) {
                wx.canvasToTempFilePath({ //画图完成保存图片
                  canvasId: 'openSign',
                  success: res => {
                    console.log('图片临时路径', res.tempFilePath)
                    let filePath = res.tempFilePath
                    wx.hideLoading()


                    wx.saveImageToPhotosAlbum({
                      filePath: filePath,
                      success: res => {
                        console.log('本地保存路径', res)
                        wx.showModal({
                          title: '保存成功', //提示的内容,
                          content: '图片已经保存到相册，可以分享到朋友圈了',
                          mask: true, //显示透明蒙层，防止触摸穿透,
                        });
                      }, fail: res => {
                        wx.showToast({
                          title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                          icon: 'none',
                          duration: 3000
                        })
                        console.log('错误信息', res)
                      }
                    })

                  },
                  fail: res => {
                    wx.showToast({
                      title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                      icon: 'none',
                      duration: 3000
                    })
                    _self.setData({
                      showCanvas: false,
                    })
                  },
                  complete: res => {
                    wx.hideLoading()
                    _self.setData({
                      showCanvas: false
                    })
                  },
                })


              }
            }
          })

        
      },
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
  
  }
})
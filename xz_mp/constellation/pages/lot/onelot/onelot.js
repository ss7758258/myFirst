// pages/onelot/onelot.js

const $vm = getApp()
const _GData = $vm.globalData
const getImageInfo = $vm.utils.wxPromisify(wx.getImageInfo)
const { canvasTextAutoLine } = $vm.utils
Page({

	/**
	 * 页面的初始数据
	 */
  data: {
    isFromShare: false,
    showCanvas: false,
    isOther: false,
    qianOpenSize: 3,
    src: '',
    //模块显示状态
    shakeLotShow: true,
    lotListShow: false,
    myLotShow: false,
    showEmptyLot: false,
    showChai: false,

    hasChai: false,
    //摇签状态
    shakeLotStatus: false,
    shakeLotSpeed: false,
    lotNotCompleted: true,

    qId: null,

    lotTitleHint: '摇摇手机，立即抽取你的每日一签',
    lotList: {
      count: 0,
      list: []
    },


    myLot: {
      troops: []
    }

  },

  // 摇签 延迟需要根据请求速度调整
  drawLots: function () {
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    innerAudioContext.src = '/assets/shake.mp3'
    innerAudioContext.onPlay(() => {
      console.log('开始播放')
    })

    const _self = this
    // 加快 摇动速度
    this.setData({
      shakeLotSpeed: true
    })
    //状态status 的值： @4-状态-1=已拆,0=拆迁中
    $vm.api.getX504({})
      .then(res => {
        console.log(res)
        wx.setNavigationBarTitle({
          title: '拆签',
        })
        wx.stopAccelerometer({

        })
        var myLot = _self.parseLot(res)
        _self.setData({

          qianOpenSize: res.qianOpenSize,
          myLot: myLot,
          qId: res.id,
          qianName: res.qianName,
          qianContent: res.qianContent,
          ownerNickName: res.ownerNickName,
          ownerHeadImage: res.ownerHeadImage,
          qianDate: res.qianDate
        })
        if (res.status === 0) {
          setTimeout(() => {
            // 摇出一个签
            this.setData({
              shakeLotStatus: true,
              shakeLotSpeed: false
            })

            setTimeout(() => {
              // 显示签详情
              this.setData({
                shakeLotShow: false,
                myLotShow: true,
                lotListShow: false,
                showEmptyLot: false,
                lotTitleHint: '下面是你的每日一签，快找好友帮你拆签吧~',
              })
            }, 1500)
          }, 1500)
        } else if (res.status == 1) {
          setTimeout(() => {
            // 摇出一个签
            this.setData({
              lotTitleHint: '你的好友已帮你完成拆签',
              shakeLotStatus: false,
              shakeLotSpeed: false
            })
            // 显示签详情
            _self.showEmptyLot()

          }, 1500)
        }
      })

  },

  // 显示我的签列表
  showLotList: function () {
    wx.stopAccelerometer({

    })
    const _self = this
    $vm.api.getX510({ pageNum: 1, pageSize: 10 })
      .then(res => {
        console.log(res)
        var list = []
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

    this.setData({
      shakeLotShow: false,
      lotListShow: true,
      myLotShow: false,
      showEmptyLot: false,
    })
  },
  // 显示空签界面
  showEmptyLot: function () {
    this.setData({
      shakeLotShow: false,
      lotListShow: false,
      myLotShow: false,
      showEmptyLot: true,
    })
  },
	/**
	 * 生命周期函数--监听页面加载
	 */
  onLoad: function (options) {
    const _self = this
    let pageFrom = options.from
    let qId = options.qId

    if (pageFrom == 'share') {
      console.log('这里要关掉摇一摇了')

      _self.setData({
        isFromShare: true,
        shakeLotShow: false,
        myLotShow: true,
        lotListShow: false,
        showEmptyLot: false,
        showChai: true,
        lotTitleHint: '下面是你的每日一签，快找好友帮你拆签吧~',
        qId: qId,
      })
      $vm.api.getX511({ id: qId })
        .then(res => {
          console.log(res)
          wx.setNavigationBarTitle({
            title: '拆签',
          })
          var myLot = _self.parseLot(res)
          _self.setData({
            myLot: myLot,
            qianOpenSize: res.qianOpenSize,
            qianName: res.qianName,
            qianContent: res.qianContent,
            ownerNickName: res.ownerNickName,
            ownerHeadImage: res.ownerHeadImage,
            qianDate: res.qianDate

          })
          ///这个签被你开过了
          if (res.alreadyOpen > 0) {
            _self.setData({
              hasChai: true,
            })
          }
          ////是自己的签
          if (res.isMyQian == 1) {

            if (res.status == 1) {

              _self.setData({
                shakeLotShow: false,
                myLotShow: true,
                lotListShow: false,
                showEmptyLot: false,
                lotNotCompleted: false,
                showChai: false,
                isOther: false,
                lotTitleHint: '你的好友已帮你完成拆签',
              })

            } else {
              _self.setData({
                showChai: false,
                isOther: false,
                lotTitleHint: '下面是你的每日一签，快找好友帮你拆签吧~',
              })
            }

          } else {
            if (res.status == 1) {
              _self.setData({
                isOther: true,
                lotNotCompleted: false,
                lotTitleHint: '此签已完成拆签',
              })
            } else {
              _self.setData({
                isOther: true,
                lotNotCompleted: true,
                lotTitleHint: '是否能够拆签成功，全都仰仗你们了！',
              })
            }
          }
        })
        .catch(err => {
          wx.showToast({
            title: err,
          })
        })
    } else {
      if (pageFrom == 'shake') {
        _self.setData({
          isFromShare: true
        })
      }
      _self.shakeFun()
    }


  },
  shakeFun: function () { // 摇一摇方法封装
    const _self = this
    var numX = 0 //x轴
    var numY = 0 // y轴
    var numZ = 0 // z轴
    var stsw = true // 开关，保证在一定的时间内只能是一次，摇成功
    var positivenum = 0 //正数 摇一摇总数

    wx.onAccelerometerChange(function (res) {  //小程序api 加速度计
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
    const _self = this
    const SData = this.data
    var shareImg = '/assets/images/share_qian.jpg'
    var shareMsg = '兄弟姐妹们，我刚刚抽了个签，大家快来拆签吧'
    var sharepath = '/pages/onelot/onelot?from=share&qId=' + SData.qId
    if (!SData.lotNotCompleted || SData.lotListShow || SData.shakeLotShow) {
      shareImg = '/assets/images/share_tong.jpg'
      shareMsg = '我刚刚抽了个签，真准啊！你也快试试吧'
      sharepath = '/pages/onelot/onelot?from=shake'
    }
    console.log("shareImg-qId===" + shareImg)
    console.log("onShareAppMessage-qId===" + SData.qId)
    if (res.from === 'button') {
      // 来自页面内转发按钮
      console.log(res.target)
    }
    console.log(res.target)
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

  //  拆签
  handleOpenLotClick (e) {
    const _self = this
    let index = e.currentTarget.dataset.index
    let id = e.currentTarget.dataset.id
    let lot = this.data.lotList.list[index]
    let lotNotCompleted = lot.status === 0

    _self.setData({
      qId: lot.id,
      shakeLotShow: false,
      myLotShow: true,
      lotListShow: false,
      showEmptyLot: false,
      lotNotCompleted: lotNotCompleted,
    })
    $vm.api.getX511({ id })
      .then(res => {
        let myLot = _self.parseLot(res)
        _self.setData({
          myLot: myLot,
          qId: res.id,
          qianOpenSize: res.qianOpenSize,
          qianName: res.qianName,
          qianContent: res.qianContent,
          ownerNickName: res.ownerNickName,
          ownerHeadImage: res.ownerHeadImage,
          qianDate: res.qianDate
        })
        ///这个签被你开过了
        if (res.alreadyOpen > 0) {
          _self.setData({
            hasChai: true,
          })
        }
        ////是自己的签
        if (res.isMyQian == 1) {

          if (res.status == 1) {

            _self.setData({
              shakeLotShow: false,
              myLotShow: true,
              lotListShow: false,
              showEmptyLot: false,
              lotNotCompleted: false,
              showChai: false,
              isOther: false,
              lotTitleHint: '你的好友已帮你完成拆签',
            })

          } else {
            _self.setData({
              showChai: false,
              isOther: false,
              lotNotCompleted: true,
              lotTitleHint: '下面是你的每日一签，快找好友帮你拆签吧~',
            })
          }

        } else {
          if (res.status == 1) {
            _self.setData({
              isOther: true,
              lotNotCompleted: false,
              lotTitleHint: '此签已完成拆签',
            })
          } else {
            _self.setData({
              isOther: true,
              lotNotCompleted: true,
              lotTitleHint: '是否能够拆签成功，全都仰仗你们了！',
            })
          }
        }
      })



  },
  chai: function () {
    const _self = this
    const _Sdata = this.data
    if (_Sdata.hasChai) {
      _self.setData({
        //模块显示状态
        shakeLotShow: true,
        lotListShow: false,
        myLotShow: false,
        showEmptyLot: false,
        showChai: false,
        qId: null,
        showHaoren: false,
        isOther: false,
        lotTitleHint: '摇摇手机，立即抽取你的每日一签',
      })
      _self.shakeFun()
      return
    }

    $vm.api.getX506({ id: this.data.qId })
      .then(res => {
        console.log(res)
        var myLot = _self.parseLot(res)
        _self.setData({
          myLot: myLot,
          hasChai: true,
          qianOpenSize: res.qianOpenSize,
          qianName: res.qianName,
          qianContent: res.qianContent,
          // ownerNickName: res.ownerNickName,
          // ownerHeadImage: res.ownerHeadImage,
          qianDate: res.qianDate,
          showHaoren: true,
        })
        if (res.status === 1) {
          _self.onclickqian()
          _self.setData({
            isOther: true,
            lotTitleHint: '此签已完成拆签',
          })
        } else {
          _self.setData({
            isOther: true,
            lotTitleHint: '是否能够拆签成功，全都仰仗你们了！',
          })
        }
      })
      .catch(err => {

      })
  },
  parseLot: function (res) {

    var troops = []
    for (var i = 1; i < res.qianOpenSize + 1; i++) {
      if (res['friendOpenId' + i]) {
        troops.push({
          openId: res['friendOpenId' + i],
          name: res['friendOpenId' + i],
          photo: res['friendHeadImage' + i],
        })
      }
    }

    var myLot = {
      troops: troops
    }
    return myLot

  },
  onclickqian: function (res) {
    this.setData({
      shakeLotShow: false,
      myLotShow: true,
      lotListShow: false,
      showEmptyLot: false,
      lotNotCompleted: false,
    })
  },
  onclickShareCircle: function (res) {

    //保存图片
    const _self = this
    const _SData = _self.data
    wx.showLoading({
      title: '图片生成中...',
      mask: true
    })
    _self.setData({
      showCanvas: true,
    })

    console.log(res)
    const ctx = wx.createCanvasContext('shareCanvas')
    ctx.drawImage('/assets/images/share1Bg.png', 0, 0, 750, 750)
    // 签类型
    ctx.setTextAlign('center')    // 文字居中
    ctx.setFillStyle('#ffffff')  // 文字颜色：白色
    ctx.setFontSize(40)         // 文字字号：22px
    ctx.fillText(_SData.qianName, 750 / 2, 77 * 2 + 40)
    ctx.setTextAlign('left')
    ctx.setFontSize(32)
    canvasTextAutoLine(ctx, _SData.qianContent, 64, 125 * 2 + 32, 32)
    ctx.setTextAlign('left')
    ctx.setFontSize(28)
    const metrics1 = ctx.measureText(_SData.ownerNickName).width / 2
    ctx.fillText(_SData.ownerNickName, 750 - metrics1 - 64 * 2 - 32, 205 * 2 + 28, 310 * 2)
    let timer = new Date();
    let newDate = timer.getFullYear() + '-' + (timer.getMonth() + 1 > 9 ? timer.getMonth() + 1 : '0' + (timer.getMonth() + 1)) + '-' + (timer.getDate() > 9 ? timer.getDate() : '0' + timer.getDate());
    console.log('输出日期：',newDate)
    const metrics2 = ctx.measureText(newDate).width / 2

    ctx.fillText(newDate, 750 - metrics2 - 64 * 2 - 32, 225 * 2 + 28, 310 * 2)

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

							wx.hideLoading()
              wx.showModal({
								title: '保存成功',
								content: '图片已经保存到相册，可以分享到朋友圈了',
								showCancel: false
							})

            }, fail(res) {
              console.log(res)
            }, complete(res) {
              // wx.hideLoading()
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
            icon: 'none',
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
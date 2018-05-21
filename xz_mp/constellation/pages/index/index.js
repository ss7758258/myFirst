// pages/index/index.js
const $vm = getApp()
const _GData = $vm.globalData

Page({

	/**
	 * 页面的初始数据
	 */
  data: {
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    hasAuthorize: false,
    signList: [{
      name: '白羊座',
      time: '3.21-4.19',
      img: '/assets/images/aries.png'
    }, {
      name: '金牛座',
      time: '4.20-5.20',
      img: '/assets/images/taurus.png'
    }, {
      name: '双子座',
      time: '5.21-6.21',
      img: '/assets/images/gemini.png'
    }, {
      name: '巨蟹座',
      time: '6.22-7.22',
      img: '/assets/images/cancer.png'
    }, {
      name: '狮子座',
      time: '7.23-8.22',
      img: '/assets/images/leo.png'
    }, {
      name: '处女座',
      time: '8.23-9.22',
      img: '/assets/images/virgo.png'
    }, {
      name: '天秤座',
      time: '9.23-10.23',
      img: '/assets/images/libra.png'
    }, {
      name: '天蝎座',
      time: '10.24-11.22',
      img: '/assets/images/scorpio.png'
    }, {
      name: '射手座',
      time: '11.23-12.21',
      img: '/assets/images/sagittarius.png'
    }, {
      name: '摩羯座',
      time: '12.22-1.19',
      img: '/assets/images/capricornus.png'
    }, {
      name: '水瓶座',
      time: '1.20-2.18',
      img: '/assets/images/aquarius.png'
    }, {
      name: '双鱼座',
      time: '2.19-3.20',
      img: '/assets/images/pisces.png'
    }],
    selectStatus: {
      selected: false,
      current: -1,
      sign: {}
    },
    pageFrom: null,
    qId: null
  },
  selectSign: function (e) {
    const _self = this
    const _SData = this.data
    if (!_SData.hasAuthorize) {
      wx.showToast({
        title: '请先同意授权',
        icon: 'none',
        mask: true,

      })
      return;
    }
    wx.setStorage({
      key: 'selectSignIndex',
      data: e.currentTarget.id,
    })
    $vm.globalData.selectSignIndex = parseInt(e.currentTarget.id) + 1

    let cur = _SData.signList[e.currentTarget.id]
    _self.setData({
      'selectStatus.current': e.currentTarget.id,
      'selectStatus.sign': cur,
      'selectStatus.selected': true
    })

    _self.saveSelect()


  },

  saveSelect: function () {
    $vm.api.getSelectx100({
      constellationId: $vm.globalData.selectSignIndex,
      nickName: $vm.globalData.userInfo.nickName,
      headImage: $vm.globalData.userInfo.avatarUrl,
      notShowLoading: true,
    }).then(res => {

    })
    const _self = this
    const _SData = this.data
    _GData.mySign = _SData.selectStatus.sign
    wx.navigateTo({
      url: '/pages/home/home',
      success: function () {
        _self.setData({
          'selectStatus.current': -1,
          'selectStatus.sign': {},
          'selectStatus.selected': false
        })
      }
    })
  },
	/**
	 * 生命周期函数--监听页面加载
	 */
  onLoad: function (options) {
    // wx.navigateTo({
    //   url: '/pages/onelot/onelot?qId=61&from=share'
    // })
    // return


    const _self = this
    const _SData = this.data
    var fromwhere = options.from
    let qId = options.qId
    if (fromwhere == 'share') {
      console.log("qId=====" + qId)
      _self.setData({
        pageFrom: fromwhere,
        qId: qId
      })
    }

    // 查看是否授权
    wx.getSetting({
      success: function (res) {
        if (res.authSetting['scope.userInfo']) {
          _self.setData({
            hasAuthorize: true
          })
          if (fromwhere == 'share') {
            console.log("qId=" + qId)
            wx.navigateTo({
              url: '/pages/onelot/onelot?qId=' + qId + '&from=share'
            })
          }
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function (res) {
              _self.setData({
                hasAuthorize: true
              })
              // $vm.globalData.userInfo = res.userInfo
              // let selectIndex = wx.getStorageSync('selectSignIndex')
              // if (selectIndex) {
              //   $vm.globalData.selectSignIndex = parseInt(selectIndex) + 1

              //   let cur = _SData.signList[selectIndex]
              //   _self.setData({
              //     'selectStatus.current': selectIndex,
              //     'selectStatus.sign': cur,
              //     'selectStatus.selected': true
              //   })

              //   _self.saveSelect()
              // }
            }
          })
        } else {
          _self.setData({
            hasAuthorize: false
          })
          if (fromwhere == 'share') {
            wx.showToast({
              title: '请先同意授权',
              icon: 'none',
              mask: true,
            })
          }

        }
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
  onShareAppMessage: function () {

  },
  bindGetUserInfo: function (e) {

    $vm.globalData.userInfo = e.detail.userInfo
    $vm.api.getSelectx100({
      constellationId: 1,
      nickName: $vm.globalData.userInfo.nickName,
      headImage: $vm.globalData.userInfo.avatarUrl,
      notShowLoading: true,
    }).then(res => {

    })
    const _self = this
    const _SData = this.data
    _self.setData({
      hasAuthorize: true
    })
    if (_SData.pageFrom == 'share') {
      let qId = _SData.qId

      wx.navigateTo({
        url: '/pages/onelot/onelot?qId=' + qId + '&from=share'
      })
    }
  }
})
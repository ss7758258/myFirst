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
    hasAuthorize: false, //是否有授权
    isClicked: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

    mta.Page.init()
    console.log('输出参数：', options)
    let lotId = options.lotId
    let fromwhere = options.from


    if (fromwhere) {
      this.setData({
        pageFrom: fromwhere,
        lotId: lotId
      })
    }
    let to = options.to
    if (to) {
      this.setData({
        toPage: to
      })
    }
    let and = options.and
    if (and) {
      this.setData({
        and: and
      })
    }
  },

  // 获取用户信息
  handleGetUserClick(e) {

    const _SData = this.data
    const userInfo = e.detail.userInfo || wx.getStorage({ key: 'userInfo' })

    console.log("userInfo",userInfo)
    
    if (!userInfo || !Object.keys(userInfo).length) {
      return 
    }

    // this.setData({
    //   canIUse:false
    // })
    const { nickName, avatarUrl } = userInfo

    // 用户信息存入Storage
    wx.setStorage({
      key: 'userInfo',
      data: userInfo,
    })

    this.setData({
      hasAuthorize: true
    })

    _GData.userInfo = userInfo

    $vm.api.getSelectx100({
      nickName,
      constellationId: _GData.selectConstellation.id,
      headImage: avatarUrl,
      notShowLoading: true,
    }).then((res) => {
      console.log('res', res)
      let { pageFrom, and, lotId, toPage } = _SData
      let url = ''
      // switch (pageFrom) {
      //   case 'shake':
      //     url = '/pages/lot/shakelot/shake?from=detail'
      //   case 'activity':
      //     if (and === 'shake') {
      //       url = '/pages/lot/shakelot/shake?from=activity'
      //     }
      //   case 'share':
      //     if (and === 'shake') {
      //       url = '/pages/lot/shakelot/shake?from=share'
      //     } else if (lotId)(
      //       url = `/pages/lot/lotdetail/lotdetail?from={pageFrom}&lotId=${lotId}`
      //     )
      //   default:
      //     url = `/pages/home/home?from=${pageFrom}&to=${toPage}`
      // }

      // wx.redirectTo({ url })

      if (pageFrom === 'shake') {
        url = '/pages/lot/shakelot/shake?from=detail'
      } else if (pageFrom === 'activity' && and === 'shake') {
        url = '/pages/lot/shakelot/shake?from=activity'
      } else if (pageFrom === 'share' && and === 'shake') {
        url = '/pages/lot/shakelot/shake?from=share'
      } else if (pageFrom === 'share' && lotId) {
        url = '/pages/lot/lotdetail/lotdetail?from=' + pageFrom + '&lotId=' + lotId
      } else {
        url = '/pages/home/home?from=' + pageFrom + '&to=' + toPage
      }

      wx.redirectTo({ url })


    }).catch(err => {
      console.log("err",err)
    })
  },


  // 授权小程序
  handleAutorizeClick(e) {
    // if (this.data.isClicked) {
    //   return
    // }
    // this.setData({
    //   isClicked: true
    // })

    wx.openSetting({
      success: (res) => {
        if (res.authSetting["scope.userInfo"]) { //如果用户重新同意了授权登录
          wx.getUserInfo({
            success: (res) => {
              _GData.userInfo = res.userInfo
              $vm.api.getSelectx100({
                nickName: res.userInfo.nickName,
                headImage: res.userInfo.avatarUrl,
                constellationId: 1,
                notShowLoading: true
              }).then((res) => {
                wx.redirectTo({
                  url: '/pages/home/home'
                })
              })
            },
            fail: (res) => {
              wx.redirectTo({
                url: '/pages/checklogin/checklogin'
              })
            }
          })
        }
        // else {
        //   用户未授权
        //   wx.redirectTo({
        //     url: '/pages/checklogin/checklogin'
        //   })
        // }
      }
    })
  }
})
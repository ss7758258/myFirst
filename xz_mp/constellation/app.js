//app.js
var aldstat = require("./utils/ald-stat.js")
const utils = require('./utils/util.js')
const api = require('./utils/api.js')
const mta = require('./utils/mta_analysis.js')
App({



  onLaunch: function (options) {
    const _self = this
    const _SData = this.globalData
    
    _SData.userInfo = wx.getStorageSync('userInfo')
    _self.getLogin().then(res => {
      console.log(res)
      wx.setStorage({
        key: 'token',
        data: res.token
      })
    }).catch(err => {
      wx.showToast({
        title: err,
        icon: 'none'
      })
    })
    _SData.selectConstellation = wx.getStorageSync('selectConstellation')
    _SData.userInfo = wx.getStorageSync('userInfo')
    mta.App.init({
      "appID": "500613478",
      "eventID": "500614618", // 高级功能-自定义事件统计ID，配置开通后在初始化处填写
      "lauchOpts": options, //渠道分析,需在onLaunch方法传入options,如onLaunch:function(options){...}
      "statPullDownFresh": true, // 使用分析-下拉刷新次数/人数，必须先开通自定义事件，并配置了合法的eventID
      "statShareApp": true, // 使用分析-分享次数/人数，必须先开通自定义事件，并配置了合法的eventID
      "statReachBottom": true // 使用分析-页面触底次数/人数，必须先开通自定义事件，并配置了合法的eventID
    })
  },

  getLogin() {
    return new utils.Promise((resolve, reject) => {
      return utils.login().then(res => {
        api.getLogin({
          notShowLoading: true,
          code: res.code
        }).then(data => {
          resolve(data)
        }).catch(err => {
          reject(err)
        })
      })
    })
  },


  globalData: {
    selectConstellation: null,
    userInfo: null,
    lotDetail: {},
  },
  //初始化
  utils,
  api
})
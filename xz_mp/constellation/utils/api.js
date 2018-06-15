const Promise = require('./Promise')
const ENV = require('../config')

const DEV_PORT = 'https://xingzuoapi.yetingfm.com/xz_api/' // 开发环境
const PRO_PORT = 'https://xingzuoapi-prod.yetingfm.com/xz_api/' // 生产环境

const DOMAIN = ENV === 'dev' ? DEV_PORT : PRO_PORT

// 小程序上线需要https，这里使用服务器端脚本转发请求为https

const requstGet = (url, data) => requst(url, 'GET', data)

const requstPost = (url, data) => requst(url, 'POST', data)

const requst = (url, method, data = {}) => {
  let notShowLoading = data.notShowLoading
  let loadingStr = data.loaingStr
  let netErrorMsg = '网络不好呦，请小主重新刷新'
  if (!loadingStr) {
    loadingStr = '加载中...'
  }
  delete(data.notShowLoading)
  delete(data.loaingStr)
  if (!notShowLoading) {
    wx.showLoading({
      title: loadingStr,
    })
  }

  return new Promise((resove, reject) => {
    wx.request({
      url: DOMAIN + url,
      data: {
        requestHeader: JSON.stringify({
          token: wx.getStorageSync('token')
        }),
        requestBody: JSON.stringify(data)
      },
      header: {
        'Accept': 'application/json',
        'Content-Type': 'application/x-www-form-urlencoded',
      },

      // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      method: method.toUpperCase(),
      success: function (res) {
        let resData = res.data
        let resBody = resData.responseBody

        if (resData && resBody &&
          (resBody.status === 'SUCCESS')) {
          if (url == 'selectConstellation/x100' && !data.constellationId) {
            resove(resBody)
          } else {
            resove(resBody.data)
          }
        } else {
          if (resData && resBody && reject) {
            reject(resBody.message)
          }
        }

      },
      fail: function (msg) {
        if (reject) {
          reject(netErrorMsg)
        } else {
          wx.showToast({
            title: netErrorMsg,
            icon: 'none'
          })
        }
      },
      complete: function (res) {
        if (!notShowLoading) {
          wx.hideLoading()
        }
      }
    })
  })
}

const PORT = {
  getLogin(data) { //登录
    return requstPost('loginConstellation/x000', data)
  },
  getSelectx100(data) { //选择星座
    return requstPost('selectConstellation/x100', data)
  },
  getSelectx101(data) { //选中星座
    return requstPost('selectConstellation/x101', data)
  },
  getSelectx102(data) {
    return requstPost('selectConstellation/x102', data)
  },
  getIndexx200(data) {
    return requstPost('indexConstellation/x200', data)
  },
  getMorex300(data) {
    return requstPost('moreConstellation/x300', data)
  },
  getDayx400(data) {
    return requstPost('everydayWords/x400', data)
  },
  getDayx401(data) {
    return requstPost('everydayWords/x401', data)
  },
  getX500(data) {
    return requstPost('everydayQian/x500', data)
  },
  getX501(data) {
    return requstPost('everydayQian/x501', data)
  },
  getX503(data) {
    return requstPost('everydayQian/x503', data)
  },
  getX504(data) {
    return requstPost('everydayQian/x504', data)
  },
  getX506(data) { //拆签   签id
    return requstPost('everydayQian/x506', data)
  },
  getX507(data) {
    return requstPost('everydayQian/x507', data)
  },
  getX510(data) {
    return requstPost('everydayQian/x510', data)
  },
  getX511(data) {
    return requstPost('everydayQian/x511', data)
  },
  getX600(path, data) {
    requstPost('statisticsConstellation/x' + path, data)
  },
  getX610(data) {
    return requstPost('statisticsConstellation/x610', data)
  },
  // 获取用户设置(需授权)
  getUserSetting(data) {
    return requstPost('userSetting/get', data)
  },
  // 获取用户设置(免授权)
  globalSetting(data) {
    return requstPost('globalSetting/get', data)
  },
  // 保存用户设置
  setUserSetting(data) {
    return requstPost('userSetting/save', data)
  },
  getBannerList(data) {
    return requstPost('ad/list', data)
  },
  /*---------------------------------------
      充值页面
  ---------------------------------------*/
  // 获取商品列表
  getGoods(data) {
    return requstPost('pay/getgoods', data)
  },
  // 获取钱包信息
  getBlance(data) {
    return requstPost('pay/getbalance', data)
  },
  // 充值操作
  getRecharge(data) {
    return requstPost('pay/recharge', data)
  }
}

module.exports = {
  Promise,
  get: requstGet,
  post: requstPost,
  requst,
  ...PORT
}
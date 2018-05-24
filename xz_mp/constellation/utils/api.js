const Promise = require('./Promise')
function requstGet(url, data) {
  return requst(url, 'GET', data)
}

function requstPost(url, data) {
  return requst(url, 'POST', data)
}

const DOMAIN = 'https://xingzuoapi.yetingfm.com/xz_api/'
// const DOMAIN = 'http://193.112.130.148:8888/xz_api/'

// 小程序上线需要https，这里使用服务器端脚本转发请求为https
function requst(url, method, data = {}) {
  var notShowLoading = data.notShowLoading
  var loadingStr = data.loaingStr
  if (!loadingStr) {
    loadingStr = '加载中...'
  }
  delete (data.notShowLoading)
  delete (data.loaingStr)
  // wx.showNavigationBarLoading()
  if (!notShowLoading) {
    wx.showLoading({
      title: loadingStr,
    })
  }

  var rewriteUrl = url
  return new Promise((resove, reject) => {
    wx.request({
      url: DOMAIN + rewriteUrl,
      data:
      {
        requestHeader: JSON.stringify({
          token: wx.getStorageSync('token')
        }),
        requestBody: JSON.stringify(data)
      }
      ,
      header: {
        'Accept': 'application/json',
        'Content-Type': 'application/x-www-form-urlencoded',
      },

      method: method.toUpperCase(), // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
      success: function (res) {
        console.log(url)
        if (url == 'statisticsConstellation/x610') {
          console.log(data)
        }
        if (res.data && res.data.responseBody &&
          ('SUCCESS' == res.data.responseBody.status)) {
          if (url == 'selectConstellation/x100' && !data.constellationId) {
            resove(res.data.responseBody)
          } else {
            resove(res.data.responseBody.data)
          }

        } else {
          if (res.data && res.data.responseBody && reject) {
            reject(res.data.responseBody.message)
          } else {
            reject('fail')
            
          }
        }

      },
      fail: function (msg) {
        if (reject) {
          reject('fail')
        } else {
          wx.showToast({
            title: '网络错误，请稍后重试',
            icon: 'none'
          })
        }
      },
      complete: function (res) {
        // wx.hideNavigationBarLoading()
        if (!notShowLoading) {
          wx.hideLoading()
        }

      }

    })
  })
}

function getLogin(data) {//登录
  return requstPost('loginConstellation/x000', data)
}

function getSelectx100(data) {//选择星座
  return requstPost('selectConstellation/x100', data)
}

// function getSelectx101(data) {//选中星座
//   return requstPost('selectConstellation/x101', data)
// }

// function getSelectx102(data) {
//   return requstPost('selectConstellation/x102', data)
// }

function getIndexx200(data) {
  return requstPost('indexConstellation/x200', data)
}

function getMorex300(data) {
  return requstPost('moreConstellation/x300', data)
}

function getDayx400(data) {
  return requstPost('everydayWords/x400', data)
}
function getDayx401(data) {
  return requstPost('everydayWords/x401', data)
}

function getX500(data) {
  return requstPost('everydayQian/x500', data)
}


function getX501(data) {
  return requstPost('everydayQian/x501', data)
}


function getX503(data) {
  return requstPost('everydayQian/x503', data)
}


function getX504(data) {
  return requstPost('everydayQian/x504', data)
}


function getX506(data) {//拆签   签id
  return requstPost('everydayQian/x506', data)
}


function getX507(data) {
  return requstPost('everydayQian/x507', data)
}

function getX510(data) {
  return requstPost('everydayQian/x510', data)
}

function getX511(data) {
  return requstPost('everydayQian/x511', data)
}

function getX610(data) {
  return requstPost('statisticsConstellation/x610', data)
}
function getX600(path, data) {
  return requstPost('statisticsConstellation/x' + path, data)
}


module.exports = {
  Promise,
  get: requstGet,
  post: requstPost,
  requst,

  //接口
  getLogin,
  getSelectx100,
  // getSelectx101,
  // getSelectx102,
  getIndexx200,
  getMorex300,
  getDayx400,
  getDayx401,
  getX500,
  getX501,
  getX503,
  getX504,
  getX506,
  getX507,
  getX510,
  getX511,

  getX600,
  getX610,
}
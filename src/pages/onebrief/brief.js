// pages/onebrief/brief.js
const $vm = getApp()
const _GData = $vm.globalData
const API = require('../../utils/api')
const util = require('../../utils/util')
const getImageInfo = $vm.utils.wxPromisify(wx.getImageInfo)
var mta = require('../../utils/mta_analysis.js')
const Storage = require('../../utils/storage')
const bus = require('../../event')
const q = require('../../utils/source')
let env = 'dev'
let timer = false
// 图片有多少个
let len = 0

Page({

  /**
   * 页面的初始数据
   */
  data: {
    picUserName: '', //用户昵称
    isFromShare: false, //未调用
    prevPic: "",
    isShow: false,
    navConf: {
      title: '今日赠言',
      state: 'root',
      isRoot: false,
      bg: '#fff',
      isIcon: true,
      iconPath: '',
      root: '',
      isTitle: true,
      showContent: true,
      color: 'black',
      fontColor: 'black',
      showPop: false,
      showTabbar: false,
      tabbar: {},
      pop: {}
    },
    opts: {
      appId: 'wx865935599617fbdb',
      path: 'pages/home/home?source=XGstars&type=in&id=110000'
    },
    // 适配高度
    hei: 64,
    isIPhoneX: false,
    // 当前滑块
    current: 0, 
    // 初始化位置
    initCurrent: 0,
    isFirst: false, //是否是第一次进来
    list: [], //页面渲染数据
    emptylist: false, //页面数据为空所加载
    tomorrow: {
      year: false,
      month: false,
      day: false,
      hour: false,
      minute: false,
      sec: false,
      timer: true,
    },
    page: 1, //默认分页
    // 版本
    version: true,
    // 按下状态
    touchStatus: false,
    showCanvas:true,
    // 加载中
    loading: true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    len = 0
    mta.Page.init()
    console.log('----------------------------------------------brief onLoad')
    let self = this
    if (options.from === 'qrcode') {
      mta.Event.stat('qrcode_brief', {})
    }
    q.sourceHandle(options)

    let handle = () => {
      console.log('登录标识')
      Storage.briefLogin = true
      self.setData({
        userInfo: Storage.userInfo,
        picUserName: Storage.userInfo.nickName
      })
      console.log(self.data.userInfo)
      this.getwordlist() //获取一言数据
      getSystemInfo(this)
      // wx.hideShareMenu() //隐藏转发按钮
    }

    if (Storage.briefRemoveId) {
      bus.remove(Storage.briefRemoveId)
    }
    if (Storage.briefLoginRemoveId) {
      bus.remove(Storage.briefLoginRemoveId)
    }
    Storage.briefRemoveId = bus.on('login-success', handle, 'login-com')
    Storage.briefLoginRemoveId = bus.on('login-success', handle, 'brief-app')

    // 如果已经存在用户信息触发登录标识
    if (Storage.userInfo) {
      bus.emit('login-success', {}, 'brief-app')
    }

  },

  onShow: function () {
    this.setData({
      version: Storage.miniPro
    })
    let starXz = Storage.starXz || {}
    console.log(starXz.id, wx.getStorageSync('constellationId'))

    if (starXz.id && starXz.id != wx.getStorageSync('constellationId')) { //判断星座id是否有变动
      this.getwordlist() //获取一言数据
    }

  },
  // 是否按下
  touchStart(e){
    // console.log(e.touches)
  },
  // 移动操作
  touchMove(e){
    // console.log(e.touches)
    this.setData({
      touchStatus: true
    })
  },
  // 结束按下
  touchEnd(e){
    this.setData({
      touchStatus: false
    })
  },
  // 查看原图图片
  _look(e){
    this.setData({
      'navConf.showPop':true
    })
  },
  // 关闭弹窗
  _close(e){
    this.setData({
      'navConf.showPop':false
    })
  },
  _loadImg(e){
    console.log('加载图片：',len)
    ++len
    if(len === this.data.list.length * 2){
      this.setData({
        loading: false
      })
    }
  },
  // 点赞
  _give(e){
    let { res, index } = e.currentTarget.dataset
    console.log(res)
    if(res.status)return
    let tmp = {}
    tmp['list[' + index + '].status'] = true
    tmp['list[' + index + '].like'] = ++this.data.list[index].like
    this.setData(tmp)
    API.setGive({ id: res.id , notShowLoading: true}).then(res => {
      console.log('点赞结果值：',res)
      let arrs = wx.getStorageSync('give_ids') || []
      arrs.push(this.data.list[index].id)
      wx.setStorageSync('give_ids', arrs)
    }).catch(e => {
      tmp['list[' + index + '].status'] = false
      tmp['list[' + index + '].like'] = --this.data.list[index].like
      this.setData(tmp)
    })
  },
  // 获取上一页数据
  getCurrent(e) {
    // console.log(e.detail.current)
    this.setData({
      current: e.detail.current
    })
  },
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
    return {
      title: '这不是简单的句子，全是你内心的独白',
      imageUrl: '/assets/images/share-brief.jpg',
      path: '/pages/home/home?to=brief&from=share&source=share&id=999998&tid=123455&shareform=brief&m=0',
    }
  },
  // 保存原图
  _saveImg(e){
    const _self = this
    let { res:data, index } = e.currentTarget.dataset
    let img = data.pic
    console.log('保存原图：', e)
    mta.Event.stat('yan_save_photo', {})

    wx.showLoading({
      title: '图片生成中...',
      mask: true,
    })
    $vm.utils.Promise.all([
      getImageInfo({
        src: img,
      })
    ]).then((res) => {
      console.log('图片信息：',res)
      if(res && res[0] && res[0].errMsg == 'getImageInfo:ok'){
        
        const ctx = wx.createCanvasContext('shareCanvas')
        ctx.drawImage(res[0].path, 0, 0, 750, 1334)

        ctx.draw(true,function(){
          wx.canvasToTempFilePath({
            canvasId: 'shareCanvas',
            x: 0,
            y: 0,
            width: 750,
            height: 1334,
            destWidth: 750,
            destHeight: 1334,
            success: function (res) {
              console.log(res.tempFilePath)
              wx.saveImageToPhotosAlbum({
                filePath: res.tempFilePath,
                success(res) {
                  wx.showModal({
                    title: '保存成功',
                    content: '图片已经保存到相册',
                    showCancel: false,
                  })
                },
                fail() {
                  wx.showToast({
                    title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                    icon: 'none',
                    duration: 3000
                  })
                },
                complete() {
                  wx.hideLoading()
                }
              })
            },
            fail: function (res) {
              console.log(res)
              wx.hideLoading()
              wx.showToast({
                title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                icon: 'none',
                duration: 3000
              })
            }
          })
        })
      }
    })
    .catch((err) => {
      wx.hideLoading()
      console.log('保存图片错误信息', err)
      wx.showToast({
        icon: 'none',
        title: '加载失败了，请检查网络',
      })
    })
  },
  /**
   * 保存图片
   */
  _save: function (e) {
    const _self = this
    let { res:data, index } = e.currentTarget.dataset
    let img = data.prevPic
    console.log('保存图片：', e)
    mta.Event.stat('brief_save_pic', {})
    mta.Event.stat("ico_brief_save", {})

    
    wx.showLoading({
      title: '图片生成中...',
      mask: true,
    })
    
    $vm.utils.Promise.all([
        getImageInfo({
          src: img,
        })
      ]).then((res) => {
        console.log('图片信息：',res)
        if(res && res[0] && res[0].errMsg == 'getImageInfo:ok'){
          let width = 700
          let height = 988
          
          const ctx = wx.createCanvasContext('shareCanvas')
          ctx.setFillStyle('white')
          ctx.fillRect(0, 0, 750, 1334)
          ctx.drawImage(res[0].path, 25, 25, width, height)

          ctx.setTextBaseline('top')
          ctx.setFillStyle('#FFFFFF')
          ctx.setFontSize(120)
          ctx.fillText(data.date, 68, 93)

          ctx.setTextBaseline('top')
          ctx.setFillStyle('rgba(255,255,255,.8)')
          ctx.setFontSize(33)
          ctx.fillText(data.month + '.' + data.year, 68, 232)

          ctx.setLineWidth(1)
          ctx.setStrokeStyle('#E5E5E5');
          ctx.strokeRect(25, 1038 , 638 , 188);
          ctx.strokeRect(30, 1033 , 638, 188);

          ctx.drawImage('/assets/images/yan.png', 610, 1058 , 115, 148)

          ctx.setTextBaseline('top')
          ctx.setFillStyle('#8080A6')
          ctx.setFontSize(28)
          ctx.fillText('我们坚信再艰难的生活，', 50, 1063)
          ctx.fillText('也需要一些仪式感。', 50, 1096)
          
          ctx.setFillStyle('#262346')
          ctx.setFontSize(30)
          ctx.fillText(_GData.userInfo.nickName, 50,1148)

          ctx.draw(true,function(){
            wx.canvasToTempFilePath({
              canvasId: 'shareCanvas',
              x: 0,
              y: 0,
              width: 750,
              height: 1334,
              destWidth: 750,
              destHeight: 1334,
              success: function (res) {
                console.log(res.tempFilePath)
                wx.saveImageToPhotosAlbum({
                  filePath: res.tempFilePath,
                  success(res) {
                    wx.showModal({
                      title: '保存成功',
                      content: '图片已经保存到相册，可以分享到朋友圈了',
                      showCancel: false,
                    })
                  },
                  fail() {
                    wx.showToast({
                      title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                      icon: 'none',
                      duration: 3000
                    })
                  },
                  complete: function() {
                    wx.hideLoading()
                  }
                })
              },
              fail: function (res) {
                console.log(res)
                wx.showToast({
                  title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                  icon: 'none',
                  duration: 3000
                })
                wx.hideLoading()
              },
            })
          })
        }
      })
      .catch((err) => {
        wx.hideLoading()
        console.log('保存图片错误信息', err)
        wx.showToast({
          icon: 'none',
          title: '加载失败了，请检查网络',
        })
      })
  },

  // 上报formid
  formid(e) {
    let isFirst = wx.getStorageInfoSync().keys
    if (isFirst.indexOf('isFirst') == -1) {
      wx.setStorageSync('isFirst', '1')
      this.setData({
        isFirst: false
      })
    }

    $vm.api.getX610({
      formid: e.detail.formId,
      notShowLoading: true
    })
  },

  // 获取一言列表数据
  getwordlist() {
    let self = this
    
    $vm.api.wordlist({
      constellationId: _GData.selectConstellation.id,
      startpage: this.data.page,
      notShowLoading: true
    }).then(res => {
      
      wx.setStorageSync('constellationId', _GData.selectConstellation.id) // 设置星座id缓存
      console.log('获取一言数据:', res)
      if (res.wordlist.length > 0) {
        let url = 'https://xingzuo-1256217146.file.myqcloud.com' + (env === 'dev' ? '' : '/prod')
        let ids = wx.getStorageSync('give_ids') || []
        res.wordlist.forEach(function (val) {
          let _date = val.currentDate.replace(/\-/g,'/')
          console.log(_date,new Date(_date),new Date(val.currentDate))
          let tmp = new Date(_date)
          val.year = tmp.getFullYear()
          val.month = util.getMonth(parseInt(tmp.getMonth() + 1))
          val.date = tmp.getDate()
          val.date = val.date.length > 1 ? val.date : '0' + val.date
          console.log('时间：',tmp.getFullYear() + '-' + util.getMonth(parseInt(tmp.getMonth()  + 1)) + '-' + tmp.getDate())
          val.prevPic = url + val.prevPic
          val.pic = url + val.pic
          val.pic = url + '/1184b2066eb44e6598f4f26cbb27bc8f_01561d56afdb427a88fec6061a65701c.png'
          val.status = ids.indexOf(val.id) != -1 ? true : false
        })
        // len = res.wordlist.length * 2

        this.setData({
          list: res.wordlist,
          current: res.wordlist.length - 1,
          initCurrent: res.wordlist.length - 1
        })

        wx.hideLoading()
        console.log('getwordlist打印数据', this.data.list)
      } else {
        wx.hideLoading()
        this.setData({
          list: false
        })
      }

    }).catch(res => {
      wx.hideLoading()
      this.setData({
        list: false
      })
    })
  },

  // 获取导航栏高度
  _setHeight(e) {
    // console.log(e.detail)
    this.setData({
      hei: e.detail || 64
    })
  },
  // 再试一次
  tryagain(e) {
    $vm.api.getX610({
      formid: e.detail.formId
    })
    wx.reLaunch({
      url: '/pages/home/home'
    })
  }
})

/**
 * 获取系统比例加入比例标识
 * @param {*} self
 */
function getSystemInfo(self) {
  let res = wx.getSystemInfoSync();
  console.log('设备信息：', res);
  if (res) {
    // 长屏手机适配
    if (res.screenWidth <= 375 && res.screenHeight >= 750) {
      self.setData({
        isIPhoneX: true
      })
    }
  }
}
const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const util = require('../../../../utils/util')
let img = '/assets/images/share-banner.png'

Page({
  data: {

    navConf: {
      title: ' 订单详情',
      state: 'root',
      isRoot: false,
      isIcon: true,
      root: '',
      bg: '#9262FB',
      isTitle: true
    },
    IPhoneX : false,
    // 默认高度
    height: 64,
    id:-1,
    noid:'21321321342421459',
    region: [],
    customItem: '请选择',
    show:true
  },

  onLoad(options) {
    console.log(options)
    this.setData({
      id:options.id
    })
    mta.Page.init()
  },

  // 变更选择的值
  bindRegionChange: function (e) {
    let tmp = e.detail.value
    console.log('picker发送选择改变，携带值为', tmp)
    if(tmp.indexOf('请选择') != -1){
      wx.showModal({
        title:'警告',
        content: '请选择正确的收货地址',
        confirmText:'确定',
        showCancel : false
      })
      return
    }
    this.setData({
      region: tmp
    })
  },

  // 获取导航栏高度
  _setHeight(e) {
    let temp = e.detail || 64 
    this.setData({
      height: temp,
      IPhoneX : temp === 64 ? false : true
    })
  },
  /**
     * 上报formId
     * @param {*} e
     */
  _reportFormId(e) {
    console.log(e.detail.formId)
    let formid = e.detail.formId
    API.getX610({ notShowLoading: true, formid: formid })
  }
})
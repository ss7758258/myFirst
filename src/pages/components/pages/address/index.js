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
    noid:'21321321342421459',
    region: [],
    customItem: '请选择',
    show:false,
    isNull : false,
    phone : null,
    ninckName:null,
    address:null
  },

  onLoad(options) {
    console.log(options)
    this.setData({
      noid:options.orderno
    })
    mta.Page.init()
  },

  // 兑换物品
  exchange(){
    let data = this.data
    if(!this.data.isNull || (!this.data.phone || this.data.phone == '') || (!this.data.ninckName || this.data.ninckName == '') || (!this.data.address || this.data.address == '')){
      wx.showToast({
        title: '信息填写不完整，请认真填写',
        icon: 'none',
        duration: 1500,
        mask: false,
      });
    }else{
      let param = {orderno:data.noid,consignee:data.ninckName,phone:data.phone}
      let tmp = data.region.join('-')
      param.address = `${tmp}-${data.address}`
      console.log(param)
      API.getUpdateAddress(param).then(res => {
        console.log('提交收货地址',res)
        if(!res){
          return
        }
        if(res.ret === 1){
          this.setData({
            show:true
          })
        }else{
          wx.showModal({
            title: '提示',
            content: res.retmsg,
            showCancel:false,
            confirmText: '确定',
            confirmColor: '#9262FB'
          })
        }
        wx.hideLoading()
      }).catch( e => {

      })
    }
  },
  changeName(e){
    let {value:val} = e.detail
    this.setData({
      ninckName:val
    })
    console.log(val)
  },
  changePhone(e){
    let {value:val} = e.detail
    this.setData({
      phone:val
    })
    console.log(val)
  },
  changeAddress(e){
    let {value:val} = e.detail
    this.setData({
      address:val
    })
    console.log(val)
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
      this.setData({
        isNull: false
      })
      return
    }
    this.setData({
      region: tmp,
      isNull: true
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
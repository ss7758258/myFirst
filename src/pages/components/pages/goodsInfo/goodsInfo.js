const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const util = require('../../../../utils/util')
let img = '/assets/images/share-banner.png'

Page({
  data: {

    navConf: {
      title: ' 精选好物',
      state: 'root',
      isRoot: false,
      isIcon: true,
      root: '',
      bg: '#9262FB',
      isTitle: true
    },
    cdn:'https://xingzuo-1256217146.file.myqcloud.com',
    IPhoneX : false,
    // 默认高度
    height: 64,
    list:[{
      id:1,
      pic:img
    },{
      id:2,
      pic:img
    }],
    data:{
      goods:500,
      name:'小米活塞耳机',
      inventory : 0
    },
    result:[],
    id:-1,
    // 默认选中
    current:0
  },
  onLoad(options) {
    console.log('进入的分享信息',options)
    this.setData({
      id:options.id
    })
    mta.Page.init()
    this.getGoodsInfo()
  },
  onShareAppMessage(){
    return {
      path : 'pages/home/home?from=share&to=goodsInfo&id=' + this.data.id
    }
  },
  onShow(opts){
    console.log('商品详情页',opts)
    if(this.data.id != -1){
      this.getGoodsInfo()
    }
  },
  // swiper变更
  _swiperChange(e){
    let {current} = e.detail
    this.setData({
      current
    })
  },
  // 获取商品详情
  getGoodsInfo(){
    console.log('输出ID信息：',+this.data.id)
    API.getGoodslist({id : +this.data.id}).then(res => {
      console.log('列表结果：',res)
      if(!res){
        wx.showToast({
          title: '未获取到商品信息，请重新获取',
          icon: 'none'
        })
        return
      }
      let data = res.goods[0]

      let list = data.pic.map(v => {
        return { pic : this.data.cdn + v} 
      })
      // console.log(list)
      this.setData({
        data,
        list
      })
    }).catch(err => {
      wx.showToast({
        title: '未获取到商品信息，请重新获取',
        icon: 'none'
      })
    })

    API.getBuylist({id : +this.data.id,startpage:1,pagesize:10}).then(res => {
      console.log('购买列表：',res)
      if(!res){
        return
      }
      let result = res.list.map(v => {
        return {
          nickName: v.name.substring(0,1) + '**',
          date: v.time.replace(/\-/ig,'/').substring(0,10),
          headImage: (v.headImage && v.headImage != '') ? v.headImage : '/assets/images/default_head.png'
        }
      })
      
      this.setData({ result })
    })
  },
  // 支付
  payGoods(){
    let self = this
    let {data : resData,id} = this.data
    console.log('前往支付')
    mta.Event.stat('pay_click_goods', { id })
    if(resData.inventory < 1){
      wx.showModal({
        title: '库存不足',
        content: '库存不足，请等待小哥为您上货',
        showCancel:false,
        confirmText: '确定',
        confirmColor: '#9262FB'
      })
      return
    }
    wx.showModal({
        title: '确定兑换？',
        content: '兑换需要消耗' + resData.goods + '颗小星星',
        showCancel: true,
        cancelColor: '#999999',
        cancelText: '我再想想',
        confirmText: '确定',
        confirmColor: '#9262FB',
        success: function (res) {
            console.log(res)
            mta.Event.stat('pay_click_goods_success', { id })
            if (res.confirm) {
                API.getBlance({
                    notShowLoading: true
                }).then(data => {
                    if (!data) {
                        return
                    }
                    console.log('钱包星星数量：', data)
                    // data.balance = 2000
                    // 当小星星不足时进行提示
                    if (data.balance < resData.goods) {

                        mta.Event.stat('pay_goods_fail', { id })

                        if (!Storage.isLogin) {
                            return
                        }

                        wx.showModal({
                            title: '余额不足',
                            content: '请先去获取一些小星星吧',
                            showCancel: true,
                            cancelColor: '#999999',
                            cancelText: '我再想想',
                            confirmText: '立即获取',
                            confirmColor: '#9262FB',
                            success: function (res) {
                                if (res.confirm) {
                                    // 跳转到小星星页面
                                    wx.navigateTo({
                                        url: '/pages/banner/banner'
                                    })
                                }
                            }
                        })
                    } else {
                      mta.Event.stat('pay_goods_success', { id })
                      wx.showLoading({
                          title: '购买中...'
                      })
                      
                      API.getEXChange({id : +id}).then(res => {
                        console.log('购买列表：',res)
                        if(!res){
                          return
                        }
                        if(res.retcode === 0){
                          wx.navigateTo({
                            url:'/pages/components/pages/address/index?orderno=' + res.order.orderno + '&id=' + id + '&status=' + res.order.reality
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
                      }).catch(e => {
                        console.log('购买失败：',e)
                        wx.showModal({
                          title: '提示',
                          content: '购买失败',
                          showCancel:false,
                          confirmText: '确定',
                          confirmColor: '#9262FB'
                        })
                      })
                      
                    }
                })
            }
        }
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
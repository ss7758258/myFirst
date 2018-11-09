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
    IPhoneX : false,
    // 默认高度
    height: 64,
    list:[{
      id:1,
      pic:img,
      title:'小米活塞耳机',
      star:2000,
      price:48
    },{
      id:2,
      pic:img,
      title:'小米活塞耳机',
      star:2000,
      price:48
    }],
    data:{
      star:500,
      title:'小米活塞耳机',
      stock : 29
    },
    result:[{
      head:img,
      nickName:'不知道是谁',
      desc:'兑换了小米活塞耳机',
      date:'2018/09/20'
    },{
      head:img,
      nickName:'不知道是谁',
      desc:'兑换了小米活塞耳机',
      date:'2018/09/20'
    }],
    id:-1,
    // 默认选中
    current:0
  },
  onLoad(options) {
    console.log(options)
    this.setData({
      id:options.id
    })
    mta.Page.init()
  },

  // 前往商品详情
  goExc(e){
    let {res} = e.currentTarget.dataset
    mta.Event.stat('page_goods_click',{id:res.id})
    console.log(res)
    wx.navigateTo({
			url:'/pages/components/pages/goodsInfo/goodsInfo'
		})
  },
  
  // swiper变更
  _swiperChange(e){
    let {current} = e.detail
    console.log(current)
    this.setData({
      current
    })
  },
  // 支付
  payGoods(){
    let self = this
    let {data : resData,id} = this.data
    console.log('前往支付')
    mta.Event.stat('pay_click_goods', { id })
    wx.showModal({
        title: '确定快速查看？',
        content: '快速查看需要消耗' + resData.star + '颗小星星',
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
                    if (data.balance < resData.star) {

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
                      
                      wx.navigateTo({
                        url:'/pages/components/pages/address/index?id=' + id
                      })
                      wx.hideLoading()
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
import API = require('../../../../utils/api')
import Storage = require('../../../../utils/storage')
import mta = require('../../../../utils/mta_analysis')
import util = require('../../../../utils/util')
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
    }]
  },
  onLoad() {
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
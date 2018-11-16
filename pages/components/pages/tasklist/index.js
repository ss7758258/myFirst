const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const util = require('../../../../utils/util')
let img = '/assets/images/share-banner.png'

Page({
  data: {

    navConf: {
      title: ' 任务列表',
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
    day:0,
    star:0,
    dayStar:0,
    starCount:0,
    noticeBtnStatus:false,
    question: false,
    qian:  false,
    signin:  false
  },

  onLoad(options) {
    console.log(options)
    mta.Page.init()
  },
  onShow(){
    this.getList()
  },
  // 获取任务信息
  getList(){
    API.getSigninstatus().then(res => {
      console.log('结果列表：',res)
      if(!res){
        return
      }
      this.setData({
        day:res.continuous || 0,
        star:res.gotsignin || 0,
        dayStar:res.gottotal || 0,
        question : res.question || false,
        qian : res.qian || false,
        signin : res.signin || false
      })
    })
    API.getUserSetting({
      notShowLoading: true
    }).then(res => {
      console.log('加载配置完成---------用户:', res);
      if (!res) {
        console.log('----------------输出错误信息----------用户配置错误')
        return false;
      }
      // 是否已经关注
      this.setData({
        noticeBtnStatus: res.noticeStatus === 1
      })
    }).catch(err => {
      console.log('加载用户配置失败---------------------------------用户配置错误')
    })

    API.getBlance({notShowLoading:true}).then(res => {
			console.log('获取钱包信息：',res)
			this.setData({
				starCount : res.balance
			})
		})
  },
  signin(){
    API.setSignin().then(res => {
			console.log('签到信息：',res)
			this.getList()
		})
  },
  // 任务点击
  goQian(e){
    mta.Event.stat('task_shake_click')
    console.log(e)
    let {res} = e.currentTarget.dataset
    if(res === ''){
      wx.navigateTo({
        url:'/pages/lot/shake/shake'
      })
    }
  },
  // 任务点击
  goList(e){
    mta.Event.stat('task_test_click')
    console.log(e)
    let {res} = e.currentTarget.dataset
    if(res === ''){
      wx.navigateTo({
        url:'/pages/components/pages/divineList/divine'
      })
    }
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
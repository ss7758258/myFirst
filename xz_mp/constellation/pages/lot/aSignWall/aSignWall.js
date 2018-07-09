// pages/aSignWall/aSignWall.js
const app=getApp()
const bus = require('../../../event.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    navConf: { //导航定义
      title: '一签墙',
      state: 'root',
      isRoot: false,
      isIcon: true,
      iconPath: '',
      root: '',
      isTitle: true,
      centerPath: '/pages/center/center'
    },
    date_list:[
        // { id: 1, date: 2018.07, day: 3, status: 1 },
        // { id: 1, date: 2018.07, day: 2, status: 3 },
    ],
    pageNum:1,//页数

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      this.useApi()
      // bus.on('login-success-report',(res) => {
      // this.useApi()
      // },'app')
  },

   useApi(){
     let self=this;
     let pageNum = this.data.pageNum;
     app.api.getX510({ pageNum:pageNum,pageSize: 5}).then(res=>{
      console.log('aaaaaa',res)
      if (res.length > 0 && pageNum>1){
        self.setData({
          date_list:self.data.date_list.concat(res)
        })
      }else{
        self.setData({
          date_list:res
        })
      }

      self.data.date_list.forEach((value, key) => {
        let day = `date_list[${key}].day`;
        let date = `date_list[${key}].date`;
        let status = `date_list[${key}].status`;
        self.setData({
          [day]: value.qianDate.substring(8),
          [date]: value.qianDate.substring(0, 7).split('-').join('.'),
          [status]: value.status
        })
      })
 
     }).catch(err=>{
       console.log('bbbb',err)
     })
   } ,

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
    this.setData({
      pageNum:++this.data.pageNum
    })
    this.useApi()
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})
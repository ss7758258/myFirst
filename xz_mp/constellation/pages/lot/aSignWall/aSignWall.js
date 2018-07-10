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

   useApi(){  // 摇签数据
     let self=this;
     let pageNum = this.data.pageNum; // 页数
     console.log(pageNum)
     app.api.getX510({ pageNum:pageNum,pageSize: 5}).then(res=>{ 
      console.log('aaaaaa',res)
      if (res.length > 0 && pageNum>1){  // 分页
        self.setData({
          date_list:self.data.date_list.concat(res)
        })
      }else if(res.length<1 && pageNum>1){ //没有更多数据了
        wx.showToast({
          title: '没有更多数据了呢！',
          icon: 'none',
          mask: true,
        })
      }else{  
        self.setData({
          date_list: res
        })
      }

      self.data.date_list.forEach((value, key) => {  //对数据进行处理
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
       wx.showToast({
         title: '抱歉您的网络出了点问题呢',
         icon: 'none',
         mask: true,
       })
       console.log('bbbb',err)
     })
   } ,

   reachBottom(e){
     
    this.setData({
      pageNum: ++this.data.pageNum
    })
    console.log('嗯哼', this.data.pageNum)
    this.useApi()
   },

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
    
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})
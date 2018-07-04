// pages/lot/openSign/openSign.js
const $vm=getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.canvasCard()
  },

  canvasCard(){
    // let res=wx.getSystemInfoSync()
    // console.log('屏幕尺寸',res.windowWidth, res.windowHeight)
    let face = $vm.globalData.userInfo.avatarUrl //头像
    


    const ctx = wx.createCanvasContext('openSign')

    

    ctx.drawImage('/assets/img/background.png',0,0,375,535) //背景图
    ctx.drawImage('/assets/img/card.png', 0, 75, 375, 275) //拆签数据图
    ctx.drawImage('/assets/images/qrcodebrief.png', 150, 350, 75, 75) //小哥星座二维码图
    ctx.drawImage('/assets/img/text.png', 97, 433, 184, 45) //小哥星座文字图

    ctx.beginPath()
    ctx.arc(187.5, 85, 25, 0, 2 * Math.PI)
    ctx.clip()
    ctx.drawImage(face,162.5,60,50,50)
    ctx.restore()

    ctx.setFontSize(20)
    ctx.fillText('幸运星', 30, 100)
    
    ctx.draw()

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
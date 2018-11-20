const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const util = require('../../../../utils/util')
const getImageInfo = util.wxPromisify(wx.getImageInfo)
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
    cdn:'https://xingzuo-1256217146.file.myqcloud.com',
    orders:[],
    noid:'21321321342421459',
    region: [],
    customItem: '请选择',
    // 是否存在下一页
    hasNext : true,
    nextPage : 1,
    opts:{
      show:false
    },
    tipArt:{
      
    }
  },

  onLoad(options) {
    console.log(options)
    mta.Page.init()
    this.getOrderList()
  },

  onShareAppMessage(){
    return {
      path : 'pages/home/home?from=share&to=order'
    }
  },

  // 下一页数据
  _nextList(){
    mta.Event.stat('page_goods_next',{})
    console.log('进入下一页')
    if(!this.data.hasNext){
        return
    }
    this.getOrderList(++this.data.nextPage)
  },
  // 前往填写地址
  goAddress(e){
    let {res} = e.currentTarget.dataset
    console.log(res)
    if(res.address === ''){
      wx.navigateTo({
        url:'/pages/components/pages/address/index?orderno=' + res.orderno + '&id=' + res.goodsid + '&status=' + res.reality
      })
    }
  },
  // 显示详细地址
  showTip(e){
    let {res} = e.currentTarget.dataset
    console.log(res)
    if(res.reality !== 1){
      return
    }
    if(!res.address){
      return
    }
    let address = (res.tmpaddress && res.tmpaddress != '') ? res.tmpaddress.split('-') : []
    console.log(address)
    this.setData({
      'tipArt.name':res.consignee || '',
      'tipArt.phone':res.phone || '',
      'tipArt.address': address
    })
    this.setData({
      'opts.show':true
    })
  },
  copyText(e){
    let {res} = e.currentTarget.dataset
    let tmp = res.courier.split(':')
    console.log(tmp)
    wx.setClipboardData({
      data: tmp[1]
    })
  },
  drawImg(e){
    let {res} = e.currentTarget.dataset
    let img = this.data.cdn + res.courier
    
		wx.showLoading({
			title: '图片生成中...',
			mask: true
		})
    console.log(res)
    util.Promise.all([
			getImageInfo({
				src: img,
			})
		]).then((res) => {
			console.log(res)
      const ctx = wx.createCanvasContext('shareCanvas')
      
			ctx.drawImage(res[0].path, 0, 0, 750, 1334)

			ctx.draw()
			setTimeout(function () {
				wx.canvasToTempFilePath({
					canvasId: 'shareCanvas',
					success: function (res) {
						console.log(res.tempFilePath)
						wx.saveImageToPhotosAlbum({
							filePath: res.tempFilePath,
							success(res) {
								wx.hideLoading()
								wx.showModal({
									title: '保存成功',
									content: '图片已经保存到相册',
									showCancel: false,
								})
								// wx.hideLoading()
							},
							fail() {
                wx.hideLoading()
								wx.showToast({
									title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
									icon: 'none',
									duration: 3000
								})
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
					},
				})
			}, 1000)
		})
		.catch((err) => {
			wx.hideLoading()
      console.log('保存图片错误信息',err)
			wx.showToast({
				icon: 'none',
				title: '加载失败了，请检查网络',
			})
		})
  },
  // 获取订单信息
  getOrderList(page = 1){
    API.getOrderlist({startpage : page}).then(res => {
      console.log('订单列表：',res)
      if(!res){
        return
      }
      let orders = []
      res.orders.forEach(v => {
        console.log(v)
        v.tmpaddress = v.address
        v.address = (v.address && v.address != '') ? v.address.replace(/-/ig,'') : ''
      })
      
      if(page === 1){
        orders = res.orders
      }else{
        orders = this.data.orders.concat(res.orders || [])
      }
      this.setData({
        orders,
        hasNext : res.hasnextpage || false
      })
    }).catch(e => {
      console.log(e)
      wx.showToast({
        title: '获取订单列表失败，请重新获取',
        icon: 'none'
      })
    })
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
  _closeTip(){
    this.setData({
      'opts.show':false
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
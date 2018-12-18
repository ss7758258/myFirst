const bus = require('../../event')
const Storage = require('../../utils/storage')
const calendar = require('../../utils/calendar')
const util = require('../../utils/util')
const star = require('../../utils/star')
const imgs = require('./imgs')
Component({

  options: {
    // 在组件定义时的选项中启用多slot支持
    multipleSlots: true
  },
  /**
   * 组件的属性列表
   * 
   */
  properties: {
    opts: {
      type: Object,
      value: {
        show: false
      },
      observer(newData, oldData) {
        let data = wx.getStorageSync('account_info_storage')
        if(data){
          let tmp = new Date(data.date)
          this.setData({
            date: tmp.getFullYear() + '-' + (tmp.getMonth() + 1) + '-' + tmp.getDate()
          })
        }
        console.log(newData)
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    descText: '公历',
    IPX: '',
    current: 0,
    imgs,
    date: '1990-01-01',
    region: [],
    address: [],
    // 默认是公历日期
    status: 0
  },
  ready() {
    console.log('输出缓存信息：', Storage)
    if (Storage.iPhoneX) {
      this.setData({
        IPX: 'IPX'
      })
    }
  },
  /**
   * 组件的方法列表
   */
  methods: {
    changeStatus(){
      if(this.data.status == 0){
        this.setData({
          status: 1,
          descText: '农历'
        })
        return
      }
      this.setData({
        status: 0,
        descText: '公历'
      })
    },
    setContent() {
      let { current, date, region, address, status} = this.data
      
      let tmpDate = new Date((date.replace(/\-/ig,'/')))
      let time = tmpDate.getTime()
      let gDate = ''
      let xz = {}
      
      if(status === 1){
        gDate = calendar.lunar2solar(tmpDate.getFullYear(), tmpDate.getMonth() + 1, tmpDate.getDate())
        console.log(gDate);
      }
      if(gDate){
        let id = util.getXZ(util.getAstro(gDate.cMonth,gDate.cDay) + '座')
        xz = star[id]
      }else{
        let id = util.getXZ(util.getAstro(tmpDate.getMonth() + 1,tmpDate.getDate()) + '座')
        xz = star[id]
      }

      // 放入缓存
      wx.setStorageSync('selectConstellation', xz)

      console.log('星座信息',xz)
      let account = {
        sex: current == 1 ? 'girl' : 'boy',
        date: time,
        birthAddress: region.length > 0 ? region.join('-') : '',
        address: address.length > 0 ? address.join('-') : '',
        status,
        xz
      }

      Storage.accountDetailInfo = account
      wx.setStorageSync('account_info_storage', account);
      // 数据更新完成
      this.triggerEvent('update', account)
    },
    changeCurrent(e) {
      let {
        index
      } = e.currentTarget.dataset
      console.log(e)
      this.setData({
        current: parseInt(index)
      })
    },
    // 变更时间
    _ChangeVal(e) {
      let {
        value: val
      } = e.detail

      if (val.length > 0) {
        this.setData({
          date: val ? val : '1960-01-01'
        })
        return
      }
    },
    // 地址变更
    _ChangeAddress(e) {
      let tmp = e.detail.value
      console.log('picker发送选择改变，携带值为', tmp)
      this.setData({
        region: tmp
      })
    },

    _ChangeAdd(e){
      let tmp = e.detail.value
      console.log('picker发送选择改变，携带值为', tmp)
      this.setData({
        address: tmp
      })
    }
  }
})
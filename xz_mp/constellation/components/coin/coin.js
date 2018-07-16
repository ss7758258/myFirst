Component({
  properties: {

  },
  //  标注*号数据必须要有
  data: {
    flag:true,    //组件总开关 *
    // hasChai: false, //是否已拆签 *
    isStar:true, //是否使用星星拆签成功，true为开 *
    isRmb:false,  //是否使用RMB完成拆签 *
    num:false,    // 帮忙解签好友数量 *
    
    list: {  // 对接拆签处理后的数据就可以 *
      hasChai: false, //是否已拆签 *
      troops:[ //帮忙解签好友相关数据 *
        { openId: "omONY5GzpgbqHKLaMJcqmgL78Yc8", name: "omONY5GzpgbqHKLaMJcqmgL78Yc8", photo: "https://wx.qlogo.cn/mmopen/vi_32/4G6xECdj7HZjOhwiaE97ccpEmdKKffBjsv7MFD4GFFggl4icElb5cIjokANFnCQ4fThjSamlET3IXQCyh0rVyc9Q/132" },
        { openId: "omONY5IdLqlOaRjCmmZWMeUb9gvU", name: "omONY5IdLqlOaRjCmmZWMeUb9gvU", photo: undefined },
        { openId: "omONY5GMJ77bCB2Hd2Iwbpc1TtXM", name: "omONY5GMJ77bCB2Hd2Iwbpc1TtXM", photo: "https://wx.qlogo.cn/mmopen/vi_32/X2a2nDHDepcX9BClMQp5LVlmGdjVAnG1Jlks2vplCFXlcW0cspic4TLEaQtXiaOs506yll148KAwjMbPv8vqmTzA/132" }
    ],

    }
  },

  ready:function(){
    this.computedNum()
  },

  methods: {
    computedNum(){ //计算需要帮忙解签的好友数量
      var num=3
      this.data.list.troops.forEach( val=>{
        if (val.photo){
          num--
        }
      })
      console.log(`还要${num}个好友才能解签の`)
      this.setData({
        num:num
      })
    }
  }
})
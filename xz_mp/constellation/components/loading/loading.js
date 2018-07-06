Component({
  properties: {
    
  },
  data: {
    flag:false,
    loading:{
      title:'正在加载ing', //内容
      icon:false, //图标
      mask:false, //是否遮罩，默认为false
    }
  },
  ready:function(){
    
  },
  methods: {
    close(){ //关闭加载状态
      this.setData({
        flag:true
      })
    }
    
  }
})
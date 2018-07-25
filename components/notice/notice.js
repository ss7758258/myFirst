Component({

    behaviors: [],

    properties: {},
    data: {
        notice:{
            type:1, //组件类型
            background:'rgba(0,0,0,0.60)',//背景样式
            color:'#FFFFFF', //字体颜色
            time:3000, //轮播时间
            side:"top",
            content:['这是一个公才叫我的鸡尾dwdwdwdwdwdwdw酒第五期单位还绝望还件'],//内容
            src:'',//跳转路径
        },
        margin:60,
        empty:false,
        left:0,//默认滚动距离

    }, // 私有数据，可用于模版渲染

    // 生命周期函数，可以为函数，或一个在methods段中定义的方法名
    attached: function () { },
    moved: function () { },
    detached: function () { },

    methods: {
        
        run(){
            let self = this,dta=this.data
            var timer=setInterval(function(){
                if(-data.margin<data.txt_length){
                    self.setData({

                    })
                }
            },20)
        }
    },
    ready() {
        let screenwidth = wx.getSystemInfoSync().screenWidth //屏幕宽度
        let self = this 
        let content = this.data.notice.content, empty = []  // content文本内容 
        let query = wx.createSelectorQuery().in(this)
        query.selectAll('.notice').boundingClientRect(res=>{ // 获取文本文字宽度
            // console.log(res)
            res.forEach(function(value){
                self.setData({
                    screenwidth: screenwidth,
                    margin:value.width < screenwidth ? screenwidth - value.width : self.data.margin,
                    txt_length:value.width
                })
                
                this.run()
            })  
            // console.log(empty)
            // self.setData({
            //     empty:empty
            // })
            
        }).exec()
        
    }

})
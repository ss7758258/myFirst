const $vm=new getApp()
Component({

    behaviors: [],

    properties: {},
    data: {
        notice: {
            id:1,
            type: 2, //组件类型 1水平,2垂直,3小程序跳转
            background: 'rgba(0,0,0,0.60)',//背景样式
            color: '#FFFFFF',            //字体颜色
            side: "top",
            content: '这是一个公告组件',  //内容
            url: '',                    //跳转路径
            time: 20,                   //轮播时间
        },
        left: 0,                        //默认滚动距离
        txt_length:false,               //文本长度
        appid:'xxxxxxxxx',              //跳转小程序appid
    }, 

    methods: {
        // 跑马灯
        run() {
            let self = this, dta = this.data
            var timer = setInterval(function () {
                if (-dta.left < dta.txt_length) {
                    self.setData({
                        left:--dta.left,
                    })
                    clearInterval(timer);
                    self.run();
                }else{
                    clearInterval(timer);
                    self.setData({
                        left: dta.screenwidth
                    });
                    self.run();
                }
            }, dta.notice.time)
        },

        getnotice(){
            $vm.api.notice({page:1}).then(res=>{
                console.log(res)
                
            }).catch(res=>{
                console.log('报错信息',res)
            })
        },

        compatibility(){ //兼容
            if (wx.canIUse('navigateToMiniProgram')){
                wx.navigateToMiniProgram({
                    appId: 'xxxx',
                })
            }else{
                return
            }
        }
    },

    ready() {
        let screenwidth = wx.getSystemInfoSync().screenWidth //屏幕宽度
        let content = this.data.notice.content, empty = [] // content文本内容 
        let query = wx.createSelectorQuery().in(this)
        query.select('.notice').boundingClientRect(res => { // 获取文本文字宽度
            console.log(res)
            let txt_length=res.width
            this.setData({
                screenwidth: screenwidth,
                txt_length: txt_length
            })
            this.run()

            
        }).exec()
        // this.getnotice()
        this.compatibility()
    }

})
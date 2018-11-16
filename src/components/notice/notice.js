const $vm=new getApp()
Component({

    properties: {
        notice: Object,
        isShowNotice:{
            type: Boolean,
            value:true,
        }
    },
    data: {
        // notice: {
        //     id:1,
        //     type: 1, //组件类型 1水平,2垂直,3小程序跳转
        //     background: 'rgba(0,0,0,0.60)',//背景样式
        //     color: '#FFFFFF',            //字体颜色
        //     side: "top",
        //     content: false,  //内容
        //     url: '',                    //跳转路径
        //     time: 20,                   //轮播时间
        // },
        // left: 0,                        //默认滚动距离
        // txt_length:false,               //文本长度
        // appid:'xxxxxxxxx',              //跳转小程序appid
    }, 

    methods: {
        
        run() {
            
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

    }

})

Component({
    /**
     * 组件的属性列表
     * 
     */
    properties: {
        opts : Object
    },

    /**
     * 组件的初始数据
     */
    data: {
        swiper : {
            current : -1,
            indicatorDots: true, // 指示点
            vertical: false, // 竖向
            autoplay: false, // 自动播放
            circular: true, // 无缝衔接
            interval: 2000, // 自动播放时间间隔
            duration: 500, // 切换动画时长
            previousMargin: 0, // 前边距
            nextMargin: 0 // 后边距
        }
    },
    
    /**
     * 组件的方法列表
     */
    methods: {
       change (e){
           this.setData({
               'swiper.current' : e.detail.current
           })
           console.log(e.detail.current)
       }
    },
    ready(){
        console.log('组件加载完成时')
        setTimeout(() => {
            this.setData({
                'swiper.current' : 0
            })
        }, 300);
    }
})



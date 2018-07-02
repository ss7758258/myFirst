
const API = require('./api')
const methods = require('./util')
const mta = require('./analytics')

// 更新列表视图
const ViewUpdate = function(self){
    if(self.data.opts){
        // 获取banner列表
        methods.getBanner(self,API,{
            openId : self.data.opts.openId || '',
            appId : self.data.opts.appId || '',
            mainId : self.data.opts.mainId || '',
            pageNum : self.data.opts.pageNum || 1,
            pageSize : self.data.opts.pageSize || 20
        });
    }
}

Component({
    /**
     * 组件的属性列表
     * 
     */
    properties: {
        opts : {
            type : Object,
            value: {
                openId : '', // 用户的openId
                appId : '', // 小程序appId
                pageNum : 1, 
                pageSize : 20
            },
            observer(newData,oldData){
                if(newData.openId){
                    // 上报统计信息
                    API.upAnalytics({
                        appId : newData.appId,
                        openId : newData.openId,
                        type : 3 // 应用墙页面打开
                    })
                }
            }
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        mta : mta, // 数据分析
        list : [], // 数据集合
        swiper : {
            list: [],
            indicatorDots: false, // 指示点
            vertical: false, // 竖向
            autoplay: true, // 自动播放
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
        goOuter : methods.goOuter
    },
    ready(){
		mta.Page.init()
        // 将Page对象放入methods中
        methods.setThis(this);
        ViewUpdate(this)
    }
})



const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')

// 处理方法存放的对象
const methods = {

    self : null,
    /**
     * 维护一个Page对象
     * @param {*} self
     * @returns
     */
    setThis(self){
        this.self = self
    },
    /**
     * 获取Banner列表
     * @param {*} self Page对象
     */
    getBanner(self){
        // 拉取Banner列表接口
        API.getBannerList().then( (res) => {
            console.log('输出广告列表：',res)
            if(res && res.constructor === Array){
                let temp = []
                res.forEach(elem => {
                    if(elem.type === 4){
                        temp.push(elem)
                    }
                })
                self.setData({
                    list : res,
                    'swiper.list' : temp,
                    'swiper.indicatorDots' : temp.length > 1 ? true : false
                })

            }
        }).catch( (err) => {
            console.log(err)
        })
    },
    /**
     * 前往外链
     * @param {*} e
     */
    goOuter (e){
        let data = e.currentTarget.dataset
        let res = data && data.res ? data.res : {}
        console.log('参数信息：',res)
        if(res.appId){
			mta.Event.stat("to_outer_" + res.id, {})
            wx.navigateToMiniProgram({
                appId: res.appId,
                path: res.path
            })
        }
    },
    /**
     * 获取btn的文字信息
     */
    getBtnText (){
        this.self.setData({
            text : wx.getStorageSync('adBtnText') || '查看'
        })
    }
}

// Page对象的配置参数
const Conf = {
    data : {
        list : [],
        navConf : {
			title : '更多好玩',
			state : 'root',
			isRoot : false,
			isIcon : true,
			iconPath : '',
            root : '',
            isTitle : true
            // root : '/pages/home/home'
        },
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
        },
        text : '查看'
    },
    onLoad (){
        // 将Page对象放入methods中
        methods.setThis(this)
        // 获取banner列表
        methods.getBanner(this)
        // 获取默认文字信息
        methods.getBtnText()
    },
    goOuter : methods.goOuter
}


// 创建Banner页
Page(Conf)
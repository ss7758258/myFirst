const API = require('../../utils/api')
const mta = require('../../utils/mta_analysis.js')



var obj = new Proxy({}, {
    get(target, key, receiver) {
        console.log(`getting ${key}!`)
        return Reflect.get(target, key, receiver)
    },
    set(target, key, value, receiver) {
        console.log(`setting ${key}!`)
        return Reflect.set(target, key, value, receiver)
    }
})

// 处理方法存放的对象
let methods = function (self = null) {

    if (!self || !self.onLoad) {
        throw Error('实例对象调用错误')
    }

    return {
        //获取Banner列表
        _getBannerList() {
            // 拉取Banner列表接口
            API.getBannerList().then((res) => {
                if (res && res.constructor === Array) {
                    let swiperList = res.filter(item => item.type == 4)
                    const host = 'https://xingzuo-1256217146.file.myqcloud.com' //资源绝对路径

                    res.forEach(item => {
                        item.logo = host + item.logo
                        item.path = item.path
                    })

                    self.setData({
                        list: res,
                        'swiper.list': swiperList,
                        'swiper.indicatorDots': swiperList.length > 1
                    })
                }
            }).catch((err) => {
                console.log(err)
            })
        },

        //获取btn的文字信息
        _getBtnText() {
            self.setData({
                text: wx.getStorageSync('adBtnText') || '查看'
            })
        }
    }
}

// Page对象的配置参数
const Conf = {
    data: {
        list: [],
        navConf: {
            title: '更多好玩',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true
            // root : '/pages/home/home'
        },
        swiper: {
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
        text: '查看',
        isLock: false
    },
    onLoad() {

        console.log(obj.set)

        methods = methods(this)

        // 获取banner列表
        methods._getBannerList()
        // 获取默认文字信息
        methods._getBtnText()

    },
    onShow() {
        methods._getBannerList()
        setTimeout(() => {
            this.setData({
                isLock: false
            })
        }, 3500)
    },

    onHide() {
        this.setData({
            isLock: true
        })
    },
    // goOuter: methods.goOuter
    //前往外链 
    handleOutLinkClick(e) {
        if (this.data.isLock) return

        let data = e.currentTarget.dataset
        let res = data && data.res ? data.res : {}
        if (res.appId) {
            mta.Event.stat("to_outer_" + res.id, {})
            wx.navigateToMiniProgram({
                appId: res.appId,
                path: res.path + '?timestamp=' + new Date().getTime()
            })
            this.setData({
                isLock: false
            })
        }
    }
}

// 创建Banner页
Page(Conf)
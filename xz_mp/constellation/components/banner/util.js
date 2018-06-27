const API = require('./api')

const methods = {

    self : null,
    /**
     * 维护一个Page对象
     * @param {*} self
     * @returns
     */
    setThis(self){
        this.self = self;
    },
    /**
     * 获取Banner列表
     * @param {*} self Page对象
     */
    getBanner(self,API,param){
        
        // 拉取Banner列表接口
        API.getBannerList(param).then( (res) => {
            console.log('输出广告列表：',res)
            res = res.list || []
            if(res && res.constructor === Array){
                let temp = [];
                res.forEach(elem => {
                    if(elem.appType === 2){
                        temp.push(elem)
                    }
                });
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
        let self = this
        let data = e.currentTarget.dataset
        let res = data && data.res ? data.res : {}
        if(res.appId){
            API.upAnalytics({
                resourceId : res.id,
                appId : res.appId,
                openId : self.data.opts.openId,
                type : 1 
            })
            wx.navigateToMiniProgram({
                appId: res.appId,
                path: res.path,
                success(data) {
                    // 打开成功
                    API.upAnalytics({
                        resourceId : res.id,
                        appId : res.appId,
                        openId : self.data.opts.openId,
                        type : 2 
                    })
                },
                fail: function() {
                    console.log('打开失败')
                }
            })
        }
    }
}


module.exports = methods
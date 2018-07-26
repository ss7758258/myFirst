const API = require('./api')
const mta = require('./analytics')
const bus = require('./event')

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
                res.forEach((elem,ind) => {
                    // 调试数据
                    // res[ind].received = 0
                    // res[ind].starAmount = 10
                    // res[ind].appId = '123'
                    // if(ind % 2 === 0){
                    //     res[ind].received = 1
                    //     res[ind].starAmount = 5
                    // }

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
     * 组件版本跳转
     */
    navOuter(e){
        let self = this
        let {res = {} , index = 0} = e.currentTarget.dataset
        if(res.appId){

            API.upAnalytics({
                resourceId : res.id,
                appId : self.data.opts.appId,
                openId : self.data.opts.openId,
                type : 1 
            })
            
            console.log('============',self.data)
            // 所有资源统计
            mta.Event.stat("click_all", {})
            // 点击资源统计，依赖资源ID
            mta.Event.stat("click_" + res.id, {})
            
            // 事件通知
            bus.emit('resource_click',{
                res,
                self,
                index,
                opts : self.data.opts
            },'banner-app',false)

        }
    },
    /**
     * 组件成功跳转
     */
    navSuccess(e){
        console.log('----------------打开成功')
        let self = this
        let {res = {} , index = 0} = e.currentTarget.dataset
        // 打开成功
        API.upAnalytics({
            resourceId : res.id,
            appId : self.data.opts.appId,
            openId : self.data.opts.openId,
            type : 2 
        })
        // 所有打开成功的资源统计
        mta.Event.stat("open_success_all", {})
        // 打开成功的资源统计，依赖资源ID
        mta.Event.stat("open_success_" + res.id, {})
        
        // 事件通知
        bus.emit('resource_open_success',{
            res : res,
            self,
            index,
            opts : self.data.opts
        },'banner-app',false)
    },
    /**
     * 组件成功跳转
     */
    navFail(e){
        console.log('打开失败')
        let self = this
        let {res = {} , index = 0} = e.currentTarget.dataset
        
        // 所有打开失败的资源统计
        mta.Event.stat("open_fail_all", {})
        // 打开失败的资源统计，依赖资源ID
        mta.Event.stat("open_fail_" + res.id, {})

        // 事件通知
        bus.emit('resource_open_fail',{
            res,
            self,
            index,
            opts : self.data.opts
        },'banner-app',false)
    },
    /**
     * 前往外链
     * @param {*} e
     */
    goOuter (e){
        let self = this
        let {res = {} , index = 0} = e.currentTarget.dataset
        console.log(e)
        if(res.appId){

            API.upAnalytics({
                resourceId : res.id,
                appId : self.data.opts.appId,
                openId : self.data.opts.openId,
                type : 1 
            })
            console.log('============',self.data)
            // 所有资源统计
            mta.Event.stat("click_all", {})
            // 点击资源统计，依赖资源ID
            mta.Event.stat("click_" + res.id, {})
            
            // 事件通知
            bus.emit('resource_click',{
                res,
                self,
                index,
                opts : self.data.opts
            },'banner-app',false)

            wx.navigateToMiniProgram({
                appId: res.appId,
                path: res.targetUrl,
                success(data) {
                    console.log('JS---------打开成功')
                    // 打开成功
                    API.upAnalytics({
                        resourceId : res.id,
                        appId : self.data.opts.appId,
                        openId : self.data.opts.openId,
                        type : 2 
                    })
                    // 所有打开成功的资源统计
                    mta.Event.stat("open_success_all", {})
                    // 打开成功的资源统计，依赖资源ID
                    mta.Event.stat("open_success_" + res.id, {})
                    
                    // 事件通知
                    bus.emit('resource_open_success',{
                        res : res,
                        self,
                        index,
                        opts : self.data.opts
                    },'banner-app',false)
                },
                fail: function() {
                    console.log('JS---------打开失败')
                    
                    // 所有打开失败的资源统计
                    mta.Event.stat("open_fail_all", {})
                    // 打开失败的资源统计，依赖资源ID
                    mta.Event.stat("open_fail_" + res.id, {})

                    // 事件通知
                    bus.emit('resource_open_fail',{
                        res,
                        self,
                        index,
                        opts : self.data.opts
                    },'banner-app',false)
                }
            })
        }
    }
}


module.exports = methods
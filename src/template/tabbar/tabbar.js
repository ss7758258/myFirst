
const tab = require('./tabConfig')
const mta = require('../../utils/mta_analysis')
const handle = function(){
    let conf = {
        tabbarConfig : {},
        isIPX : false,
        // 初始化tab
        initTab(self,index = 0){
            conf.self = self
            conf.isIPX = false
            if(wx.getStorageInfo('iPhoneX')){
                conf.isIPX = true
            }else{
                let sys = wx.getSystemInfoSync()
                sys = sys || {screenWidth : 750 , screenHeight : 1334,model : 'iPhone',system : 'ios',SDKVersion : '1.9.0'}
                if(sys.model.indexOf('iPhone X') != -1){
                    wx.setStorage({
                        key: 'iPhoneX',
                        data: true
                    });
                    conf.isIPX = true
                }
            }
            let tabbarConfig = conf.tabbarConfig = {
                // 选中的下标
                selected : index,
                tab,
                show : false,
                isIPX:conf.isIPX
            }
            Object.defineProperty(conf,'height',{
                configurable: true,
                enumerable: false,
                value : conf.isIPX ? 83 : 49
                
            })

            // 切换选项卡
            self._switchTab = conf._switchTab
            console.log(conf)
            
            console.log(conf.height)

            // 生成数据信息
            self.setData({
                tabbarConfig
            })
        },
        /**
         * 切换选项卡
         * @param {number} [index=0]
         * @param {string} [name='首页']
         * @param {*} self
         */
        switchTab(index = 0,name = '首页',self){
            let c = {
                'tabbarConfig.selected' : index,
            }
            // c[tabbarConfig.tabbar[index]]
            self.setData(c)
        },
        _switchTab(e){
            let {index,res:data} = e.currentTarget.dataset
            console.log(data)
            // this.setData({
            //     'tabbarConfig.selected' : index
            // })
            mta.Event.stat(`${data.desc}_tab_click`,{})
            wx.switchTab({
                url : '/' + data.path
            })
        },
        // 获取tabbar的高度信息
        getHeight(){
            return {tab : conf.height , nav : conf.isIPX ? 89 : 64}
        },
        // 显示
        show(obj = {animate : false}){
            conf.self.setData({
                'tabbarConfig.show' :  obj.animate ? 9 : 5
            })
        },
        // 隐藏
        hide(){
            conf.self.setData({
                'tabbarConfig.show' :  false
            })
        }
    }

    return conf
}

module.exports = handle()
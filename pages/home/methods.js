const bus = require('../../event')
const API = require('../../utils/api')
let $vm = null
let _GData = null

const me = {
    /**
     * 初始化
     */
    init(){
        $vm = getApp()
        _GData = $vm.globalData
        console.log(this)
        me._getContent.call(this)
    },

    _getContent(){
        let selectConstellation = _GData.selectConstellation
        console.log(selectConstellation)

        let self = this
        if (selectConstellation && !selectConstellation.isFirst) {
            self.setData({
                myConstellation: selectConstellation,
                selectBack: false,
                showHome: true,
                'navConf.isIcon' : true
            })
            self.onShowingHome()
        } else {
            self.setData({
                showHome: false,
                'navConf.isIcon' : false
            })
        }
    }
}

const methods = function(){
    return {
        /**
         * 获取用户信息，处理登录之后的逻辑
         * @param {*} self
         * @param {*} selectConstellation
         * @param {*} options
         */
        getUserInfo(self,selectConstellation,options){
            bus.on('login-success-app',(res) => {
                // 获取选中星座的数据
                getContent(self,selectConstellation)
            },'app')
        },
        /**
         * 初始化页面
         * @param {*} options
         */
        onLoad(options){
		    console.log('onLoad-------------------------------参数：',options)
            me.init.call(this)
            console.log('-------------------------------------',this)
        },
        /**
         * 上报formId
         * @param {*} e
         */
        reportFormId(e){
            let formid = e.detail.formId
            API.getX610({ notShowLoading: true, formid: formid })
        },
        /**
         * 前往更多运势
         */
        goLuck(){
            
        }
    }
}

module.exports = methods()
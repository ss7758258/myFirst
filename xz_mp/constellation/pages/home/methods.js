const bus = require('../../event')

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
        }
    }
}

module.exports = methods()
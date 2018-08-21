// 用户信息上传次数
let userInfoLen = 0;
let $vm = getApp()

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/;

module.exports = {
    /**
     * 数据分析
     * @param {*} self 当前调用的Page对象
     * @param {*} options 当前load方法的参数
     * @param {*} pageFrom 来源地址
     * @param {*} mta 数据分析
     */
    analytics (self,options,pageFrom,mta){
        // 初始化页面分析
        mta.Page.init()
        // 存储来源信息
        self.setData({
            fromPage: pageFrom || 'share'
        })
        if(options.from === 'qrcode'){
            self.setData({
                "navConf.root": '/pages/home/home'
            })
            mta.Event.stat("ico_in_from_shake_qrcode", {})
        }
        if (pageFrom == 'share') {
            self.setData({
                "navConf.root": '/pages/home/home'
            })
            if (options.where == 'list') {
                mta.Event.stat("ico_in_from_list", {})
            } else if (options.where == 'detail') {
                mta.Event.stat("ico_in_from_detail", {})
            } else if (options.where == 'shake') {
                if (options.hotapp == 1) {
                    mta.Event.stat("ico_in_from_shake_qrcode", {})
                } else {
                    mta.Event.stat("ico_in_from_shake", {})
                }
            }
        } else if (pageFrom == 'activity') {
            self.setData({
                "navConf.root": '/pages/home/home?share_notice=shake'
            })
            mta.Event.stat("ico_in_from_shake_activity", {})
        } else if (pageFrom == 'outer' && options.id) {
            self.setData({
                "navConf.root": '/pages/home/home?share_notice=shake'
            })
            if (reg.test(options.id)) {
                mta.Event.stat('outer_' + options.id, {})
            } else {
                mta.Event.stat('outer_unknown', {})
            }
        } else if(pageFrom === 'spread'){ // 活动推广统计
            self.setData({
                "navConf.root": '/pages/home/home?share_notice=shake'
            })
			console.log('输出活动来源',options.id)
            if (reg.test(options.id)) {
                mta.Event.stat('spread_' + options.id, {})
            } else {
                mta.Event.stat('spread_unknown', {})
            }
        }
        // 统计特殊来源
        if(options.source && options.source.constructor === String && options.source !== ''){
            self.setData({
                "navConf.root": '/pages/home/home?share_notice=shake'
            })
			console.log('输出活动来源',options.id)
            if (reg.test(options.id)) {
                mta.Event.stat(options.source + '_' + options.id, {})
            } else {
                mta.Event.stat(options.source + '_unknown', {})
            }
        }
    },
    /**
     * 上传用户信息 失败后重新调用一次
     * @param {*} res
     * @param {*} id
     * @param {*} cb
     */
    setUserInfo (res,id,cb){
        let me = this
        console.log(res)
        // 保存用户信息
        $vm.api.getSelectx100({
            constellationId: id,
            nickName: res.userInfo.nickName,
            headImage: res.userInfo.avatarUrl,
            notShowLoading: true,
        }).then(res =>{
            userInfoLen = 0
            cb && cb.constructor === Function ? cb(res) : ''
        }).catch(err => {
            if(userInfoLen > 1){
                userInfoLen = 0
                return false
            }
            me.setUserInfo(res,id,cb)
        })
    }
}
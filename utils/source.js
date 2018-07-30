const mta = require('./mta_analysis')

// 验证Id是否位6位纯数字
let reg = /^\d{6}$/

module.exports = {
    // 参数来源
    sourceHandle (options){
        // 分享来源
        if(options.shareform){
            mta.Event.stat('share_' + options.shareform, {})
        }
        // 用户来源
        if (reg.test(options.id)) {
			mta.Event.stat(options.source + '_' + options.id, {})
		}else if(reg.test(options.tid)){
            mta.Event.stat(options.source + '_' + options.tid, {})
        } else {
			mta.Event.stat(options.source + '_unknown', {})
		}
    }
}
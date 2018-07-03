const config = {
    domain : 'https://micro.yetingfm.com/appwall'
}

/**
 * 处理数据信息之后回调
 * @param {*} res
 * @param {*} cb
 * @returns
 */
function successHandleCbData(res,cb){
    console.log('数据信息：',res, res.statusCode === 200 && res.data && res.data.code === 0)
    if(res.statusCode === 200 && res.data && res.data.code === 0){
        res = res.data.data || {}
    }else if(res.statusCode === 200 && res.data && res.data.code){
        switch (res.data.code) {
            case 10005:
                console.log('错误日志：',res.data.msg)
                break;
            default:
                console.log('错误日志：',res.data.msg)
                break;
        }
    }
    return cb(res)
}
/**
 * 异常请求处理回调
 * @param {*} err
 * @param {*} cb
 * @returns
 */
function errorHandle(err,cb){
    return cb(err)
}

function $http(url = '',method = 'post',data = {}){
    return new Promise((resolve,reject) => {
        wx.request({
            url: config.domain + url,
            method: method.toUpperCase(),
            data: data,
            header: {
                'content-type': method === 'POST' ? 'application/json' : 'application/x-www-form-urlencoded'
            },
            success: (res) => {
                successHandleCbData(res,resolve)
            },
            fail: (err) => {
                errorHandle(err,reject)
            }
        });
    })
}

// 方法
const methods = {
    /**
     * 获取banner列表信息
     * @param {*} [data={}]
     */
    getBannerList(data = {}){
        return $http('/front/resource/list','get',data)
    },
    /**
     * 上报信息
     * @param {*} [data={}]
     * @returns
     */
    upAnalytics(data = {}){
        return $http('/front/report','POST',data)
    }
}

module.exports = methods
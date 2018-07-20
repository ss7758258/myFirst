const bus = require('../event')
const Storage = require('../utils/storage')
let $vm = null

const methods = () => {
    let conf = {
        /**
         * 检测是否登录
         */
        checkLogin(num = 0){
            Storage.isLogin = false
            wx.getSetting({
                success (res) {
                    console.log('用户授权信息：',!res.authSetting['scope.userInfo'])
                    if(!res.authSetting['scope.userInfo']){
                        console.log('用户尚未授权')
                        // 用户未授权
                        bus.emit('no-login-app', res , 'app')
                    }else{
                        wx.getUserInfo({
                            withCredentials: true,
                            success(res){
                                console.log('检查登录中的用户信息：',res)
                                wx.setStorageSync('userInfo',res.userInfo)
                                let userConf = wx.getStorageSync('userConfig')
                                
                                if(res && res.userInfo){
                                    if(userConf && userConf !== ''){
                                        let keyNames = Object.keys(userConf.userInfo)
                                        let flag = false
                                        // res.userInfo.nickName = 12345
                                        keyNames.forEach(v => {
                                            userConf.userInfo[v] !== res.userInfo[v] ? flag = true : ''
                                        })
                                        if(flag){
                                            console.log('用户信息有变动')
                                            // 执行静默登录方案
                                            console.log(conf)
                                            conf.silentLogin()
                                            return
                                            // 用户未授权
                                            // bus.emit('no-login-app', res , 'app')
                                        }
                                        console.log('用户的keys : ',keyNames)
                                        Storage.token = userConf.token
                                        Storage.openId = userConf.openId
                                        Storage.userInfo = userConf.userInfo
                                        Storage.isLogin = true
                                        bus.emit('login-success', {} , 'login-com')
                                        
                                    }else{
                                        // 当前用户不存在登录信息
                                        console.log('当前用户不存在登录信息')
                                        bus.emit('no-login-app', res , 'app')
                                    }
                                }else{
                                    console.log('未加载到用户信息')
                                    // 用户未授权
                                    bus.emit('no-login-app', res , 'app')
                                }
                            },
                            fail(err){
                                console.log('用户未点击授权')
                                // 用户未授权
                                bus.emit('no-login-app', res , 'app')
                            }
                        });
                    }
                },
                fail(err){
                    num ++ 
                    if(num > 5){
                        return
                    }
                    // 重新尝试检查微信授权
                    this.checkLogin(num)
                }
            })
        },
        /**
         * 静默登录
         * @param {*} cb //登录完成的回调
         */
        silentLogin(cb){
            $vm = getApp()
            $vm.getLogin().then(res => {
                console.log(`登录成功：`,res)
                // 缓存关键数据
                Storage.token = res.token
                wx.setStorageSync('token',res.token)
                Storage.sessionKey = res.sessionKey
                Storage.openId = res.openId
                wx.setStorageSync('openId',res.openId)

                console.log(Storage)
                // 获取用户信息进行上报
                wx.getUserInfo({
                    withCredentials: true,
                    success(data){
                        console.log('静默登录--------------------输出用户信息：',data)
                        Storage.userC = {
                            token : res.token,
                            openId : res.openId,
                            userInfo : data.userInfo
                        }
                        wx.setStorageSync('userInfo',data.userInfo)
                        Storage.userInfo = data.userInfo
                        let silent = true
                        // 确定触发消息
                        bus.emit('load-userinfo-success', {data,silent,cb} , 'login-com')
                    },
                    fail (err){
                        console.log('静默登录--------------------未加载到用户信息')
                        // 用户未授权
                        bus.emit('no-login-app', res , 'app')
                    }
                })

            }).catch(err => {
                console.log('静默登录--------------------登录失败')
                // 用户未授权
                bus.emit('no-login-app', res , 'app')
            })
        }
    }
    return conf
}

module.exports = methods()
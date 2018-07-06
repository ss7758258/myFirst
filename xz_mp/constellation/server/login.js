const bus = require('../event')
const Storage = require('../utils/storage')
let $vm = null

const methods = () => {
    let conf = {
        /**
         * 检测是否登录
         */
        checkLogin(num = 0){
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
                                let userConf = wx.getStorageSync('userConfig')
                                if(res && res.constructor === Object && res.userInfo){
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
                                
                                // // token创建时间
                                // let tokenStart = wx.getStorageSync('token-create')
                                // let t = 1296000000,dt = new Date();//86400000 2592000000 1296000000
        
                                // // 如果token大于30天则重新登录
                                // if(loginData && tokenStart && (dt.getTime() - tokenStart) < t){
                                //     // 将用户信息放入缓存
                                //     Storage.userInfo = res.userInfo
                                //     // 拿取token
                                //     Storage.token = loginData.token
                                //     bus.emit('login-success', res , 'login')
                                // }
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
        silentLogin(){
            $vm = getApp()
            $vm.getLogin().then(res => {
                console.log(`登录成功：`,res)
                // 缓存关键数据
                Storage.token = res.token
                Storage.sessionKey = res.sessionKey
                Storage.openId = res.openId

                console.log(Storage)
                // 获取用户信息进行上报
                wx.getUserInfo({
                    withCredentials: true,
                    success(data){
                        console.log('静默登录--------------------输出用户信息：',data)
                        Storage.userC = data
                        Storage.userInfo = data.userInfo
                        let silent = true
                        // 确定触发消息
                        bus.emit('load-userinfo-success', {data,silent} , 'login-com')
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
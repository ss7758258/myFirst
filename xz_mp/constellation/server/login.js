const bus = require('../event')
const Storage = require('../event')

const methods = () => {
    return {
        /**
         * 检测是否登录
         */
        checkLogin(){
            wx.getSetting({
                success (res) {
                    console.log('用户授权信息：',!res.authSetting['scope.userInfo'])
                    if(!res.authSetting['scope.userInfo']){
                        // 用户未授权
                        bus.emit('no-login', res , 'login')
                        wx.navigateTo({
                            url: '/pages/checklogin/checklogin'
                        });
                    }else{
                        wx.getUserInfo({
                            withCredentials: true,
                            success(res){
                                // wx.setStorageSync(res.userInfo.avatarUrl, '6651b1d47bc40e30cee21e79ea04d785');
                                console.log('用户详情信息：',res)
                                // 判断用户的头像是否为空
                                res.userInfo.avatarUrl = res.userInfo.avatarUrl !== '' ? res.userInfo.avatarUrl : res.userInfo.nickName
                                // 拉取缓存下的token
                                let loginData = wx.getStorageSync(res.userInfo.avatarUrl)
                                // token创建时间
                                let tokenStart = wx.getStorageSync('token-create')
                                let t = 1296000000,dt = new Date();//86400000 2592000000 1296000000
        
                                // 如果token大于30天则重新登录
                                if(loginData && tokenStart && (dt.getTime() - tokenStart) < t){
                                    // 将用户信息放入缓存
                                    Storage.userInfo = res.userInfo
                                    // 拿取token
                                    Storage.token = loginData.token
                                    bus.emit('login-success', res , 'login')
                                }else{
                                    // 用户未登录
                                    bus.emit('no-login', res , 'login')
                                    wx.navigateTo({
                                        url: '/pages/checklogin/checklogin'
                                    });
                                }
                            },
                            fail(err){
                                // 用户未授权
                                bus.emit('no-login', res , 'login')
                                wx.navigateTo({
                                    url: '/pages/checklogin/checklogin'
                                });
                            }
                        });
                    }
                },
                fail(err){
                    this.checkLogin()
                }
            })
        }
    }
}

module.exports = methods()
module.exports = {
    // 登录状态
    loginStatus : false,
    // 用户加密信息上报状态
    loginForMore : false,
    // 初始化状态信息
    init (){
        this.loginStatus = false
        this.loginForMore = false
        this.sessionKey = null
        this.prevPic = null
    }
}
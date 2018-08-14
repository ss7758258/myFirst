const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')
const p = require('./json')
const starNum = 1
let current = 1

const pageConf = {

    data: {
        navConf: {
            title: '朋友圈配对',
            state: 'root',
            isRoot: false,
            isIcon: true,
            root: '',
            isTitle: true,
        },
        // 根据导航高度做出适配
        height : 64,
        // 是否是自己
        isMe : true,
        // 选中的块
        current : '',
        IPX : false,
        // loading 是否正在执行
        loading : true,
        // 是否无数据
        noList : true,
        starXz : {},
        // 描述文字
        text : '我',
        pairList : [],
        // 自己的openId
        openId : '',
        // 分享的id
        shareOpenId : '',
        // 是否存在下一页
        hasNext : true,
        // 分享者的信息
        account : {
            pairNickName : '',
            sex : '',
            head : ''
        },
        // preview预览
        preview : false,
        // 预览图片地址
        previewSrc : ''
    },

    onLoad: function(options) {
        // this._drawCode()
        let self = this
        let starXz = wx.getStorageSync('selectConstellation') || {
            id: 1,
            name: '白羊座',
            time: '3.21~4.19',
            startTime: '3月21日',
            endTime: '4月19日',
            bgcolor: '#FFF1D8',
            bgc: 'rgba(255,192,64,0.1)',
            color: '#F08000',
            img: '/assets/img/1.svg'
        }
        this.setData({
            starXz
        })
        this._handleShare()
        wx.getStorage({
            key : 'openId',
            success (res){
                console.log(res)
                self.setData({
                    openId : res.data || ''
                })
                if(res.data === self.data.shareOpenId || self.data.shareOpenId === ''){
                    self.setData({
                        isMe : true
                    })
                }else{
                    self.setData({
                        text : 'TA',
                        isMe : false
                    })
                }
                // 获取好友列表
                self._getPairList(1)
            },
            fail(err){
                
            }
        })
    },

    onShow: function() {

    },

    onHide: function() {

    },

    onShareAppMessage: function() {
        console.log('/pages/home/home?to=pairCus&from=share&source=share&id=999997&tid=123455&shareform=pairCus&m=0&openId=' + this.data.openId + '&cid=' + this.data.starXz.id + '&sex=' + Storage.AccountSex)
        return {
			title : '这不是简单的句子，全是你内心的独白',
            imageUrl: '/assets/images/share-brief.jpg',
			path : '/pages/home/home?to=pairCus&from=share&source=share&id=999997&tid=123455&shareform=pairCus&m=0&openId=' + this.data.openId + '&cid=' + this.data.starXz.id + '&sex=' + Storage.AccountSex,
		}
    },
    // 处理分享参数
    _handleShare(){
        let opts = this.options
        console.log(this.options,this)
        if(opts.from === 'share' && opts.to === 'pairCus'){
            this.setData({
                shareOpenId : opts.openId,
                'account.id' : opts.cid,
                'account.sex' : opts.sex,
            })
        }else{
            this.setData({
                shareOpenId : ''
            })
        }
    },
    // 前往配对页面
    _goPairWX(e){
        console.log(e)
        if(this.data.loading){
            return
        }
        let {item : data,index} = e.currentTarget.dataset
        console.log(data,index)
        
        Storage.SharePairList = [{
            id : this.data.starXz.id,
            sex : Storage.AccountSex || 'woman',
            name :  Storage.userInfo.nickName
        },{
            id : this.data.account.pairConstellationId || 1,
            sex : this.data.account.sex || 'woman',
            name : this.data.account.pairNickName || ''
        }]

        if(data){
            if(this.data.openId === this.data.shareOpenId || this.data.shareOpenId === ''){
                if(!data.unlock){
                    deLock(this,data,index)
                    return
                }
                Storage.SharePairList = [{
                    id : data.constellationId,
                    sex : data.sex == 1 ? 'man' : 'woman',
                    name :  data.nickName
                },{
                    id : this.data.starXz.id,
                    sex : Storage.AccountSex || 'woman',
                    name :  Storage.userInfo.nickName
                }]
            }
            wx.navigateTo({
                url:'/pages/components/pages/pairResult/result'
            })
        }else{
            API.getFriendpair({
                notShowLoading : true,
                bypair : this.data.shareOpenId,
                sex : Storage.AccountSex === 'man' ? 1 : 2,
                startpage : 1
            }).then(res => {
                console.log('上报结果',res)
                wx.navigateTo({
                    url:'/pages/components/pages/pairResult/result'
                })
            }).catch(err => {
                wx.showToast({
                    title : '小主，有点小错误呦',
                    icon: 'none',
                    mask : true,
                    duration : 1600
                })
            })
        }
    },
    // 下一页的数据
    _nextList(){
        console.log('进入下一页')
        if(!this.data.hasNext){
            wx.showToast({
                title : '小主，没有更多了',
                icon: 'none',
                mask : true,
                duration : 1600
            })
            return
        }
        this._getPairList(current++)
    },
    // 绘制图片生成图片并将图片展示到页面上
    _drawCode(){
        let head = ''
        let self = this
        wx.showToast({
            title : '生成图片中...',
            icon : 'loading',
            mask : true
        })
        wx.request({
            url : 'https://cli.im/mina/generate_qrcode',
            header: {
				'Content-Type': 'application/x-www-form-urlencoded',
			},
            data : {
                tpl_id : '16747',
                code_type : 'qrcode',
                'param_value[0]' : 'pairCus',
                'param_value[1]' : 'share',
                'param_value[2]' : this.data.openId,
                'param_value[3]' : Storage.AccountSex || 'woman',
                'param_value[4]' : this.data.starXz.id || 1,
            },
            success(res){
                console.log(res)
                if(res && res.statusCode === 200 && res.data){
                    let data = res.data
                    if(data.code === 1 && data.data){
                        head = data.data
                        wx.getImageInfo({
                            //将二维码转路径
                            src: head, //图片的路径，可以是相对路径，临时文件路径，存储文件路径，网络图片路径,
                            success: res => {
                                console.log('头像本地路径', res.path)
                                // 画图
                                const ctx = wx.createCanvasContext('shareCanvas')
                
                                ctx.drawImage('/pages/components/source/canvas-bg.png', 0, 0, 690, 890) //背景图
                                
                                ctx.save()
                                ctx.rect(286,664,120,120)
                                ctx.clip()
                                ctx.drawImage(res.path, 286, 664, 120, 140) //背景图
                                ctx.restore()

                                ctx.setFillStyle('#666666')
                                ctx.setFontSize(28)
                                ctx.setTextAlign('center')
                                ctx.fillText('长按二维码，看看我们搭不搭', 690 / 2, 824)
                                
                                ctx.draw()
                                console.log('画图完成===================')
                
                                setTimeout(function () {
                                    wx.canvasToTempFilePath({
                                        canvasId: 'shareCanvas',
                                        success: function (res) {
                                            console.log(res.tempFilePath)
                                            wx.saveImageToPhotosAlbum({
                                                filePath: res.tempFilePath,
                                                success(data) {
                                                    self.setData({
                                                        preview : true,
                                                        previewSrc : res.tempFilePath
                                                    })
                                                    wx.hideLoading()
                                                },
                                                fail() {
                                                    wx.showToast({
                                                        title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                                                        icon: 'none',
                                                        duration: 1700
                                                    })
                                                }
                                            })
                                        },
                                        fail: function (res) {
                                            console.log(res)
                                            wx.showToast({
                                                title: '图片保存失败，请检查右上角关于小哥星座的设置中查看是否开启权限',
                                                icon: 'none',
                                                duration: 1700
                                            })
                                            // wx.hideLoading()
                                        },
                                    })
                                }, 1000)
                            },
                            fail(){
                                wx.showToast({
                                    title: '生成分享信息失败',
                                    icon: 'none',
                                    duration: 1700,
                                    mask: true
                                })
                            }
                        })
                    }else{
                        wx.showToast({
                            title: '生成分享信息失败',
                            icon: 'none',
                            duration: 1700,
                            mask: true
                        })
                    }
                }else{
                    wx.showToast({
                        title: '生成分享信息失败',
                        icon: 'none',
                        duration: 1700,
                        mask: true
                    })
                }
            },
            fail(){
                wx.showToast({
                    title: '生成分享信息失败',
                    icon: 'none',
                    duration: 1700,
                    mask: true
                })
            }
        })
    },
    // 获取配对列表
    _getPairList(page){
        let self = this
        console.log('分享者的Id:',this.data.shareOpenId)
        API.getPairList({
            notShowLoading : true,
            openid : this.data.shareOpenId !== '' ? this.data.shareOpenId : this.data.openId,
            startpage : page
        }).then(res => {
            console.log(res)
            // res.list = []
            // res.list = [{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":true},{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false},{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false},{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false},{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false},{"byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false},{"byOpenId":"omONY5HlUp8VMMS14Jj4Wla_t5-c","constellationId":1,"generalTxt":"犹如磁铁的两端，吸引又排斥，讨厌又喜欢","headImage":"null","nickName":"null","openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U","pairScore":60,"sex":2,"unlock":false}]
            if(res){
                let data = {
                    noList : false,
                    loading : false,
                    account : self.data.account
                }
                try {
                    if(res.list.length <= 0 && page === 1){
                        data.noList = true
                    }else{
                        if(!wx.getStorageSync('openSharePair')){
                            wx.setStorage({
                                key : 'openSharePair',
                                data : true
                            })
                        }
                        data.hasNext = res.hasnextpage || false
                        page !== 1 ? data.pairList = self.data.pairList.concat(res.list) : data.pairList = res.list
                        data.account.pairNickName = res.pairNickName
                        data.account.pairImage = res.pairImage
                        data.account.pairConstellationId = res.pairConstellationId
                    }
                    data.current = 'current'
                    self.setData(data)
                } catch (error) {
                    wx.showToast({
                        title : '小主，系统出了点故障，请重新尝试',
                        icon: 'none',
                        mask : true,
                        duration : 1600
                    })
                }
            }
        }).catch(err => {
            wx.showToast({
                title : '小主，有点小错误呦',
                icon: 'none',
                mask : true,
                duration : 1600
            })
        })
    },
    // 关闭海报
    _close(){
        this.setData({
            preview : false
        })
    },
    // 图片加载完成是关闭loading并且展示
    _drawImg(){
        wx.hideToast()
    },
    // 根据导航设置高度
    _setHeight(e){
        let temp = e.detail || 64 
        this.setData({
            height:temp,
            IPX : temp === 64 ? false : true
        })
    },
    /**
     * 上报formId
     * @param {*} e
     */
    _reportFormId(e){
        console.log(e.detail.formId)
        let formid = e.detail.formId
        API.getX610({ notShowLoading: true, formid: formid })
    }
}

// 解锁操作
function deLock(self,data,index){
    wx.showModal({
        title: '确定快速查看？',
        content: '快速查看需要消耗' + starNum + '颗小星星',
        showCancel: true,
        cancelColor: '#999999',
        cancelText: '我再想想',
        confirmText: '确定',
        confirmColor: '#9262FB',
        success: function (res) {
            console.log(res)
            mta.Event.stat('pay_click_success', {})
            if (res.confirm) {
                API.getBlance({ notShowLoading: true }).then(result => {
                    if (!result) {
                        return
                    }
                    console.log('钱包星星数量：', result)
                    // data.balance = 2000
                    // 当小星星不足时进行提示
                    if (result.balance < starNum) {
                        
                        mta.Event.stat('pay_fail', {})
                        
                        if(!Storage.isLogin){
                            return
                        }
                        
                        wx.showModal({
                            title: '余额不足',
                            content: '请先去获取一些小星星吧',
                            showCancel: true,
                            cancelColor: '#999999',
                            cancelText: '我再想想',
                            confirmText: '立即获取',
                            confirmColor: '#9262FB',
                            success: function (res) {
                                if (res.confirm) {
                                    // 跳转到小星星页面
                                    wx.navigateTo({
                                        url: '/pages/banner/banner'
                                    })
                                }
                            }
                        })
                    } else {

                        mta.Event.stat('pay_success', {})
                        wx.showLoading({
                            title : '解锁中...'
                        })
                        API.delock({
                            notShowLoading : true,
                            byunlock : data.byOpenId
                        }).then(res => {
                            console.log(res)
                            if(res.retcode != -2){
                                wx.showToast({
                                    title : res.retmsg,
                                    icon: 'none',
                                    mask : true,
                                    duration : 1600
                                })
                                return
                            }
                            console.log(`pairList[${index}].unlock`)
                            self.setData({
                                [`pairList[${index}].unlock`] : true
                            })
                            wx.hideLoading()
                        }).catch(err => {
                            console.log(err)
                            wx.showToast({
                                title : err,
                                icon: 'none',
                                mask : true,
                                duration : 1600
                            })
                        })
                    }
                })
            }
        }
    })
}


Page(pageConf);
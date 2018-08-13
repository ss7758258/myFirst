const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')

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
        selfOpenId : '',
        // 分享者的信息
        account : {
            name : '',
            sex : '',
            head : ''
        }
    },

    onLoad: function(options) {
        let self = this
        this._handleShare()
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
        wx.getStorage({
            key : 'openId',
            success (res){
                console.log(res)
                if(res.data === self.data.selfOpenId){
                    self._getPairList(1)
                }else{
                    self.setData({
                        text : 'TA'
                    })
                    // 获取好友列表
                    self._getPairList(1,2)
                }
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
        return {
			title : '这不是简单的句子，全是你内心的独白',
            imageUrl: '/assets/images/share-brief.jpg',
			path : '/pages/home/home?to=pairCus&from=share&source=share&id=999997&tid=123455&shareform=pairCus&m=0&openId=' + this.data.selfOpenId,
		}
        // '/pages/home/home?to=brief&from=share&source=share&id=999998&tid=123455&shareform=brief&m=0',
    },
    // 处理分享参数
    _handleShare(){
        let opts = this.options
        console.log(this.options,this)
        if(opts.from === 'share' && opts.source === 'share' && opts.shareform === 'pairCus'){
            this.setData({
                selfOpenId : opts.openId
            })
        }else{
            this.setData({
                selfOpenId : wx.getStorageSync('openId')
            })
        }
    },
    // 获取配对列表
    _getPairList(page,type = 1){
        let self = this
        API[type === 1 ? 'getPairList' : 'getFriendpair']({
            notShowLoading : true,
            constellationId : this.data.starXz.id,
            startpage : page
        }).then(res => {
            console.log(res)
            res.list = [{
                "byOpenId":"omONY5HlUp8VMMS14Jj4Wla_t5-c",
                "constellationId":7,
                "generalTxt":"相当理想的一对",
                "headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIzetd1l0aUDWia90HbAPBCgELtgywcMOsbArbCTWpuWWf255zndyYDfDgP8pBxW10PIQU0h0j0mKg/132",
                "nickName":"SG9uZQ==",
                "openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U",
                "pairScore":80,
                "sex":2,
                "unlock":false
            },{
                "byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U",
                "constellationId":7,
                "generalTxt":"相当理想的一对",
                "headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIzetd1l0aUDWia90HbAPBCgELtgywcMOsbArbCTWpuWWf255zndyYDfDgP8pBxW10PIQU0h0j0mKg/132",
                "nickName":"SG9uZQ==",
                "openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U",
                "pairScore":30,
                "sex":2,
                "unlock":true
            },{
                "byOpenId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U",
                "constellationId":7,
                "generalTxt":"相当理想的一对",
                "headImage":"https://wx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEIzetd1l0aUDWia90HbAPBCgELtgywcMOsbArbCTWpuWWf255zndyYDfDgP8pBxW10PIQU0h0j0mKg/132",
                "nickName":"SG9uZQ==",
                "openId":"omONY5NNuhisWGf8zp6ZNGIFdQ4U",
                "pairScore":80,
                "sex":2,
                "unlock":false
            }]
        
            console.log(res)
            if(res){
                let data = {
                    noList : false,
                    loading : false,
                    account : {}
                }
                try {
                    if(res.list.length <= 0 && page === 1){
                        data.noList = true
                    }else{
                        data.hasNext = res.hasnextpage || false
                        page !== 1 ? data.pairList = self.data.pairList.concat(res.list) : data.pairList = res.list
                        data.account.pairNickName = res.pairNickName
                        data.account.pairImage = res.pairImage
                    }
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

        })
    },
    // 根据导航设置高度
    _setHeight(e){
        let temp = e.detail || 64 
        this.setData({
            height:temp,
            IPX : temp === 64 ? false : true
        })
    }
}




Page(pageConf);
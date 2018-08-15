const Storage = require('.././../../../utils/storage')
const star = require('../../../../utils/star')
const API = require('../../../../utils/api')
let timerNum = null

const conf = {

    data: {
        navConf: {
            title: '配对结果',
            state: 'root',
            isRoot: false,
            isIcon: true,
            root: '',
            bg : '#9262FB',
            isTitle: true,
        },
        star,
        lists: [{
            id : 1,
            sex : 'woman'
        },{
            id : 12,
            sex : 'man',
            name : '小'
        }],
        userInfo:{
            nickName: ''
        },
        // 过渡时间
        timer: 0.5,
        result : [{
            // 默认第二段的过渡时间
            kt : 0,
            // 文案
            pairStr : '配对指数',
            pairNum : 0,
            num : 0,
            circles : [0,0.000000001],
        }],
        pair : {
            friendTxt : '虽没有一见钟情，但纸短情长也是一种美',
            // 两情相悦
            lqxyScore : 4,
            // 天长地久
            tcdjScore : 3
        },
        // 超过50%的情况下必须等待上半圈的动画结束才能执行
        transitionend : false,
        // 默认导航高度
        height : 64,
        // 是否iPhone X
        IPX : false
    },

    onLoad: function(options) {
        this._methods.initStar.call(this)
    },
    // 方法集合
    _methods: {
        // 初始化用户信息
        initStar(){
            let self = this
            let lists = Storage.SharePairList || []
            if(lists.length > 0 && lists.length === 2){
                this.setData({
                    lists,
                    userInfo : Storage.userInfo
                })
                // 获取配对的详情信息
                this._methods.getPair.call(this)
            }else{
                wx.showToast({
                    title:'加载失败',
                    icon:'none',
                    duration : 1600
                })
            }
            
        },
        // 获取配对结果
        getPair(){
            let self = this
            
            let params = { 
                notShowLoading:true,
                maleConstellationId: this.data.lists[0].id, 
                femaleConstellationId: this.data.lists[1].id
            }
            if(this.data.lists[0].sex === this.data.lists[1].sex){
                self.setData({
                    pair : {
                        friendTxt : '同性间的爱已超脱世俗，不计分数，暂且祝你们幸福咯',
                        lqxyScore : 0,
                        tcdjScore : 0
                    }
                })
                return
            }
            API.pair(params).then(res => {
                console.log('最佳匹配星座：',res)
                if(res && res.constructor === Object){
                    self.setData({
                        pair : res
                    })
                    conf._methods.setClip(res,self)
                }
            }).catch(err => {
                wx.showToast({
                    title:'获取失败',
                    icon:'none',
                    duration : 1600
                })
            })
        },
        // 设置圆环信息
        setClip(res,self){
            let pairNum = res.pairScore && res.pairScore === 0 ? 0.0000001 : res.pairScore
            let temp = {}
            let num = 0
            let t = (self.data.timer * 1000) / 50
            let len = 0
            temp['result[0].pairNum'] = pairNum
            if(pairNum > 50){
                temp['result[0].circles'] = [pairNum - 50, 50]
                num = pairNum - 50
            }else{
                temp['result[0].circles'] = [0, pairNum]
                num = 0
            }
            let kt = num > 0 ? (t * num + 200) / 1000 : 0
            temp['result[0].kt'] = kt
            
            self.setData(temp)
            timerNum = setInterval(() => {
                len += 1
                if(len >= pairNum){
                    clearInterval(timerNum)
                    self.setData({
                        ['result[0].num'] : pairNum
                    })
                    return
                }
                self.setData({
                    ['result[0].num'] : len
                })
            }, (self.data.timer + kt ) * 1000 / pairNum + 2)
        }
    },
    // 当动画结束时继续执行下一波操作
    _stopRotate(e){
        console.log('输出当前结束动画内容：',e)
        let {index} = e.currentTarget.dataset
        let res = this.data.result[index]
        if(res.circles[0] > 0){
            let str = `result[${index}].circles`
            console.log(str)
            this.setData({
                [str] : [res.circles[0],0],
                transitionend : true,
            })
        }
    },
    // 返回首页
    _goHome(){
        wx.reLaunch({
            url : '/pages/home/home'
        })
    },
    // 前往自定义配对圈子
    _goPairWX(){
        wx.reLaunch({
            url : '/pages/components/pages/pairCus/pairCus'
        })
    },
    // 根据导航高度设置具体展示高度
    _setHeight(e){
        let temp = e.detail || 64
        this.setData({
            height : temp,
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
Page(conf);
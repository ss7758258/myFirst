const Storage = require('.././../../../utils/storage')
const star = require('../../../../utils/star')
let timerNum = null

const conf = {

    data: {
        navConf: {
            title: '配对结果',
            state: 'root',
            isRoot: false,
            isIcon: true,
            root: '',
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
        // 默认第二段的过渡时间
        kt : 0,
        pairNum : 90,
        num : 0,
        // 文案
        pairStr : '配对指数',
        circles : [0,0.000000001],
        // 超过50%的情况下必须等待上半圈的动画结束才能执行
        transitionend : false
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
            }
            let num = 0;
            setTimeout(() => {
                let pairNum = self.data.pairNum
                if(pairNum > 50){
                    self.setData({
                        circles : [pairNum - 50, 50]
                    })
                }else{
                    self.setData({
                        circles : [0,pairNum]
                    })
                }
                let num = this.data.circles[0];
                let t = (this.data.timer * 1000) / 50
                let kt = (t * num + 200) / 1000
                console.log(kt)
                kt = t == 0 ? 0 :  kt
                console.log(kt)
                self.setData({
                    kt
                })
                let te = (this.data.timer + this.data.kt) * 1000  / pairNum
                console.log((this.data.timer + this.data.kt) * 1000  / pairNum )
                timerNum = setInterval(() => {
                    if(num >= pairNum){
                        self.setData({
                            num : pairNum
                        })
                        clearInterval(timerNum)
                        return
                    }
                    num += 1
                    self.setData({
                        num : num
                    })
                },te)
            },500)
        }
    },
    // 当动画结束时继续执行下一波操作
    _stopRotate(e){
        console.log('输出当前结束动画内容：',e)
        if(this.data.circles[0] > 0){
            this.setData({
                transitionend : true,
                // kt,
                circles : [this.data.circles[0],0]
            })
        }
    }
}
Page(conf);
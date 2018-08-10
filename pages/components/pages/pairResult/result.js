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
        result : [{
            // 默认第二段的过渡时间
            kt : 0,
            // 文案
            pairStr : '配对指数',
            pairNum : 75,
            num : 0,
            circles : [0,0.000000001],
        }],
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
            setTimeout(() => {
                let res = self.data.result[0]
                let pairNum = res.pairNum
                let temp = {}
                let num = 0
                let t = (self.data.timer * 1000) / 50
                let len = 0
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
                }, (self.data.timer + kt) * 1000 / pairNum)
            },300)
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
    }
}
Page(conf);
let $vm = getApp()

Page({
    data: {
        navConf: {
            title: '运势',
            state: 'root',
            isRoot: false,
            isIcon: true,
            iconPath: '',
            root: '',
            isTitle: true,
            centerPath: '/pages/center/center'
        },
        headerlist: ['今日运势', '本周运势', '本月运势'], //导航栏数据
        current: 0, //导航栏下标
        contentlist: ['综合指数', '爱情指数', '财富指数', '工作指数'],
        list:{       //运势详情接口数据
           day:{
               summary_star:5, //综合指数
               love_star:5,     //爱情指数
               money_star:5,    //财富指数
               work_star:5,     //工作指数
               grxz:'处女座',   //贵人星座
               lucky_num:9,     //幸运数字
               lucky_time:'2018.07.18',
               lucky_direction:'left',
               day_notice:'aaa',
               general_txt:'fefefef',//运势简评
               love_txt:'defeuifeufhe',//爱情运势
               work_txt:'dhwuidwuihdiud',//工作运势
               money_txt:'hahahahhahah',//财富运势
               time: '2018.07.18',
               lucky_color:'red',
           },
           week: {
               summary_star: 5,
               love_star: 5,
               money_star: 5,
               work_star: 5,
               grxz: '处女座',
               xrxz:'处女座',
               lucky_num: 9,
               lucky_day: '2018.07.18',
               lucky_direction: 'left',
               week_notice: 'aaa',
               general_txt: 'fefefef',
               love_txt: 'defeuifeufhe',
               work_txt: 'dhwuidwuihdiud',
               money_txt: 'hahahahhahah',
               health_txt:'hdiwhuidwuid',
               time: '2018.07.18',
               lucky_color: 'red',
           },
           month: {
               summary_star: 5,
               love_star: 5,
               money_star: 5,
               work_star: 5,
               grxz: '处女座',
               xrxz: '处女座',
               yfxz:'处女座',
               lucky_direction: 'left',
               month_advantage:'本月优势',
               month_weakness:'本月弱势',
               week_notice: 'aaa',
               general_txt: 'fefefef',
               love_txt: 'defeuifeufhe',
               work_txt: 'dhwuidwuihdiud',
               money_txt: 'hahahahhahah',
               health_txt: 'hdiwhuidwuid',
               time: '2018.07.18',
               lucky_color: 'red',
           },
        }, 
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        // this.getData()
        this.selected()
    },

    onShow: function () {
        
    },
    getData(){
        $vm.api.getMorex300({ constellationId: $vm.globalData.selectConstellation.id, notShowLoading: true, }).then(res => {
            console.log('运势详情数据：', res)
            if(res!=''){
                this.setData({
                    list: res
                })
            }
            
        }).catch(res => {
            console.log('运势详情返回报错数据：', res)
        })
    },
    // 选择运势
    selected(e){
        console.log(e)
        let current = 0, list = this.data.list.day;
        if(e){
            let index = e.target.dataset.selected
            if (index == 0) {
                current = 0
                list = this.data.list.day
            } else if (index == 1) {
                current = 1
                list = this.data.list.week
            } else {
                current = 2
                list = this.data.list.month
            }
        }
        
        this.setData({
            current:current,
            list:list
        })
        console.log(list)
        // this.getData()
        
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },


    

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    }
})
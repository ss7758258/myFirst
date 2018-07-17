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
        contentlist: [
            {name: '综合指数',count: 2},
            {name: '爱情指数',count: 3},
            {name: '财富指数',count: 4},
            {name: '工作指数',count: 5},
            {name: '幸运颜色',content: '黄色'},
            {name: '缘分星座',content: '处女座'}
        ],
        list:[], //运势详情接口数据
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        // this.getData()
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
        // console.log(e)
        let index = e.target.dataset.selected
        let current;
        if(index == 0){
            current = 0
            
        }else if(index == 1){
            current = 1
        }else{
            current = 2
        }

        this.setData({
            current:current
        })

        this.getData()
        
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
const API = require('../../../../utils/api')
const Storage = require('../../../../utils/storage')
const mta = require('../../../../utils/mta_analysis')

const pageConf = {

    data: {
        navConf: {
            title: '占卜测试',
            state: 'root',
            isRoot: false,
            isIcon: true,
            root: '',
            bg : '#9262FB',
            isTitle: true,
        },
        lists : [],
        // 图片是否加载失败
        imgs : [true],
        baseUrl : 'https://xingzuo-1256217146.file.myqcloud.com',
        // 根据导航高度做出适配
        height : 64,
        IPX : false,
        // loading 是否正在执行
        loading : true,
        // 是否无数据
        noList : false,
        // 是否存在下一页
        hasNext : true,
        nextPage : 1
    },

    onLoad: function(options) {
        mta.Page.init()
		// wx.hideShareMenu()
        this._getGameList()
    },

    onShow: function() {
    },

    onHide: function() {

    },

    onShareAppMessage: function() {
        mta.Event.stat('share_divine')
        console.log('/pages/home/home?to=divine&from=share&source=share&id=999996&tid=123454&shareform=divine')
        return {
            title : '想知道你的名字的含义么',
            imageUrl : '../../source/share_test.png',
			path : '/pages/home/home?to=divine&from=share&source=share&id=999996&tid=123454&shareform=divine'
		}
    },
    _imgError(e){
        let {index} = e.currentTarget.dataset
        this.setData({
            [`lists[${index}].pic`]: '../../source/img_error.png', 
            [`imgs[${index}]`] : true
        })
    },
    // 获取占卜游戏列表
    _getGameList(page = 1){
        API.getGameList({startpage : page,testnum:1}).then(res => {
            console.log('列表结果：',res)
            if(!res){
                return
            }
            let list = []
            console.log('页码：',page)
            if(page === 1){
                list = res.tests || []
            }else{
                list = this.data.lists.concat(res.tests || [])
            }
            this.setData({
                lists : list,
                imgs : [],
                hasNext : res.hasnextpage
            })
        }).catch(err => {
            console.log(err)
        })
    },
    // 前往下游戏界面
    _goGame(e){
        let { res : data } = e.currentTarget.dataset
        mta.Event.stat('page_divine_click',{ gameid : data.id ,gamename : data.name})
        console.log(data,data.name)
        let tmpPath = 'divine_one'
        switch (data.type) {
            case 1:
                tmpPath = 'divine_one'
                break;
            default:
                tmpPath = 'divine_two'
                break;
        }
        wx.navigateTo({
            url: `/pages/components/pages/divine/${tmpPath}?gameId=${data.id}`
        });
    },
    _goBanner(){
        mta.Event.stat('page_divine_click_banner',{})
        wx.navigateTo({
            url: '/pages/banner/banner'
        })
    },
    // 下一页的数据
    _nextList(){
        mta.Event.stat('page_divine_next',{})
        console.log('进入下一页')
        if(!this.data.hasNext){
            return
        }
        this._getGameList(nextPage++)
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

Page(pageConf);
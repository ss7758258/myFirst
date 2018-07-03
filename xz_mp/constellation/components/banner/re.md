
```

在需要调用应用墙的页面的配置中加入以下配置 ps:地址需要大兄弟你自己引用的地址
"usingComponents": {
    "banner": "/components/banner/banner"
}
之后在wxml中加入以下代码
<banner opts='{{bannerConf}}'></banner>
在js的data中加入
bannerConf : {
    appId : 'wxedc8a06ed85ce4df',
    pageNum : 1,
    pageSize : 20
}

在js的onLoad事件中加入 ps:缓存中的openId是在登录之后获取到存储到缓存中的
this.setData({
    'bannerConf.openId' : wx.getStorageSync('openId') || ''
})

PS:mta的sdk需要在analytics中填入你自己项目的引用路径，banner组件需要放在滚动容器中哦
PS: banner组件支持事件通知 需引入组件中的event.js 如：const bus = require('./event')
调用banner组件的js可以监听如下事件
事件：bus.on('resource_click',(res) => {}, 'banner-app') 资源点击后
事件：bus.on('resource_open_success',(res) => {}, 'banner-app') 资源打开成功后
事件：bus.on('resource_open_fail',(res) => {}, 'banner-app') 资源打开失败后

搞定收工
```


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

搞定收工
```

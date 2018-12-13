module.exports = {
    color : '#494C83',
    bg : '#FFFFFF',
    selectedColor : '#FF5D7C',
    borderStyle:'1px solid rgba(0,0,0,.1)',
    shadow : 'none',
    tabbar : [{
            path : 'pages/home/home',
            icon : '/assets/menu/home.png',
            selectedIcon : '/assets/menu/home-selected.png',
            type : 1,
            animate : false,
            width : 'auto',
            text : '星座',
            desc : 'home'
        },
        {
            path : 'pages/center/center',
            icon : '/assets/menu/center.png',
            selectedIcon : '/assets/menu/center-selected.png',
            type : 1,
            animate : false,
            width : 'auto',
            text : '我的',
            desc : 'center'
        }
    ]
}
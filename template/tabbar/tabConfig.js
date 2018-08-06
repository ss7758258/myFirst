module.exports = {
    color : '#464646',
    bg : '#FFFFFF',
    selectedColor : '#9262FB',
    borderStyle:'1px solid rgba(0,0,0,.1)',
    shadow : 'none',
    tabbar : [{
            path : 'pages/home/home',
            icon : '/assets/menu/home.png',
            selectedIcon : '/assets/menu/home-selected.png',
            type : 1,
            width : 'auto',
            text : '星座'
        },
        {
            path : 'pages/lot/shake/shake',
            icon : '/assets/menu/shake.png',
            selectedIcon : '/assets/menu/shake-selected.png',
            type : 2,
            width : 'auto',
            text : '必玩'
        },
        {
            path : 'pages/onebrief/brief',
            icon : '/assets/menu/yan.png',
            selectedIcon : '/assets/menu/yan-selected.png',
            type : 1,
            width : 'auto',
            text : '一言'
        }
    ]
}
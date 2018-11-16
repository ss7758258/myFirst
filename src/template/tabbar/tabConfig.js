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
            animate : false,
            width : 'auto',
            text : '星座',
            desc : 'home'
        },
        // {
        //     path : 'pages/lot/shake/shake',
        //     icon : '/assets/menu/shake.png',
        //     selectedIcon : '/assets/menu/shake-selected.png',
        //     type : 2,
        //     animate : true,
        //     color : '#F7701F',
        //     selectedColor : '#F7701F',
        //     width : 'auto',
        //     text : '必玩'
        // },
        {
            path : 'pages/find/find',
            icon : '/assets/menu/yan.png',
            selectedIcon : '/assets/menu/yan-selected.png',
            type : 1,
            animate : false,
            width : 'auto',
            text : '发现',
            desc : 'find'
        }
    ]
}
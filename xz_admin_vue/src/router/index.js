import Vue from 'vue'
import Router from 'vue-router'
import layout from '@/views/layout'
const _import = require('./_import')
import NoFound from '@/views/404'
Vue.use(Router)

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/


export const constantRouterMap = [{
        path: '/login',
        component: _import('login'),
        hidden: true
    }, {
        path: '/404',
        component: NoFound,
        hidden: true
    }, {
        path: '/',
        component: _import('layout'),
        redirect: '/dashboard',
        name: 'dashboard',
        hidden: true,
        children: [{
            name: "Dashboard",
            path: 'dashboard',
            component: _import('dashboard'),
            meta: {
                "title": "首页"
            }
        }]
    }, {
        path: '/fate',
        component: _import('layout'),
        name: 'fate',
        "meta": {
            "title": "运势管理",
            "icon": ''
        },
        children: [{
            name: "add-fate",
            path: 'add-fate',
            component: _import('fate/addFate'),
            meta: {
                "title": "运势发布"
            }
        }, {
            name: "fate-draft",
            path: 'fate-draft',
            component: _import('fate/fateDraft'),
            meta: {
                "title": "历史发布"
            }
        }]
    },
    {
        path: '/yiyan',
        component: _import('layout'),
        name: 'yiyan',
        "meta": {
            "title": "一言管理",
            "icon": ''
        },
        children: [{
            name: "add-yiyan",
            path: 'add-yiyan',
            component: _import('yiyan/addYiyan'),
            meta: {
                "title": "一言发布"
            }
        }, {
            name: "yiyan-history",
            path: 'yiyan-history',
            component: _import('yiyan/yiyanHistory'),
            meta: {
                "title": "一言历史"
            }
        }]
    },
    {
        path: '/yiqian',
        component: _import('layout'),
        name: 'yiqian',
        "meta": {
            "title": "一签管理",
            "icon": ''
        },
        children: [{
            name: "yiqian-content",
            path: 'yiqian-content',
            component: _import('yiqian/yiqianContent'),
            meta: {
                "title": "签内容管理"
            }
        },
    {
        name: "yiqian-list",
        path: 'yiqian-list',
        component: _import('yiqian/yiqianContent/preview.vue'),
        meta: {
            "title": "签列表"
        }
    }]


    },
{
    path: '/user',
    component: _import('layout'),
    name: 'user',
    "meta": {
        "title": "用户管理",
        "icon": ''
    },
    children: [{
        name: "admin",
        path: 'admin',
        component: _import('user/admin'),
        meta: {
            "title": "系统用户管理"
        }
    }, {
        name: "wxUser",
        path: 'wxUser',
        component: _import('user/wxUser'),
        meta: {
            "title": "微信用户管理"
        }
    }]


},



    //
    {
        path: '*',
        redirect: '/404',
        hidden: true
    }
]

export default new Router({
    // mode: 'history', //后端支持可开
    scrollBehavior: () => ({
        y: 0
    }),
    routes: constantRouterMap
})

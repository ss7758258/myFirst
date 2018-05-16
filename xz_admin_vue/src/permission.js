import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css' // Progress 进度条样式
import router from './router'
import store from './store'

const whiteList = ['/login', '/404', '/pass'] // 不重定向白名单
router.beforeEach((to, from, next) => {
    NProgress.start()
    if (whiteList.indexOf(to.path) !== -1 || to.path.indexOf("edit") != -1) {
        next()
        return
    }
    next()
    if (store.getters.logined) {
        if (to.path === '/login') {
            next({
                path: '/'
            })
        } else {
            next()
        }
    } else if (from.path !== '/login') {
        next("/login")
    } else {
        NProgress.done() // 结束Progress
    }

})

router.afterEach(() => {
    NProgress.done() // 结束Progress
})

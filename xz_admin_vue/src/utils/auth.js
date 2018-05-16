import store from '@/store'
function auth(url) {
    return store.getters.permission.indexOf(url)
}

export default { auth };

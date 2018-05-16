const getters = {
  sidebar: state => state.app.sidebar,
  nickname: state => state.user.nickname,
  logined: state => state.user.logined,
  perms: state => state.user.perms,
  visitedViews: state => state.app.visitedViews,
  baseUrl: state => state.app.baseUrl
}
export default getters

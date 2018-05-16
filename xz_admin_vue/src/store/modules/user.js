
const user = {
  state: {
    logined:sessionStorage.getItem('logined')==1,
    nickname: sessionStorage.getItem('nickname')||"noName",
    perms: sessionStorage.getItem('ps')||""  // 权限列表
  },

  mutations: {

    SET_LOGINED:(state, status) => {
      if(status){
        sessionStorage.setItem('logined', 1)
      }else{
        sessionStorage.setItem('logined', 0)
      }
      state.logined = status
    },
    SET_NICKNAME: (state, name) => {
      state.nickname = name
      sessionStorage.setItem('nickname',name)
    },

    SET_PERMISSION: (state, perms) => {
      state.perms = perms
      sessionStorage.setItem('ps',perms)
    }
  },

  actions: {
    Logout({ commit, state }){
      commit('SET_LOGINED', false);
    },
    Login({ commit, state }){
      commit('SET_LOGINED', true);
    },
  }
}

export default user

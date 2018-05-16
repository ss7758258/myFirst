<template>
<div id="app">
  <router-view></router-view>
</div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style  lang="scss">
  @import './styles/index.scss'; // 全局自定义的css样式

  .el-tabs__content{
      height: 100% !important;
  }

  .el-table .cell {
      /*height: 40px;*/
      /*line-height: 40px;*/
      overflow: hidden  !important;
      text-overflow: ellipsis !important;
      white-space: nowrap !important;
  }
</style>

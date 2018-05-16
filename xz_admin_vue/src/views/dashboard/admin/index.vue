<template>
  <div class="dashboard-editor-container">

    <!-- <panel-group @handleSetLineChartData="handleSetLineChartData"></panel-group> -->
  <h1 style="width:40%;margin:auto">星座小程序--后台管理系统</h1>
  </div>
</template>

<script>

  import PanelGroup from './components/PanelGroup'
export default {
  name: 'dashboard-admin',
  components: {
    PanelGroup,
  },
  data() {
    return {

    }
  },
  methods: {
    handleSetLineChartData(){

    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard-editor-container {
  padding: 30px;
  background-color: rgb(240, 242, 245);
  .chart-wrapper {
    background: #fff;
    padding: 15px 15px 0;
  }
}
</style>

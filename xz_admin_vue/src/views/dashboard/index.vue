<template>
  <div class="dashboard-container">
    <component :is="currentRole"></component>
  </div>
</template>

<script>
  import adminDashboard from './admin'
  import editorDashboard from './editor'

  export default {
    name: 'dashboard',
    components: { adminDashboard, editorDashboard },
    data() {
      return {
        currentRole: 'adminDashboard'
      }
    },
    computed: {
    },
    created() {
      // if (!localStorage.getItem("loginType").includes('admin')) {
      //   this.currentRole = 'editorDashboard'
      // }
    }
  }
</script>

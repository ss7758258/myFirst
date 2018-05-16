<style rel="stylesheet/scss" lang="scss" scoped>

.panel-group {
    margin-top: 18px;
    .card-panel-col {
        margin-bottom: 32px;
    }
    .card-panel {
        height: 108px;
        cursor: pointer;
        font-size: 12px;
        position: relative;
        overflow: hidden;
        color: #666;
        background: #fff;
        box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
        border-color: rgba(0, 0, 0, .05);
        &:hover {
            .card-panel-icon-wrapper {
                color: #fff;
            }
            .icon-people {
                background: #40c9c6;
            }
            .icon-message {
                background: #36a3f7;
            }
            .icon-money {
                background: #f4516c;
            }
            .icon-shoppingCard {
                background: #34bfa3
            }
        }
        .icon-people {
            color: #40c9c6;
        }
        .icon-message {
            color: #36a3f7;
        }
        .icon-money {
            color: #f4516c;
        }
        .icon-shoppingCard {
            color: #34bfa3
        }
        .card-panel-icon-wrapper {
            float: left;
            margin: 14px 0 0 14px;
            padding: 16px;
            transition: all 0.38s ease-out;
            border-radius: 6px;
        }
        .card-panel-icon {
            float: left;
            font-size: 48px;
        }
        .card-panel-description {
            float: right;
            font-weight: bold;
            margin: 26px;
            margin-left: 0px;
            .card-panel-text {
                line-height: 18px;
                color: rgba(0, 0, 0, 0.45);
                font-size: 16px;
                margin-bottom: 12px;
            }
            .card-panel-num {
                font-size: 20px;
            }
        }
    }
}

</style>

<template>

<el-row class="panel-group" :gutter="40">
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class='card-panel' @click="handleSetLineChartData('newVisitis')">
           
            <div class="card-panel-description">
               
                    <div class="card-panel-text">总部门数</div>
                    <count-to class="card-panel-num" :startVal="0" :endVal="numData.deptNum.total" :duration="3600"></count-to>
                
            </div>
        </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('messages')">
         
            <div class="card-panel-description">
                
                    <div class="card-panel-text">总机构数</div>
                    <count-to class="card-panel-num" :startVal="0" :endVal="numData.orgNum.total" :duration="4000"></count-to>
                
            </div>
        </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
        
            <div class="card-panel-description">
                
                    <div class="card-panel-text">总医生数</div>
                    <count-to class="card-panel-num" :startVal="0" :endVal="numData.doctorNum.total" :duration="4000"></count-to>
               
            </div>
        </div>
    </el-col>
    <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('shoppings')">
          
            <div class="card-panel-description">
               
                    <div class="card-panel-text">总监督数</div>
                    <count-to class="card-panel-num" :startVal="0" :endVal="numData.suprviceNum.total" :duration="4600"></count-to>
                
            </div>
        </div>
    </el-col>
</el-row>

</template>

<script>

import CountTo from 'vue-count-to'

export default {
    components: {
        CountTo
    },
    data() {
        return {
            numData: {
                deptNum: {
                    total: 0
                },
                orgNum: {
                    total: 0
                },
                doctorNum: {
                    total: 0
                },
                suprviceNum: {
                    total: 0
                },
            }
        }
    },
    methods: {
        handleSetLineChartData(type) {
                this.$emit('handleSetLineChartData', type)
            },

    },
    created() {
        this.$http.post("dashboard", (data) => {
            //this.numData = data
        })

    }

}

</script>

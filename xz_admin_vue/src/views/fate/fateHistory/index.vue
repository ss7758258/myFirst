<style scoped>

</style>

<template>
    <div class="app-container">
        <my-table :api="api" :cols="cols" ref="table" :idField="idField" :opeBtn="false" @refresh="refresh" :searchInfo="searchInfo" :editInfo="editInfo" :showAdd='false' @beforeCommit="beforeCommit" @handleAdd="handleAdd" @handleEdit="handleEdit" :editable="false">
            <div slot="columns">
                 <el-table-column v-for="col in cols" :key="col.field" align="center" v-if="col.type === 'img'"  :formatter="col.formatter" :prop="col.field" :label="col.label" :width="col.width">
<template slot-scope="scope">
    <a :href="baseUrl+scope.row['prevPic']" target="_blank">
        <img :src="baseUrl+scope.row['prevPic']" class="header_pic" /></a>
</template>
            </el-table-column>

            <el-table-column align="center" v-else :formatter="col.formatter"  :prop="col.field" :label="col.label" :width="col.width">
            </el-table-column>
                <el-table-column label="操作" align="center">
                    <div slot-scope="scope">
                        <el-button-group>
                            <el-button size="mini" type="info" @click="handleDiyEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                        </el-button-group>
                    </div>
                </el-table-column>
            </div>
        </my-table>

    </div>
</template>

<script>
    import MyTable from "@/components/MyTable";
    export default {
        components: {
            MyTable
        },
        data() {
            return {
                // 接口路径
                baseUrl: BASE_URL,
                api: {
                    list: "/tiLucky/json/findTiLuckysByPage",
                    delete: "/tiLucky/json/delTiLuckyById",
                },
                // id 字段
                idField: "id",
                // 表头字段
                cols: [{
                    field: "id",
                    label: "ID",
                    width: 125
                }, {
                    field: "updateTimestamp",
                    label: "编辑时间"
                }, {
                    field: "publishName",
                    label: "编辑人员"
                }],
                // 表单对象
                editInfo: {

                },
                searchInfo: {

                },
                // 表单验证
                validRule: {
                    dictValue: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                    dictLabel: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                    type: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                    parentLabel: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                    sort: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                },
                // ========自定义变量
                editable: 1,
                options: [],
                selectValue: '',
                dialogType: true
            };
        },
        methods: {
            // 以下为必备函数
            beforeCommit(param) {
                this.$refs.form.validate((valid) => {
                    if (valid) {
                        this.editInfo.editable = this.editable;
                        param.ok = true; // 允许提交
                    } else {
                        param.ok = false; // 允许提交
                    }
                });
            },
            handleAdd(param) {
                param.needNew = false; //不打开新页面
                this.editInfo = {};
                this.editInfo.editable = 1;
                this.editInfo.sort = 1;
                this.dialogType = true;
            },
            handleEdit(param, row) {
                param.needNew = true; //打开新页面
                this.$router.push({
                    path: '/fate/add-fate',
                    query: {
                        id: row.id,
                        type: 'edit',
                        disabled: false
                    }
                })

            },
            refresh() {

            },
            // 自定义函数
            selectArea(areaId) {

            },
            doSearch(e) {
                // TODO: 后端搜索接口
                this.searchInfo.parent = e;
                this.$refs.table.search();
            },
            handleDiyEdit(index, row) {
                this.$refs.table.handleEdit(index, row);
            },
            handleDelete(index, row) {
                this.$refs.table.delete(row[this.idField])
            }
        },
        mounted() {

        }
    };
</script>

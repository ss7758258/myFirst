<style scoped>

</style>

<template>
    <div class="app-container">
        <my-table :api="api" :previewId="libId" :cols="cols" ref="table" :idField="idField" :opeBtn="false" @refresh="refresh" :searchInfo="searchInfo" :editInfo="editInfo" :showAdd='false' @beforeCommit="beforeCommit" @handleAdd="handleAdd" @handleEdit="handleEdit"
            :editable="false">

            <div slot="columns">
                <el-table-column v-for="col in cols" :key="col.field" align="center" v-if="col.type === 'img'" :formatter="col.formatter" :prop="col.field" :label="col.label" :width="col.width">
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

            <!-- 表单 -->
            <div slot="dialog">

                <el-form :status-icon="true" ref="form" :rules="rules" :model="editInfo" label-width="100px" size="medium">

                    <!-- 自定义表单   -++++++++++++++++++ -->
                    <el-form-item hidden>
                        <el-input v-model="editInfo.id" auto-complete="off"></el-input>
                    </el-form-item>

                    <!-- 自定义表单   -++++++++++++++++++ -->

                    <el-form-item label="签名"  prop="name">
                        <el-input type="text" v-model="editInfo.name" auto-complete="on"></el-input>
                    </el-form-item>
                    <el-form-item label="签内容" prop="content">
                        <el-input type="text" v-model="editInfo.content" auto-complete="off"></el-input>
                    </el-form-item>
                    <!-- 自定义表单 -->
                </el-form>

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
                    list: "/f00/qianListByLibId",
                    delete: "/tiQianList/json/delTiQianListById",
                    add: "/tiQianList/json/addTiQianList",
                    edit: "/tiQianList/json/updateTiQianListById",
                },
                // id 字段
                idField: "id",
                // 表头字段
                cols: [{
                    field: "id",
                    label: "ID",
                    width: 125
                }, {
                    field: "name",
                    label: "签名"
                }, {
                    field: "content",
                    label: "签内容"
                }],
                // 表单对象
                editInfo: {

                },
                searchInfo: {

                },
                rules: {
                    name: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                    content: [{
                        required: true,
                        message: '不能为空',
                        trigger: 'blur'
                    }],
                },
                // ========自定义变量
                editable: 1,
                options: [],
                selectValue: '',
                dialogType: true,
                libId: ''
            };
        },
        methods: {
            // 以下为必备函数
            beforeCommit(param) {
                this.$refs.form.validate((valid) => {
                    if (valid) {
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
                param.needNew = false; //不打开新页面
                this.editInfo = JSON.parse(JSON.stringify(row));
                this.dialogType = false;
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
            this.libId = this.$router.currentRoute.query.id;
            console.log(this.libId);

        }
    };
</script>

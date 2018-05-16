<style scoped>

</style>

<template>
    <div class="app-container">
        <my-table :api="api" :cols="cols" ref="table" :idField="idField" :opeBtn="false" @refresh="refresh" :searchInfo="searchInfo" :editInfo="editInfo" :showAdd='true' @beforeCommit="beforeCommit" @handleAdd="handleAdd" @handleEdit="handleEdit" :editable="false">

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

            <!-- 表单 -->
            <div slot="dialog">

                <el-form :status-icon="true" ref="form" :rules="validRule" :model="editInfo" label-width="100px" size="medium">

                    <!-- 自定义表单   -++++++++++++++++++ -->
                    <el-form-item hidden>
                        <el-input v-model="editInfo.id" auto-complete="off"></el-input>
                    </el-form-item>

                    <!-- 自定义表单   -++++++++++++++++++ -->

                    <el-form-item label="用户名" prop="username">
                        <el-input type="text" v-model="editInfo.username" auto-complete="on"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="pass">
                        <el-input type="password" v-model="editInfo.pass" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="checkPass">
                        <el-input type="password" v-model="editInfo.checkPass" auto-complete="off"></el-input>
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
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.editInfo.checkPass !== '') {
                        this.$refs.form.validateField('checkPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.editInfo.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                // 接口路径
                baseUrl: BASE_URL,
                api: {
                    list: "/tiAdmin/json/findTiAdminsByPage",
                    delete: "/tiAdmin/json/delTiAdminById",
                    add: "/tiAdmin/json/addTiAdmin",
                    edit: "/tiAdmin/json/updateTiAdminById",
                },
                // id 字段
                idField: "id",
                // 表头字段
                cols: [{
                    field: "id",
                    label: "ID",
                    width: 125
                }, {
                    field: "username",
                    label: "用户名"
                }],
                // 表单对象
                editInfo: {

                },
                searchInfo: {

                },
                // 表单验证
                validRule: {
                    pass: [{
                        required: true,
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    checkPass: [{
                        required: true,
                        validator: validatePass2,
                        trigger: 'blur'
                    }],
                    username: [{
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

        }
    };
</script>

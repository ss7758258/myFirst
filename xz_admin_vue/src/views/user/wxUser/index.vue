<style scoped>

</style>

<template>
    <div class="app-container">
        <my-table :api="api" :cols="cols" ref="table" :idField="idField" :opeBtn="false" @refresh="refresh" :searchInfo="searchInfo" :editInfo="editInfo" :showAdd='false' @beforeCommit="beforeCommit" @handleAdd="handleAdd" @handleEdit="handleEdit" :editable="false">
            <div slot="columns">
                 <el-table-column v-for="col in cols" :key="col.field" align="center" v-if="col.type === 'img'"  :formatter="col.formatter" :prop="col.field" :label="col.label" :width="col.width">
<template slot-scope="scope">
    <a :href="scope.row['headImage']" target="_blank">
        <img :src="scope.row['headImage']" class="header_pic" /></a>
</template>
            </el-table-column>

            <el-table-column align="center" v-else :formatter="col.formatter"  :prop="col.field" :label="col.label" :width="col.width">
            </el-table-column>
                <el-table-column label="操作" align="center">
                    <div slot-scope="scope">
                        <el-button-group>
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
                baseUrl: 'https://xingzuo-1256217146.file.myqcloud.com/',
                api: {
                    list: "/weixinUser/json/findWeixinUsersByPage",
                    delete: "/weixinUser/json/delWeixinUserById",
                    add: "/weixinUser/json/addWeixinUser",
                    edit: "/weixinUser/json/updateWeixinUserById",
                },
                // id 字段
                idField: "id",
                // 表头字段
                cols: [{
                    field: "id",
                    label: "ID",
                    width: 125
                }, {
                    field: "nickName",
                    label: "昵称"
                }, {
                    field: "headImage",
                    label: "头像",
                    type: 'img'
                }, {
                    field: "gender",
                    label: "性别",
                },{
                    field: "constellationId",
                    label: "星座",
                    formatter: (row, column, cellValue) => {
                        switch (cellValue) {
                            case 1:
                                return "水瓶座";
                                break;
                            case 2:
                                return "白羊座";
                                break;
                            case 3:
                                return "金牛座";
                                break;
                            case 4:
                                return "双子座";
                                break;
                            case 5:
                                return "巨蟹座";
                                break;
                            case 6:
                                return "狮子座";
                                break;
                            case 7:
                                return "处女座";
                                break;
                            case 8:
                                return "天秤座";
                                break;
                            case 9:
                                return "天蝎座";
                                break;
                            case 10:
                                return "射手座";
                                break;
                            case 11:
                                return "摩羯座";
                                break;
                            case 12:
                                return "双鱼座";
                                break;
                        }

                    }
                },{
                    field: "openId",
                    label: "openID"
                },{
                    field: "address",
                    label: "地址"
                }],
                // 表单对象
                editInfo: {

                },
                searchInfo: {

                },
                // ========自定义变量
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
            },
        },
        mounted() {

        }
    };
</script>

<style scoped>

</style>

<template>
    <div class="app-container">
        <my-table :api="api" :cols="cols" ref="table" :idField="idField" :opeBtn="false" @refresh="refresh" :searchInfo="searchInfo" :editInfo="editInfo" :showAdd='false' @handleEdit="handleEdit" :editable="false">
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
                            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                            <el-button size="mini" type="info" v-if="scope.row['publishStatus'] != 1" @click="handleDiyEdit(scope.$index, scope.row)">编辑</el-button>
                            <el-button size="mini" type="primary" v-if="scope.row['publishStatus'] == 1" @click="handlePreview(scope.$index, scope.row)">查看</el-button>
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
                    list: "/tiYanList/json/findTiYanListsByPage",
                    delete: "/tiYanList/json/delTiYanListById",
                },
                // id 字段
                idField: "id",
                // 表头字段
                cols: [{
                    field: "id",
                    label: "ID",
                    width: 125
                },{
                    field: "constellationId",
                    label: "星座",
                    formatter: (row, column, cellValue) => {
                        switch (cellValue) {
                            case 1:
                                return "白羊座";
                                break;
                            case 2:
                                return "金牛座";
                                break;
                            case 3:
                                return "双子座";
                                break;
                            case 4:
                                return "巨蟹座";
                                break;
                            case 5:
                                return "狮子座";
                                break;
                            case 6:
                                return "处女座";
                                break;
                            case 7:
                                return "天秤座";
                                break;
                            case 8:
                                return "天蝎座";
                                break;
                            case 9:
                                return "射手座";
                                break;
                            case 10:
                                return "摩羯座";
                                break;
                            case 11:
                                return "水瓶座";
                                break;
                            case 12:
                                return "双鱼座";
                                break;
                        }

                    }
                }, {
                    field: "publishTime",
                    label: "发布时间"
                }, {
                    field: "publishStatus",
                    label: "发布状态",
                    formatter: (row, column, cellValue) => {
                        switch (cellValue) {
                            case '-1':
                                return '未发布';
                                break;
                            case '1':
                                return '已发布';
                                break;
                            case '0':
                                return '待发布';
                                break;
                        }
                    }
                }, {
                    field: "prevPic",
                    label: "图片预览",
                    type: 'img'
                }, {
                    field: "publishPerson",
                    label: "发布人员"
                }],
                // 表单对象
                editInfo: {

                },
                searchInfo: {

                },
                // ========自定义变量
                editable: 1,
                options: [],
                selectValue: '',
                dialogType: true
            };
        },
        methods: {
            handlePreview(param, row) {
                this.$router.push({
                    path: '/yiyan/add-yiyan',
                    query: {
                        id: row.id,
                        type: 'edit',
                        disabled: 0
                    }
                })
            },
            handleEdit(param, row) {
                param.needNew = true; //打开新页面
                this.$router.push({
                    path: '/yiyan/add-yiyan',
                    query: {
                        id: row.id,
                        type: 'edit',
                        disabled: 1
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

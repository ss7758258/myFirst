<style lang="scss">
    .table-foot {
        margin: 5px;
    }

    @media only screen and (max-height: 768px) {
        .tableDiv {
            min-height: 668px;
            max-height: 668px;
        }
    }

    @media only screen and (max-height: 1280px) {
        .tableDiv {
            min-height: 750px;
            max-height: 750px;
        }
    }

    @media only screen and (max-height: 600px) {
        .tableDiv {
            min-height: 500px;
            max-height: 500px;
        }
    }
</style>

<template lang="html">
    <div>

        <!-- 顶部操作区 -->
        <el-row type="flex" class="table-foot">
            <el-col :span="20">
                <!-- 搜索框 -->
                <slot name="searchBox"></slot>
            </el-col>
            <!-- 右侧按钮区 -->
            <el-col align="right" :span="4">
                <el-button-group v-if="showAddBtn">
                    <el-button size="small" type="primary" v-if="addAble" icon="el-icon-plus" @click="handleAdd">添加</el-button>
                    <el-button size="small" type="info" v-if="exportAble" icon="el-icon-download" @click="handleExport">导出Excel</el-button>
                    <el-tooltip class="item" effect="dark" content="可用于清空搜索结果" placement="top-start">
                        <el-button size="small" type="success" icon="el-icon-refresh" @click="refreshTable">刷新</el-button>
                    </el-tooltip>

                </el-button-group>
                <el-button-group v-else>
                    <el-tooltip class="item" effect="dark" content="可用于清空搜索结果" placement="top-start">
                        <el-button size="small" type="success" icon="el-icon-refresh" @click="refreshTable">刷新</el-button>
                    </el-tooltip>
                </el-button-group>
            </el-col>
        </el-row>
        <!-- 表格区 -->
        <el-table height="auto" :data="tableData" :stripe="true" :border="true" class="tableDiv" v-loading="listLoading" element-loading-text="正在加载数据..." @selection-change="handleSelectionChange" style="width: 100%">
            <el-table-column label="操作" align="center" v-if="diyOpeBtn" width="150">
                <div slot-scope="scope">
                    <el-button-group>
                        <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">{{canDeleteS}}删除</el-button>
                    </el-button-group>
                </div>
            </el-table-column>
            <el-table-column width="1" v-if="!diyOpeBtn">
            </el-table-column>
            <slot name="columns"></slot>
        </el-table>

        <!-- 弹窗表单 -->
        <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" :lock-scroll="false" width="60%">
            <el-row>
                <slot name="dialog"></slot>
            </el-row>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogCancel">取 消</el-button>
                <el-button type="primary" @click="dialogOk">确 定</el-button>
            </div>
        </el-dialog>


        <!-- 底部操作 -->
        <el-row type="flex" class="table-foot">
            <el-col :span="12" align="left">
                <el-button v-if="false" type="danger" size="small" @click="deleteManyHandle" icon="el-icon-delete">批量删除</el-button>
            </el-col>
            <el-col align="right">
                <!-- 分页 -->
                <el-pagination :small="small" background :current-page.sync="curPage" :page-sizes="[20, 30, 50, 100]" :page-size="pageSize" @size-change="sizeChangeHandle" @current-change="currentChangeHandle" layout="total, sizes, prev, pager, next, jumper" :total="totalNum">
                </el-pagination>
            </el-col>
        </el-row>


    </div>
</template>

<script>
    import {
        mapGetters
    }
    from "vuex";
    export default {
        props: ["api", "cols", "previewId", "opeBtn", "idField", "editInfo", "searchInfo", "showAdd", "editable", "addable", "canDelete", "exportAble"],
        data() {
            return {
                tableData: null, // 数据
                listLoading: true, //加载
                listType: "list", // 列表类型search list
                // 分页相关
                curPage: 1, //当前页
                totalNum: 0, // 总数
                small: true, //小屏幕
                pageSize: 20, //每页大小

                // 自定义操作按钮
                diyOpeBtn: this.opeBtn && true,
                showAddBtn: this.showAdd && true,
                editableS: this.editable && true,
                canDeleteS: this.canDelete && true,
                //所选ids
                checkIds: [],

                // 表单相关
                dialogTitle: "添加", // 弹窗标题
                dialogFormVisible: false, // 弹窗控制
                baseUrl: BASE_URL,
                addAble: !this.addable && true,
            };
        },
        methods: {
            getTableData() {
                // 获取列表数据

                this.listLoading = true;
                this.tableData = []
                let type = this.$router.currentRoute.query.type;
                let id = this.$router.currentRoute.query.id;

                setTimeout(() => {
                    let param = null;

                    if (this.listType === "list") {
                        param = {
                            pageNum: this.curPage,
                            pageSize: this.pageSize
                        };
                    } else {
                        param = this.searchInfo;
                        param['pageNum'] = this.curPage;
                        param['pageSize'] = this.pageSize;
                    }
                    if (this.$router.currentRoute.path == "/yiqian/yiqian-list") {
                        param.id = this.previewId;
                    }
                    this.$http.get({
                        url: this.api[this.listType],
                        params: param,
                        success: res => {
                            this.tableData = res.data.list;
                            this.totalNum = res.data.total;
                            this.listLoading = false;
                        },
                        excep: () => {
                            this.$message.error('网络错误');
                            this.listLoading = false;
                        }
                    });
                }, 300);
            },
            handleSelectionChange(val) {
                //获取选择的id
                this.checkIds = [];
                for (var i = 0; i < val.length; i++) {
                    this.checkIds.push(val[i][this.idField]);
                }
            },
            refreshTable() {
                this.curPage = 1;
                this.$emit("refresh")
                this.listType = "list"
                for (var x in this.searchInfo) {
                    this.searchInfo[x] = undefined;
                }
                this.getTableData();
            },

            dialogCancel() {
                //弹窗关闭
                this.dialogFormVisible = false;
            },
            dialogOk() {
                let param = {
                    ok: true
                }
                this.$emit("beforeCommit", param)
                if (param.ok) {
                    this.$http.post(this.api[this.editType], this.editInfo, (data, res) => {
                        this.$message({
                            type: "success",
                            message: "操作成功!"
                        });
                        this.getTableData();
                        this.dialogFormVisible = false;
                    });
                }

            },
            sizeChangeHandle(size) {
                //分页大小发生变化
                this.pageSize = size;
                this.refreshTable();
            },
            currentChangeHandle(currentPage) {
                //页数发生变化
                this.curPage = currentPage;
                console.log(currentPage);

                this.getTableData();
            },

            handleAdd() {
                let param = {
                    needNew: false
                };
                this.$emit("handleAdd", param);
                if (!param.needNew) {
                    this.dialogTitle = "添加";
                    this.editType = "add";
                    this.dialogFormVisible = true;
                }
            },
            handleEdit(index, row) {
                // 传递row到父级
                let param = {
                    needNew: false
                };

                this.$emit("handleEdit", param, row);
                if (!param.needNew) {

                    this.dialogTitle = "编辑";
                    this.editType = "edit";
                    this.dialogFormVisible = true;
                }
            },
            // 删除
            deleteManyHandle() {
                this.delete(this.checkIds);
            },
            handleDelete(index, row) {
                this.delete(row[this.idField]);
            },
            delete(id) {

                this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                    callback: action => {
                        if (action == "confirm") {
                            var params = {};
                            params["id"] = id;
                            this.$http.post({
                                url: this.api.delete,
                                params,
                                success: res => {
                                    if (res.status == 'SUCCESS') {
                                        this.refreshTable();
                                        this.$message({
                                            type: "success",
                                            message: "删除成功!"
                                        });
                                    } else {
                                        this.$message({
                                            type: "error",
                                            message: res.message
                                        });
                                    }

                                }
                            });
                        }
                    }
                });
            },
            search() {
                this.curPage = 1;
                this.listType = "search";

                this.getTableData()
            },
            getKey(index) {
                return new Date();
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },
            handleExport() {
                require.ensure([], () => {
                    const {
                        export_json_to_excel
                    } = require('../../vendor/Export2Excel');
                    let tHeader = [];
                    let filterVal = [];
                    tHeader.push('序号')
                    tHeader.push('联系地址')
                    filterVal.push('indexNo')
                    filterVal.push('address')
                    this.cols.map((res, index) => {
                        if (res.label != '头像' && res.label != 'ID') {
                            tHeader.push(res.label);
                        }
                        if (res.field != 'avatar' && res.field != 'id') {
                            filterVal.push(res.field);
                        }

                    });
                    let list = this.tableData;
                    let newList = [];
                    list.map((res, index) => {
                        res.indexNo = index + 1;
                        switch (res.status) {
                            case 1:
                                res.status = "已报名"
                                break;
                            case 2:
                                res.status = "培训一次"
                                break;
                            case 3:
                                res.status = "培训两次"
                                break;
                            case 4:
                                res.status = "考试通过"
                                break;
                            case 5:
                                res.status = "考试不通过"
                                break;
                            case 6:
                                res.status = "重新考试"
                                break;
                        }
                        newList.push(res);

                    })

                    const data = this.formatJson(filterVal, list);
                    export_json_to_excel(tHeader, data, '报名记录表');
                })
            }
        },
        activated() {
            this.getTableData();
            let _this = this;
            window.onresize = function() {
                // 调整分页样式大小
                _this.small = window.innerWidth < 1124;
            };
        }
    };
</script>

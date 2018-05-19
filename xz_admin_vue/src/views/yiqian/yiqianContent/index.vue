<template>
    <div class="container">
        <header>
            <el-button type="primary" @click="showModalAdd">创建一签库</el-button>
        </header>
        <article v-loading="showLoading">
            <el-card shadow="never" class="box-card" v-for="(item, index) in fullData" :key="index">
                <el-row :gutter="20">
                    <el-col :span="8">
                        <div class="item-label">{{item.libs.name}}</div>
                        <div class="img-item">
                            <div class="img-title">背景上传</div>
                            <img-upload type="img" :uploadUrls="'/tiQianLib/json/picUpload'" :model.sync="item.libs.pic" functionName="user"></img-upload>
                        </div>
                    </el-col>
                    <el-col :span="16">
                        <div class="item-label">{{item.libs.name + '-'}}签库</div>
                        <div class="content-item" v-if="item.items.length>0">
                            <div class="list-item" v-for="(res, count) in item.items" :key="count">
                                <el-button type="primary" size="mini">{{count+1}}</el-button>
                                <span>{{res.name}}+{{res.content}}</span>
                            </div>
                        </div>
                        <el-button style="float:right;" type="primary" size="mini" @click="previewHandle(item.libs.id)">查看更多</el-button>
                        <div class="no-data" v-if="item.items.length<1">暂无内容</div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="8">
                        &nbsp;
                    </el-col>
                    <el-col :span="16">
                        <div class="actions">
                            <el-button size="mini" type="danger" @click="delHandle(item.libs.id)">删除</el-button>
                            <el-button size="mini" type="warning" @click="toStop(item.libs.id, item.libs.status)">{{item.libs.status == 0?"重新启用":'暂停使用'}}</el-button>
                            <el-button size="mini" :disabled="item.libs.status == 0" type="primary" @click="showModal(item.libs.id)">上传内容</el-button>
                            <el-date-picker :disabled="item.libs.status == 0" value-format="yyyy-MM-dd HH:mm:ss" size="mini" v-model="editInfo.publishTime" type="datetime" placeholder="选择发布时间">
                            </el-date-picker>
                            <el-button size="mini" :disabled="item.libs.status == 0" type="success" @click="handleUpdate(item.libs.id, item.libs.pic)">发布</el-button>
                        </div>
                    </el-col>
                </el-row>
            </el-card>
        </article>
        <el-dialog title="上传内容" :visible.sync="dialogVisible" width="60%">
            <el-form :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="100px" class="demo-dynamic">
                <el-form-item style="margin-bottom:10px;">
                    <el-row :gutter="20">
                        <el-col :span="9">
                            <span>签名</span>
                        </el-col>
                        <el-col :span="9">
                            <span>签内容</span>
                        </el-col>
                        <el-col :span="5">
                            <p>操作</p>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item style="margin-bottom:10px;" v-for="(domain, index) in dynamicValidateForm.domains" :key="domain.key">
                    <el-row :gutter="20">
                        <el-col :span="9">
                            <el-input type="textarea" v-model="domain.type" :autosize="{ minRows: 2, maxRows: 4}"></el-input>
                        </el-col>
                        <el-col :span="9">
                            <el-input type="textarea" v-model="domain.content" :autosize="{ minRows: 2, maxRows: 4}"></el-input>
                        </el-col>
                        <el-col :span="5">
                            <el-button size="small" type="danger" @click.prevent="removeDomain(domain)">删除</el-button>
                        </el-col>
                    </el-row>
                </el-form-item>
                <el-form-item>
                    <el-button @click="addDomain" size="small">添加签</el-button>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                                        <el-button @click="dialogVisible = false">取 消</el-button>
                                        <el-button type="primary" @click="submitDialog">确 定</el-button>
                                    </span>
        </el-dialog>

        <el-dialog title="上传内容" :visible.sync="dialogVisibleAdd" width="60%">
            <el-form :model="libInfo" ref="form" label-width="100px" class="demo-dynamic">
                <el-form-item label="库名">
                    <el-input v-model="libInfo.name"></el-input>
                </el-form-item>
                <el-form-item label="预览图">
                    <img-upload type="img" :model.sync="libInfo.pic" :uploadUrls="'/tiQianLib/json/picUpload'" functionName="user"></img-upload>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                                        <el-button @click="dialogVisibleAdd = false">取 消</el-button>
                                        <el-button type="primary" @click="addHandle">确 定</el-button>
                                    </span>
        </el-dialog>
    </div>
</template>

<script>
    import ImgUpload from "@/components/MyUpload";
    import {
        noNullProp
    } from '@/utils/common.js'
    export default {
        components: {
            ImgUpload
        },
        data() {
            return {
                editInfo: {},
                libInfo: {},
                defaultArr: [],
                defaultObj: {
                    imgSrc: '',
                    itemList: [],
                    pushTime: ''
                },
                showLoading: true,
                dialogVisible: false,
                dynamicValidateForm: {
                    domains: [{
                        type: '',
                        content: ''
                    }]
                },
                dialogVisibleAdd: false,
                pageNum: 1,
                forTotal: 0,
                itemList: [],
                fullData: [],
                libsId: ''
            }
        },
        methods: {
            addHandle() {
                this.showLoading = true;
                this.$http.post({
                    url: 'tiQianLib/json/addTiQianLib',
                    params: this.libInfo,
                    success: res => {
                        if (res.status == 'SUCCESS') {
                            this.$message.success('增加成功！')
                        } else {
                            this.$message.error('增加失败！')
                        }
                        this.dialogVisibleAdd = false;
                        this.getLib();
                    },
                    excep: () => {
                        this.$message.error('网络错误');
                        this.showLoading = false;
                    }
                })
            },
            getLib() {
                this.$http.post({
                    url: 'tiQianLib/json/findTiQianLibsByPage',
                    success: res => {
                        this.fullData = [];
                        res.data.list.map((value, index) => {
                            let obj = {};
                            obj.libs = value;
                            this.$http.post({
                                url: "f00/qianListByLibId",
                                params: {
                                    pageSize: 5,
                                    pageNum: this.pageNum,
                                    id: value.id,
                                },
                                success: data => {
                                    obj.items = data.data.list;
                                    this.fullData.push(obj);
                                    if (index == res.data.list.length - 1) {
                                        this.showLoading = false;
                                    }
                                }
                            })
                        });
                    }
                })
            },
            showModal(id) {
                this.dialogVisible = true;
                this.libsId = id;
            },
            showModalAdd() {
                this.dialogVisibleAdd = true;
            },
            removeDomain(item) {
                var index = this.dynamicValidateForm.domains.indexOf(item)
                if (index !== -1) {
                    this.dynamicValidateForm.domains.splice(index, 1)
                }
            },
            addDomain() {
                this.dynamicValidateForm.domains.push({
                    type: '',
                    content: '',
                    key: Date.now()
                });
            },
            handleUpdate(id, pic) {
                this.editInfo.id = id;
                this.editInfo.pic = pic;
                this.showLoading = true;

                this.$http.post({
                    url: 'tiQianLib/json/updateTiQianLibById',
                    params: this.editInfo,
                    success: res => {
                        if (res.status == "SUCCESS") {
                            this.$message.success('发布成功！')
                        } else {
                            this.$message.error('发布失败！')
                        }
                        this.showLoading = false;
                        this.editInfo = {};
                    },
                    excep: () => {
                        this.$message.error('网络错误！')
                        this.showLoading = false;
                    }
                })
            },
            submitDialog() {
                this.dynamicValidateForm.domains.map(res => {
                    if (noNullProp(res)) {
                        this.$http.post({
                            url: '/tiQianList/json/addTiQianList',
                            params: {
                                name: res.type,
                                content: res.content,
                                qianLibId: this.libsId
                            },
                            success: data => {
                                if (data.status == 'SUCCESS') {
                                    this.showLoading = true;
                                    this.getLib();
                                } else {
                                    this.showLoading = false;
                                }
                                this.dialogVisible = false;
                            }
                        })
                    } else {
                        this.$message.error('信息填充不完整！')
                    }

                })
            },
            delHandle(id) {
                console.log(id);
                this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning",
                    callback: action => {
                        if (action == "confirm") {
                            var params = {};
                            params["id"] = id;
                            this.$http.post({
                                url: '/tiQianLib/json/delTiQianLibById',
                                params,
                                success: res => {
                                    if (res.status == 'SUCCESS') {
                                        this.showLoading = true;
                                        this.getLib();
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
            previewHandle(id) {
                this.$router.push({
                    path: '/yiqian/yiqian-list',
                    query: {
                        id
                    }
                })
            },
            toStop(id, status) {
                if (status == 1) {
                    this.$confirm("此操作将暂停使用该签库, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                        callback: action => {
                            if (action == "confirm") {
                                var params = {};
                                params["id"] = id;
                                this.showLoading = true;
                                this.$http.post({
                                    url: '/f00/qianDisable',
                                    params,
                                    success: res => {
                                        if (res.status == 'SUCCESS') {
                                            this.getLib();
                                            this.$message({
                                                type: "success",
                                                message: "暂停成功!"
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
                } else {
                    this.$confirm("此操作将重新使用该签库, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                        callback: action => {
                            if (action == "confirm") {
                                this.showLoading = true;
                                this.$http.post({
                                    url: '/tiQianLib/json/updateTiQianLibById',
                                    params: {
                                        status: 1,
                                        id
                                    },
                                    success: res => {
                                        if (res.status == 'SUCCESS') {
                                            this.getLib();
                                            this.$message({
                                                type: "success",
                                                message: "启用成功!"
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
                }

            }
        },
        mounted() {
            this.getLib();
        }
    }
</script>

<style scoped>
    .container {
        width: 100%;
        height: 100px;
        padding: 20px;
    }

    header {
        height: 60px;
        line-height: 60px;
        padding-left: 20px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        border-bottom: 1px solid #00f;
        margin-bottom: 20px;
    }

    .img-item {
        width: 100%;
        height: 280px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .3);
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-direction: column;
    }

    .content-item {
        width: 100%;
        height: 280px;
        padding: 20px;
        /* background: #99a9bf;
                                color: #fff; */
    }

    .item-label {
        color: #666;
        padding-bottom: 10px;
    }

    .img-title {
        color: #ccc;
    }

    .list-item {
        margin-bottom: 20px;
    }

    .list-more {
        color: #00f;
        cursor: pointer;
        margin-top: 20px;
        text-align: right;
    }

    .actions {
        margin-top: 20px;
        width: 100%;
        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    .box-card {
        margin-bottom: 20px;
    }

    .no-data {
        width: 100%;
        height: 280px;
        display: flex;
        justify-content: center;
        align-items: center;
        color: #ccc;
    }
</style>

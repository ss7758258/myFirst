<template>
    <div class="container" v-loading="isLoading">
        <el-form ref="form" label-position="left" :model="editInfo" label-width="140px">
            <el-form-item label="星座选择" class="form-item">
                <el-select :disabled="isDisabled" v-model="editInfo.constellationId" placeholder="请选择">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="预计发布时间" class="form-item">
                <el-date-picker :disabled="isDisabled" value-format="yyyy-MM-dd HH:mm:ss" v-model="editInfo.publishTime" type="datetime" placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item class="form-item" label="预览图">
                <img-upload :uploadUrls="'/tiYanList/json/prevPicUpload'" type="img" :model.sync="editInfo.prevPic" functionName="user"></img-upload>
            </el-form-item>
            <el-form-item class="form-item" label="发布人">
                <el-input :disabled="isDisabled" v-model="editInfo.publishPerson"></el-input>
            </el-form-item>
            <el-form-item v-if="!isDisabled">
                <el-button type="primary" @click="onSubmit" :disabled="btnDis">定时发布</el-button>
            </el-form-item>
            <el-form-item v-else>
                <el-button type="success" @click="back">返回</el-button>
            </el-form-item>
        </el-form>
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
                options: [],
                editInfo: {
                    publishPerson: '',
                    publishStatus: '',
                    publishTime: '',
                    constellationId: '',
                    prevPic: ""
                },
                defaultData: {
                    publishPerson: '',
                    publishStatus: '',
                    publishTime: '',
                    constellationId: '',
                    prevPic: ""
                },
                yiyanType: {},
                api: "",
                isDisabled: false,
                isLoading: true,
                btnDis: false
            };
        },
        methods: {
            getOptions() {
                this.$http.get({
                    url: 'tcConstellation/json/findTcConstellationsByPage',
                    params: {
                        pageSize: 20
                    },
                    success: res => {
                        this.options = [];
                        res.data.list.map(res => {
                            let obj = {
                                value: res.id,
                                label: res.constellationName
                            }
                            this.options.push(obj);
                        })
                        this.isLoading = false;
                    }
                })
            },
            onSubmit() {
                this.btnDis = true;
                if (this.yiyanType.type == 'edit') {
                    this.api = "tiYanList/json/updateTiYanListById"
                    this.editInfo.id = this.yiyanType.id;
                } else {
                    this.api = "tiYanList/json/addTiYanList";
                    this.editInfo.publishStatus = 0;
                }

                if (noNullProp(this.editInfo)) {
                    this.isLoading = true;
                    this.$http.post({
                        url: this.api,
                        params: this.editInfo,
                        success: res => {
                            if (res.status == 'SUCCESS') {
                                if (this.yiyanType.type == 'edit') {
                                    this.$message.success('修改成功！');
                                    this.$router.go(-1)
                                } else {
                                    this.editInfo = JSON.parse(JSON.stringify(this.defaultData));
                                    this.$message.success(res.message);
                                }
                            } else {
                                this.$message.error('添加失败！');
                            }
                            this.isLoading = false;
                            this.btnDis = false;
                        },
                        excep: () => {
                            this.$message.error('网络错误');
                            this.isLoading = false;
                            this.btnDis = false;
                        }
                    })
                } else {
                    this.$message.error('信息填充不完整！');
                    this.btnDis = false;
                }
            },
            back() {
                this.$router.go(-1)
            },
            getYiyan() {
                this.$http.post({
                    url: "tiYanList/json/getTiYanListById",
                    params: {
                        id: this.yiyanType.id
                    },
                    success: res => {
                        this.editInfo = {
                            publishPerson: res.data.publishPerson || '',
                            publishStatus: res.data.publishStatus || 0,
                            publishTime: res.data.publishTime || '',
                            constellationId: res.data.constellationId || '',
                            prevPic: res.data.prevPic || ''
                        }
                        this.isLoading = false;
                    },
                    excep: () => {
                        this.$message.error('网络错误');
                        this.isLoading = false;
                    }
                })
            },
        },
        activated() {
            this.getOptions();
            this.yiyanType = this.$router.currentRoute.query;
            if (this.yiyanType.disabled == 0) {
                this.isDisabled = true;
            } else {
                this.isDisabled = false;
            }
            if (this.yiyanType.type == 'edit') {
                this.getYiyan();
            }
        }
    };
</script>

<style scoped>
    .container {
        width: 100%;
        height: 100%;
        padding: 20px;
    }

    .form-item {
        position: relative;
        padding-left: 25px;
    }

    .form-item::before {
        position: absolute;
        left: 10px;
        content: "";
        width: 5px;
        border-left: 5px solid #7e06ee;
        height: 30px;
        margin-top: 5px;
    }
</style>

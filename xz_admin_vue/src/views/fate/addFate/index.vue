<template>
    <div class="container">
        <el-form ref="form" v-loading="showLoading" label-position="left" :rules="ruleFrom" :model="editInfo" label-width="140px">
            <el-form-item label="星座选择" class="form-item">
                <el-select @change="getType" :disabled="isDisabled" v-model="editInfo.constellationId" placeholder="请选择">
                    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="首页运势类型" class="form-item" prop="fate">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyType1"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyType2"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyType3"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyType4"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item label="运势评分（百分比）" class="form-item" prop="score">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" min="1" max="100" type="number" placeholder="请输入内容" v-model="editInfo.luckyScore1"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" min="1" max="100" type="number" placeholder="请输入内容" v-model="editInfo.luckyScore2"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" min="1" max="100" type="number" placeholder="请输入内容" v-model="editInfo.luckyScore3"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" min="1" max="100" type="number" placeholder="请输入内容" v-model="editInfo.luckyScore4"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item label="今日星座提醒" class="form-item">
                <el-input :disabled="isDisabled" type="textarea" v-model="editInfo.remindToday" placeholder="请输入内容"></el-input>
            </el-form-item>
            <div class="line"></div>
            <el-form-item label="更多运势类型" class="form-item" prop="fateMore">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyTypeMore1"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyTypeMore2"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyTypeMore3"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" placeholder="请输入内容" v-model="editInfo.luckyTypeMore4"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item label="运势评分（星值）" prop="score2" class="form-item">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="number" min="1" max="5" v-model="editInfo.luckyScoreMore1" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="number" min="1" max="5" v-model="editInfo.luckyScoreMore2" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="number" min="1" max="5" v-model="editInfo.luckyScoreMore3" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="number" min="1" max="5" v-model="editInfo.luckyScoreMore4" placeholder="请输入内容"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item label="运势寄语" class="form-item">
                <el-row :gutter="20">
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="textarea" v-model="editInfo.luckyWords1" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="textarea" v-model="editInfo.luckyWords2" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="textarea" v-model="editInfo.luckyWords3" placeholder="请输入内容"></el-input>
                    </el-col>
                    <el-col :span="4">
                        <el-input :disabled="isDisabled" type="textarea" v-model="editInfo.luckyWords4" placeholder="请输入内容"></el-input>
                    </el-col>
                </el-row>
            </el-form-item>
            <el-form-item label="今日去做" class="form-item">
                <el-input type="textarea" :disabled="isDisabled" v-model="editInfo.toDo" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="今日不做" class="form-item">
                <el-input type="textarea" :disabled="isDisabled" v-model="editInfo.notDo" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item label="预计发布时间" class="form-item">
                <el-date-picker :disabled="isDisabled" value-format="yyyy-MM-dd HH:mm:ss" v-model="editInfo.publishTime" type="datetime" placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
            <el-form-item label="发布人员" class="form-item">
                <el-input :disabled="isDisabled" v-model="editInfo.publishName" style="width: 300px" placeholder="请输入内容"></el-input>
            </el-form-item>
            <el-form-item v-if="!isDisabled">
                <el-button type="success" v-if="fateType.type=='edit'" @click="back">返回</el-button>
                <el-button type="primary" @click="addArticle" :disabled="btnDis">定时发布</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {
        noNullProp
    } from '@/utils/common.js'
    export default {
        data() {
            var validatePoint = (rule, value, callback) => {
                if (this.editInfo.luckyScore1.toString().indexOf('.') != -1 || this.editInfo.luckyScore2.toString().indexOf('.') != -1 || this.editInfo.luckyScore3.toString().indexOf('.') != -1 || this.editInfo.luckyScore4.toString().indexOf('.') != -1) {
                    callback(new Error('不能包含小数点'));
                } else if (this.editInfo.luckyScore1 > 100 || this.editInfo.luckyScore2 > 100 || this.editInfo.luckyScore3 > 100 || this.editInfo.luckyScore4 > 100) {
                    callback(new Error('值不能大于100'));
                } else if ((this.editInfo.luckyScore1 < 1 && this.editInfo.luckyScore1 != '') || (this.editInfo.luckyScore2 < 1 && this.editInfo.luckyScore2 != '') || (this.editInfo.luckyScore3 < 1 && this.editInfo.luckyScore3 != '') || (this.editInfo.luckyScore4 < 1 && this.editInfo.luckyScore4 != '')) {
                    callback(new Error('值不能小于1'));
                } else {
                    callback();
                }
            };
            var validatePoint2 = (rule, value, callback) => {
                if (this.editInfo.luckyScoreMore1.toString().indexOf('.') != -1 || this.editInfo.luckyScoreMore2.toString().indexOf('.') != -1 || this.editInfo.luckyScoreMore3.toString().indexOf('.') != -1 || this.editInfo.luckyScoreMore4.toString().indexOf('.') != -1) {
                    callback(new Error('不能包含小数点'));
                } else if (this.editInfo.luckyScoreMore1 > 5 || this.editInfo.luckyScoreMore2 > 5 || this.editInfo.luckyScoreMore3 > 5 || this.editInfo.luckyScoreMore4 > 5) {
                    callback(new Error('值不能大于5'));
                } else if ((this.editInfo.luckyScoreMore1 < 1 && this.editInfo.luckyScoreMore1 != '') || (this.editInfo.luckyScoreMore2 < 1 && this.editInfo.luckyScoreMore2 != '') || (this.editInfo.luckyScoreMore3 < 1 && this.editInfo.luckyScoreMore3 != '') || (this.editInfo.luckyScoreMore4 < 1 && this.editInfo.luckyScoreMore4 != '')) {
                    callback(new Error('值不能小于1'));
                } else {
                    callback();
                }
            };
            var validateFate = (rule, value, callback) => {
                if (this.editInfo.luckyType1.length > 4 || this.editInfo.luckyType2.length > 4 || this.editInfo.luckyType3.length > 4 || this.editInfo.luckyType4.length > 4) {
                    callback(new Error('不能超过四个字'));
                } else {
                    callback();
                }
            };
            var validateFate2 = (rule, value, callback) => {
                if (this.editInfo.luckyTypeMore1.length > 4 || this.editInfo.luckyTypeMore2.length > 4 || this.editInfo.luckyTypeMore3.length > 4 || this.editInfo.luckyTypeMore4.length > 4) {
                    callback(new Error('不能超过四个字'));
                } else {
                    callback();
                }
            };
            return {
                editInfo: {
                    constellationId: '',
                    luckyType1: '',
                    luckyType2: '',
                    luckyType3: '',
                    luckyType4: '',
                    luckyScore1: '',
                    luckyScore2: '',
                    luckyScore3: '',
                    luckyScore4: '',
                    remindToday: '',
                    luckyTypeMore1: '',
                    luckyTypeMore2: '',
                    luckyTypeMore3: '',
                    luckyTypeMore4: '',
                    luckyScoreMore1: '',
                    luckyScoreMore2: '',
                    luckyScoreMore3: '',
                    luckyScoreMore4: '',
                    luckyWords1: '',
                    luckyWords2: '',
                    luckyWords3: '',
                    luckyWords4: '',
                    toDo: '',
                    notDo: '',
                    publishTime: '',
                    status: '',
                    publishName: ""
                },
                defaultInfo: {
                    constellationId: '',
                    luckyType1: '',
                    luckyType2: '',
                    luckyType3: '',
                    luckyType4: '',
                    luckyScore1: '',
                    luckyScore2: '',
                    luckyScore3: '',
                    luckyScore4: '',
                    remindToday: '',
                    luckyTypeMore1: '',
                    luckyTypeMore2: '',
                    luckyTypeMore3: '',
                    luckyTypeMore4: '',
                    luckyScoreMore1: '',
                    luckyScoreMore2: '',
                    luckyScoreMore3: '',
                    luckyScoreMore4: '',
                    luckyWords1: '',
                    luckyWords2: '',
                    luckyWords3: '',
                    luckyWords4: '',
                    toDo: '',
                    notDo: '',
                    publishTime: '',
                    status: '',
                    publishName: ""
                },
                options: [],
                showLoading: true,
                fateType: {},
                api: '',
                isDisabled: false,
                ruleFrom: {
                    score: {
                        validator: validatePoint,
                        trigger: 'blur'
                    },
                    score2: {
                        validator: validatePoint2,
                        trigger: 'blur'
                    },
                    fate: {
                        validator: validateFate,
                        trigger: 'blur'
                    },
                    fateMore: {
                        validator: validateFate2,
                        trigger: 'blur'
                    },
                },
                btnDis: false,
                editType: window.location.href
            }
        },
        methods: {
            addArticle() {
                this.editInfo.status = 0;
                const _self = this;
                this.btnDis = true;
                this.$refs.form.validate(valid => {
                    if (valid) {
                        _self.submit();
                    } else {
                        _self.btnDis = false;
                        return false;
                    }
                })
            },
            submit() {
                if (this.fateType.type == 'edit') {
                    this.api = "tiLucky/json/updateTiLuckyById"
                    this.editInfo.id = this.fateType.id;
                } else {
                    this.api = "tiLucky/json/addTiLucky"
                }
                console.log(this.editInfo);

                if (noNullProp(this.editInfo)) {
                    this.showLoading = true;
                    this.$http.post({
                        url: this.api,
                        params: this.editInfo,
                        success: res => {
                            if (res.status == 'SUCCESS') {
                                if (this.fateType.type == 'edit') {
                                    this.$message.success('修改成功！');
                                    this.$router.go(-1)
                                } else {
                                    this.editInfo = JSON.parse(JSON.stringify(this.defaultInfo));
                                    this.$message.success(res.message);
                                }
                            } else {
                                this.$message.error('添加失败！');
                            }
                            this.showLoading = false;
                            this.btnDis = false;
                        },
                        excep: () => {
                            this.$message.error('网络错误');
                            this.showLoading = false;
                            this.btnDis = false;
                        }
                    })
                } else {
                    this.$message.error('信息填充不完整！');
                    this.btnDis = false;
                }
            },
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
                            this.showLoading = false;
                        })

                    },
                    excep: () => {
                        this.$message.error('网络错误');
                        this.showLoading = false;
                    }
                })
            },
            getFate() {
                this.showLoading = true;


                this.$http.post({
                    url: "tiLucky/json/getTiLuckyById",
                    params: {
                        id: this.fateType.id
                    },
                    success: res => {
                        this.editInfo = {
                            constellationId: res.data.constellationId || '',
                            luckyType1: res.data.luckyType1 || '',
                            luckyType2: res.data.luckyType2 || '',
                            luckyType3: res.data.luckyType3 || '',
                            luckyType4: res.data.luckyType4 || '',
                            luckyScore1: res.data.luckyScore1 || '',
                            luckyScore2: res.data.luckyScore2 || '',
                            luckyScore3: res.data.luckyScore3 || '',
                            luckyScore4: res.data.luckyScore4 || '',
                            remindToday: res.data.remindToday || '',
                            luckyTypeMore1: res.data.luckyTypeMore1 || '',
                            luckyTypeMore2: res.data.luckyTypeMore2 || '',
                            luckyTypeMore3: res.data.luckyTypeMore3 || '',
                            luckyTypeMore4: res.data.luckyTypeMore4 || '',
                            luckyScoreMore1: res.data.luckyScoreMore1 || '',
                            luckyScoreMore2: res.data.luckyScoreMore2 || '',
                            luckyScoreMore3: res.data.luckyScoreMore3 || '',
                            luckyScoreMore4: res.data.luckyScoreMore4 || '',
                            luckyWords1: res.data.luckyWords1 || '',
                            luckyWords2: res.data.luckyWords2 || '',
                            luckyWords3: res.data.luckyWords3 || '',
                            luckyWords4: res.data.luckyWords4 || '',
                            toDo: res.data.toDo || '',
                            notDo: res.data.notDo || '',
                            publishTime: res.data.publishTime || '',
                            status: res.data.status || 1,
                            publishName: res.data.publishName || '',
                        }
                    },
                    excep: () => {
                        this.$message.error('网络错误');
                        this.showLoading = false;
                    }
                })

            },
            back() {
                this.$router.go(-1)
            },
            getType(e) {
                if (this.fateType.type != 'edit') {
                    this.showLoading = true;
                    this.$http.post({
                        url: '/tiLucky/json/findTiLuckysByPage',
                        success: res => {
                            let data = res.data.list[0];
                            this.editInfo = {
                                luckyType1: data.luckyType1 || '',
                                luckyType2: data.luckyType2 || '',
                                luckyType3: data.luckyType3 || '',
                                luckyType4: data.luckyType4 || '',
                                luckyScore1: '',
                                luckyScore2: '',
                                luckyScore3: '',
                                luckyScore4: '',
                                remindToday: '',
                                luckyTypeMore1: data.luckyTypeMore1 || '',
                                luckyTypeMore2: data.luckyTypeMore2 || '',
                                luckyTypeMore3: data.luckyTypeMore3 || '',
                                luckyTypeMore4: data.luckyTypeMore4 || '',
                                luckyScoreMore1: '',
                                luckyScoreMore2: '',
                                luckyScoreMore3: '',
                                luckyScoreMore4: '',
                                luckyWords1: '',
                                luckyWords2: '',
                                luckyWords3: '',
                                luckyWords4: '',
                                toDo: '',
                                notDo: '',
                                publishTime: '',
                                status: 1,
                                publishName: '',
                            }

                            if (e) {
                                this.editInfo.constellationId = e;
                            }
                            this.showLoading = false;
                        },
                        excep: err => {
                            this.showLoading = false;
                        }
                    })
                }
            }
        },
        activated() {
            this.getOptions();
            this.fateType = this.$router.currentRoute.query;
            this.fateType.disabled == true ? this.isDisabled = true : this.isDisabled = false;
            if (this.fateType.type == 'edit') {
                this.getFate();
            } else {
                this.getType();
            }
        }
    }
</script>

<style scoped>
    .container {
        width: 100%;
        height: 100%;
        padding: 10px;
    }

    .form-item {
        position: relative;
        padding-left: 25px;
    }

    .form-item::before {
        position: absolute;
        left: 10px;
        content: '';
        width: 5px;
        border-left: 5px solid #7e06ee;
        height: 30px;
        margin-top: 5px;
    }

    .line {
        width: 100%;
        height: 1px;
        border: 1px solid #ebebe8;
        margin: 20px 0;
    }
</style>

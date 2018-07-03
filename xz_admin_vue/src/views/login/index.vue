<template lang="html">
    <div class="login-container" @keyup.enter="submitForm('ruleForm2')">
        <el-form v-loading.body="isLoading" element-loading-text="正在登陆中..." element-loading-background="rgba(0, 0, 0, 0)" :model="ruleForm2" status-icon :rules="rules2" ref="ruleForm2" label-width="100px" class="demo-ruleForm">
            <h3 class="title">星座小程序--后台管理系统</h3>
            <el-form-item label="用户名：" prop="username">
                <el-input type="text" v-model="ruleForm2.username" auto-complete="off"></el-input>
            </el-form-item>
            <el-form-item label="密 码：" prop="pass">
                <el-input type="password" v-model="ruleForm2.password" auto-complete="off"></el-input>
            </el-form-item>

            <el-form-item label-width="0">
                <div class="submit-btn" @click="submitForm('ruleForm2')">提交</div>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import {
        mapGetters
    } from 'vuex'
    export default {
        data() {
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    callback();
                }
            };
            var validateUsername = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入用户名'));
                } else {
                    callback();
                }
            };
            return {
                ruleForm2: {
                    username: localStorage.getItem("loginUsername") || "",
                    password: '',
                },
                isLoading: false,
                rules2: {
                    password: [{
                        validator: validatePass,
                        trigger: 'blur'
                    }],
                    username: [{
                        validator: validateUsername,
                        trigger: 'blur'
                    }]
                },
            };
        },
        computed: {
            ...mapGetters([
                'logined'
            ])
        },
        methods: {
            submitForm(formName) {
                this.isLoading = true;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        this.$http.post({
                            url: '/f00/login',
                            params: this.ruleForm2,
                            success: data => {
                                if (data.status == 'SUCCESS') {
                                    this.$store.dispatch("Login", true);
                                    this.$store.commit("SET_NICKNAME", this.ruleForm2.username);
                                    this.isLoading = false;
                                    this.$router.push("/")
                                }else{
                                    this.isLoading = false;
                                    this.$message({
                                        message: data.message,
                                        type: 'error'
                                    });
                                }

                            }
                        })
                        // this.$store.dispatch("Login", true);
                        // this.$store.commit("SET_NICKNAME", this.ruleForm2.username);
                        // setTimeout(() => {
                        //     this.isLoading = false;
                        //     this.$router.push("/")
                        // }, 1000);

                    } else {
                        return false;
                    }
                });
            },
        },
        created() {
            // if(this.logined){
            //   this.$router.push("/")
            // }
        }
    }
</script>

<style lang="css">
    .login-container {
        position: absolute;
        left: 0;
        right: 0;
        width: 400px;
        padding: 120px 0;
        width: 100%;
        height: 100%;
        background-color: #2d3a4b;
    }

    .login-container form {
        position: absolute;
        left: 0;
        right: 0;
        width: 400px;
        padding: 35px 35px 15px;
        margin: 120px auto;
    }

    .login-container .el-form-item {
        border: 1px solid hsla(0, 0%, 100%, .1);
        background: rgba(0, 0, 0, .1);
        border-radius: 5px;
        color: #454545;
    }

    .login-container .el-form-item:last-of-type .el-form-item__content {
        margin-left: 0 !important;
    }

    .login-container .el-select {
        width: 100%;
    }

    .login-container .el-form-item__label {
        color: #fff;
        line-height: 47px;
        height: 47px;
    }

    .login-container .el-input input {
        background: transparent !important;
        border: 0;
        -webkit-appearance: none;
        border-radius: 0;
        padding: 12px 5px 12px 15px;
        color: #eee;
        height: 47px;
    }

    .login-container .title {
        font-size: 26px;
        font-weight: 400;
        color: #eee;
        margin: 0 auto 40px;
        text-align: center;
        font-weight: 700;
    }

    .login-container .el-form-item__content {
        line-height: 40px;
        position: relative;
        font-size: 14px;
    }

    .submit-btn {
        width: 100%;
        background: #66b1ff;
        border-color: #66b1ff;
        color: #fff;
        display: inline-block;
        line-height: 1;
        white-space: nowrap;
        cursor: pointer;
        -webkit-appearance: none;
        text-align: center;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        outline: 0;
        margin: 0;
        -webkit-transition: .1s;
        transition: .1s;
        font-weight: 500;
        padding: 12px 20px;
        font-size: 14px;
        border-radius: 4px;
    }
</style>

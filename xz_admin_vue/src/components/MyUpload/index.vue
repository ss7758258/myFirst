<style scope>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }

    .fileUpload .el-upload {
        width: 100%;
    }

    .fileUpload .el-input-group__append {
        width: 25%;
    }

    .myBtn {
        color: #ffffff;
        background-color: #409EFF;
        border-color: #409EFF;
        padding: 12px 20px;
        line-height: 1;
        white-space: nowrap;
        cursor: pointer;
        text-align: center;
        font-weight: 500;
        font-size: 14px;
    }
</style>

<template>
    <el-upload v-if="type=='img'" class="avatar-uploader" :with-credentials="true" name="file" :action="uploadUrl" :show-file-list="false" :data="uploadData" v-loading.body="loading" element-loading-text="正在上传数据..." :on-success="handleAvatarSuccess" :on-change="onUploadChange"
        :on-progress="onProgress">
        <img v-if="model && !uploading" :src="getPic(model)" class="avatar">
        <el-progress v-else-if="uploading" type="circle" :percentage="uploadingNum" :status="status"></el-progress>
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>

    </el-upload>

    <div v-else>
        <el-input class="fileUpload" placeholder="请输入内容" v-model="fileName" @change="change">
            <template slot="append">
                    <el-upload :with-credentials="true" name="file" :action="uploadUrl" :show-file-list="false" :data="uploadData" :on-success="handleAvatarSuccess" :on-change="onUploadChange" :on-progress="onProgress">
                        <el-progress v-if="uploading" :text-inside="true" :stroke-width="18" :percentage="uploadingNum" status="success"></el-progress>
                        <el-tooltip v-else class="item" effect="dark" content="如果是链接请直接填写,格式类似于http://xxxxx.xxx/xxx" placement="top-start">
                            <div>
                                <el-button>选择文件</el-button>

                            </div>
                        </el-tooltip>
                    </el-upload>
</template>

    </el-input>
    <el-button v-if="false" style="float:right;width:25%" @click="downFile" type="primary">下载查看</el-button>
    <a :href="url" target="_blank" ref="down"></a>
</div>

</template>

<script>
    export default {
        props: ['type', "model", "functionName", "audioUrl", "uploadUrls"],
        data() {
            return {
                uploadUrl: BASE_URL + this.uploadUrls,
                imageUrl: 'https://xingzuo-1256217146.file.myqcloud.com/',
                // imageUrl:  "http://localhost:8282/xz_admin/",
                uploadData: {
                    functionName: this.functionName
                },
                fileName: "",
                loading: false,
                uploading: false,
                uploadingNum: 0,
                status: "",
                fid: "",
                url: ""
            };
        },
        methods: {
            onClick(val) {
                if (isNaN(this.model) && this.model != undefined && this.model.indexOf("://")) {
                    this.fileName = this.model
                    alert(0)
                } else {
                    this.fileName = "";
                    alert(1)
                }

            },
            handleAvatarSuccess(res, file) {
                this.uploadingNum = Math.ceil(Math.random()*100+50);
                    if (res.status == 'SUCCESS') {
                    this.status = "success";
                    this.uploadingNum = 100;
                    setTimeout(() => {
                        this.fid = res.data;
                        this.fileName = file.name;
                        this.$emit('update:model', res.data)
                        this.uploading = false;
                    }, 500)
                }else{
                    this.status = 'exception'
                }

                // this.imageUrl = URL.createObjectURL(file.raw);
            },
            change(val) {
                if (!isNaN(this.model)) {
                    // this.getFileName(this.fid)
                } else {
                    this.fileName = this.model;
                }
                this.$emit('update:model', val)
            },
            onProgress(event, file, fileList) {

                // this.uploadingNum = parseFloat(event.percent.toFixed(2));
                // var timer = setInterval(() => {
                //     this.uploadingNum += Math.ceil(Math.random() * 5 + 1);
                //     if (this.uploadingNum >= 100) {
                //         clearInterval(timer);
                //         this.uploadingNum = 0;
                //     }
                // }, 10)

            },
            beforeAvatarUpload(file) {
                // const isJPG = file.type === 'image/jpeg';
                // const isLt2M = file.size / 1024 / 1024 < 2;

                // if (!isJPG) {
                //   this.$message.error('上传头像图片只能是 JPG 格式!');
                // }
                // if (!isLt2M) {
                //   this.$message.error('上传头像图片大小不能超过 2MB!');
                // }
                // return isJPG && isLt2M;
                // let param = {
                //   file:file
                // }
                // var formdata = new FormData();
                // formdata.append("file", file);

                // this.$http.post("/json/fileUpload",formdata,(data)=>{
                //      this.$emit('update:model', data.url)
                // })
                return true;
            },
            onUploadChange(file, fileList) {
                // this.loading = true;
                this.uploading = true;
            },
            getFileName(id) {
                if (id) {
                    // this.$http.post("/getFiles", {
                    //     ids: id
                    // }, (data) => {
                    //     console.log(data);
                    // this.fileName = data[id].filename;
                    // })
                }

            },
            downFile() {
                let id = this.fid;
                this.url = BASE_URL + id;
                console.log(this.url)
                setTimeout(() => {
                    this.$refs.down.click()
                }, 100)
            },
            getPic(url) {
                if (typeof url == "string" && url.indexOf("http") != -1) {
                    return url;
                } else {
                    return this.imageUrl + url;
                }
            }
        },
        watch: {
            model(newVal) {
                this.fid = ""
                if (isNaN(newVal)) {
                    this.fileName = this.model;
                } else {
                    this.getFileName(newVal);
                    this.fid = newVal
                }
            }
        },
        created() {

            if (!this.model || isNaN(this.model)) {
                this.fileName = this.model;
            } else {
                this.getFileName(this.model);
                this.fid = this.model
            }
        }
    }
</script>

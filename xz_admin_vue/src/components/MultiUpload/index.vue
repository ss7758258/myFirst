<style scope>

</style>

<template>

    <div>
        <div v-if="type=='img'">
            <el-upload multiple  :with-credentials="true" name="file" :action="uploadUrl" :data="uploadData" :on-success="onSuccess"
                :file-list="fileList"
            >
                        <el-button type='primary'>选择文件</el-button>
            </el-upload>
        </div>
         <div v-else>
            <el-upload  multiple :with-credentials="true" name="file" :action="uploadUrl" :data="uploadData" :on-success="onSuccess"
                :file-list="fileList"
                >
            <el-button type='primary'>选择文件</el-button>
        </el-upload>
        </div>
    </div>


</template>

<script>

export default {
    props: ['type', "model", "functionName"],
    data() {
        return {
            uploadUrl: BASE_URL + "/json/fileUpload",
            imageUrl: BASE_URL + "/",
            uploadData: {
                functionName: this.functionName
            },
            loading: false,
            uploading: false,
            uploadingNum: 0,
            status: "",
            fileList:[],
            filesPath:'',
            fids:[]
        };
    },
    methods: {
        onSuccess(res,file,list){
            if(res.code == 200){
                setTimeout(()=>{
                    let old = this.model;
                    if(this.model.length>0){
                        alert(1)
                       old =  old+","+res.data.fid;
                    }else{
                        alert(0)
                    }
                    this.$emit('update:model', old);
                    this.uploading = false;
                })
            }
        },
        getFiles(params){
            this.$http.post('/getFiles', params, res => {
                if (res) {
                    this.fileList = [];
                    for (var item in res) {
                        var obj = {};
                        obj.name = res[item].name;
                        obj.url = res[item].path;
                        this.fileList.push(obj);
                    }
                }
            })
        }
    },
    watch:{
        model(newVal,oldVal){
            let ids = newVal;
            if(ids){
                this.getFiles({ids:ids})
            }
        }
    },
    mounted(){
       
    }
}

</script>

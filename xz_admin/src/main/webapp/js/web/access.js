function saveRecord(opt)
{
    var url="access/json/addAccess";
    var resourceIdField  = $("#resourceId").val();var resourceNameField  = $("#resourceName").val();var resourceCodeField  = $("#resourceCode").val();var statusField  = $("#status").val();var useridField  = $("#userid").val();var usernameField  = $("#username").val();var addFlagField=$('input:radio[name="addFlag"]:checked').val();var delFlagField=$('input:radio[name="delFlag"]:checked').val();var updFlagField=$('input:radio[name="updFlag"]:checked').val();var viewFlagField=$('input:radio[name="viewFlag"]:checked').val();
	var data = {resourceId: resourceIdField,resourceName: resourceNameField,resourceCode: resourceCodeField,status: statusField,userid: useridField,username: usernameField,addFlag: addFlagField,delFlag: delFlagField,updFlag: updFlagField,viewFlag: viewFlagField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="accessList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="access"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="access/json/updateAccessById";
    var idField  = $("#id").val();var resourceIdField  = $("#resourceId").val();var resourceNameField  = $("#resourceName").val();var resourceCodeField  = $("#resourceCode").val();var statusField  = $("#status").val();var useridField  = $("#userid").val();var usernameField  = $("#username").val();var addFlagField=$('input:radio[name="addFlag"]:checked').val();var delFlagField=$('input:radio[name="delFlag"]:checked').val();var updFlagField=$('input:radio[name="updFlag"]:checked').val();var viewFlagField=$('input:radio[name="viewFlag"]:checked').val();
	var data = {id: idField,resourceId: resourceIdField,resourceName: resourceNameField,resourceCode: resourceCodeField,status: statusField,userid: useridField,username: usernameField,addFlag: addFlagField,delFlag: delFlagField,updFlag: updFlagField,viewFlag: viewFlagField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="accessList.html";
    })
};
function getRecord(id)
{
    var url="access/json/getAccessById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#resourceId").val(result.resourceId);$("#resourceName").val(result.resourceName);$("#resourceCode").val(result.resourceCode);$("#status").val(result.status);$("#userid").val(result.userid);$("#username").val(result.username);$("input[name='addFlag']").eq(!result.addFlag).attr("checked","checked");$("input[name='delFlag']").eq(!result.delFlag).attr("checked","checked");$("input[name='updFlag']").eq(!result.updFlag).attr("checked","checked");$("input[name='viewFlag']").eq(!result.viewFlag).attr("checked","checked");
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="access/json/delAccessById";
    var data = {id: id};
    msg_confirm('确认要删除?',function(){
        ajaxPost(url, data, function (result) {
            $(opt).parent().parent().remove();
        })
    })
};
function initTable(opt)
{
    var pageNum = 1;
    if(opt==null||opt==undefined)
        pageNum=1;
    else
        pageNum = $(opt).attr("data-dt-idx");
    var url="access/json/findAccesssByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>资源ID</th><th>资源名称</th><th>资源代码</th><th>状态</th><th>用户名称</th><th>增加权限</th><th>删除权限</th><th>更新权限</th><th>查看权限</th>';
        //动态数据End
        header = header + '    <th>操作</th>';
        header = header + '    </tr>';
        header = header + '    </thead>';
        var body = "<tbody><tr>";
        for(var i=0;i<list.length;i++)
        {
            var obj = list[i];
            body = body+'<tr>';
            //动态数据Start
            var status;if(obj.status==1)status = '启用';if(obj.status==0)status = '锁定';var addFlag;if(obj.addFlag==true)addFlag = '开启';if(obj.addFlag==false)addFlag = '锁定';var delFlag;if(obj.delFlag==true)delFlag = '开启';if(obj.delFlag==false)delFlag = '锁定';var updFlag;if(obj.updFlag==true)updFlag = '开启';if(obj.updFlag==false)updFlag = '锁定';var viewFlag;if(obj.viewFlag==true)viewFlag = '开启';if(obj.viewFlag==false)viewFlag = '锁定';

            body = body+'<td>'+obj.id+'</td><td>'+obj.resourceId+'</td><td>'+obj.resourceName+'</td><td>'+obj.resourceCode+'</td><td>'+status+'</td><td>'+obj.username+'</td><td>'+addFlag+'</td><td>'+delFlag+'</td><td>'+updFlag+'</td><td>'+viewFlag+'</td>';
            //动态数据End
            body = body+'<td>';
            body = body+'<button class="btn btn-success btn-circle" type="button" data="'+obj.id+'" onclick="changePage(this,\'View\')"><i class="fa fa-info"></i></button>';
            body = body+'<button class="btn btn-primary btn-circle" type="button" data="'+obj.id+'" onclick="changePage(this,\'Update\')"><i class="fa fa-list"></i></button>';
            body = body+'<button class="btn btn-warning btn-circle" type="button" data="'+obj.id+'" onclick="removeRecord(this)"><i class="fa fa-times"></i></button></td>';
            body = body+'</td>';
            body = body+'</tr>';
        }
        body = body+'</tbody>';
        html = header+body;
        $("#table").html(html);
        buildPager(pager);
    });
}

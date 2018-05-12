function saveRecord(opt)
{
    var url="tiAdmin/json/addTiAdmin";
    var usernameField  = $("#username").val();var passwordField  = $("#password").val();var statusField  = $("#status").val();
	var data = {username: usernameField,password: passwordField,status: statusField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiAdminList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tiAdmin"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tiAdmin/json/updateTiAdminById";
    var idField  = $("#id").val();var usernameField  = $("#username").val();var passwordField  = $("#password").val();var statusField  = $("#status").val();
	var data = {id: idField,username: usernameField,password: passwordField,status: statusField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiAdminList.html";
    })
};
function getRecord(id)
{
    var url="tiAdmin/json/getTiAdminById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#username").val(result.username);$("#password").val(result.password);$("#status").val(result.status);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tiAdmin/json/delTiAdminById";
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
    var url="tiAdmin/json/findTiAdminsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>用户名称</th><th>状态</th>';
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
            var status;if(obj.status==1)status = '启用';if(obj.status==0)status = '锁定';

            body = body+'<td>'+obj.id+'</td><td>'+obj.username+'</td><td>'+status+'</td>';
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

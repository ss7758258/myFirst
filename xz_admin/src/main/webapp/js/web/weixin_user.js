function saveRecord(opt)
{
    var url="weixinUser/json/addWeixinUser";
    var constellationIdField  = $("#constellationId").val();var openIdField  = $("#openId").val();var userNameField  = $("#userName").val();var nickNameField  = $("#nickName").val();var phoneNoField  = $("#phoneNo").val();var isDisabledField  = $("#isDisabled").val();var headImageField  = $("#headImage").val();var genderField  = $("#gender").val();var passwdField  = $("#passwd").val();var addressField  = $("#address").val();
	var data = {constellationId: constellationIdField,openId: openIdField,userName: userNameField,nickName: nickNameField,phoneNo: phoneNoField,isDisabled: isDisabledField,headImage: headImageField,gender: genderField,passwd: passwdField,address: addressField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="weixinUserList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="weixinUser"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="weixinUser/json/updateWeixinUserById";
    var idField  = $("#id").val();var constellationIdField  = $("#constellationId").val();var openIdField  = $("#openId").val();var userNameField  = $("#userName").val();var nickNameField  = $("#nickName").val();var phoneNoField  = $("#phoneNo").val();var isDisabledField  = $("#isDisabled").val();var headImageField  = $("#headImage").val();var genderField  = $("#gender").val();var passwdField  = $("#passwd").val();var addressField  = $("#address").val();
	var data = {id: idField,constellationId: constellationIdField,openId: openIdField,userName: userNameField,nickName: nickNameField,phoneNo: phoneNoField,isDisabled: isDisabledField,headImage: headImageField,gender: genderField,passwd: passwdField,address: addressField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="weixinUserList.html";
    })
};
function getRecord(id)
{
    var url="weixinUser/json/getWeixinUserById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#constellationId").val(result.constellationId);$("#openId").val(result.openId);$("#userName").val(result.userName);$("#nickName").val(result.nickName);$("#phoneNo").val(result.phoneNo);$("#isDisabled").val(result.isDisabled);$("#headImage").val(result.headImage);$("#gender").val(result.gender);$("#passwd").val(result.passwd);$("#address").val(result.address);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="weixinUser/json/delWeixinUserById";
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
    var url="weixinUser/json/findWeixinUsersByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>星座id</th><th>微信openId</th><th>真实姓名</th><th>昵称</th><th>电话号码</th><th>是否可用 是 否</th><th>头像</th><th>性别男 女</th><th>密码</th><th>地址</th>';
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
            

            body = body+'<td>'+obj.id+'</td><td>'+obj.constellationId+'</td><td>'+obj.openId+'</td><td>'+obj.userName+'</td><td>'+obj.nickName+'</td><td>'+obj.phoneNo+'</td><td>'+obj.isDisabled+'</td><td>'+obj.headImage+'</td><td>'+obj.gender+'</td><td>'+obj.passwd+'</td><td>'+obj.address+'</td>';
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

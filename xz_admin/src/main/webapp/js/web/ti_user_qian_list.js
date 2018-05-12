function saveRecord(opt)
{
    var url="tiUserQianList/json/addTiUserQianList";
    var qianDateField  = $("#qianDate").val();var statusField  = $("#status").val();var qianNameField  = $("#qianName").val();var qianContentField  = $("#qianContent").val();var userIdField  = $("#userId").val();var friendOpenId1Field  = $("#friendOpenId1").val();var friendOpenId2Field  = $("#friendOpenId2").val();var friendOpenId3Field  = $("#friendOpenId3").val();var friendOpenId4Field  = $("#friendOpenId4").val();var friendOpenId5Field  = $("#friendOpenId5").val();
	var data = {qianDate: qianDateField,status: statusField,qianName: qianNameField,qianContent: qianContentField,userId: userIdField,friendOpenId1: friendOpenId1Field,friendOpenId2: friendOpenId2Field,friendOpenId3: friendOpenId3Field,friendOpenId4: friendOpenId4Field,friendOpenId5: friendOpenId5Field,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiUserQianListList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tiUserQianList"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tiUserQianList/json/updateTiUserQianListById";
    var idField  = $("#id").val();var qianDateField  = $("#qianDate").val();var statusField  = $("#status").val();var qianNameField  = $("#qianName").val();var qianContentField  = $("#qianContent").val();var userIdField  = $("#userId").val();var friendOpenId1Field  = $("#friendOpenId1").val();var friendOpenId2Field  = $("#friendOpenId2").val();var friendOpenId3Field  = $("#friendOpenId3").val();var friendOpenId4Field  = $("#friendOpenId4").val();var friendOpenId5Field  = $("#friendOpenId5").val();
	var data = {id: idField,qianDate: qianDateField,status: statusField,qianName: qianNameField,qianContent: qianContentField,userId: userIdField,friendOpenId1: friendOpenId1Field,friendOpenId2: friendOpenId2Field,friendOpenId3: friendOpenId3Field,friendOpenId4: friendOpenId4Field,friendOpenId5: friendOpenId5Field,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiUserQianListList.html";
    })
};
function getRecord(id)
{
    var url="tiUserQianList/json/getTiUserQianListById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#qianDate").val(result.qianDate);$("#status").val(result.status);$("#qianName").val(result.qianName);$("#qianContent").val(result.qianContent);$("#userId").val(result.userId);$("#friendOpenId1").val(result.friendOpenId1);$("#friendOpenId2").val(result.friendOpenId2);$("#friendOpenId3").val(result.friendOpenId3);$("#friendOpenId4").val(result.friendOpenId4);$("#friendOpenId5").val(result.friendOpenId5);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tiUserQianList/json/delTiUserQianListById";
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
    var url="tiUserQianList/json/findTiUserQianListsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>抽签时间</th><th>状态</th><th>抽签人</th><th>抽签内容</th><th>抽签人ID</th><th>好友openId</th><th>好友openId</th><th>好友openId</th><th>好友openId</th><th>好友openId</th>';
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
            var status;if(obj.status==1)status = '已拆';if(obj.status==0)status = '拆迁中';

            body = body+'<td>'+obj.id+'</td><td>'+obj.qianDate+'</td><td>'+status+'</td><td>'+obj.qianName+'</td><td>'+obj.qianContent+'</td><td>'+obj.userId+'</td><td>'+obj.friendOpenId1+'</td><td>'+obj.friendOpenId2+'</td><td>'+obj.friendOpenId3+'</td><td>'+obj.friendOpenId4+'</td><td>'+obj.friendOpenId5+'</td>';
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

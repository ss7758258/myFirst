function saveRecord(opt)
{
    var url="tiYanList/json/addTiYanList";
    var constellationIdField  = $("#constellationId").val();var prevPicField  = $("#prevPic").val();var publishPersonField  = $("#publishPerson").val();var publishStatusField  = $("#publishStatus").val();var publishTimeField  = $("#publishTime").val();
	var data = {constellationId: constellationIdField,prevPic: prevPicField,publishPerson: publishPersonField,publishStatus: publishStatusField,publishTime: publishTimeField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiYanListList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tiYanList"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tiYanList/json/updateTiYanListById";
    var idField  = $("#id").val();var constellationIdField  = $("#constellationId").val();var prevPicField  = $("#prevPic").val();var publishPersonField  = $("#publishPerson").val();var publishStatusField  = $("#publishStatus").val();var publishTimeField  = $("#publishTime").val();
	var data = {id: idField,constellationId: constellationIdField,prevPic: prevPicField,publishPerson: publishPersonField,publishStatus: publishStatusField,publishTime: publishTimeField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiYanListList.html";
    })
};
function getRecord(id)
{
    var url="tiYanList/json/getTiYanListById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#constellationId").val(result.constellationId);$("#prevPic").val(result.prevPic);$("#publishPerson").val(result.publishPerson);$("#publishStatus").val(result.publishStatus);$("#publishTime").val(result.publishTime);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tiYanList/json/delTiYanListById";
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
    var url="tiYanList/json/findTiYanListsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>星座id</th><th>预览图</th><th>发布人</th><th>发布状态</th><th>发布时间</th>';
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
            

            body = body+'<td>'+obj.id+'</td><td>'+obj.constellationId+'</td><td>'+obj.prevPic+'</td><td>'+obj.publishPerson+'</td><td>'+obj.publishStatus+'</td><td>'+obj.publishTime+'</td>';
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

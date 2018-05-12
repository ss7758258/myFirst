function saveRecord(opt)
{
    var url="tcQianYanUrl/json/addTcQianYanUrl";
    var yanUrlField  = $("#yanUrl").val();var qianUrlField  = $("#qianUrl").val();
	var data = {yanUrl: yanUrlField,qianUrl: qianUrlField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tcQianYanUrlList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tcQianYanUrl"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tcQianYanUrl/json/updateTcQianYanUrlById";
    var idField  = $("#id").val();var yanUrlField  = $("#yanUrl").val();var qianUrlField  = $("#qianUrl").val();
	var data = {id: idField,yanUrl: yanUrlField,qianUrl: qianUrlField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tcQianYanUrlList.html";
    })
};
function getRecord(id)
{
    var url="tcQianYanUrl/json/getTcQianYanUrlById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#yanUrl").val(result.yanUrl);$("#qianUrl").val(result.qianUrl);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tcQianYanUrl/json/delTcQianYanUrlById";
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
    var url="tcQianYanUrl/json/findTcQianYanUrlsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>一言url</th><th>一签url</th>';
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
            

            body = body+'<td>'+obj.id+'</td><td>'+obj.yanUrl+'</td><td>'+obj.qianUrl+'</td>';
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

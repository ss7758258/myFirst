$(document).ready(function () {
    initTable();
});

function initTable(opt)
{
    var pageNum = 1;
    if(opt==null||opt==undefined)
        pageNum=1;
    else
        pageNum = $(opt).attr("data-dt-idx");
    var url="admin/json/findAdminsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '    <th>#id</th>';
        header = header + '    <th>username</th>';
        header = header + '    <th>password</th>';
        header = header + '    <th>age</th>';
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
            body = body+'<td>'+obj.id+'</td>';
            body = body+'<td>'+obj.username+'</td>';
            body = body+'<td>'+obj.password+'</td>';
            body = body+'<td>'+obj.age+'</td>';
            //动态数据End
            body = body+'<td><button class="btn btn-primary btn-circle" type="button"><i class="fa fa-list"></i></button><button class="btn btn-warning btn-circle" type="button"><i class="fa fa-times"></i></button></td>';
            body = body+'</tr>';
        }
        body = body+'</tbody>';
        html = header+body;
        $("#table").html(html);
        buildPager(pager);
    });
}
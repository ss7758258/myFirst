function saveRecord(opt)
{
    var url="<#_S>key</#_E>/json/add<#_S>Key</#_E>";
    //<#_S>jsAddContent</#_E>
    ajaxPost(url, data, function (result) {
        window.location.href ="<#_S>key</#_E>List.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="<#_S>key</#_E>"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="<#_S>key</#_E>/json/update<#_S>Key</#_E>ById";
    //<#_S>jsUpdateContent</#_E>
    ajaxPost(url, data, function (result) {
        window.location.href ="<#_S>key</#_E>List.html";
    })
};
function getRecord(id)
{
    var url="<#_S>key</#_E>/json/get<#_S>Key</#_E>ById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        //<#_S>jsGetContent</#_E>
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="<#_S>key</#_E>/json/del<#_S>Key</#_E>ById";
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
    var url="<#_S>key</#_E>/json/find<#_S>Key</#_E>sByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<#_S>header</#_E>';
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
            //<#_S>preContent</#_E>
            body = body+'<#_S>content</#_E>';
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
function saveRecord(opt)
{
    var url="tcConstellation/json/addTcConstellation";
    var constellationNameField  = $("#constellationName").val();var startDateField  = $("#startDate").val();var endDateField  = $("#endDate").val();var pictureUrlField  = $("#pictureUrl").val();var remarkField  = $("#remark").val();
	var data = {constellationName: constellationNameField,startDate: startDateField,endDate: endDateField,pictureUrl: pictureUrlField,remark: remarkField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tcConstellationList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tcConstellation"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tcConstellation/json/updateTcConstellationById";
    var idField  = $("#id").val();var constellationNameField  = $("#constellationName").val();var startDateField  = $("#startDate").val();var endDateField  = $("#endDate").val();var pictureUrlField  = $("#pictureUrl").val();var remarkField  = $("#remark").val();
	var data = {id: idField,constellationName: constellationNameField,startDate: startDateField,endDate: endDateField,pictureUrl: pictureUrlField,remark: remarkField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tcConstellationList.html";
    })
};
function getRecord(id)
{
    var url="tcConstellation/json/getTcConstellationById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#constellationName").val(result.constellationName);$("#startDate").val(result.startDate);$("#endDate").val(result.endDate);$("#pictureUrl").val(result.pictureUrl);$("#remark").val(result.remark);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tcConstellation/json/delTcConstellationById";
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
    var url="tcConstellation/json/findTcConstellationsByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>星座</th><th>月份开始日期</th><th>结束日期</th><th>背景图片</th><th>备注</th>';
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
            

            body = body+'<td>'+obj.id+'</td><td>'+obj.constellationName+'</td><td>'+obj.startDate+'</td><td>'+obj.endDate+'</td><td>'+obj.pictureUrl+'</td><td>'+obj.remark+'</td>';
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
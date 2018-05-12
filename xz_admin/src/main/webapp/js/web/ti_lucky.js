function saveRecord(opt)
{
    var url="tiLucky/json/addTiLucky";
    var constellationIdField  = $("#constellationId").val();var luckyType1Field  = $("#luckyType1").val();var luckyType2Field  = $("#luckyType2").val();var luckyType3Field  = $("#luckyType3").val();var luckyType4Field  = $("#luckyType4").val();var luckyScore1Field  = $("#luckyScore1").val();var luckyScore2Field  = $("#luckyScore2").val();var luckyScore3Field  = $("#luckyScore3").val();var luckyScore4Field  = $("#luckyScore4").val();var remindTodayField  = $("#remindToday").val();var luckyTypeMore1Field  = $("#luckyTypeMore1").val();var luckyTypeMore2Field  = $("#luckyTypeMore2").val();var luckyTypeMore3Field  = $("#luckyTypeMore3").val();var luckyTypeMore4Field  = $("#luckyTypeMore4").val();var luckyScoreMore1Field  = $("#luckyScoreMore1").val();var luckyScoreMore2Field  = $("#luckyScoreMore2").val();var luckyScoreMore3Field  = $("#luckyScoreMore3").val();var luckyScoreMore4Field  = $("#luckyScoreMore4").val();var luckyWords1Field  = $("#luckyWords1").val();var luckyWords2Field  = $("#luckyWords2").val();var luckyWords3Field  = $("#luckyWords3").val();var luckyWords4Field  = $("#luckyWords4").val();var toDoField  = $("#toDo").val();var notDoField  = $("#notDo").val();var publishTimeField  = $("#publishTime").val();var luckyDateField  = $("#luckyDate").val();
	var data = {constellationId: constellationIdField,luckyType1: luckyType1Field,luckyType2: luckyType2Field,luckyType3: luckyType3Field,luckyType4: luckyType4Field,luckyScore1: luckyScore1Field,luckyScore2: luckyScore2Field,luckyScore3: luckyScore3Field,luckyScore4: luckyScore4Field,remindToday: remindTodayField,luckyTypeMore1: luckyTypeMore1Field,luckyTypeMore2: luckyTypeMore2Field,luckyTypeMore3: luckyTypeMore3Field,luckyTypeMore4: luckyTypeMore4Field,luckyScoreMore1: luckyScoreMore1Field,luckyScoreMore2: luckyScoreMore2Field,luckyScoreMore3: luckyScoreMore3Field,luckyScoreMore4: luckyScoreMore4Field,luckyWords1: luckyWords1Field,luckyWords2: luckyWords2Field,luckyWords3: luckyWords3Field,luckyWords4: luckyWords4Field,toDo: toDoField,notDo: notDoField,publishTime: publishTimeField,luckyDate: luckyDateField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiLuckyList.html";
    })
};
function changePage(opt,functionName)
{
    var id = $(opt).attr("data");
    window.location.href ="tiLucky"+functionName+".html?id="+id;
};
function updateRecord(opt)
{
    var url="tiLucky/json/updateTiLuckyById";
    var idField  = $("#id").val();var constellationIdField  = $("#constellationId").val();var luckyType1Field  = $("#luckyType1").val();var luckyType2Field  = $("#luckyType2").val();var luckyType3Field  = $("#luckyType3").val();var luckyType4Field  = $("#luckyType4").val();var luckyScore1Field  = $("#luckyScore1").val();var luckyScore2Field  = $("#luckyScore2").val();var luckyScore3Field  = $("#luckyScore3").val();var luckyScore4Field  = $("#luckyScore4").val();var remindTodayField  = $("#remindToday").val();var luckyTypeMore1Field  = $("#luckyTypeMore1").val();var luckyTypeMore2Field  = $("#luckyTypeMore2").val();var luckyTypeMore3Field  = $("#luckyTypeMore3").val();var luckyTypeMore4Field  = $("#luckyTypeMore4").val();var luckyScoreMore1Field  = $("#luckyScoreMore1").val();var luckyScoreMore2Field  = $("#luckyScoreMore2").val();var luckyScoreMore3Field  = $("#luckyScoreMore3").val();var luckyScoreMore4Field  = $("#luckyScoreMore4").val();var luckyWords1Field  = $("#luckyWords1").val();var luckyWords2Field  = $("#luckyWords2").val();var luckyWords3Field  = $("#luckyWords3").val();var luckyWords4Field  = $("#luckyWords4").val();var toDoField  = $("#toDo").val();var notDoField  = $("#notDo").val();var publishTimeField  = $("#publishTime").val();var luckyDateField  = $("#luckyDate").val();
	var data = {id: idField,constellationId: constellationIdField,luckyType1: luckyType1Field,luckyType2: luckyType2Field,luckyType3: luckyType3Field,luckyType4: luckyType4Field,luckyScore1: luckyScore1Field,luckyScore2: luckyScore2Field,luckyScore3: luckyScore3Field,luckyScore4: luckyScore4Field,remindToday: remindTodayField,luckyTypeMore1: luckyTypeMore1Field,luckyTypeMore2: luckyTypeMore2Field,luckyTypeMore3: luckyTypeMore3Field,luckyTypeMore4: luckyTypeMore4Field,luckyScoreMore1: luckyScoreMore1Field,luckyScoreMore2: luckyScoreMore2Field,luckyScoreMore3: luckyScoreMore3Field,luckyScoreMore4: luckyScoreMore4Field,luckyWords1: luckyWords1Field,luckyWords2: luckyWords2Field,luckyWords3: luckyWords3Field,luckyWords4: luckyWords4Field,toDo: toDoField,notDo: notDoField,publishTime: publishTimeField,luckyDate: luckyDateField,testXXX:1};
    ajaxPost(url, data, function (result) {
        window.location.href ="tiLuckyList.html";
    })
};
function getRecord(id)
{
    var url="tiLucky/json/getTiLuckyById";
    var data = {id: id};
    ajaxPost(url, data, function (result) {
        if(result==null)back();
        $("#id").val(result.id);$("#constellationId").val(result.constellationId);$("#luckyType1").val(result.luckyType1);$("#luckyType2").val(result.luckyType2);$("#luckyType3").val(result.luckyType3);$("#luckyType4").val(result.luckyType4);$("#luckyScore1").val(result.luckyScore1);$("#luckyScore2").val(result.luckyScore2);$("#luckyScore3").val(result.luckyScore3);$("#luckyScore4").val(result.luckyScore4);$("#remindToday").val(result.remindToday);$("#luckyTypeMore1").val(result.luckyTypeMore1);$("#luckyTypeMore2").val(result.luckyTypeMore2);$("#luckyTypeMore3").val(result.luckyTypeMore3);$("#luckyTypeMore4").val(result.luckyTypeMore4);$("#luckyScoreMore1").val(result.luckyScoreMore1);$("#luckyScoreMore2").val(result.luckyScoreMore2);$("#luckyScoreMore3").val(result.luckyScoreMore3);$("#luckyScoreMore4").val(result.luckyScoreMore4);$("#luckyWords1").val(result.luckyWords1);$("#luckyWords2").val(result.luckyWords2);$("#luckyWords3").val(result.luckyWords3);$("#luckyWords4").val(result.luckyWords4);$("#toDo").val(result.toDo);$("#notDo").val(result.notDo);$("#publishTime").val(result.publishTime);$("#luckyDate").val(result.luckyDate);
    })
};
function removeRecord(opt)
{
    var id = $(opt).attr("data");
    var url="tiLucky/json/delTiLuckyById";
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
    var url="tiLucky/json/findTiLuckysByPage";
    var data = {pageNum: pageNum};
    ajaxPost(url, data, function (pager) {
        var list = pager.list;
        var html = "";
        var header ="";
        header = header + '<thead>';
        header = header + '    <tr>';
        //动态数据Start
        header = header + '<th>#ID</th><th>星座id</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th><th>Test</th>';
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
            

            body = body+'<td>'+obj.id+'</td><td>'+obj.constellationId+'</td><td>'+obj.luckyType1+'</td><td>'+obj.luckyType2+'</td><td>'+obj.luckyType3+'</td><td>'+obj.luckyType4+'</td><td>'+obj.luckyScore1+'</td><td>'+obj.luckyScore2+'</td><td>'+obj.luckyScore3+'</td><td>'+obj.luckyScore4+'</td><td>'+obj.remindToday+'</td><td>'+obj.luckyTypeMore1+'</td><td>'+obj.luckyTypeMore2+'</td><td>'+obj.luckyTypeMore3+'</td><td>'+obj.luckyTypeMore4+'</td><td>'+obj.luckyScoreMore1+'</td><td>'+obj.luckyScoreMore2+'</td><td>'+obj.luckyScoreMore3+'</td><td>'+obj.luckyScoreMore4+'</td><td>'+obj.luckyWords1+'</td><td>'+obj.luckyWords2+'</td><td>'+obj.luckyWords3+'</td><td>'+obj.luckyWords4+'</td><td>'+obj.toDo+'</td><td>'+obj.notDo+'</td><td>'+obj.publishTime+'</td><td>'+obj.luckyDate+'</td>';
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

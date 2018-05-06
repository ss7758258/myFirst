var base = ""
var loading;
function startLoading() {
    loading = layer.load(2, {time: 30*1000});
};
function endLoading() {
    layer.close(loading);
};
function msg_alert(msg) {
    if (msg != null && msg != "")
        layer.alert(msg);
}
function keyboard_commit(keycode, callback) {
    document.onkeydown = function (e) {
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == keycode) {
            callback();
        }
    }
}
function msg_success(msg) {
    if (msg != null && msg != "")
        layer.msg(msg, {icon: 1});
}
function msg_error(msg) {
    if (msg != null && msg != "")
        layer.msg(msg, {icon: 5});
}
function msg_confirm(msg, callback) {
    layer.confirm(msg, function(index){
        callback();
        layer.close(index);
    });
}
function ajaxPost(url, data, callback) {
    this.ajaxPrivate("POST", url, data, callback);
}

function ajaxGet(url, data, callback) {
    this.ajaxPrivate("GET", url, data, callback);
}

function ajaxDelete(url, data, callback) {
    this.ajaxPrivate("DELETE", url, data, callback);
}

function ajaxPut(url, data, callback) {
    this.ajaxPrivate("PUT", url, data, callback);
}
function ajaxPrivate(type, url, data, callback) {
    ajaxPrivate(type, url, data, callback);
}
function ajaxPrivate(type, url, data, callback) {
    $.ajax({
        beforeSend: startLoading,
        complete:endLoading,
        type: type,
        url: url,
        data: data,
        dataType: "json",
        //contentType: "application/json; charset=utf-8",
        xhrFields: {
            withCredentials: true
        },
        success: function (result) {
            if (result.status == "SUCCESS") {
                msg_success(result.message);
                callback(result.data);
            } else {
                if (result.message != null && result.message != "") {
                    msg_error(result.message);
                } else {
                    msg_error(result.status);
                }
            }
        },
        error: function (msg) {
            var text = msg.responseText;
            msg_error(msg.responseText);
        }
    });
}
$(document).ready(function () {
});

function back()
{
    window.history.go(-1);
}
function isNotValidateTel(tel) {
    var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
    if (reg.test(tel)) {
        return false;
    } else {
        return true;
    }
    ;
}
function buildPager(pager)
{
    var pageNum=pager.pageNum;
    var pages=pager.pages;
    var firstPage=pager.firstPage;
    var prePage=pager.prePage;
    var nextPage=pager.nextPage;
    var lastPage=pager.pages;
    var _startPage = pageNum-3;
    var _endPage = pageNum+3;
    if(_startPage<1)
    {
        _endPage = _endPage - _startPage+1;
        _startPage = 1;
    }
    if(_endPage>pages)
    {
        _startPage = _startPage-(_endPage-pages);
        _endPage = pages;
    }
    if(_startPage<1)
        _startPage = 1;
    if(prePage<1)
        prePage = 1;
    if(nextPage>lastPage||nextPage<1)
        nextPage = lastPage;
    var html="";
    html = html +'<ul class="pagination">';
    html = html +'';
    html = html +'<li class="paginate_button previous disabled" id="DataTables_Table_0_previous"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+firstPage+'" tabindex="0" onclick="initTable(this)">首页</a></li>';
    if(firstPage==prePage)
        html = html +'<li class="paginate_button previous disabled" id="DataTables_Table_0_previous"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+prePage+'" tabindex="0">上一页</a></li>';
    else
        html = html +'<li class="paginate_button previous disabled" id="DataTables_Table_0_previous"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+prePage+'" tabindex="0" onclick="initTable(this)">上一页</a></li>';
    for(var i=_startPage;i<_endPage;i++)
    {
        if(i==pageNum)
            html = html +'<li class="paginate_button active"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+i+'" tabindex="0">'+i+'</a></li>';
        else
            html = html +'<li class="paginate_button "><a href="javascript:void(0);" aria-controls="DataTables_Table_0"  data-dt-idx="'+i+'" tabindex="0" onclick="initTable(this)">'+i+'</a></li>';
    }
    if(lastPage==nextPage)
        html = html +'<li class="paginate_button next" id="DataTables_Table_0_next"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+nextPage+'" tabindex="0">下一页</a></li>';
    else
        html = html +'<li class="paginate_button next" id="DataTables_Table_0_next"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+nextPage+'" tabindex="0" onclick="initTable(this)">下一页</a></li>';
    html = html +'<li class="paginate_button previous disabled" id="DataTables_Table_0_previous"><a href="javascript:void(0);" aria-controls="DataTables_Table_0" data-dt-idx="'+lastPage+'" tabindex="0" onclick="initTable(this)">尾页</a></li>';
    html = html +'</ul>';
    $("#pager").html(html);
};

function upload(fieldName,functionName)
{
    var url = functionName+"/json/"+fieldName+"Upload";
    startLoading();
    $.ajaxFileUpload({
        url: url,
        data: {},
        async:'false',
        secureuri: false, //一般设置为false
        fileElementId: fieldName, //文件上传空间的id属性
        dataType: 'json',
        success: function (result, status)  //服务器成功响应处理函数
        {
            endLoading();
            if (result.status == "SUCCESS") {
                $("#urlSpan").html(result.data);
                msg_alert(result.message);
            }else{
                msg_alert(result.message);
            }
        },
        error: function (result, status, e)//服务器响应失败处理函数
        {
            msg_alert(result.message);
        }
    });
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
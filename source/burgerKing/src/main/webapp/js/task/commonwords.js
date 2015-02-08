$(function () {
    $("#gridTableStore")
        .jqGrid(
        {
            url: basePath + "/task/commonwords/list",
            mtype: "POST",
            postData: {
                "type": function () {
                    return $.trim($('#type').val());
                }
            },
            altRows: true,
            forceFit: true,
            altclass: "jqgrid_alt_row",
            datatype: "json",
            multiselect: false,
            rownumbers: true,
            rownumWidth: "80",
            viewsortcols: [true, 'vertical', true],
            colModel: [
                {
                    name: "id",
                    hidden: false
                },
                {
                    label: "常用语",
                    name: "content",
                    sortable: false,
                    width: 300
                } ,
                {
                    label: "操作",
                    sortable: false,
                    width: 150,
                    formatter: function (cellvalue, options,
                                         rowObject) {
                        var id = rowObject.id;
                        var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
                            + id + "\')\"/>";
                        html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
                        + id + "\')\"/>";
                        /*html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
                         + id + "\')\"/>";*/
                        html += "<input type='button' class='upload' title='上传报表' value='报表查看' onclick=\"viewReport(\'"
                        + id + "\')\"/>";
                        /*html += "<input type='button' class='bind' title='绑定' value='绑定' onclick=\"bind(\'"
                         + id + "\')\"/>";*/
                        return html;
                    }
                }],
            rowNum: 10,
            rowList: [10, 20, 50],
            pager: "#gridPagerStore",
            viewrecords: true,
            jsonReader: {
                repeatitems: false
            },
            sortname: "t.id",
            autowidth: true,
            width: "100%",
            height: "100%",
            gridComplete: function () {
                var neww = $(".ui-jqgrid").width();
                var tablew = $(".ui-jqgrid-btable").width();
                if (tablew < neww) {
                    $(".ui-jqgrid-htable").width(neww);
                    $(".ui-jqgrid-btable").width(neww);
                }
            }
        });
});

//上传
function showStoreUpload() {
    doUploadOperate();
}
// 查看
function detail(id) {
    var url = basePath + "/task/commonwords/get?id=" + id;
   // tipsWindown("查看门店", "url:post?" + url, "960", "600", "true", "", "true", "");
    easyUIWindow($("#win"),"查看门店",   url,  960, 600, true, true, true);

}
// 修改
function modify(id) {
    var url = basePath + "/task/commonwords/create?id=" + id;
    //tipsWindown("修改门店", "url:post?" + url, "960", "600", "true", "", "true", "");
    easyUIWindow($("#win"),"修改门店",   url,  960, 600, true, true, true);
}
//删除
function del(id) {
    if (confirm('确定删除么?')) {
        $.ajax({
            async: false,
            type: 'post',
            url: basePath + "/task/commonwords/del",
            data: {
                "id": id
            },
            dataType: "json",
            cache: false,
            error: function (err) {
                alert("系统出错了，请联系管理员！");
            },
            success: function (data) {
                if (data == '0') {
                    alert('删除成功!');
                }
                $("#gridTableStore").jqGrid().trigger("reloadGrid");
            }
        });
    }
}
function addCommonWords() {
    var url = basePath + "/task/commonwords/create";
   // tipsWindown("添加门店", "url:post?" + url, "960", "600", "true", "", "true", "");
    easyUIWindow($("#win"),"添加门店",   url,  960, 600, true, true, true);
}




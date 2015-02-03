<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
    $(document).ready(function () {
        $.post("${basePath}/store/getOCTreeList",{role:'2'},function(data){
            var comboData= treeconvert($.parseJSON(data));
            $('#treeDemo').combotree('loadData',comboData);
        });
        $(".big_green").removeClass("big_ddd");
        //鼠标放到图片上
        $(".storepic_list").delegate("li", "mouseenter", function () {
            var _obj = $(this);
            _obj.find(".del").show();
        }).delegate("li", "mouseleave", function () {
            var _obj = $(this);
            _obj.find(".del").hide();
        });


        //删除图片时
        $(".storepic_list").delegate(".del", "click", function () {
            if (confirm('确定删除该图片么?')) {
                var _obj = $(this);
                _obj.parent().remove();
                $(".storepic_list li:eq(4)").removeClass("more_li");
                var lilength = $(".storepic_list li").length;

                if (lilength <= 5) {
                    $(".more_stropic_btn").hide();
                }
            }
        });
    });
</script>
<style>
    #storeForm .text {
        width: 200px;
    }

    #storeForm .title {
        width: 120px;
    }
</style>


<form id="form1" method="post">
    <table class="addform_wrap" id="storeForm" style="margin:20px;">
        <tr>
            <td class="title"><b>*</b>餐厅面积：</td>
            <td><input type="text" id="shopsize" value="${shop.shopsize}"
                       name="shopsize" class="text" data-required="true" data-required-msg="餐厅面积不能为空"/></td>
            <td class="title" style="padding-left:60px"><b>*</b>餐厅座位数：</td>
            <td><input type="text" id="shopseatnum" data-required="true" data-required-msg="餐厅座位数不能为空"
                       value="${shop.shopseatnum}" name="shopseatnum" class="text"/></td>
        </tr>
        <tr>
            <td class="title">经度：</td>
            <td><input type="text" id="longitude" value="${shop.longitude}"
                       name="longitude" class="text" /></td>
            <td class="title" style="padding-left:60px">纬度：</td>
            <td><input type="text" id="latitude"    value="${shop.latitude}" name="latitude" class="text"/></td>
        </tr>
        <tr>
            <td class="title"><b>*</b>厨房面积：</td>
            <td><input type="text" id="kitchenArea" name="kitchenArea" data-required="true" data-required-msg="厨房面积不能为空"
                       value="${shop.kitchenArea}" class="text"/></td>
            <td class="title" style="padding-left:60px"><b>*</b>营业状态：</td>
            <td>
                <select id="shopStatu">
                    <c:forEach items="${shopStatus}" var="statu">
                        <option value="${statu.value eq null ? '-1' : statu.value}">${statu.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td class="title"><b>*</b>开业时间：</td>
            <td align="left"><input type="text" id="shopopentime" data-required="true" data-required-msg="开业时间不能为空"
                                    name="shopopentime" class="Wdate input_w150"
                                    value=""
                                    onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" maxlength="10"/></td>
            <td class="title" style="padding-left:60px">重装开业：</td>
            <td align="left"><input type="text" id="reOpenTime"
                                    name="reOpenTime" class="Wdate input_w150"
                                    value=""
                                    onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" maxlength="10"/></td>
        </tr>
        <tr>
            <td class="title"><b>*</b>营业时间：</td>
            <td><input type="text" id="shopbusinesstime" data-required="true" data-required-msg="营业时间不能为空"
                       name="shopbusinesstime" value="${shop.shopbusinesstime}" class="text"/>
            </td>
            <td class="title" style="padding-left:60px"><b>*</b>餐厅租期到期日：</td>
            <td><input type="text" id="expireTime" data-required="true" data-required-msg="餐厅租期到期日不能为空"
                       name="expireTime" class="Wdate input_w150"
                       value="${shop.expireTime}"
                       onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" maxlength="10"/>
            </td>
        </tr>
        <tr>
            <td class="title"><b>*</b>租金信息：</td>
            <td align="left"><input type="text" id="currentRent" data-required="true" data-required-msg="租金信息不能为空"
                                    name="currentRent" value="${shop.currentRent}" class="text"/></td>
            <td class="title" style="padding-left:60px"><b>*</b>设备折旧：</td>
            <td><input type="text" id="equipment" data-required="true" data-required-msg="设备折旧不能为空"
                       value="${shop.equipment}" name="equipment" class="text"/></td>
        </tr>
        <tr>
            <td class="title"><b>*</b>装修摊销：</td>
            <td align="left"><input type="text" id="lhi" data-required="true" data-required-msg="装修摊销不能为空"
                                    name="lhi" value="${shop.lhi}" class="text"/></td>
            <td class="title" style="padding-left:60px"><b>*</b>营运督导：</td>
            <td style="">   <input id="treeDemo"  class="easyui-combotree"/>
                <input id="citySel"  type="hidden"   name="authNames"/>
                <input id="authIds" name="authIds" type="hidden"/>
            </td>
        </tr>
        <tr>
            <td class="title">餐厅商圈类型：</td>
            <td><input type="text" id="businessCircle" value="${shop.businessCircle}"
                       name="businessCircle" class="text"/></td>
            <td class="title" style="padding-left:60px"><b>*</b>联系方式：</td>
            <td><input type="text" id="shopphone" data-required="true" data-required-msg="联系方式不能为空"
                       value="${shop.shopphone}" name="shopphone" class="text"/></td>
        </tr>
        <tr>
            <td class="title"><b>*</b>保本营业额：</td>
            <td><input type="text" id="ebitda" value="${shop.ebitda}" data-required="true" data-required-msg="保本营业额不能为空"
                       name="ebitda" class="text"/></td>
            <td class="title" style="padding-left:60px">品牌延伸：</td>
            <td>
                <div class="ckbox_wrap">
                    <c:forEach items="${brandExtesions}" var="brandExt">
                        <input type="checkbox" name="brandExtension" value="${brandExt.value}">${brandExt.name}</input>
                    </c:forEach>
                </div>
            </td>
        </tr>

        <tr>
            <td class="title">餐厅名称：</td>
            <td><input type="text" id="shopname" value="${shop.shopname}"
                       name="shopname" class="text"/></td>
            <td class="title" style="padding-left:120px">门店地址：</td>
            <td><input type="text" id="shopaddress" value="${shop.shopaddress}"
                       name="shopaddress" class="text"/></td>
        </tr>
        <tr>
            <td class="title">投资类型：</td>
            <td>
                <select id="straightJointJoin" name="straightJointJoin">
                    <option value="-1">---请选择---</option>
                    <option value="直营">直营</option>
                    <option value="合资">合资</option>
                    <option value="加盟">加盟</option>
                </select>
            </td>
            <td class="title" style="padding-left:120px">价格组：</td>
            <td><input type="text" id="tiers" value="${shop.tiers}"
                       name="tiers" class="text"/></td>
        </tr>
        <tr>
            <td class="title">所属区域：</td>
            <td>
                <select id="regional" name="regional">
                </select>
            </td>
            <td class="title" style="padding-left:120px">城市：</td>
            <td>
                <select id="prefecture" name="prefecture">
                </select>
            </td>
        </tr>
        <tr>
            <td class="title">门店图片：</td>
            <td colspan="3">
                <div class="clearfix">
                    <input type="file" class="files" onchange="checkFileSuffix('shop_image1')" name="shop_image1"
                           id="shop_image1"/>

                    <div class="xx_act_btn">
                        <a class="xx_add" href="javascript:void(0)" title="增加" onClick="addFile();"></a>
                    </div>
                    <input name="shopimage" id="shopimage" type="hidden" value="${shop.shopimage}"/>
                </div>
                <ul class="add_files" id="addFiles">

                </ul>
                <p class="tips">
                    图片格式为JPG、BMP和PNG,小于1MB,长度小于662px,宽度小于350px
                </p>

            </td>
        </tr>
        <c:if test="${empty shop.id}">
        <tr style="display:none;">
            </c:if>
            <c:if test="${not empty shop.id}">
        <tr>
            </c:if>
            <td class="title">
                <label>已上传图片:</label>
            </td>
            <td colspan="3">
                <c:if
                        test="${shop.shopimage!=''&&shop.shopimage!=null}">
                    <!-- <span -->
                    <%-- 							onclick="showpic('${shop.shopimage}')" --%>
                    <!-- style="cursor: pointer; color: blue">查看图片</span> -->
                    <div class="top5_stopic clearfix">
                        <ul class="storepic_list">

                        </ul>

                    </div>
                    <!-- <div class="more_stopic clearfix" style="display:none"> -->
                    <!-- <ul class="storepic_list"> -->

                    <!-- </ul> -->
                    <!-- </div> -->
                </c:if>
                <c:if
                        test="${shop.shopimage==''||shop.shopimage==null}">
                    <span>无图片</span>
                </c:if>
            </td>
        </tr>
        <tr>
            <td class="title">store information1：</td>
            <td colspan="3">
                <div class="clearfix">
                    <input type="file" class="files" name="store_Info1"
                           id="store_Info1"/>
                    <input name="storeInfo1" id="storeInfo1" type="hidden" value="${shop.storeInfo1}"/>

                </div>
                <ul class="add_files" id="addFiles">
                </ul>
        </tr>

        <tr>
            <td class="title">store information2：</td>
            <td colspan="3">
                <div class="clearfix">
                    <input type="file" class="files" name="store_Info2"
                           id="store_Info2"/>
                    <input name="storeInfo2" id="storeInfo2" type="hidden" value="${shop.storeInfo2}"/>
                </div>
                <ul class="add_files" id="addFiles">
                </ul>
        </tr>
        <tr>
            <td colspan="4" style="padding-left:220px;">
                <div class="qjact_btn clearfix">
                    <a href="javascript:void(0)" onclick="closeOutWindow()"
                       class="big_ea"><span>取消</span></a> <a href="javascript:void(0)"
                                                             class="big_green" id="btn_save"><span>保存</span></a>
                </div>
            </td>
        </tr>
    </table>
    <input id="h_reopentime" type="hidden" value="${shop.reOpenTime}"/>
    <input id="h_expireTime" type="hidden" value="${shop.expireTime}"/>
    <input id="h_shopopentime" type="hidden" value="${shop.shopopentime}"/>
</form>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script type="text/javascript">
    var areaInfo = $.parseJSON('${areaInfo}');
    $(function () {
        var shopimages = '${shop.shopimage}';

        var role = '${role}';


        var iurl = basePath + "/upload/storeImage/";
        var imagesarr = shopimages.split(",");
        $(".storepic_list").remove("li");
        for (var i = 0; i < imagesarr.length; i++) {
            var str = "'" + imagesarr[i] + "'";
            var liclass = "";
            if (i >= 5) {
                liclass = "more_li";
                $(".more_stropic_btn").remove();
                $(".storepic_list").after('<a href="javascript:void(0)" class="more_stropic_btn">更多</a>');
            }
            var li = $('<li class=' + liclass + '><a title="点击查看大图" class="stopic_wrap" onclick="showpic(' + str + ')" href="javascript:void(0)"><img lang="' + imagesarr[i] + '"  src="' + iurl + imagesarr[i] + '" /></a>'
            + '<div class="del" title="删除"><img alt="删除" src="${basePath}/images/del.png"></div></li>');

            $(".storepic_list").append(li);
        }
        //点击更多
        $(".more_stropic_btn").toggle(
                function () {
                    $(".more_li").show();
                    $(".more_stropic_btn").text("收起");
                },
                function () {
                    $(".more_li").hide();
                    $(".more_stropic_btn").text("更多");
                }
        );

        var opentime = $('#h_reopentime').val();
        var expireTime = $('#h_expireTime').val();
        var shopopentime = $('#h_shopopentime').val();
        $('#reOpenTime').val(opentime.split(' ')[0]);
        $('#expireTime').val(expireTime.split(' ')[0]);
        $('#shopopentime').val(shopopentime.split(' ')[0]);

        $(".big_green").removeClass("big_ddd");

        //营业状态
        var shopStatus = '${shop.status}' ? '${shop.status}' : '-1';
        $('#shopStatu').val(shopStatus);

        var brands = '${shop.brandExtension}';
        if (brands) {
            var ary = brands.split(',');
            for (var i = 0; i < ary.length; i++) {
                $('input[name=brandExtension][value=' + ary[i] + ']').attr('checked', "checked");
            }
        }

        $('#straightJointJoin').val('${shop.straightJointJoin}');
        //过滤不符合的地区信息
        areaInfo = convertAreaInfo(areaInfo);
        buildRegionOption(areaInfo, 'regional');

        setSelectVal('regional', '${shop.regional}');
        buildPrefectureOption('${shop.regional}', 'prefecture');

        setSelectVal('prefecture', '${shop.prefecture}');

        //
        $('#regional').bind('change', function (e) {
            var region = $('#regional').val();
            buildPrefectureOption(region, 'prefecture');
        })
    });

    function setSelectVal(selectId, val) {
        if (val) {
            var $sel = $('#' + selectId).val(val);
        }
    }

    function convertAreaInfo(areaInfo) {
        var obj = {};
        for (var i = 0; i < areaInfo.length; i++) {
            var item = areaInfo[i];
            if (item.children.length > 0) {
                obj[item.area] = item.children;
            }
        }

        return obj;
    }

    function buildRegionOption(areaInfo, selectId) {
        var options = [];
        options.push(buildOption('-1', '---请选择---'));
        for (var region in areaInfo) {
            options.push(buildOption(region, region));
        }

        $("#" + selectId).html(options.join(''));
    }

    function buildPrefectureOption(region, selectId) {
        var options = [];
        options.push(buildOption('-1', '---请选择---'));
        if (region) {
            var children = areaInfo[region];

            for (var i = 0; i < children.length; i++) {
                var item = children[i];
                options.push(buildOption(item.area, item.area));
            }
        }
        $("#" + selectId).html(options.join(''));
    }

    function buildOption(val, name) {
        return "<option value='" + val + "'>" + name + "</option>";
    }

    function showpic(imgid) {
        var iurl = basePath + "/upload/storeImage/";
        window.open(basePath + "/common/showmultipic.jsp?filePath=" + iurl + "&filenames=" + imgid, "newwindow", "scrollbars=yes");
        return;
    }
    function checkFileSuffix(fileId) {
        var inputComp = document.getElementById(fileId);
        var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf("."))
                .toLowerCase();
        if (!(fileExt == ".png") && !(fileExt == ".jpg") && !(fileExt == ".bmp")) {
            if (navigator.userAgent.indexOf("MSIE") > 0) {
                document.getElementById(fileId).outerHTML += '';//清空IE的
            } else {
                document.getElementById(fileId).value = "";//可以清空火狐的
            }
            alert("支持文件格式png、jpg、bmp");
            return false;
        }
    }
    function ajaxS() {
        var id = "${id}";
        var options = {
            url: "${basePath}/store/uploadStore?id=" + id,
            dataType: "html",
            type: 'POST',
            success: function (data, status) {
                if (status == "success") {
                    var obj = eval('(' + data + ')');
                    if ("" != obj.msg) {
                        alert("门店报表上传成功");
                        closeOutWindow();
                    }
                }
            }
        };
        var form = $('#form1');
        form.attr("enctype", "multipart/form-data");
        $('#form1').ajaxSubmit(options);
    }

    var reg = /^\d+$|^\d+\.\d+$/;
    var id = '${shop.id}';
    $("#btn_save").bind("click", function () {
        //避免重复提交
        var _obj = $(this), _cname = _obj.prop("class");
        if (_cname.indexOf("big_ddd") >= 0) {
            return;
        }

        var vo = {};
        vo.id = id;
        var flag = true;
        $('#storeForm input[type=text]').each(function (i, item) {
            var $this = $(item);
            var val = $.trim($this.val());
            if (val) {
                vo[$this.attr('name')] = val;
            } else if ($this.attr('data-required') == 'true') {
                alert($this.attr('data-required-msg'));
                flag = false;
                return false;
            }
        });
        if (!flag) {
            return;
        }

        vo.status = $('#shopStatu').val() == -1 ? null : $('#shopStatu').val();
        vo.regional = $('#regional').val() == -1 ? null : $('#regional').val();
        vo.prefecture = $('#prefecture').val() == -1 ? null : $('#prefecture').val();
        vo.straightJointJoin = $('#straightJointJoin').val() == -1 ? null : $('#straightJointJoin').val();
        var brandExtension = [];
        $('input[name=brandExtension]:checked').each(function (i, item) {
            brandExtension.push($(item).val());
        });
        vo.brandExtension = brandExtension.join(',');
        var   value = $("#treeDemo").combotree("getValue");
        if (value.length == 0) {
            alert('运营督导不能为空');
            return;
        }
        //门店图片
        var pics = $(".stopic_wrap img"), picsurl = "";
        for (var i = 0; i < pics.length; i++) {
            picsurl = picsurl + "," + $(pics[i]).prop("lang");
        }
        picsurl = picsurl.replace(",", "");
        _obj.addClass("big_ddd").removeClass("big_green");
        vo.shopimage = $('#shopimage').val();
        vo.storeInfo1 = $('#storeInfo1').val();
        vo.storeInfo2 = $('#storeInfo2').val();
        var options = {
            url: "${basePath}/store/addStore",
            data: {
                "jsons": JSON.stringify(vo),
                "ids": value,
                "shopimages": picsurl
            },
            dataType: "html",
            type: 'POST',
            success: function (data, status) {
                if (id == '') {
                    alert('添加成功!');
                } else {
                    alert('修改成功!');
                }
                closeOutWindow();
                $("#gridTableStore").jqGrid().trigger("reloadGrid");
            }
        };
        var form = $('#form1');
        form.attr("enctype", "multipart/form-data");
        $('#form1').ajaxSubmit(options);

    });
    var i = 2, ul = document.getElementById("addFiles");
    function addFile() {
        var li = document.createElement("li");
        var file = document.createElement("input");
        file.type = "file";
        file.id = "shop_image" + i;
        file.name = "shop_image" + i;
        file.className = "files";
        if (window.addEventListener) { // Mozilla, Netscape, Firefox
            file.addEventListener('change', function () {
                checkFileSuffix(file.id);
            }, false);
        } else { // IE
            file.attachEvent('onchange', function () {
                checkFileSuffix(file.id);
            });
        }
        var del = $('<div class="xx_act_btn"><a class="xx_del" title="删除" href="javascript:void(0)"></a></div>');
        $(li).append(file).append(del);
        $(ul).append(li);
        del.bind("click", function () {
            $(li).remove();
        });
        i++;
    }


</script>

$(function() {
	$("#gridTableQuestion").jqGrid(
			{
				url : basePath + "/questionType/list",
				mtype : "POST",
				postData : {
					"quesType" : function(){return $.trim($('#questionSel').val());},
					"hValue" : function(){return $.trim($('#hValueSer').val());}
				},
				altRows : true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				multiselect : false,
				rownumbers : true,
				rownumWidth:"80",
				forceFit:true,
				viewsortcols : [ true, 'vertical', true ],
				colModel : [
							{
								name : "id",
								hidden : true
							},
							{
								label : "字段名称",
								name : "hValue",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "字段类型",
								name : "hType",
								title : false,
								sortable : false,
								width : 200,
								formatter:'select',
								editoptions:{value:"1:填空题;2:选择题;3:简答题;4:日期;5:下拉;6:评估人;7:置顶;8:上次稽核成绩;9:成绩;10:重复扣分项编号;11:时间;12:地区"}
							},
							{
								label : "抬头/问题类型标识",
								name : "type",
								title : false,
								sortable : false,
								width : 200,
								formatter:'select',
								editoptions:{value:"1:抬头;2:问题"}	
							},
							{
								label : "操作",
								sortable : false,
								width : 100,
								align : "left",
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var	html = "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
											+ id + "\')\"/>";
										html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
											+ id + "\')\"/>";
										return "<div style='text-align:center'>"+html+"</div>";
								}
							}],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerQuestion",
				viewrecords : true,
				jsonReader : {
					repeatitems : false
				},
				autowidth : true,
				width : "100%",
				height : "100%",
				gridComplete : function() {	
					var neww = $(".ui-jqgrid").width();
					var tablew = $(".ui-jqgrid-btable").width();
					if(tablew<neww){
						$(".ui-jqgrid-htable").width(neww);
						$(".ui-jqgrid-btable").width(neww);
					}
				}
			});
});

// 修改
function modify(id) {
	var url =basePath+"/questionType/addOrUpdatePage?id="+id;
    tipsWindown("修改台头/问题信息","url:post?"+url,"660","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/questionType/del",
			data : {
				"id" : id
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				if(data=='0'){
					alert('删除成功!');
				}
				$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function addQuestion(){
	var url =basePath+"/questionType/addOrUpdatePage";
    tipsWindown("添加台头/问题信息","url:post?"+url,"660","400","true","","true","");	
}

/*选择控件*/
(function ($) {
	$.fn.selectui = function (options) {
		var settings = $.extend({}, $.fn.selectui.defaults, options);
		return this.each(function () {
			var _obj = $(this);
			new SelectUI(settings, _obj).init();
		});
	}
	$.fn.selectui.defaults = {
		"optionclickcallback":null,//选择某一项执行的除默认方法外的一些自定义方法
		"selectwidth":200,//宽度，有的文字比较长
		"selectedval":"",
		"selectedtext":"请选择"
	}  

	function SelectUI(opts, obj) {
		this.conf = opts;
		this.selectobj = obj;
		this.data = {};
		this.dom = {};
		this.func = this;
	}
	SelectUI.prototype = {
		init: function () {
			this.define().addEvent();
		},
		define: function () {
			var that = this;
			that.dom.selectobj = that.selectobj;
			that.data.width = that.conf.selectwidth;
			that.dom.actionbtn = null;
			that.dom.acttext = null;
			that.dom.showwrap = null;
			that.data.selectedval = that.conf.selectedval;
			that.data.selectedtext = that.conf.selectedtext;
			that.dom.chooseactbtn = null;
			that.dom.tmpl = '<div id={1} class="select_wrap">'+
								'<div class="action_wrap clearfix"><div class="action_left"><div class="action_right">'+
									'<span class="action_text" value="{3}">{2}</span><i class="action_btn"></i>'+
								'</div></div></div>'+
								'<ul class="select_options">'+
								'{0}'+
								'</ul>'+
							'</div>';
			that.func.optionclickcallback = that.conf.optionclickcallback;
			return that;
		},
		bulidSelectDom: function(){
			var that = this;
			that.dom.selectobj.hide();
			var _suiid = that.dom.selectobj.prop("id")+"_selectUI";
			var _options = that.dom.selectobj.find("option");
			var _lis = "";
			for(var i=0;i<_options.length;i++){
				var _obj = $(_options[i]);
				var _cur = "";
				_lis = _lis + "<li><a href='javascript:void(0)' value='"+_obj.val()+"'>"+_obj.text()+"</a></li>";
			}
			that.dom.tmpl = that.dom.tmpl.replace('{0}',_lis).
			replace('{1}',_suiid).replace('{2}',that.data.selectedtext).replace('{3}',that.data.selectedval);
			var _sdiv = $(that.dom.tmpl);
			that.dom.selectobj.after(_sdiv);			
			_sdiv.css({
				'width':that.data.width
			});
			that.dom.actionbtn = $("#"+_suiid).find(".action_wrap");
			that.dom.acttext = $("#"+_suiid).find(".action_text");
			that.dom.showwrap = $("#"+_suiid).find(".select_options");
			$("#"+_suiid).find(".action_right").width(that.data.width-20);
			that.dom.showwrap.css('width',that.data.width-2);
			that.dom.chooseactbtn = $("#"+_suiid).find("li a");
			that.actionClickEvent();
			that.sOptionClickEvent();
			return that;
		},
		actionClickEvent: function () {
			var that = this,_ismenter = false, _seltimer = null;
			that.dom.actionbtn.bind("click", function (event) {
				_ismenter = true;
				//选中已选择过的值
				var _value = that.dom.acttext.attr("value");
				that.dom.showwrap.find(".current").removeClass("current");
				that.dom.showwrap.find("a[value="+_value+"]").addClass("current");
				var _ul = that.dom.showwrap;
				var _cname = _ul.prop("class");
				if(_cname.indexOf("show")>0){
					_ul.slideUp("fast").removeClass("show");
				}else{
					_ul.slideDown("fast").addClass("show");
				}
			}).bind("mouseleave", function () {
				_ismenter = false;
				if (_seltimer) clearTimeout(_seltimer);
				_seltimer = setTimeout(function () {
					if (!_ismenter) {
						that.dom.showwrap.slideUp("fast").removeClass("show");
					}
				}, 100);
			});
			
			that.dom.showwrap.bind("mouseleave", function () {
				_ismenter = false;
				if (_seltimer) clearTimeout(_seltimer);
				_seltimer = setTimeout(function () {
					if (!_ismenter) {
						that.dom.showwrap.slideUp("fast").removeClass("show");
					}
				},100);
				
			}).bind("mouseenter", function () {
				_ismenter = true;
			});
			return that;
		},
		sOptionClickEvent:function(){
			var that = this;
			that.dom.chooseactbtn.bind("click", function (event) {
				event.preventDefault();
				event.stopPropagation();
				var _obj = $(this),_text = _obj.text();
				that.dom.acttext.text(_text);
				var _value = _obj.attr("value");
				that.dom.acttext.attr("value",_value);
				that.dom.selectobj.val(_value);
				if(that.optionclickcallback!=null){
					that.optionclickcallback(_value,_text);
				}
				that.dom.showwrap.slideUp("fast").removeClass("show");
			});
			return that;
		},
		addEvent: function () {
			var that = this;
			that.bulidSelectDom();
			return that;
		}
	}
})(jQuery);

var ExaminationPaper = (function($){
	var model = {};
	var option = {
		questionType : [],
		examType : null,
		headValues : null,
		questionInfo : null
	};
	$.extend(model,{
		init : function(op){
			option = $.extend(option,op);
			_initGrid(option.questionInfo);
			_initMultiselect(option.headValues);
//			_initTreeValue(option.examType);
			_bindEvent();
		},
		addQues : function(quesType){
			var url = basePath + "/examManager/addQuesPage?quesType=" + quesType;
			tipsWindown("添加试题","url:post?"+url,"740","400","true","","true","");
		},
		clearQues : function(quesType){
			var $tr = $('#questionTypeTable').find('td[title='+quesType+']').parent('tr');
			$tr.find('[name=questionIds]').val('');
			$tr.find('[aria-describedby=questionTypeTable_quesNum]').html(0);
			var $score = $tr.find('[aria-describedby=questionTypeTable_score]');
			var score = $.trim($score.html()) - 0;
			$score.html(0);
			var $totalScore = $('#basicInfo [name=totalValue]');
			$totalScore.val($totalScore.val() - score);
		}
	});
	
	function _initMultiselect(headValues){
		if(headValues && headValues.length > 0){
			$("#headsFields option").each(function(i,item){
				var val = $(item).val();
				for(var i = 0;i < headValues.length;i++){
					if(headValues[i] == val){
						$(item).attr('data-selected',true);
					} 
				}
			});
		}
		
		$("#headsFields").multiselect({
	        noneSelectedText: "==请选择==",
	        checkAllText: "全选",
	        uncheckAllText: '全不选',
	        selectedList:4
	    });  
	}
	
	function _initGrid(questionInfo){
		$("#questionTypeTable")
		.jqGrid(
				{
					altRows : true,
					forceFit:true,
					altclass : "jqgrid_alt_row",
					datatype : "local",
					/*
					 * width : 958, height : 300, autowidth : true,
					 */
					multiselect : false,
					rownumbers : true,
					rownumWidth:"80",
					viewsortcols : [ true, 'vertical', true ],
					colModel : [
								{
									name : "value",
									hidden : true,
									width : 200
								},
								{
									label : "类型",
									name : "name",
									title : false,
									sortable : false,
									width : 200
								},
								{
									label : "考题列表",
									name : "questionIds",
									title : false,
									sortable : false,
									width : 200,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var html = "<input type='text' class='text' name='questionIds' readonly='readonly'>";
										return html;
									}
								},
								{
									label : "数量",
									name : "quesNum",
									title : false,
									sortable : false,
									width : 200
								},
								{
									label : "分值",
									name : "score",
									title : false,
									sortable : false,
									width : 200
								},
								{
									label : "操作",
									sortable : false,
									width :200,
									formatter : function(cellvalue, options,
											rowObject) {
										var quesType = rowObject.value;
										var 
										html = "<input type='button' class='add' title='增加' value='增加' onclick=\"ExaminationPaper.addQues(\'"
												+ quesType + "\')\"/>";
										html += "<input type='button' class='clear' title='清空' value='清空' onclick=\"ExaminationPaper.clearQues(\'"
												+ quesType + "\')\"/>";
										return html;
									}
								} ],
					viewrecords : true,
					jsonReader : {
						repeatitems : false
					},
					autowidth : true,
					width : "100%",
					height : "100%",
					gridComplete : function() {
						var neww = $("#gbox_questionTypeTable").width();
						var tablew = $("#gview_questionTypeTable .ui-jqgrid-btable").width();
						if(tablew<neww){
							$("#gview_questionTypeTable .ui-jqgrid-htable").width(neww);
							$("#gview_questionTypeTable .ui-jqgrid-btable").width(neww);
						}
					}
				});
		_addGridData();
		//
		_initQuestionData(questionInfo);
	}
	
	function _initQuestionData(questionInfo){
		if(!questionInfo){
			return;
		}
		
		var map = _doMapByQuesType(questionInfo);
		for(var quesType in map){
			var selectQues = [];
			var totalScore = 0;
			var questions = map[quesType];
			for(var i = 0;i < questions.length;i++){
				var rowData = questions[i];
				selectQues.push(rowData.id);
				totalScore += (rowData.qvalue - 0);
			}
			
			var $tr = $('#questionTypeTable').find('td[title='+quesType+']').parent('tr');
			$tr.find('[name=questionIds]').val(selectQues.join(','));
			$tr.find('[aria-describedby=questionTypeTable_quesNum]').html(questions.length);
			$tr.find('[aria-describedby=questionTypeTable_score]').html(totalScore);
		}
	}
	
	//以quesType作为键，值为试题列表进行转换
	function _doMapByQuesType(questionInfo){
		var map = {};
		for(var i = 0;i < questionInfo.length;i++){
			var qInfo = questionInfo[i];
			map[qInfo.qtype] = map[qInfo.qtype] || [];
			map[qInfo.qtype].push(qInfo);
		}
		return map;
	}
	
	function _bindEvent(){
		$('#addExam,#updateExam').bind('click',function(e){
			saveOrUpdateHandler(e);
		});
		
		$('#saveAsAnotherExam').bind('click',function(e){
			$('#examId').val('');
			saveOrUpdateHandler(e)
			
		});
		
		function saveOrUpdateHandler(event){
			var _obj = $(event.currentTarget),_cname = _obj.prop("class");
			if(_cname.indexOf("big_ddd")>=0){
				return;
			}
			if(!_validateRequiredFilled()){
				return;
			}
			_obj.addClass("big_ddd").removeClass("big_green");
			var examInfo = _buildExamInfo();
			$.ajax({
				async : false,
				type : 'post',
				url : basePath + "/examManager/addExamInfo",
				data : {
					"examInfo" : $.toJSON(examInfo)
				},
				dataType : "json",
				cache : false,
				error : function(err) {
					alert("系统出错了，请联系管理员！");
				},
				success : function(data) {
					alert('操作成功');
					window.location = basePath + "/examManager/index";
				}
			});
		}
	}
	
	/**
	 * 校验必填项
	 */
	function _validateRequiredFilled(){
		if ($.trim($('#examName').val()) == '') {
			alert('试卷名称不能为空!');
			return false;
		}
		if ($.trim($('#examType').val()) == '') {
			alert('请选择试卷类型!');
			return false;
		}
		if ($.trim($('#examTimer').val()) == '') {
			alert('答题时间不能为空!');
			return false;
		}
		if ($.trim($('#totalValue').val()) == '') {
			alert('试卷总分不能为空!');
			return false;
		}
		
		return true;
	}
	function _buildExamInfo(){
		var vo = {};
		$('#basicInfo input[name]').each(function(index,item){
			var $this = $(item);
			vo[$this.attr('name')] = $this.val();
		});
		
		vo.heads = _getHeads();
		vo.description = $('#description').text();
	
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);
		vo.examType = nodes.length > 0 ? nodes[0].id : $('#examTypeId').val();
		
		var questions = vo.questionList = [];
		$('#questionTypeTable input[name=questionIds]').each(function(index,item){
			var $this = $(item);
			var quesIds = $.trim($this.val());
			if(quesIds != ''){
				var ary = quesIds.split(',');
				$.each(ary,function(i,item){
					questions.push({"id" : item});
				});
			}
		});
		
		vo.id = $('#examId').val() || null;
		
		return vo;
	}
	
	function _getHeads(){
		var heads = [];
		$("#headsFields").multiselect("getChecked").each(function(i,item){
			heads.push($(item).val());
		});
		return heads.join(',');
	}
	
	function _addGridData(){
		var quesType = option.questionType;
		for(var i=0;i<quesType.length;i++){
			$("#questionTypeTable").jqGrid('addRowData',i+1,
										$.extend({questionIds:'',quesNum : 0,score : 0},quesType[i]));
		}
	}
	return model;
})(window.jQuery);

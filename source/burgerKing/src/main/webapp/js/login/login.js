$(function() {
	 $(".icon_ltxt input").val("");
	 $(".icon_ltxt").delegate(".defalut_val", "click", function(){
		 var _obj = $(this);
		_obj.parent().find("input").focus();	
	 }); 
 
	 $(".icon_ltxt input").focus(function(){
		$(this).addClass("focus_on");
		 var _obj = $(this),_val = $.trim(_obj.val());
		 if(_obj.prop("id")=="name"&&_val==""){
			 _obj.parent().find(".defalut_val").remove();
		 }
		if(_obj.prop("id")=="pwd"&&_val==""){
			_obj.parent().find(".defalut_val").remove();
		
		}
	 }).blur(function(){
		 $(this).removeClass("focus_on");
		 var _obj = $(this),_val = $.trim(_obj.val());
		 if(_val==""){
			 if(_obj.prop("id")=="name"){
				 _obj.parent().append("<span class='defalut_val'>用户名</span>");
			 }
			if(_obj.prop("id")=="pwd"){
				_obj.parent().append("<span class='defalut_val'>密码</span>");
			} 
		 }
	 });
	 
	//$(".icon_ltxt input").keyup(function(event){
//		var _obj = $(this),_val = $.trim(_obj.val());
//		if(_val.length>0){
//			_obj.parent().find(".defalut_val").remove();	
//		}
//		if(_val.length==0){
//			if(_obj.prop("id")=="name"){
//				_obj.parent().append("<span class='defalut_val'>用户名</span>");
//			}else{
//				_obj.parent().append("<span class='defalut_val'>密码</span>");
//			}
//		}
//		
		
	//});
//	$(".icon_ltxt input").bind({
//		paste : function(){ 
//			var _obj = $(this);
//			setTimeout(function() {
//				var _val = _obj.val();
//				if(_val.length>0){
//					_obj.parent().find(".defalut_val").remove();	
//				}
//				if(_val.length==0){
//					if(_obj.prop("id")=="name"){
//						_obj.parent().append("<span class='defalut_val'>用户名</span>");
//					}else{
//						_obj.parent().append("<span class='defalut_val'>密码</span>");
//					}
//				} 
//			}, 100);
//        },
//        cut : function(){  
//        	var _obj = $(this);
//        	setTimeout(function() {
//				var _val = _obj.val();
//				if(_val.length==0){
//					if(_obj.prop("id")=="name"){
//						_obj.parent().append("<span class='defalut_val'>用户名</span>");
//					}else{
//						_obj.parent().append("<span class='defalut_val'>密码</span>");
//					}
//				} 
//			}, 100);
//        } 
//	});
	
	$('#btnReset').click(function() {
		$('#name').val("");
		$('#pwd').val("");
	}); 
	 
	$('#btnLogin').click(function() {
		var name = $.trim($('#name').val());
		var pwd = $.trim($('#pwd').val());
		loginAjaxSubmit(name,pwd);

	});
	
	function loginAjaxSubmit(name,pwd){
		var name = $.trim($('#name').val());
		var pwd = $.trim($('#pwd').val());
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if (name == '') {
			alert("用户名不能为空！");
			$("#name").focus();
			return false;
		}
		else{
			if("admin" != name){
				if(!reg.test(name)){
					alert("邮箱格式不正确！");
					$('#name').focus();
					return false;
				}
			}
		}
		if (pwd == '') {
			alert("用户密码不能为空！");
			$("#pwd").focus();
			return false;
		}
		
		$.ajax({
			async : false,
			type : 'post',
			url : basePath+"/login",
			data : {
				"name" : name,
				"pwd" : pwd
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				if (data == '2') { // 登录成功
					window.location.href =basePath+ "/index";
				} else if(data=='1') {
					alert("用户名或者密码错误！");
					return false;
				}else if(data=='0') {
					alert("用户不存在！");
					return false;
				}
				       
			
			}
		});
	}
	
	//是否按回车输入了文字，onchange事件可以判断是否输入文字
	var _nchange = false,_nisenter = false,_pchange = false,_pisenter = false;
	$(document).keyup(function(event){
		var _obj = $(".focus_on"),_val = $.trim(_obj.val());
		var name = $.trim($('#name').val());
		var pwd = $.trim($('#pwd').val());
		if($(".focus_on").length>0&&event.keyCode ==13){
			
			if(_obj.prop("id")=="name"){
				if (_val == '') {
					alert("用户名不能为空！");
					$("#name").focus();
					return false;
				}else{
					_nisenter = true;
					if(_nchange&&pwd == ''){
						alert("用户密码不能为空！");
						$("#pwd").focus();
						return false;
					}
				}
				
			}else{
				
				if (_val == '') {
					alert("用户密码不能为空！");
					$("#pwd").focus();
					return false;
				}else{
					_pisenter = true;
					if(_pchange&&name == ''){
						alert("用户名不能为空！");
						//$("#name").focus();
						return false;
					}
				}
			}	
			
			if(name!=""&&pwd!=""){	
				loginAjaxSubmit(name,pwd);
			}
	    }
	});
	
	$("#name").change(function(){
		if(_nisenter){
			_nchange = true;
		}
	
	});
	$("#pwd").change(function(){
		if(_pisenter){
			_pchange = true;	
		}
		
	});
});

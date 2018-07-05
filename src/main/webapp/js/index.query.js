//服务于登录的js
$(function() {
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	$("#loading_Shadow").hide();
	
	//点击登录
	$("#loginButton").on('click', function() {
		login_AJAX();
	});
	$("#login_name,#login_psd").keydown(function(e) {
		if(e.keyCode==13){
			login_AJAX();
		}
	});
});

	//请求webLogin
function login_AJAX() {
	$("#err_msg").html("");
	if($("#login_name").val() == ""){
		$("#err_msg").html("登录名为空");
		return;
	}else if($("#login_psd").val() == ""){
		$("#err_msg").html("登录密码为空");
		return;
	}
	
	$("#loading_Shadow").show();
	$("#loginButton").attr("disabled", true);
	var param = {};
	param["loginName"] = $("#login_name").val();
	param["loginPwd"] = $("#login_psd").val();
	param["random"] = newGuid();
	var url = "web/webLogin.do";
	$.post(url, param, function(data, textStatus) {
		$("#loginButton").attr("disabled", false);
		queryCallBack(data, textStatus);
	});
}
	
	//登录成功后跳转到mobile.html
function queryCallBack(data, textStatus) {
	if (textStatus != "success") {
		alert("请求错误！");
		$("#loading_Shadow").hide();
		return;
	}
	if (data === "ERROR") {
		alert("后台发生错误！");
		$("#loading_Shadow").hide();
		return;
	}
	myobj = data;
	if (myobj == undefined) {
		alert("未获取到数据！");
		$("#loading_Shadow").hide();
		return;
	}

	if (myobj["success"]) {
		window.location.href = "mobile.html?date=" + newGuid();
	} else {
		$("#err_msg").html(myobj["info"].replace('[','').replace(']',''));
		$("#loading_Shadow").hide();
	}
}
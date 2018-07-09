$(function() {
	typeArr = {};
	typePareArr = {};
	allType={};
	getAllBank();
	showAllType();
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	$("#loading_Shadow").hide();

	$("#mobileSaveBtn").on('click', function() {
		mobile_Save_AJAX();
	});

});

function mobile_Save_AJAX() {
	$("#err_msg").html("");
	if ($("#loginName").val() == "") {
		$("#err_msg").html("登录名为空");
		return;
	}
	if ($("#mobile").val() == "") {
		$("#err_msg").html("手机号码为空");
		return;
	}
	if ($("#mobile").val().length > 11) {
		$("#err_msg").html("手机号码长度大于11位");
		return;
	}
	$("#loading_Shadow").show();
	$("#mobileSaveBtn").attr("disabled", true);
	var param = {};
	
	param["loginName"] = $("#loginName").val();
	param["mobile"] = $("#mobile").val();
	param["name"] = $("#name").val();
	param["gender"] = $("#gender").val();
	param["idNumber"] = $("#id_number").val();
	param["department"] = $("#department").val();
	param["duties"] = $("#duties").val();
	param["detailDes"] = $("#detail_des").val();
	param["parenttype"] = $("#parenttype").val();
	param["typeGuid"] = $("#typeGuid").val();
	
	
	param["random"] = newGuid();
	var url = "web/saveMobile.do";
	$.post(url, param, function(data, textStatus) {
		$("#mobileQueryBtn").attr("disabled", false);
		queryMobileCallBack(data, textStatus);
	});

}

function queryMobileCallBack(data, textStatus) {
	$("#loading_Shadow").hide();
	$("#mobileSaveBtn").attr("disabled", false);
	if (textStatus != "success") {
		alert("请求错误！");
		return;
	}


	myobj = data;
	if (myobj == undefined) {
		alert("未获取到数据！");
		return;
	}
	if (myobj["sessionState"] == 0) {
		window.location.href = "login.html?data=" + newGuid();
		return;
	}

	if (myobj["success"]) {
		window.location.href = "mobile.html?data=" + newGuid();
	} else {
		$("#err_msg").html(myobj["info"]);
	}
}

function showAllType() {
	for ( var key in typePareArr) {
		if (key=='000') {
			$("<option/>", {
				val : key,
				text : typePareArr[key],
				selected : "selected"
			}).appendTo($("#parenttype"))
			$("#bankBranch").hide();
		} else {
			$("<option/>", {
				val : key,
				text : typePareArr[key]
			}).appendTo($("#parenttype"))
		}
	}
}
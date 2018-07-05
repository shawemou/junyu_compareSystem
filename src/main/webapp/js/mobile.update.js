$(function() {
	allType={};
	typeArr = {};
	typePareArr = {};
	getAllBank();
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	$("#loading_Shadow").hide();

	$("#mobileSaveBtn").on('click', function() {
		mobile_Save_AJAX();
	});
	$("#clearBtn").on('click', function() {
		loadMobile();
	});
	$("#guid").val(getUrlParam('id'));
	loadMobile();
});

// 查询单个user
function loadMobile() {
	$("#mobileSaveBtn").attr("disabled", true);
	$("#loading_Shadow").show();
	var param = {};
	param["guid"] = $("#guid").val();
	param["random"] = newGuid();
	var url = "web/loadMobile.do";
	$.post(url, param, function(data, textStatus) {
		$("#mobileSaveBtn").attr("disabled", false);
		loadMobileCallBack(data, textStatus);
	});
}

function loadMobileCallBack(data, textStatus) {
	$("#loading_Shadow").hide();
	$("#mobileSaveBtn").attr("disabled", false);
	if (textStatus != "success") {
		alert("请求错误！");
		return;
	}
	if (!data) {
		alert("后台发生错误！");
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
	if (myobj) {
		for ( var key in typePareArr) {
			if (myobj.typeGuid == key || myobj.typeGuid.substring(0,2) == key) {
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
		
		for ( var key in allType) {
			if (myobj.typeGuid.length==3) {
				break;
			}
			if (myobj.typeGuid == key) {
				$("<option/>", {
					val : key,
					text : allType[key],
					selected : "selected"
				}).appendTo($("#typeGuid"))
				$("#bankBranch").show();
			} else if(myobj.typeGuid.substring(0,2) == key.substring(0,2) && key.length!=3) {
				$("<option/>", {
					val : key,
					text : allType[key],
				}).appendTo($("#typeGuid"))
			}
		}
		

		$("#loginName").val(myobj["loginName"])
		$("#mobile").val(myobj["mobile"]);
		$("#name").val(myobj["name"]);
		$("#gender").val(myobj["gender"]);
		$("#id_number").val(myobj["idNumber"]);
		$("#department").val(myobj["department"]);
		$("#duties").val(myobj["duties"]);
		$("#detail_des").val(myobj["detailDes"]);
	}
}

// 获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}

// 保存修改
function mobile_Save_AJAX() {
	$("#err_msg").html("");
	if ($("loginName").val() == "") {
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
	param["guid"] = $("#guid").val();
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
	var url = "web/updateUser.do";
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
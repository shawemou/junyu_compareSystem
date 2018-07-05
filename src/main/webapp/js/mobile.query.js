$(function() {
	window.typeArr = {};
	typePareArr = {};
	allType={};
	getAllBank();
	showAllType()

	var cok = $.cookie('pageSize_mobile');
	if (cok != null) {
		pageSize = cok;
		$("#pageCount").val(cok);
	}

	// 修改页码
	$("#pageCount").on('change', function() {
		pageSize = $("#pageCount").val();
		$.cookie('pageSize_mobile', pageSize, {
			expires : 30
		});
		query_AJAX();
	});

	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	$("#loading_Shadow").hide();

	// 查询按钮绑定事件
	$("#mobileQueryBtn").on('click', function() {
		$("#page_no").val("1");
		query_AJAX();
	});

	// 在手机号码 , 主机名字 , 银行 文本框内点击回车触发事件
	$("#mobile,#name,#bank_name").keydown(function(e) {
		if (e.keyCode == 13) {
			$("#page_no").val("1");
			query_AJAX();
		}
	});

	// 新增按钮触发事件
	$("#mobileAddBtn").on('click', function() {
		window.location.href = "mobileAdd.html?data=" + newGuid();
	});

	// 初始化查询
	query_AJAX();

});

// 初始化查询
function query_AJAX() {
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);

	$("#err_msg").html("");
	$("#loading_Shadow").show();
	$("#mobileQueryBtn").attr("disabled", true);
	var param = {};
	param["mobile"] = $("#mobile").val();
	param["name"] = $("#name").val();
	param["parenttype"] = $("#parenttype").val();
	param["busable"] = $("#busable").val();
	param["page_no"] = $("#page_no").val();
	param["page_count"] = $("#pageCount").val();
	param["random"] = newGuid();
	var url = "web/webDateList.do";

	// 调用webMobile
	$.post(url, param, function(data, textStatus) {
		$("#mobileQueryBtn").attr("disabled", false);
		queryMobileCallBack(data, textStatus);
	});

}

// 调用webMobile后数据显示
function queryMobileCallBack(data, textStatus) {
	$("#loading_Shadow").hide();
	if (textStatus != "success") {
		alert("请求错误！");
		return;
	}

	/*
	 * try { var myobj = eval('(' + data + ')'); } catch (Error) {
	 * alert("数据转换失败,格式不正确！"); return; }
	 */
	myobj = data;

	if (myobj == undefined) {
		alert("未获取到数据！");
		return;
	}
	if (data["success"] == false) {
		alert("后台发生错误！");
		return;
	}
	if (myobj["sessionState"] == 0) {
		window.location.href = "login.html?data=" + newGuid();
		return;
	}
	$("#page_list").empty();
	$("#page_no_html").empty();
	// 当前页
	var pageNo = myobj["page_no"];
	$("#page_no").val(pageNo);
	var itemCount = myobj["itemCount"];
	if (itemCount) {
		$("#page_list").html(printPage(itemCount, pageNo));
		$("#page_no_html").html(printPageNum(itemCount, pageNo));
		onChilkPage();
	} else {
		$("#page_no_html").html("共计0条")
	}
	$("#maobile_query tbody").empty();
	for ( var i in myobj["data"]) {
		var item = myobj["data"][i];
		var tdhtml = '<tr id="' + item["guid"] + '" busable_data=' + item["busable"] + '>' 
					+ '<td>' + item["loginName"] + '</td>'
					+ '<td>'+ (item["mobile"] == null ? '' : item["mobile"]) + '</td>' 
					+ '<td>' + item["password"] + '</td>' 
					+ '<td title=' + typeName(item["typeGuid"])+ '>' + typeName(item["typeGuid"]) 
					+ '</td>' + '<td>' + item["name"] + '</td>' 
					+ '<td>' + gender(item["gender"]) + '</td>' 
					+ '<td>'+ (item["idNumber"] == null ? "" : item["idNumber"]) + '</td>' 
					+ '<td class=' + (item["busable"] == '2' ? 'red' : '') + '>'
				+ busable(item["busable"]) + '</td>' + '<td>重置密码</td>' + '</tr>';
		$("#maobile_query tbody").append(tdhtml);
	}
	$("#maobile_query tbody tr:even").addClass("alt");
	$("#maobile_query tbody tr").find("td").click(function() {
		// 跳转到更新页面
		var i = $(this).index();
		if (i <= 1 || (i >= 3 && i <= 6)) {
			$("#loading_Shadow").show();
			var id = $(this).parent().attr("id");
			window.location.href = "mobileUpdate.html?id=" + id + "&data=" + newGuid();
		}
	});

	// 更新是否可用状态
	$("#maobile_query tbody tr").find("td:eq(7)").click(function() {
		updateBusable(this);
	}).mouseover(function() {
		var html = $(this).parent().attr("busable_data");
		if (html == "1") {
			$(this).html("点击停用");
			$(this).css("color", "red");
		} else {
			$(this).html("点击启用");
			$(this).css("color", "green");
		}
	}).mouseout(function() {
		var html = $(this).parent().attr("busable_data");
		if (html == "1") {
			$(this).removeClass('red');
			$(this).html("可用");
		} else {
			$(this).addClass('red');
			$(this).html("停用");
		}
		$(this).css("color", "");
	});

	// 重置密码点击触发事件
	$("#maobile_query tbody tr").find("td:eq(8)").click(function() {
		updatePwd(this);
	}).mouseover(function() {
		$(this).css("color", "red");
	}).mouseout(function() {
		$(this).css("color", "");
	});

}

// 更新页面
function onChilkPage() {
	$("#fristPage").on('click', function() {
		$("#page_no").val("1");
		query_AJAX();
	});
	$("#endPage").on('click', function() {
		$("#page_no").val(endPage);
		query_AJAX();
	});
	$("#lastPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no - 1) <= 0 ? 1 : (page_no - 1);
		$("#page_no").val(page_no);
		query_AJAX();
	});
	$("#nextPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no + 1) > endPage ? endPage : (page_no + 1);
		$("#page_no").val(page_no);
		query_AJAX();
	});
	$("#page_next").keydown(function(e) {
		if (e.keyCode == 13) {
			var page_no = $("#page_next").val();
			if (!isNaN(page_no)) {
				page_no = page_no > endPage ? endPage : page_no;
				page_no = page_no <= 0 ? 1 : page_no;
				$("#page_no").val(page_no);
				query_AJAX();
			} else {
				alert("页码请输入数字");
			}
		}
	});

}

// 更新是否可用
function updateBusable(obj) {
	$("#loading_Shadow").show();
	var id = $(obj).parent().attr("id");
	var param = {};
	param["guid"] = id;
	param["random"] = newGuid();
	var url = "web/updateBusable.do";
	$.post(url, param, function(data, textStatus) {
		if (textStatus != "success") {
			alert("请求错误！");
			return;
		}
		if (!data.success) {
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

		if (myobj["success"]) {
			$(obj).parent().attr("busable_data", myobj["info"]);
			if (myobj["info"] == "1") {
				$(obj).removeClass('red');
				$(obj).html("可用");
			} else {
				$(obj).addClass('red');
				$(obj).html("停用");
			}
		} else {
			alert(myobj["info"]);
		}
		$("#loading_Shadow").hide();
	});
}

// 更新密码
function updatePwd(obj) {
	$("#loading_Shadow").show();
	var id = $(obj).parent().attr("id");
	var param = {};
	param["guid"] = id;
	param["random"] = newGuid();
	var url = "web/updatePwd.do";
	$.post(url, param, function(data, textStatus) {
		if (textStatus != "success") {
			alert("请求错误！");
			return;
		}
		if (!data.success) {
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

		if (myobj["success"]) {
			$(obj).parent().find("td:eq(2)").html("111111");
		} else {
			alert(myobj["info"]);
		}
		$("#loading_Shadow").hide();
	});
}

function gender(id) {
	if (id == "1") {
		return "男";
	} else if (id == "2") {
		return "女";
	} else {
		return "未知";
	}
}

function busable(id) {
	if (id == "1") {
		return "可用";
	} else {
		return "停用";
	}
}

function typeName(id) {
	if (id) {
		return allType[id];
	}
	return "";
}

function showAllType() {
	$("<option/>", {
		val : "",
		text : "--- 请选择用户类型 ---",
		selected : "selected"
	}).appendTo($("#parenttype"))
	for ( var key in typePareArr) {
		$("<option/>", {
			val : key,
			text : typePareArr[key]
		}).appendTo($("#parenttype"))
	}
}

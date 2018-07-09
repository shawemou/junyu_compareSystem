$(function() {
	userArr={};
	typeArr = {};
	allType={};
	typePareArr = {};
	getAllBank();
	var cok = $.cookie('pageSize_mobile');
	if (cok != null) {
		pageSize = cok;
		$("#pageCount").val(cok);
	}
	$("#pageCount").on('change', function() {
		pageSize = $("#pageCount").val();
		$.cookie('pageSize_mobile', pageSize, {
			expires : 30
		});
		query_stat_AJAX();
	});
	query_stat_AJAX();
	
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	$("#loading_Shadow").hide();

	$("#statQueryBtn").on('click', function() {
		$("#page_no").val("1");
		query_stat_AJAX();
	});

});

function query_stat_AJAX() {
	var shadowCSS = [];
	shadowCSS["width"] = $("#show_div").width();
	shadowCSS["height"] = $("#show_div").height();
	$("#loading_Shadow").css(shadowCSS);
	var param = {};
	param["page_no"] = $("#page_no").val();
	param["page_count"] = $("#pageCount").val();
	param["offlineTime"] = $("#offlineTime").val();
	$("#err_msg").html("");
	$("#loading_Shadow").show();
	$("#statQueryBtn").attr("disabled", true);
	var url = "web/offlineList.do";
	$.post(url, param, function(data, textStatus) {
		$("#statQueryBtn").attr("disabled", false);
		queryStatCallBack(data, textStatus);
	});

}

function queryStatCallBack(data, textStatus) {
	$("#loading_Shadow").hide();
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
	$("#page_list").empty();
	$("#page_no_html").empty();
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
		var tdhtml = '<tr>' 
			+ '<td>' + item["name"] + '</td>'
			+ '<td>' + (item["idNumber"]==null?"": item["idNumber"])+ '</td>' 
			+ '<td>' + typeName(item["typeGuid"])+ '</td>'
			+ '</tr>';
			$("#maobile_query tbody").append(tdhtml);
	}
	
	$("#maobile_query tbody tr:even").addClass("alt");
}

function onChilkPage() {
	$("#fristPage").on('click', function() {
		$("#page_no").val("1");
		query_stat_AJAX();
	});
	$("#endPage").on('click', function() {
		$("#page_no").val(endPage);
		query_stat_AJAX();
	});
	$("#lastPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no - 1) <= 0 ? 1 : (page_no - 1);
		$("#page_no").val(page_no);
		query_stat_AJAX();
	});
	$("#nextPage").on('click', function() {
		var page_no = parseInt($("#page_no").val());
		page_no = (page_no + 1) > endPage ? endPage : (page_no + 1);
		$("#page_no").val(page_no);
		query_stat_AJAX();
	});
	$("#page_next").keydown(function(e) {
		if (e.keyCode == 13) {
			var page_no = $("#page_next").val();
			if (!isNaN(page_no)) {
				page_no = page_no > endPage ? endPage : page_no;
				page_no = page_no <= 0 ? 1 : page_no;
				$("#page_no").val(page_no);
				query_stat_AJAX();
			} else {
				alert("页码请输入数字");
			}
		}
	});

}

function typeName(id) {
	if (id) {
		return allType[id];
	}
	return "";
}





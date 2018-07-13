//获取参数
var getParam = function() {
	try {
		var obj = JSON.parse(window.top.row);
		
		return obj;
	} catch (e) {
		return null;
	}
};
// 获取所有的银行列表
function getAllBank() {
	$.ajax({
		url : "/CompareSystem/web/getBanKList.do",
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.success) {
				for ( var i in result.dbInfo.typeList) {
					var type = result.dbInfo.typeList[i];
					// 1,所有的用户类型
					allType[type.guid] = type.name;
					if (type.guid.length != 2 || type.guid != "000") {
						// 2,所有的子类型 和 担保公司
						typeArr[type.guid] = type.name;
					}
					if (type.guid.length == 2 || type.guid.length == 3) {
						// 3,所有的总行 和 担保公司 和 市中心
						typePareArr[type.guid] = type.name;
					}
				}
			}
		}
	});
}

// 生成GUID类似的随机数
function newGuid() {
	var guid = "";
	for (var i = 1; i <= 32; i++) {
		var n = Math.floor(Math.random() * 16.0).toString(16);
		guid += n;
		if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
			guid += "-";
	}
	return guid;
}

// 获取所用用户
function getAllUser() {
	$.ajax({
		url : "web/getUserList.do",
		dataType : "json",
		async : false,
		success : function(result) {
			if (result.success) {
				for ( var i in result.dbInfo.userList) {
					var user = result.dbInfo.userList[i];
					userArr[user.guid] = user;
				}
			}
		}
	});

}

$(function() {
	$("#parenttype").change(function(id) {
		$("#typeGuid").html("");
		if($("#haveRole").size()==1){
			if(this.value=="000"){
				$("#haveRole").show();
			}else{
				$("#haveRole").hide();
			}
		}
		if (this.value.length != 0) {
			for ( var key in allType) {
				if (key.substring(0, 2) == this.value.substring(0, 2)) {
					$("<option/>", {
						val : key,
						text : allType[key],
					}).appendTo($("#typeGuid"))
				}
			}
			$("#bankBranch").show();
		} else {
			$("#bankBranch").hide();
		}

	});

})

function addOrUpdateUser(data) {
	data[key] = value, $.ajax({
		url : "web/validataUser.do",
		data : data,
		dataType : "json",
		success : function(result, textStatus) {
			queryMobileCallBack(result, textStatus);
		}
	})
}

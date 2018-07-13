//获取所有的银行列表
function getAllBank() {
	$.ajax({
		url : "web/getBanKList.do",
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
	$("[name=stat1]").get(0).href = "mobile.html?" + new Date().getTime();
	$("[name=stat2]").get(0).href = "stat.html?" + new Date().getTime();
	$("[name=stat3]").get(0).href = "rate.html?" + new Date().getTime();
	$("[name=stat4]").get(0).href = "offline.html?" + new Date().getTime();

	$("#parenttype").change(function(id) {
		$("#typeGuid").html("");
		
		if (this.value.length != 0) {
			
			for ( var key in allType) {
				if (key.substring(0, 2) == this.value.substring(0,2)) {
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

		/*if (this.value.length != 3 && this.value.length != 0) {
			$("<option/>", {
				val : this.value,
				text : typePareArr[this.value],
				selected : "selected"
			}).appendTo($("#typeGuid"))

			for ( var key in typeArr) {
				if (key.substring(0, 2) == this.value && key.length != 3) {
					$("<option/>", {
						val : key,
						text : typeArr[key],
					}).appendTo($("#typeGuid"))
				}
			}
			$("#bankBranch").show();

		} else {
			$("#bankBranch").hide();
		}*/
	});

	/*
	 * // 登录名或者用户名change发送ajax请求 $("#loginName,#mobile").change(function() {
	 * validataUser(this.name, this.value);
	 *  })
	 */
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

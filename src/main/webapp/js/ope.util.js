Request = {
	QueryString : function(item) {
		var svalue = location.search.match(new RegExp("[\?\&]" + item
				+ "=([^\&]*)(\&?)", "i"));
		if (svalue === undefined)
			return undefined;
		return svalue ? svalue[1] : svalue;
	}
};//RegExp方法获取地址栏参数,不足就是每次只能选一个参数，.../test.html?str=123456 alert(QueryString('str'));

function getArgs() {
	var args = new Object();
	var query = location.search.substring(1); // Get query string
	var pairs = query.split("&"); // Break at ampersand
	for ( var i = 0; i < pairs.length; i++) {
		var pos = pairs[i].indexOf('='); // Look for "name=value"
		if (pos == -1)
			continue; // If not found, skip
		var argname = pairs[i].substring(0, pos); // Extract the name
		var value = pairs[i].substring(pos + 1); // Extract the value
		value = decodeURIComponent(value); // Decode it, if needed
		args[argname] = value; // Store as a property
	}
	return args; // Return the object
}
//alert(getArgs()['str']);或alert(getArgs().str);

// 返回url的参数部分
function GetURLParam(arr) {
	var text = "";
	if (arr === undefined)
		return text;
	for ( var i in arr) {
		if (arr[i] != undefined) {
			if (text.length > 0)
				text += "&";
			text += (i + "=" + arr[i]);
		}
	}
	if (text.length > 0)
		text = "?" + text;

	return text;
}

function GetURLParamNO(arr) {
	var text = "";
	if (arr === undefined)
		return text;
	for ( var i in arr) {
		if (arr[i] != undefined) {
			if (text.length > 0)
				text += "&";
			text += (i + "=" + arr[i]);
		}
	}

	return text;
} 

//生成GUID类似的随机数
function newGuid() {
	var guid = "";
	for ( var i = 1; i <= 32; i++) {
		var n = Math.floor(Math.random() * 16.0).toString(16);
		guid += n;
		if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
			guid += "-";
	}
	return guid;
}
/**
 * 全选
 * 
 * items 复选框的name
 */
function allCheckBox(items) {
	$('[name=' + items + ']:checkbox').attr("checked", true);
}

/**
 * 全不选
 * 
 */
function unAllCheckBox() {
	$('[type=checkbox]:checkbox').attr('checked', false);
}

/**
 * 反选
 * 
 * items 复选框的name
 */
function inverseCheckBox(items) {
	$('[name=' + items + ']:checkbox').each(function() {
		//此处用jq写法颇显啰嗦。体现不出JQ飘逸的感觉。
			//$(this).attr("checked", !$(this).attr("checked"));

			//直接使用js原生代码，简单实用
			this.checked = !this.checked;
		});
}
function checkKeyValueIsNull(str) {
	if (str == undefined || str == null || str == "" || str == "null"
			|| str == "undefined") {
		return true;
	} else
		return false;

}function checkValueIsNull(str) {
	if (str == null || str == "" || str == "null" || str == "undefined") {
		return true;
	}
	return false;
}


//会干扰到extjs的Date
//Date.prototype.format = function(format) {
//	var o = {
//		"M+" : this.getMonth() + 1, //month
//		"d+" : this.getDate(), //day
//		"h+" : this.getHours(), //hour
//		"m+" : this.getMinutes(), //minute
//		"s+" : this.getSeconds(), //second
//		"q+" : Math.floor((this.getMonth() + 3) / 3), //quarter
//		"S" : this.getMilliseconds()
//	//millisecond
//	}
//	if (/(y+)/.test(format))
//		format = format.replace(RegExp.$1, (this.getFullYear() + "")
//				.substr(4 - RegExp.$1.length));
//	for ( var k in o)
//		if (new RegExp("(" + k + ")").test(format))
//			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
//					: ("00" + o[k]).substr(("" + o[k]).length));
//	return format;
//}
/**Parses string formatted as YYYY-MM-DD to a Date object.
 * If the supplied string does not match the format, an
 * invalid Date (value NaN) is returned.
 * @param {string} dateStringInRange format YYYY-MM-DD, with year in
 * range of 0000-9999, inclusive.
 * @return {Date} Date object representing the string.
 */
function parseISO8601(dateStringInRange) {
	var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/, date = new Date(NaN), month, parts = isoExp
			.exec(dateStringInRange);

	if (parts) {
		month = +parts[2];
		date.setFullYear(parts[1], month - 1, parts[3]);
		if (month != date.getMonth() + 1) {
			date.setTime(NaN);
		}
	}
	return date;
} 

//针对ie6的兼容情况
/**
 * 等比例缩放图片[长或宽达到100%]
 * @param {HTMLObject} imgObj 存放图片的dom节点
 * @param {Number} widthValue 宽度
 * @param {Number} heightValue 高度
 */
function DrawImage2(imgObj, widthValue, heightValue) {
	if (imgObj == undefined || widthValue == undefined
			|| heightValue == undefined) {
		return;
	}
	var image = new Image();
	image.src = imgObj.src;
	var imageRatio = image.width / image.height;
	var thumbRatio = widthValue / heightValue;
	if (thumbRatio < imageRatio) {
		heightValue = widthValue / imageRatio;
	} else {
		widthValue = heightValue * imageRatio;
	}
	imgObj.width = widthValue;
	imgObj.height = heightValue;
}
/**
 * 图片的显示大小[宽高同时限制]
 * @param {HTMLObject} imgObj 存放图片的dom节点
 * @param {Number} widthValue 宽度
 * @param {Number} heightValue 高度
 */
function DrawImage(imgObj, widthValue, heightValue) {
	var image = new Image();
	image.src = imgObj.src;
	if (image.width > 0 && image.height > 0) {
		if (image.height > heightValue || image.width > widthValue) {
			var h = 0, w, wflg = false;
			if (image.height > heightValue)
				wflg = true;
			if (wflg) {
				w = widthValue;
				h = (image.height * widthValue) / image.width;
			}
			if (h == 0 || h > heightValue) {
				h = heightValue;
				w = (image.width * heightValue) / image.height;
			}
//			alert(w);
//			alert(h);
			imgObj.width = w;
			imgObj.height = h;
		} else {
			imgObj.width = image.width;
			imgObj.height = image.height;
		}
	}
}
/**
 * @return {int类型的IE版本号}
 */
function getIEVersion(){
	var isIE=!!window.ActiveXObject;
	var isIE6=isIE&&!window.XMLHttpRequest;
	var isIE8=isIE&&!!document.documentMode;
	var isIE7=isIE&&!isIE6&&!isIE8;
	if (isIE){
	    if (isIE6){
	       return 6;
	    }else if (isIE8){
	        return 8;
	    }else if (isIE7){
	        return 7;
	    }
	}
}
/**
 * 是否是IE6浏览器
 * @return {true/false} 
 */
function IsIE6(){
	var isIE=!!window.ActiveXObject;
	var isIE6=isIE&&!window.XMLHttpRequest;
	if (isIE){
	    if (isIE6){
	       return true;
	    }
	}
	return false;
}
function ShowImg(iwidth, iheight,ImgID) {
	var image = new Image();
	image.src = $("#"+ImgID).attr("src");
    var widths = image.width;
    var heights = image.height;
    var newHeight, newWidth;
    if (widths > 0 && heights > 0) {
        if (widths / heights >= iwidth / iheight) {
            if (widths > iwidth) {
                newWidth = iwidth;
                newHeight = (heights * iwidth) / widths;
            } else {
                newWidth = iwidth;
                newHeight = (heights * iwidth) / widths;
            }
        }
        else {
            if (heights > iheight) {
                newHeight = iheight;
                newWidth = (widths * iheight) / heights;
            } else {
                newHeight = iheight;
                newWidth = (widths * iheight) / heights;
            }
        }
    }
    $("#"+ImgID).css("height", newHeight);
    $("#"+ImgID).css("width", newWidth);
    /*以下三行代码是将图片设置在其外的div的水平居中和垂直居中显示,此处DIV的宽度和高度均为325px*/
    var newWidth = parseInt($("#"+ImgID).css("width"));
    var lefts = parseInt((325 - newWidth) / 2), tops = parseInt((325 - newHeight) / 2);
    $("#"+ImgID).css("margin-left", lefts + "px").css("margin-top", tops + "px");
 }

/**
 * 判断数字a的第几位是否为1
 * 
 * @param a
 *            原始数字
 * @param bit
 *            第几位 ,从0开始
 * @return true/false
 */
function isset(a, bit) {
	a = a >> bit;
	if ((a & 1) == 0)
		return false;
	return true;
} 

/**
 * 修改时间格式
 * @param {Object} time
 * @return {TypeName} 
 */
function updateTimeShow(time){
	if(time.length == 14){
		return time.substring(0,4) + "-" + time.substring(4,6) + "-" + time.substring(6,8) + " " 
			+ time.substring(8,10) + ":" + time.substring(10,12) + ":" + time.substring(12);
	}else if(time.length == 8){
		return time.substring(0,4) + "-" + time.substring(4,6) + "-" + time.substring(6);
	}else if(time.length == 6){
		return time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4);
	}
	return time;
}

/**
 * 验证是否是数字
 * @return {TypeName} 
 */
function validateNum(obj){
	if(obj.length <= 0){
		return false;
	}
	var reg = new RegExp("^[0-9]*$");
    if(!reg.test(obj)){
        return false;
    }
    if(!/^[0-9]*$/.test(obj)){
        return false;
    }
    return true;
}

/**
 * 修改钱的显示样式
 * @param {Object} money
 */
function fmoney(s, n){
   n = n > 0 && n <= 20 ? n : 2;   
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
   var l = s.split(".")[0].split("").reverse(),   
   r = s.split(".")[1];   
   t = "";   
   for(i = 0; i < l.length; i ++ )   
   {   
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
   }   
   return t.split("").reverse().join("") + "." + r;   
}
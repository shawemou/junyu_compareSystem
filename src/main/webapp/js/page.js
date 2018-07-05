var pageSize = 10; //页面分页个数
var endPage = 1;//尾页
/**
 * 显示条数 当前页和共计页数
 * @param {Object} count
 * @param {Object} nowPage
 * @return {TypeName} 
 */
function printPageNum(count,nowPage){
	endPage = (parseInt(count/pageSize) + (count%pageSize > 0 ? 1 : 0));
	return "共计" + count + "条 当前" + nowPage + "页 共" + (parseInt(count/pageSize) + (count%pageSize > 0 ? 1 : 0)) + "页";
}

/**
 * 页码
 * @param {Object} count 共计条数
 * @param {Object} nowPage 当前页
 * @return {TypeName} 
 */
function printPage(count,nowPage) {
//	var pageCount = parseInt(count/pageSize) + (count%pageSize > 0 ? 1 : 0);
	return "<div>第<input id='page_next' value="+nowPage+">页</div> <div id='endPage'>尾页</div> <div id='nextPage'>下一页</div> <div id='lastPage'>上一页</div> <div id='fristPage'>首页</div>";
	
	var pageHtml = '<li class="' + (nowPage == 1 ? 'disabled' : '') + '" data-page="1"><a href="#" class="1"><i class="icon-double-angle-left"></i></a></li>';
	
	var beforeNum = 5;
	var afterNum = 5;
	if(nowPage <= parseInt(pageSize/2)){
		beforeNum = nowPage-1;
		afterNum = pageSize - beforeNum;
	}else if(nowPage + parseInt(pageSize/2) > pageCount){
		afterNum = pageCount - nowPage;
		beforeNum = pageSize-afterNum
	}
	
	for(var i = beforeNum; i > 0; i--){
		if(nowPage - i > 0)
		pageHtml += '<li data-page="' + (nowPage - i) + '"><a href="#">' + (nowPage - i) + '</a></li>';
	}
	
	pageHtml += '<li class="active"><a href="#">' + nowPage + '</a></li>';
	
	for(var j = 1;j <= afterNum; j++){
		if(nowPage + j <= pageCount)
		pageHtml += '<li data-page="' + (nowPage + j) + '"><a href="#">' + (nowPage + j) + '</a></li>';
	}
	
	pageHtml += '<li class="' + (nowPage == pageCount ? 'disabled' : '') + '" data-page="' + pageCount + '"><a href="#"><i class="icon-double-angle-right"></i></a></li>';
	return pageHtml;
}


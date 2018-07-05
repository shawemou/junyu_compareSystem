package com.junyu.common.returnBean;

import java.util.HashMap;
import java.util.Map;

public class EReturnCompareCode {
	public static Map<String,String> map = new HashMap<String,String>();
	static {
		map.put("0", "系统判断为同一人");
		map.put("1", "系统不能确定是否为同一人");
		map.put("2", "系统判断为不同人");
		
		map.put("-4", "翻拍照,质量不合格");
		map.put("-7", "翻拍照,格式不正确");
		map.put("-6", "翻拍照,检测人脸失败");
		map.put("-9", "翻拍照,检测到多张人脸");
		
		map.put("-3", "现场照,质量不合格");
		map.put("-8", "现场照,格式不正确");
		map.put("-2", "现场照,检测人脸失败");
		map.put("-10", "现场照,检测到多张人脸");
		
		map.put("-5", "服务器内部错误");
		map.put("2000", "服务器内部错误");
		map.put("12002", "服务器内部错误");
		map.put("-1300", "服务器内部错误");
		map.put("-1301", "服务器连接超时");
	}
}

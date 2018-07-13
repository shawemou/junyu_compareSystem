package com.junyu.common.requestBean;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

public class CacheListBean {
	private String bar_code;

	@NotBlank(message = "方法名不能为空")
	private String method;

	private List<Object> data;

	@NotBlank(message = "用户id不能为空")
	private String user_guid;

	@NotBlank(message = "用户类型id不能为空")
	private String type_guid;
	
	@NotBlank(message="合同类型的id不能为空")
	private String compare_guid;
	

	public String getCompare_guid() {
		return compare_guid;
	}

	public void setCompare_guid(String compare_guid) {
		this.compare_guid = compare_guid;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String barCode) {
		bar_code = barCode;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}

	public String getUser_guid() {
		return user_guid;
	}

	public void setUser_guid(String user_guid) {
		this.user_guid = user_guid;
	}

	public String getType_guid() {
		return type_guid;
	}

	public void setType_guid(String type_guid) {
		this.type_guid = type_guid;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
}

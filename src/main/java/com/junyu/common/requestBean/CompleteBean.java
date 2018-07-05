package com.junyu.common.requestBean;

import org.hibernate.validator.constraints.NotBlank;

public class CompleteBean {
	
	@NotBlank(message="�����벻��Ϊ��")
	private String bar_code;

	@NotBlank(message="�û���guid����Ϊ��")
	private String user_guid;
	
	@NotBlank(message="�û������Ͳ���Ϊ��")
	private String type_guid;
	
	private String type_name;

	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
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
	public String getBar_code() {
		return bar_code;
	}
	public void setBar_code(String barCode) {
		bar_code = barCode;
	}
}

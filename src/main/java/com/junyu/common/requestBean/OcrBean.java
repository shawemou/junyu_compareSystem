package com.junyu.common.requestBean;

import org.hibernate.validator.constraints.NotBlank;

public class OcrBean {
	
	private String mobile;
	
	private String bar_code;
	
	@NotBlank(message="身份证照片不能为空")
	private String id_photo;

	@NotBlank(message="用户的guid不能为空")
	private String user_guid;
	
	@NotBlank(message="用户的类型不能为空")
	private String type_guid;
	
	private String type_name;
	
	
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

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String barCode) {
		this.bar_code = barCode;
	}

	public String getId_photo() {
		return id_photo;
	}

	public void setId_photo(String idPhoto) {
		this.id_photo = idPhoto;
	}
}

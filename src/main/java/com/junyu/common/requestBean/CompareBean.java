package com.junyu.common.requestBean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class CompareBean {

	@Length(min = 11, message = "手机号不能小于11位")
	private String mobile;
	
	private String bar_code;
	
	@NotNull(message = "姓名不能为空")
	private String name;
	
	@NotNull(message="证件号码不能为空")
	private String id_number;
	
	@NotNull(message="证件类型不能为空")
	private String id_type;// 0其他证件，1身份证

	@NotNull(message="翻拍照不能为空")
	private String photo_id;
	
	@NotNull(message="现场照不能为空")
	private String photo_user;
	
	//芯片照
	private String chipPhoto;
	
	
	@NotNull(message="用户的id不能为空")
	private String user_guid;
	
	@NotNull(message="用户的类型不能为空")
	private String type_guid;
	
	private String compare_guid;
	
	private String loginName;

	

	public String getChipPhoto() {
		return chipPhoto;
	}

	public void setChipPhoto(String chipPhoto) {
		this.chipPhoto = chipPhoto;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getCompare_guid() {
		return compare_guid;
	}

	public void setCompare_guid(String compare_guid) {
		this.compare_guid = compare_guid;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String idNumber) {
		this.id_number = idNumber;
	}

	public String getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(String photoId) {
		this.photo_id = photoId;
	}

	public String getPhoto_user() {
		return photo_user;
	}

	public void setPhoto_user(String photoUser) {
		this.photo_user = photoUser;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String idType) {
		this.id_type = idType;
	}
}

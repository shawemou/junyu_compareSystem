package com.junyu.common.requestBean;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordBean extends LoginBean {

	@NotBlank(message = "新密码不能为空")
	private String new_secret_key;

	@NotBlank(message = "重新输入密码不能为空")
	private String repeat_secret_key;

	private String user_guid;

	public String getUser_guid() {
		return user_guid;
	}

	public void setUser_guid(String user_guid) {
		this.user_guid = user_guid;
	}

	public String getNew_secret_key() {
		return new_secret_key;
	}

	public void setNew_secret_key(String newSecretKey) {
		new_secret_key = newSecretKey;
	}

	public String getRepeat_secret_key() {
		return repeat_secret_key;
	}

	public void setRepeat_secret_key(String repeatSecretKey) {
		repeat_secret_key = repeatSecretKey;
	}
}

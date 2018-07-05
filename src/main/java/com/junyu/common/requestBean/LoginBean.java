package com.junyu.common.requestBean;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mu Xiao-Fei
 * @Description
 * @Data 2018��6��28��
 */
public class LoginBean {

	@Length(min = 11, message = "�ֻ��Ų���С��11λ")
	private String mobile;

	
	@NotBlank(message="���벻��Ϊ��")
	private String secret_key;

	private String loginName;

	private String type;
	
	private String ip;
	

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSecret_key() {
		return secret_key;
	}

	public void setSecret_key(String secretKey) {
		this.secret_key = secretKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "LoginBean [mobile=" + mobile + ", secret_key=" + secret_key + ", type=" + type + "]";
	}

}
package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Sms {

	@JsonProperty("Extend")
	private String Extend;

	@JsonProperty("Code")
	private String Code;

	@JsonIgnore
	public String getExtend() {
		return Extend;
	}

	@JsonIgnore
	public void setExtend(String extend) {
		Extend = extend;
	}
	
	@JsonIgnore
	public String getCode() {
		return Code;
	}

	@JsonIgnore
	public void setCode(String code) {
		Code = code;
	}
}

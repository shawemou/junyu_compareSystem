package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Cert {

	@JsonProperty("Cert")
	private String Cert;

	@JsonProperty("InData")
	private String InData;

	@JsonProperty("SignData")
	private String SignData;
	
	@JsonProperty("SignAlg")
	private String SignAlg;

	@JsonIgnore
	public String getCert() {
		return Cert;
	}

	@JsonIgnore
	public void setCert(String cert) {
		Cert = cert;
	}

	@JsonIgnore
	public String getInData() {
		return InData;
	}

	@JsonIgnore
	public void setInData(String inData) {
		InData = inData;
	}

	@JsonIgnore
	public String getSignData() {
		return SignData;
	}

	@JsonIgnore
	public void setSignData(String signData) {
		SignData = signData;
	}

	@JsonIgnore
	public String getSignAlg() {
		return SignAlg;
	}

	@JsonIgnore
	public void setSignAlg(String signAlg) {
		SignAlg = signAlg;
	}
	

}

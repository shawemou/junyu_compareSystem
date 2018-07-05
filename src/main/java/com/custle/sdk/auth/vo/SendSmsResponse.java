package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendSmsResponse {

	@JsonProperty("Result")
	private int Result;

	@JsonProperty("Return")
	private String Return;
	
	@JsonProperty("Code")
	private String Code;

	@JsonProperty("SignatureAlgorithm")
	private String SignatureAlgorithm;

	@JsonProperty("SignatureValue")
	private String SignatureValue;

	public SendSmsResponse() {

	}

	public SendSmsResponse(int result, String msg, String code, String signAlg,
			String signValue) {
		this.Result = result;
		this.Return = msg;
		this.Code = code;
		this.SignatureAlgorithm = signAlg;
		this.SignatureValue = signValue;
	}

	@JsonIgnore
	public int getResult() {
		return Result;
	}

	@JsonIgnore
	public void setResult(int result) {
		Result = result;
	}

	@JsonIgnore
	public String getReturn() {
		return Return;
	}

	@JsonIgnore
	public void setReturn(String msg) {
		Return = msg;
	}

	@JsonIgnore
	public String getSignatureAlgorithm() {
		return SignatureAlgorithm;
	}

	@JsonIgnore
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		SignatureAlgorithm = signatureAlgorithm;
	}

	@JsonIgnore
	public String getSignatureValue() {
		return SignatureValue;
	}

	@JsonIgnore
	public void setSignatureValue(String signatureValue) {
		SignatureValue = signatureValue;
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

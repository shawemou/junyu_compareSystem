package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

	@JsonProperty("Result")
	private int Result;

	@JsonProperty("Return")
	private String Return;

	@JsonProperty("BizSN")
	private String BizSN;

	@JsonProperty("PersonName")
	private String PersonName;

	@JsonProperty("PersonID")
	private String PersonID;

	@JsonProperty("SignatureAlgorithm")
	private String SignatureAlgorithm;

	@JsonProperty("SignatureValue")
	private String SignatureValue;
	
	private String Similarity;
	
	private int userId;
	
	private Integer respSource;

	public AuthResponse() {
		
	}
	public AuthResponse(int result, String msg, String bizSN,
			String personName, String personID, String signAlg, String signValue) {
		this.Result = result;
		this.Return = msg;
		this.BizSN = bizSN;
		this.PersonName = personName;
		this.PersonID = personID;
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
	public String getBizSN() {
		return BizSN;
	}

	@JsonIgnore
	public void setBizSN(String bizSN) {
		BizSN = bizSN;
	}

	@JsonIgnore
	public String getPersonName() {
		return PersonName;
	}

	@JsonIgnore
	public void setPersonName(String personName) {
		PersonName = personName;
	}

	@JsonIgnore
	public String getPersonID() {
		return PersonID;
	}

	@JsonIgnore
	public void setPersonID(String personID) {
		PersonID = personID;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getSimilarity() {
		return Similarity;
	}
	
	public void setSimilarity(String similarity) {
		Similarity = similarity;
	}
	public Integer getRespSource() {
		return respSource;
	}
	public void setRespSource(Integer respSource) {
		this.respSource = respSource;
	}

}

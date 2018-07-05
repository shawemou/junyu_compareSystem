package com.custle.sdk.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class QryIDPhotoResponse implements Serializable{

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
	
	@JsonProperty("IDPhoto")
	private String IDPhoto; 

	@JsonProperty("SignatureAlgorithm")
	private String SignatureAlgorithm;

	@JsonProperty("SignatureValue")
	private String SignatureValue;

	private int userId;
	
	private int dataSource;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
	public void setReturn(String return1) {
		Return = return1;
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
	public String getIDPhoto() {
		return IDPhoto;
	}

	@JsonIgnore
	public void setIDPhoto(String iDPhoto) {
		IDPhoto = iDPhoto;
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

	public int getDataSource() {
		return dataSource;
	}

	public void setDataSource(int dataSource) {
		this.dataSource = dataSource;
	}
	
}

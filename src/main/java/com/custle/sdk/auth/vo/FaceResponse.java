package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceResponse {

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
	
	@JsonProperty("Similarity")
	private String Similarity;

	@JsonProperty("SignatureAlgorithm")
	private String SignatureAlgorithm;

	@JsonProperty("SignatureValue")
	private String SignatureValue;
	
	// ��ȡ��������
	@JsonProperty("FacePhotos")
	private String[] FacePhotos;
	
	@JsonIgnore
	private int userId;

	public FaceResponse() {
		
	}
	public FaceResponse(int result, String msg, String bizSN,
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

	@JsonIgnore
	public int getUserId() {
		return userId;
	}

	@JsonIgnore
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@JsonIgnore
	public String getSimilarity() {
		return Similarity;
	}

	@JsonIgnore
	public void setSimilarity(String similarity) {
		Similarity = similarity;
	}
	
	@JsonIgnore
	public String[] getFacePhotos() {
		return FacePhotos;
	}

	@JsonIgnore
	public void setFacePhotos(String[] facePhotos) {
		FacePhotos = facePhotos;
	}
}

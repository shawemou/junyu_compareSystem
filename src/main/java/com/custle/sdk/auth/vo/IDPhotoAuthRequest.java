package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IDPhotoAuthRequest {

	@JsonProperty("AppID")
	private String AppID;

	@JsonProperty("BizSN")
	private String BizSN;
	
	@JsonProperty("PaidAppID")
	private String PaidAppID;

	@JsonProperty("Person")
	private Person Person;

	@JsonProperty("Face")
	private IDPhotoAuthFace Face;

	@JsonIgnore
	public String getAppID() {
		return AppID;
	}

	@JsonIgnore
	public void setAppID(String appID) {
		AppID = appID;
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
	public Person getPerson() {
		return Person;
	}

	@JsonIgnore
	public void setPerson(Person person) {
		Person = person;
	}

	@JsonIgnore
	public IDPhotoAuthFace getFace() {
		return Face;
	}

	@JsonIgnore
	public void setFace(IDPhotoAuthFace face) {
		Face = face;
	}

	@JsonIgnore
	public String getPaidAppID() {
		return PaidAppID;
	}

	@JsonIgnore
	public void setPaidAppID(String paidAppID) {
		PaidAppID = paidAppID;
	}

}

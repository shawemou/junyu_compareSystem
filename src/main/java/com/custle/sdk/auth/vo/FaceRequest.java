package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FaceRequest {

	@JsonProperty("AppID")
	private String AppID;

	@JsonProperty("BizSN")
	private String BizSN;
	
	@JsonProperty("PaidAppID")
	private String PaidAppID;

	@JsonProperty("ClientName")
	private String ClientName;

	@JsonProperty("ClientType")
	private String ClientType;

	@JsonProperty("Person")
	private Person Person;

	@JsonProperty("Face")
	private Face Face;

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
	public String getClientName() {
		return ClientName;
	}

	@JsonIgnore
	public void setClientName(String clientName) {
		ClientName = clientName;
	}

	@JsonIgnore
	public String getClientType() {
		return ClientType;
	}

	@JsonIgnore
	public void setClientType(String clientType) {
		ClientType = clientType;
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
	public Face getFace() {
		return Face;
	}

	@JsonIgnore
	public void setFace(Face face) {
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

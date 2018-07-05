package com.custle.sdk.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class QryIDPhotoRequest implements Serializable{

	@JsonProperty("AppID")
	private String AppID;
	
	@JsonProperty("BizSN")
	private String BizSN;
	
	@JsonProperty("PersonName")
	private String PersonName;

	@JsonProperty("PersonID")
	private String PersonID;

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
	
	
}

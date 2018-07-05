package com.custle.sdk.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AuditApplyRequest implements Serializable {
	@JsonProperty("AppID")
	private String AppID;

	@JsonProperty("BizSN")
	private String BizSN;
	
	@JsonProperty("FrontSidePhoto")
	private String FrontSidePhoto;
	
	@JsonProperty("BackSidePhoto")
	private String BackSidePhoto;
	
	@JsonProperty("CallBackURL")
	private String CallBackURL;

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
	public String getCallBackURL() {
		return CallBackURL;
	}

	@JsonIgnore
	public void setCallBackURL(String callBackURL) {
		CallBackURL = callBackURL;
	}

	@JsonIgnore
	public String getFrontSidePhoto() {
		return FrontSidePhoto;
	}

	@JsonIgnore
	public void setFrontSidePhoto(String frontSidePhoto) {
		FrontSidePhoto = frontSidePhoto;
	}

	@JsonIgnore
	public String getBackSidePhoto() {
		return BackSidePhoto;
	}

	@JsonIgnore
	public void setBackSidePhoto(String backSidePhoto) {
		BackSidePhoto = backSidePhoto;
	}
	
	
}

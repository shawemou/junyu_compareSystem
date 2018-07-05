package com.custle.sdk.auth.vo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CompareRequest implements Serializable {

	@JsonProperty("AppID")
	private String AppID;

	@JsonProperty("BizSN")
	private String BizSN;
	
	@JsonProperty("Result")
	private int Result;
	
	@JsonProperty("Msg")
	private String Msg;

	@JsonProperty("Similarity")
	private String Similarity;
	
	
	@JsonIgnore
	public String getAppID() {
		return AppID;
	}

	@JsonIgnore
	public void setAppID(String appID) {
		AppID = appID;
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
	public String getBizSN() {
		return BizSN;
	}

	@JsonIgnore
	public void setBizSN(String bizSN) {
		BizSN = bizSN;
	}
	
	@JsonIgnore
	public String getMsg() {
		return Msg;
	}

	@JsonIgnore
	public void setMsg(String msg) {
		Msg = msg;
	}

	@JsonIgnore
	public String getSimilarity() {
		return Similarity;
	}
	
	@JsonIgnore
	public void setSimilarity(String similarity) {
		Similarity = similarity;
	}
}

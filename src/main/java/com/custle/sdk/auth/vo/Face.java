package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Face {

	@JsonProperty("FacePhoto")
	private String FacePhoto;

	// ֤������������
	@JsonProperty("CopyIDPhoto")
	private String CopyIDPhoto;
		
	@JsonProperty("Extend")
	private String Extend;

	@JsonIgnore
	public String getFacePhoto() {
		return FacePhoto;
	}

	@JsonIgnore
	public void setFacePhoto(String facePhoto) {
		FacePhoto = facePhoto;
	}

	@JsonIgnore
	public String getExtend() {
		return Extend;
	}

	@JsonIgnore
	public void setExtend(String extend) {
		Extend = extend;
	}

	@JsonIgnore
	public String getCopyIDPhoto() {
		return CopyIDPhoto;
	}

	@JsonIgnore
	public void setCopyIDPhoto(String copyIDPhoto) {
		CopyIDPhoto = copyIDPhoto;
	}

}

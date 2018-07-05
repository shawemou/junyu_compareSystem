package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IDPhotoAuthFace {

	@JsonProperty("FacePhoto")
	private String FacePhoto;

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

}

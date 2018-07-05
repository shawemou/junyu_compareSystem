package com.junyu.common.returnBean;

import java.util.HashMap;
import java.util.Map;

public class BaseReturn {

	private boolean Success = true;
	private boolean version = true;
	private String code;
	private String info;
	private Map<String,Object> dbInfo = new HashMap<String,Object>();

	public BaseReturn() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public Map<String, Object> getDbInfo() {
		return dbInfo;
	}

	public void setDbInfo(Map<String, Object> dbInfo) {
		this.dbInfo = dbInfo;
	}

	public boolean getSuccess() {
		return Success;
	}

	public void setSuccess(boolean success) {
		Success = success;
	}

	public boolean getVersion() {
		return version;
	}

	public void setVersion(boolean version) {
		this.version = version;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


}

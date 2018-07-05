package com.junyu.common.returnBean;

import java.util.Date;

public class OcrReturn extends BaseReturn {

	public String name;
	public String id_number;
	public Date beginDate = new Date();
	
	public OcrReturn(){}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String idNumber) {
		id_number = idNumber;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
}

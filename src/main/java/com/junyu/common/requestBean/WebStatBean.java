package com.junyu.common.requestBean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class WebStatBean {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date beginDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public Date endDate;
	public String bankName;
	private String parenttype;
	private String typeGuid;

	private String page_no;
	private String page_count;

	public String getTypeGuid() {
		return typeGuid;
	}

	public void setTypeGuid(String typeGuid) {
		this.typeGuid = typeGuid;
	}

	public String getParenttype() {
		return parenttype;
	}

	public void setParenttype(String parenttype) {
		this.parenttype = parenttype;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getPage_no() {
		return page_no;
	}

	public void setPage_no(String pageNo) {
		page_no = pageNo;
	}

	public String getPage_count() {
		return page_count;
	}

	public void setPage_count(String pageCount) {
		page_count = pageCount;
	}
}

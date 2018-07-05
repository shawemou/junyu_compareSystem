package com.junyu.common.requestBean;

public class WebMobileBean {
	
	private String guid;
	private String mobile;
	private String name;
	private String gender;
	private String id_number;
	private String department;
	private String duties;
	private String busable;
	private String detail_des;
	private String bank_name;
	private String parenttype;
	
	private String page_no;
	private String page_count;
	
	
	public String getParenttype() {
		return parenttype;
	}
	public void setParenttype(String parenttype) {
		this.parenttype = parenttype;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String idNumber) {
		this.id_number = idNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public String getBusable() {
		return busable;
	}
	public void setBusable(String busable) {
		this.busable = busable;
	}
	public String getDetail_des() {
		return detail_des;
	}
	public void setDetail_des(String detailDes) {
		this.detail_des = detailDes;
	}
	public String getPage_no() {
		return page_no;
	}
	public void setPage_no(String pageNo) {
		page_no = pageNo;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bankName) {
		bank_name = bankName;
	}
	public String getPage_count() {
		return page_count;
	}
	public void setPage_count(String pageCount) {
		page_count = pageCount;
	}
}

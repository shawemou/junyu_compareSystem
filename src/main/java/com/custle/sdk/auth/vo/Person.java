package com.custle.sdk.auth.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {

	@JsonProperty("PersonName")
	private String PersonName;

	@JsonProperty("PersonID")
	private String PersonID;

	@JsonProperty("Province")
	private String Province;

	@JsonProperty("City")
	private String City;

	@JsonProperty("Unit")
	private String Unit;

	@JsonProperty("Department")
	private String Department;

	@JsonProperty("Mobilephone")
	private String Mobilephone;

	@JsonProperty("Telephone")
	private String Telephone;

	@JsonProperty("Email")
	private String Email;

	@JsonProperty("Address")
	private String Address;

	@JsonProperty("Postcode")
	private String Postcode;

	@JsonProperty("Extend")
	private String Extend;

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

	@JsonIgnore
	public String getProvince() {
		return Province;
	}

	@JsonIgnore
	public void setProvince(String province) {
		Province = province;
	}

	@JsonIgnore
	public String getCity() {
		return City;
	}

	@JsonIgnore
	public void setCity(String city) {
		City = city;
	}

	@JsonIgnore
	public String getUnit() {
		return Unit;
	}

	@JsonIgnore
	public void setUnit(String unit) {
		Unit = unit;
	}

	@JsonIgnore
	public String getDepartment() {
		return Department;
	}

	@JsonIgnore
	public void setDepartment(String department) {
		Department = department;
	}

	@JsonIgnore
	public String getMobilephone() {
		return Mobilephone;
	}

	@JsonIgnore
	public void setMobilephone(String mobilephone) {
		Mobilephone = mobilephone;
	}

	@JsonIgnore
	public String getTelephone() {
		return Telephone;
	}

	@JsonIgnore
	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

	@JsonIgnore
	public String getEmail() {
		return Email;
	}

	@JsonIgnore
	public void setEmail(String email) {
		Email = email;
	}

	@JsonIgnore
	public String getAddress() {
		return Address;
	}

	@JsonIgnore
	public void setAddress(String address) {
		Address = address;
	}

	@JsonIgnore
	public String getPostcode() {
		return Postcode;
	}

	@JsonIgnore
	public void setPostcode(String postcode) {
		Postcode = postcode;
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

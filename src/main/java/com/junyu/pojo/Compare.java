package com.junyu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Table(name = "F_COMPARE")
public class Compare extends BasePojo implements Serializable {
	@Id
	@Column(name = "GUID")
	private String guid;

	@Column(name = "USER_GUID")
	private String userGuid;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CERTID")
	private String certid;

	@Column(name = "SEX")
	private String sex;

	@Column(name = "BIRTHDAY")
	private String birthday;

	@Column(name = "FORK")
	private String fork;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "ISSUE_AUTHORITY")
	private String issueAuthority;

	@Column(name = "VALID_PRIOD")
	private String validPriod;

	@Column(name = "RETURN_CODE")
	private String returnCode;

	@Column(name = "RETURN_INFO")
	private String returnInfo;

	@Column(name = "CODE1")
	private String code1;

	@Column(name = "SIMILARITY1")
	private BigDecimal similarity1;

	@Column(name = "CODE2")
	private String code2;

	@Column(name = "SIMILARITY2")
	private BigDecimal similarity2;

	@Column(name = "CODE3")
	private String code3;

	@Column(name = "SIMILARITY3")
	private BigDecimal similarity3;

	@Column(name = "SOURCE_CODE")
	private String sourceCode;

	@Column(name = "SOURCE_INFO")
	private String sourceInfo;

	@Column(name = "IP")
	private String ip;

	@Column(name = "HOLD_TIME")
	private BigDecimal holdTime;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "TYPE_NAME")
	private String typeName;

	@Column(name = "COMPARE_TYPE")
	private String compareType;

	@Column(name = "BAR_CODE")
	private String barCode;

	@Column(name = "CERTID_TYPE")
	private String certidType;

	@Column(name = "MOBILE")
	private String mobile;

	// 0代表有合同号1代表没有合同号
	@Column(name = "BAR_FlAG")
	private String barFlag;

	// 机构名称
	@Column(name = "ORG_NAME")
	private String orgName;

	// 业务类型id
	@Column(name = "SERVICE_ID")
	private String serviceId;
	
	// 受理编号
	@Column(name = "SERVICE_NUMBER")
	private String serviceNumber;

	private static final long serialVersionUID = 1L;

	public String getBarFlag() {
		return barFlag;
	}

	public void setBarFlag(String barFlag) {
		this.barFlag = barFlag;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	/**
	 * @return GUID
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * @return USER_GUID
	 */
	public String getUserGuid() {
		return userGuid;
	}

	/**
	 * @param userGuid
	 */
	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
	}

	/**
	 * @return NAME
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return CERTID
	 */
	public String getCertid() {
		return certid;
	}

	/**
	 * @param certid
	 */
	public void setCertid(String certid) {
		this.certid = certid;
	}

	/**
	 * @return SEX
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return BIRTHDAY
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return FORK
	 */
	public String getFork() {
		return fork;
	}

	/**
	 * @param fork
	 */
	public void setFork(String fork) {
		this.fork = fork;
	}

	/**
	 * @return ADDRESS
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return ISSUE_AUTHORITY
	 */
	public String getIssueAuthority() {
		return issueAuthority;
	}

	/**
	 * @param issueAuthority
	 */
	public void setIssueAuthority(String issueAuthority) {
		this.issueAuthority = issueAuthority;
	}

	/**
	 * @return VALID_PRIOD
	 */
	public String getValidPriod() {
		return validPriod;
	}

	/**
	 * @param validPriod
	 */
	public void setValidPriod(String validPriod) {
		this.validPriod = validPriod;
	}

	/**
	 * @return RETURN_CODE
	 */
	public String getReturnCode() {
		return returnCode;
	}

	/**
	 * @param returnCode
	 */
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return RETURN_INFO
	 */
	public String getReturnInfo() {
		return returnInfo;
	}

	/**
	 * @param returnInfo
	 */
	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	/**
	 * @return CODE1
	 */
	public String getCode1() {
		return code1;
	}

	/**
	 * @param code1
	 */
	public void setCode1(String code1) {
		this.code1 = code1;
	}

	/**
	 * @return SIMILARITY1
	 */
	public BigDecimal getSimilarity1() {
		return similarity1;
	}

	/**
	 * @param similarity1
	 */
	public void setSimilarity1(BigDecimal similarity1) {
		this.similarity1 = similarity1;
	}

	/**
	 * @return CODE2
	 */
	public String getCode2() {
		return code2;
	}

	/**
	 * @param code2
	 */
	public void setCode2(String code2) {
		this.code2 = code2;
	}

	/**
	 * @return SIMILARITY2
	 */
	public BigDecimal getSimilarity2() {
		return similarity2;
	}

	/**
	 * @param similarity2
	 */
	public void setSimilarity2(BigDecimal similarity2) {
		this.similarity2 = similarity2;
	}

	/**
	 * @return CODE3
	 */
	public String getCode3() {
		return code3;
	}

	/**
	 * @param code3
	 */
	public void setCode3(String code3) {
		this.code3 = code3;
	}

	/**
	 * @return SIMILARITY3
	 */
	public BigDecimal getSimilarity3() {
		return similarity3;
	}

	/**
	 * @param similarity3
	 */
	public void setSimilarity3(BigDecimal similarity3) {
		this.similarity3 = similarity3;
	}

	/**
	 * @return SOURCE_CODE
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 * @param sourceCode
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * @return SOURCE_INFO
	 */
	public String getSourceInfo() {
		return sourceInfo;
	}

	/**
	 * @param sourceInfo
	 */
	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}

	/**
	 * @return IP
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return HOLD_TIME
	 */
	public BigDecimal getHoldTime() {
		return holdTime;
	}

	/**
	 * @param holdTime
	 */
	public void setHoldTime(BigDecimal holdTime) {
		this.holdTime = holdTime;
	}

	/**
	 * @return CREATE_TIME
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return USER_TYPE
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return TYPE_NAME
	 */
	public String getTypeName() {
		return typeName;
	}

	/**
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * @return COMPARE_TYPE
	 */
	public String getCompareType() {
		return compareType;
	}

	/**
	 * @param compareType
	 */
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	/**
	 * @return BAR_CODE
	 */
	public String getBarCode() {
		return barCode;
	}

	/**
	 * @param barCode
	 */
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 * @return CERTID_TYPE
	 */
	public String getCertidType() {
		return certidType;
	}

	/**
	 * @param certidType
	 */
	public void setCertidType(String certidType) {
		this.certidType = certidType;
	}

	/**
	 * @return MOBILE
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
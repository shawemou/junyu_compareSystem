package com.junyu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Table(name = "F_CACHE_HISTORY")
public class CacheHistory extends BasePojo implements Serializable {
	@Id
	@Column(name = "GUID")
	private String guid;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "BAR_CODE")
	private String barCode;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ID_NUMBER")
	private String idNumber;

	@Column(name = "SIMILARITY")
	private BigDecimal similarity;

	@Column(name = "SUCCESS")
	private String success;

	@Column(name = "RETURN_CODE")
	private String returnCode;

	@Column(name = "HOLD_TIME")
	private BigDecimal holdTime;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "ID_TYPE")
	private String idType;

	@Column(name = "ETYPE")
	private String etype;

	@Column(name = "SOURCE_CODE")
	private String sourceCode;

	@Column(name = "SOURCE_INFO")
	private String sourceInfo;

	@Column(name = "SOURCE_HOLD_TIME")
	private BigDecimal sourceHoldTime;

	@Column(name = "USER_GUID")
	private String userGuid;

	@Column(name = "USER_TYPE")
	private String userType;

	@Column(name = "TYPE_NAME")
	private String typeName;

	@Column(name = "COMPARE_TYPE")
	private String compareType;

	@Column(name = "RETURN_INFO")
	private String returnInfo;

	@Column(name = "CODE")
	private String code;

	private static final long serialVersionUID = 1L;

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
	 * @return ID_NUMBER
	 */
	public String getIdNumber() {
		return idNumber;
	}

	/**
	 * @param idNumber
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	/**
	 * @return SIMILARITY
	 */
	public BigDecimal getSimilarity() {
		return similarity;
	}

	/**
	 * @param similarity
	 */
	public void setSimilarity(BigDecimal similarity) {
		this.similarity = similarity;
	}

	/**
	 * @return SUCCESS
	 */
	public String getSuccess() {
		return success;
	}

	/**
	 * @param success
	 */
	public void setSuccess(String success) {
		this.success = success;
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
	 * @return ID_TYPE
	 */
	public String getIdType() {
		return idType;
	}

	/**
	 * @param idType
	 */
	public void setIdType(String idType) {
		this.idType = idType;
	}

	/**
	 * @return ETYPE
	 */
	public String getEtype() {
		return etype;
	}

	/**
	 * @param etype
	 */
	public void setEtype(String etype) {
		this.etype = etype;
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
	 * @return SOURCE_HOLD_TIME
	 */
	public BigDecimal getSourceHoldTime() {
		return sourceHoldTime;
	}

	/**
	 * @param sourceHoldTime
	 */
	public void setSourceHoldTime(BigDecimal sourceHoldTime) {
		this.sourceHoldTime = sourceHoldTime;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
package com.junyu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "F_OCR_HISTORY")
public class OcrHistory extends BasePojo implements Serializable {
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

    @Column(name = "SUCCESS")
    private String success;

    @Column(name = "HOLD_TIME")
    private BigDecimal holdTime;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "ID_PHOTO")
    private String idPhoto;

    @Column(name = "PHO_ID")
    private byte[] phoId;
    
    @Column(name="USER_NAME")
    private String userName;

    private static final long serialVersionUID = 1L;

    
    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
     * @return ID_PHOTO
     */
    public String getIdPhoto() {
        return idPhoto;
    }

    /**
     * @param idPhoto
     */
    public void setIdPhoto(String idPhoto) {
        this.idPhoto = idPhoto;
    }

    /**
     * @return PHO_ID
     */
    public byte[] getPhoId() {
        return phoId;
    }

    /**
     * @param phoId
     */
    public void setPhoId(byte[] phoId) {
        this.phoId = phoId;
    }
}
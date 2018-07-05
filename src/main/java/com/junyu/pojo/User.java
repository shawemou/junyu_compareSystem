package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Table(name = "T_USER")
public class User extends BasePojo implements Serializable {
    @Id
    @Column(name = "GUID")
    private String guid;

    @NotBlank(message="登录名不能为空")
    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "ID_NUMBER")
    private String idNumber;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "BUSABLE")
    private String busable;

    @Column(name = "DETAIL_DES")
    private String detailDes;

    @Column(name = "USER_GUID")
    private String userGuid;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "SECRET_TYPE")
    private String secretType;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    @NotBlank(message="手机号不能为空")
    @Length(max=11,message="手机号不能大于11位")
    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "TYPE_GUID")
    private String typeGuid;

    @Column(name = "TYPE_NAME")
    private String typeName;
    
    @Column(name = "DUTIES")
    private String duties;


    private static final long serialVersionUID = 1L;
    
    

    public String getDuties() {
		return duties;
	}

	public void setDuties(String duties) {
		this.duties = duties;
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
     * @return LOGIN_NAME
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * @param loginName
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return PASSWORD
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return GENDER
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
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
     * @return DEPARTMENT
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return BUSABLE
     */
    public String getBusable() {
        return busable;
    }

    /**
     * @param busable
     */
    public void setBusable(String busable) {
        this.busable = busable;
    }

    /**
     * @return DETAIL_DES
     */
    public String getDetailDes() {
        return detailDes;
    }

    /**
     * @param detailDes
     */
    public void setDetailDes(String detailDes) {
        this.detailDes = detailDes;
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
     * @return SECRET_TYPE
     */
    public String getSecretType() {
        return secretType;
    }

    /**
     * @param secretType
     */
    public void setSecretType(String secretType) {
        this.secretType = secretType;
    }

    /**
     * @return UPDATE_TIME
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * @return TYPE_GUID
     */
    public String getTypeGuid() {
        return typeGuid;
    }

    /**
     * @param typeGuid
     */
    public void setTypeGuid(String typeGuid) {
        this.typeGuid = typeGuid;
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

}
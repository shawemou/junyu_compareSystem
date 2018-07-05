package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "T_USER_LOGIN_LOG")
public class UserLoginLog extends BasePojo implements Serializable {
    @Column(name = "GUID")
    private String guid;

    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CODE")
    private String code;

    @Column(name = "IP")
    private String ip;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "SUCCESS")
    private String success;
    
    @Column(name="USER_GUID")
    private String userGuid;

    private static final long serialVersionUID = 1L;

    
    public String getUserGuid() {
		return userGuid;
	}

	public void setUserGuid(String userGuid) {
		this.userGuid = userGuid;
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
     * @return CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
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
}
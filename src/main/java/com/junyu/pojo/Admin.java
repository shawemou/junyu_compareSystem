package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Table(name = "T_ADMIN")
public class Admin extends BasePojo implements Serializable {
    @Id
    @Column(name = "GUID")
    private String guid;

    @Length(min=5,max=18,message="登录名长度不正确")
    @NotBlank(message="用户名不能为空")
    @Column(name = "LOGIN_NAME")
    private String loginName;

    @Length(min=5,max=18,message="密码长度不正确")
    @NotBlank(message="密码不能为空")
    @Column(name = "LOGIN_PWD")
    private String loginPwd;

    @Column(name = "NAME")
    private String name;

    @Column(name = "BUSABLE")
    private String busable;

    @Column(name = "DETAIL_DES")
    private String detailDes;

    @Column(name = "CREATE_TIME")
    private Date createTime;

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
     * @return LOGIN_PWD
     */
    public String getLoginPwd() {
        return loginPwd;
    }

    /**
     * @param loginPwd
     */
    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
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
}
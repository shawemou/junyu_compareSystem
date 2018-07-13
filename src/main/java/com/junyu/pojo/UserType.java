package com.junyu.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Table(name = "F_USER_TYPE")
public class UserType extends BasePojo implements Serializable {
    @Id
    @Column(name = "GUID")
    private String guid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PARENT_ID")
    private String parentId;

    @Column(name = "CREATE_TIME")
    private Date createTime;

    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "UserType [guid=" + guid + ", name=" + name + ", parentId=" + parentId + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

    
}
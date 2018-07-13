package com.junyu.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "F_SERVICE")
public class ServiceDic extends BasePojo{

	@Id
    @Column(name = "GUID")
	private String guid;

    @Column(name = "NAME")
	private String name;
    
    @Column(name = "CREATE_TIME")
    private Date createTime;

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

	public ServiceDic() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceDic(String guid, String name) {
		super();
		this.guid = guid;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Service [guid=" + guid + ", name=" + name + "]";
	}

}

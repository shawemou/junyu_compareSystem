package com.junyu.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junyu.common.returnBean.BaseReturn;
import com.junyu.pojo.Admin;
import com.junyu.utils.AESCodec;
import com.junyu.utils.CommonUtils;

@Service
public class AdminService extends BaseService<Admin> {
	
	@Autowired
	private HttpServletRequest request;

	//У���û���
	public void validate(Admin record, BaseReturn br) {
		CommonUtils.setInfo(br, true, "��¼�ɹ�");
		record.setLoginPwd(AESCodec.aesEncrypt(record.getLoginPwd()));
		Admin admin = this.queryOne(record);
		if(admin==null){
			CommonUtils.setInfo(br, false, "��¼ʧ��,��¼�������벻����");
			return ;
		}
		if(StringUtils.equals(admin.getBusable(), "0")){
			CommonUtils.setInfo(br, false, "���û���ͣ��");
			return;
		}
		request.getSession().setAttribute("user", admin);
	}
}

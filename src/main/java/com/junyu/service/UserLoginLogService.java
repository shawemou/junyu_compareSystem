package com.junyu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.pojo.User;
import com.junyu.pojo.UserLoginLog;
import com.junyu.utils.CommonUtils;
import com.junyu.utils.IPUtils;

@Service
public class UserLoginLogService extends BaseService<UserLoginLog> {
	
	@Autowired
	private IPUtils ipUtils;

	public void save(LoginBean loginBean, BaseReturn br) {
		UserLoginLog record = new UserLoginLog();
		record.setGuid(CommonUtils.getUUID());
		record.setLoginName(loginBean.getLoginName());
		record.setSuccess(br.getSuccess() ? "1" : "0");
		record.setIp(loginBean.getIp());
		User user = (User) br.getDbInfo().get("user");
		if(user!=null&&user.getGuid()!=null){
			record.setUserGuid(user.getGuid());
		}
		this.save(record);
	}
}

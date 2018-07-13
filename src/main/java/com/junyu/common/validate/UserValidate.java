package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnLogin;
import com.junyu.pojo.UserLoginLog;

public class UserValidate {

	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean) {

		try {
			  UserLoginLog loginLog = new UserLoginLog();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || StringUtils.isBlank(json.getString("loginName"))){
				bean.setCode(EReturnLogin.RT_NotMatch_Null_LoginName);
				return false;
			}else{
				loginLog.setLoginName(json.getString("loginName"));
			}
			viBean.setLoginLog(loginLog);
		} catch (JSONException e) {
			Log4jUtil.log.error("»Î≤ŒstrJsonΩ‚Œˆ ß∞‹", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return true;
	}
}

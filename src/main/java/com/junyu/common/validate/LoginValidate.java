package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnLogin;
import com.junyu.common.returnBean.ReturnBean;
import com.sun.tools.javac.comp.Check;

/**
 * @ClassName: LoginValidate 
 * @Description: 登录信息验证
 * @author lulinlin
 * @date 2017-11-24 下午02:15:28 
 *
 */
public class LoginValidate {
	
	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		try {
			LoginBean loginBean = new LoginBean();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || StringUtils.isBlank(json.getString("loginName"))){
				bean.setCode(EReturnLogin.RT_NotMatch_Null_LoginName);
				return false;
			}else{
				loginBean.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("password") || StringUtils.isBlank(json.getString("password"))){
				bean.setCode(EReturnLogin.RT_NotMatch_Null_Password);
				return false;
			}else{
				loginBean.setSecret_key(json.getString("password"));
			}
			loginBean.setIp(viBean.getIp());
			viBean.setLoginBean(loginBean);
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return true;
	}
}

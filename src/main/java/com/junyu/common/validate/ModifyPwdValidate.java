package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.ModifyPwdBean;
import com.junyu.common.requestBean.PasswordBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnModifyPwd;
import com.junyu.common.returnBean.ReturnBean;
import com.sun.tools.javac.comp.Check;

/**
 * @ClassName: ModifyPwdValidate 
 * @Description:  密码修改验证 
 * @author lulinlin
 * @date 2017-11-24 下午02:16:48 
 *
 */
public class ModifyPwdValidate {
	
	public static boolean vali(String strJson, VisitInfoBean viBean, ReturnBean bean){
		try {
			PasswordBean modifyPwdBean = new PasswordBean();
			JSONObject json = new JSONObject(strJson);
			if( !json.has("loginName") || StringUtils.isBlank(json.getString("loginName"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_LoginName);
				return false;
			}else{
				modifyPwdBean.setLoginName(json.getString("loginName"));
			}
			
			if( !json.has("password") || StringUtils.isBlank(json.getString("password"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_Password);
				return false;
			}else{
				modifyPwdBean.setSecret_key(json.getString("password"));
			}
			
			if( !json.has("newPassword") || StringUtils.isBlank(json.getString("newPassword"))){
				bean.setCode(EReturnModifyPwd.RT_NotMatch_Null_NewPassword);
				return false;
			}else{
				modifyPwdBean.setNew_secret_key(json.getString("newPassword"));
			}
			
			viBean.setModifyPwdBean(modifyPwdBean);
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		return true;
	}
}

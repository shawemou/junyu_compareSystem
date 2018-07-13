package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EnumInstance;
import com.junyu.common.returnBean.EnumInstance.EReturn;
import com.junyu.common.returnBean.ReturnBean;
import com.sun.tools.javac.comp.Check;

/**
 * @ClassName: MainValidate 
 * @Description: 入参验证
 * @author lulinlin
 * @date 2017-11-24 上午11:15:25 
 *
 */
public class MainValidate {

	/**
	 * 常用入参验证
	 */
	public static boolean validate(String strJson, VisitInfoBean viBean, ReturnBean bean){
		if(StringUtils.isBlank(strJson)){
			bean.setCode(EReturn.RT_NotMatch_Format);
			return false;
		}
		//1,首先有type
		try {
			JSONObject json = new JSONObject(strJson);
			if( !json.has("type") || StringUtils.isBlank(json.getString("type"))){
				bean.setCode(EReturn.RT_NotMatch_type_null);
				return false;
			}else{
				viBean.setType(json.getString("type"));
			}
		} catch (JSONException e) {
			Log4jUtil.log.error("入参strJson解析失败", e);
			bean.setCode(EReturn.RT_NotMatch_Format_Json);
			return false;
		}
		//2,根据type来判断业务
		if( viBean.getType().equals(EnumInstance.loginType) ){
			return LoginValidate.vali(strJson, viBean, bean);
		}else if( viBean.getType().equals(EnumInstance.modifyPwdType) ){
			return ModifyPwdValidate.vali(strJson, viBean, bean);
		}else if( viBean.getType().equals(EnumInstance.compareType) ){
			return CompareValidate.vali(strJson, viBean, bean);
		}else if( viBean.getType().equals(EnumInstance.userAndBankType) ){
			return UserValidate.vali(strJson, viBean, bean);
		}else{
			bean.setCode(EReturn.RT_NotMatch_Service_null);
			return false;
		}
	}
}

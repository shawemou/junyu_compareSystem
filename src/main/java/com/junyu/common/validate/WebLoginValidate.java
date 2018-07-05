package com.junyu.common.validate;

import org.apache.commons.lang3.StringUtils;

import com.junyu.common.returnBean.BaseReturn;

public class WebLoginValidate {
	
	public static BaseReturn validate(String login_name, String login_psd){
		BaseReturn br = new BaseReturn();
		if( StringUtils.isBlank(login_name) ){
			br.setSuccess(false);
			br.setInfo("��¼��Ϊ��");
			return br;
		}else if( login_name.length() < 5 || login_name.length() > 18 ){
			br.setSuccess(false);
			br.setInfo("��¼�����Ȳ���ȷ");
			return br;
		}else if( StringUtils.isBlank(login_psd) ){
			br.setSuccess(false);
			br.setInfo("��¼����Ϊ��");
			return br;
		}else if( login_psd.length() < 5 || login_psd.length() > 18 ){
			br.setSuccess(false);
			br.setInfo("��¼���볤�Ȳ���ȷ");
			return br;
		}
		return br;
	}
}

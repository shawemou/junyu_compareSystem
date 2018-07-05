package com.junyu.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.config.ReadingSetting;

public class CommonUtils {

	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	public static boolean valiVersion(BaseReturn br, String version) {
		if (StringUtils.isBlank(version)) {
			br.setSuccess(false);
			br.setVersion(true);
			br.setInfo("����˽�����Ϣ������,������"); 
			logger.warn("����˽�����Ϣ������,������");
			return false;
		} else {
			if (!SpringBeanUtils.getBean2(ReadingSetting.class).getVersion().equals(decode(version))) {
				br.setSuccess(false);
				br.setVersion(false);
				br.setInfo("�汾����,������"); 
				Log4jUtil.log.warn("�汾����,������");
				return false;
			}
		}
		br.setInfo("�ɹ�");
		return true;
	}

	public static String decode(String reqString) {
		if (StringUtils.isNoneBlank(reqString)) {
			try {
				return URLDecoder.decode(reqString, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return reqString;
	}

	public static BaseReturn validate(LoginBean loginBean) {
		BaseReturn br = new BaseReturn();
		if (StringUtils.isBlank(loginBean.getMobile())) {
			br.setSuccess(false);
			br.setInfo("�ֻ��Ų���Ϊ��");
			return br;
		} else if (loginBean.getMobile().length() < 11) {
			br.setSuccess(false);
			br.setInfo("�ֻ��Ų�������11λ");
			return br;
		} else if (StringUtils.isBlank(loginBean.getSecret_key())) {
			br.setSuccess(false);
			br.setInfo("�������벻��Ϊ��");
			return br;
		}
		return br;
	}

	//����uuid
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	
	//���÷���ֵ
	public static void setInfo(BaseReturn br,Boolean success,Boolean version,String info){
		br.setInfo(info);
		br.setSuccess(success);
		br.setVersion(version);
	}
	
	public static void setInfo(BaseReturn br,Boolean success,String info){
		setInfo(br, success, true, info);
	}

	//��ȡ��У��ϸ��Ϣ
	public static ArrayList<String> getError(BindingResult result) {
		ArrayList<String> info = new ArrayList<String>();
		for (ObjectError error : result.getAllErrors()) {
			info.add(error.getDefaultMessage());
		}
		return info;
	}
}

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
			br.setInfo("服务端接收信息不完整,请重试"); 
			logger.warn("服务端接收信息不完整,请重试");
			return false;
		} else {
			if (!SpringBeanUtils.getBean2(ReadingSetting.class).getVersion().equals(decode(version))) {
				br.setSuccess(false);
				br.setVersion(false);
				br.setInfo("版本过低,请升级"); 
				Log4jUtil.log.warn("版本过低,请升级");
				return false;
			}
		}
		br.setInfo("成功");
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
			br.setInfo("手机号不能为空");
			return br;
		} else if (loginBean.getMobile().length() < 11) {
			br.setSuccess(false);
			br.setInfo("手机号不能少于11位");
			return br;
		} else if (StringUtils.isBlank(loginBean.getSecret_key())) {
			br.setSuccess(false);
			br.setInfo("输入密码不能为空");
			return br;
		}
		return br;
	}

	//生成uuid
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	
	//设置返回值
	public static void setInfo(BaseReturn br,Boolean success,Boolean version,String info){
		br.setInfo(info);
		br.setSuccess(success);
		br.setVersion(version);
	}
	
	public static void setInfo(BaseReturn br,Boolean success,String info){
		setInfo(br, success, true, info);
	}

	//读取表单校验细信息
	public static ArrayList<String> getError(BindingResult result) {
		ArrayList<String> info = new ArrayList<String>();
		for (ObjectError error : result.getAllErrors()) {
			info.add(error.getDefaultMessage());
		}
		return info;
	}
}

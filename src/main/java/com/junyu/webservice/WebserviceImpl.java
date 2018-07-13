package com.junyu.webservice;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.custle.sdk.Log4jUtil;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.returnBean.EReturnCompareCode;
import com.junyu.common.returnBean.EnumInstance;
import com.junyu.common.returnBean.EnumInstance.EReturnCompare;
import com.junyu.common.returnBean.EnumInstance.EReturnLogin;
import com.junyu.common.returnBean.EnumInstance.EReturnModifyPwd;
import com.junyu.common.returnBean.EnumInstance.EReturnSource;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.common.validate.MainValidate;
import com.junyu.pojo.User;
import com.junyu.service.CompareService;
import com.junyu.service.UserLoginLogService;
import com.junyu.service.UserService;
import com.junyu.utils.SpringBeanUtils;

/**
 * @ClassName: JYWebserviceImpl
 * @Description: 骏聿接口服务，对外发布Webservice
 * @author lulinlin
 * @date 2015-7-28 下午10:16:14
 *
 */
public class WebserviceImpl implements IWebservice {
	private static Logger logger = LoggerFactory.getLogger(WebserviceImpl.class);

	/**
	 * 测试调用
	 * 
	 * @param message
	 *            测试信息
	 * @return
	 */
	public String example(String message) {
		UserService userService = (UserService) SpringBeanUtils.getBean(UserService.class);
		List<User> userlist = userService.queryAll();
		UserLoginLogService loginLogService = (UserLoginLogService) SpringBeanUtils.getBean(UserLoginLogService.class);
		CompareService compareService = (CompareService) SpringBeanUtils.getBean(CompareService.class);
		return message + "_success" + userlist.toString();
	}

	/**
	 * 程序入口
	 */
	public String method(String strJson) {
		VisitInfoBean viBean = new VisitInfoBean();
		ReturnBean bean = new ReturnBean();
		try {
			UserService userService = (UserService) SpringBeanUtils.getBean(UserService.class);
			UserLoginLogService loginLogService = (UserLoginLogService) SpringBeanUtils.getBean(UserLoginLogService.class);
			CompareService compareService = (CompareService) SpringBeanUtils.getBean(CompareService.class);

			logger.info("开始:" + strJson.length());
			// 1,基本信息验证
			if (!MainValidate.validate(strJson, viBean, bean)) {
				return getReturnStr(viBean, bean);
			}

			if (EnumInstance.loginType.equals(viBean.getType())) {
				// 2,用户登录
				userService.login(viBean.getLoginBean(), bean);
				loginLogService.save(viBean.getLoginBean(), bean);
			} else if (EnumInstance.modifyPwdType.equals(viBean.getType())) {
				// 3,密码修改
				userService.passwordModify(viBean.getModifyPwdBean(), bean);
			} else if (EnumInstance.userAndBankType.equals(viBean.getType())) {
				// 4,获取用户和所有银行信息修改
				// 先校验user
				userService.validateUser(viBean, bean);
				if (bean.getSuccess()) {
					userService.getUserAndBanks(viBean, bean);
				}
			} else if (EnumInstance.compareType.equals(viBean.getType())) {
				// 5,对比
				// 先校验user
				userService.validateUser(viBean, bean);
				if (bean.getSuccess()) {
					compareService.client(viBean, bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bean.setCode(EReturnSource.RT_InError);
		}

		// 4,返回给用户并入库
		return getReturnStr(viBean, bean);
	}

	private String getReturnStr(VisitInfoBean viBean, ReturnBean bean) {
		JSONObject json = new JSONObject();
		try {
			json.put("code", Integer.parseInt(bean.getCode()));
			if (EnumInstance.loginType.equals(viBean.getType())) {
				json.put("info", EReturnLogin.map.get(bean.getCode()));
			} else if (EnumInstance.modifyPwdType.equals(viBean.getType())) {
				json.put("info", EReturnModifyPwd.map.get(bean.getCode()));
			} else if (EnumInstance.compareType.equals(viBean.getType())) {
				setCompareInfo(viBean, bean, json);
				CompareService compareService = (CompareService) SpringBeanUtils.getBean(CompareService.class);
				compareService.saveWebServiceCompare(viBean, bean);
			} else if (EnumInstance.userAndBankType.equals(viBean.getType())) {
				User user = viBean.getUser();
				json.put("info", EReturnLogin.map.get(bean.getCode()));
				json.put("typeNameList", bean.getDbInfo().get("typeNameList"));
				json.put("serviceNameList", bean.getDbInfo().get("serviceNameList"));
				json.put("userType", bean.getDbInfo().get("userType"));
				json.put("role", bean.getDbInfo().get("role"));
			} else {
				json.put("info", EReturnLogin.map.get(bean.getCode()));
			}
			logger.info("返回:" + json.toString());
		} catch (JSONException e) {
			logger.error("组装返回json异常", e);
		}
		return json.toString();
	}

	/**
	 * 比对服务，重写返回描述
	 * 
	 * @param viBean
	 * @param bean
	 */
	private void setCompareInfo(VisitInfoBean viBean, ReturnBean bean, JSONObject json) {
		try {
			String info = EReturnCompare.map.get(bean.getCode());
			json.put("code", Integer.parseInt(bean.getCode()));
			json.put("info", info);

			// 多源接口调用失败时，二次定义返回码和描述，注释时间2018年04月19日
			// 有芯片照时，三照比对通过时，多源接口调用方式已改为多线程调用
			// 没有芯片照时，翻拍和现场比对通过时，顺序调用多源接口
			if (EReturnCompare.RT_Success.equals(bean.getCode()) && StringUtils.isNotBlank(bean.getCrBean4().getCode())) {
				if (!EReturnSource.RT_Success.equals(bean.getCrBean4().getCode())) {
					bean.setCode(EReturnCompare.RT_InError_source);
					String info4 = bean.getCrBean4().getInfo() == null ? "" : (":" + bean.getCrBean4().getInfo());
					info = EReturnCompare.map.get(bean.getCode()) + info4;
					json.put("code", Integer.parseInt(bean.getCode()));
					json.put("info", info);
				}
			}
			bean.setInfo(info);

			// if(EReturnCompare.RT_Success.equals(bean.getCode()) ){
			// if( Check.IsStringNULL(bean.getCrBean4().getCode()) ){
			// bean.setCode(EReturnCompare.RT_InError_source);
			// json.put("code", Integer.parseInt(bean.getCode()));
			// json.put("info", EReturnCompare.map.get(bean.getCode()));
			// }else
			// if(EReturnSource.RT_Success.equals(bean.getCrBean4().getCode())){
			// json.put("info", EReturnCompare.map.get(bean.getCode()));
			// }else{
			// bean.setCode(EReturnCompare.RT_InError_source);
			// String info = bean.getCrBean4().getInfo() == null ? "" : (":" +
			// bean.getCrBean4().getInfo());
			// json.put("code", Integer.parseInt(bean.getCode()));
			// json.put("info", EReturnCompare.map.get(bean.getCode()) + info );
			// }
			// }else{
			// json.put("info", EReturnCompare.map.get(bean.getCode()));
			// }
		} catch (JSONException e) {
			Log4jUtil.log.error("组装比对接口返回json异常", e);
		}
	}

}

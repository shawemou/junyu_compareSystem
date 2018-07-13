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
 * @Description: ���ӿڷ��񣬶��ⷢ��Webservice
 * @author lulinlin
 * @date 2015-7-28 ����10:16:14
 *
 */
public class WebserviceImpl implements IWebservice {
	private static Logger logger = LoggerFactory.getLogger(WebserviceImpl.class);

	/**
	 * ���Ե���
	 * 
	 * @param message
	 *            ������Ϣ
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
	 * �������
	 */
	public String method(String strJson) {
		VisitInfoBean viBean = new VisitInfoBean();
		ReturnBean bean = new ReturnBean();
		try {
			UserService userService = (UserService) SpringBeanUtils.getBean(UserService.class);
			UserLoginLogService loginLogService = (UserLoginLogService) SpringBeanUtils.getBean(UserLoginLogService.class);
			CompareService compareService = (CompareService) SpringBeanUtils.getBean(CompareService.class);

			logger.info("��ʼ:" + strJson.length());
			// 1,������Ϣ��֤
			if (!MainValidate.validate(strJson, viBean, bean)) {
				return getReturnStr(viBean, bean);
			}

			if (EnumInstance.loginType.equals(viBean.getType())) {
				// 2,�û���¼
				userService.login(viBean.getLoginBean(), bean);
				loginLogService.save(viBean.getLoginBean(), bean);
			} else if (EnumInstance.modifyPwdType.equals(viBean.getType())) {
				// 3,�����޸�
				userService.passwordModify(viBean.getModifyPwdBean(), bean);
			} else if (EnumInstance.userAndBankType.equals(viBean.getType())) {
				// 4,��ȡ�û�������������Ϣ�޸�
				// ��У��user
				userService.validateUser(viBean, bean);
				if (bean.getSuccess()) {
					userService.getUserAndBanks(viBean, bean);
				}
			} else if (EnumInstance.compareType.equals(viBean.getType())) {
				// 5,�Ա�
				// ��У��user
				userService.validateUser(viBean, bean);
				if (bean.getSuccess()) {
					compareService.client(viBean, bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			bean.setCode(EReturnSource.RT_InError);
		}

		// 4,���ظ��û������
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
			logger.info("����:" + json.toString());
		} catch (JSONException e) {
			logger.error("��װ����json�쳣", e);
		}
		return json.toString();
	}

	/**
	 * �ȶԷ�����д��������
	 * 
	 * @param viBean
	 * @param bean
	 */
	private void setCompareInfo(VisitInfoBean viBean, ReturnBean bean, JSONObject json) {
		try {
			String info = EReturnCompare.map.get(bean.getCode());
			json.put("code", Integer.parseInt(bean.getCode()));
			json.put("info", info);

			// ��Դ�ӿڵ���ʧ��ʱ�����ζ��巵�����������ע��ʱ��2018��04��19��
			// ��оƬ��ʱ�����ձȶ�ͨ��ʱ����Դ�ӿڵ��÷�ʽ�Ѹ�Ϊ���̵߳���
			// û��оƬ��ʱ�����ĺ��ֳ��ȶ�ͨ��ʱ��˳����ö�Դ�ӿ�
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
			Log4jUtil.log.error("��װ�ȶԽӿڷ���json�쳣", e);
		}
	}

}

package com.junyu.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.junyu.common.requestBean.WebMobileBean;
import com.junyu.common.requestBean.WebStatBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.common.returnBean.Page;
import com.junyu.pojo.Admin;
import com.junyu.pojo.Compare;
import com.junyu.pojo.Rate;
import com.junyu.pojo.User;
import com.junyu.pojo.UserType;
import com.junyu.service.CompareService;
import com.junyu.service.UserService;
import com.junyu.service.UserTypeService;
import com.junyu.utils.AESCodec;
import com.junyu.utils.CommonUtils;

@Controller
@RequestMapping("web")
public class WebUserController {

	private static Logger logger = LoggerFactory.getLogger(WebLoginController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CompareService compareService;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private HttpServletRequest request;

	// 1,��ȡ�û���Ϣ
	@RequestMapping(value = "webDateList", method = { RequestMethod.POST, RequestMethod.GET }/*/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public Page<User> wenLogin(WebMobileBean webBean) {
		logger.info("��ȡ�����û���Ϣ");
		Page<User> page = new Page<User>();
		try {
			page = this.userService.query(page, webBean);
		} catch (Exception e) {
			logger.error("��ȡ�û���Ϣʧ��" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("��ȡ��Ϣʧ��");
		}

		return page;

	}

	// 2,��ȡ��������
	@RequestMapping(value = "loadMobile", method = { RequestMethod.POST, RequestMethod.GET }/*/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public User getOneUser(WebMobileBean webBean) {
		logger.info("��ȡ������Ϣ");
		try {
			User user = this.userService.queryById(webBean.getGuid());
			UserType userType = this.userTypeService.queryById(user.getTypeGuid());
			user.setTypeName(userType.getName());
			return user;
		} catch (Exception e) {
			logger.error("��ȡ�����û���Ϣʧ��" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// 3,���Ŀ���״̬
	@RequestMapping(value = "updateBusable", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn updateBusable(WebMobileBean webBean) {
		logger.info("���Ŀ���״̬");
		BaseReturn br = new BaseReturn();
		try {
			User user = this.userService.queryById(webBean.getGuid());
			User record = new User();
			record.setGuid(webBean.getGuid());
			record.setBusable("1".equals(user.getBusable()) ? "2" : "1");
			this.userService.updateSelective(record);
			CommonUtils.setInfo(br, true, "1".equals(user.getBusable()) ? "2" : "1");
		} catch (Exception e) {
			logger.error("����״̬ʧ��" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "����״̬ʧ��");
		}
		return br;
	}

	// 4,��������
	@RequestMapping(value = "updatePwd", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn reSetPassword(WebMobileBean webBean) {
		logger.info("��������");
		BaseReturn br = new BaseReturn();
		try {
			User record = new User();
			record.setGuid(webBean.getGuid());
			record.setPassword(AESCodec.aesEncrypt("111111"));
			record.setSecretType("1");
			this.userService.updateSelective(record);
			CommonUtils.setInfo(br, true, "��������ɹ�");
		} catch (Exception e) {
			logger.error("��������ʧ��" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "��������ʧ��");
		}

		return br;
	}

	// 5,change�¼���ȡuser
	@RequestMapping(value = "validataUser", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn validataUser(User record) {
		logger.info("У���޸Ļ���ע���û����ֻ��ź��û��Ƿ����");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			if (record.getLoginName() != null && record.getLoginName().matches("[0-9]+")) {
				CommonUtils.setInfo(br, false, "��¼������Ϊ������");
			} else if (record.getMobile() != null && record.getMobile().length() > 11) {
				CommonUtils.setInfo(br, false, "�ֻ��Ų��ܴ���11λ");
			}
			if (br.getSuccess()) {
				User user = this.userService.queryOne(record);
				if (user == null) {
					CommonUtils.setInfo(br, false, "���û��Ѿ���������������");
				}
			}
		} catch (Exception e) {
			logger.error("У��" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "ϵͳ����");
		}
		return br;
	}

	// 6,�޸�
	@RequestMapping(value = "updateUser", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn updateUser(@Valid User record, @RequestParam("parenttype") String parenttype, BindingResult result) {
		logger.info("�޸��û�");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			// 1,��У��
			if (result.hasErrors()) {
				CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				return br;
			}
			// 2,����user
			this.userService.updateUser(br, record, parenttype);
		} catch (Exception e) {
			logger.error("����ʧ��" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "ϵͳ����");
		}
		return br;
	}

	// 7,����saveMobile
	@RequestMapping(value = "saveMobile", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn saveMobile(@Valid User record, @RequestParam("parenttype") String parenttype, BindingResult result) {
		logger.info("�����û�");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			// 1,��У��
			if (result.hasErrors()) {
				CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				return br;
			}
			// 2,����user
			Admin admin = (Admin) request.getSession().getAttribute("user");
			record.setUserGuid(admin.getGuid());
			this.userService.addUser(br, record, parenttype);

		} catch (Exception e) {
			logger.error("����ʧ��" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "ϵͳ����");
		}
		return br;
	}

	// 8,ͳ�Ʋ�ѯ
	@RequestMapping(value = "completeList", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public Page<Compare> completeList(WebStatBean statBean) {
		logger.info("ͳ�Ʋ�ѯ");
		Page<Compare> page = new Page<Compare>();
		try {
			page = this.compareService.query(page, statBean);
		} catch (Exception e) {
			logger.error("��ȡͳ����Ϣʧ��" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("��ȡ��Ϣʧ��");
		}
		return page;
	}

	// 9,��֤һ��ʹ����ͳ��
	@RequestMapping(value = "rateList", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public Page<Rate> rateList(WebStatBean statBean) {
		logger.info("ͳ�Ʋ�ѯ");
		Page<Rate> page = new Page<Rate>();
		try {
			page = this.compareService.queryRate(page, statBean);
		} catch (Exception e) {
			logger.error("��ȡͳ����Ϣʧ��" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("��ȡ��Ϣʧ��");
		}
		return page;

	}

	//10, ��ȡ���е��û�����
	@RequestMapping(value = "getBanKList", method = { RequestMethod.POST, RequestMethod.GET }/*
																							  ,
																							  produces
																							  =
																							  "application/json;charset=GBK"
																							 */)
	@ResponseBody
	public BaseReturn getBanKList() {
		logger.info("��ȡ���е��û�����");
		BaseReturn br = new BaseReturn();
		new Page<UserType>();
		try {
			HashMap<String, Object> dbInfo = new HashMap<String, Object>();
			dbInfo.put("typeList", this.userTypeService.queryAll());
			br.setDbInfo(dbInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "����������");
			br.setDbInfo(null);
		}
		return br;
	}
	
	
	
		//11, ��ȡ�����û�
		@RequestMapping(value = "getUserList", method = { RequestMethod.POST, RequestMethod.GET }/*
																								  ,
																								  produces
																								  =
																								  "application/json;charset=GBK"
																								 */)
		@ResponseBody
		public BaseReturn getUserList() {
			logger.info("��ȡ�����û�");
			BaseReturn br = new BaseReturn();
			new Page<UserType>();
			try {
				HashMap<String, Object> dbInfo = new HashMap<String, Object>();
				dbInfo.put("userList", this.userService.queryAll());
				br.setDbInfo(dbInfo);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
				CommonUtils.setInfo(br, false, "����������");
				br.setDbInfo(null);
			}
			return br;
		}
		
		
		// 12,��ȡ�����û�
		@RequestMapping(value = "offlineList", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
		@ResponseBody
		public Page<User> offlineList(WebStatBean statBean) {
			logger.info("��ȡ�����û�");
			Page<User> page = new Page<User>();
			try {
				page = this.userService.queryOffline(page,statBean);
			} catch (Exception e) {
				logger.error("��ȡ�û���Ϣʧ��" + e.getMessage());
				e.printStackTrace();
				page.setSuccess(false);
				page.setData(null);
				page.setInfo("��ȡ��Ϣʧ��");
			}
			return page;
		}
}

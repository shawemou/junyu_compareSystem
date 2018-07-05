package com.junyu.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.service.UserLoginLogService;
import com.junyu.service.UserService;
import com.junyu.utils.CommonUtils;

@Controller
public class UserLoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserLoginLogService userLoginLogService;
	
	private static Logger logger = LoggerFactory.getLogger(UserLoginController.class);

	@RequestMapping(value = "login", method = RequestMethod.POST,produces="application/json;charset=gbk")
	@ResponseBody
	public String login(@RequestParam("version") String version, @Valid LoginBean loginBean, BindingResult result) throws ServletException, IOException {
		BaseReturn br = new BaseReturn();
		logger.info("�û���¼");
		try {
			// 1,У��汾�Ƿ�Ϊ��;�жϰ汾���Ƿ�����õİ汾��һ��
			if (CommonUtils.valiVersion(br, version)) {
				// 2,��У��
				if (StringUtils.isBlank(loginBean.getLoginName() + loginBean.getMobile())) {
					CommonUtils.setInfo(br, false, "��������ȷ���û������� �ֻ���");
				} else if (result.hasErrors()) {
					CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				}
				if (br.getSuccess()) {
					// 3,��ѯ�û��Ƿ���ڲ�У������
					br = this.userService.login(loginBean, br);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "����������");
		}
		// 4,�����¼��־
		this.userLoginLogService.save(loginBean, br);
		return JSONObject.toJSONString(br);
	}
}

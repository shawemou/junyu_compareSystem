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
		logger.info("用户登录");
		try {
			// 1,校验版本是否为空;判断版本号是否和内置的版本号一致
			if (CommonUtils.valiVersion(br, version)) {
				// 2,表单校验
				if (StringUtils.isBlank(loginBean.getLoginName() + loginBean.getMobile())) {
					CommonUtils.setInfo(br, false, "请输入正确的用户名或者 手机号");
				} else if (result.hasErrors()) {
					CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				}
				if (br.getSuccess()) {
					// 3,查询用户是否存在并校验密码
					br = this.userService.login(loginBean, br);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "服务器出错");
		}
		// 4,插入登录日志
		this.userLoginLogService.save(loginBean, br);
		return JSONObject.toJSONString(br);
	}
}

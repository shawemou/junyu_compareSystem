package com.junyu.controller;

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

import com.junyu.common.returnBean.BaseReturn;
import com.junyu.pojo.Admin;
import com.junyu.service.AdminService;
import com.junyu.utils.CommonUtils;

@Controller
@RequestMapping("web")
public class WebLoginController {

	private static Logger logger = LoggerFactory.getLogger(WebLoginController.class);
	
	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "webLogin", method = { RequestMethod.POST, RequestMethod.GET }/*, produces = "application/json; charset=UTF-8"*/)
	@ResponseBody
	public BaseReturn wenLogin(@Valid Admin admin, BindingResult result) {
		logger.info("web登录");
		BaseReturn br = new BaseReturn();
		try {
			// 1,表单校验
			if (result.hasErrors()) {
				CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
			}
			if (br.getSuccess()) {
				// 2,数据库校验
				this.adminService.validate(admin,br);

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "登录异常");
		}
		return br;
	}
}

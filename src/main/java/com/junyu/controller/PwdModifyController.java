package com.junyu.controller;

import java.io.IOException;

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
import com.junyu.common.requestBean.PasswordBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.service.UserService;
import com.junyu.utils.CommonUtils;

/**
 * @author Mu Xiao-Fei
 * @Description 修改密码
 * @Data 2018年6月28日
 */
@Controller
public class PwdModifyController {

	private static Logger logger = LoggerFactory.getLogger(PwdModifyController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "pwdModify", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String login(@RequestParam("version") String version, @Valid PasswordBean passwordBean, BindingResult result) throws ServletException,
			IOException {
		logger.info("修改密码");
		BaseReturn br = new BaseReturn();
		try {
			// 1,version校验
			if (CommonUtils.valiVersion(br, version)) {
				// 2,表单校验
				if (result.hasErrors()) {
					CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				}
				if (!StringUtils.equals(passwordBean.getNew_secret_key(), passwordBean.getRepeat_secret_key())) {
					CommonUtils.setInfo(br, false, "新密码和旧密码不一致");
				}
				if (br.getSuccess()) {
					// 3,校验并保存用户密码
					this.userService.passwordModify(passwordBean, br);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "服务器出错");
		}
		br.setDbInfo(null);
		return JSONObject.toJSONString(br);
	}

}

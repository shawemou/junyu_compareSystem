package com.junyu.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.pojo.User;
import com.junyu.service.UserService;
import com.junyu.service.UserTypeService;
import com.junyu.utils.CommonUtils;

@Controller
public class GetUserInfoController {

	private static Logger logger = LoggerFactory.getLogger(GetUserInfoController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserTypeService userTypeService;

	// 获取用户信息和用户类型
	@RequestMapping(value = "getUser", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String getUserInfoAndBanKList(@RequestParam("version") String version, @RequestParam("user_guid") String user_guid) {
		logger.info("获取用户信息和用户类型");
		BaseReturn br = new BaseReturn();
		try {
			// 1,校验版本是否为空;判断版本号是否和内置的版本号一致
			if (CommonUtils.valiVersion(br, version)) {
				// 2,根据user_guid查询用户
				User user = this.userService.queryById(user_guid);
				// 3,根据所有的user_type
				HashMap<String, Object> dbInfo = new HashMap<String, Object>();
				dbInfo.put("typeList", this.userTypeService.queryAll());
				dbInfo.put("user", user);
				br.setDbInfo(dbInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "服务器出错");
			br.setDbInfo(null);
		}
		return JSONObject.toJSONString(br);
	}

}

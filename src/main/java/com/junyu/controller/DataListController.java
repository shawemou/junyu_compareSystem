package com.junyu.controller;

import java.util.HashMap;

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

import com.alibaba.fastjson.JSONObject;
import com.junyu.common.requestBean.CompleteBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.service.CacheService;
import com.junyu.service.CompareService;
import com.junyu.utils.CommonUtils;

@Controller
public class DataListController {

	private static Logger logger = LoggerFactory.getLogger(DataListController.class);

	@Autowired
	private CompareService compareService;

	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "dataList", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String getDataList(@RequestParam("version") String version, @Valid CompleteBean completeBean, BindingResult result) {
		logger.info("获取业务信息");
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setInfo("成功");
		try {
			// 1,校验版本是否为空;判断版本号是否和内置的版本号一致
			if (CommonUtils.valiVersion(baseReturn, version)) {
				// 2,表单校验
				if (result.hasErrors()) {
					CommonUtils.setInfo(baseReturn, false, CommonUtils.getError(result).toString());
				}
				// 3,查询业务信息
				if (baseReturn.getSuccess()) {
					HashMap<String, Object> dbInfo = new HashMap<String, Object>();
					dbInfo.put("compareList", this.compareService.getDataList(completeBean));
					dbInfo.put("cahceList", this.cacheService.getDataList(completeBean));
					baseReturn.setDbInfo(dbInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(baseReturn, false, "服务器出错");
		}
		return JSONObject.toJSONString(baseReturn);
	}
}

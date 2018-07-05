package com.junyu.controller;

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
import com.junyu.common.requestBean.CompareBean;
import com.junyu.common.returnBean.CompareReturn;
import com.junyu.service.CacheService;
import com.junyu.service.CompareService;
import com.junyu.utils.CommonUtils;

@Controller
public class CatchController {

	private static Logger logger = LoggerFactory.getLogger(CatchController.class);

	@Autowired
	private CompareService compareService;
	
	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "cache", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String catchCompare(@RequestParam("version") String version, @Valid CompareBean compareBean, BindingResult result) {
		logger.info("二照比对");
		CompareReturn cr = new CompareReturn();
		try {
			// 1,校验version
			if (CommonUtils.valiVersion(cr, version)) {
				// 2,表单校验
				if (result.hasErrors()) {
					CommonUtils.setInfo(cr, false, CommonUtils.getError(result).toString());
				}
				// 3,调用webservice比对
				if (cr.getSuccess()) {
					cr = compareService.compare(compareBean, cr);
					// 4,插入比对暂存表(没有合同号-->进最终库,如果有合同号-->进暂存区)
					if(StringUtils.isBlank(compareBean.getBar_code())){
						this.compareService.saveCompare(compareBean, cr);
					}else{
						this.cacheService.saveCacheHistory(compareBean, cr);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(cr, false, "服务器出错");
		}
		return JSONObject.toJSONString(cr);
	}
}

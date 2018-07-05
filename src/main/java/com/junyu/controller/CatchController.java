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
		logger.info("���ձȶ�");
		CompareReturn cr = new CompareReturn();
		try {
			// 1,У��version
			if (CommonUtils.valiVersion(cr, version)) {
				// 2,��У��
				if (result.hasErrors()) {
					CommonUtils.setInfo(cr, false, CommonUtils.getError(result).toString());
				}
				// 3,����webservice�ȶ�
				if (cr.getSuccess()) {
					cr = compareService.compare(compareBean, cr);
					// 4,����ȶ��ݴ��(û�к�ͬ��-->�����տ�,����к�ͬ��-->���ݴ���)
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
			CommonUtils.setInfo(cr, false, "����������");
		}
		return JSONObject.toJSONString(cr);
	}
}

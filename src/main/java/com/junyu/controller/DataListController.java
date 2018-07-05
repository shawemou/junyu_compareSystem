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
		logger.info("��ȡҵ����Ϣ");
		BaseReturn baseReturn = new BaseReturn();
		baseReturn.setInfo("�ɹ�");
		try {
			// 1,У��汾�Ƿ�Ϊ��;�жϰ汾���Ƿ�����õİ汾��һ��
			if (CommonUtils.valiVersion(baseReturn, version)) {
				// 2,��У��
				if (result.hasErrors()) {
					CommonUtils.setInfo(baseReturn, false, CommonUtils.getError(result).toString());
				}
				// 3,��ѯҵ����Ϣ
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
			CommonUtils.setInfo(baseReturn, false, "����������");
		}
		return JSONObject.toJSONString(baseReturn);
	}
}

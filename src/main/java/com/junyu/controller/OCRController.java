package com.junyu.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import com.junyu.common.requestBean.OcrBean;
import com.junyu.common.returnBean.OcrReturn;
import com.junyu.service.OCRService;
import com.junyu.utils.CommonUtils;

@Controller
public class OCRController {

	private static Logger logger = LoggerFactory.getLogger(OCRController.class);

	@Autowired
	private OCRService ocrService;

	@RequestMapping(value = "ocr", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String orc(@RequestParam("version") String version, @Valid OcrBean ocrBean, BindingResult result) {
		OcrReturn ocrReturn = new OcrReturn();
		/*try {
			ocrBean.setId_photo(URLEncoder.encode(ocrBean.getId_photo(), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		logger.info("用户进行ocr识别");
		try {
			// 1,校验版本是否为空;判断版本号是否和内置的版本号一致
			if (CommonUtils.valiVersion(ocrReturn, version)) {
				// 2,表单校验
				if (result.hasErrors()) {
					CommonUtils.setInfo(ocrReturn, false, CommonUtils.getError(result).toString());
				}
				// 3,非市中心用户条形码不能为空
				if (!"000".equals(ocrBean.getType_guid()) && StringUtils.isBlank(ocrBean.getBar_code())) {
					CommonUtils.setInfo(ocrReturn, false, "条形码不能为空");
				}
				if (ocrReturn.getSuccess()) {
					// 4,调用服务 保存记录
					this.ocrService.saveOcrLog(ocrBean, this.ocrService.ocr(ocrBean, ocrReturn));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(ocrReturn, false, "服务器出错");
		}
		return JSONObject.toJSONString(ocrReturn);
	}
}

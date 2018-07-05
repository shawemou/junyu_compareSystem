package com.junyu.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
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
import com.junyu.common.requestBean.CacheListBean;
import com.junyu.common.returnBean.CompareReturn;
import com.junyu.service.CacheService;
import com.junyu.service.CompareService;
import com.junyu.utils.CommonUtils;

@Controller
public class CatchListController {

	private static Logger logger = LoggerFactory.getLogger(CatchListController.class);

	@Autowired
	private CompareService compareService;

	@Autowired
	private CacheService cacheService;

	@RequestMapping(value = "cacheList", method = { RequestMethod.POST, RequestMethod.GET },produces="application/json;charset=gbk")
	@ResponseBody
	public String catchToCompare(@RequestParam("version") String version, @Valid CacheListBean cacheListBean, BindingResult result) {
		logger.info("操作两照比对数据");
		CompareReturn cr = new CompareReturn();
		try {
			// 1,校验version
			if (CommonUtils.valiVersion(cr, version)) {
				// 2,表单校验
				if (result.hasErrors()) {
					CommonUtils.setInfo(cr, false, CommonUtils.getError(result).toString());
				}
				if (cr.getSuccess()) {
					// 3,对请求方法进行操作
					if (StringUtils.equals("list", cacheListBean.getMethod())) {
						logger.info("获取列表");
						if (this.isNullOfBarCord(cacheListBean.getBar_code(), cr).getSuccess()) {
							HashMap<String, Object> dbInfo = new HashMap<String, Object>();
							dbInfo.put("cacheList", this.cacheService.getCachList(cacheListBean));
							cr.setDbInfo(dbInfo);
						}
					} else if (StringUtils.equals("delete", cacheListBean.getMethod())) {
						logger.info("删除暂存");
						if (CollectionUtils.isEmpty(cacheListBean.getData())) {
							CommonUtils.setInfo(cr, false, "请选择要删除的数据");
						}
						if (cr.getSuccess()) {
							this.cacheService.deleteByGuids(cacheListBean.getData(), "guid");
						}
					} else if (StringUtils.equals("done", cacheListBean.getMethod())) {
						logger.info("暂存转正式");
						if (this.isNullOfBarCord(cacheListBean.getBar_code(), cr).getSuccess()) {
							this.compareService.fromCacheToCompare(cacheListBean);
						}
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

	public CompareReturn isNullOfBarCord(String bar_cord, CompareReturn cr) {
		if (StringUtils.isBlank(bar_cord)) {
			CommonUtils.setInfo(cr, false, "条形码不能为空");
		}
		return cr;
	}
}

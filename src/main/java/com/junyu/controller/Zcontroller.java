package com.junyu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.junyu.common.requestBean.CacheListBean;
import com.junyu.pojo.Z;
import com.junyu.service.CompareService;
import com.junyu.service.Zservice;
import com.junyu.utils.Base64ImgUtil;

@Controller
public class Zcontroller {
	
	@Autowired
	private Zservice zservice;
	
	@Autowired
	private CompareService comService;
	
	@RequestMapping(method={RequestMethod.GET,RequestMethod.POST},value="z")
	public void inseret(@RequestParam("photo")String photo){
		byte[] p = Base64ImgUtil.fromBase64(photo);
		Z z = new Z();
		z.setPhoto(p);
		System.out.println("a");
		zservice.save(z);
	}
	
	@RequestMapping(method={RequestMethod.GET,RequestMethod.POST},value="com")
	public void inseret(){
		CacheListBean cacheListBean = new CacheListBean();
		cacheListBean.setBar_code("q");
		this.comService.fromCacheToCompare(cacheListBean);
	}
}

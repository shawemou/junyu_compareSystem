package com.junyu.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.custle.sdk.Log4jUtil;
import com.custle.sdk.TestIDPhotoAuthService;
import com.junyu.common.requestBean.CompareBean1;
import com.junyu.common.requestBean.CompareBean2;
import com.junyu.common.requestBean.WebMobileBean;
import com.junyu.common.requestBean.WebStatBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.common.returnBean.Page;
import com.junyu.pojo.Admin;
import com.junyu.pojo.Compare;
import com.junyu.pojo.Rate;
import com.junyu.pojo.User;
import com.junyu.pojo.UserType;
import com.junyu.service.CompareService;
import com.junyu.service.UserService;
import com.junyu.service.UserTypeService;
import com.junyu.utils.AESCodec;
import com.junyu.utils.CommonUtils;
import com.junyu.utils.IPUtils;
import com.junyu.webservice.WebserviceImpl;

@Controller
@RequestMapping("web")
public class WebUserController {

	private static Logger logger = LoggerFactory.getLogger(WebLoginController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CompareService compareService;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private HttpServletRequest request;

	// 1,获取用户信息
	@RequestMapping(value = "webDateList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Page<User> wenLogin(WebMobileBean webBean) {
		logger.info("获取所有用户信息");
		Page<User> page = new Page<User>();
		try {
			page = this.userService.query(page, webBean);
		} catch (Exception e) {
			logger.error("获取用户信息失败" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("获取信息失败");
		}

		return page;

	}

	@RequestMapping(value = "webDateList1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String, Object> webDateList1(WebMobileBean webBean) {
		logger.info("获取所有用户信息");
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page<User> page = new Page<User>();
		try {
			page = this.userService.query(page, webBean);
			map.put("aaData", page.getData());
			map.put("iTotalRecords", page.getItemCount());
			map.put("iTotalDisplayRecords", page.getItemCount());
			return map;
		} catch (Exception e) {
			logger.error("获取用户信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	// 2,获取单个配置
	@RequestMapping(value = "loadMobile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public User getOneUser(WebMobileBean webBean) {
		logger.info("获取单个信息");
		try {
			User user = this.userService.queryById(webBean.getGuid());
			UserType userType = this.userTypeService.queryById(user.getTypeGuid());
			user.setTypeName(userType.getName());
			return user;
		} catch (Exception e) {
			logger.error("获取单个用户信息失败" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// 3,更改可用状态
	@RequestMapping(value = "updateBusable", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn updateBusable(WebMobileBean webBean) {
		logger.info("更改可用状态");
		BaseReturn br = new BaseReturn();
		try {
			User user = this.userService.queryById(webBean.getGuid());
			User record = new User();
			record.setGuid(webBean.getGuid());
			record.setBusable("1".equals(user.getBusable()) ? "2" : "1");
			this.userService.updateSelective(record);
			CommonUtils.setInfo(br, true, "1".equals(user.getBusable()) ? "2" : "1");
		} catch (Exception e) {
			logger.error("更改状态失败" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "更改状态失败");
		}
		return br;
	}

	// 4,密码重置
	@RequestMapping(value = "updatePwd", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn reSetPassword(WebMobileBean webBean) {
		logger.info("重置密码");
		BaseReturn br = new BaseReturn();
		try {
			User record = new User();
			record.setGuid(webBean.getGuid());
			record.setPassword(AESCodec.aesEncrypt("111111"));
			record.setSecretType("1");
			this.userService.updateSelective(record);
			CommonUtils.setInfo(br, true, "重置密码成功");
		} catch (Exception e) {
			logger.error("重置密码失败" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "重置密码失败");
		}

		return br;
	}

	// 5,change事件获取user
	@RequestMapping(value = "validataUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn validataUser(User record) {
		logger.info("校验修改或者注册用户的手机号和用户是否存在");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			if (record.getLoginName() != null && record.getLoginName().matches("[0-9]+")) {
				CommonUtils.setInfo(br, false, "登录名不能为纯数字");
			} else if (record.getMobile() != null && record.getMobile().length() > 11) {
				CommonUtils.setInfo(br, false, "手机号不能大于11位");
			}
			if (br.getSuccess()) {
				User user = this.userService.queryOne(record);
				if (user == null) {
					CommonUtils.setInfo(br, false, "此用户已经存在请重新输入");
				}
			}
		} catch (Exception e) {
			logger.error("校验" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "系统出错");
		}
		return br;
	}

	// 6,修改
	@RequestMapping(value = "updateUser", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn updateUser(@Valid User record, @RequestParam("parenttype") String parenttype, BindingResult result) {
		logger.info("修改用户");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			// 1,表单校验
			if (result.hasErrors()) {
				CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				return br;
			}
			if (!record.getMobile().matches("[0-9]+")) {
				CommonUtils.setInfo(br, false, "手机号存在非法字符");
				return br;
			}
			// 2,保存user
			this.userService.updateUser(br, record, parenttype);
		} catch (Exception e) {
			logger.error("更新失败" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "系统出错");
		}
		return br;
	}

	// 7,新增saveMobile
	@RequestMapping(value = "saveMobile", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn saveMobile(@Valid User record, @RequestParam("parenttype") String parenttype, BindingResult result) {
		logger.info("保存用户");
		BaseReturn br = new BaseReturn();
		br.setSuccess(true);
		try {
			// 1,表单校验
			if (result.hasErrors()) {
				CommonUtils.setInfo(br, false, CommonUtils.getError(result).toString());
				return br;
			}
			if (!record.getMobile().matches("[0-9]+")) {
				CommonUtils.setInfo(br, false, "手机号存在非法字符");
				return br;
			}
			// 2,保存user
			Admin admin = (Admin) request.getSession().getAttribute("user");
			record.setUserGuid(admin.getGuid());
			this.userService.addUser(br, record, parenttype);

		} catch (Exception e) {
			logger.error("更新失败" + e.getMessage());
			e.printStackTrace();
			CommonUtils.setInfo(br, false, "系统出错");
		}
		return br;
	}

	// 8,统计查询
	@RequestMapping(value = "completeList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Page<Compare> completeList(WebStatBean statBean) {
		logger.info("统计查询");
		Page<Compare> page = new Page<Compare>();
		try {
			page = this.compareService.query(page, statBean);
		} catch (Exception e) {
			logger.error("获取统计信息失败" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("获取信息失败");
		}
		return page;
	}
	
	@RequestMapping(value = "completeList1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> completeList1(WebStatBean statBean) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("统计查询");
		Page<Compare> page = new Page<Compare>();
		try {
			page = this.compareService.query(page, statBean);
			map.put("aaData", page.getData());
			map.put("iTotalRecords", page.getItemCount());
			map.put("iTotalDisplayRecords", page.getItemCount());
			return map;
		} catch (Exception e) {
			logger.error("获取统计信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	// 9,人证一致使用率统计
	@RequestMapping(value = "rateList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Page<Rate> rateList(WebStatBean statBean) {
		logger.info("统计查询");
		Page<Rate> page = new Page<Rate>();
		try {
			page = this.compareService.queryRate(page, statBean);
		} catch (Exception e) {
			logger.error("获取统计信息失败" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("获取信息失败");
		}
		return page;

	}
	
	
	@RequestMapping(value = "rateList1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> rateList1(WebStatBean statBean) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("银行使用率统计");
		Page<Rate> page = new Page<Rate>();
		try {
			page = this.compareService.queryRate(page, statBean);
			map.put("aaData", page.getData());
			map.put("iTotalRecords", page.getItemCount());
			map.put("iTotalDisplayRecords", page.getItemCount());
			return map;
		} catch (Exception e) {
			logger.error("获取统计信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return map;

	}

	// 10, 获取所有的用户类型
	@RequestMapping(value = "getBanKList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn getBanKList() {
		logger.info("获取所有的用户类型");
		BaseReturn br = new BaseReturn();
		new Page<UserType>();
		try {
			HashMap<String, Object> dbInfo = new HashMap<String, Object>();
			dbInfo.put("typeList", this.userTypeService.queryAll());
			br.setDbInfo(dbInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "服务器出错");
			br.setDbInfo(null);
		}
		return br;
	}

	// 11, 获取所用用户
	@RequestMapping(value = "getUserList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseReturn getUserList() {
		logger.info("获取所用用户");
		BaseReturn br = new BaseReturn();
		new Page<UserType>();
		try {
			HashMap<String, Object> dbInfo = new HashMap<String, Object>();
			dbInfo.put("userList", this.userService.queryAll());
			br.setDbInfo(dbInfo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			CommonUtils.setInfo(br, false, "服务器出错");
			br.setDbInfo(null);
		}
		return br;
	}

	// 12,获取离线用户
	@RequestMapping(value = "offlineList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Page<User> offlineList(WebStatBean statBean, @RequestParam(value = "offlineTime", required = false, defaultValue = "3") String offlineTime) {
		logger.info("获取离线用户");
		Page<User> page = new Page<User>();
		try {
			page = this.userService.queryOffline(page, statBean, offlineTime);
		} catch (Exception e) {
			logger.error("获取用户信息失败" + e.getMessage());
			e.printStackTrace();
			page.setSuccess(false);
			page.setData(null);
			page.setInfo("获取信息失败");
		}
		return page;
	}
	
	@RequestMapping(value = "offlineList1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> offlineList1(WebStatBean statBean, @RequestParam(value = "offlineTime", required = false, defaultValue = "3") String offlineTime) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.info("获取离线用户");
		Page<User> page = new Page<User>();
		try {
			page = this.userService.queryOffline(page, statBean, offlineTime);
			map.put("aaData", page.getData());
			map.put("iTotalRecords", page.getItemCount());
			map.put("iTotalDisplayRecords", page.getItemCount());
			return map;
		} catch (Exception e) {
			logger.error("获取离线信息失败" + e.getMessage());
			e.printStackTrace();
		}
		return map;
	}
	
	
	@Autowired
	private IPUtils ipUtils;
	
	@RequestMapping(value = "desk", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String deskRequest(@RequestParam("jsonString")String jsonString) {
 		WebserviceImpl webService = new WebserviceImpl();
		String result = webService.method(jsonString);
		return  result ;
	}
	
	@RequestMapping(value = "desk1", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String deskRequest1(@RequestParam(value="strIDPhoto",required=false) String strIDPhoto, @RequestParam(value="type",required=false) String type ,
							   @RequestParam(value="loginName",required=false) String loginName, @RequestParam(value="name",required=false) String  name,
							   @RequestParam(value="certid",required=false) String certid, @RequestParam(value="vaild_priod",required=false) String vaild_priod ,
							   @RequestParam(value="strUserPhoto") String strUserPhoto, @RequestParam(value="strChipPhoto",required=false) String  strChipPhoto,
							   @RequestParam(value="sex",required=false) String sex, @RequestParam(value="birthday",required=false) String  birthday,
							   @RequestParam(value="fork",required=false) String fork, @RequestParam(value="address",required=false) String  address,
							   @RequestParam(value="issue_authority",required=false) String issue_authority, @RequestParam(value="compareName",required=false) String compareName ,
							   @RequestParam(value="barCode",required=false)String barCode) {
		String jsonString = JSONObject.toJSONString(null);
		WebserviceImpl webService = new WebserviceImpl();
		String result = webService.method(jsonString);
		return  result ;
	} 
	
	@RequestMapping(value = "desk2", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String deskRequest2(CompareBean1 compareBean1) {
 		String jsonString = JSONObject.toJSONString(compareBean1);
		WebserviceImpl webService = new WebserviceImpl();
		String result = webService.method(jsonString);
		return  result ;
	}
	
	@RequestMapping(value = "desk3", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public String deskRequest3(CompareBean2 compareBean2) {
		String jsonString = JSONObject.toJSONString(compareBean2);
		WebserviceImpl webService = new WebserviceImpl();
		String result = webService.method(jsonString);
		return  result ;
	}
}

package com.junyu.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.junyu.common.requestBean.LoginBean;
import com.junyu.common.requestBean.PasswordBean;
import com.junyu.common.requestBean.VisitInfoBean;
import com.junyu.common.requestBean.WebMobileBean;
import com.junyu.common.requestBean.WebStatBean;
import com.junyu.common.returnBean.BaseReturn;
import com.junyu.common.returnBean.EnumInstance.EReturnCompare;
import com.junyu.common.returnBean.EnumInstance.EReturnLogin;
import com.junyu.common.returnBean.Page;
import com.junyu.common.returnBean.ReturnBean;
import com.junyu.config.ReadingSetting;
import com.junyu.mapper.ServiceDicMapper;
import com.junyu.mapper.UserLoginLogMapper;
import com.junyu.mapper.UserMapper;
import com.junyu.mapper.UserTypeMapper;
import com.junyu.pojo.ServiceDic;
import com.junyu.pojo.User;
import com.junyu.pojo.UserType;
import com.junyu.utils.AESCodec;
import com.junyu.utils.CommonUtils;
import com.junyu.utils.JYMD5;

@Service
public class UserService extends BaseService<User> {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private ReadingSetting readingSetting;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserLoginLogMapper loginLogMapper;

	@Autowired
	private UserTypeMapper userTypeMapper;

	@Autowired
	private ServiceDicMapper serviceDicMapper;

	/**
	 * 1,登录校验
	 * 
	 * @Title: login
	 * @Description: TODO
	 * @param @param loginBean
	 * @param @param br
	 * @param @return
	 * @return BaseReturn
	 * @throws
	 */
	public BaseReturn login(LoginBean loginBean, BaseReturn br) {
		// 1,查询MOBILE 返回 密码 和 busable
		Example example = new Example(User.class);
		Criteria criteria1 = example.createCriteria();
		criteria1.andEqualTo("loginName", loginBean.getLoginName());
		Criteria criteria2 = example.createCriteria();
		criteria2.andEqualTo("mobile", loginBean.getLoginName());
		example.or(criteria2);
		List<User> users = this.userMapper.selectByExample(example);

		// 2,没有查到与手机号一样的注册信息,并校验密码
		if (users.size() == 0 || users.get(0) == null) {
			br.setSuccess(false);
			br.setInfo("未发现与本手机号相关联的配置");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return br;
		} else {
			return this.validate(users.get(0), loginBean, br);
		}
	}

	/**
	 * 2,修改密码
	 * 
	 * @Title: passwordModify
	 * @Description: TODO
	 * @param @param passwordBean
	 * @param @param br
	 * @return void
	 * @throws
	 */
	public void passwordModify(PasswordBean passwordBean, BaseReturn br) {
		// 1,校验用户是否存在,可用
		Example example = new Example(User.class);
		Criteria criteria1 = example.createCriteria();
		criteria1.andEqualTo("loginName", passwordBean.getLoginName());
		Criteria criteria2 = example.createCriteria();
		criteria2.andEqualTo("mobile", passwordBean.getLoginName());
		example.or(criteria2);
		List<User> users = this.userMapper.selectByExample(example);

		if (users.size() == 0) {
			br.setSuccess(false);
			br.setInfo("未找到对应的客户");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return;
		}

		User user = users.get(0);
		br = this.validate(user, passwordBean, br);
		// 2,修改密码
		if (br.getSuccess()) {
			user.setPassword(AESCodec.aesEncrypt(passwordBean.getNew_secret_key()));
			user.setSecretType("2");
			this.updateSelective(user);
		}
	}

	public BaseReturn validate(User user, LoginBean loginBean, BaseReturn br) {
		if (user == null) {
			br.setSuccess(false);
			br.setInfo("未找到对应的客户");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return br;
		}

		if (user.getBusable() != null && user.getBusable().toString().equals("2")) {
			br.setSuccess(false);
			br.setInfo("与本手机号相关联的配置已停用");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Busable);
			return br;
		}

		if (user.getPassword() != null) {
			if (loginBean.getSecret_key() == null) {
				br.setSuccess(false);
				br.setInfo("密码不正确");
				br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
				return br;
			}
			String password = AESCodec.aesDecrypt(user.getPassword());
			// TYPE为1表示ios系统登录
			if (loginBean.getType() != null && loginBean.getType().toString().equals("1")) {

				String secret_key = AESCodec.aesDecrypt(user.getPassword().toString());
				if (!JYMD5.MD5Encoder(secret_key, "UTF-8").equalsIgnoreCase(loginBean.getSecret_key())) {
					br.setSuccess(false);
					br.setInfo("密码验证不通过");
					return br;
				}
				/*
				 * if (!JYMD5.MD5Encoder(password,
				 * "UTF-8").equalsIgnoreCase(JYMD5
				 * .MD5Encoder(loginBean.getSecret_key().toString(), "UTF-8")))
				 * { System.out.println(loginBean.getSecret_key().toString());
				 * br.setSuccess(false); br.setInfo("密码验证不通过");
				 * br.setCode(EReturnLogin.RT_NotMatch_Format_Login); return br;
				 * }
				 */

			} else {
				// type不是1或者为null判定为安卓手机登录
				if (!password.equals(loginBean.getSecret_key()) && !StringUtils.equals(user.getPassword(), loginBean.getSecret_key())) {
					System.out.println(JYMD5.MD5Encoder(loginBean.getSecret_key().toString(), "UTF-8"));
					br.setSuccess(false);
					br.setInfo("密码验证不通过");
					br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
					return br;
				}
			}
		}
		HashMap<String, Object> dbInfo = new HashMap<String, Object>();
		dbInfo.put("user", user);
		br.setDbInfo(dbInfo);
		br.setCode(EReturnLogin.RT_Success);
		return br;
	}

	/**
	 * 3,网页分页查询
	 * 
	 * @Title: query
	 * @Description: TODO
	 * @param @param page
	 * @param @param webBean
	 * @param @return
	 * @return Page<User>
	 * @throws
	 */
	public Page<User> query(Page<User> page, WebMobileBean webBean) {
		int startNum = NumberUtils.toInt(webBean.getStart(), 0);
		int pageSize = NumberUtils.toInt(webBean.getLength(), 10);
		int startPage = startNum / pageSize + 1;

		PageHelper.startPage(startPage, pageSize);
		Example example = new Example(User.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNoneBlank(webBean.getBusable())) {
			criteria.andEqualTo("busable", webBean.getBusable());
		}
		if (StringUtils.isNoneBlank(webBean.getMobile())) {
			criteria.andEqualTo("mobile", webBean.getMobile());
		}
		if (StringUtils.isNoneBlank(webBean.getName())) {
			criteria.andEqualTo("name", webBean.getName());
		}
		if (StringUtils.isNoneBlank(webBean.getTypeGuid())) {
			criteria.andEqualTo("typeGuid", webBean.getTypeGuid());
		}
		PageInfo<User> pageInfo = new PageInfo<User>(this.userMapper.selectByExample(example));

		for (User user : pageInfo.getList()) {
			if (user.getSecretType() != null && user.getSecretType().equals("2")) {
				user.setPassword("机主已修改");
			} else {
				user.setPassword(AESCodec.aesDecrypt(user.getPassword()));
			}
		}
		page.setData(pageInfo.getList());
		page.setItemCount(pageInfo.getTotal() + "");
		page.setPage_no(pageInfo.getPageNum() + "");
		page.setSuccess(true);
		page.setInfo("成功");
		return page;
	}

	/**
	 * 4,更新用户
	 * 
	 * @Title: updateUser
	 * @Description: TODO
	 * @param @param br
	 * @param @param record
	 * @param @param parenttype
	 * @return void
	 * @throws
	 */
	public void updateUser(BaseReturn br, User record, String parenttype) {
		// 1,用户登录名如果发生了变化,用户名不能重复
		User user = this.queryById(record.getGuid());
		User newUser = new User();
		if (!StringUtils.equals(record.getLoginName(), user.getLoginName())) {
			newUser.setLoginName(record.getLoginName());
			List<User> UserList = this.queryListByWhere(newUser);
			if (!CollectionUtils.isEmpty(UserList)) {
				CommonUtils.setInfo(br, false, "该登录名已经被注册");
				return;
			}
		}
		// 2,测试手机号是否被注册
		if (!StringUtils.equals(record.getMobile(), user.getMobile())) {
			newUser.setLoginName(null);
			newUser.setMobile(record.getMobile());
			List<User> UserList = this.queryListByWhere(newUser);
			if (!CollectionUtils.isEmpty(UserList)) {
				CommonUtils.setInfo(br, false, "该手机号已经被注册");
				return;
			}

		}
		// 3,更新
		record.setUpdateTime(new Date());
		this.updateSelective(record);
	}

	/**
	 * 5,新增用户
	 * 
	 * @Title: addUser
	 * @Description: TODO
	 * @param @param br
	 * @param @param record
	 * @param @param parenttype
	 * @return void
	 * @throws
	 */
	public void addUser(BaseReturn br, User record, String parenttype) {
		// 1,登录名是否被注册
		User newUser = new User();
		newUser.setLoginName(record.getLoginName());
		List<User> UserList = this.queryListByWhere(newUser);
		if (!CollectionUtils.isEmpty(UserList)) {
			CommonUtils.setInfo(br, false, "该登录名已经被注册");
			return;
		}
		// 2,手机号是否被注册
		newUser.setLoginName(null);
		newUser.setMobile(record.getMobile());
		UserList = this.queryListByWhere(newUser);
		if (!CollectionUtils.isEmpty(UserList)) {
			CommonUtils.setInfo(br, false, "该手机号已经被注册");
			return;
		}
		// 4,保存
		record.setGuid(CommonUtils.getUUID());
		record.setPassword(AESCodec.aesEncrypt(CommonUtils.getUUID().substring(26)));
		this.saveSelective(record);
	}

	/**
	 * 6,获取离线用户
	 * 
	 * @Title: queryOffline
	 * @Description: TODO
	 * @param @param page
	 * @param @param webBean
	 * @param @return
	 * @return Page<User>
	 * @throws
	 */
	public Page<User> queryOffline(Page<User> page, WebStatBean webBean, String offlineTime) {
		// 1,获取在线列表
		List<Object> onlines = this.loginLogMapper.queryOnline(offlineTime);
		ArrayList<Object> list = new ArrayList<Object>();
		for (Object object : onlines) {
			if (object != null) {
				list.add(object);
			}
		}
		onlines = list;
		if (CollectionUtils.isEmpty(onlines)) {
			onlines.add(CommonUtils.getUUID());
		}

		int startNum = NumberUtils.toInt(webBean.getStart(), 0);
		int pageSize = NumberUtils.toInt(webBean.getLength(), 10);
		int startPage = startNum / pageSize + 1;

		PageHelper.startPage(startPage, pageSize);

		// 2,获取没有登录的user
		Example example = new Example(User.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		criteria.andNotIn("guid", onlines);
		criteria.andEqualTo("busable", 1);
		if (StringUtils.isNotBlank(webBean.getTypeGuid())) {
			criteria.andEqualTo("typeGuid", webBean.getTypeGuid());
		}
		PageInfo<User> pageInfo = new PageInfo<User>(this.userMapper.selectByExample(example));
		page.setData(pageInfo.getList());
		page.setItemCount(pageInfo.getTotal() + "");
		page.setPage_no(pageInfo.getPageNum() + "");
		page.setSuccess(true);
		page.setInfo("成功");
		return page;
	}

	/**
	 * 7,webService接口校验
	 * 
	 * @Title: validateUser
	 * @Description: TODO
	 * @param @param viBean
	 * @param @param bean
	 * @return void
	 * @throws
	 */
	public void validateUser(VisitInfoBean viBean, ReturnBean bean) {
		// 1,校验用户是否存在,可用
		Example example = new Example(User.class);
		Criteria criteria1 = example.createCriteria();
		criteria1.andEqualTo("loginName", viBean.getLoginLog().getLoginName());
		Criteria criteria2 = example.createCriteria();
		criteria2.andEqualTo("mobile", viBean.getLoginLog().getLoginName());
		example.or(criteria2);
		List<User> users = this.userMapper.selectByExample(example);

		if (users.size() == 0) {
			bean.setSuccess(false);
			bean.setInfo("未找到对应的客户");
			bean.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return;
		}

		User user = users.get(0);

		if (user == null) {
			bean.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			bean.setSuccess(false);
			bean.setInfo("登录名不正确");
		} else {
			viBean.setUser(user);
			if (user.getBusable() == null || user.getBusable().equals("2")) {
				bean.setCode(EReturnLogin.RT_NotMatch_Format_Busable);
				bean.setSuccess(false);
				bean.setInfo("登录名不可用");
			} else {
				bean.setSuccess(true);
			}
		}
	}

	/**
	 * 8,webService接口 返回用户和所有银行
	 * 
	 * @Title: getUserAndBanks
	 * @Description: TODO
	 * @param @param viBean
	 * @param @param bean
	 * @return void
	 * @throws
	 */
	public void getUserAndBanks(VisitInfoBean viBean, ReturnBean bean) {
		User user = viBean.getUser();
		String typeGuid = user.getTypeGuid();
		ArrayList<String> typeNameList = new ArrayList<String>();
		ArrayList<String> serviceNameList = new ArrayList<String>();
		// 1,如果是市中心用户
		if (StringUtils.equals(typeGuid, "000")) {
			bean.getDbInfo().put("role", user.getRole1());
			// 查询所有银行-->不能是 担保 和 中心
			Example example = new Example(User.class);
			Criteria criteria = example.createCriteria();
			ArrayList<Object> guidList = new ArrayList<Object>();
			guidList.add("000");
			guidList.add("999");
			criteria.andNotIn("guid", guidList);
			List<UserType> userTypes = this.userTypeMapper.selectByExample(example);
			for (UserType userType : userTypes) {
				typeNameList.add(userType.getName());
			}
			// 1.1如果用户角色1是0 -->有代理权限(合同号必须填),也有无合同号填写 --->返回所有银行类型 和 业务类型
			if (StringUtils.equals("0", user.getRole1())) {
				// 查询业务类型名称,遍历并返回
				List<ServiceDic> serviceList = this.serviceDicMapper.select(null);
				for (ServiceDic serviceDic : serviceList) {
					serviceNameList.add(serviceDic.getName());
				}
			} 
			// 1.2如果用户角色1是1 --->有代理权限(合同号必须填),---> 返回所有银行类型
		}// 2,如果是非中心用户
		
		String userType = this.userTypeMapper.selectByPrimaryKey(typeGuid).getName();
		
		bean.getDbInfo().put("userType", userType);
		bean.getDbInfo().put("typeNameList", typeNameList);
		bean.getDbInfo().put("serviceNameList", serviceNameList);
		bean.setCode("0");
	}
}

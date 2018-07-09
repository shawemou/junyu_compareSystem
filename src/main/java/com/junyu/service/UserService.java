package com.junyu.service;

import java.util.ArrayList;
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
import com.junyu.mapper.UserLoginLogMapper;
import com.junyu.mapper.UserMapper;
import com.junyu.pojo.User;
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

	/**
	 * 1,��¼У��
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
		// 1,��ѯMOBILE ���� ���� �� busable
		User record = new User();
		record.setMobile(loginBean.getMobile());
		record.setLoginName(loginBean.getLoginName());
		User user = this.queryOne(record);

		// 2,û�в鵽���ֻ���һ����ע����Ϣ,��У������
		if (user == null) {
			br.setSuccess(false);
			br.setInfo("δ�����뱾�ֻ��������������");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return br;
		} else {
			return this.validate(user, loginBean, br);
		}
	}

	/**
	 * 2,�޸�����
	 * 
	 * @Title: passwordModify
	 * @Description: TODO
	 * @param @param passwordBean
	 * @param @param br
	 * @return void
	 * @throws
	 */
	public void passwordModify(PasswordBean passwordBean, BaseReturn br) {
		// 1,У���û��Ƿ����,����
		User record = new User();
		if(StringUtils.isNotBlank(passwordBean.getLoginName())){
			record.setLoginName(passwordBean.getLoginName());
		}
		if(StringUtils.isNotBlank(passwordBean.getMobile())){
			record.setMobile(passwordBean.getMobile());
		}
		User user = this.queryOne(record);
		br = this.validate(user, passwordBean, br);
		// 2,�޸�����
		if(br.getSuccess()){
			user.setPassword(AESCodec.aesEncrypt(passwordBean.getNew_secret_key()));
			user.setSecretType("2");
			this.updateSelective(user);
		}
	}

	public BaseReturn validate(User user, LoginBean loginBean, BaseReturn br) {
		if (user == null) {
			br.setSuccess(false);
			br.setInfo("δ�ҵ���Ӧ�Ŀͻ�");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
			return br;
		}

		if (user.getBusable() != null && user.getBusable().toString().equals("0")) {
			br.setSuccess(false);
			br.setInfo("�뱾�ֻ����������������ͣ��");
			br.setCode(EReturnLogin.RT_NotMatch_Format_Busable);
			return br;
		}

		if (user.getPassword() != null) {
			if (loginBean.getSecret_key() == null) {
				br.setSuccess(false);
				br.setInfo("���벻��ȷ");
				br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
				return br;
			}
			String password = AESCodec.aesDecrypt(user.getPassword());
			// TYPEΪ1��ʾiosϵͳ��¼
			if (loginBean.getType() != null && loginBean.getType().toString().equals("1")) {
				
				String secret_key = AESCodec.aesDecrypt(user.getPassword().toString());
				if( !JYMD5.MD5Encoder(secret_key,"UTF-8").equalsIgnoreCase(loginBean.getSecret_key())){
					br.setSuccess(false);
					br.setInfo("������֤��ͨ��");
					return br;
				}
				/*if (!JYMD5.MD5Encoder(password, "UTF-8").equalsIgnoreCase(JYMD5.MD5Encoder(loginBean.getSecret_key().toString(), "UTF-8"))) {
					System.out.println(loginBean.getSecret_key().toString());
					br.setSuccess(false);
					br.setInfo("������֤��ͨ��");
					br.setCode(EReturnLogin.RT_NotMatch_Format_Login);
					return br;
				}*/

			} else {
				// type����1����Ϊnull�ж�Ϊ��׿�ֻ���¼
				if (!password.equals(loginBean.getSecret_key())&&!StringUtils.equals(user.getPassword(), loginBean.getSecret_key())) {
					System.out.println(JYMD5.MD5Encoder(loginBean.getSecret_key().toString(), "UTF-8"));
					br.setSuccess(false);
					br.setInfo("������֤��ͨ��");
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
	 * 3,��ҳ��ҳ��ѯ
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

		PageHelper.startPage(NumberUtils.toInt(webBean.getPage_no(), 1), NumberUtils.toInt(webBean.getPage_count(), 10));
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
		if (StringUtils.isNoneBlank(webBean.getParenttype())) {
			criteria.andLike("typeGuid", webBean.getParenttype() + "%");
		}
		PageInfo<User> pageInfo = new PageInfo<User>(this.userMapper.selectByExample(example));

		for (User user : pageInfo.getList()) {
			if (user.getSecretType() != null && user.getSecretType().equals("2")) {
				user.setPassword("�������޸�");
			} else {
				user.setPassword(AESCodec.aesDecrypt(user.getPassword()));
			}
		}
		page.setData(pageInfo.getList());
		page.setItemCount(pageInfo.getTotal() + "");
		page.setPage_no(pageInfo.getPageNum() + "");
		page.setSuccess(true);
		page.setInfo("�ɹ�");
		return page;
	}

	/**
	 * 4,�����û�
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
		// 1,�û���¼����������˱仯,����Ϊ11λ����,�û��������ظ�
		User user = this.queryById(record.getGuid());
		User newUser = new User();
		if (!StringUtils.equals(record.getLoginName(), user.getLoginName())) {
			if (record.getLoginName().matches("\\d{11}")) {
				CommonUtils.setInfo(br, false, "��¼������Ϊ11λ����");
				return;
			}
			newUser.setLoginName(record.getLoginName());
			List<User> UserList = this.queryListByWhere(newUser);
			if (!CollectionUtils.isEmpty(UserList)) {
				CommonUtils.setInfo(br, false, "�õ�¼���Ѿ���ע��");
				return;
			}
		}
		// 2,�����ֻ����Ƿ񱻵�¼
		if (!StringUtils.equals(record.getMobile(), user.getMobile())) {
			newUser.setLoginName(null);
			newUser.setMobile(record.getMobile());
			List<User> UserList = this.queryListByWhere(newUser);
			if (!CollectionUtils.isEmpty(UserList)) {
				CommonUtils.setInfo(br, false, "���ֻ����Ѿ���ע��");
				return;
			}

		}
		// 3,����
		if (parenttype != null && parenttype.length() != 2) {
			record.setTypeGuid(parenttype);
		}
		record.setUpdateTime(new Date());
		this.updateSelective(record);
	}

	/**
	 * 5,�����û�
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
		// 1.�û�������Ϊ11λ����
		if (record.getLoginName().matches("\\d{11}")) {
			CommonUtils.setInfo(br, false, "��¼������Ϊ11λ����");
			return;
		}
		// 2,��¼���Ƿ�ע��
		User newUser = new User();
		newUser.setLoginName(record.getLoginName());
		List<User> UserList = this.queryListByWhere(newUser);
		if (!CollectionUtils.isEmpty(UserList)) {
			CommonUtils.setInfo(br, false, "�õ�¼���Ѿ���ע��");
			return;
		}
		// 3,�ֻ����Ƿ�ע��
		newUser.setLoginName(null);
		newUser.setMobile(record.getMobile());
		UserList = this.queryListByWhere(newUser);
		if (!CollectionUtils.isEmpty(UserList)) {
			CommonUtils.setInfo(br, false, "���ֻ����Ѿ���ע��");
			return;
		}
		// 4,����
		if (parenttype != null && parenttype.length() != 2) {
			record.setTypeGuid(parenttype);
		}
		record.setGuid(CommonUtils.getUUID());
		record.setPassword(AESCodec.aesEncrypt(CommonUtils.getUUID().substring(26)));
		this.saveSelective(record);
	}

	/**
	 * 6,��ȡ�����û�
	 * 
	 * @Title: queryOffline
	 * @Description: TODO
	 * @param @param page
	 * @param @param webBean
	 * @param @return
	 * @return Page<User>
	 * @throws
	 */
	public Page<User> queryOffline(Page<User> page, WebStatBean webBean,String offlineTime) {
		// 1,��ȡ�����б�
		List<Object> onlines = this.loginLogMapper.queryOnline(offlineTime);
		ArrayList<Object> list = new ArrayList<Object>();
		for (Object object : onlines) {
			if(object!=null){
				list.add(object);
			}
		}
		onlines = list;
		PageHelper.startPage(NumberUtils.toInt(webBean.getPage_no(), 1), NumberUtils.toInt(webBean.getPage_count(), 10));

		// 2,��ȡû�е�¼��user
		Example example = new Example(User.class);
		example.setOrderByClause("createTime asc");
		Criteria criteria = example.createCriteria();
		criteria.andNotIn("guid", onlines);
		criteria.andEqualTo("busable", 1);
		PageInfo<User> pageInfo = new PageInfo<User>(this.userMapper.selectByExample(example));
		page.setData(pageInfo.getList());
		page.setItemCount(pageInfo.getTotal() + "");
		page.setPage_no(pageInfo.getPageNum() + "");
		page.setSuccess(true);
		page.setInfo("�ɹ�");
		return page;
	}

	/**
	 * 7,webService�ӿ�У��
	 * 
	 * @Title: validateUser
	 * @Description: TODO
	 * @param @param viBean
	 * @param @param bean
	 * @return void
	 * @throws
	 */
	public void validateUser(VisitInfoBean viBean, ReturnBean bean) {
		User record = new User();
		record.setLoginName(viBean.getLoginLog().getLoginName());
		User user = this.queryOne(record);
		if (user == null) {
			bean.setCode(EReturnCompare.RT_NotMatch_Format_Login);
			bean.setSuccess(false);
			bean.setInfo("��¼������ȷ");
		} else {
			viBean.setUser(user);
			if (user.getBusable() == null || user.getBusable().equals("2")) {
				bean.setCode(EReturnCompare.RT_NotMatch_Format_Busable);
				bean.setSuccess(false);
				bean.setInfo("��¼��������");
			}else{
				bean.setSuccess(true);
			}
		}
	}
}

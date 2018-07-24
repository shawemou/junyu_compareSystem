package com.junyu.common.requestBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.codehaus.xfire.transport.http.XFireServletController;

import com.junyu.pojo.Compare;
import com.junyu.pojo.ComparePhoto;
import com.junyu.pojo.User;
import com.junyu.pojo.UserLoginLog;
import com.junyu.utils.CommonUtils;
import com.junyu.utils.SpringBeanUtils;

public class VisitInfoBean {

	private String guid;
	private String type;
	private String ip;// @Todo ¼ÓºÏÍ¬ºÅ
	public Date beginDate = new Date();

	private LoginBean loginBean;
	private PasswordBean modifyPwdBean;
	private CompareBean compareBean = new CompareBean();

	private Compare compare;

	private ComparePhoto comparePhoto;

	private UserLoginLog loginLog;

	private Map<String, Object> userMap = new HashMap<String, Object>();

	private User user;

	public VisitInfoBean() {

		//Message message = PhaseInterceptorChain.getCurrentMessage();
		//HttpServletRequest httprequest = (HttpServletRequest)SpringBeanUtils.getBean(HttpServletRequest.class);
				//message.get(AbstractHTTPDestination.HTTP_REQUEST);
		 
		this.guid = CommonUtils.getUUID();
		//this.ip = httprequest.getRemoteAddr();

	}

	public Compare getCompare() {
		return compare;
	}

	public void setCompare(Compare compare) {
		this.compare = compare;
	}

	public ComparePhoto getComparePhoto() {
		return comparePhoto;
	}

	public void setComparePhoto(ComparePhoto comparePhoto) {
		this.comparePhoto = comparePhoto;
	}

	public UserLoginLog getLoginLog() {
		return loginLog;
	}

	public void setLoginLog(UserLoginLog loginLog) {
		this.loginLog = loginLog;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public PasswordBean getModifyPwdBean() {
		return modifyPwdBean;
	}

	public void setModifyPwdBean(PasswordBean modifyPwdBean) {
		this.modifyPwdBean = modifyPwdBean;
	}

	public CompareBean getCompareBean() {
		return compareBean;
	}

	public void setCompareBean(CompareBean compareBean) {
		this.compareBean = compareBean;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Map<String, Object> getUserMap() {
		return userMap;
	}

	public void setUserMap(Map<String, Object> userMap) {
		this.userMap = userMap;
	}
}

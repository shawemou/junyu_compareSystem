package com.junyu.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.junyu.pojo.User;
import com.junyu.service.UserService;
import com.junyu.utils.SpringBeanUtils;


public class AppFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 强制转换为与协议相关的对象
		HttpServletRequest req = (HttpServletRequest) request;
		String login = req.getRequestURL().toString().toUpperCase();
		boolean isGo = true;

		if (login.indexOf("WEB")<=0) {
			req = new MyRequest(req);
			if(login.indexOf("LOGIN.DO")<=0&&login.indexOf("PWDMODIFY.DO")<=0){
				String userGuid = req.getParameter("user_guid");
				UserService userService = (UserService) SpringBeanUtils.getBean(UserService.class);
				User user = userService.queryById(userGuid);
				if(user.getBusable()=="2"){
					isGo=false;
					response.getOutputStream().write("{\"success\":\"false\"}".getBytes("GBK"));
				}
			}
		}
		
		if(isGo){
			// 放行
			chain.doFilter(req, response);
		}
	}

	@Override
	public void destroy() {

	}

	/**
	 * 重写需要增强的方法
	 */
	private class MyRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;
		private boolean encoded = false; // 是否已编码过

		public MyRequest(HttpServletRequest request) throws UnsupportedEncodingException {
			super(request);
			this.request = request;
		}

		public String getParameter(String name) {
			String value = super.getParameter(name);
			if(StringUtils.isNoneBlank(value)){
				try {
					value = new String(URLDecoder.decode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return value;
		}

		public String[] getParameterValues(String name) {
			String[] values = super.getParameterValues(name);
			if(ArrayUtils.isNotEmpty(values)){
				for (int i = 0; i < values.length; i++) {
					try {
						values[i] = new String(URLDecoder.decode(values[i], "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
			return values;
		}
	}

}

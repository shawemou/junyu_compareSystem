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


public class AppFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// ǿ��ת��Ϊ��Э����صĶ���
		HttpServletRequest req = (HttpServletRequest) request;
		String login = req.getRequestURL().toString().toUpperCase();

		if (login.indexOf("WEB")<=0) {
			req = new MyRequest(req);
		}
		// ����
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/**
	 * ��д��Ҫ��ǿ�ķ���
	 */
	private class MyRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;
		private boolean encoded = false; // �Ƿ��ѱ����

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
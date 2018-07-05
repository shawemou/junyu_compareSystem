package com.junyu.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter {

	public void destroy() {
	}

	// 对全局访问进行控制
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String login = req.getRequestURL().toString().toUpperCase();

		// if(login.indexOf("MOBILE.HTML") > 0){
		// chain.doFilter(request, response);
		// }else{
		if (login.indexOf("LOGIN.DO") <= 0 &&(login.indexOf("STAT.HTML") > 0||login.indexOf("RATE.HTML") > 0||login.indexOf("OFFLINE.HTML") > 0||login.indexOf("MOBILE.HTML") > 0 || login.indexOf("MOBILEADD.HTML") > 0 || login.indexOf("MOBILEUPDATE.HTML") > 0 || login.indexOf("WEB") > 0)) {

			HttpSession session = req.getSession(false);

			// 在这里等以后有URL重写来定位具体的用户名后，就不会简单的判断UserContextContainer对象在不在会话里了
			Object obj = (session == null ? null : session.getAttribute("user"));
			if (session != null && obj != null ) {
				chain.doFilter(request, response);
			} else {
				// 根据HTTP报文头判断是否AJAX请求
				String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
				if (requestType != null && "XMLHttpRequest".equals(requestType)) {
					PrintWriter printWriter = response.getWriter();
					printWriter.print("{\"sessionState\":0}");
					printWriter.flush();
					printWriter.close();
				} else {
					request.getRequestDispatcher("login.html").forward(request, response);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
		// }
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public static void main(String[] args) {
	}
}

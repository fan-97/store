package com.store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.serviceImp.UserServiceImpl;
import com.store.utils.CookUtils;

public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String serlvetPath = req.getServletPath();
		// 如果是登录页面直接放行
		if (serlvetPath.startsWith("/UserServlet")) {
			String method = req.getParameter("method");
			if ("loginUI".equals(method)) {
				chain.doFilter(req, resp);
				return;
			}
		}

		// 获取用户登录信息
		User user = (User) req.getSession().getAttribute("userLogin");
		if (user != null) {
			// 已经登录直接放行
			chain.doFilter(req, resp);
			return;
		}

		// 获取cookie
		Cookie userCookie = CookUtils.getCookieByName("autoLoginCookie", req.getCookies());

		if (null == userCookie) {
			chain.doFilter(req, resp);
			return;
		}

		String username = userCookie.getValue().split("#4412#")[0];
		String password = userCookie.getValue().split("#4412#")[1];

		UserService userService = new UserServiceImpl();
		User uu = new User();
		uu.setUsername(username);
		uu.setPassword(password);
		User user2 = null;
		try {
			user2 = userService.UserLogin(uu);
			if (user2 == null) {
				// 不存在该用户
				chain.doFilter(req, resp);
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 将用户信息存放到session中实现自动登录
		req.getSession().setAttribute("userLogin", user2);
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}

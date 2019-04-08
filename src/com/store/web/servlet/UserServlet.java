package com.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.IOFileFilter;

import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.serviceImp.UserServiceImpl;
import com.store.utils.MailUtils;
import com.store.utils.MyBeanUtils;
import com.store.utils.UUIDUtils;
import com.store.web.base.BaseServlet;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;

public class UserServlet extends BaseServlet {
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String[]> map = request.getParameterMap();
		UserService userService = new UserServiceImpl();
		User user = new User();
		MyBeanUtils.populate(user, map);
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		try {
			userService.userRegist(user);
			// 发送激活邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			request.setAttribute("msg", "注测成功，请前往激活");
		} catch (SQLException e) {
			request.setAttribute("msg", "注测失败，请重新注册");
			e.printStackTrace();
		} catch (AddressException e) {

			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取激活码
		try {
			String code = request.getParameter("code");
			// 激活用户
			if (null != code) {
				UserService userService = new UserServiceImpl();
				boolean flag = userService.userActive(code);
				if (flag) {
					request.setAttribute("msg", "用户激活成功，请登录");
					return "/jsp/login.jsp";
				} else {
					request.setAttribute("msg", "用户激活失败，请重新激活");
					return "/jsp/info.jsp";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	public String userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String[]> map = request.getParameterMap();
		User user = new User();
		MyBeanUtils.populate(user, map);
		UserService userService = new UserServiceImpl();
		try {
			User user02 = userService.UserLogin(user);
			// 登录成功
			request.getSession().setAttribute("userLogin", user02);
			// 1#自动登录 start
			String s = request.getParameter("autoLogin");
			if ("1".equals(s)) {
				// cookie保存用户名和密码
				Cookie cookie = new Cookie("autoLoginCookie", user02.getUsername() + "#4412#" + user02.getPassword());
				cookie.setMaxAge(7 * 24 * 60 * 60);// cookie存放7天
				cookie.setPath("/");
				response.addCookie(cookie);
			} else {
				// 删除cookie
				Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
				autoLoginCookie.setPath("/");
				autoLoginCookie.setMaxAge(0);
				response.addCookie(autoLoginCookie);
			}
			// 1#自动登录end

			// 2#记住我start
			String rememberme = request.getParameter("rememberme");
			if ("1".equals(rememberme)) {
				Cookie remembermeCookie = new Cookie("remembermeCookie", user02.getUsername());
				remembermeCookie.setMaxAge(60 * 60 * 7 * 24);
				remembermeCookie.setPath("/");
				response.addCookie(remembermeCookie);
			} else {
				Cookie remembermeCookie = new Cookie("remembermeCookie", "");
				remembermeCookie.setMaxAge(0);
				remembermeCookie.setPath("/");
				response.addCookie(remembermeCookie);
			}
			// 2#记住我end

			// 重定向到首页
			response.sendRedirect("/store_v1.0/index.jsp");
			return null;
		} catch (Exception e) {
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}

	public String logout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//清除session
		request.getSession().invalidate();
		// 删除cookie
		Cookie autoLoginCookie = new Cookie("autoLoginCookie", "");
		autoLoginCookie.setPath("/");
		autoLoginCookie.setMaxAge(0);
		response.addCookie(autoLoginCookie);
		response.sendRedirect("/store_v1.0/index.jsp");
		return null;
	}

	public void checkUsername(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		UserService userService = new UserServiceImpl();
		User user = null;
		try {
			user = userService.findByUserName(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (user != null) {
			// 用户名已被使用
			response.getWriter().println(1);
		} else {
			// 用户名未被使用
			response.getWriter().println(0);
		}
	}

}

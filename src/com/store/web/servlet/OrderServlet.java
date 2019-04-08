package com.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.OrderItem;
import com.store.domain.Orders;
import com.store.domain.PageModel;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.service.serviceImp.OrderServiceImpl;
import com.store.utils.UUIDUtils;
import com.store.web.base.BaseServlet;

public class OrderServlet extends BaseServlet {

	public String saveOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 检查用户是否登录
		User user = (User) request.getSession().getAttribute("userLogin");
		if (null == user) {
			request.setAttribute("msg", "请登录后使用");
			return "/jsp/info.jsp";
		}
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Orders orders = new Orders();
		orders.setUser(user);
		orders.setState(1);
		orders.setOid(UUIDUtils.getCode());
		orders.setTotal(cart.getTotal());
		orders.setOrderTime(new Date());
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemId(UUIDUtils.getCode());
			orderItem.setProduct(item.getProduct());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getSubTotal());
			orderItem.setOrder(orders);
			orders.getList().add(orderItem);
		}
		// 清空购物车
		cart.removeCart();
		// 调用业务层 保存订单信息
		OrderService orderService = new OrderServiceImpl();
		orderService.saveOrder(orders);
		request.setAttribute("orders", orders);
		return "/jsp/order_info.jsp";
	}

	public String findOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("userLogin");
		int num = Integer.parseInt(request.getParameter("num"));
		// 调用业务层功能：查询当前用户的订单信息并返回
		OrderService orderService = new OrderServiceImpl();
		try {
			PageModel<Orders> pm = orderService.findOrdersWithPage(user, num);
			request.setAttribute("page", pm);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/order_list.jsp";
	}

}

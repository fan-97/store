package com.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.serviceImp.ProductServiceImpl;
import com.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			String pid = request.getParameter("pid");
			int num = Integer.parseInt(request.getParameter("quantity"));

			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if (cart == null) {
				// 不存在session
				cart = new Cart();
				// 添加到session
				request.getSession().setAttribute("cart", cart);
			}
			ProductService productService = new ProductServiceImpl();
			// 获取product对象
			Product product = productService.findById(pid);
			CartItem cartItem = new CartItem();
			cartItem.setProduct(product);
			cartItem.setNum(num);
			cart.addCartItemToCartI(cartItem);
			response.sendRedirect("/store_v1.0/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String delteCartItem(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.removeCartItem(pid);
		response.sendRedirect("/store_v1.0/jsp/cart.jsp");
		return null;
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		cart.removeCart();
		response.sendRedirect("/store_v1.0/jsp/cart.jsp");
		return null;
	}
}

package com.store.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.PageModel;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.serviceImp.ProductServiceImpl;
import com.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String pid = request.getParameter("pid");
			ProductService productService = new ProductServiceImpl();
			Product product = productService.findById(pid);
			request.setAttribute("product", product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/product_info.jsp";
	}
	public String findByCid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		ProductService productService = new ProductServiceImpl();
		PageModel<Product> pm = productService.findByCidWithPage(cid,curNum);
		request.setAttribute("page", pm);
		return "/jsp/product_list.jsp";
	}
	
}

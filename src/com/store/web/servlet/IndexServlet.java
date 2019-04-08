package com.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.serviceImp.ProductServiceImpl;
import com.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {

	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			ProductService productService = new ProductServiceImpl();
			List<Product> hots = productService.findByHot();
			List<Product> news = productService.findByNew();
			request.setAttribute("hots", hots);
			request.setAttribute("news", news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}

}

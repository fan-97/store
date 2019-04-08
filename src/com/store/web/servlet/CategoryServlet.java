package com.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.serviceImp.CategoryServiceImpl;
import com.store.web.base.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	public String findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = null;
		try {
			list = categoryService.findAll();
			String ss = JSONArray.fromObject(list).toString();
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(ss);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}

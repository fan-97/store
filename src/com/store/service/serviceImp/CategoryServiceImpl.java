package com.store.service.serviceImp;

import java.sql.SQLException;
import java.util.List;

import com.store.dao.CategoryDao;
import com.store.dao.daoImp.CategoryDaoImpl;
import com.store.domain.Category;
import com.store.service.CategoryService;

public class CategoryServiceImpl implements CategoryService{
	
	CategoryDao dao = new CategoryDaoImpl();
	
	@Override
	public List<Category> findAll() throws SQLException {
		return dao.findAll();
	}
	
}

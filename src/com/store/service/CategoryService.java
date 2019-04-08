package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.domain.Category;

public interface CategoryService {

	List<Category> findAll() throws SQLException;
	
}

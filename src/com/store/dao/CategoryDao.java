package com.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.store.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws SQLException;

}

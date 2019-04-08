package com.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.store.domain.Product;

public interface ProductDao {

	List<Product> findByHot() throws SQLException;

	List<Product> findByNew() throws SQLException;

	Product findById(String pid) throws SQLException;

	int findTotalRecordsByCid(String cid)throws SQLException;

	List<Product> findAllByCid(String cid, int startIndex, int pageSize) throws SQLException;

	
}

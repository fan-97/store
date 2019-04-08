package com.store.service;

import java.sql.SQLException;
import java.util.List;

import com.store.domain.PageModel;
import com.store.domain.Product;

public interface ProductService {

	List<Product> findByHot() throws SQLException;

	List<Product> findByNew() throws SQLException;

	Product findById(String pid) throws SQLException;

	PageModel<Product> findByCidWithPage(String cid, int curNum);

}

package com.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.domain.OrderItem;
import com.store.domain.Orders;
import com.store.domain.User;

public interface OrderDao {

	void saveOrders(Connection conn,Orders orders) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem items) throws SQLException;

	int findTotalRecords(User user) throws SQLException;

	List<Orders> findAllOrders(User user, int startIndex, int pageSize) throws SQLException;
	
}

package com.store.service;

import java.sql.SQLException;

import com.store.domain.Orders;
import com.store.domain.PageModel;
import com.store.domain.User;

public interface OrderService {

	void saveOrder(Orders orders);

	PageModel<Orders> findOrdersWithPage(User user, int num) throws SQLException;

}

package com.store.service.serviceImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDao;
import com.store.dao.OrderDaoImpl;
import com.store.domain.OrderItem;
import com.store.domain.Orders;
import com.store.domain.PageModel;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void saveOrder(Orders orders) {
		Connection conn = null;
		try {
			// 获取连接
			conn = JDBCUtils.getConnection();
			// 开启事务
			conn.setAutoCommit(false);
			//保存订单
			OrderDao dao = new OrderDaoImpl();
			dao.saveOrders(conn,orders);
			//保存订单项
			for (OrderItem items:orders.getList()) {
				dao.saveOrderItem(conn,items);
			}
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	@Override
	public PageModel<Orders> findOrdersWithPage(User user, int num) throws SQLException {
		OrderDao dao = new OrderDaoImpl();
		//1.创建pageModel对象
		//查询总共的记录数
		int totalRecords = dao.findTotalRecords(user);
		PageModel<Orders> pm = new PageModel<>(num, 3, totalRecords);
		//2.关联集合
		List<Orders> list = dao.findAllOrders(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3.关联url
		pm.setUrl("OrderServlet?method=findOrders");
		return pm;
	}

}

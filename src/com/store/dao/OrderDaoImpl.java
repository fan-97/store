package com.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.domain.OrderItem;
import com.store.domain.Orders;
import com.store.domain.Product;
import com.store.domain.User;
import com.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrders(Connection conn, Orders orders) throws SQLException {
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, orders.getOid(), orders.getOrderTime(), orders.getTotal(), orders.getState(),
				orders.getAddress(), orders.getName(), orders.getTelephone(), orders.getUser().getUid());
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem items) throws SQLException {
		String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		runner.update(conn, sql, items.getItemId(), items.getQuantity(), items.getTotal(), items.getProduct().getPid(),
				items.getOrder().getOid());
	}

	@Override
	public int findTotalRecords(User user) throws SQLException {
		String sql = "select count(*) from orders where uid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) runner.query(sql, new ScalarHandler(),user.getUid());
		return num.intValue();
	}

	@Override
	public List<Orders> findAllOrders(User user, int startIndex, int pageSize) throws SQLException {
		// 查询所有订单
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=? limit ?,?";
		List<Orders> orders = runner.query(sql, new BeanListHandler<Orders>(Orders.class),user.getUid(),startIndex,pageSize);
		// 查询所有订单下的订单项
		for (Orders o : orders) {
			sql = "SELECT * FROM product p,orderitem o where p.pid=o.pid and o.oid=?";
			List<Map<String, Object>> maps = runner.query(sql, new MapListHandler(), o.getOid());
			for (Map<String, Object> map : maps) {
				try {
					// 封装商品信息和订单项信息
					Product product = new Product();
					BeanUtils.populate(product, map);
					OrderItem orderItem = new OrderItem();
					BeanUtils.populate(orderItem, map);
					//将商品信息装进订单项
					orderItem.setProduct(product);
					//将订单项添加到订单中
					o.getList().add(orderItem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return orders;
	}

}

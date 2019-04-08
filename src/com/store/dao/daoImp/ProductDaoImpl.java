package com.store.dao.daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.ProductDao;
import com.store.domain.Product;
import com.store.utils.JDBCUtils;

public class ProductDaoImpl implements ProductDao {

	@Override
	public List<Product> findByHot() throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query("select * from product where is_hot = 1 limit 0,9",
				new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findByNew() throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query("select * from product order by pdate desc limit 0,9",
				new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findById(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query("select * from product where pid = ?", new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public int findTotalRecordsByCid(String cid) throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from product where cid=?";
		Long num = (Long) runner.query(sql, new ScalarHandler(), cid);
		return num.intValue();
	}

	@Override
	public List<Product> findAllByCid(String cid, int startIndex, int pageSize) throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from product where cid=? and pflag=0 limit ?,?";
		return runner.query(sql, new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize); 
	}

}

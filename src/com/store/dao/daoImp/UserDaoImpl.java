package com.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.store.dao.UserDao;
import com.store.domain.User;
import com.store.utils.JDBCUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		runner.update("insert into user values (?,?,?,?,?,?,?,?,?,?)", params);
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql = "select * from user where code = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class), code);
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set username=?,password = ?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		runner.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getTelephone(),
				user.getBirthday(), user.getSex(), user.getState(), user.getCode(), user.getUid());
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
	}

	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query("select * from user where username=?", new BeanHandler<User>(User.class),username);
	}
}
package com.store.service.serviceImp;

import java.sql.SQLException;

import com.store.dao.UserDao;
import com.store.dao.daoImp.UserDaoImpl;
import com.store.domain.User;
import com.store.service.UserService;

public class UserServiceImpl implements UserService {

	@Override
	public void userRegist(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// 用户激活
		UserDao userDao = new UserDaoImpl();
		User user = userDao.userActive(code);
		if (null != user) {
			// 激活成功 修改状态
			user.setState(1);
			user.setCode(null);
			userDao.updateUser(user);
			return true;
		}
		return false;
	}

	@Override
	public User UserLogin(User user) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		User uu = userDao.userLogin(user);
		if (null == uu) {
			throw new RuntimeException("用户名或者密码错误，请重新登录");
		} else if (uu.getState() == 0) {
			throw new RuntimeException("用户未激活哦，请前往激活");
		} else {
			return uu;
		}
	}

	@Override
	public User findByUserName(String username) throws SQLException {
		UserDao userDao = new UserDaoImpl();
		return userDao.findByUsername(username);
	}

}

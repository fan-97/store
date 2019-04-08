package com.store.service;

import java.sql.SQLException;

import com.store.domain.User;

public interface UserService {
	public void userRegist(User user) throws SQLException;

	public boolean userActive(String code) throws SQLException;

	public User UserLogin(User user) throws SQLException;

	public User findByUserName(String username)throws SQLException;
}

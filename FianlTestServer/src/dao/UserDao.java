package dao;

import java.sql.SQLException;

import entity.User;

public interface UserDao {
	// ��һ���û�
	public User findOne(String name, String password) throws SQLException;

	public int add(User user);
}

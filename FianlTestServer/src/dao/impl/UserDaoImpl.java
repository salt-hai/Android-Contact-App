package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import dao.UserDao;
import db.DBUtil;
import entity.User;

public class UserDaoImpl implements UserDao {
	static Connection connection = null;
	static PreparedStatement pst = null;
	static ResultSet resultSet = null;

	 @Override
	    public User findOne(String name, String password) throws SQLException {
	        Connection conn = null;
	        PreparedStatement ps = null;
	        ResultSet rs = null;
	        User user = null;
	        //String sql = "select * from "+DBConstant.TB_USER+" where "+ DBConstant.USER_NAME +"=? and "+DBConstant.USER_PASSWORD+"=?";
	        String sql = "select * from user where username=? and password=?";
	        try {
	            conn = DBUtil.getConnection();
	            ps = conn.prepareStatement(sql);
	            ps.setString(1,name);
	            ps.setString(2,password);
	            rs = ps.executeQuery();
	            if(rs.next()){
	                user = new User();
	                user.setId(rs.getInt(1));
	                user.setUsername(rs.getString(2));
	                user.setPassword(rs.getString(3));
	            }
	        }catch (SQLException e){
	            e.printStackTrace();
	            throw new SQLException("²éÑ¯Êý¾ÝÊ§°Ü");
	        }finally {
	            DBUtil.close(rs,ps,conn);
	        }
	        return user;
	    }

	@Override
	public int add(User user) {
	
		connection = DBUtil.getConnection();
		String sql = "insert into user(username, password) values(?,?)" ;
		try {
			pst = connection.prepareStatement(sql);
			pst.setString(1,user.getUsername());
			pst.setString(2,user.getPassword());
			return pst.executeUpdate();
	}catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return 0;
		}
	}

}

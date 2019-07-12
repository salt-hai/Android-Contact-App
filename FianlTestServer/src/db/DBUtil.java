package db;

import java.sql.*;

public class DBUtil {
	static {
        try {
            Class.forName(DBConstant.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //����һ����ȡ���ݿ����ӵķ���
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBConstant.DB_URL,DBConstant.USER, DBConstant.PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("��ȡ����ʧ��");
        }
        return conn;
    }

    /**
     * �ر����ݿ�����
     * @param rs
     * @param stat
     * @param conn
     */
    public static void close(ResultSet rs, Statement stat, Connection conn){
        try {
            if(rs!=null)rs.close();
            if(stat!=null)stat.close();
            if(conn!=null)conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

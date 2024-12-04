package org.example;


import java.sql.*;

public class LinkMysql {
    public static void main(String[] args) throws SQLException {
        //获取数据库连接
        try (Connection connection = JdbcUtil.getConnection()) {
            //需要执行的sql语句
            String sql = "DELETE FROM students where id >= ?";
            if (connection != null) {
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setObject(1, 10);
                    int n = ps.executeUpdate();
                    System.out.println(n);
                }
            }
        }
        }
}

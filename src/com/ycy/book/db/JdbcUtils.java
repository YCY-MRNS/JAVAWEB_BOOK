package com.ycy.book.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @program: MVC
 * @description: JDBC的操作类
 * @author: ChangYue
 * @create: 2019-01-22 09:36
 */
public class JdbcUtils {
    /**
     * 释放connection对象的方法
     *
     * @param connection
     */
    public static void releaseConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void releaseConnection(ResultSet set, Statement statement, Connection connection) {
        try {
            if (set != null) {
                set.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("book");
    }

    /**
     * 获得数据源的一个Connection的对象
     *
     * @return
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

package com.myhive.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/22 16:32
 * @Description:
 */
public class HiveJdbcClient {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = DriverManager.getConnection("jdbc:hive2://node4:10000/bigdata", "root", "");
        Statement stmt = conn.createStatement();
        String sql = "select * from person limit 5";
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1) + "-" + res.getString("name"));
        }
    }
}

package com.myhive.jdbc;

import java.sql.SQLException;
import java.sql.*;

/**
 * @Version 1.0
 * @Author ZHANGBAIFA
 * @Date 2021/2/25 10:04
 * @Description:
 */
public class HiveJdbcClient2 {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection("jdbc:hive2://node2,node3,node4/default;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk", "root", "");
        Statement stmt = conn.createStatement();
        String sql = "select * from person";
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(res.getString(1));
        }
    }

}

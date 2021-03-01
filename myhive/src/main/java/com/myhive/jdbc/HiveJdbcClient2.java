package com.myhive.jdbc;

import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        Connection conn = DriverManager.getConnection("jdbc:hive2://node2,node3,node4/bigdata;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2_zk", "root", "");
        Statement stmt = conn.createStatement();
        String sql = "select * from t";
        ResultSet res = stmt.executeQuery(sql);
        List<String> list = new ArrayList<String>();
        int i = 0 ;
        while (res.next()) {
//            System.out.println(res.getString(1)+"-" + res.getString("name"));
            list.add(res.getString(2));
            i++ ;
            System.out.print(res.getString(1)+"\t");
            if( null != res.getString(2)){
                System.out.println(list.get(i-1));
            }else{
                System.out.println(res.getString(2));
            }
        }
        
    }

}

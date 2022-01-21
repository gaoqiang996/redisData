package com.redisdata.gq.utils;

import java.sql.*;

/**
 * JDBC 操作 Hive（注：JDBC 访问 Hive 前需要先启动HiveServer2）
 */
public class HiveJDBC {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    private static String url = "jdbc:hive2://hadoop10:2181/hive_jdbc_test;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";
    private static String user = "root";
    private static String password = "";

    private static Connection conn = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    // 加载驱动、创建连接
    public static void init() throws Exception {
        Class.forName(driverName);
        conn = DriverManager.getConnection(url,user,password);
        stmt = conn.createStatement();
    }

    // 创建数据库
    public static void createDatabase() throws Exception {
        String sql = "create database hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有数据库
    public static void showDatabases() throws Exception {
        String sql = "show databases";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 创建表
    public static void createTable() throws Exception {
        String sql = "create table emp(\n" +
                "empno int,\n" +
                "ename string,\n" +
                "job string,\n" +
                "mgr int,\n" +
                "hiredate string,\n" +
                "sal double,\n" +
                "comm double,\n" +
                "deptno int\n" +
                ")\n" +
                "row format delimited fields terminated by '\\t'";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询所有表
    public static void showTables() throws Exception {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

    // 查看表结构
    public static void descTable() throws Exception {
        String sql = "desc emp";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1) + "\t" + rs.getString(2));
        }
    }

    // 加载数据
    public static void loadData() throws Exception {
        String filePath = "/usr/local/data/emp.txt";
        String sql = "load data local inpath '" + filePath + "' overwrite into table emp";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 查询数据
    public static void selectData() throws Exception {
        String sql = "select * from emp";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        System.out.println("员工编号" + "\t" + "员工姓名" + "\t" + "工作岗位");
        while (rs.next()) {
            System.out.println(rs.getString("empno") + "\t\t" + rs.getString("ename") + "\t\t" + rs.getString("job"));
        }
    }

    // 统计查询（会运行mapreduce作业）
    public static void countData() throws Exception {
        String sql = "select count(1) from emp";
        System.out.println("Running: " + sql);
        rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getInt(1) );
        }
    }

    // 删除数据库
    public static void dropDatabase() throws Exception {
        String sql = "drop database if exists hive_jdbc_test";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 删除数据库表
    public static void deopTable() throws Exception {
        String sql = "drop table if exists emp";
        System.out.println("Running: " + sql);
        stmt.execute(sql);
    }

    // 释放资源
    public static void destory() throws Exception {
        if ( rs != null) {
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public static void main(String[] args) {
        try {
            init();
            String sql = "select * from test.t_order limit 0,100";
//            String sql = "select count(1) from t_item";
            System.out.println("Running: " + sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("order_id") + "\t\t" + rs.getString("person_id") + "\t\t" + rs.getString("order_date"));
            }
//            createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
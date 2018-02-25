package cn.com.huadi.aos.hdoa.dbtx.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

	public static Connection getConnection() throws Exception{
		 // 注册驱动
        Class.forName("com.gbase.jdbc.Driver");// 动态加载mysql驱动
        // 用户名
        String username="scdbtx";
        // 密码
        String password="123456";
        // 获取URL
        String url="jdbc:gbase://192.168.58.114:80/hddbtx";	 
        // 一个Connection代表一个数据库连接
        Connection conn = DriverManager.getConnection(url,username,password);
        
        return conn;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kovtun.WorkTimeMap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author kovtun
 */
public class SQLConnector {
    public static Connection getConnection(String login,String password,String url,int port) throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver");
        Properties properties=new Properties();
        properties.setProperty("user",login);
        properties.setProperty("password",password);
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+url+":"+port+"/", properties);
        return con;
    }
}

package com.serviceagency.dataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static DataSource ds = new BasicDataSource();

    static {
//        ds.setUrl("jdbc:mysql://localhost:3306/service_agency");
//        ds.setUsername("root");
//        ds.setPassword("root");
//        ds.setMinIdle(5);
//        ds.setMaxIdle(10);
//        ds.setMaxOpenPreparedStatements(100);



        try {
            InitialContext ctx = new InitialContext();
            Context initCtx  = (Context) ctx.lookup("java:/comp/env");
            ds = (DataSource) initCtx.lookup("jdbc/MyLocalDB");

            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource() {
    }

}

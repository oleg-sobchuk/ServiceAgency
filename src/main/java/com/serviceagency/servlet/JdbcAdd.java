package com.serviceagency.servlet;

import com.serviceagency.dataSource.DBCPDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet(name = "JdbcAdd", urlPatterns = "/jdbcadd")
public class JdbcAdd extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {

        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user VALUES (NULL, ?, ?)")
        ){
            ps.setString(1, request.getParameter("name"));
            ps.setString(2, request.getParameter("pass"));
            int i = ps.executeUpdate();


        }catch (SQLException e){
            //need log
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }
}
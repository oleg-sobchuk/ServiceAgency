package com.serviceagency.servlet;

import com.serviceagency.dataSource.JdbcDataSource;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "JdbcAdd", urlPatterns = "/jdbcadd")
public class JdbcAdd extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = JdbcDataSource.getConnection();

        try{
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user VALUES (NULL, ?, ?)");
            ps.setString(1, request.getParameter("name"));
            ps.setString(2, request.getParameter("pass"));
            int i = ps.executeUpdate();

        }catch (SQLException e){

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
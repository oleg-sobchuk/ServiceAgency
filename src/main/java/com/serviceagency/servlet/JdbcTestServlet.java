package com.serviceagency.servlet;

import com.serviceagency.dataSource.JdbcDataSource;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "JdbcTestServlet", urlPatterns = "/jdbc")
public class JdbcTestServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = JdbcDataSource.getConnection();
        PrintWriter writer = response.getWriter();
        try(PreparedStatement statement = conn.prepareStatement("SELECT name FROM user");
            ResultSet resultSet = statement.executeQuery()
        ){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>My app</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>My final app</h1>");
            while(resultSet.next()) {
                writer.println(resultSet.getString("name"));;
            }
            writer.println("</body>");
            writer.println("</html>");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

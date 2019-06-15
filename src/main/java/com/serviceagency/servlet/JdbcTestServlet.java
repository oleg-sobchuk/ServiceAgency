package com.serviceagency.servlet;

import com.serviceagency.dataSource.DBCPDataSource;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "JdbcTestServlet", urlPatterns = "/jdbc")
public class JdbcTestServlet extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {


        PrintWriter writer = response.getWriter();
        try(Connection conn = DBCPDataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT name FROM user")
        ){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>My app</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>My final app</h1>");
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                while(resultSet.next()) {
                    writer.println(resultSet.getString("name"));
                }
            }
            writer.println("</body>");
            writer.println("</html>");

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
}

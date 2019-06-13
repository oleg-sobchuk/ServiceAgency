package com.serviceagency.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Servlet", urlPatterns = "/servlet")
public class Servlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try(PrintWriter writer = response.getWriter()){
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<title>My app</title>");
            writer.println("</head>");
            writer.println("<body>");
            writer.println("<h1>My final app</h1>");
            writer.println("</body>");
            writer.println("</html>");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}

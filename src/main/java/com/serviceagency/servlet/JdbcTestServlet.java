package com.serviceagency.servlet;

import com.serviceagency.DaoJdbcSqlImpl.UserDaoImpl;
import com.serviceagency.Model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "JdbcTestServlet", urlPatterns = "/jdbc")
public class JdbcTestServlet extends HttpServlet {
    private UserDaoImpl userDao = new UserDaoImpl();

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();
        if (session.getAttribute("user")==null){
            String nextURL = "/login.jsp";
            session.invalidate();
            response.sendRedirect(request.getContextPath() + nextURL);
            return;
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();

        //userDao.deleteUser(1);
        //userDao.addUser("коля", "4");

        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<meta charset=\"utf-8\">");
        writer.println("<title>My app</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>My final app</h1>");
        writer.println("<table>");
        List<User> users = userDao.getAll();
        for (User user : users) {
            writer.println("<tr>");
            writer.println("<td>");
            writer.println(user);
            writer.println("</td>");
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.println("</body>");
        writer.println("</html>");


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
}

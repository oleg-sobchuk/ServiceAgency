package com.serviceagency.servlet;

import com.serviceagency.model.User;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IUserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String userPassword = request.getParameter("userPassword");

        String nextURL = "../error.jsp";


        if (userService.findByName(userName) == null){

            userService.addUser(userName, userPassword);

            nextURL = "/login";

            response.sendRedirect(nextURL);
            return;
        }else{
            request.setAttribute("message", "user with this name already exist");
            nextURL = "/register.jsp";
        }

        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
    }
}

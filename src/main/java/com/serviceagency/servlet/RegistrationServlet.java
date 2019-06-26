package com.serviceagency.servlet;


import com.serviceagency.exception.DataBaseException;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.IUserService;
import com.serviceagency.utils.OnExceptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IUserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("name");
        String userPassword = request.getParameter("password");


        String nextURL = "../error.jsp";

        try {
            if (userService.findByName(userName) == null){

                userService.addUser(userName, userPassword);

                nextURL = "/login";

                response.sendRedirect(nextURL);
                return;
            }else{
                request.setAttribute("message", "user with this name already exist");
                nextURL = "/register.jsp";
            }
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(RegistrationServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(RegistrationServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
    }
}

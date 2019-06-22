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

/**
 * Used for login and logout user.
 * Create an instance of the user and put it in the session
 * only add the user to the session if the user if valid.
 *
 * redirect to index.jsp when valid
 * redirect back to login.jsp when user missed
 *
 * logout and redirect to login.jsp when param(action) = "logout"
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IUserService userService = new UserServiceImpl();

    public LoginServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("name");
        String userpassword = request.getParameter("password");
        String action = request.getParameter("action");

        String nextURL = "/error.jsp";

        HttpSession session = request.getSession();

        if (action.equals("logout")){
            session.invalidate();
            nextURL = "/login.jsp";
        }else{
            if (userService.isValid(username,userpassword)){
                User user = userService.findByName(username);

                session.setAttribute("user", user);

                if (userService.isOnlyUserRole(user.getId())) {
                    nextURL = "/";
                    session.setAttribute("userOnly", true);
                } else {
                    nextURL = "/jdbc";
                }
                response.sendRedirect(nextURL);
                return;
            }else{
                request.setAttribute("message", "Invalid username and password");
                nextURL = "/login.jsp";
            }
        }
        getServletContext().getRequestDispatcher(nextURL).forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(request,response);
    }
}

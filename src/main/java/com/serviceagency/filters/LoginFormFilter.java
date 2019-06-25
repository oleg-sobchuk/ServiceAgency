package com.serviceagency.filters;

import com.serviceagency.model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "LoginFormFilter")
public class LoginFormFilter implements Filter {

    private final String incorrectNameMsg = "Username cant be empty and must contains only digits and Latin letters";
    private final String incorrectPasswordMsg = "Password cant be empty and must contains only digits and Latin letters";

    private Logger logger = Logger.getLogger(LoginFormFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String username = req.getParameter("name");
        String password = req.getParameter("password");
        String action = req.getParameter("action");

        HttpSession session = ((HttpServletRequest) req).getSession();

        String nextURL = "/login.jsp";

        if (action != null && action.equals("logout")) {
            if (session.getAttribute("user") != null) {
                User user = (User) session.getAttribute("user");
                logger.info("User " + user.getName() + " logout");
            }
            session.invalidate();
            request.getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        if(request.getMethod().equalsIgnoreCase("POST")){

            if (username != null && username.matches("[A-Za-z0-9]+")) {

                if (password != null && password.matches("[A-Za-z0-9]+")) {
                    chain.doFilter(req, resp);
                }else {
                    request.setAttribute("message", incorrectPasswordMsg);
                    logger.info(incorrectPasswordMsg);
                    request.getRequestDispatcher(nextURL).forward(request, response);
                }
            }else {
                request.setAttribute("message", incorrectNameMsg);
                logger.info(incorrectNameMsg);
                request.getRequestDispatcher(nextURL).forward(request, response);
            }
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

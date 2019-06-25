package com.serviceagency.filters;

import com.serviceagency.model.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/user/","/personal/"})
public class AuthenticationFilter implements Filter {

    private Logger logger = Logger.getLogger(AuthenticationFilter.class);

    public void destroy() {
        logger.info("Auth filter destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String nextURL = "../error.jsp";

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            nextURL = "/login.jsp";
            logger.info("Unauthorized request to " + request.getRequestURI() + " Redirected to login page.");
            session.invalidate();
            request.setAttribute("message", "Unauthorized request! Please login...");
            request.getRequestDispatcher(nextURL).forward(request, response);
            return;
        }else {
            logger.info("User <" + ((User)session.getAttribute("user")).getName() + "> go to " + request.getRequestURI());
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        logger.info("Auth filter init");
    }

}

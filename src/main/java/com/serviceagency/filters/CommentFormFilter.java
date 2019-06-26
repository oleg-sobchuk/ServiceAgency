package com.serviceagency.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CommentFormFilter")
public class CommentFormFilter implements Filter {
    private final String emptyCommentMsg = "Comment cant be empty";
    private final String invalidOrderIdMsg = "Incorrect order id";
    private final String resourceNotFoundMsg = "Resource not founded";

    private Logger logger = Logger.getLogger(CommentFormFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String orderId = req.getParameter("order_id");
        String text = req.getParameter("text");

        String nextURL = "/user/comments";

        if (orderId != null) {
            try {
                Long.parseLong(orderId);
            } catch (NumberFormatException e) {
                logger.warn(invalidOrderIdMsg + ". " + e);
                request.setAttribute("message", invalidOrderIdMsg);
                nextURL = "/error.jsp";

                request.getRequestDispatcher(nextURL).forward(request, response);
                return;
            }
        } else {
            logger.warn(invalidOrderIdMsg);
            request.setAttribute("message", invalidOrderIdMsg);
            nextURL = "/error.jsp";
            request.getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        if (request.getMethod().equalsIgnoreCase("POST")) {

            if (text != null && !text.trim().isEmpty()) {
                chain.doFilter(req, resp);
            } else {
                logger.info(emptyCommentMsg);
                request.setAttribute("message", emptyCommentMsg);
                request.getRequestDispatcher(nextURL).forward(request, response);
                return;
            }

        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

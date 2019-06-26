package com.serviceagency.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "OrderManageFormFilter")
public class OrderManageFormFilter implements Filter {
    private final String invalidOrderIdMsg = "Incorrect order id";
    private final String resourceNotFoundMsg = "Resource not founded";

    private Logger logger = Logger.getLogger(OrderManageFormFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String orderId = req.getParameter("order_id");

        String nextURL = "/error.jsp";

        if (orderId != null) {
            try {
                Long.parseLong(orderId);
            } catch (NumberFormatException e) {
                logger.warn(invalidOrderIdMsg + ". " + e);
                request.setAttribute("message", resourceNotFoundMsg);

                request.getRequestDispatcher(nextURL).forward(request, response);
                return;
            }
        } else {
            logger.warn(invalidOrderIdMsg);
            request.setAttribute("message", resourceNotFoundMsg);
            request.getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        chain.doFilter(req, resp);

    }

    public void init(FilterConfig config) throws ServletException {

    }

}

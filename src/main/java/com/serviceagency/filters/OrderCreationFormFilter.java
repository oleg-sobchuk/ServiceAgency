package com.serviceagency.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "OrderCreationFormFilter")
public class OrderCreationFormFilter implements Filter {
    private final String invalidParametersMsg = "Device description and Malfunction description cant be empty";

    private Logger logger = Logger.getLogger(OrderCreationFormFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String deviceDesc = request.getParameter("device_desc");
        String malfuncDesc = request.getParameter("malfunc_desc");

        String nextURL = "/user/orders";

        if (request.getMethod().equalsIgnoreCase("POST")) {
            if (deviceDesc != null && !deviceDesc.trim().isEmpty() || malfuncDesc != null && !malfuncDesc.trim().isEmpty()) {
                chain.doFilter(req, resp);
            } else {
                logger.warn(invalidParametersMsg);
                request.setAttribute("message", invalidParametersMsg);
//                request.getRequestDispatcher(nextURL).forward(request, response);
                chain.doFilter(new OrderCreationFormFilterWrapper(request), resp);
            }
        }else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

package com.serviceagency.servlet;

import com.serviceagency.model.Order;
import com.serviceagency.model.User;
import com.serviceagency.serviceImpl.OrderServiceImpl;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.IOrderService;
import com.serviceagency.services.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserOrderServlet", urlPatterns = "/user/orders")
public class UserOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IOrderService orderService = new OrderServiceImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String deviceDesc = request.getParameter("deviceDesc");
        String malfuncDesc = request.getParameter("malfuncDesc");

        String nextURL = "/error.jsp";

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            nextURL = "/login.jsp";
            session.invalidate();
            request.setAttribute("message", "Unauthorized request! Please login...");
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");

        Order order = new Order(user.getId(), deviceDesc, malfuncDesc);
        orderService.add(order);

        List<Order> userOrders = orderService.findByUserId(user.getId());
        request.setAttribute("orders", userOrders);
        nextURL = "/user/orders.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextURL = "/error.jsp";

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            nextURL = "/login.jsp";
            session.invalidate();
            request.setAttribute("message", "Unauthorized request! Please login...");
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");
        List<Order> userOrders = orderService.findByUserId(user.getId());
        request.setAttribute("orders", userOrders);
        nextURL = "/user/orders.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

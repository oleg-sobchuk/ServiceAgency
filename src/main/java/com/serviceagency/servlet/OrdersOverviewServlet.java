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

@WebServlet(name = "OrdersOverviewServlet", urlPatterns = "/personal/process_order")
public class OrdersOverviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IUserService userService = new UserServiceImpl();
    private IOrderService orderService = new OrderServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String orderId = request.getParameter("order_id");
        String note = request.getParameter("note");
        String price = request.getParameter("price");
        String action = request.getParameter("action");

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
        List<String> userRoles = userService.getRoleNames(user.getId());
        boolean isDone = orderService.makeAction(userRoles, action, orderId, note, price);

        doGet(request,response);
        return;

        //nextURL = "/personal/manage_orders";
        //getServletContext().getRequestDispatcher(nextURL).forward(request, response);
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

        //User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.getAll();
        request.setAttribute("orders", orders);
        nextURL = "/personal/manage_orders.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

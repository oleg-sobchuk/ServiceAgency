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

@WebServlet(name = "OrderInfoServlet", urlPatterns = "/personal/order_info")
public class OrderInfoServlet extends HttpServlet {

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

        User user = (User) session.getAttribute("user");
        List<String> userRoles = userService.getRoleNames(user.getId());
        boolean isDone = orderService.makeAction(userRoles, action, orderId, note, price);

        request.setAttribute("success_message", "Change done successful");

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("order_id");

        String nextURL = "/error.jsp";

        long orderId = Long.parseLong(id);
        Order order = orderService.findById(orderId);
        User ownerUser = userService.findById(order.getUserId());

        request.setAttribute("owner_user", ownerUser);
        request.setAttribute("order", order);
        nextURL = "/personal/order_info.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

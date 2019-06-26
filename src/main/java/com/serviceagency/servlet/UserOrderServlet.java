package com.serviceagency.servlet;

import com.serviceagency.exception.DataBaseException;
import com.serviceagency.model.Order;
import com.serviceagency.model.User;
import com.serviceagency.serviceImpl.OrderServiceImpl;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.IOrderService;
import com.serviceagency.services.IUserService;
import com.serviceagency.utils.OnExceptionUtil;

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
        String deviceDesc = request.getParameter("device_desc");
        String malfuncDesc = request.getParameter("malfunc_desc");

        //Dont redirect from filter...
        if (deviceDesc == null || deviceDesc.trim().isEmpty() || malfuncDesc == null || malfuncDesc.trim().isEmpty()) {
            request.setAttribute("message", "Device description and Malfunction description cant be empty");
            doGet(request, response);
            return;
        }

        String nextURL = "/error.jsp";

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Order order = new Order(user.getId(), deviceDesc, malfuncDesc);

        try{
            orderService.add(order);
            List<Order> userOrders = orderService.findByUserId(user.getId());
            request.setAttribute("orders", userOrders);
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(UserOrderServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(UserOrderServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }


        nextURL = "/user/orders.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextURL = "/error.jsp";

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            List<Order> userOrders = orderService.findByUserId(user.getId());
            request.setAttribute("orders", userOrders);
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(UserOrderServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(UserOrderServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        nextURL = "/user/orders.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

package com.serviceagency.servlet;

import com.serviceagency.exception.DataBaseException;
import com.serviceagency.exception.IllegalOrderStatusException;
import com.serviceagency.exception.NotEnoughAuthorityException;
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

        try {
            List<String> userRoles = userService.getRoleNames(user.getId());
            boolean isDone = orderService.makeAction(userRoles, action, orderId, note, price);
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (IllegalArgumentException | IllegalOrderStatusException e) {
            OnExceptionUtil.processErrorInvalidParamException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (NotEnoughAuthorityException e) {
            OnExceptionUtil.processErrorException(OrderInfoServlet.class, e, "Not enough authority for action.", request);
            getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }


        request.setAttribute("success_message", "Change done successful");

        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("order_id");

        String nextURL = "/error.jsp";

        try {
            long orderId = Long.parseLong(id);
            Order order = orderService.findById(orderId);
            User ownerUser = userService.findById(order.getUserId());
            request.setAttribute("owner_user", ownerUser);
            request.setAttribute("order", order);

        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (NumberFormatException e) {
            OnExceptionUtil.processErrorInvalidParamException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(OrderInfoServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        nextURL = "/personal/order_info.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

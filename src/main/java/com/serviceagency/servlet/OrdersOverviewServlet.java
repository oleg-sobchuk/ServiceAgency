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

        if (session.getAttribute("order_page_number") == null) {
            session.setAttribute("order_page_number", 1);
        }
        int currentPage = (Integer) session.getAttribute("order_page_number");
        String pageAction = request.getParameter("page_action");
        int pageSize = 5;
        int ordersCount = orderService.getAll().size();


        if (pageAction != null) {
            if (pageAction.equals("next") && currentPage * pageSize <= ordersCount) {
                currentPage++;
            }else {
                if (pageAction.equals("prev") && currentPage > 1) {
                    currentPage--;
                }
            }
        }

        List<Order> orders = orderService.getOrdersPage(currentPage, pageSize);
        session.setAttribute("order_list_size", ordersCount);
        session.setAttribute("order_page_number", currentPage);
        request.setAttribute("orders", orders);
        nextURL = "/personal/manage_orders_paging.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

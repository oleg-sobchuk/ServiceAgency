package com.serviceagency.servlet;

import com.serviceagency.model.Comment;
import com.serviceagency.model.Order;
import com.serviceagency.model.User;
import com.serviceagency.serviceImpl.CommentServiceImpl;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.ICommentService;
import com.serviceagency.services.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CommentsServlet", urlPatterns = "/user/comments")
public class CommentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ICommentService commentService = new CommentServiceImpl();
    private IUserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order_id");
        String text = request.getParameter("text");

        String nextURL = "../error.jsp";

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            nextURL = "/login.jsp";
            session.invalidate();
            request.setAttribute("message", "Unauthorized request! Please login...");
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        User user = (User) session.getAttribute("user");

        Comment comment = new Comment(user.getId(), Long.parseLong(orderId), text);
        commentService.add(comment);

        List<Comment> comments = commentService.findByOrderId(Long.parseLong(orderId));
        request.setAttribute("comments", comments);
        request.setAttribute("order_id", orderId);
        nextURL = "/user/comments.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order_id");

        String nextURL = "../error.jsp";

        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            nextURL = "../login.jsp";
            session.invalidate();
            request.setAttribute("message", "Unauthorized request! Please login...");
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        List<Comment> comments = commentService.findByOrderId(Long.parseLong(orderId));
        request.setAttribute("comments", comments);
        request.setAttribute("order_id", orderId);
        nextURL = "/user/comments.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

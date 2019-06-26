package com.serviceagency.servlet;

import com.serviceagency.exception.DataBaseException;
import com.serviceagency.model.Comment;
import com.serviceagency.model.Order;
import com.serviceagency.model.User;
import com.serviceagency.serviceImpl.CommentServiceImpl;
import com.serviceagency.serviceImpl.UserServiceImpl;
import com.serviceagency.services.ICommentService;
import com.serviceagency.services.IUserService;
import com.serviceagency.utils.OnExceptionUtil;
import org.apache.log4j.Logger;

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

    Logger logger = Logger.getLogger(CommentsServlet.class);

    private ICommentService commentService = new CommentServiceImpl();
    private IUserService userService = new UserServiceImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order_id");
        String text = request.getParameter("text");

        String nextURL = "../error.jsp";

        HttpSession session = request.getSession();

        if (text == null || text.trim().isEmpty()) {
            doGet(request,response);
            return;
        }

        User user = (User) session.getAttribute("user");

        try {
            Comment comment = new Comment(user.getId(), Long.parseLong(orderId), text);
            commentService.add(comment);
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (NumberFormatException e) {
            OnExceptionUtil.processErrorInvalidParamException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("order_id");

        String nextURL = "../error.jsp";

        try {
            List<Comment> comments = commentService.findByOrderId(Long.parseLong(orderId));
            request.setAttribute("comments", comments);
            request.setAttribute("order_id", orderId);
        }catch (DataBaseException e) {
            OnExceptionUtil.processErrorDbException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (NumberFormatException e) {
            OnExceptionUtil.processErrorInvalidParamException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }catch (Exception e) {
            OnExceptionUtil.processErrorUnknownException(CommentsServlet.class, e, request);
            getServletContext().getRequestDispatcher(nextURL).forward(request, response);
            return;
        }

        nextURL = "/user/comments.jsp";
        getServletContext().getRequestDispatcher(nextURL).forward(request, response);
    }
}

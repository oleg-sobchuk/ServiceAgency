package com.serviceagency.serviceImpl;

import com.serviceagency.dao.ICommentDao;
import com.serviceagency.daoJdbcSqlImpl.CommentDaoImpl;
import com.serviceagency.model.Comment;
import com.serviceagency.services.ICommentService;

import java.util.List;

public class CommentServiceImpl implements ICommentService {

    private ICommentDao commentDao = new CommentDaoImpl();

    @Override
    public Comment findById(long id) {
        return commentDao.findById(id);
    }

    @Override
    public List<Comment> findByUserId(long userId) {
        return commentDao.findByUserId(userId);
    }

    @Override
    public List<Comment> findByOrderId(long orderId) {
        return commentDao.findByOrderId(orderId);
    }

    @Override
    public List<Comment> getAll() {
        return commentDao.getAll();
    }

    @Override
    public boolean add(Comment comment) {
        return commentDao.add(comment);
    }
}

package com.serviceagency.Dao;

import com.serviceagency.Model.Comment;

import java.util.List;

public interface ICommentDao {
    Comment findById(long id);
    List<Comment> findByUserId(long userId);
    List<Comment> findByOrderId(long orderId);
    List<Comment> getAll();
    void update(long id, String text);
}

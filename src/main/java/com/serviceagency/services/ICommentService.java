package com.serviceagency.services;

import com.serviceagency.model.Comment;

import java.util.List;

public interface ICommentService {
    Comment findById(long id);
    List<Comment> findByUserId(long userId);
    List<Comment> findByOrderId(long orderId);
    List<Comment> getAll();
    boolean add(Comment comment);
}

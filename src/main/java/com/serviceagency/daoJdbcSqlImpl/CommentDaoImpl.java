package com.serviceagency.daoJdbcSqlImpl;

import com.serviceagency.dao.ICommentDao;
import com.serviceagency.model.Comment;
import com.serviceagency.dataSource.DBCPDataSource;
import com.serviceagency.exception.DataBaseException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentDaoImpl implements ICommentDao {

    private static final Logger logger = LogManager.getLogger(CommentDaoImpl.class);
    private final String SQL_EXCEPTION_MESSAGE = "SQL Exception ";

    @Override
    public Comment findById(long id) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM comments WHERE id = ? ")
        ) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<Comment> comments = getComments(resultSet);
                if (!comments.isEmpty()) {
                    return comments.get(0);
                }
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return null;
    }

    @Override
    public List<Comment> findByUserId(long userId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM comments WHERE user_id = ? ")
        ) {
            preparedStatement.setLong(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                comments = getComments(resultSet);
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return comments;
    }

    @Override
    public List<Comment> findByOrderId(long orderId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM comments WHERE order_id = ? ")
        ) {
            preparedStatement.setLong(1, orderId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                comments = getComments(resultSet);
            }
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return comments;
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(" SELECT * FROM comments ");
             ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            comments = getComments(resultSet);
        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return comments;
    }

    @Override
    public boolean add(Comment comment) {
        try (Connection conn = DBCPDataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO comments (id, user_id, order_id, create_date, text) VALUES (NULL, ?, ?, ?, ?)")
        ) {

            ps.setLong(1, comment.getUserId());
            ps.setLong(2, comment.getOrderId());
            ps.setObject(3, comment.getCreateDate());
            ps.setString(4, comment.getText());

            int i = ps.executeUpdate();
            if (i > 0) {
                return true;
            }

        } catch (SQLException e) {
            logger.warn(SQL_EXCEPTION_MESSAGE, e);
            throw new DataBaseException(SQL_EXCEPTION_MESSAGE, e);
        }
        return false;
    }

    private List<Comment> getComments(ResultSet resultSet) throws SQLException {
        List<Comment> orders = new ArrayList<>();
        while (resultSet.next()) {
            Comment comment = new Comment();
            comment.setId(resultSet.getLong("id"));
            comment.setUserId(resultSet.getLong("user_id"));
            comment.setOrderId(resultSet.getLong("order_id"));
            comment.setCreateDate(resultSet.getObject("create_date", LocalDateTime.class));
            comment.setText(resultSet.getString("text"));
            orders.add(comment);
        }
        return orders;
    }
}

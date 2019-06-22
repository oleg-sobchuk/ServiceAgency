package com.serviceagency.dao;

import com.serviceagency.model.UserRole;

import java.util.List;

public interface IUserRoleDao {
    List<UserRole> getAll();

    /**Looking for roles that belong to user with asked ID
     *
     * @param id is user ID
     * @return list of "role IDs" or empty list if user have not additional roles
     *
     */
    List<Long> findByUserId(long id);


    /**Looking for users that have role with asked ID
     *
     * @param id is role ID
     * @return list of "user IDs" or empty list if asked role no one use
     *
     */
    List<Long> findByRoleId(long id);
    boolean add(long userId, long roleId);
    boolean delete(long userId, long roleId);
}

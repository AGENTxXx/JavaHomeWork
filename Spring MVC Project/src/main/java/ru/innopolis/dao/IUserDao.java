package ru.innopolis.dao;

import ru.innopolis.models.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public interface IUserDao {
    int createUser(User user) throws SQLException;

    User authUser(String login, String password) throws SQLException;

    int updateUser(User user) throws SQLException;

    int removeUser(int userId) throws SQLException;

    int userUnlock(int userId) throws SQLException;

    int userLock(int userId) throws SQLException;

    List<User> getAllUsers() throws SQLException;

}

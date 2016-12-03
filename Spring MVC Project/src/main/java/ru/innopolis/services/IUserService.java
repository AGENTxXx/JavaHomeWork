package ru.innopolis.services;

import ru.innopolis.models.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
public interface IUserService {
    List<User> getAllUsers() throws SQLException;
    User authUser(String login, String password) throws SQLException;
    int createUser(User user) throws SQLException;
    int userUnlock(int userId) throws SQLException;
    int userLock(int userId) throws SQLException;
    int removeUser(int userId) throws SQLException;
    int updateUser(User user) throws SQLException;

}

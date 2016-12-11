package ru.innopolis.services;

import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.User;

import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
public interface IUserService {
    List<User> getAllUsers() throws ServiceHandlerException;
    User authUser(String login, String password) throws ServiceHandlerException;
    int createUser(User user) throws ServiceHandlerException;
    int userUnlock(int userId) throws ServiceHandlerException;
    int userLock(int userId) throws ServiceHandlerException;
    int removeUser(int userId) throws ServiceHandlerException;
    int updateUser(User user) throws ServiceHandlerException;
    User getUser(int userId) throws ServiceHandlerException;
    User getUserByUsername(String username) throws ServiceHandlerException;
}

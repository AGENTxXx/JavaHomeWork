package ru.innopolis.dao;

import ru.innopolis.models.User;

import java.util.List;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public interface IUserDao {
    int createUser(User user);

    User authUser(String login, String password);

    int updateUser(User user);

    int removeUser(int userId);

    int userUnlock(int userId);

    int userLock(int userId);

    List<User> getAllUsers();
    User getUser(int i);

    User getUserByUsername(String username);
}

package ru.innopolis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.innopolis.dao.IUserDao;
import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.User;

import javax.persistence.PersistenceException;
import java.util.List;

import static ru.innopolis.config.Constants.MD5;
import static ru.innopolis.config.Constants.SALT;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */

public class UserService implements IUserService {
    /*Поле-инферыейс позволяющий подключить Dao-объект по работе со статьями */
    private IUserDao userDao;

    /*Инициализация Dao-Объекта*/
    @Autowired(required = true)
    @Qualifier(value = "userDao")
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Метод возвращает всех пользователей
     * @return - null, если пользователи не найдены, иначе - List<User>
     * @throws ServiceHandlerException
     */
    @Override
    public List<User> getAllUsers() throws ServiceHandlerException {
        List<User> users = null;
        try {
            users = this.userDao.getAllUsers();
        } catch (IllegalArgumentException e) {
            throw new ServiceHandlerException("");
        }

        return users;

    }

    /**
     * Метод отвечает за авторизацию пользователя
     * @param login
     * @param password
     * @return - null, если данные о пользователе не верны, иначе User
     * @throws ServiceHandlerException
     */
    @Override
    public User authUser(String login, String password) throws ServiceHandlerException {
        /*
        if (login.length() > 0 && password.length() > 0) {
            try {
                password = MD5(MD5(password) + SALT);
                return this.userDao.authUser(login, password);
            } catch () {

            }

        }
        */
        return null;
    }

    /**
     * Метод отвечает за создание нового пользователя
     * @param user - данные о новом пользователе
     * @return - 0, если не удалось создать пользователя, -1 - если логин уже существует, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int createUser(User user) throws ServiceHandlerException {
        int result = 0;
        try {
            result = this.userDao.createUser(user);
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw new ServiceHandlerException("");
        }
        return result;
    }

    /**
     * Метод отвечает за разблокировку пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int userUnlock(int userId) throws ServiceHandlerException {
        if (userId > 0) {
            try {
                return this.userDao.userUnlock(userId);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }

    /**
     * Метод отвечает за блокировку пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int userLock(int userId) throws ServiceHandlerException {
        if (userId > 0) {
            try {
                return this.userDao.userLock(userId);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }
        return 0;
    }

    /**
     * Метод отвечает за удаление пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int removeUser(int userId) throws ServiceHandlerException {
        if (userId > 0) {
            try {
                return this.userDao.removeUser(userId);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }

    /**
     * Метод отвечает за обновление информации о пользователе
     * @param user - новая информация о пользователе
     * @return - 0 - если не удалось найти пользователя или переданные данные не полны/не верны, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int updateUser(User user) throws ServiceHandlerException {
        int result = 0;
        if (user.getEmail().length() > 0 && user.getFirstname().length() > 0 && user.getLastname().length() > 0) {
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                user.setPassword(MD5(MD5(user.getPassword()) + SALT));
            }
            else {
                user.setPassword(null);
            }
            try {
                result = this.userDao.updateUser(user);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return result;
    }

    /**
     *
     * @param userId
     * @return
     * @throws ServiceHandlerException
     */
    @Override
    public User getUser(int userId) throws ServiceHandlerException {
        if (userId > 0) {
            try {
                return this.userDao.getUser(userId);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return null;
    }

    /**
     *
     * @param username
     * @return
     * @throws ServiceHandlerException
     */
    @Override
    public User getUserByUsername(String username) throws ServiceHandlerException {
        if (username != null && username.length() > 0) {
            try {
                return this.userDao.getUserByUsername(username);
            } catch (IllegalArgumentException e) {
                throw new ServiceHandlerException("");
            }
        }
        return null;
    }
}

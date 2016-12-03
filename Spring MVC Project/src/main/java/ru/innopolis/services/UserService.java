package ru.innopolis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.innopolis.dao.IUserDao;
import ru.innopolis.models.User;

import javax.naming.NamingException;
import java.sql.SQLException;
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
     * @throws SQLException
     */
    @Override
    public List<User> getAllUsers() throws SQLException {
        this.userDao.getAllUsers();
        return null;
    }

    /**
     * Метод отвечает за авторизацию пользователя
     * @param login
     * @param password
     * @return - null, если данные о пользователе не верны, иначе User
     * @throws SQLException
     */
    @Override
    public User authUser(String login, String password) throws SQLException {
        if (login.length() > 0 && password.length() > 0) {
            password = MD5(MD5(password) + SALT);
            return this.userDao.authUser(login, password);
        }

        return null;
    }

    /**
     * Метод отвечает за создание нового пользователя
     * @param user - данные о новом пользователе
     * @return - 0, если не удалось создать пользователя, -1 - если логин уже существует, иначе - 1
     */
    @Override
    public int createUser(User user) {
        int result = 0;
        try {
            result = this.userDao.createUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод отвечает за разблокировку пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws SQLException
     */
    @Override
    public int userUnlock(int userId) throws SQLException {
        if (userId > 0) {
            return this.userDao.userUnlock(userId);
        }

        return 0;
    }

    /**
     * Метод отвечает за блокировку пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws SQLException
     */
    @Override
    public int userLock(int userId) throws SQLException {
        if (userId > 0) {
            return this.userDao.userLock(userId);
        }
        return 0;
    }

    /**
     * Метод отвечает за удаление пользователя
     * @param userId - ид. пользователя
     * @return - 0, если не удалось найти пользователя или id пользователя задан неверно, иначе - 1
     * @throws SQLException
     */
    @Override
    public int removeUser(int userId) throws SQLException {
        if (userId > 0) {
            int result = this.userDao.removeUser(userId);
            return result;
        }

        return 0;
    }

    /**
     * Метод отвечает за обновление информации о пользователе
     * @param user - новая информация о пользователе
     * @return - 0 - если не удалось найти пользователя или переданные данные не полны/не верны, иначе - 1
     * @throws SQLException
     */
    @Override
    public int updateUser(User user) throws SQLException {
        int result = 0;
        if (user.getEmail().length() > 0 && user.getFirstname().length() > 0 && user.getLastname().length() > 0) {
            if (user.getPassword().length() > 0) {
                user.setPassword(MD5(MD5(user.getPassword()) + SALT));
            }
            else {
                user.setPassword(null);
            }
            result = this.userDao.updateUser(user);
        }

        return result;
    }
}

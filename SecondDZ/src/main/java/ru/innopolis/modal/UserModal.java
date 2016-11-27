package ru.innopolis.modal;

import ru.innopolis.constants.DB;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import static ru.innopolis.constants.Constants.*;

/**
 * Created by Alexander Chuvashov on 23.11.2016.
 */
public class UserModal {

    /**
     * Метод получает всех пользователей
     * @return возвращает список пользователей
     * @throws SQLException
     * @throws NamingException
     */
    public List<User> getAllUsers() throws SQLException, NamingException {

        String sql = "SELECT id, fistname, lastname, email login, locked FROM USERS";
        List<User> users = new LinkedList<>();
        try(Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getBoolean(6)));
            }
            return users;
        }
    }

    /**
     * Метод отвечает за проверку существования пользовтаеля в базе
     * @param login
     * @param password
     * @return - возвращает пользователя
     * @throws SQLException
     * @throws NamingException
     */
    public User authUser(String login, String password) throws SQLException, NamingException {


        if (login.length() > 0 && password.length() > 0) {
            password = MD5(password + SALT);

            String sql = "SELECT * FROM USERS WHERE login = '"+login+"' AND password = '"+password+"' AND locked = false LIMIT 1";
            try(Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement();
                ResultSet rs = statement.executeQuery(sql)) {
                while(rs.next()) {
                    return new User(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getBoolean(7));
                }
            }
        }
        return null;
    }

    /**
     * Метод отвечает за регистрацию нового пользователя
     * @param firstname
     * @param lastname
     * @param email
     * @param login
     * @param password
     * @return если успшено - возвращает положительное число, иначе -1
     * @throws SQLException
     * @throws NamingException
     */
    public int regUser(String firstname, String lastname, String email, String login, String password) throws SQLException, NamingException {

        if (login.length() > 0 && password.length() > 0 && email.length() > 0 && firstname.length() > 0 && lastname.length() > 0) {
            password = MD5(password + SALT);
            String sql = "INSERT INTO USERS (firstname, lastname, email, login, password, locked) VALUES('"+firstname+"','"+lastname+"','"+email+"','"+login+"','"+password+"',true)";
            try(Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод отвечает за блокировку указанного в userId пользотваеля
     * @param userId - id пользователя
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int userLock(int userId) throws SQLException, NamingException {
        if (userId > 0) {
            String sql = "UPDATE USERS SET locked = true WHERE user_id = "+userId;
            Connection cn = DB.getInstance().getConnection();
            try(Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод отвечает за разблокировку указанного в userId пользотваеля
     * @param userId - id пользователя
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int userUnlock(int userId) throws SQLException, NamingException {
        if (userId > 0) {
            String sql = "UPDATE USERS SET locked = false WHERE user_id = "+userId;
            Connection cn = DB.getInstance().getConnection();
            try(Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод отвечает за удаление пользователя из БД по id
     * @param userId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int deleteUser(int userId) throws SQLException, NamingException {
        if (userId > 0) {
            String sql = "DELETE USERS WHERE user_id = "+userId+"";
            Connection cn = DB.getInstance().getConnection();
            try(Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод отвечает за редактирование профиля пользователя
     * @param userId
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int editUser(String userId, String firstname, String lastname, String email, String password) throws SQLException, NamingException {
        Connection cn = DB.getInstance().getConnection();
        if (password.length() > 0 && email.length() > 0 && firstname.length() > 0 && lastname.length() > 0) {
            password = MD5(password + SALT);
            String sql = "UPDATE USERS SET firstname = '"+firstname+"',lastname = '"+lastname+"', email = '"+email+"', password = '"+password+"' WHERE id = " + userId;
            try(Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }
}

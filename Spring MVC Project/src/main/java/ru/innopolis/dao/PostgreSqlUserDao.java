package ru.innopolis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.models.User;

import javax.naming.NamingException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static ru.innopolis.config.Constants.*;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public class PostgreSqlUserDao implements IUserDao {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(PostgreSqlUserDao.class);

    public PostgreSqlUserDao(Connection connection) {
        this.connection = connection;
    }


    public PostgreSqlUserDao() throws SQLException, NamingException {
        this.connection = DB.getInstance().getConnection();
        logger.info("Connection created!");
    }

    @Override
    public int createUser(User user) throws SQLException {

        if (user.getLogin().length() > 0 && user.getPassword().length() > 0 && user.getEmail().length() > 0 && user.getFirstname().length() > 0 && user.getLastname().length() > 0) {
            String sql = "SELECT id FROM USERS WHERE login = ?";
            try(PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1,user.getLogin());
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        return -1;
                    }
                }

            }
            sql = "INSERT INTO USERS (firstname, lastname, email, login, password, locked) VALUES(?,?,?,?,?,false) RETURNING id";
            try(PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1,user.getFirstname());
                stm.setString(2,user.getLastname());
                stm.setString(3,user.getEmail());
                stm.setString(4,user.getLogin());
                stm.setString(5,user.getPassword());
                try (ResultSet rs = stm.executeQuery()) {
                    rs.next();
                    logger.info("User created!");
                    return rs.getInt(1);
                }

            }
            catch (SQLException e) {
                String err = "Запрос " + sql + " со следующими данными" + user  + " завершился с ошибкой: " + e.getMessage();
                logger.error(err);
                throw new SQLException(err);
            }
        }
        return 0;
    }

    @Override
    public User authUser(String login, String password) throws SQLException {
        String sql = "SELECT id, firstname, lastname, email, login, locked FROM USERS WHERE login = ? AND password = ? AND locked = false";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, login);
            stm.setString(2, password);
            try(ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    logger.info("User not found!");
                    return null;
                }

                logger.info("User found!");
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
            }


        }
        catch (SQLException e) {
            String err = "Запрос " + sql + " со следующими данными login:" + login  + ", password:" + password  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }

    @Override
    public int updateUser(User user) throws SQLException {
        String addSql = "";
        if (user.getPassword() != null) {
            addSql = ", password = ?";
        }

        String sql = "UPDATE USERS SET firstname = ?, lastname = ?, email = ?" + addSql + " WHERE id = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1,user.getFirstname());
            stm.setString(2,user.getLastname());
            stm.setString(3,user.getEmail());
            if (user.getPassword() != null && user.getPassword().length() > 0) {
                stm.setString(4,user.getPassword());
                stm.setInt(5,user.getId());
            }
            else {
                stm.setInt(4,user.getId());
            }
            logger.info("User updated!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + " со следующими данными" + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }

    @Override
    public int removeUser(int userId) throws SQLException {
        String sql = "DELETE USERS WHERE user_id = ?";
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1,userId);
            logger.info("User removed!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + "с user_id " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }

    @Override
    public int userUnlock(int userId) throws SQLException {
        String sql = "UPDATE USERS SET locked = false WHERE user_id = "+userId;
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            logger.info("User unlocked!");
            return stm.executeUpdate(sql);
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + "с user_id " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }

    @Override
    public int userLock(int userId) throws SQLException {
        String sql = "UPDATE USERS SET locked = true WHERE user_id = "+userId;
        try(PreparedStatement stm = connection.prepareStatement(sql)) {
            logger.info("User locked!");
            return stm.executeUpdate(sql);
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + "с user_id " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT id, firstname, lastname, email login, locked FROM USERS";
        List<User> users = new LinkedList<>();
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while(rs.next()) {
                users.add(new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getBoolean(6)));
            }
            logger.info("All user recived!");
            return users;
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
    }
}

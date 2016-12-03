package ru.innopolis.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public interface DaoFactory {
    public Connection getConnection()throws SQLException;
    public IUserDao getUserDao(Connection connection);
    public PostgreSqlArticleDao getArticleDao(Connection connection);
}

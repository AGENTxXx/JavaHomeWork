package ru.innopolis.dao;

import com.sun.corba.se.pept.transport.ConnectionCache;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.innopolis.config.Constants.*;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
@Component
public class DB {

    private static DataSource datasource;

    public DB() {
        datasource = new DataSource();
        datasource.setPoolProperties(setProperty());
    }

    /*Получение инстанса*/
    public static DB getInstance() {
        try {
            return DbSingletonManagerHolder.instance;
        } catch (ExceptionInInitializerError ex) {

        }
        return null;
    }

    /*Хэндлер по созданию инстанса*/
    private static class DbSingletonManagerHolder {
        private final static DB instance = new DB();
    }

    /*Метод по настройке Connection pool'а*/
    private PoolProperties setProperty() {
        PoolProperties p = new PoolProperties();
        p.setUrl(JDBC_URL);
        p.setDriverClassName(JDBC_DRIVER);
        p.setUsername(USER);
        p.setPassword(PASSWORD);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(6000);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;"
                        + "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");
        /*
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        */

        return p;
    }

    /*Получение соединения*/
    public static Connection getConnection() throws SQLException, NamingException {
        Connection con = datasource.getConnection();
        /*
        if (datasource.getConnection() != null && datasource.getConnection().isClosed() != true) {
            con = datasource.getConnection();
        }
        else {
            con = getInstance().getConnection();
        }
        */
        return con;
    }
}

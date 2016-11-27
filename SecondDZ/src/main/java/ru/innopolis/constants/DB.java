package ru.innopolis.constants;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.crypto.Data;

import com.sun.corba.se.pept.transport.ConnectionCache;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static ru.innopolis.constants.Constants.*;


/**
 * Created by Alexander Chuvashov on 23.11.2016.
 */
public class DB {
    private static DataSource datasource;
    private static Statement connection;

    public DB() {
        datasource = new DataSource();
        datasource.setPoolProperties(setProperty());
        //connection = setConnection();
    }

    public static DB getInstance() {
        try {
            return DbSingletonManagerHolder.instance;
        } catch (ExceptionInInitializerError ex) {

        }
        return null;
    }

    /* Private inner class responsible for instantiating the single instance of the singleton */
    private static class DbSingletonManagerHolder {
        private final static DB instance = new DB();
    }


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
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        return p;
    }

    /*
    private static Statement setConnection() {
        try {
            Connection conn = datasource.getConnection();
            return conn.createStatement();
        } catch (SQLException ex) {
            //Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    */

    public static Connection getConnection() throws SQLException, NamingException {
        Connection con = datasource.getConnection();
        return con;
    }
}

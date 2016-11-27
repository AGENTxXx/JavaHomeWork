package ru.innopolis.constants;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexander Chuvashov on 23.11.2016.
 */


/*Класс констант*/
public class Constants {
    public final static String JDBC_DRIVER = "org.postgresql.Driver";
    public final static String JDBC_URL = "jdbc:postgresql://localhost:5432/IS";
    public final static String USER = "postgres";
    public final static String PASSWORD = "";
    public final static String SALT = "perm";

    /**
     * Функция формирует md5 строку
     * @param md5 - строка, которая должна быть закодирована
     * @return
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

}


package ru.innopolis.modal;

import ru.innopolis.constants.DB;

import javax.naming.NamingException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 24.11.2016.
 */
public class ArticleModal {
    /**
     * Метод возвращает статью по указанному id
     * @param articleId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public Article getArticle(int articleId) throws SQLException, NamingException {
        if (articleId > 0) {
            String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.ts, a.checking, a.publish  FROM articles a " +
                    "LEFT JOIN users u ON u.id = a.user_id " +
                    "WHERE a.id = "+articleId;
            Connection cn = DB.getInstance().getConnection();
            try(Statement statement = cn.createStatement(); ResultSet rs = statement.executeQuery(sql);) {

                rs.next();
                return new Article(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getBoolean(8));
            }
        }
        return null;
    }

    /**
     * Метод создаёт новую статью от указанного пользователя
     * @param user_id
     * @param title
     * @param content
     * @throws SQLException
     * @throws NamingException
     */
    public void createArticle(int user_id, String title, String content) throws SQLException, NamingException {
        String sql = "INSERT INTO articles (user_id, title, content, checking, publish) VALUES(?,?,?,?,?)";
        try(Connection cn = DB.getInstance().getConnection();)
        {
            PreparedStatement statement = cn.prepareStatement(sql);
            statement.setInt(1, user_id);
            statement.setString(2, title);
            statement.setString(3, content);
            statement.setBoolean(4, false);
            statement.setBoolean(5, false);
            statement.executeUpdate();
        }
    }

    /**
     * Метод возвращает все статьи указанного пользователя
     * @param userId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public List<Article> getAllUserArticle(int userId) throws SQLException, NamingException {
        if (userId > 0) {
            String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.ts, a.checking, a.publish FROM articles a" +
                    " LEFT JOIN users u On u.id = a.user_id" +
                    " WHERE user_id = " + userId;
            Connection cn = DB.getInstance().getConnection();
            List<Article> articles = new LinkedList<>();
            try (Statement statement = cn.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
                while(rs.next()) {
                    articles.add(new Article(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getBoolean(8)));
                }
            }
            return articles;
        }
        return null;
    }

    /**
     * Метод возвращает 10 самых новых статей
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public List<Article> getLastArticles() throws SQLException, NamingException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.ts, a.checking, a.publish FROM articles  a " +
                " LEFT JOIN users u On u.id = a.user_id" +
                " ORDER BY id DESC LIMIT 10";

        Connection cn = DB.getInstance().getConnection();
        List<Article> articles = new LinkedList<>();
        try (Statement statement = cn.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
            while(rs.next()) {
                articles.add(new Article(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getBoolean(8)));
            }
        }
        return articles;
    }

    /**
     * Метод ищет статьи, которые удовлетваряют условию при поиске. Возвращает максимум 10 найденный по наличию ключевых слов в заголовке статьи
     * @param searchText
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public List<Article> getFindArticles(String searchText) throws SQLException, NamingException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.ts, a.checking, a.publish FROM articles  a " +
                " LEFT JOIN users u On u.id = a.user_id" +
                " WHERE LOWER(a.title) like LOWER('%"+searchText+"%') " +
                "ORDER BY id DESC LIMIT 10";

        Connection cn = DB.getInstance().getConnection();
        List<Article> articles = new LinkedList<>();
        try (Statement statement = cn.createStatement(); ResultSet rs = statement.executeQuery(sql);) {
            while(rs.next()) {
                articles.add(new Article(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getBoolean(7),rs.getBoolean(8)));
            }
        }
        return articles;
    }

    /**
     * Метод удаляет статью по id
     * @param articleId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int deleteArticle(int articleId) throws SQLException, NamingException {
        if (articleId > 0) {
            String sql = "DELETE FROM articles WHERE id = " + articleId;
            try (Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод меняет статус отображения статьи на true
     * @param articleId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int sendTopublishArticle(int articleId) throws SQLException, NamingException {
        if (articleId > 0) {
            String sql = "UPDATE articles SET checking=true WHERE id = " + articleId;
            try (Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }

    /**
     * Метод меняет переключатель "Отправка статьи на модерацию" в значение true
     * @param articleId
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public int publshArticle (int articleId) throws SQLException, NamingException {
        if (articleId > 0) {
            String sql = "UPDATE articles SET publish=true WHERE id = " + articleId + " AND checking=true";
            try (Connection cn = DB.getInstance().getConnection(); Statement statement = cn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }
        return -1;
    }
}

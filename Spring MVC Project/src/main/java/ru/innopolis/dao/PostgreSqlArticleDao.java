package ru.innopolis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.models.Article;

import javax.naming.NamingException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static ru.innopolis.config.Constants.closeAllTags;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public class PostgreSqlArticleDao implements IArticleDao {
    private final Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(PostgreSqlArticleDao.class);

    public PostgreSqlArticleDao(Connection connection) {
        this.connection = connection;
    }

    public PostgreSqlArticleDao() throws SQLException, NamingException {
        this.connection = DB.getInstance().getConnection();
        logger.info("Connection created!");
    }


    @Override
    public int createArticle(Article article) throws SQLException {
        article.setPreview(closeAllTags(article.getContent(), 1024));
        String sql = "INSERT INTO articles (user_id, title, content, preview) VALUES(?,?,?,?) RETURNING id";
        try(PreparedStatement stm = connection.prepareStatement(sql))
        {
            stm.setInt(1, article.getUserId());
            stm.setString(2, article.getTitle());
            stm.setString(3, article.getContent());
            stm.setString(4, article.getContent());

            logger.info("Article created");
            try(ResultSet rs = stm.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими данными" + article  + " завершился с ошибкой: " + e.getMessage());
        }
    }

    @Override
    public Article getArticle(int articleId) throws SQLException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.preview, a.ts, a.checking, a.is_moderation  FROM articles a " +
                "LEFT JOIN users u ON u.id = a.user_id " +
                "WHERE a.remove = false AND a.id = "+articleId;
        try(PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery();) {
            rs.next();
            logger.info("Article received!");
            return new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9));
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими articleId" + articleId  + " завершился с ошибкой: " + e.getMessage());
        }
    }

    @Override
    public int removeArticle(int articleId) throws SQLException {
        String sql = "UPDATE articles SET remove=true WHERE id = " + articleId;
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            logger.info("Article deleted!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими articleId" + articleId  + " завершился с ошибкой: " + e.getMessage());
        }
    }

    @Override
    public int updateArticle(Article article) throws SQLException {
        article.setPreview(closeAllTags(article.getContent(), 1024));
        String sql = "UPDATE articles SET title = ?, content = ?, preview = ?, is_moderation = false, checking = false WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1, article.getTitle());
            stm.setString(2, article.getContent());
            stm.setString(3, article.getPreview());
            stm.setInt(4, article.getId());
            logger.info("Article updated!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими данными " + article  + " завершился с ошибкой: " + e.getMessage());
        }
    }

    @Override
    public List<Article> getAllUserArticle(int userId) throws SQLException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.preview, a.ts, a.checking, a.is_moderation FROM articles a" +
                " LEFT JOIN users u On u.id = a.user_id" +
                " WHERE a.remove = false AND user_id = ? " +
                " ORDER BY a.id DESC";
        List<Article> articles = new LinkedList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql); ) {
            stm.setInt(1, userId);
            try (ResultSet rs = stm.executeQuery())
            {
                while(rs.next()) {
                    articles.add(new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9)));
                }
            }


        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими userId " + userId  + " завершился с ошибкой: " + e.getMessage());
        }

        logger.info("User articles reviced!");
        return articles;
    }

    @Override
    public List<Article> getLastArticles() throws SQLException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.preview, a.ts, a.checking, a.is_moderation FROM articles  a " +
                " LEFT JOIN users u On u.id = a.user_id" +
                " WHERE a.remove = false AND a.checking = true" +
                " ORDER BY id DESC LIMIT 10";

        List<Article> articles = new LinkedList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery();) {
            while(rs.next()) {
                articles.add(new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9)));
            }
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " завершился с ошибкой: " + e.getMessage());
        }

        logger.info("Last articles reviced!");
        return articles;
    }

    @Override
    public List<Article> getFindArticles(String searchText) throws SQLException {
        String sql = "SELECT a.id, a.user_id, u.login, a.title, a.content, a.preview, a.ts, a.checking, a.is_moderation FROM articles  a " +
                " LEFT JOIN users u On u.id = a.user_id" +
                " WHERE a.remove = false AND a.checking = true AND LOWER(a.title) like LOWER(?) " +
                "ORDER BY id DESC LIMIT 10";

        List<Article> articles = new LinkedList<>();
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setString(1,"%"+searchText+"%");
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                articles.add(new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9)));
            }
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " с текстом поиска: " + searchText + " завершился с ошибкой: " + e.getMessage());
        }
        logger.info("Find articles method is done!");
        return articles;
    }

    @Override
    public int moderationArticle(int articleId) throws SQLException {
        String sql = "UPDATE articles SET is_moderation=true WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql);)
        {
            stm.setInt(1,articleId);
            logger.info("Article to moderation site!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими articleId " + articleId  + " завершился с ошибкой: " + e.getMessage());
        }
    }

    @Override
    public int publshArticle(int articleId) throws SQLException {
        String sql = "UPDATE articles SET checking=true WHERE id = ? AND is_moderation=true";
        try (PreparedStatement stm = connection.prepareStatement(sql);) {
            stm.setInt(1,articleId);
            logger.info("Article to sent moderator for checking!");
            return stm.executeUpdate();
        }
        catch (SQLException e) {
            throw new SQLException("Запрос " + sql + " со следующими articleId " + articleId  + " завершился с ошибкой: " + e.getMessage());
        }
    }
}

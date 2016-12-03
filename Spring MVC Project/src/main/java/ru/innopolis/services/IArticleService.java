package ru.innopolis.services;

import ru.innopolis.models.Article;
import javax.naming.NamingException;
import java.sql.*;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
public interface IArticleService {
    int createArticle(Article article) throws SQLException;
    Article getArticle(int articleId) throws SQLException;
    int removeArticle(int articleId) throws SQLException;
    int updateArticle(Article article) throws SQLException;
    List<Article> getAllUserArticle(int userId) throws SQLException;
    List<Article> getLastArticles() throws SQLException;
    List<Article> getFindArticles(String searchText) throws SQLException;
    int moderationArticle(int articleId) throws SQLException;
    int publshArticle (int articleId) throws SQLException;
}

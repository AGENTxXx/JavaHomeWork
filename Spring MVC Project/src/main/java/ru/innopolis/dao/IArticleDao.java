package ru.innopolis.dao;

import ru.innopolis.models.Article;
import ru.innopolis.models.User;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 30.11.2016.
 */
public interface IArticleDao {
    int createArticle(Article article);
    Article getArticle(int articleId);
    int removeArticle(int articleId, User user);
    int updateArticle(Article article);
    List<Article> getAllUserArticle(int userId);
    List<Article> getLastArticles();
    List<Article> getFindArticles(String searchText);
    List<Article> getModerationArticle();
    int moderationArticle(int articleId);
    int publishArticle(int articleId);
}

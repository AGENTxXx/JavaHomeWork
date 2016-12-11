package ru.innopolis.services;

import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.Article;
import ru.innopolis.models.User;

import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
public interface IArticleService {
    int createArticle(Article article) throws ServiceHandlerException;
    Article getArticle(int articleId) throws ServiceHandlerException;
    int removeArticle(int articleId, User user) throws ServiceHandlerException;
    int updateArticle(Article article) throws ServiceHandlerException;
    List<Article> getAllUserArticle(int userId) throws ServiceHandlerException;
    List<Article> getLastArticles() throws ServiceHandlerException;
    List<Article> getFindArticles(String searchText) throws ServiceHandlerException;
    List<Article> getModerationArticle() throws ServiceHandlerException;
    int moderationArticle(int articleId) throws ServiceHandlerException;
    int publishArticle(int articleId) throws ServiceHandlerException;
}

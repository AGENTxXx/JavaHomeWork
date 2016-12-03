package ru.innopolis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.innopolis.dao.IArticleDao;
import ru.innopolis.models.Article;

import java.sql.*;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */

@Service
public class ArticleService implements IArticleService {
    /*Поле-инферыейс позволяющий подключить Dao-объект по работе со статьями */
    private IArticleDao articleDao;

    /*Инициализация Dao-Объекта*/
    @Autowired(required = true)
    @Qualifier(value = "articleDao")
    public void setArticleDao(IArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * Метод отвечает за создание статьи
     * @param article - статья
     * @return - 0, если не удалось создать иначе 1
     * @throws SQLException
     */
    @Override
    public int createArticle(Article article) throws SQLException {
        if (article.getUserId() > 0 && article.getTitle().length() > 0 && article.getContent().length() > 0) {
            return this.articleDao.createArticle(article);
        }
        return 0;
    }

    /**
     * Метод возвращает информацию по статье
     * @param articleId - ид. статьи
     * @return - null, если статья не найдена, иначе article
     * @throws SQLException
     */
    @Override
    public Article getArticle(int articleId) throws SQLException {
        if (articleId > 0) {
            return this.articleDao.getArticle(articleId);
        }

        return null;
    }

    /**
     * Метод отвечает за удаление статьи
     * @param articleId - ид. статьи
     * @return - 0, если не удалось удалить, иначе - 1
     * @throws SQLException
     */
    @Override
    public int removeArticle(int articleId) throws SQLException {
        if (articleId > 0) {
            return this.articleDao.removeArticle(articleId);
        }

        return 0;
    }

    /**
     * Метод отвечает за обновление информации о статье
     * @param article - новая информация о статье
     * @return - 0, если не удалось обновить, иначе - 1
     * @throws SQLException
     */
    @Override
    public int updateArticle(Article article) throws SQLException {
        if (article.getId() > 0 && article.getContent().length() > 0 && article.getTitle().length() > 0 ) {
            return this.articleDao.updateArticle(article);
        }

        return 0;
    }

    /**
     * Метод возвращает все статьи пользователя
     * @param userId - ид. пользователя
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws SQLException
     */
    @Override
    public List<Article> getAllUserArticle(int userId) throws SQLException {
        if (userId > 0) {
            return this.articleDao.getAllUserArticle(userId);
        }

        return null;
    }

    /**
     * Метод возвращает 10 самых последних статей
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws SQLException
     */
    @Override
    public List<Article> getLastArticles() throws SQLException {
        return this.articleDao.getLastArticles();
    }

    /**
     * Метод позволяющий найти статьи используя параметры поиска. Поиск осуществляется по полю title
     * Регистр букв не учитывается, используется маска %<строка_поиска>%
     * @param searchText - строка поиска.
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws SQLException
     */
    @Override
    public List<Article> getFindArticles(String searchText) throws SQLException {
        if (searchText.length() > 0) {
            return this.articleDao.getFindArticles(searchText);
        }

        return null;
    }

    /**
     * Метод помечает статью о том, что она отправлена на модерацию
     * @param articleId - ид. статьи
     * @return - 0, если не удалось пометить статью, что она отправлена на модерацию, иначе - 1
     * @throws SQLException
     */
    @Override
    public int moderationArticle(int articleId) throws SQLException {
        if (articleId > 0) {
            return this.articleDao.moderationArticle(articleId);
        }

        return 0;
    }

    /**
     * Метод отвечает за публикацию статьи. Данная статья становится видна другим пользователям
     * @param articleId - ид. статьи
     * @return - 0, если не удалось пометить статью, что она опубликована в общий доступ, иначе - 1
     * @throws SQLException
     */
    @Override
    public int publshArticle(int articleId) throws SQLException {
        if (articleId > 0) {
            return this.articleDao.publshArticle(articleId);
        }

        return 0;
    }
}

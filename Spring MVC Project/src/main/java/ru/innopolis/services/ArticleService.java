package ru.innopolis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.innopolis.dao.IArticleDao;
import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.Article;
import ru.innopolis.models.User;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */


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
     * @throws ServiceHandlerException
     */
    @Override
    public int createArticle(Article article) throws ServiceHandlerException {
        if (article.getUserId() > 0 && article.getTitle().length() > 0 && article.getContent().length() > 0) {
            try {
                return this.articleDao.createArticle(article);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }
        return 0;
    }

    /**
     * Метод возвращает информацию по статье
     * @param articleId - ид. статьи
     * @return - null, если статья не найдена, иначе article
     * @throws ServiceHandlerException
     */
    @Override
    public Article getArticle(int articleId) throws ServiceHandlerException {
        if (articleId > 0) {
            try {
                return this.articleDao.getArticle(articleId);
            } catch (IllegalArgumentException e) {
                throw new ServiceHandlerException("");
            }

        }

        return null;
    }

    /**
     * Метод отвечает за удаление статьи
     * @param articleId - ид. статьи
     * @return - 0, если не удалось удалить, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int removeArticle(int articleId, User user) throws ServiceHandlerException {
        if (articleId > 0) {
            try {
                return this.articleDao.removeArticle(articleId, user);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }

    /**
     * Метод отвечает за обновление информации о статье
     * @param article - новая информация о статье
     * @return - 0, если не удалось обновить, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int updateArticle(Article article) throws ServiceHandlerException {
        if (article.getId() > 0 && article.getContent().length() > 0 && article.getTitle().length() > 0 ) {
            try {
                return this.articleDao.updateArticle(article);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }

    /**
     * Метод возвращает все статьи пользователя
     * @param userId - ид. пользователя
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws ServiceHandlerException
     */
    @Override
    public List<Article> getAllUserArticle(int userId) throws ServiceHandlerException {
        if (userId > 0) {
            try {
                return this.articleDao.getAllUserArticle(userId);
            } catch (IllegalArgumentException e) {
                throw new ServiceHandlerException("");
            }
        }

        return null;
    }

    /**
     * Метод возвращает все статьи, которые необходимо промодерировать
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws ServiceHandlerException
     */
    @Override
    public List<Article> getModerationArticle() throws ServiceHandlerException {
        try {
            return this.articleDao.getModerationArticle();
        } catch (IllegalArgumentException e) {
            throw new ServiceHandlerException("");
        }
    }

    /**
     * Метод возвращает 10 самых последних статей
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws ServiceHandlerException
     */
    @Override
    public List<Article> getLastArticles() throws ServiceHandlerException {
        try {
            return this.articleDao.getLastArticles();
        } catch (IllegalArgumentException e) {
            throw new ServiceHandlerException("");
        }
    }

    /**
     * Метод позволяющий найти статьи используя параметры поиска. Поиск осуществляется по полю title
     * Регистр букв не учитывается, используется маска %<строка_поиска>%
     * @param searchText - строка поиска.
     * @return - null, если не удалось получить статьи, иначе - List<Article>
     * @throws ServiceHandlerException
     */
    @Override
    public List<Article> getFindArticles(String searchText) throws ServiceHandlerException {
        if (searchText.length() > 0) {
            try {
                return this.articleDao.getFindArticles(searchText);
            } catch (IllegalArgumentException e) {
                throw new ServiceHandlerException("");
            }
        }

        return new ArrayList<>();
    }

    /**
     * Метод помечает статью о том, что она отправлена на модерацию
     * @param articleId - ид. статьи
     * @return - 0, если не удалось пометить статью, что она отправлена на модерацию, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int moderationArticle(int articleId) throws ServiceHandlerException {
        if (articleId > 0) {
            try {
                return this.articleDao.moderationArticle(articleId);
            } catch (IllegalArgumentException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }

    /**
     * Метод отвечает за публикацию статьи. Данная статья становится видна другим пользователям
     * @param articleId - ид. статьи
     * @return - 0, если не удалось пометить статью, что она опубликована в общий доступ, иначе - 1
     * @throws ServiceHandlerException
     */
    @Override
    public int publishArticle(int articleId) throws ServiceHandlerException {
        if (articleId > 0) {
            try {
                return this.articleDao.publishArticle(articleId);
            } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
                throw new ServiceHandlerException("");
            }
        }

        return 0;
    }
}

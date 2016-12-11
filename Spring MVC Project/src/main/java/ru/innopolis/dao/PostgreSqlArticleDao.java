package ru.innopolis.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import ru.innopolis.models.Article;
import ru.innopolis.models.EArticle;
import ru.innopolis.models.User;

import javax.persistence.*;
import java.sql.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.Date;

import static ru.innopolis.config.Constants.closeAllTags;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */

public class PostgreSqlArticleDao implements IArticleDao {
    private static final Logger logger = LoggerFactory.getLogger(PostgreSqlArticleDao.class);

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("postgres");

    @Override
    public int createArticle(Article article) {
        EntityManager entityManager = emfactory.createEntityManager();

        article.setPreview(closeAllTags(article.getContent(), 1024));

        int id = 0;
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            Date ts = new Timestamp(System.currentTimeMillis());
            EArticle eArticle = new EArticle(article.getUserId(), article.getTitle(), article.getContent(), article.getPreview(), ts);
            entityManager.persist(eArticle);
            tx.commit();

            id = eArticle.getId();
        } catch (IllegalArgumentException e) {
            String err = "Метод updateArticle со следующими article: " + article + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод updateArticle со следующими article: " + article + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод updateArticle со следующими article: " + article + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод updateArticle со следующими article: " + article + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }
        
        return id;
    }

    @Override
    public Article getArticle(int articleId) {

        EntityManager entityManager = emfactory.createEntityManager();

        String SQL = "SELECT a.id, a.userId, a.title, a.content, a.preview, a.ts, u.login" +
                " FROM EArticle a " +
                " LEFT JOIN EUser u On u.id = a.userId" +
                " WHERE a.isRemove = false AND a.id = :articleId";

        Article article = null;
        try {
            Query query = entityManager.createQuery(SQL).setParameter("articleId",articleId);
    
            List<Object[]> result = query.getResultList();
    
            for (Object[] f: result) {
                article = new Article((int)f[0], (int)f[1], String.valueOf(f[2]), String.valueOf(f[3]), String.valueOf(f[4]), Timestamp.valueOf(f[5].toString()));
            }
        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }
        return article;

        /*
        String sql = "SELECT a.id, a.userId, u.login, a.title, a.content, a.preview, a.ts, a.isPublish, a.isModeration  FROM articles a " +
                "LEFT JOIN users u ON u.id = a.userId " +
                "WHERE a.isRemove = false AND a.id = "+articleId;
        try(PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {
            rs.next();
            logger.info("Article received!");
            return new Article(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getBoolean(8),rs.getBoolean(9));
        }
        catch (SQLException e) {
            String err = "Запрос " + sql + " со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
        */
    }

    @Override
    public int removeArticle(int articleId, User user) {
        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EArticle eArticle = entityManager.find(EArticle.class, articleId);

            if (user.getId() == eArticle.getId() || "admin".equals(user.getLogin())) {
                eArticle.setIsRemove(true);
                entityManager.merge(eArticle);
                tx.commit();
            }

        } catch (IllegalArgumentException e) {
            String err = "Метод removeArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод removeArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод removeArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод removeArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public int updateArticle(Article article) {
        article.setPreview(closeAllTags(article.getContent(), 1024));
        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EArticle eArticle = entityManager.find(EArticle.class, article.getId());
            eArticle.setContent(article.getContent());
            eArticle.setTitle(article.getTitle());
            eArticle.setPreview(article.getPreview());
            entityManager.merge(eArticle);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод updateArticle со следующими article: " + article  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод updateArticle со следующими article: " + article  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод updateArticle со следующими article: " + article  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод updateArticle со следующими article: " + article  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public List<Article> getAllUserArticle(int userId) {
        EntityManager entityManager = emfactory.createEntityManager();

        String SQL = "SELECT a.id, a.userId, a.title, a.content, a.preview, a.ts, a.isPublish, a.isModeration, u.login" +
                " FROM EArticle a " +
                " LEFT JOIN EUser u On u.id = a.userId" +
                " WHERE a.isRemove = false AND userId = :userId  " +
                " ORDER BY a.id DESC";

        List<Article> articles = new LinkedList<>();
        try {
            Query query = entityManager.createQuery(SQL).setParameter("userId",userId);

            List<Object[]> result = query.getResultList();

            for (Object[] f: result) {
                articles.add(new Article((int)f[0], (int)f[1], String.valueOf(f[2]), String.valueOf(f[3]), String.valueOf(f[4]), Timestamp.valueOf(f[5].toString()), Boolean.valueOf(f[6].toString()),Boolean.valueOf(f[7].toString())));
            }
        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }
        return articles;
    }

    @Override
    public List<Article> getLastArticles() {
        EntityManager entityManager = emfactory.createEntityManager();

        String SQL = "SELECT a.id, a.userId, a.title, a.content, a.preview, a.ts, u.login" +
                " FROM EArticle  a " +
                " LEFT JOIN EUser u On u.id = a.userId" +
                " WHERE a.isRemove = false AND a.isPublish = true ";

        List<Article> articles = new LinkedList<>();
        try {
            Query query = entityManager.createQuery(SQL);

            List<Object[]> result = query.getResultList();

            for (Object[] f: result) {
                articles.add(new Article((int)f[0], (int)f[1], String.valueOf(f[2]), String.valueOf(f[3]), String.valueOf(f[4]), Timestamp.valueOf(f[5].toString())));
            }
            logger.info("Last articles reviced!");

        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }

        return articles;
    }

    @Override
    public List<Article> getFindArticles(String searchText) {
        searchText = "%" + searchText.toLowerCase() + "%";
        EntityManager entityManager = emfactory.createEntityManager();

        String SQL = "SELECT a.id, a.userId, a.title, a.content, a.preview, a.ts, u.login" +
                " FROM EArticle  a " +
                " LEFT JOIN EUser u On u.id = a.userId" +
                " WHERE a.isRemove = false AND a.isPublish = true AND LOWER(a.title) like :title " +
                " ORDER BY a.id DESC";

        List<Article> articles = new LinkedList<>();
        try {
            Query query = entityManager.createQuery(SQL).setParameter("title", searchText);

            List<Object[]> result = query.getResultList();

            for (Object[] f : result) {
                articles.add(new Article((int) f[0], (int) f[1], String.valueOf(f[2]), String.valueOf(f[3]), String.valueOf(f[4]), Timestamp.valueOf(f[5].toString())));
            }
            logger.info("Find articles method is done!");
        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " с текстом поиска: " + searchText + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }
        return articles;
    }

    @Override
    public List<Article> getModerationArticle() {
        EntityManager entityManager = emfactory.createEntityManager();

        String SQL = "select id, title, preview, content from EArticle where isRemove = false AND isPublish = false AND isModeration = true";
        List<Article> articles = new LinkedList<>();

        try {
            Query query = entityManager.createQuery(SQL);

            List<Object[]> result = query.getResultList();

            for (Object[] f: result) {
                articles.add(new Article((int) f[0], String.valueOf(f[1]), String.valueOf(f[2]), String.valueOf(f[3])));
            }

        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " метода  getModerationArticle завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }

        return articles;

    }

    @Override
    public int moderationArticle(int articleId) {

        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EArticle eArticle = entityManager.find(EArticle.class, articleId);
            eArticle.setIsModeration(true);
            entityManager.merge(eArticle);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод moderationArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод moderationArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод moderationArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public int publishArticle(int articleId) {
        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EArticle eArticle = entityManager.find(EArticle.class, articleId);
            eArticle.setIsPublish(true);
            entityManager.merge(eArticle);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод publishArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод publishArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод publishArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод publishArticle со следующими articleId: " + articleId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }
}

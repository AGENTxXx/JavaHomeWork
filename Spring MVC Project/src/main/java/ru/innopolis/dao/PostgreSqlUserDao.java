package ru.innopolis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.models.EArticle;
import ru.innopolis.models.EUser;
import ru.innopolis.models.EUserRole;
import ru.innopolis.models.User;

import javax.naming.NamingException;
import javax.persistence.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 28.11.2016.
 */
public class PostgreSqlUserDao implements IUserDao {

    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("postgres");

    private static final Logger logger = LoggerFactory.getLogger(PostgreSqlUserDao.class);


    @Override
    public int createUser(User user) {
        EntityManager entityManager = emfactory.createEntityManager();

        int id = 0;
        String SQL = "SELECT id FROM EUser WHERE login = :login";

        try {
            Query query = entityManager.createQuery(SQL).setParameter("login",user.getLogin()).setMaxResults(1);
    
            List result = query.getResultList();
    
            if (!result.isEmpty()) {
                return -1;
            }
            
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EUser eUser = new EUser(user.getFirstname(), user.getLastname(), user.getEmail(), user.getLogin(), user.getPassword());
            entityManager.persist(eUser);
            EUserRole eUserRole = new EUserRole(user.getLogin(),"ROLE_USER");
            entityManager.persist(eUserRole);
            tx.commit();
            id = eUser.getId();

        } catch (IllegalArgumentException e) {
            String err = "Метод createUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод createUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод createUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод createUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {

            entityManager.close();
        }

        return id;

    }

    @Override
    public User authUser(String login, String password) {
        return null;
        /*
        String sql = "SELECT id, firstname, lastname, email, login, isLocked FROM USERS WHERE login = ? AND password = ? AND isLocked = false";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, login);
            stm.setString(2, password);
            try(ResultSet rs = stm.executeQuery()) {
                if (!rs.next()) {
                    logger.info("User not found!");
                    return null;
                }

                logger.info("User found!");
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBoolean(6));
            }


        }
        catch (SQLException e) {
            String err = "Запрос " + sql + " со следующими данными login:" + login  + ", password:" + password  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new SQLException(err);
        }
        */
    }

    @Override
    public int updateUser(User user) {

        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EUser eUser = entityManager.find(EUser.class, user.getId());
            eUser.setFirstname(user.getFirstname());
            eUser.setLastname(user.getLastname());
            eUser.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                eUser.setPassword(user.getPassword());
            }
    
            entityManager.persist(eUser);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод updateUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод updateUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод updateUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод updateUser со следующими user: " + user  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public int removeUser(int userId) {

        EntityManager entityManager = emfactory.createEntityManager();
        
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EUser eUser = entityManager.find(EUser.class, userId);
            entityManager.remove(eUser);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод removeUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод removeUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод removeUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод removeUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;

    }

    @Override
    public int userUnlock(int userId) {

        EntityManager entityManager = emfactory.createEntityManager();
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            EUser eUser = entityManager.find(EUser.class, userId);
            eUser.setIsLocked(false);
            entityManager.merge(eUser);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод userUnlock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод userUnlock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод userUnlock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод userUnlock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public int userLock(int userId) {

        EntityManager entityManager = emfactory.createEntityManager();

        EUser eUser = null;
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            eUser = entityManager.find(EUser.class, userId);
            eUser.setIsLocked(true);
            entityManager.merge(eUser);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод userLock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод userLock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод userLock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод userLock со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return 1;
    }

    @Override
    public List<User> getAllUsers() {
        EntityManager entityManager = emfactory.createEntityManager();

        List<User> users = new LinkedList<>();
        try {
            TypedQuery<EUser> result = entityManager.createQuery("from EUser", EUser.class);
    
            for (EUser user: result.getResultList()) {
                users.add(new User(user.getId(),user.getFirstname(), user.getLastname(), user.getEmail(), user.getLogin(),user.getIsLocked()));
            }
        } catch (IllegalArgumentException e) {
            String err = "Запрос from EUser метода  getAllUsers завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }
        return users;

    }

    @Override
    public User getUser(int userId) {
        EntityManager entityManager = emfactory.createEntityManager();

        EUser eUser = null;
        try {
            EntityTransaction tx = entityManager.getTransaction();
            if (!tx.isActive()) {
                tx.begin();
            }
            eUser = entityManager.find(EUser.class, userId);
            tx.commit();
        } catch (IllegalArgumentException e) {
            String err = "Метод getUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } catch (IllegalStateException e) {
            String err = "Метод getUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalStateException(e);
        } catch (RollbackException e) {
            String err = "Метод getUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new RollbackException(e);
        } catch (PersistenceException e) {
            String err = "Метод getUser со следующими userId: " + userId  + " завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new PersistenceException(e);
        } finally {
            entityManager.close();
        }

        return new User(eUser.getId(), eUser.getFirstname(),eUser.getLastname(),eUser.getEmail(),eUser.getLogin(),eUser.getIsLocked());
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager entityManager = emfactory.createEntityManager();
        
        String SQL = "from EUser where login = :username";
        EUser eUser = null;

        try {
            TypedQuery<EUser> query = entityManager.createQuery(SQL, EUser.class).setParameter("username",username).setMaxResults(1);
            eUser = query.getSingleResult();

        } catch (IllegalArgumentException e) {
            String err = "Запрос " + SQL + " метода  getUserByUsername завершился с ошибкой: " + e.getMessage();
            logger.error(err);
            throw new IllegalArgumentException(e);
        } finally {
            entityManager.close();
        }

        return new User(eUser.getId(),eUser.getFirstname(), eUser.getLastname(), eUser.getEmail(), eUser.getLogin(),eUser.getIsLocked());
    }
}

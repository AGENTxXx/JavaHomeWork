package ru.innopolis;

import ru.innopolis.dao.PostgreSqlArticleDao;
import ru.innopolis.dao.PostgreSqlUserDao;
import ru.innopolis.models.Article;
import ru.innopolis.models.User;
import ru.innopolis.services.ArticleService;
import ru.innopolis.services.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 10.12.2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        /*
        ArticleService as = new ArticleService();
        as.setArticleDao(new PostgreSqlArticleDao());
        List<Article> articles =  as.getLastArticles();
        List<Article> userArticles = as.getAllUserArticle(22);
        Article article = as.getArticle(articles.get(0).getId());
        article.setTitle("Nanana");

        int a = as.moderationArticle(articles.get(0).getId());
        int b = as.publishArticle(articles.get(0).getId());

        int c = as.removeArticle(3);

        List<Article> searchArticles = as.getFindArticles("без");
        List<Article> moderationArticle = as.getModerationArticle();
        */

        /*
        UserService userService = new UserService();
        userService.setUserDao(new PostgreSqlUserDao());

        List<User> users = userService.getAllUsers();

        User user = new User("firstName","lastName","email@eial.ru","login12345","password");
        int u = userService.createUser(user);

        String login = "";
        String password = "";

        //userService.authUser(login, password);

        User user2 = userService.getUser(19);

        user2.setFirstname("First");
        user2.setLastname("Last");

        int i = userService.updateUser(user2);

        int h = userService.removeUser(19);

        int j = userService.userUnlock(users.get(0).getId());

        int k = userService.userLock(users.get(0).getId());

        System.out.println("OK");
        */
    }
}

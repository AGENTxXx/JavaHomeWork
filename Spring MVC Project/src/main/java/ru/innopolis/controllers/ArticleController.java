package ru.innopolis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.models.Article;
import ru.innopolis.models.User;
import ru.innopolis.services.ArticleService;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
@Controller
public class ArticleController {

    /*Сервис для работы со статьями*/
    private ArticleService articleService;

    /*Инициализация сервиса*/
    @Autowired(required = true)
    @Qualifier(value = "articleService")
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }


    /**
     * Метод отвечающий за сохранения новой статьи в БД
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/article/create", method = {RequestMethod.POST })
    public String createArticleInDB(Model model, HttpServletRequest req) throws SQLException {

        int userId = ((User)req.getAttribute("user")).getId();
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        int articleId = this.articleService.createArticle(new Article(userId, title, content));
        String result ="{\"success\" : false, \"method\":\"create\"}";
        if (articleId > 0) {
            result ="{\"success\" : true, \"method\":\"create\", \"article_id\":" + articleId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод отвечающий за отобажение view для создания новой статьи
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping(value = "/article/create", method = {RequestMethod.GET })
    public String createArticle(Model model, HttpServletRequest req) throws SQLException {

        model.addAttribute("user", req.getAttribute("user"));
        return "articleCreate";
    }

    /**
     * Метод отвечает за обновление данных о статье
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @param articleId - идентификатор статьи
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/article/update/{id}", method = {RequestMethod.POST })
    public String updateArticleInDB(Model model, HttpServletRequest req, @PathVariable("id") int articleId ) throws SQLException {
        User user = (User)req.getAttribute("user");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Article article = new Article(articleId, user.getId(), title, content);

        String result ="{\"success\" : false, \"method\":\"update\"}";
        if (this.articleService.updateArticle(article) > 0) {
            result ="{\"success\" : true, \"method\":\"update\", \"articleId\":" + articleId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод отвечающий за отобажение view для редактирования созданной статьи
     * @param model - модель передачи данных во view
     * @param articleId - идентификатор статьи
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping(value = "/article/update/{id}", method = {RequestMethod.GET })
    public String updateArticle(Model model, @PathVariable("id") int articleId ) throws SQLException {
        Article article = this.articleService.getArticle(articleId);
        model.addAttribute("article", article);
        return "articleUpdate";
    }

    /**
     * Метод отвечает за установления в статье флага о том, что статья отправлена на модерацию
     * @param model - модель передачи данных во view
     * @param articleId - идентификатор статьи
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/article/moderation/{id}", method = {RequestMethod.POST })
    public String moderationArticle(Model model, @PathVariable("id") int articleId ) throws SQLException {
        int answer = this.articleService.moderationArticle(articleId);
        String result ="{\"success\" : false, \"method\":\"moderation\"}";
        if (answer > 0) {
            result ="{\"success\" : true, \"method\":\"moderation\", \"articleId\":" + articleId + "}";
        }

        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод возвращает все статьи, которые создал пользователь
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/articles/current")
    public String userArticles(Model model, HttpServletRequest req ) throws SQLException {
        User user = (User)req.getAttribute("user");
        List<Article> articles = this.articleService.getAllUserArticle(user.getId());
        model.addAttribute("articles", articles);
        model.addAttribute("edit", true);
        return "articleList";
    }

    /**
     * Метод отвечает за удаление статьи (помечает флаг таблицы articles remove в true)
     * @param model - модель передачи данных во view
     * @param articleId - идентификатор статьи
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/article/remove/{id}", method = {RequestMethod.POST })
    public String removeArticle(Model model, @PathVariable("id") int articleId) throws SQLException {

        String result ="{\"success\" : false, \"method\":\"remove\"}";
        if (this.articleService.removeArticle(articleId) > 0) {
            result ="{\"success\" : true, \"method\":\"remove\", \"articleId\":" + articleId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод отображает 10 самых новых опубликованных на сайте статей
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping(value = { "/", "/articles" })
    public String getLastArticles(Model model, HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        List<Article> articles;
        if (req.getAttribute("search_article") != null) {
            articles = articleService.getFindArticles(req.getAttribute("search_article").toString());
        }
        else {
            articles = articleService.getLastArticles();
        }
        model.addAttribute("articles", articles);
        model.addAttribute("edit", false);
        return "articleList";
    }


    @RequestMapping(value = {"/articles"}, method = {RequestMethod.POST })
    public String getSearchArticles(Model model, HttpServletRequest req) throws SQLException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        List<Article> articles = articleService.getFindArticles(req.getParameter("search_article").toString());
        model.addAttribute("articles", articles);
        model.addAttribute("searchArticle", req.getParameter("search_article"));
        model.addAttribute("edit", false);
        return "articleList";
    }


    @RequestMapping(value = {"/article/{id}"})
    public String getArticle(Model model, HttpServletRequest req, @PathVariable("id") int articleId) throws SQLException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        Article article = articleService.getArticle(articleId);
        model.addAttribute("article", article);
        model.addAttribute("edit", false);
        return "articleOpen";
    }
}

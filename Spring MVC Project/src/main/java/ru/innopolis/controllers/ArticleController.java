package ru.innopolis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.Article;
import ru.innopolis.models.User;
import ru.innopolis.services.ArticleService;
import ru.innopolis.services.UserService;

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

    /*Сервис для работы с пользователями*/
    private UserService userService;

    /*Инициализация сервиса*/
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * Метод отвечающий за сохранения новой статьи в БД
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/article/create", method = {RequestMethod.POST })
    public String createArticleInDB(Model model, HttpServletRequest req) {

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        int articleId = 0;
        String result ="";
        try {
            User user = this.userService.getUserByUsername(req.getAttribute("username").toString());
            articleId = this.articleService.createArticle(new Article(user.getId(), title, content));
            if (articleId > 0) {
                result ="{\"success\" : true, \"method\":\"create\", \"article_id\":" + articleId + "}";
            } else {
                result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException e) {
            result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    public String createArticle(Model model, HttpServletRequest req) {

        //model.addAttribute("user", req.getAttribute("username"));
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
    public String updateArticleInDB(Model model, HttpServletRequest req, @PathVariable("id") int articleId ) {
        //User user = (User)req.getAttribute("user");


        String title = req.getParameter("title");
        String content = req.getParameter("content");

        String result ="";
        try {
            User user = this.userService.getUserByUsername(req.getAttribute("username").toString());
            Article article = new Article(articleId, user.getId(), title, content);
            if (this.articleService.updateArticle(article) > 0) {
                result ="{\"success\" : true, \"method\":\"update\", \"articleId\":" + articleId + "}";
            } else {
                result ="{\"success\" : false, \"method\":\"update\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"update\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    public String updateArticle(Model model, @PathVariable("id") int articleId ) {
        Article article = null;
        try {
            article = this.articleService.getArticle(articleId);
            model.addAttribute("article", article);
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }

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
    public String moderationArticle(Model model, @PathVariable("id") int articleId ) {
        int answer = 0;
        String result ="";
        try {
            answer = this.articleService.moderationArticle(articleId);
            if (answer > 0) {
                result ="{\"success\" : true, \"method\":\"moderation\", \"articleId\":" + articleId + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"moderation\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"update\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    public String userArticles(Model model, HttpServletRequest req ) {
        //User user = (User)req.getAttribute("user");

        List<Article> articles = null;
        try {
            User user = this.userService.getUserByUsername(req.getAttribute("username").toString());
            articles = this.articleService.getAllUserArticle(user.getId());
            model.addAttribute("articles", articles);
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
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
    public String removeArticle(Model model, @PathVariable("id") int articleId, HttpServletRequest req) {


        String result ="{\"success\" : false, \"method\":\"remove\"}";
        try {
            User user = this.userService.getUserByUsername(req.getAttribute("username").toString());
            if (this.articleService.removeArticle(articleId, user) > 0) {
                result ="{\"success\" : true, \"method\":\"remove\", \"articleId\":" + articleId + "}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"remove\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    public String getLastArticles(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }
        List<Article> articles = null;
        try {
            if (req.getAttribute("search_article") != null) {
                articles = articleService.getFindArticles(req.getAttribute("search_article").toString());
            } else {
                articles = articleService.getLastArticles();
            }
            model.addAttribute("articles", articles);
        } catch (ServiceHandlerException e) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");

        }
        model.addAttribute("edit", false);
        return "articleList";
    }


    @RequestMapping(value = {"/articles"}, method = {RequestMethod.POST })
    public String getSearchArticles(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        List<Article> articles = null;
        try {
            articles = articleService.getFindArticles(req.getParameter("search_article"));
            model.addAttribute("articles", articles);
            model.addAttribute("searchArticle", req.getParameter("search_article"));
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        model.addAttribute("edit", false);
        return "articleList";
    }


    @RequestMapping(value = {"/articles/{id}"})
    public String getArticle(Model model, HttpServletRequest req, @PathVariable("id") int articleId) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        Article article = null;
        try {
            article = articleService.getArticle(articleId);
            model.addAttribute("article", article);
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        model.addAttribute("edit", false);
        return "articleOpen";
    }



    /**
     * Метод список статей, которые необходимо отмодерировать
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/admin/articles/moderation")
    public String adminModeration(Model model, HttpServletRequest req) {
        model.addAttribute("user", req.getAttribute("user"));
        List<Article> articles = null;
        try {
            articles = articleService.getModerationArticle();
            model.addAttribute("articles", articles);
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        return "articleListManager";
    }

    /**
     * Метод список статей, которые необходимо отмодерировать
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/admin/article/publish/{id}")
    public String adminArticlePublish(Model model, HttpServletRequest req, @PathVariable("id") int articleId) {
        String result ="{\"success\" : false, \"method\":\"remove\"}";
        int answer = 0;
        try {
            answer = this.articleService.publishArticle(articleId);
            if (answer > 0) {
                result ="{\"success\" : true, \"method\":\"publish\", \"articleId\":" + articleId + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"publish\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"publish\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }
}

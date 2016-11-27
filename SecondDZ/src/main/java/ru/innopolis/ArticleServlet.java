package ru.innopolis;

import ru.innopolis.modal.Article;
import ru.innopolis.modal.ArticleModal;
import ru.innopolis.modal.User;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 24.11.2016.
 */
public class ArticleServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            HttpSession session = req.getSession(true);
            if (session.getAttribute("user")!= null) {
                User user = (User)session.getAttribute("user");
                req.setAttribute("login", user.getLogin());
                req.setAttribute("userId", user.getId());
                authUser(req, resp);
            }
            else {
                notAuthUser(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    public void notAuthUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NamingException, ServletException, IOException {
        String[] path = req.getRequestURI().split("/");
        if (path[2].equals("articleList")) {
            ArticleModal articleModal = new ArticleModal();
            if (path.length > 3 && Integer.parseInt(path[3])>0) {
                Article article = articleModal.getArticle(Integer.parseInt(path[3]));
                req.setAttribute("title",article.getTitle());
                req.setAttribute("content",article.getContent());
                getServletContext().getRequestDispatcher("/article.jsp").forward(req, resp);
            }
            else {
                List<Article> articles = articleModal.getLastArticles();
                req.setAttribute("edit", false);
                req.setAttribute("articles",articles);
                getServletContext().getRequestDispatcher("/articleList.jsp").forward(req, resp);
            }

        }
        else {
            resp.sendRedirect("/MyApp/");
        }

    }

    public void authUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, NamingException, ServletException, IOException {
        ArticleModal articleModal = new ArticleModal();
        String[] path = req.getRequestURI().split("/");
        if (path[2].equals("myArticles")) {
            if (path.length > 3 && path[3].equals("edit")) {
                Article article = articleModal.getArticle(Integer.parseInt(path[4]));
                req.setAttribute("id",article.getId());
                req.setAttribute("title",article.getTitle());
                req.setAttribute("content",article.getContent());
                getServletContext().getRequestDispatcher("/articleEdit.jsp").forward(req, resp);
            }
            else {
                List<Article> articles = articleModal.getAllUserArticle(Integer.parseInt(req.getAttribute("userId").toString()) );
                req.setAttribute("edit", true);
                req.setAttribute("articles",articles);
                getServletContext().getRequestDispatcher("/articleList.jsp").forward(req, resp);
            }
        }
        else if (path[2].equals("articleList")) {
            notAuthUser(req, resp);
        }
        else if (path[2].equals("createArticle")) {
            getServletContext().getRequestDispatcher("/articleCreate.jsp").forward(req, resp);
        }
    }
}

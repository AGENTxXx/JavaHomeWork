package ru.innopolis;

import ru.innopolis.constants.DB;
import ru.innopolis.modal.Article;
import ru.innopolis.modal.ArticleModal;
import ru.innopolis.modal.User;
import ru.innopolis.modal.UserModal;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

import static ru.innopolis.constants.Constants.*;
/**
 * Created by Alexander Chuvashov on 23.11.2016.
 */
public class StartPageServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);


        if (req.isRequestedSessionIdValid() && req.getParameter("logout") != null && req.getParameter("logout").equals("true")) {
            session.removeAttribute("token");
            session.removeAttribute("user");
            session.invalidate();
            resp.sendRedirect("/MyApp/");
            return;
        }

        ArticleModal articleModal = new ArticleModal();
        List<Article> articles;
        try {
            if (req.getParameter("search_article") == null) {
                articles = articleModal.getLastArticles();
            }
            else {
                articles = articleModal.getFindArticles(req.getParameter("search_article"));
            }

            req.setAttribute("articles", articles);
            req.setAttribute("edit", false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (session != null && session.getAttribute("token") != null) {
            User user = (User)session.getAttribute("user");
            req.setAttribute("login", user.getLogin());
        }

        /*
        int i=3;
        req.setAttribute("i", i);
        req.setAttribute("title", "Заголовок!");
        req.getRequestDispatcher("/articlePreview.jsp");
        */
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession(true);

        ArticleModal articleModal = new ArticleModal();
        List<Article> articles;
        try {
            if (req.getParameter("search_article") == null) {
                articles = articleModal.getLastArticles();
            }
            else {
                articles = articleModal.getFindArticles(req.getParameter("search_article"));

            }

            req.setAttribute("articles", articles);
            req.setAttribute("edit", false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (session.getAttribute("token") != null) {
            User user = (User)session.getAttribute("user");
            req.setAttribute("login", user.getLogin());
        }

        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }


}

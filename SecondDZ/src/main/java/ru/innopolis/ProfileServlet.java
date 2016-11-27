package ru.innopolis;

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
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */

/*Сервлет отвечает за редактирование профиля сущности "Пользователь"*/
public class ProfileServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(true);

        User user = (User)session.getAttribute("user");
        req.setAttribute("id", user.getId());
        req.setAttribute("firstname", user.getFirstname());
        req.setAttribute("lastname", user.getLastname());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("login", user.getLogin());
        getServletContext().getRequestDispatcher("/profileEdit.jsp").forward(req, resp);

    }
}

package ru.innopolis;

import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;

import ru.innopolis.modal.ArticleModal;
import ru.innopolis.modal.User;
import ru.innopolis.modal.UserModal;

import static ru.innopolis.constants.Constants.MD5;

/**
 * Created by Alexander Chuvashov on 23.11.2016.
 */

/*Сервлет отвечает за Ajax-запросы к севреру*/
public class AjaxServlet extends HttpServlet {

    /**
     * Метод обрабатывает запросы переданные с помощью ajax и использующий метод передачи данных - post
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String method = req.getParameter("method");

        PrintWriter out = resp.getWriter();
        StringBuffer sb = new StringBuffer();
        UserModal userModal = new UserModal();
        HttpSession session;
        User user;
        switch (method) {
            case "auth":
                try {
                    session = req.getSession(true);
                    if (session.getAttribute("token") == null) {
                        user = userModal.authUser(req.getParameter("login"), req.getParameter("password"));
                        if (user != null) {
                            Calendar cal = Calendar.getInstance();
                            session.setAttribute("token", MD5(Double.toString(cal.getTimeInMillis() + Math.random())));
                            session.setAttribute("user", user);

                            sb.append("{\"auth\": true, \"method\":\"auth\"}");
                        }
                    }
                    else {
                        sb.append("{\"auth\": true, \"method\":\"auth\"}");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }

                break;
            case "reg":

                String firstName = req.getParameter("firstname");
                String lastName = req.getParameter("lastname");
                String email = req.getParameter("email");
                String login = req.getParameter("login");
                String password = req.getParameter("password");

                try {
                    if (userModal.regUser(firstName,lastName,email,login,password) > -1) {
                        sb.append("{\"registration\": true, \"method\":\"reg\"}");

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                break;
            case "edit":
                session = req.getSession(true);
                user = (User)session.getAttribute("user");
                try {
                    userModal.editUser(user.getId(),req.getParameter("firstname"),req.getParameter("lastname"),req.getParameter("email"),req.getParameter("password"));
                    sb.append("{\"success\": true, \"method\":\"edit\"}");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                break;
            case "create":
                session = req.getSession(true);
                user = (User)session.getAttribute("user");
                ArticleModal articleModal = new ArticleModal();
                try {
                    articleModal.createArticle(Integer.parseInt(user.getId()),req.getParameter("title"),req.getParameter("content"));
                    sb.append("{\"success\": true, \"method\":\"create\"}");
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
                break;

        }
        resp.setContentType("application/json");
        resp.setHeader("Cache-Control", "no-cache");
        out.println(sb);
    }
}

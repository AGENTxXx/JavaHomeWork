package ru.innopolis.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.innopolis.models.User;
import ru.innopolis.services.UserService;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import static ru.innopolis.config.Constants.MD5;
import static ru.innopolis.config.Constants.SALT;

/**
 * Created by Alexander Chuvashov on 27.11.2016.
 */
@Controller
public class UserController {

    /*Сервис для работы с пользователями*/
    private UserService userService;

    /*Инициализация сервиса*/
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод отвечающий за регестрацию нового пользователя
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value = "/user/create", method = {RequestMethod.POST })
    public String createUser(Model model, HttpServletRequest req) throws SQLException {

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String result ="{\"success\" : false, \"method\":\"create\"}";
        int answer = userService.createUser(new User(firstname, lastname, email, login, MD5(MD5(password) + SALT)));
        if (answer > 0) {
            result = "{\"success\" : true, \"method\":\"create\"}";
        }
        else if (answer == -1) {
            result = "{\"success\" : false, \"method\":\"create\", \"error\":\"exist\"}";
        }
        model.addAttribute("result", result);

        return "ajaxResult";
    }

    /**
     * Метод отвечает за автаризацию пользователя
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping(value="/user/auth", method = {RequestMethod.POST })
    public String authUser(Model model, HttpServletRequest req) throws SQLException {
        Gson gson = new Gson();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = userService.authUser(login, password);

        if (user != null) {
            HttpSession session = req.getSession(false);
            Calendar cal = Calendar.getInstance();
            session.setAttribute("token", MD5(Double.toString(cal.getTimeInMillis() + Math.random())));
            session.setAttribute("user", user);
        }

        String result ="{\"success\" : false, \"method\":\"auth\"}";
        if (user != null) {

            result = "{\"success\" : true, \"method\":\"auth\", \"user\":" + gson.toJson(user) + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Модель отвечает за обновление данных о пользователе
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/user/update")
    public String updateUser(Model model, HttpServletRequest req) throws SQLException {

        User user = (User)req.getAttribute("user");

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        String result ="{\"success\" : false, \"method\":\"update\", \"user_id\":" + user.getId() + "}";
        if (userService.updateUser(new User(user.getId(),firstName,lastName,email,password)) > 0) {
            HttpSession session = req.getSession(false);
            User oldUser = (User)session.getAttribute("user");
            session.removeAttribute("user");
            User newUser = new User(firstName, lastName, email, oldUser.getLogin(), oldUser.getPassword());
            newUser.setId(oldUser.getId());
            session.setAttribute("user", newUser);
            result = "{\"success\" : true, \"method\":\"update\", \"user_id\":" + user.getId() + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     *
     * @param model - модель передачи данных во view
     * @param userId- идентификатор пользователя
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/user/remove/{id}")
    public String removeUser(Model model, @PathVariable("id") int userId) throws SQLException {


        String result ="{\"success\" : false, \"method\":\"remove\", \"user_id\":" + userId + "}";
        if (userService.removeUser(userId) > 0) {
            result = "{\"success\" : true, \"method\":\"remove\", \"user_id\":" + userId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод возвращает представление отображения информации о пользователе
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/user/profile")
    public String userProfile(Model model, HttpServletRequest req) throws SQLException {
        model.addAttribute("user", req.getAttribute("user"));
        return "userProfile";
    }

    /**
     * Метод отвечает за разблокировку пользователя по id
     * @param model - модель передачи данных во view
     * @param userId - ид. пользователя
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/api/user/unlock/{id}")
    public String userUnlock(Model model, @PathVariable("id") int userId) throws SQLException {

        String result ="{\"success\" : false, \"method\":\"unlock\", \"user_id\":" + userId + "}";
        if (userService.userUnlock(userId) > 0) {
            result = "{\"success\" : true, \"method\":\"unlock\", \"user_id\":" + userId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод отвечает за блокировку пользователя по id
     * @param model - модель передачи данных во view
     * @param userId - ид. пользователя
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/api/user/lock/{id}")
    public String userLock(Model model, @PathVariable("id") int userId) throws SQLException {

        String result ="{\"success\" : false, \"method\":\"lock\", \"user_id\":" + userId + "}";
        if (userService.userLock(userId) > 0) {
            result = "{\"success\" : true, \"method\":\"lock\", \"user_id\":" + userId + "}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /**
     * Метод отвечает за вывод всех пользователей
     * @param model - модель передачи данных во view
     * @return - возвращает имя view
     * @throws SQLException
     * @throws NamingException
     */
    @RequestMapping("/admin/users")
    public String getAllUsers(Model model) throws SQLException {

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList";
    }

    /**
     * Метод отвечает за logout пользователя (удаляет информацию о сессии
     * @param req - HttpServletResponse
     * @param resp - HttpServletResponse
     * @throws IOException
     */
    @RequestMapping("/user/logout")
    public void userLogout(HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        HttpSession session = req.getSession(false);
        session.removeAttribute("token");
        session.removeAttribute("user");
        session.invalidate();
        resp.sendRedirect("/MyApp/articles");
        return;
    }

}

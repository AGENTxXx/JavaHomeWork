package ru.innopolis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.innopolis.exception.ServiceHandlerException;
import ru.innopolis.models.User;
import ru.innopolis.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
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
    public String createUser(Model model, HttpServletRequest req) {

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String result ="{\"success\" : false, \"method\":\"create\"}";
        int answer = 0;
        try {
            answer = userService.createUser(new User(firstname, lastname, email, login, MD5(MD5(password) + SALT)));
            if (answer > 0) {
                result = "{\"success\" : true, \"method\":\"create\"}";
            }
            else if (answer == -1) {
                result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Пользователь с таким логином уже существует\", \"error_type\":\"exist\"}";
            } else {
                result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
        }
        /*
        if (answer > 0) {
            result = "{\"success\" : true, \"method\":\"create\"}";
        }
        else if (answer == -1) {
            result = "{\"success\" : false, \"method\":\"create\", \"error\":\"exist\"}";
        }
        */
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
    /*
    @RequestMapping(value="/user/auth", method = {RequestMethod.POST })
    public String authUser(Model model, HttpServletRequest req) {
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
    */

    /**
     * Модель отвечает за обновление данных о пользователе
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/user/update")
    public String updateUser(Model model, HttpServletRequest req) {

        User user = null;
        String result ="";
        try {
            user = userService.getUserByUsername(req.getAttribute("username").toString());
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"update\", \"error\":\"Ошибка получения данных о пользователе\"}";
            model.addAttribute("result", result);
            return "ajaxResult";
        }

        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        
        try {
            if (userService.updateUser(new User(user.getId(),firstName,lastName,email,password)) > 0) {
                /*
                HttpSession session = req.getSession(false);
                User oldUser = (User)session.getAttribute("user");
                session.removeAttribute("user");
                User newUser = new User(firstName, lastName, email, oldUser.getLogin(), oldUser.getPassword());
                newUser.setId(oldUser.getId());
                session.setAttribute("user", newUser);
                */
                result = "{\"success\" : true, \"method\":\"update\", \"userId\":" + user.getId() + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"create\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"update\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    public String userProfile(Model model, HttpServletRequest req) {
        User user = null;
        try {
            user = userService.getUserByUsername(req.getAttribute("username").toString());
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        model.addAttribute("user", user);
        return "userProfile";
    }


    /**
     * Метод отвечает за logout пользователя (удаляет информацию о сессии
     * @param req - HttpServletResponse
     * @param resp - HttpServletResponse
     * @throws IOException
     */
    /*
    @RequestMapping("/user/logout")
    public void userLogout(HttpServletRequest req, HttpServletResponse resp ) throws IOException {
        HttpSession session = req.getSession(false);
        session.removeAttribute("token");
        session.removeAttribute("user");
        session.invalidate();
        resp.sendRedirect("/MyApp/articles");
        return;
    }
    */

    /**
     * Метод отвечает за вывод всех пользователей
     * @param model - модель передачи данных во view
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/admin/users")
    public String getAllUsers(Model model,HttpServletRequest req) {
        List<User> users = null;
        try {
            users = userService.getAllUsers();
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        //model.addAttribute("user", req.getAttribute("user"));
        model.addAttribute("users", users);
        return "userList";
    }

    /**
     * Метод возвращает данные о пользователе
     * @param model - модель передачи данных во view
     * @param req - HttpServletRequest
     * @return - возвращает имя view
     * @throws SQLException
     */
    @RequestMapping("/admin/profile")
    public String adminProfile(Model model, HttpServletRequest req) {
        User user = null;
        try {
            user = userService.getUserByUsername(req.getAttribute("username").toString());
        } catch (ServiceHandlerException serviceHandlerException) {
            model.addAttribute("error", "Сервер вернул ошибку. Попробуйте повторить попытку позже");
        }
        model.addAttribute("user", user);
        return "userProfile";
    }

    /**
     * Метод отвечает за разблокировку пользователя по id
     * @param model - модель передачи данных во view
     * @param userId - ид. пользователя
     * @return - возвращает ajax-шаблон с сформированными данными
     * @throws SQLException
     */
    @RequestMapping("/admin/user/unlock/{id}")
    public String userUnlock(Model model, @PathVariable("id") int userId) {

        String result ="{\"success\" : false, \"method\":\"unlock\", \"userId\":" + userId + "}";
        try {
            if (userService.userUnlock(userId) > 0) {
                result = "{\"success\" : true, \"method\":\"unlock\", \"userId\":" + userId + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"unlock\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"unlock\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    @RequestMapping("/admin/user/lock/{id}")
    public String userLock(Model model, @PathVariable("id") int userId) {

        String result ="{\"success\" : false, \"method\":\"lock\", \"userId\":" + userId + "}";
        try {
            if (userService.userLock(userId) > 0) {
                result = "{\"success\" : true, \"method\":\"lock\", \"userId\":" + userId + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"lock\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"lock\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
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
    @RequestMapping("/admin/user/remove/{id}")
    public String removeUser(Model model, @PathVariable("id") int userId) {


        String result ="{\"success\" : false, \"method\":\"remove\", \"userId\":" + userId + "}";
        try {
            if (userService.removeUser(userId) > 0) {
                result = "{\"success\" : true, \"method\":\"remove\", \"userId\":" + userId + "}";
            }
            else {
                result ="{\"success\" : false, \"method\":\"remove\", \"error\":\"Ошибка полученных данных\"}";
            }
        } catch (ServiceHandlerException serviceHandlerException) {
            result ="{\"success\" : false, \"method\":\"remove\", \"error\":\"Сервис временно не доступен. Попробуйте позже\"}";
        }
        model.addAttribute("result", result);
        return "ajaxResult";
    }

}

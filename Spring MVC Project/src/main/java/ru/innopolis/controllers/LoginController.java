package ru.innopolis.controllers;

/**
 * Created by Alexander Chuvashov on 08.12.2016.
 */
import org.springframework.security.authentication.DisabledException;
import org.springframework.ui.Model;
import ru.innopolis.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.innopolis.services.UserService;

import javax.servlet.http.HttpServletRequest;

import static ru.innopolis.config.Constants.MD5;
import static ru.innopolis.config.Constants.SALT;

@Controller
@RequestMapping("/user/login")
public class LoginController {

    @Autowired
    @Qualifier("authenticationManager")
    AuthenticationManager authenticationManager;


    /*Сервис для работы с пользователями*/
    private UserService userService;

    /*Инициализация сервиса*/
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /*
    @Autowired(required = true)
    @Qualifier(value = "authenticationManager")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }
    */

    /*
    @RequestMapping(method = RequestMethod.GET)
    //@ResponseBody
    public LoginStatus getStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser") && auth.isAuthenticated()) {
            return new LoginStatus(true, auth.getName());
        } else {
            return new LoginStatus(false, null);
        }
    }
    */

    @RequestMapping(method = RequestMethod.POST)
    //@ResponseBody
    public String login(Model model, HttpServletRequest req, @RequestParam("j_username") String username,
                             @RequestParam("j_password") String password) {

        password = MD5(MD5(password) + SALT);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        User user = new User(username);
        token.setDetails(user);

        String result ="{\"success\" : false, \"method\":\"auth\"}";

        try {
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            if (user != null) {
                result = "{\"success\" : true, \"method\":\"auth\", \"user\":\""+user.getLogin()+"\"}";
            }

            //return new LoginStatus(auth.isAuthenticated(), auth.getName());
        } catch (BadCredentialsException e) {
            result = "{\"success\" : false, \"method\":\"auth\", \"error\":\"Логин и/или пароль не верны!\"}";
        } catch (DisabledException e) {
            result = "{\"success\" : false, \"method\":\"auth\", \"error\":\"Пользователь заблокирован!\"}";
        }



        model.addAttribute("result", result);
        return "ajaxResult";
    }

    /*
    public class LoginStatus {

        private final boolean loggedIn;
        private final String username;

        public LoginStatus(boolean loggedIn, String username) {
            this.loggedIn = loggedIn;
            this.username = username;
        }

        public boolean isLoggedIn() {
            return loggedIn;
        }

        public String getUsername() {
            return username;
        }
    }
    */
}

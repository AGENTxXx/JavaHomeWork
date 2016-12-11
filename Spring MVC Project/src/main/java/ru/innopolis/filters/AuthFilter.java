package ru.innopolis.filters;

import org.hibernate.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.innopolis.models.User;
import ru.innopolis.services.UserService;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Alexander Chuvashov on 30.11.2016.
 */
@Component
public class AuthFilter implements Filter {


    /*Сервис для работы с пользователями*/
    private UserService userService;

    /*Инициализация сервиса*/
    @Autowired(required = true)
    @Qualifier(value = "userService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        SecurityContextImpl sci = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");

        if (sci != null) {

            UserDetails user = (UserDetails) sci.getAuthentication().getPrincipal();
            request.setAttribute("username",user.getUsername());
            if (user.getAuthorities().size() > 0) {
                request.setAttribute("userRole",user.getAuthorities().toArray()[0]);
            }

        }


        /*

        String url = req.getServletPath();
        String[] path = url.split("/");

        if ("/user/auth".equals(url) || "/user/create".equals(url) || "/user/login".equals(url) || (!"/articles/current".equals(url) && "articles".equals(path[1]))) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("token") == null) {
            res.sendRedirect("/MyApp/articles");
            return;
        }
        else {
            User user = (User)session.getAttribute("user");
            if (!"admin".equals(user.getLogin()) && "admin".equals(path[1])) {
                res.sendRedirect("/MyApp/articles");
                return;
            }
            request.setAttribute("user",user);
        }
        */

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

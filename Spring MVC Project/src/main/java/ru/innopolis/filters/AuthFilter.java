package ru.innopolis.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Alexander Chuvashov on 30.11.2016.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        String url = req.getServletPath();

        if ("/user/auth".equals(url) || "/user/create".equals(url)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("token") == null) {
            res.sendRedirect("/MyApp/articles");
            return;
        }
        else {
            request.setAttribute("user",session.getAttribute("user"));
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

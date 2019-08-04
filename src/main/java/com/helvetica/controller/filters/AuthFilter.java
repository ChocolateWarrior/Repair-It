package com.helvetica.controller.filters;

import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

//import static java.util.Objects.nonNull;

@Log4j2
@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    private final List<String> adminPaths = Arrays.asList("/repairit_war/index",
            "/repairit_war/logout",
            "/repairit_war/display",
            "/repairit_war/display/delete");
    private final List<String> authorizedPaths = Arrays.asList("/repairit_war/index",
            "/repairit_war/logout",
            "/repairit_war/display",
//            "/repairit_war/request",
            "/repairit_war/request");
    private final List<String> unauthorizedPaths = Arrays.asList("/repairit_war/index",
            "/repairit_war/login",
            "/repairit_war/registration");
    private Map<Role, List<String>> allowedPathPatterns = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPathPatterns.put(Role.USER, authorizedPaths);
        allowedPathPatterns.put(Role.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        System.out.println(uri);
        User user = (User) session.getAttribute("user");
        System.out.println(user);

        if (Objects.isNull(user)) {
            if (unauthorizedPaths.contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(
                        "/repairit_war/login");
                return;
            }
        }

        List<String> paths = allowedPathPatterns.getOrDefault(user.getAuthority(), unauthorizedPaths);

        if (paths.contains(uri)) {
            filterChain.doFilter(request, response);
            System.out.println("HERE");
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }

    }


    @Override
    public void destroy() {
    }
}
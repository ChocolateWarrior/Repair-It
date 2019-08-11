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
import java.util.stream.Collectors;

@Log4j2
@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    private final List<String> adminPaths = Arrays.asList("/index",
            "/logout",
            "/display",
            "/display/delete");
    private final List<String> authorizedPaths = Arrays.asList("/index",
            "/index/edit",
            "/index/payment",
            "/balance",
            "/logout",
            "/index/comment",
            "/index/complete",
            "/display",
            "/display-request",
            "/display-request/reject",
            "/display-request/edit",
            "/registration",
            "/login",
            "/display/delete",
            "/display/edit",
            "/request");
    private final List<String> unauthorizedPaths = Arrays.asList(
            "/login",
            "/registration",
            "/display-request",
            "/display-request/reject",
            "/display-request/edit");
    private Map<Role, List<String>> allowedPathPatterns = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        allowedPathPatterns.put(Role.USER, authorizedPaths);
        allowedPathPatterns.put(Role.MASTER, authorizedPaths);
        allowedPathPatterns.put(Role.ADMIN, adminPaths);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String uri = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        User user = (User) session.getAttribute("user");

        if (Objects.isNull(user)) {
            if (unauthorizedPaths.contains(uri)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.sendRedirect(request.getContextPath() +
                        request.getServletPath() +
                        "/login");
                return;
            }
        }

        List<String> paths = user.getAuthorities().stream()
                .flatMap(authority -> allowedPathPatterns.get(authority).stream())
                .distinct()
                .collect(Collectors.toList());

        if (paths.contains(uri)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(403);
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }

    }


    @Override
    public void destroy() {
    }
}
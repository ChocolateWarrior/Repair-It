package com.helvetica.services.filters;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.entity.Role;
import com.helvetica.model.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

//import static java.util.Objects.nonNull;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {


    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException {

        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) resp;

        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        @SuppressWarnings("unchecked")
        final AtomicReference<UserDao> dao=(AtomicReference<UserDao>)request.getServletContext().getAttribute("dao");

        final HttpSession session = request.getSession();

//        if(nonNull(session) &&
//        nonNull(session.getAttribute("username")) &&
//        nonNull(session.getAttribute("password"))) {

        if(session != null &&
                session.getAttribute("username") != null &&
                session.getAttribute("password") != null){
            final Role role = (Role) session.getAttribute("role");
            moveToMain(request,response, role);
        } else if(dao.get().findByUsernameAndPassword(username, password) != null){

            final Role role = dao.get().findByUsernameAndPassword(username, password).getRole();

            request.getSession().setAttribute("password", password);
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("role", role);

            moveToMain(request,response,role);
        } else {

            moveToMain(request, response, Role.UNKNOWN);
        }

        chain.doFilter(req, resp);
    }

    private void moveToMain(final HttpServletRequest request,
                            final HttpServletResponse response,
                            final Role role) throws ServletException, IOException{

        switch (role){
            case ADMIN:
                request.getRequestDispatcher("/WEB-INF/admin_page.jsp");
                break;

            case USER:
                request.getRequestDispatcher("/WEB-INF/user_page.jsp");
                break;

            case UNKNOWN:
                request.getRequestDispatcher("WEB_INF/view/login.jsp");
                break;
        }
    }


    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}

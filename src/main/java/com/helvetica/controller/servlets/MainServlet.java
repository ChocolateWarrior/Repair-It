package com.helvetica.controller.servlets;

import com.helvetica.controller.command.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class MainServlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();
    private static final Logger log = LogManager.getLogger();

    public void init(ServletConfig servletConfig){

        commands.put("/logout", new LogoutCommand());
        commands.put("/login", new LoginCommand());
        commands.put("/request", new RequestCommand());
        commands.put("/registration" , new RegistrationCommand());
        commands.put("/display", new UserDisplayCommand());
        commands.put("/index", new IndexCommand());
        commands.put("/index/comment", new IndexCommentCommand());
        commands.put("/index/payment", new IndexPaymentCommand());
        commands.put("/balance", new BalanceCommand());
        commands.put("/display/delete", new UserDeleteCommand());
        commands.put("/display/edit", new UserEditCommand());
        commands.put("/display-request", new RequestDisplayCommand());
        commands.put("/display-request/reject", new RequestRejectCommand());
        commands.put("/display-request/edit", new RequestEditCommand());
        commands.put("/index/complete", new IndexCompleteCommand());

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().replaceFirst(request.getContextPath() + "/app", "");
        Command command = commands.getOrDefault(path ,
                (r)->"/WEB-INF/view/index.jsp");

        log.info("Current command: " + command.getClass().getSimpleName());
        String page = command.execute(request);

        if(page.contains("redirect")) {
            response.sendRedirect(request.getContextPath() + request.getServletPath() + page.replace("redirect:", ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);

    }

}

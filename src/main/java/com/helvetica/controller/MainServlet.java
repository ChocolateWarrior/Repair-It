package com.helvetica.controller;

import com.helvetica.controller.command.*;
import com.helvetica.services.RequestService;
import com.helvetica.services.UserService;
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

    private final UserService userService = new UserService();
    private final RequestService requestService = new RequestService();

    public void init(ServletConfig servletConfig){

        commands.put("/logout", new LogoutCommand());
        commands.put("/login", new LoginCommand(userService));
        commands.put("/request", new RequestCommand());
        commands.put("/registration" , new RegistrationCommand(userService));
        commands.put("/display", new UserDisplayCommand(userService));
        commands.put("/index", new IndexCommand(userService, requestService));
        commands.put("/index/comment", new IndexCommentCommand(requestService));
        commands.put("/index/payment", new IndexPaymentCommand(userService));
        commands.put("/balance", new BalanceCommand(userService));
        commands.put("/display/delete", new UserDeleteCommand(userService));
        commands.put("/display/edit", new UserEditCommand(userService));
        commands.put("/display-request", new RequestDisplayCommand(requestService));
        commands.put("/display-request/reject", new RequestRejectCommand(requestService));
        commands.put("/display-request/edit", new RequestEditCommand(requestService, userService));
        commands.put("/index/complete", new IndexCompleteCommand(requestService));
        commands.put("/display-request/delete", new RequestDeleteCommand(requestService));

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

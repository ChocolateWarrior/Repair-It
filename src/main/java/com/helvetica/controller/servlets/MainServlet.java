package com.helvetica.controller.servlets;

import com.helvetica.controller.command.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@WebServlet(name = "MainServlet")
public class MainServlet extends HttpServlet {

    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){

        servletConfig.getServletContext()
                .setAttribute("users", new HashSet<String>());

        commands.put("logout", new LogoutCommand());
        commands.put("login", new LoginCommand());
        commands.put("exception" , new ExceptionCommand());
        commands.put("admin" , new AdminPageCommand());
        commands.put("request", new RequestCommand());
        commands.put("user" , new UserPageCommand());
        commands.put("registration" , new RegistrationCommand());
        commands.put("display", new UserDisplayCommand());

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI().replaceAll(".*/repairit_war/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/WEB-INF/view/index.jsp");

//        Command command = commands.get(path);
        System.out.println(command.getClass().getName());

        String page = command.execute(request);

        if(page.contains("redirect")){
            response.sendRedirect(page.replace("redirect:", ""));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request,response);

//        PrintWriter pw = response.getWriter();
//        pw.println("<html>");
//        pw.println("<p>HELLO</p>");
//        pw.println("</html>");

    }

}

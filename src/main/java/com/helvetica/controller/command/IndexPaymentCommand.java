package com.helvetica.controller.command;

import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

public class IndexPaymentCommand implements Command{

    private UserService userService;

    public IndexPaymentCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        request.setAttribute("balance", user.getBalance());
        request.setAttribute("paid", RequestState.PAID.name());
        request.setAttribute("completed", RequestState.COMPLETED.name());

        int user_id = user.getId();
        int request_id = Integer.parseInt(request.getParameter("request_payment_id"));
        BigDecimal price = new BigDecimal(request.getParameter("request_payment_price"));

        userService.payForRequest(user_id, request_id, price);
        return "redirect:/index";
    }
}

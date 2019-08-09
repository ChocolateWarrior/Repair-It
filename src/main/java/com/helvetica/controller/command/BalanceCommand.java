package com.helvetica.controller.command;

import com.helvetica.model.entity.User;
import com.helvetica.services.BalanceService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class BalanceCommand implements Command {

    private BalanceService balanceService;

    public BalanceCommand() {
        this.balanceService = new BalanceService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        User userToEdit = balanceService.findById(id);

        request.setAttribute("user", userToEdit);
        request.setAttribute("balance", userToEdit.getBalance());
        request.setAttribute("replenish_message", "Balance successfully replenished!");

        Optional<String> sumOpt = Optional.ofNullable(request.getParameter("sum"));
        BigDecimal sum = sumOpt.isEmpty() ? new BigDecimal(0) : new BigDecimal(sumOpt.get());

        balanceService.replenishBalance(id, sum);
        return "/WEB-INF/view/balance.jsp";
    }
}

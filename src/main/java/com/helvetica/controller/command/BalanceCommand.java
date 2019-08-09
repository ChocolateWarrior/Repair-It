package com.helvetica.controller.command;

import com.helvetica.services.BalanceService;

import javax.servlet.http.HttpServletRequest;

public class BalanceCommand implements Command {

    private BalanceService balanceService;

    public BalanceCommand() {
        this.balanceService = new BalanceService();
    }

    @Override
    public String execute(HttpServletRequest request) {
        balanceService.replenishBalance(request);
        return "/WEB-INF/view/balance.jsp";
    }
}

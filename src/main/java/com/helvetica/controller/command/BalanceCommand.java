package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.PositiveValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.User;
import com.helvetica.services.BalanceService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class BalanceCommand implements Command {

    private BalanceService balanceService;
//    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages", );


    public BalanceCommand() {
        this.balanceService = new BalanceService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        PositiveValidator positiveValidator = new PositiveValidator(new BigDecimal(10), new BigDecimal(100000));
        NotBlankValidator notBlankValidator = new NotBlankValidator(positiveValidator);

        int id = Integer.parseInt(request.getParameter("id"));
        User userToEdit = balanceService.findById(id);

        request.setAttribute("user", userToEdit);
        request.setAttribute("balance", userToEdit.getBalance());

        Optional<String> sumOpt = Optional.ofNullable(request.getParameter("sum"));

        if(sumOpt.isPresent()) {
            Result result = notBlankValidator.validate(sumOpt.get());
            if (!result.isOk()){
                request.setAttribute("user", userToEdit);
                request.setAttribute("balance", userToEdit.getBalance());
                request.setAttribute("replenish_message_er", result.getMessage());

                return "/WEB-INF/view/balance.jsp";
            }
            else
                request.setAttribute("replenish_message_sc",
//                        resourceBundle.getString("balance.replenished_sc")
                        "NO"
                        );
        }

        BigDecimal sum = sumOpt.isEmpty() ? new BigDecimal(0) : new BigDecimal(sumOpt.get());

        balanceService.replenishBalance(id, sum);
        return "/WEB-INF/view/balance.jsp";
    }
}

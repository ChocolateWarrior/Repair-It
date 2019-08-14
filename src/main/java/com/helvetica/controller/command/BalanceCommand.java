package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.PositiveValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.User;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.ResourceBundle;

public class BalanceCommand implements Command {

    private UserService userService;
    private  ResourceBundle resourceBundle;


    public BalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));

        PositiveValidator positiveValidator = new PositiveValidator(new BigDecimal(10),
                new BigDecimal(100000), resourceBundle.getString("valid.positive"));
        NotBlankValidator notBlankValidator = new NotBlankValidator(positiveValidator,
                resourceBundle.getString("valid.non_blank"));

        int id = Integer.parseInt(request.getParameter("id"));
        User userToEdit = userService.findById(id);

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
            else {
                request.setAttribute("replenish_message_sc",
                        resourceBundle.getString("balance.replenished_sc"));
            }
        }

        BigDecimal sum = sumOpt.isEmpty() ? new BigDecimal(0) : new BigDecimal(sumOpt.get());

        userService.replenishBalance(id, sum);
        request.setAttribute("balance", userToEdit.getBalance().add(sum));
        return "/WEB-INF/view/balance.jsp";
    }
}

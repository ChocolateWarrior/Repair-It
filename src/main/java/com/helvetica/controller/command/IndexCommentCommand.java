package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RequestState;
import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class IndexCommentCommand implements Command {

    private MainPageService mainPageService;

    public IndexCommentCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(8, 80);
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator);

        int id = Integer.parseInt(request.getParameter("request_comment_id"));
        request.setAttribute("completed", RequestState.COMPLETED.name());
        String comment = request.getParameter("comment");

        if(Objects.nonNull(comment)) {
            Result result = notBlankValidator.validate(comment);
            if (!result.isOk()){
                request.setAttribute("comment_message_er", result.getMessage());
                return "redirect:/index";
            }
        }

        mainPageService.leaveComment(id, comment);
        return "redirect:/index";
    }
}

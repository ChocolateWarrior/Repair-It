package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RequestState;
import com.helvetica.services.MainPageService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.ResourceBundle;

public class IndexCommentCommand implements Command {

    private MainPageService mainPageService;
    private ResourceBundle resourceBundle;


    public IndexCommentCommand() {
        this.mainPageService = new MainPageService();
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));
        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(8, 80,
                resourceBundle.getString("valid.in_range"));
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator,
                resourceBundle.getString("valid.non_blank"));

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

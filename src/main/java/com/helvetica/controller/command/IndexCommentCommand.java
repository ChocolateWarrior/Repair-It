package com.helvetica.controller.command;

import com.helvetica.controller.validators.NotBlankValidator;
import com.helvetica.controller.validators.RangeLengthValidator;
import com.helvetica.controller.validators.Result;
import com.helvetica.model.entity.RequestState;
import com.helvetica.model.entity.User;
import com.helvetica.services.RequestService;
import com.helvetica.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.ResourceBundle;

public class IndexCommentCommand implements Command {

    private RequestService requestService;
    private UserService userService;
    private ResourceBundle resourceBundle;


    public IndexCommentCommand(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {

        resourceBundle = ResourceBundle.getBundle("property/messages", CommandUtility.getSessionLocale(request));
        RangeLengthValidator rangeLengthValidator = new RangeLengthValidator(8, 80,
                resourceBundle.getString("valid.in_range"));
        NotBlankValidator notBlankValidator = new NotBlankValidator(rangeLengthValidator,
                resourceBundle.getString("valid.non_blank"));

        HttpSession session = request.getSession();
        User user = userService.getByUsername((String)session.getAttribute("username"));

        request.setAttribute("user", user);
        request.setAttribute("user_requests", requestService.findByUser(user.getId()));
        request.setAttribute("master_requests", user.getMasterRequests());
        request.setAttribute("paid", RequestState.PAID);
        request.setAttribute("accepted", RequestState.ACCEPTED);

        int id = Integer.parseInt(request.getParameter("request_comment_id"));
        request.setAttribute("completed", RequestState.COMPLETED.name());
        String comment = request.getParameter("comment");

        if(Objects.nonNull(comment)) {
            Result result = notBlankValidator.validate(comment);
            if (!result.isOk()){
                request.setAttribute("comment_message_error", result.getMessage());
                return "/WEB-INF/view/index.jsp";
            }
        }

        requestService.leaveComment(id, comment);
        request.setAttribute("user_requests", requestService.findByUser(user.getId()));
        return "/WEB-INF/view/index.jsp";
    }
}
